package Model;

import Controller.Controller;
import View.Observer;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class AlberoAziendale extends AbstractModel {

    private AziendaTreeModel aziendaTreeModel;

    public AlberoAziendale() {
        aziendaTreeModel = new AziendaTreeModel();
    }

    public void setAziendaTreeModel(AziendaTreeModel aziendaTreeModel) {
        this.aziendaTreeModel = aziendaTreeModel;
        aggiorna();
    }

    public AziendaTreeModel getAziendaTreeModel() {
        return aziendaTreeModel;
    }

    public UnitaOrganizzativa aggiungiNodo(UnitaOrganizzativa parent, String nomeNodo) {
        return aziendaTreeModel.aggiungiNodo(parent, nomeNodo);
    }

    public void rimuoviNodo(UnitaOrganizzativa nodoSelezionato) {
        aziendaTreeModel.rimuoviNodo(nodoSelezionato);
    }

    public HashMap<String, Impiegato> getImpiegatoByName(){
        return aziendaTreeModel.getImpiegatiByName();
    }

    // TODO fare questo controllo in controller
//    public void aggiungiImpiegato(UnitaOrganizzativa nodoSelezionato, String nomeImpiegato, String ruolo) {
//        HashMap<String, Impiegato> impiegatiByName = aziendaTreeModel.getImpiegatiByName();
//
//        if (!impiegatiByName.containsKey(nomeImpiegato)) {
//            impiegatiByName.put(nomeImpiegato, new Impiegato(nomeImpiegato));
//        }
//        Impiegato impiegato = impiegatiByName.get(nomeImpiegato);
//
//        if(nodoSelezionato.getListaImpiegati().contains(impiegato)){
//        }
//
//        nodoSelezionato.aggiungiImpiegato(impiegato);
//    }


    // TODO fare questo controllo in controller
//    public void rimuoviImpiegato(DefaultMutableTreeNode nodoSelezionato, Impiegato impiegato) {
//        impiegato.rimuoviImpiego((String) nodoSelezionato.getUserObject());
//        if (impiegato.getRuoloByNodo().isEmpty()) {
//            impiegatiByName.remove(impiegato.getNome());
//        }
//        LinkedList<Impiegato> lista = impiegatiInNodo.get((String) nodoSelezionato.getUserObject());
//        lista.remove(impiegato);
//    }

    // TODO fare questo controllo in controller
//    public void rimuoviRuolo(DefaultMutableTreeNode nodoSelezionato, String ruolo) {
//        LinkedList<String> lista = ruoliInNodo.get((String) nodoSelezionato.getUserObject());
//
//        LinkedList<Impiegato> impiegati = impiegatiInNodo.get((String) nodoSelezionato.getUserObject());
//        for (Impiegato i : impiegati) {
//            if (i.getRuolo((String) nodoSelezionato.getUserObject()).equals(ruolo)) {
//                JOptionPane.showMessageDialog(null, "Esiste almeno un impiegato con questo ruolo\nAssicurati che nessun impiegato abbia il ruolo in questione prima di eliminarlo");
//                return;
//            }
//        }
//
//        lista.remove(ruolo);
//    }

    // TODO fare questo controllo in controller
//    public void aggiungiRuolo(DefaultMutableTreeNode nodoSelezionato, String ruolo) {
//        LinkedList<String> lista = ruoliInNodo.get((String) nodoSelezionato.getUserObject());
//        if (lista.contains(ruolo)) {
//            JOptionPane.showMessageDialog(null, "Il ruolo " + ruolo + " esiste già");
//            return;
//        }
//
//        lista.add(ruolo);
//    }
}
