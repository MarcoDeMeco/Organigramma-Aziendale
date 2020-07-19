package View;

import Controller.Controller;
import Model.AlberoAziendale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinestraOrganigramma extends JFrame {

    // TODO chiedere di salvare prima di chiudere
    // implementa observer per ricevere aggiornamenti di cambiamento e dal controller notifica quando salvi

    public FinestraOrganigramma(Controller controller, AlberoAziendale alberoAziendale){
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Salva");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.salva();
            }
        });
        menu.add(item);
        item = new JMenuItem("Apri");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.apri();
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

        PannelloImpiegati pannelloImpiegati = new PannelloImpiegati(controller);

        AlberoView alberoView = new AlberoView(alberoAziendale, pannelloImpiegati);
        alberoAziendale.attach(alberoView);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2));
        mainPanel.add(new PannelloAlbero(controller, alberoView));
        mainPanel.add(pannelloImpiegati);

        setTitle("Organigramma Aziendale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setJMenuBar(menuBar);
        pack();
    }
}
