package Controller;

import Model.AlberoAziendale;
import Model.AziendaTreeModel;
import Model.Impiegato;
import Model.UnitaOrganizzativa;
import View.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.io.*;

public class Controller {
    private AlberoAziendale alberoAziendale;
    private FinestraOrganigramma finestra;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private DefaultMutableTreeNode nodoSelezionato;
    private UnitaOrganizzativa unitaSelezionata;

    public Controller(){
        alberoAziendale = new AlberoAziendale();
        finestra = new FinestraOrganigramma(this, alberoAziendale);
        // TODO controllare i setVisible
        finestra.setVisible(true);
    }

    public DefaultMutableTreeNode aggiungiNodo(String nomeNodo) {
        if (alberoAziendale.getListaNodi().contains(nomeNodo)) {
            // TODO
            JOptionPane.showMessageDialog(null, "Il nodo " + nomeNodo + " esiste già");
            return null;
        }
        finestra.setModificaEffettuata(true);
        return alberoAziendale.aggiungiNodo(nodoSelezionato, nomeNodo);
    }

    public void rimuoviNodo() {
        TreeNode parent = nodoSelezionato.getParent();

        if (parent == null) {
            toolkit.beep();
            return;
        }

        for(Impiegato i : unitaSelezionata.getListaImpiegati()){
            i.rimuoviImpiego(unitaSelezionata.toString());
            if(i.isDisoccupato())
                alberoAziendale.getImpiegatiByName().remove(i);
        }

        alberoAziendale.rimuoviNodo(nodoSelezionato);
        finestra.setModificaEffettuata(true);
    }

    public void aggiungiImpiegato(String nomeImpiegato, String ruolo) {
        Impiegato nuovoImpiegato = new Impiegato(nomeImpiegato);
        if(alberoAziendale.getImpiegatiByName().containsKey(nomeImpiegato)){
            nuovoImpiegato = alberoAziendale.getImpiegatiByName().get(nomeImpiegato);
        }

        if(unitaSelezionata.getListaImpiegati().contains(nuovoImpiegato)){
            // TODO messaggio di errore: L'impiegato è già assunto
            return;
        }

        nuovoImpiegato.aggiungiRuolo(unitaSelezionata.toString(), ruolo);
        unitaSelezionata.aggiungiImpiegato(nuovoImpiegato);
        alberoAziendale.aggiungiImpiegato(nuovoImpiegato);
        finestra.setModificaEffettuata(true);
    }

    public void rimuoviImpiegato(Impiegato impiegato) {
        unitaSelezionata.rimuoviImpiegato(impiegato);
        impiegato.rimuoviImpiego(unitaSelezionata.toString());
        finestra.setModificaEffettuata(true);
    }

    public void rimuoviRuolo(String ruolo) {
        for(Impiegato i : unitaSelezionata.getListaImpiegati()){
            if(i.getRuolo(unitaSelezionata.toString()).equals(ruolo)){
                // TODO messaggio di errore: c'è almeno un impiegato che ha questo ruolo
                return;
            }
        }
        unitaSelezionata.rimuoviRuolo(ruolo);
        finestra.setModificaEffettuata(true);
    }

    public void aggiungiRuolo(String ruolo) {
        if(unitaSelezionata.getListaRuoli().contains(ruolo)){
            // TODO messaggio di errore: il ruolo esiste già
            return;
        }
        unitaSelezionata.aggiungiRuolo(ruolo);
        finestra.setModificaEffettuata(true);
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
        finestra.setModificaEffettuata(false);
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
        finestra.setModificaEffettuata(false);
    }

    public void setNodoSelezionato(DefaultMutableTreeNode nodoSelezionato) {
        this.nodoSelezionato = nodoSelezionato;
        unitaSelezionata = (UnitaOrganizzativa) nodoSelezionato.getUserObject();
    }
}
