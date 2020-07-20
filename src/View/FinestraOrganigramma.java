package View;

import Controller.Controller;
import Model.AlberoAziendale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FinestraOrganigramma extends JFrame {

    private boolean modificaEffettuata;

    public FinestraOrganigramma(Controller controller, AlberoAziendale alberoAziendale){
        JMenuBar menuBar = new JMenuBar();

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if(modificaEffettuata) {
                    int ris = JOptionPane.showConfirmDialog(null, "Vuoi uscire senza salvare?\n Tutte le modifiche non salvate andranno perse", "Attenzione", JOptionPane.YES_NO_OPTION);
                    if(ris == JOptionPane.YES_OPTION){
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
                return;
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

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

        AlberoView alberoView = new AlberoView(controller, alberoAziendale, pannelloImpiegati);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2));
        mainPanel.add(new PannelloAlbero(controller, alberoView));
        mainPanel.add(pannelloImpiegati);

        setTitle("Organigramma Aziendale");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setJMenuBar(menuBar);
        pack();
    }

    public void setModificaEffettuata(boolean modificaEffettuata) {
        if(modificaEffettuata && !this.modificaEffettuata){
            setTitle("*"+getTitle());
        }
        if(!modificaEffettuata && this.modificaEffettuata){
            String titolo = getTitle().substring(1);
            setTitle(titolo);
        }
        this.modificaEffettuata = modificaEffettuata;
    }
}
