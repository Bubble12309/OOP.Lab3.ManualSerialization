/*
 * Package of burgers
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
 * Hamburger extends Abstract burger. It contains beef pickles, dill pickles and onions additionally to abstract burgers components.
 *
 * @version 1.0.0 28 Apr 2022
 * @author Alexander Kononov
 */
public class Hamburger extends AbstractBurger implements JSONable, GUIEditable {
    /**
     * The minimum amount of beef patties in burger
     */
    public static int MIN_BEEF_PATTIES = 1;
    /**
     * The maximum amount of beef patties in burger
     */
    public static int MAX_BEEF_PATTIES = 4;
    /**
     * The minimum amount of dill pickles in burger
     */
    public static int MIN_DILL_PICKLES = 0;
    /**
     * The maximum amount of dill pickles in burger
     */
    public static int MAX_DILL_PICKLES = 12;
    /**
     * The minimum amount of onions in burger
     */
    public static int MIN_ONIONS = 0;
    /**
     * The maximum amount of onions in burger
     */
    public static int MAX_ONIONS = 4;

    /**
     * Field, current number of beef patties in burger, must be limited with constants MIN_BEEF_PATTIES and MAX_BEEF_PATTIES. Default value - MIN_BEEF_PATTIES;
     *
     * @see Hamburger#MIN_BEEF_PATTIES
     * @see Hamburger#MAX_BEEF_PATTIES
     */
    private int fBeefPatties = MIN_BEEF_PATTIES;

    /**
     * Field, current number of dill pickles in burger, must be limited with constants MIN_DILL_PICKLES and MAX_DILL_PICKLES. Default value - MIN_DILL_PICKLES;
     *
     * @see Hamburger#MIN_DILL_PICKLES
     * @see Hamburger#MAX_DILL_PICKLES
     */
    private int fDillPickles = MIN_DILL_PICKLES;

    /**
     * Field, current number of onions in burger, must be limited with constants MIN_ONIONS and MAX_ONIONS. Default value - MIN_ONIONS;
     *
     * @see Hamburger#MIN_ONIONS
     * @see Hamburger#MAX_ONIONS
     */
    private int fOnions = MIN_ONIONS;

    /**
     * Field, status of whether ketchup be added or not. Default value - true
     */
    private boolean fWithKetchup = true;

    /**
     * Field, status of whether ketchup be added or not. Default value - false
     */
    private boolean fWithMustard = false;

    /**
     * Returns the current number of beef patties in burger
     *
     * @return number of beef patties in burger
     */
    public int getBeefPatties() {
        return this.fBeefPatties;
    }

    /**
     * Sets the number of beef patties in burger to be certain value.The value is limited with constants MIN_BEEF_PATTIES and MAX_BEEF_PATTIES
     *
     * @param n the number of beef patties to be set
     * @see Hamburger#MIN_BEEF_PATTIES
     * @see Hamburger#MAX_BEEF_PATTIES
     */
    public void setBeefPatties(int n) {
        if (n <= MIN_BEEF_PATTIES) {
            fBeefPatties = Math.max(0, MIN_BEEF_PATTIES);
        } else {
            fBeefPatties = Math.min(n, MAX_BEEF_PATTIES);
        }
    }

    /**
     * Returns the current number of dill pickles in burger
     *
     * @return number of dill pickles in burger
     */
    public int getDillPickles() {
        return this.fDillPickles;
    }

    /**
     * Sets the number of dill pickles in burger to be certain value.The value is limited with constants MIN_DILL_PICKLES and MAX_DILL_PICKLES
     *
     * @param n the number of dill pickles to be set
     * @see Hamburger#MIN_DILL_PICKLES
     * @see Hamburger#MAX_DILL_PICKLES
     */
    public void setDillPickles(int n) {
        if (n <= MIN_DILL_PICKLES) {
            fDillPickles = Math.max(0, MIN_DILL_PICKLES);
        } else {
            fDillPickles = Math.min(n, MAX_DILL_PICKLES);
        }
    }

    /**
     * Returns the current number of onions in burger
     *
     * @return number of onions in burger
     */
    public int getOnions() {
        return this.fOnions;
    }

    /**
     * Sets the number of onion in burger to be certain value.The value is limited with constants MIN_ONIONS and MAX_ONIONS
     *
     * @param n the number of onions to be set
     * @see Hamburger#MIN_ONIONS
     * @see Hamburger#MAX_ONIONS
     */
    public void setOnions(int n) {
        if (n <= MIN_ONIONS) {
            fOnions = Math.max(0, MIN_ONIONS);
        } else {
            fOnions = Math.min(n, MAX_ONIONS);
        }
    }

    /**
     * Returns if ketchup to be added in burger
     *
     * @return if ketchup to be added in burger
     */
    public boolean getKetchup() {
        return this.fWithKetchup;
    }

    /**
     * Set if ketchup to be added in burger
     *
     * @param status if ketchup to be added in burger
     */
    public void setKetchup(boolean status) {
        this.fWithKetchup = status;
    }

    /**
     * Returns if mustard to be added in burger
     *
     * @return if mustard to be added in burger
     */
    public boolean getMustard() {
        return this.fWithMustard;
    }

    /**
     * Set if mustard to be added in burger
     *
     * @param status if mustard to be added in burger
     */
    public void setMustard(boolean status) {
        this.fWithMustard = status;
    }


    /**
     * Constructor with no parameters. All values are to be default
     */
    public Hamburger() {
        super();
    }

    protected Hamburger(HashMap<String, Vector<String>> map) throws InvalidParameterException {
        super(map);
        Vector<String> vector = map.get("fBeefPatties");
        if ((vector == null) || (vector.size() <= 0)) throw new InvalidParameterException("no parameter in map");
        this.setBeefPatties(Integer.parseInt(vector.get(0)));
        vector = map.get("fDillPickles");
        if ((vector == null) || (vector.size() <= 0)) throw new InvalidParameterException("no parameter in map");
        this.setDillPickles(Integer.parseInt(vector.get(0)));
        vector = map.get("fOnions");
        if ((vector == null) || (vector.size() <= 0)) throw new InvalidParameterException("no parameter in map");
        this.setOnions(Integer.parseInt(vector.get(0)));
        vector = map.get("fWithKetchup");
        if ((vector == null) || (vector.size() <= 0)) throw new InvalidParameterException("no parameter in map");
        this.setKetchup(Boolean.parseBoolean(vector.get(0)));
        vector = map.get("fWithMustard");
        if ((vector == null) || (vector.size() <= 0)) throw new InvalidParameterException("no parameter in map");
        this.setMustard(Boolean.parseBoolean(vector.get(0)));
    }

    public StringBuilder getJSONContent() {
        StringBuilder record = super.getJSONContent();
        record.append(',');
        record.append("\"fBeefPatties\" : ").append(this.getBeefPatties()).append(',');
        record.append("\"fDillPickles\" : ").append(this.getDillPickles()).append(',');
        record.append("\"fOnions\" : ").append(this.getOnions()).append(',');
        record.append("\"fWithKetchup\" : ").append(this.getKetchup()).append(',');
        record.append("\"fWithMustard\" : ").append(this.getMustard());
        return record;
    }

    @Override
    public String toString() {
        return super.toString() + " bp = " + this.getBeefPatties() + "; dp = " + this.getDillPickles() + "; on = " + this.getOnions() +
                "; ke = " + this.getKetchup() + "; mu = " + this.getMustard() + ";";
    }

    @Override
    public JDialog getEditDialog() {
        JDialog frame = super.getEditDialog();
        //--------------------------Panel beef patties---------------------------
        JPanel panelBeefPatties = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        frame.add(panelBeefPatties);

        JLabel labelBeefPatties = new JLabel("Beef patties");
        labelBeefPatties.setPreferredSize(new Dimension(200, 20));
        JTextField textFieldBeefPatties = new JTextField();
        textFieldBeefPatties.setText(Integer.toString(this.getBeefPatties()));
        textFieldBeefPatties.setPreferredSize(new Dimension(60, 20));
        textFieldBeefPatties.setEditable(false);
        JButton buttonAddBeefPatties = new JButton("+");
        buttonAddBeefPatties.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBeefPatties(getBeefPatties() + 1);
                textFieldBeefPatties.setText(Integer.toString(getBeefPatties()));
            }
        });

        JButton buttonRemoveBeefPatties = new JButton("-");
        buttonRemoveBeefPatties.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBeefPatties(getBeefPatties() - 1);
                textFieldBeefPatties.setText(Integer.toString(getBeefPatties()));
            }
        });

        panelBeefPatties.add(labelBeefPatties);
        panelBeefPatties.add(textFieldBeefPatties);
        panelBeefPatties.add(buttonAddBeefPatties);
        panelBeefPatties.add(buttonRemoveBeefPatties);
        //--------------------------Panel dill pickles---------------------------
        JPanel panelDillPickles = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        frame.add(panelDillPickles);

        JLabel labelDillPickles = new JLabel("Dill pickles");
        labelDillPickles.setPreferredSize(new Dimension(200, 20));
        JTextField textFieldDillPickles = new JTextField();
        textFieldDillPickles.setText(Integer.toString(this.getDillPickles()));
        textFieldDillPickles.setPreferredSize(new Dimension(60, 20));
        textFieldDillPickles.setEditable(false);
        JButton buttonAddDillPickles = new JButton("+");
        buttonAddDillPickles.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDillPickles(getDillPickles() + 1);
                textFieldDillPickles.setText(Integer.toString(getDillPickles()));
            }
        });

        JButton buttonRemoveDillPickles = new JButton("-");
        buttonRemoveDillPickles.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDillPickles(getDillPickles() - 1);
                textFieldDillPickles.setText(Integer.toString(getDillPickles()));
            }
        });

        panelDillPickles.add(labelDillPickles);
        panelDillPickles.add(textFieldDillPickles);
        panelDillPickles.add(buttonAddDillPickles);
        panelDillPickles.add(buttonRemoveDillPickles);
        //--------------------------Panel onions---------------------------------
        JPanel panelOnions = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        frame.add(panelOnions);

        JLabel labelOnions = new JLabel("Onions");
        labelOnions.setPreferredSize(new Dimension(200, 20));
        JTextField textFieldOnions = new JTextField();
        textFieldOnions.setText(Integer.toString(this.getOnions()));
        textFieldOnions.setPreferredSize(new Dimension(60, 20));
        textFieldOnions.setEditable(false);
        JButton buttonAddOnions = new JButton("+");
        buttonAddOnions.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setOnions(getOnions() + 1);
                textFieldOnions.setText(Integer.toString(getOnions()));
            }
        });

        JButton buttonRemoveOnions = new JButton("-");
        buttonRemoveOnions.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setOnions(getOnions() - 1);
                textFieldOnions.setText(Integer.toString(getOnions()));
            }
        });

        panelOnions.add(labelOnions);
        panelOnions.add(textFieldOnions);
        panelOnions.add(buttonAddOnions);
        panelOnions.add(buttonRemoveOnions);
        //--------------------------Panel ketchup--------------------------------
        JPanel panelKetchup = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        frame.add(panelKetchup);

        JLabel labelKetchup = new JLabel("Ketchup");
        labelKetchup.setPreferredSize(new Dimension(200, 20));

        JRadioButton radioButtonKetchup = new JRadioButton();
        radioButtonKetchup.setSelected(getKetchup());
        radioButtonKetchup.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setKetchup(!getKetchup());
                radioButtonKetchup.setSelected(getKetchup());
            }
        });

        panelKetchup.add(labelKetchup);
        panelKetchup.add(radioButtonKetchup);
        //--------------------------Panel mustard--------------------------------
        JPanel panelMustard = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        frame.add(panelMustard);

        JLabel labelMustard = new JLabel("Mustard");
        labelMustard.setPreferredSize(new Dimension(200, 20));

        JRadioButton radioButtonMustard = new JRadioButton();
        radioButtonMustard.setSelected(getMustard());
        radioButtonMustard.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMustard(!getMustard());
                radioButtonMustard.setSelected(getMustard());
            }
        });

        panelMustard.add(labelMustard);
        panelMustard.add(radioButtonMustard);
        //--------------------------------------------------------------------------
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
                poolToAdd.add(new Hamburger());
            }

            @Override
            public String toString() {
                return Hamburger.class.getSimpleName();
            }
        };
    }
}