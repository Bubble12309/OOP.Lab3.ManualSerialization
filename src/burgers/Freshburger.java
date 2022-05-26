/*
package of burgers
 */
package burgers;

import editwindows.GUIEditable;
import jsonio.JSONable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Vector;

/**
 * Freshburger extends Fishburger. It contains lettuce additionally to buns.
 *
 * @version 1.0.0 26 May 2022
 * @author Alexander Kononov
 */
public class Freshburger extends Fishburger implements JSONable, GUIEditable {
    /**
     * The minimum amount of lettuce in burger
     */
    public static int MIN_LETTUCE = 1;
    /**
     * The maximum amount of lettuce in burger
     */
    public static int MAX_LETTUCE = 2;
    /**
     * Field, current number of lettuce in burger, must be limited with constants MIN_LETTUCE and MAX_LETTUCE. Default value - MIN_LETTUCE;
     *
     * @see Freshburger#MIN_LETTUCE
     * @see Freshburger#MAX_LETTUCE
     */
    private int fLettuce = MIN_LETTUCE;

    /**
     * Returns the current number lettuce in burger
     *
     * @return number of lettuce in burger
     */
    public int getLettuce() {
        return this.fLettuce;
    }

    /**
     * Sets the number of lettuce in burger to be certain value.The value is limited with constants MIN_LETTUCE and MAX_LETTUCE
     *
     * @param n the number of lettuce to be set
     * @see Freshburger#MIN_LETTUCE
     * @see Freshburger#MAX_LETTUCE
     */
    public void setLettuce(int n) {
        if (n <= MIN_LETTUCE) {
            fLettuce = Math.max(0, MIN_LETTUCE);
        } else {
            fLettuce = Math.min(n, MAX_LETTUCE);
        }
    }

    /**
     * Constructor with no parameters. All values are to be default
     */
    public Freshburger() {
        super();
    }

    /**
     * Hidden constructor that used by BurgersJSONFactory to deserialize object
     *
     * @param map HashMap that represents JSON content
     * @throws InvalidParameterException if some data appears to be invalid
     */
    protected Freshburger(HashMap<String, Vector<String>> map) throws InvalidParameterException {
        super(map);
        Vector<String> vector = map.get("fLettuce");
        if ((vector == null) || (vector.size() <= 0)) throw new InvalidParameterException("no parameter in map");
        this.setLettuce(Integer.parseInt(vector.get(0)));
    }

    @Override
    public String toString() {
        return super.toString() + " le = " + this.getLettuce() + ";";
    }

    @Override
    public StringBuilder getJSONContent() {
        StringBuilder record = super.getJSONContent();
        record.append(',');
        record.append("\"fLettuce\" : ").append(this.getLettuce());
        return record;
    }

    @Override
    public JDialog getEditDialog() {
        JDialog frame = super.getEditDialog();
        //--------------------------------Lettuce-------------------------------------------
        JPanel panelLettuce = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        frame.add(panelLettuce);

        JLabel labelLettuce = new JLabel("Lettuce");
        labelLettuce.setPreferredSize(new Dimension(200, 20));
        JTextField textFieldLettuce = new JTextField();
        textFieldLettuce.setText(Integer.toString(this.getLettuce()));
        textFieldLettuce.setPreferredSize(new Dimension(60, 20));
        textFieldLettuce.setEditable(false);
        JButton buttonAddLettuce = new JButton("+");
        buttonAddLettuce.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLettuce(getLettuce() + 1);
                textFieldLettuce.setText(Integer.toString(getLettuce()));
            }
        });

        JButton buttonRemoveLettuce = new JButton("-");
        buttonRemoveLettuce.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLettuce(getLettuce() - 1);
                textFieldLettuce.setText(Integer.toString(getLettuce()));
            }
        });

        panelLettuce.add(labelLettuce);
        panelLettuce.add(textFieldLettuce);
        panelLettuce.add(buttonAddLettuce);
        panelLettuce.add(buttonRemoveLettuce);
        //----------------------------------------------------------------------------------
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
                poolToAdd.add(new Freshburger());
            }

            @Override
            public String toString() {
                return Freshburger.class.getSimpleName();
            }
        };
    }
}