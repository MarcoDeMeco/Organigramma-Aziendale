package View;

import Controller.Controller;
import Model.AlberoAziendale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PannelloAlbero extends JPanel implements ActionListener {

    private static final String AGGIUNGI = "aggiungi";
    private static final String RIMUOVI = "rimuovi";
    private static final String RUOLI = "ruoli";

    private JScrollPane scrollPane;
    private JPanel pannello;

    public PannelloAlbero() {
        super(new BorderLayout());

        JButton addButton = new JButton("Aggiungi");
        addButton.setActionCommand(AGGIUNGI);
        addButton.addActionListener(this);

        JButton removeButton = new JButton("Rimuovi");
        removeButton.setActionCommand(RIMUOVI);
        removeButton.addActionListener(this);

        JButton ruoliButton = new JButton("Gestisci ruoli");
        ruoliButton.setActionCommand(RUOLI);
        ruoliButton.addActionListener(this);

        pannello = new JPanel(new GridLayout(1, 0));
        scrollPane = new JScrollPane(Controller.getAlberoAziendale());
        pannello.add(scrollPane);
        pannello.setPreferredSize(new Dimension(300, 300));
        add(pannello, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(0, 3));
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(ruoliButton);
        add(panel, BorderLayout.SOUTH);
    }

    public void aggiorna() {
        remove(pannello);

        pannello = new JPanel(new GridLayout(1, 0));
        scrollPane = new JScrollPane(Controller.getAlberoAziendale());
        pannello.add(scrollPane);
        pannello.setPreferredSize(new Dimension(300, 300));
        add(pannello, BorderLayout.CENTER);

        // Il metodo repaint non funziona, uso questo workaround
        setVisible(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (AGGIUNGI.equals(command)) {
            String nomeNodo = JOptionPane.showInputDialog("Nome del nuovo nodo: ");
            if (nomeNodo != null && !nomeNodo.equals(""))
                Controller.aggiungiNodo(nomeNodo);

        } else if (RIMUOVI.equals(command)) {
            Controller.rimuoviNodoCorrente();

        } else if (RUOLI.equals(command)) {
            Controller.gestisciRuoli();
        }
    }
}