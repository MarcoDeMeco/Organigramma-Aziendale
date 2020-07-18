package View;

import Controller.Controller;
import Model.AlberoAziendale;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class AlberoView extends JTree implements TreeSelectionListener {

    public AlberoView(AlberoAziendale alberoAziendale) {
        addTreeSelectionListener(this);
        setModel(alberoAziendale);
        setEditable(false);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setShowsRootHandles(true);
    }

    public void setAlberoAziendale(AlberoAziendale alberoAziendale) {
        setModel(alberoAziendale);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath parentPath = getSelectionPath();
        DefaultMutableTreeNode nodoSelezionato = null;

        if (parentPath != null) {
            nodoSelezionato = (DefaultMutableTreeNode) parentPath.getLastPathComponent();
        }

        Controller.setNodoSelezionato(nodoSelezionato);
    }
}
