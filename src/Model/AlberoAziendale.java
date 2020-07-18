package Model;

import Controller.Controller;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class AlberoAziendale extends DefaultTreeModel {

    // <nodo, lista impiegati del nodo>
    private HashMap<String, LinkedList<Impiegato>> impiegatiInNodo;

    // <nome impiegato, impiegato corrispondente>
    private HashMap<String, Impiegato> impiegatiByName;

    // <nodo, lista ruoli nel nodo>
    private HashMap<String, LinkedList<String>> ruoliInNodo;

    private DefaultMutableTreeNode rootNode;

    public AlberoAziendale() {
        super(new DefaultMutableTreeNode("Azienda"));

        rootNode = (DefaultMutableTreeNode) getRoot();

        impiegatiInNodo = new HashMap<>();
        impiegatiInNodo.put((String) rootNode.getUserObject(), new LinkedList<>());

        impiegatiByName = new HashMap<>();

        ruoliInNodo = new HashMap<>();
        LinkedList<String> listaRuoli = new LinkedList<>();
        listaRuoli.add("CEO");
        listaRuoli.add("CTO");
        listaRuoli.add("Direttore");
        ruoliInNodo.put((String) rootNode.getUserObject(), listaRuoli);
    }

    public DefaultMutableTreeNode aggiungiNodo(DefaultMutableTreeNode parent, String nomeNodo) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(nomeNodo);

        if (impiegatiInNodo.containsKey((String) childNode.getUserObject())) {
            JOptionPane.showMessageDialog(null, "Il nodo "+nomeNodo+" esiste già");
            return null;
        }

        insertNodeInto(childNode, parent, parent.getChildCount());
        impiegatiInNodo.put((String) childNode.getUserObject(), new LinkedList<>());
        ruoliInNodo.put(nomeNodo, new LinkedList<>());
        return childNode;
    }

    public void rimuoviNodo(DefaultMutableTreeNode nodoSelezionato) {
        for(Impiegato i : impiegatiInNodo.get((String) nodoSelezionato.getUserObject())){
            i.rimuoviImpiego((String) nodoSelezionato.getUserObject());
        }
        impiegatiInNodo.remove((String) nodoSelezionato.getUserObject());
        ruoliInNodo.remove((String) nodoSelezionato.getUserObject());
        removeNodeFromParent(nodoSelezionato);
    }

    public void aggiungiImpiegato(DefaultMutableTreeNode nodoSelezionato, String nomeImpiegato, String ruolo) {
        if (!impiegatiByName.containsKey(nomeImpiegato)) {
            impiegatiByName.put(nomeImpiegato, new Impiegato(nomeImpiegato));
        }
        Impiegato impiegato = impiegatiByName.get(nomeImpiegato);

        LinkedList<Impiegato> listaImpiegati = impiegatiInNodo.get((String) nodoSelezionato.getUserObject());

        if (listaImpiegati.contains(impiegato)) {
            JOptionPane.showMessageDialog(null, "L'impiegato "+nomeImpiegato+" è già presente in questo nodo");
            return;
        }

        impiegato.aggiungiRuolo((String) nodoSelezionato.getUserObject(), ruolo);
        listaImpiegati.add(impiegato);
    }

    public LinkedList<Impiegato> getListaImpiegati(DefaultMutableTreeNode nodoSelezionato){
        return impiegatiInNodo.get((String) nodoSelezionato.getUserObject());
    }

    public void rimuoviImpiegato(DefaultMutableTreeNode nodoSelezionato, Impiegato impiegato) {
        impiegato.rimuoviImpiego((String) nodoSelezionato.getUserObject());
        if(impiegato.getRuoloByNodo().isEmpty()){
            impiegatiByName.remove(impiegato.getNome());
        }
        LinkedList<Impiegato> lista = impiegatiInNodo.get((String) nodoSelezionato.getUserObject());
        lista.remove(impiegato);
    }

    public LinkedList<String> getListaRuoli(DefaultMutableTreeNode nodoSelezionato) {
        return ruoliInNodo.get((String) nodoSelezionato.getUserObject());
    }

    public void rimuoviRuolo(DefaultMutableTreeNode nodoSelezionato, String ruolo) {
        LinkedList<String> lista = ruoliInNodo.get((String) nodoSelezionato.getUserObject());

        LinkedList<Impiegato> impiegati = impiegatiInNodo.get((String) nodoSelezionato.getUserObject());
        for (Impiegato i : impiegati) {
            if (i.getRuolo((String) nodoSelezionato.getUserObject()).equals(ruolo)) {
                JOptionPane.showMessageDialog(null, "Esiste almeno un impiegato con questo ruolo\nAssicurati che nessun impiegato abbia il ruolo in questione prima di eliminarlo");
                return;
            }
        }

        lista.remove(ruolo);
    }

    public void aggiungiRuolo(DefaultMutableTreeNode nodoSelezionato, String ruolo) {
        LinkedList<String> lista = ruoliInNodo.get((String) nodoSelezionato.getUserObject());
        if(lista.contains(ruolo)){
            JOptionPane.showMessageDialog(null, "Il ruolo "+ruolo+" esiste già");
            return;
        }

        lista.add(ruolo);
    }
}
