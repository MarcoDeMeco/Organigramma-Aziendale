package Model;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import java.util.LinkedList;

public class AlberoAziendale extends AbstractModel {
    private AziendaTreeModel aziendaTreeModel;

    public AlberoAziendale() {
        aziendaTreeModel = new AziendaTreeModel();
    }

    public DefaultMutableTreeNode aggiungiNodo(DefaultMutableTreeNode parent, String nomeNodo) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new UnitaOrganizzativa(nomeNodo));
        aziendaTreeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        getListaNodi().add(nomeNodo);
        return childNode;
    }

    public void rimuoviNodo(DefaultMutableTreeNode nodoSelezionato) {
        aziendaTreeModel.removeNodeFromParent(nodoSelezionato);
        String nomeNodo = ((UnitaOrganizzativa) nodoSelezionato.getUserObject()).toString();
        getListaNodi().remove(nomeNodo);
        aggiorna();
    }

    public void aggiungiImpiegato(Impiegato impiegato){
        if(getImpiegatiByName().containsKey((String) impiegato.getNome())){
            return;
        }
        getImpiegatiByName().put((String) impiegato.getNome(), impiegato);
    }

    public void setAziendaTreeModel(AziendaTreeModel aziendaTreeModel) {
        this.aziendaTreeModel = aziendaTreeModel;
        aggiorna();
    }

    public LinkedList<String> getListaNodi(){
        return aziendaTreeModel.getListaNodi();
    }

    public AziendaTreeModel getAziendaTreeModel() {
        return aziendaTreeModel;
    }

    public HashMap<String, Impiegato> getImpiegatiByName(){
        return aziendaTreeModel.getImpiegatiByName();
    }
}
