/*
* Package of burgers
 */
package burgers;

import customlayouts.BlockLayout;
import editwindows.GUIEditable;
import jsonio.JSONable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Vector;

/**
 * The root of Burger hierarchy. Every burger contains at least one top bun and one bottom bun. This finds implementation in the root class.
 *
 * @version 1.0.0 28 Apr 2022
 * @author Alexander Kononov
 */
public abstract class AbstractBurger implements JSONable, GUIEditable {
    /**
     * The minimum amount of top buns in burger
     */
    public static int MIN_TOP_BUNS = 1;

    /**
     * The maximum amount of top buns in burger
     */
    public static int MAX_TOP_BUNS = 3;

    /**
     * The minimum amount of bottom buns in burger
     */
    public static int MIN_BOTTOM_BUNS = 1;

    /**
     * The maximum amount of bottom buns in burger
     */
    public static int MAX_BOTTOM_BUNS = 3;

    /**
     * Field, current number of top buns in burger, must be limited with constants MIN_TOP_BUNS and MAX_TOP_BUNS. Default value - MIN_TOP_BUNS;
     *
     * @see AbstractBurger#MIN_TOP_BUNS
     * @see AbstractBurger#MAX_TOP_BUNS
     */
    private int fTopBuns = MIN_TOP_BUNS;

    /**
     * Field, current number of top bottom in burger, limited with constants MIN_BOTTOM_BUNS and MAX_BOTTOM_BUNS. Default value - MIN_BOTTOM_BUNS;
     *
     * @see AbstractBurger#MIN_BOTTOM_BUNS
     * @see AbstractBurger#MAX_BOTTOM_BUNS
     */
    private int fBottomBuns = MIN_BOTTOM_BUNS;

    /**
     * Returns current number of top buns in burger
     *
     * @return number of top buns in burger
     * @see AbstractBurger#fTopBuns
     */
    public int getTopBuns() {
        return fTopBuns;
    }

    /**
     * Sets number of top buns in burger, limited with constants MIN_TOP_BUNS and MAX_TOP_BUNS
     *
     * @param n number to be set
     * @see AbstractBurger#MIN_TOP_BUNS
     * @see AbstractBurger#MAX_TOP_BUNS
     */
    public void setTopBuns(int n) {
        if (n <= MIN_TOP_BUNS) {
            fTopBuns = Math.max(0, MIN_TOP_BUNS);
        } else {
            fTopBuns = Math.min(n, MAX_TOP_BUNS);
        }
    }

    /**
     * Returns current number of bottom buns in burger
     *
     * @return number of bottom buns in burger
     * @see AbstractBurger#fBottomBuns
     */
    public int getBottomBuns() {
        return fBottomBuns;
    }

    /**
     * Sets number of top buns in burger, limited with constants MIN_BOTTOM_BUNS and MAX_BOTTOM_BUNS
     *
     * @param n number to be set
     * @see AbstractBurger#MIN_BOTTOM_BUNS
     * @see AbstractBurger#MAX_BOTTOM_BUNS
     */
    public void setBottomBuns(int n) {
        if (n <= MIN_BOTTOM_BUNS) {
            fBottomBuns = Math.max(0, MIN_BOTTOM_BUNS);
        } else {
            fBottomBuns = Math.min(n, MAX_BOTTOM_BUNS);
        }
    }

    /**
     * Constructor with no arguments. Sets top buns and bottom buns numbers to be minimal
     *
     * @see AbstractBurger#MIN_TOP_BUNS
     * @see AbstractBurger#MAX_TOP_BUNS
     * @see AbstractBurger#MIN_BOTTOM_BUNS
     * @see AbstractBurger#MAX_BOTTOM_BUNS
     */
    protected AbstractBurger() {
    }

    /**
     * Constructor that sets top buns and bottom buns numbers to be certain values
     *
     * @param topBuns    number of top buns to be set, limited with constants MIN_TOP_BUNS and MAX_TOP_BUNS
     * @param bottomBuns number of bottom buns to be set, limited with constants MIN_BOTTOM_BUNS and MAX_BOTTOM_BUNS
     * @see AbstractBurger#MIN_TOP_BUNS
     * @see AbstractBurger#MAX_TOP_BUNS
     * @see AbstractBurger#MIN_BOTTOM_BUNS
     * @see AbstractBurger#MAX_BOTTOM_BUNS
     */
    protected AbstractBurger(int topBuns, int bottomBuns) {
        this.setTopBuns(topBuns);
        this.setBottomBuns(bottomBuns);
    }

    /**
     * Hidden constructor that used by BurgersJSONFactory to deserialize object
     *
     * @param map HashMap that represents JSON content
     * @throws InvalidParameterException if some data appears to be invalid
     */
    protected AbstractBurger(HashMap<String, Vector<String>> map) throws InvalidParameterException {
        if (map == null) throw new InvalidParameterException("map is occurred to be null");
        Vector<String> vector = map.get("fTopBuns");
        if ((vector == null) || (vector.size() <= 0)) throw new InvalidParameterException("no parameter in map");
        this.setTopBuns(Integer.parseInt(vector.get(0)));
        vector = map.get("fBottomBuns");
        if ((vector == null) || (vector.size() <= 0)) throw new InvalidParameterException("no parameter in map");
        this.setBottomBuns(Integer.parseInt(vector.get(0)));
    }

    public StringBuilder getJSONContent(){ //should be protected but the language does not allow
        StringBuilder record = new StringBuilder();
        record.append("\"fTopBuns\" : ").append(this.getTopBuns()).append(',');
        record.append("\"fBottomBuns\" : ").append(this.getBottomBuns());
        return record;
    }

    public String toJSON(){
        return  "{\"class\" : " + JSONable.shield(this.getClass().getName()) + "," + this.getJSONContent() + "}";
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": tb = " + this.getTopBuns() + "; bb = " + this.getBottomBuns() + ";";
    }

    @Override
    public JDialog getEditDialog() {
        JDialog frame = new JDialog((Frame) null,"instance of " + this.getClass().getSimpleName(), true);
        frame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        frame.setLayout(new BlockLayout(BlockLayout.MODE.LAST_STRICT, 0));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //-------------------------------Panel Top Buns---------------------------------------
        JPanel panelTopBuns = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        frame.add(panelTopBuns);

        JLabel labelTopBuns = new JLabel("Top Buns");
        labelTopBuns.setPreferredSize(new Dimension(200,20));
        JTextField textFieldTopBuns = new JTextField();
        textFieldTopBuns.setText(Integer.toString(this.getTopBuns()));
        textFieldTopBuns.setPreferredSize(new Dimension(60, 20));
        textFieldTopBuns.setEditable(false);
        JButton buttonAddTopBuns = new JButton("+");
        buttonAddTopBuns.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTopBuns(getTopBuns()+1);
                textFieldTopBuns.setText(Integer.toString(getTopBuns()));
            }
        });

        JButton buttonRemoveTopBuns = new JButton("-");
        buttonRemoveTopBuns.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTopBuns(getTopBuns()-1);
                textFieldTopBuns.setText(Integer.toString(getTopBuns()));
            }
        });

        panelTopBuns.add(labelTopBuns);
        panelTopBuns.add(textFieldTopBuns);
        panelTopBuns.add(buttonAddTopBuns);
        panelTopBuns.add(buttonRemoveTopBuns);
        //-------------------------------Panel Bottom Buns---------------------------------------
        JPanel panelBottomBuns = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        frame.add(panelBottomBuns);

        JLabel labelBottomBuns = new JLabel("Bottom Buns");
        labelBottomBuns.setPreferredSize(new Dimension(200,20));
        JTextField textFieldBottomBuns = new JTextField();
        textFieldBottomBuns.setText(Integer.toString(this.getBottomBuns()));
        textFieldBottomBuns.setPreferredSize(new Dimension(60, 20));
        textFieldBottomBuns.setEditable(false);
        JButton buttonAddBottomBuns = new JButton("+");
        buttonAddBottomBuns.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBottomBuns(getBottomBuns()+1);
                textFieldBottomBuns.setText(Integer.toString(getBottomBuns()));
            }
        });

        JButton buttonRemoveBottomBuns = new JButton("-");
        buttonRemoveBottomBuns.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBottomBuns(getBottomBuns()-1);
                textFieldBottomBuns.setText(Integer.toString(getBottomBuns()));
            }
        });
        panelBottomBuns.add(labelBottomBuns);
        panelBottomBuns.add(textFieldBottomBuns);
        panelBottomBuns.add(buttonAddBottomBuns);
        panelBottomBuns.add(buttonRemoveBottomBuns);
        //--------------------------------------------------------------------------------
        frame.pack();
        return frame;
    }
}