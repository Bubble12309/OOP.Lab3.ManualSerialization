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
 * Fishburger extends Abstract burger. It contains fish patties and eggs additionally to buns.
 *
 * @version 1.0.0 26 May 2022
 * @author Alexander Kononov
 */
public class Fishburger extends AbstractBurger implements JSONable, GUIEditable {

    /**
     * The minimum amount of fish patties in burger
     */
    public static int MIN_FISH_PATTIES = 1;
    /**
     * The maximum amount of fish patties in burger
     */
    public static int MAX_FISH_PATTIES = 3;
    /**
     * The minimum amount of eggs in burger
     */
    public static int MIN_EGGS = 0;
    /**
     * The maximum amount of eggs in burger
     */
    public static int MAX_EGGS = 2;

    /**
     * Field, current number of fish patties in burger, must be limited with constants MIN_FISH_PATTIES and MAX_FISH_PATTIES. Default value - MIN_FISH_PATTIES;
     *
     * @see Fishburger#MIN_FISH_PATTIES
     * @see Fishburger#MAX_FISH_PATTIES
     */
    private int fFishPatties = MIN_FISH_PATTIES;
    /**
     * Field, current number of eggs in burger, must be limited with constants MIN_EGGS and MAX_EGGS. Default value - MIN_EGGS;
     *
     * @see Fishburger#MIN_EGGS
     * @see Fishburger#MAX_EGGS
     */
    private int fEggs = MIN_EGGS;

    /**
     * Returns the current number of fish patties in burger
     *
     * @return number of fish patties in burger
     */
    public int getFishPatties(){
        return this.fFishPatties;
    }

    /**
     * Sets the number of cheese in burger to be certain value.The value is limited with constants MIN_CHEESE and MAX_CHEESE
     *
     * @param n the number of fish patties to be set
     * @see Fishburger#MIN_FISH_PATTIES
     * @see Fishburger#MAX_FISH_PATTIES
     */
    public void setFishPatties(int n){
        if (n <= MIN_FISH_PATTIES) {
            fFishPatties = Math.max(0, MIN_FISH_PATTIES);
        } else {
            fFishPatties = Math.min(n, MAX_FISH_PATTIES);
        }
    }

    /**
     * Returns the current number of eggs in burger
     *
     * @return number of eggs in burger
     */
    public int getEggs(){
        return this.fEggs;
    }

    /**
     * Sets the number of eggs in burger to be certain value.The value is limited with constants MIN_EGGS and MAX_EGGS
     *
     * @param n the number of fish patties to be set
     * @see Fishburger#MIN_EGGS
     * @see Fishburger#MAX_EGGS
     */
    public void setEggs(int n){
        if (n <= MIN_EGGS) {
            fEggs = Math.max(0, MIN_EGGS);
        } else {
            fEggs = Math.min(n, MAX_EGGS);
        }
    }

    /**
     * Constructor with no parameters. All values are to be default
     */
    public Fishburger() {
        super();
    }

    /**
     * Hidden constructor that used by BurgersJSONFactory to deserialize object
     *
     * @param map HashMap that represents JSON content
     * @throws InvalidParameterException if some data appears to be invalid
     */
    protected Fishburger(HashMap<String, Vector<String>> map) throws InvalidParameterException {
        super(map);
        Vector<String> vector = map.get("fFishPatties");
        if ((vector == null) || (vector.size() <= 0)) throw new InvalidParameterException("no parameter in map");
        this.setFishPatties(Integer.parseInt(vector.get(0)));
        vector = map.get("fEggs");
        if ((vector == null) || (vector.size() <= 0)) throw new InvalidParameterException("no parameter in map");
        this.setEggs(Integer.parseInt(vector.get(0)));
    }

    @Override
    public StringBuilder getJSONContent() {
        StringBuilder record =  super.getJSONContent();
        record.append(',');
        record.append("\"fFishPatties\" : ").append(this.getFishPatties()).append(',');
        record.append("\"fEggs\" : ").append(this.getEggs());
        return record;
    }

    @Override
    public String toString() {
        return super.toString() + " fp = " + this.getFishPatties() + "; eg = " + this.getEggs() + ";";
    }

    @Override
    public JDialog getEditDialog(){
        JDialog frame = super.getEditDialog();
        //------------------------------------Fish patties------------------------------
        JPanel panelFishPatties = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        frame.add(panelFishPatties);

        JLabel labelFishPatties = new JLabel("Fish patties");
        labelFishPatties.setPreferredSize(new Dimension(200,20));
        JTextField textFieldFishPatties = new JTextField();
        textFieldFishPatties.setText(Integer.toString(this.getFishPatties()));
        textFieldFishPatties.setPreferredSize(new Dimension(60, 20));
        textFieldFishPatties.setEditable(false);
        JButton buttonAddFishPatties = new JButton("+");
        buttonAddFishPatties.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFishPatties(getFishPatties()+1);
                textFieldFishPatties.setText(Integer.toString(getFishPatties()));
            }
        });

        JButton buttonRemoveFishPatties = new JButton("-");
        buttonRemoveFishPatties.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFishPatties(getFishPatties()-1);
                textFieldFishPatties.setText(Integer.toString(getFishPatties()));
            }
        });

        panelFishPatties.add(labelFishPatties);
        panelFishPatties.add(textFieldFishPatties);
        panelFishPatties.add(buttonAddFishPatties);
        panelFishPatties.add(buttonRemoveFishPatties);
        //------------------------------------Eggs--------------------------------------
        JPanel panelEggs = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        frame.add(panelEggs);

        JLabel labelEggs = new JLabel("Eggs");
        labelEggs.setPreferredSize(new Dimension(200,20));
        JTextField textFieldEggs = new JTextField();
        textFieldEggs.setText(Integer.toString(this.getEggs()));
        textFieldEggs.setPreferredSize(new Dimension(60, 20));
        textFieldEggs.setEditable(false);
        JButton buttonAddEggs = new JButton("+");
        buttonAddEggs.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEggs(getEggs()+1);
                textFieldEggs.setText(Integer.toString(getEggs()));
            }
        });

        JButton buttonRemoveEggs = new JButton("-");
        buttonRemoveEggs.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEggs(getEggs()-1);
                textFieldEggs.setText(Integer.toString(getEggs()));
            }
        });

        panelEggs.add(labelEggs);
        panelEggs.add(textFieldEggs);
        panelEggs.add(buttonAddEggs);
        panelEggs.add(buttonRemoveEggs);
        //------------------------------------------------------------------------------
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
                poolToAdd.add(new Fishburger());
            }

            @Override
            public String toString() {
                return Fishburger.class.getSimpleName();
            }
        };
    }
}