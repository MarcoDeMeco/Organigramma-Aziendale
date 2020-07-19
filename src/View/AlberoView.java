package View;

import Model.AlberoAziendale;
import Model.UnitaOrganizzativa;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class AlberoView extends JTree implements TreeSelectionListener, Observer {

    private AlberoAziendale alberoAziendale;
    private PannelloImpiegati pannelloImpiegati;
    private UnitaOrganizzativa nodoSelezionato;

    public AlberoView(AlberoAziendale alberoAziendale, PannelloImpiegati pannelloImpiegati) {
        this.alberoAziendale = alberoAziendale;
        this.pannelloImpiegati = pannelloImpiegati;

        addTreeSelectionListener(this);
        setModel(alberoAziendale.getAziendaTreeModel());
        setEditable(false);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setShowsRootHandles(true);
    }

    public UnitaOrganizzativa getNodoSelezionato() {
        return nodoSelezionato;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        if(nodoSelezionato != null){
            nodoSelezionato.detach(pannelloImpiegati);
        }

        TreePath parentPath = getSelectionPath();

        if (parentPath != null) {
            nodoSelezionato = (UnitaOrganizzativa) parentPath.getLastPathComponent();
        }
        else {
            nodoSelezionato = (UnitaOrganizzativa) getModel().getRoot();
        }
        nodoSelezionato.attach(pannelloImpiegati);
        pannelloImpiegati.aggiornaNodoSelezionato(nodoSelezionato);
    }

    @Override
    public void aggiorna() {
        setModel(alberoAziendale.getAziendaTreeModel());
        UnitaOrganizzativa nodoSelezionato = (UnitaOrganizzativa) getModel().getRoot();
        pannelloImpiegati.aggiornaNodoSelezionato(nodoSelezionato);
    }
}
