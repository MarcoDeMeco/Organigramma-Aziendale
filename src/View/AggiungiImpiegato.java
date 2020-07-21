package View;

import Controller.Controller;
import Model.UnitaOrganizzativa;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class AggiungiImpiegato extends JDialog {
    public AggiungiImpiegato(Controller controller, UnitaOrganizzativa unitaSelezionata){
        String nomeNodo = unitaSelezionata.toString();
        LinkedList<String> listaRuoli = unitaSelezionata.getListaRuoli();

        JButton aggiungiButton = new JButton("Aggiugni impiegato");
        aggiungiButton.setEnabled(false);

        JTextField inputNome = new JTextField();

        inputNome.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                aggiungiButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(inputNome.getText().equals("")){
                    aggiungiButton.setEnabled(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        JComboBox<String> inputRuolo = new JComboBox<>();
        for(String ruolo : listaRuoli){
            inputRuolo.addItem(ruolo);
        }

        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.aggiungiImpiegato(inputNome.getText(), (String) inputRuolo.getSelectedItem());
                dispose();
            }
        });

        JTextField scegliNome = new JTextField("Nome e cognome: ");
        scegliNome.setEditable(false);
        JTextField scegliRuolo = new JTextField("Ruolo: ");
        scegliRuolo.setEditable(false);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(scegliNome);
        inputPanel.add(inputNome);
        inputPanel.add(scegliRuolo);
        inputPanel.add(inputRuolo);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(aggiungiButton, BorderLayout.SOUTH);

        add(mainPanel);
        setTitle("Aggiungi impiegato in: "+nomeNodo);
        setSize(300, 120);
        setLocationRelativeTo(null);
        setModal(true);
        setVisible(true);
    }
}
