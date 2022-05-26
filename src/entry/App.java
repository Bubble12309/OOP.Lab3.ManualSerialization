package entry;

import burgers.*;

import javax.swing.*;
import java.util.Vector;


public class App {

    public static void main(String[] args) {
        var burgersPool = new Vector<AbstractBurger>();
        var creatorsList = new Vector<AbstractAction>(); //this is extension point to plugins
        creatorsList.add(Hamburger.getCreateAction(burgersPool));
        creatorsList.add(Cheeseburger.getCreateAction(burgersPool));
        creatorsList.add(Fishburger.getCreateAction(burgersPool));
        creatorsList.add(Freshburger.getCreateAction(burgersPool));
        FrmMain frmMain = new FrmMain(burgersPool, creatorsList);
        frmMain.setVisible(true);
    }
}