package View;

import Model.Impiegato;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class InfoImpiegato extends JDialog {

    public InfoImpiegato(Impiegato impiegato) {
        HashMap<String, String> ruoloByNodo = impiegato.getRuoloByNodo();

        MyTableModel tableModel = new MyTableModel();
        tableModel.addColumn("Nodo");
        tableModel.addColumn("Ruolo");

        JTable tabellaLavori = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabellaLavori);

        String s = "Nome impiegato: " + impiegato.getNome();

        JTextField info = new JTextField(s);
        info.setEditable(false);
        info.setHorizontalAlignment(0);

        Object[] data = new Object[2];
        for (Map.Entry<String, String> e : ruoloByNodo.entrySet()) {
            data[0] = e.getKey();
            data[1] = e.getValue();
            tableModel.addRow(data);
        }

        JPanel pannello = new JPanel(new BorderLayout());
        pannello.add(info, BorderLayout.NORTH);
        pannello.add(scrollPane, BorderLayout.CENTER);

        add(pannello);
        setTitle("Informazioni impiegato");
        setSize(300,300);
        setLocationRelativeTo(null);
        setModal(true);
        setVisible(true);
    }

    private class MyTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
