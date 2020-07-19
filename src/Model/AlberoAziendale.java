package Model;

import javax.swing.*;
import java.util.HashMap;

public class AlberoAziendale extends AbstractModel {

    // TODO qua ho applicato qualche pattern senza rendermene conto

    private AziendaTreeModel aziendaTreeModel;

    public AlberoAziendale() {
        aziendaTreeModel = new AziendaTreeModel();
    }

    public UnitaOrganizzativa aggiungiNodo(UnitaOrganizzativa parent, String nomeNodo) {
        // TODO controllare il nome del nodo ""
        if (aziendaTreeModel.getListaNodi().contains(nomeNodo)) {
            JOptionPane.showMessageDialog(null, "Il nodo " + nomeNodo + " esiste già");
            return null;
        }
        UnitaOrganizzativa childNode = new UnitaOrganizzativa(nomeNodo);
        aziendaTreeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        return childNode;
    }

    public void rimuoviNodo(UnitaOrganizzativa nodoSelezionato) {
        for(Impiegato i : nodoSelezionato.getListaImpiegati()){
            i.rimuoviImpiego((String) nodoSelezionato.getUserObject());
            if(i.isDisoccupato())
                getImpiegatiByName().remove(i);
        }
        aziendaTreeModel.removeNodeFromParent(nodoSelezionato);
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

    public AziendaTreeModel getAziendaTreeModel() {
        return aziendaTreeModel;
    }

    public HashMap<String, Impiegato> getImpiegatiByName(){
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
