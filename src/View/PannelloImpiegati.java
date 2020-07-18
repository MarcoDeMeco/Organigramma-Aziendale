package View;

import Controller.Controller;
import Model.Impiegato;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class PannelloImpiegati extends JPanel implements ActionListener {

    private static final String ADD_COMMAND = "aggiungi";
    private static final String REMOVE_COMMAND = "rimuovi";
    private static final String INFO_COMMAND = "info";

    private JTextField titolo;
    private JTable tabellaImpiegati;
    private MyTableModel tableModel;
    private Impiegato impiegatoSelezionato;
    private JButton removeButton;
    private JButton infoButton;

    public PannelloImpiegati() {
        super(new BorderLayout());

        JButton addButton = new JButton("Aggiungi");
        addButton.setActionCommand(ADD_COMMAND);
        addButton.addActionListener(this);

        removeButton = new JButton("Rimuovi");
        removeButton.setActionCommand(REMOVE_COMMAND);
        removeButton.addActionListener(this);
        removeButton.setEnabled(false);

        infoButton = new JButton("Info");
        infoButton.setActionCommand(INFO_COMMAND);
        infoButton.addActionListener(this);
        infoButton.setEnabled(false);

        tableModel = new MyTableModel();
        tableModel.addColumn("Nome e cognome");
        tableModel.addColumn("Ruolo");

        tabellaImpiegati = new JTable(tableModel);
        tabellaImpiegati.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                impiegatoSelezionato = (Impiegato) tableModel.getValueAt(tabellaImpiegati.getSelectedRow(), 0);
                removeButton.setEnabled(true);
                infoButton.setEnabled(true);
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
        JScrollPane scrollPane = new JScrollPane(tabellaImpiegati);
        add(scrollPane, BorderLayout.CENTER);

        titolo = new JTextField();
        titolo.setHorizontalAlignment(0);
        titolo.setBackground(Color.white);
        titolo.setEditable(false);
        add(titolo, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(0, 3));
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(infoButton);
        add(panel, BorderLayout.SOUTH);
    }


    public void aggiornaImpiegati(String nomeNodo, LinkedList<Impiegato> impiegati) {
        titolo.setText("Impiegati del nodo " + nomeNodo);

        removeButton.setEnabled(false);
        infoButton.setEnabled(false);

        for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
            tableModel.removeRow(i);
        }

        Object[] data = new Object[4];
        for (Impiegato i : impiegati) {
            data[0] = i;
            data[1] = i.getRuolo(nomeNodo);
            tableModel.addRow(data);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (ADD_COMMAND.equals(command)) {
            Controller.aggiungiImpiegato();
//            String nomeImpiegato = JOptionPane.showInputDialog("Nome del nuovo impiegato: ");
//            Controller.aggiungiImpiegato(nomeImpiegato);
        } else if (REMOVE_COMMAND.equals(command)) {
            Controller.rimuoviImpiegato(impiegatoSelezionato);
        } else if (INFO_COMMAND.equals(command)){
            new InfoImpiegato(impiegatoSelezionato).setVisible(true);
        }
    }

    private class MyTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}