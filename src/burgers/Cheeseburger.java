/*
 * Package of burgers
 */
package burgers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Vector;

/**
 * Cheeseburger extends Hamburger. It contains cheese additionally to abstract burgers components.
 *
 * @version 1.0.0 28 Apr 2022
 * @author Alexander Kononov
 */
public class Cheeseburger extends Hamburger {
    /**
     * The minimum amount of cheese in burger
     */
    public static int MIN_CHEESE = 1;
    /**
     * The maximum amount of cheese in burger
     */
    public static int MAX_CHEESE = 2;

    /**
     * Field, current number of cheese in burger, must be limited with constants MIN_CHEESE and MAX_CHEESE. Default value - MIN_CHEESE;
     *
     * @see Cheeseburger#MIN_CHEESE
     * @see Cheeseburger#MAX_CHEESE
     */
    private int fCheese = MIN_CHEESE;

    /**
     * Returns the current number of cheese in burger
     *
     * @return number of cheese in burger
     */
    public int getCheese() {
        return this.fCheese;
    }

    /**
     * Sets the number of cheese in burger to be certain value.The value is limited with constants MIN_CHEESE and MAX_CHEESE
     *
     * @param n the number of beef patties to be set
     * @see Cheeseburger#MIN_CHEESE
     * @see Cheeseburger#MAX_CHEESE
     */
    public void setCheese(int n) {
        if (n <= MIN_CHEESE) {
            fCheese = Math.max(0, MIN_CHEESE);
        } else {
            fCheese = Math.min(n, MAX_CHEESE);
        }
    }

    /**
     * Constructor with no parameters. All values are to be default
     */
    public Cheeseburger() {
        super();
    }

    /**
     * Hidden constructor that used by BurgersJSONFactory to deserialize object
     *
     * @param map HashMap that represents JSON content
     * @throws InvalidParameterException if some data appears to be invalid
     */
    protected Cheeseburger(HashMap<String, Vector<String>> map) throws InvalidParameterException {
        super(map);
        Vector<String> vector = map.get("fCheese");
        if ((vector == null) || (vector.size() <= 0)) throw new InvalidParameterException("no parameter in map");
        this.setCheese(Integer.parseInt(vector.get(0)));
    }

    public StringBuilder getJSONContent() {
        StringBuilder record = super.getJSONContent();
        record.append(',');
        record.append("\"fCheese\" : ").append(this.getCheese());
        return record;
    }

    @Override
    public String toString() {
        return super.toString() + " ch = " + this.getCheese() + ";";
    }

    @Override
    public JDialog getEditDialog() {
        JDialog frame = super.getEditDialog();
        //-----------------------Panel Cheese------------------------
        JPanel panelCheese = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        frame.add(panelCheese);

        JLabel labelCheese = new JLabel("Cheese");
        labelCheese.setPreferredSize(new Dimension(200, 20));
        JTextField textFieldCheese = new JTextField();
        textFieldCheese.setText(Integer.toString(this.getCheese()));
        textFieldCheese.setPreferredSize(new Dimension(60, 20));
        textFieldCheese.setEditable(false);
        JButton buttonAddCheese = new JButton("+");
        buttonAddCheese.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCheese(getCheese() + 1);
                textFieldCheese.setText(Integer.toString(getCheese()));
            }
        });

        JButton buttonRemoveCheese = new JButton("-");
        buttonRemoveCheese.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCheese(getCheese() - 1);
                textFieldCheese.setText(Integer.toString(getCheese()));
            }
        });

        panelCheese.add(labelCheese);
        panelCheese.add(textFieldCheese);
        panelCheese.add(buttonAddCheese);
        panelCheese.add(buttonRemoveCheese);
        //-------------------------------------------------------------------
        frame.pack();
        return frame;
    }

    /**
     * Provides action to create objects with GUI
     *
     * @return action to create objects with GUI
     */
    public static AbstractAction getCreateAction(Vector<AbstractBurger> poolToAdd) {
        if (poolToAdd == null) return null;
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                poolToAdd.add(new Cheeseburger());
            }

            @Override
            public String toString() {
                return Cheeseburger.class.getSimpleName();
            }
        };
    }
}