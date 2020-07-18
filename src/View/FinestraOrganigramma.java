package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinestraOrganigramma extends JFrame {

    // TODO chiedere di salvare prima di chiudere

    public FinestraOrganigramma(PannelloAlbero pannelloAlbero, PannelloImpiegati pannelloImpiegati){
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Salva");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.salva();
            }
        });
        menu.add(item);
        item = new JMenuItem("Apri");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.apri();
            }
        });
        menu.add(item);
        menuBar.add(menu);

        menu = new JMenu("Help");
        item = new JMenuItem("Tutorial");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("holaaa");
            }
        });
        menu.add(item);
        menuBar.add(menu);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2));
        mainPanel.add(pannelloAlbero);
        mainPanel.add(pannelloImpiegati);

        setTitle("Organigramma Aziendale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setJMenuBar(menuBar);
        pack();
    }
}
