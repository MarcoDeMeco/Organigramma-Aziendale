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

    public HashMap<String, Impiegato> getImpiegatiByName() {
        return impiegatiByName;
    }

    public LinkedList<String> getListaNodi() {
        return listaNodi;
    }
}
