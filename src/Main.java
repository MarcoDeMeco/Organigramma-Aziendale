import Controller.Controller;
import Model.AlberoAziendale;
import View.PannelloAlbero;
import View.PannelloImpiegati;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // TODO thread safe
        Controller controller = new Controller();
        controller.createAndShowGUI();
    }
}
