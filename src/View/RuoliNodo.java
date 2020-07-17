package View;

import Controller.Controller;
import Model.Impiegato;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Map;

public class RuoliNodo extends JDialog {

    private MyTableModel tableModel;
    private String ruoloSelezionato;
    private JButton removeButton;

    public RuoliNodo(String nodo, LinkedList<String> listaRuoli){
        JTextField info = new JTextField("Lista Ruoli del nodo: "+nodo);
        info.setEditable(false);
        info.setHorizontalAlignment(0);

        tableModel = new MyTableModel();
        tableModel.addColumn("Ruolo");
        Object[] data = new Object[1];
        for (String ruolo : listaRuoli) {
            data[0] = ruolo;
            tableModel.addRow(data);
        }

        JTable tabellaRuoli = new JTable(tableModel);
        tabellaRuoli.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                ruoloSelezionato = (String) tableModel.getValueAt(tabellaRuoli.getSelectedRow(), 0);
                removeButton.setEnabled(true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        JScrollPane scrollPane = new JScrollPane(tabellaRuoli);

        removeButton = new JButton("Rimuovi ruolo");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.rimuoviRuolo(ruoloSelezionato);
            }
        });
        removeButton.setEnabled(false);

        JTextField input = new JTextField();
        JButton aggiungiButton = new JButton("Aggiungi ruolo");
        aggiungiButton.setEnabled(false);
        input.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                aggiungiButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(input.getText().equals("")){
                    aggiungiButton.setEnabled(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.aggiungiRuolo(input.getText());
                input.setText("");
                aggiungiButton.setEnabled(false);
            }
        });

        JPanel aggiungiPanel = new JPanel(new GridLayout(0,2));
        aggiungiPanel.add(input);
        aggiungiPanel.add(aggiungiButton);

        JPanel container = new JPanel(new GridLayout(2, 0));
        container.add(removeButton);
        container.add(aggiungiPanel);

        JPanel pannello = new JPanel(new BorderLayout());
        pannello.add(info, BorderLayout.NORTH);
        pannello.add(scrollPane, BorderLayout.CENTER);
        pannello.add(container, BorderLayout.SOUTH);

        add(pannello);
        setTitle("Ruoli del nodo "+nodo);
        setSize(300,300);
        setLocationRelativeTo(null);
        setModal(true);
    }

    public void aggiornaRuoli(LinkedList<String> listaRuoli){
        removeButton.setEnabled(false);

        for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
            tableModel.removeRow(i);
        }

        Object[] data = new Object[1];
        for (String ruolo : listaRuoli) {
            data[0] = ruolo;
            tableModel.addRow(data);
        }
    }

    private class MyTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
