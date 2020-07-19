package Controller;

import Model.AlberoAziendale;
import Model.AziendaTreeModel;
import Model.Impiegato;
import Model.UnitaOrganizzativa;
import View.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class Controller {
    private AlberoAziendale alberoAziendale;
    private FinestraOrganigramma finestra;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    // ------ DA VIEW -------

    public UnitaOrganizzativa aggiungiNodo(UnitaOrganizzativa nodoSelezionato, String nomeNodo) {
        return alberoAziendale.aggiungiNodo(nodoSelezionato, nomeNodo);
        // TODO
//        if (nuovoNodo != null) {
//            alberoView.scrollPathToVisible(new TreePath(nuovoNodo.getPath()));
//        }
    }

    public void rimuoviNodo(UnitaOrganizzativa nodoSelezionato) {
        UnitaOrganizzativa parent = (UnitaOrganizzativa) nodoSelezionato.getParent();

        if (parent == null) {
            toolkit.beep();
            return;
        }

        alberoAziendale.rimuoviNodo(nodoSelezionato);
    }

    public void aggiungiImpiegato(UnitaOrganizzativa nodoSelezionato, String nomeImpiegato, String ruolo) {
        Impiegato nuovoImpiegato = new Impiegato(nomeImpiegato);
        if(alberoAziendale.getImpiegatiByName().containsKey(nomeImpiegato)){
            nuovoImpiegato = alberoAziendale.getImpiegatiByName().get(nomeImpiegato);
        }

        if(nodoSelezionato.getListaImpiegati().contains(nuovoImpiegato)){
            // TODO messaggio di errore: L'impiegato è già assunto
            return;
        }

        nuovoImpiegato.aggiungiRuolo((String) nodoSelezionato.getUserObject(), ruolo);
        nodoSelezionato.aggiungiImpiegato(nuovoImpiegato);
        alberoAziendale.aggiungiImpiegato(nuovoImpiegato);
    }

    public void rimuoviImpiegato(UnitaOrganizzativa nodoSelezionato, Impiegato impiegato) {
        nodoSelezionato.rimuoviImpiegato(impiegato);
        impiegato.rimuoviImpiego((String) nodoSelezionato.getUserObject());
    }

    public void rimuoviRuolo(UnitaOrganizzativa nodoSelezionato, String ruolo) {
        for(Impiegato i : nodoSelezionato.getListaImpiegati()){
            if(i.getRuolo((String) nodoSelezionato.getUserObject()).equals(ruolo)){
                // TODO messaggio di errore: c'è almeno un impiegato che ha questo ruolo
                return;
            }
        }
        nodoSelezionato.rimuoviRuolo(ruolo);
    }

    public void aggiungiRuolo(UnitaOrganizzativa nodoSelezionato, String ruolo) {
        if(nodoSelezionato.getListaRuoli().contains(ruolo)){
            // TODO messaggio di errore: il ruolo esiste già
            return;
        }
        nodoSelezionato.aggiungiRuolo(ruolo);
    }

    public void salva() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salva");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave.getAbsolutePath()));
                oos.writeObject(alberoAziendale.getAziendaTreeModel());

            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Errore durante la scrittura del file");
                e.printStackTrace();
            }
        }
    }

    public void apri() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Apri");
        fileChooser.setApproveButtonText("Apri");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToSave.getAbsolutePath()));
                AziendaTreeModel model = (AziendaTreeModel) ois.readObject();
                alberoAziendale.setAziendaTreeModel(model);

            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Errore");
                e.printStackTrace();
            }
        }
    }

    // ------ INIT ------

    public void createAndShowGUI() {
        alberoAziendale = new AlberoAziendale();
        new FinestraOrganigramma(this, alberoAziendale).setVisible(true);
    }
}
