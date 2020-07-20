package View;

import Controller.Controller;
import Model.AlberoAziendale;
import Model.UnitaOrganizzativa;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class AlberoView extends JTree implements TreeSelectionListener, Observer {

    private AlberoAziendale alberoAziendale;
    private PannelloImpiegati pannelloImpiegati;
    private DefaultMutableTreeNode nodoSelezionato;
    private Controller controller;

    public AlberoView(Controller controller, AlberoAziendale alberoAziendale, PannelloImpiegati pannelloImpiegati) {
        this.alberoAziendale = alberoAziendale;
        this.pannelloImpiegati = pannelloImpiegati;
        this.controller = controller;

        alberoAziendale.attach(this);

        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) getCellRenderer();
        Icon closedIcon = new ImageIcon("open.png");
        Icon openIcon = new ImageIcon("open.png");
        Icon leafIcon = new ImageIcon("leaf.png");
        renderer.setClosedIcon(closedIcon);
        renderer.setOpenIcon(openIcon);
        renderer.setLeafIcon(leafIcon);

        addTreeSelectionListener(this);
        setModel(alberoAziendale.getAziendaTreeModel());
        setEditable(false);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setShowsRootHandles(true);

        nodoSelezionato = (DefaultMutableTreeNode) getModel().getRoot();
        controller.setNodoSelezionato(nodoSelezionato);
        UnitaOrganizzativa unitaSelezionata = (UnitaOrganizzativa) nodoSelezionato.getUserObject();
        unitaSelezionata.attach(pannelloImpiegati);
        // TODO controlla
        pannelloImpiegati.aggiornaNodoSelezionato(unitaSelezionata);
    }

    public DefaultMutableTreeNode getNodoSelezionato() {
        return nodoSelezionato;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        UnitaOrganizzativa unitaSelezionata = null;

        if (nodoSelezionato != null) {
            unitaSelezionata = (UnitaOrganizzativa) nodoSelezionato.getUserObject();
            unitaSelezionata.detach(pannelloImpiegati);
        }

        TreePath parentPath = getSelectionPath();

        if (parentPath != null) {
            nodoSelezionato = (DefaultMutableTreeNode) parentPath.getLastPathComponent();
        } else {
            nodoSelezionato = (DefaultMutableTreeNode) getModel().getRoot();
        }
        controller.setNodoSelezionato(nodoSelezionato);
        unitaSelezionata = (UnitaOrganizzativa) nodoSelezionato.getUserObject();
        unitaSelezionata.attach(pannelloImpiegati);
        pannelloImpiegati.aggiornaNodoSelezionato(unitaSelezionata);
    }

    @Override
    public void aggiorna() {
        setModel(alberoAziendale.getAziendaTreeModel());
        DefaultMutableTreeNode nodoSelezionato = (DefaultMutableTreeNode) getModel().getRoot();
        pannelloImpiegati.aggiornaNodoSelezionato((UnitaOrganizzativa) nodoSelezionato.getUserObject());
    }
}
