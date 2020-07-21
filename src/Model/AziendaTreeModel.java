package Model;

import javax.swing.tree.*;
import java.util.HashMap;
import java.util.LinkedList;

public class AziendaTreeModel extends DefaultTreeModel {
    // <nome impiegato, impiegato>
    private HashMap<String, Impiegato> impiegatiByName;

    private LinkedList<String> listaNodi;
    private DefaultMutableTreeNode rootNode;

    public AziendaTreeModel() {
        super(new DefaultMutableTreeNode(new UnitaOrganizzativa("Azienda")));

        impiegatiByName = new HashMap<>();
        listaNodi = new LinkedList<>();

        rootNode = (DefaultMutableTreeNode) getRoot();

        UnitaOrganizzativa azienda = (UnitaOrganizzativa) rootNode.getUserObject();
        azienda.aggiungiRuolo("CEO");
        azienda.aggiungiRuolo("CTO");
        azienda.aggiungiRuolo("Direttore");
    }

    public LinkedList<String> getListaNodi() {
        return listaNodi;
    }

    public HashMap<String, Impiegato> getImpiegatiByName() {
        return impiegatiByName;
    }
}
