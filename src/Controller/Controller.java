package Controller;

import Model.AlberoAziendale;
import Model.Impiegato;
import View.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;

public class Controller {
    private static AlberoAziendale alberoAziendale;
    private static PannelloImpiegati pannelloImpiegati;
    private static PannelloAlbero pannelloAlbero;
    private static DialogoRuoli dialogoRuoli;
    private static AlberoView alberoView;
    private static DefaultMutableTreeNode nodoSelezionato;
    private static Toolkit toolkit = Toolkit.getDefaultToolkit();

    // ------ DA VIEW -------

    public static void aggiungiNodo(String nomeNodo) {
        DefaultMutableTreeNode nuovoNodo = alberoAziendale.aggiungiNodo(nodoSelezionato, nomeNodo);
        if (nuovoNodo != null) {
            alberoView.scrollPathToVisible(new TreePath(nuovoNodo.getPath()));
        }
    }

    public static void rimuoviNodoCorrente() {
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) nodoSelezionato.getParent();

        if (parent == null) {
            toolkit.beep();
            return;
        }

        alberoAziendale.rimuoviNodo(nodoSelezionato);
    }

    public static void aggiungiImpiegato(String nomeImpiegato, String ruolo) {
        alberoAziendale.aggiungiImpiegato(nodoSelezionato, nomeImpiegato, ruolo);
        aggiornaPannelloImpiegati();
    }

    public static void rimuoviImpiegato(Impiegato impiegato) {
        alberoAziendale.rimuoviImpiegato(nodoSelezionato, impiegato);
        aggiornaPannelloImpiegati();
    }

    public static void gestisciRuoli() {
        LinkedList<String> lista = alberoAziendale.getListaRuoli(nodoSelezionato);
        dialogoRuoli = new DialogoRuoli((String) nodoSelezionato.getUserObject(), lista);
        dialogoRuoli.setVisible(true);
    }

    public static void rimuoviRuolo(String ruolo) {
        alberoAziendale.rimuoviRuolo(nodoSelezionato, ruolo);
        aggiornaDialogoRuoli();
    }

    public static void aggiungiRuolo(String ruolo) {
        alberoAziendale.aggiungiRuolo(nodoSelezionato, ruolo);
        aggiornaDialogoRuoli();
    }

    public static void aggiungiImpiegato() {
        LinkedList<String> lista = alberoAziendale.getListaRuoli(nodoSelezionato);
        if (lista.size() == 0) {
            JOptionPane.showMessageDialog(null, "Nessun ruolo disponibile per il nodo " + (String) nodoSelezionato.getUserObject());
            return;
        }
        new AggiungiImpiegato((String) nodoSelezionato.getUserObject(), lista).setVisible(true);
    }

    public static void salva() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salva");
        fileChooser.setApproveButtonText("Salva");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave.getAbsolutePath()));
                oos.writeObject(alberoAziendale);

            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Errore durante la scrittura del file");
                e.printStackTrace();
            }
        }
    }

    public static void apri() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Apri");
        fileChooser.setApproveButtonText("Apri");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToSave.getAbsolutePath()));
                alberoAziendale = (AlberoAziendale) ois.readObject();
                aggiornaAlbero();

            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Errore");
                e.printStackTrace();
            }
        }
    }

    // --------------------------------

    public static void setNodoSelezionato(DefaultMutableTreeNode nodoSelezionato) {
        Controller.nodoSelezionato = nodoSelezionato;
        if (nodoSelezionato == null) {
            Controller.nodoSelezionato = (DefaultMutableTreeNode) alberoAziendale.getRoot();
        }
        aggiornaPannelloImpiegati();
    }

    public static void aggiornaAlbero() {
        nodoSelezionato = (DefaultMutableTreeNode) alberoAziendale.getRoot();
        alberoView.setAlberoAziendale(alberoAziendale);
        aggiornaPannelloImpiegati();
    }

    public static void aggiornaPannelloImpiegati() {
        LinkedList<Impiegato> lista = alberoAziendale.getListaImpiegati(nodoSelezionato);
        pannelloImpiegati.aggiornaImpiegati((String) nodoSelezionato.getUserObject(), lista);
    }

    public static void aggiornaDialogoRuoli(){
        LinkedList<String> lista = alberoAziendale.getListaRuoli(nodoSelezionato);
        dialogoRuoli.aggiornaRuoli(lista);
    }

    // ------ INIT ------

    public static void createAndShowGUI() {
        alberoAziendale = new AlberoAziendale();
        nodoSelezionato = (DefaultMutableTreeNode) alberoAziendale.getRoot();
        alberoView = new AlberoView(alberoAziendale);
        pannelloAlbero = new PannelloAlbero(alberoView);
        pannelloImpiegati = new PannelloImpiegati();
        new FinestraOrganigramma(pannelloAlbero, pannelloImpiegati).setVisible(true);
    }
}
