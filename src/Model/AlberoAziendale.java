package Model;

import Controller.Controller;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;

public class AlberoAziendale extends JTree implements TreeSelectionListener {
    // <nodo, lista impiegati del nodo>
    private HashMap<String, LinkedList<Impiegato>> impiegatiInNodo;

    // <nome impiegato, impiegato corrispondente>
    private HashMap<String, Impiegato> impiegatiByName;

    // <nodo, lista ruoli nel nodo>
    private HashMap<String, LinkedList<String>> ruoliInNodo;

    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public AlberoAziendale() {
        super();

        // TODO cambia l'icona

        rootNode = new DefaultMutableTreeNode("Azienda");
        treeModel = new DefaultTreeModel(rootNode);

        impiegatiInNodo = new HashMap<>();
        impiegatiInNodo.put((String) rootNode.getUserObject(), new LinkedList<>());

        impiegatiByName = new HashMap<>();

        ruoliInNodo = new HashMap<>();
        LinkedList<String> listaRuoli = new LinkedList<>();
        listaRuoli.add("CEO");
        listaRuoli.add("CTO");
        listaRuoli.add("Direttore");
        ruoliInNodo.put((String) rootNode.getUserObject(), listaRuoli);

        addTreeSelectionListener(this);

        setModel(treeModel);
        setEditable(false);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setShowsRootHandles(true);
    }

    public AlberoAziendale(Object[] fromRead) {
        super();

        try {
            treeModel = (DefaultTreeModel) fromRead[0];
            impiegatiInNodo = (HashMap<String, LinkedList<Impiegato>>) fromRead[1];
            ruoliInNodo = (HashMap<String, LinkedList<String>>) fromRead[2];
            impiegatiByName = (HashMap<String, Impiegato>) fromRead[3];

            rootNode = (DefaultMutableTreeNode) treeModel.getRoot();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Errore durante la lettura del file");
        }

        DefaultMutableTreeNode nodoSelezionato = getNodoSelezionato();
        Controller.aggiornaPannelloImpiegati((String) nodoSelezionato.getUserObject(), impiegatiInNodo.get((String) nodoSelezionato.getUserObject()));

        addTreeSelectionListener(this);
        setModel(treeModel);
        setEditable(false);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setShowsRootHandles(true);
    }

    private DefaultMutableTreeNode getNodoSelezionato() {
        DefaultMutableTreeNode nodoSelezionato = null;
        TreePath parentPath = getSelectionPath();

        if (parentPath == null) {
            nodoSelezionato = rootNode;
        } else {
            nodoSelezionato = (DefaultMutableTreeNode) parentPath.getLastPathComponent();
        }

        return nodoSelezionato;
    }

    public DefaultMutableTreeNode aggiungiNodo(String nomeNodo) {
        DefaultMutableTreeNode parent = getNodoSelezionato();
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(nomeNodo);

        if (impiegatiInNodo.containsKey((String) childNode.getUserObject())) {
            JOptionPane.showMessageDialog(null, "Il nodo "+nomeNodo+" esiste già");
            return null;
        }

        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        impiegatiInNodo.put((String) childNode.getUserObject(), new LinkedList<>());
        ruoliInNodo.put(nomeNodo, new LinkedList<>());

        scrollPathToVisible(new TreePath(childNode.getPath()));
        return childNode;
    }

    public void rimuoviNodoCorrente() {
        DefaultMutableTreeNode nodoSelezionato = getNodoSelezionato();
        MutableTreeNode parent = (MutableTreeNode) nodoSelezionato.getParent();

        if (parent == null) {
            toolkit.beep();
            return;
        }

        for(Impiegato i : impiegatiInNodo.get((String) nodoSelezionato.getUserObject())){
            i.rimuoviImpiego((String) nodoSelezionato.getUserObject());
        }
        impiegatiInNodo.remove((String) nodoSelezionato.getUserObject());
        ruoliInNodo.remove((String) nodoSelezionato.getUserObject());
        treeModel.removeNodeFromParent(nodoSelezionato);
    }

    public void aggiungiImpiegato(String nomeImpiegato, String ruolo) {
        DefaultMutableTreeNode nodoSelezionato = getNodoSelezionato();

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

        Controller.aggiornaPannelloImpiegati((String) nodoSelezionato.getUserObject(), listaImpiegati);
    }

    public void rimuoviImpiegato(Impiegato impiegato) {
        DefaultMutableTreeNode nodoSelezionato = getNodoSelezionato();

        impiegato.rimuoviImpiego((String) nodoSelezionato.getUserObject());
        if(impiegato.getRuoloByNodo().isEmpty()){
            impiegatiByName.remove(impiegato.getNome());
        }
        LinkedList<Impiegato> lista = impiegatiInNodo.get((String) nodoSelezionato.getUserObject());
        lista.remove(impiegato);
        Controller.aggiornaPannelloImpiegati((String) nodoSelezionato.getUserObject(), lista);
    }

    public Object[] getListaRuoli() {
        DefaultMutableTreeNode nodoSelezionato = getNodoSelezionato();

        LinkedList<String> lista = ruoliInNodo.get((String) nodoSelezionato.getUserObject());
        Object[] res = new Object[2];
        res[0] = nodoSelezionato.getUserObject();
        res[1] = lista;
        return res;
    }

    public LinkedList<String> rimuoviRuolo(String ruolo) {
        DefaultMutableTreeNode nodoSelezionato = getNodoSelezionato();

        LinkedList<String> lista = ruoliInNodo.get((String) nodoSelezionato.getUserObject());

        LinkedList<Impiegato> impiegati = impiegatiInNodo.get((String) nodoSelezionato.getUserObject());
        for (Impiegato i : impiegati) {
            if (i.getRuolo((String) nodoSelezionato.getUserObject()).equals(ruolo)) {
                JOptionPane.showMessageDialog(null, "Esiste almeno un impiegato con questo ruolo\nAssicurati che nessun impiegato abbia il ruolo in questione prima di eliminarlo");
                return lista;
            }
        }

        lista.remove(ruolo);
        return lista;
    }

    public LinkedList<String> aggiungiRuolo(String ruolo) {
        DefaultMutableTreeNode nodoSelezionato = getNodoSelezionato();

        LinkedList<String> lista = ruoliInNodo.get((String) nodoSelezionato.getUserObject());
        if(lista.contains(ruolo)){
            JOptionPane.showMessageDialog(null, "Il ruolo "+ruolo+" esiste già");
            return lista;
        }

        lista.add(ruolo);
        return lista;
    }

    public Object[] getObjectsToSave(){
        Object[] toSave = new Object[4];
        toSave[0] = treeModel;
        toSave[1] = impiegatiInNodo;
        toSave[2] = ruoliInNodo;
        toSave[3] = impiegatiByName;
        return toSave;
    }

    public void setObjectsFromRead(Object[] fromRead){

    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode nodoSelezionato = getNodoSelezionato();

        LinkedList<Impiegato> lista = impiegatiInNodo.get((String) nodoSelezionato.getUserObject());
        Controller.aggiornaPannelloImpiegati((String) nodoSelezionato.getUserObject(), lista);
    }
}
