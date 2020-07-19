package Model;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.HashMap;
import java.util.LinkedList;

public class AziendaTreeModel extends DefaultTreeModel {
    private HashMap<String, Impiegato> impiegatiByName;
    private LinkedList<String> listaNodi;
    private UnitaOrganizzativa rootNode;

    public AziendaTreeModel() {
        super(new UnitaOrganizzativa("Azienda"));

        impiegatiByName = new HashMap<>();
        listaNodi = new LinkedList<>();

        rootNode = (UnitaOrganizzativa) getRoot();
        rootNode.aggiungiRuolo("CEO");
        rootNode.aggiungiRuolo("CTO");
        rootNode.aggiungiRuolo("Direttore");
    }

    public UnitaOrganizzativa aggiungiNodo(UnitaOrganizzativa parent, String nomeNodo){
        UnitaOrganizzativa childNode = new UnitaOrganizzativa(nomeNodo);

        //TODO fare questo controllo in controller
//        if (listaNodi.contains((String) childNode.getUserObject())) {
//            // TODO dai la responsabilità alla view di mostrare questo errore
//            JOptionPane.showMessageDialog(null, "Il nodo " + nomeNodo + " esiste già");
//            return null;
//        }

        insertNodeInto(childNode, parent, parent.getChildCount());
        return childNode;
    }

    public void rimuoviNodo(UnitaOrganizzativa nodoSelezionato){
        for(Impiegato i : nodoSelezionato.getListaImpiegati()){
            i.rimuoviImpiego((String) nodoSelezionato.getUserObject());
            if(i.isDisoccupato())
                impiegatiByName.remove(i);
        }
        listaNodi.remove((String) nodoSelezionato.getUserObject());
        removeNodeFromParent(nodoSelezionato);
    }

    public HashMap<String, Impiegato> getImpiegatiByName() {
        return impiegatiByName;
    }
}
