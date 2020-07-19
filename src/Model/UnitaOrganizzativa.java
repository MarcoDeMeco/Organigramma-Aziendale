package Model;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.beans.Transient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class UnitaOrganizzativa extends AbstractModel implements MutableTreeNode, Serializable {
    private DefaultMutableTreeNode treeNode;
    private LinkedList<String> listaRuoli;
    private LinkedList<Impiegato> listaImpiegati;

    public UnitaOrganizzativa(Object object){
        treeNode = new DefaultMutableTreeNode(object);
        listaRuoli = new LinkedList<>();
        listaImpiegati = new LinkedList<>();
    }

    public DefaultMutableTreeNode getTreeNode() {
        return treeNode;
    }

    public LinkedList<Impiegato> getListaImpiegati() {
        return listaImpiegati;
    }

    public LinkedList<String> getListaRuoli() {
        return listaRuoli;
    }

    public void aggiungiImpiegato(Impiegato impiegato){
        listaImpiegati.add(impiegato);
        aggiorna();
    }

    public void rimuoviImpiegato(Impiegato impiegato){
        listaImpiegati.remove(impiegato);
        aggiorna();
    }

    public void aggiungiRuolo(String ruolo){
        listaRuoli.add(ruolo);
        aggiorna();
    }

    public void rimuoviRuolo(String ruolo){
        listaRuoli.remove(ruolo);
        aggiorna();
    }

    @Override
    public void insert(MutableTreeNode child, int index) {
        treeNode.insert(child, index);
    }

    @Override
    public void remove(int index) {
        treeNode.remove(index);
    }

    @Override
    public void remove(MutableTreeNode node) {
        treeNode.remove(node);
    }

    @Override
    public void setUserObject(Object object) {
        treeNode.setUserObject(object);
    }

    @Override
    public String toString() {
        return (String) getUserObject();
    }

    public Object getUserObject(){
        return treeNode.getUserObject();
    }

    @Override
    public void removeFromParent() {
        treeNode.removeFromParent();
    }

    @Override
    public void setParent(MutableTreeNode newParent) {
        treeNode.setParent(newParent);
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return treeNode.getChildAt(childIndex);
    }

    @Override
    public int getChildCount() {
        return treeNode.getChildCount();
    }

    @Override
    public TreeNode getParent() {
        return treeNode.getParent();
    }

    @Override
    public int getIndex(TreeNode node) {
        return treeNode.getIndex(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return treeNode.getAllowsChildren();
    }

    @Override
    public boolean isLeaf() {
        return treeNode.isLeaf();
    }

    @Override
    public Enumeration children() {
        return treeNode.children();
    }
}
