package Controller;

import Model.AlberoAziendale;
import Model.Impiegato;
import View.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;
import java.util.LinkedList;
import java.util.Map;

public class Controller {
    private static AlberoAziendale alberoAziendale;
    private static PannelloImpiegati pannelloImpiegati;
    private static RuoliNodo ruoliNodo;
    private static PannelloAlbero pannelloAlbero;

    // ------ DA MODEL ------

    public static void aggiornaPannelloImpiegati(String nomeNodo, LinkedList<Impiegato> impiegati) {
        pannelloImpiegati.aggiornaImpiegati(nomeNodo, impiegati);
    }
    // ------ DA VIEW -------

    public static void aggiungiNodo(String nomeNodo) {
        alberoAziendale.aggiungiNodo(nomeNodo);
    }

    public static void rimuoviNodoCorrente() {
        alberoAziendale.rimuoviNodoCorrente();
    }

    public static void aggiungiImpiegato(String nomeImpiegato, String ruolo) {
        alberoAziendale.aggiungiImpiegato(nomeImpiegato, ruolo);
    }

    public static void gestisciRuoli() {
        Object[] res = alberoAziendale.getListaRuoli();
        ruoliNodo = new RuoliNodo((String) res[0], (LinkedList<String>) res[1]);
        ruoliNodo.setVisible(true);
    }

    public static void rimuoviImpiegato(Impiegato impiegato) {
        alberoAziendale.rimuoviImpiegato(impiegato);
    }

    public static void rimuoviRuolo(String ruolo) {
        LinkedList<String> lista = alberoAziendale.rimuoviRuolo(ruolo);
        ruoliNodo.aggiornaRuoli(lista);
    }

    public static void aggiungiRuolo(String ruolo) {
        LinkedList<String> lista = alberoAziendale.aggiungiRuolo(ruolo);
        ruoliNodo.aggiornaRuoli(lista);
    }

    public static void aggiungiImpiegato() {
        Object[] res = alberoAziendale.getListaRuoli();
        if (((LinkedList<String>) res[1]).size() == 0) {
            JOptionPane.showMessageDialog(null, "Nessun ruolo disponibile per il nodo "+(String) res[0]);
            return;
        }
        new AggiungiImpiegato((String) res[0], (LinkedList<String>) res[1]).setVisible(true);
    }

    public static void salva(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salva");
        fileChooser.setApproveButtonText("Salva");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave.getAbsolutePath()));
                oos.writeObject(alberoAziendale.getObjectsToSave());

            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Errore durante la scrittura del file");
                e.printStackTrace();
            }
        }
    }

    public static void apri(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Apri");
        fileChooser.setApproveButtonText("Apri");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToSave.getAbsolutePath()));
                Object[] fromRead = (Object[]) ois.readObject();
                alberoAziendale = new AlberoAziendale(fromRead);
                aggiornaAlbero();

            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Errore");
                e.printStackTrace();
            }
        }
    }

    // --------------------------------

    public static void setPannelloImpiegati(PannelloImpiegati pannelloImpiegati) {
        Controller.pannelloImpiegati = pannelloImpiegati;
    }

    public static void setPannelloAlbero(PannelloAlbero pannelloAlbero) {
        Controller.pannelloAlbero = pannelloAlbero;
    }

    public static void aggiornaAlbero(){
        pannelloAlbero.aggiorna();
    }

    public static AlberoAziendale getAlberoAziendale() {
        return alberoAziendale;
    }

    // ------ INIT ------

    public static void createAndShowGUI(){
        alberoAziendale = new AlberoAziendale();
        new FinestraOrganigramma().setVisible(true);
    }
}
