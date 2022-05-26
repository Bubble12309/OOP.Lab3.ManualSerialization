package entry;

import burgers.*;
import customlayouts.*;
import jsonio.JSONable;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.Vector;

public final class FrmMain extends JFrame {

    //-------------------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------Initial values----------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------------------------------
    Dimension stdFrmMainPreferredSize = new Dimension(950, 870);
    Dimension stdFrmMainMinimumSize = new Dimension(230, 230);

    Dimension stdPanelCaptionSize = new Dimension(0, 40); // this must use borderLayout
    Color stdPanelCaptionBackground = new Color(236, 230, 234);
    Font stdCaptionFont = new Font(Font.SERIF, Font.BOLD, 20);

    Color stdPanelInstrumentsBackground = new Color(186, 188, 194);

    Dimension stdPanelInstrumentAddSize = new Dimension(160, 95);

    Dimension stdPanelInstrumentRemoveSize = new Dimension(160, 95);

    Dimension stdPanelEditSize = new Dimension(180, 95);

    Dimension stdPanelInstrumentLoadSize = new Dimension(140, 95);

    Dimension stdPanelInstrumentSaveSize = new Dimension(140, 95);

    Color stdInstrumentBackground = new Color(238, 243, 242);
    //-------------------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------Form components---------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------------------------------
    private final BlockLayout mainLayout;

    private final JPanel panelInstrumentsCaption;
    private final JLabel labelInstrumentsCaption;

    private final JPanel panelInstruments;
    private final JPanel panelInstrumentLoad;
    private final JPanel panelInstrumentEdit;
    private final JPanel panelInstrumentSave;
    private final JPanel panelInstrumentAddInstance;
    private final JPanel panelInstrumentRemoveInstance;
    private JList<AbstractBurger> listBurgers;

    //---------------------------------------------
    //--------Start of the only constructor--------
    //---------------------------------------------
    public FrmMain(Vector<AbstractBurger> burgersPool, Vector<AbstractAction> listCreators) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //---------------------------------------------------------------------------------------------------------------------
        //------------------------------------------------Setting sizes of a form----------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------
        this.setMinimumSize(stdFrmMainMinimumSize);
        this.setPreferredSize(stdFrmMainPreferredSize);
        this.setSize(stdFrmMainPreferredSize);
        //---------------------------------------------------------------------------------------------------------------------
        //------------------------------------------------Setting a form layout------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------
        this.mainLayout = new BlockLayout(BlockLayout.MODE.LAST_STRICT);
        this.getContentPane().setLayout(mainLayout);
        //---------------------------------------------------------------------------------------------------------------------
        //-------------------------------------------------Setting a form title------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------
        this.setTitle("Lab3. Serialization and deserialization");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("res/images/title-16.png"));
        //---------------------------------------------------------------------------------------------------------------------
        //-----------------------------------------------Instruments panel caption---------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------
        panelInstrumentsCaption = new JPanel();
        panelInstrumentsCaption.setLayout(new BorderLayout());
        panelInstrumentsCaption.setPreferredSize(stdPanelCaptionSize);
        panelInstrumentsCaption.setBackground(stdPanelCaptionBackground);
        this.getContentPane().add(panelInstrumentsCaption);

        labelInstrumentsCaption = new JLabel("Instruments for this work", SwingConstants.CENTER);
        labelInstrumentsCaption.setFont(stdCaptionFont);
        panelInstrumentsCaption.add(labelInstrumentsCaption, BorderLayout.CENTER);
        //---------------------------------------------------------------------------------------------------------------------
        //-----------------------------------------------Instruments panel ----------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------
        panelInstruments = new JPanel();
        panelInstruments.setLayout(new InlineBlockLayout(6));
        panelInstruments.setBackground(stdPanelInstrumentsBackground);
        panelInstruments.setBorder(new BasicBorders.FieldBorder(Color.gray, Color.darkGray, Color.gray, Color.lightGray));
        this.getContentPane().add(panelInstruments);

        //---------------------------------------------
        //------------Instrument add instance----------
        //---------------------------------------------
        panelInstrumentAddInstance = new JPanel();
        panelInstrumentAddInstance.setLayout(new BoxLayout(panelInstrumentAddInstance, BoxLayout.Y_AXIS));
        panelInstrumentAddInstance.setPreferredSize(stdPanelInstrumentAddSize);
        panelInstrumentAddInstance.setBackground(stdInstrumentBackground);
        panelInstrumentAddInstance.setBorder(new BasicBorders.FieldBorder(Color.gray, Color.darkGray, Color.gray, Color.lightGray));
        panelInstruments.add(panelInstrumentAddInstance);

        JLabel labelAddInstance = new JLabel("Add element");
        labelAddInstance.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInstrumentAddInstance.add(labelAddInstance);

        JButton buttonAddInstance = new JButton("...");
        buttonAddInstance.addActionListener(listCreators.get(0));
        buttonAddInstance.addActionListener(e -> listBurgers.setListData(burgersPool));
        JComboBox<AbstractAction> comboBoxAddInstance = new JComboBox<>(listCreators);
        comboBoxAddInstance.addItemListener(e -> {
            switch (e.getStateChange()) {
                case ItemEvent.SELECTED -> buttonAddInstance.addActionListener((ActionListener) e.getItem());
                case ItemEvent.DESELECTED -> buttonAddInstance.removeActionListener((ActionListener) e.getItem());
            }
        });
        panelInstrumentAddInstance.add(comboBoxAddInstance);
        buttonAddInstance.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInstrumentAddInstance.add(buttonAddInstance);
        //---------------------------------------------
        //-----------Instrument remove instance--------
        //---------------------------------------------
        panelInstrumentRemoveInstance = new JPanel();
        panelInstrumentRemoveInstance.setLayout(new BoxLayout(panelInstrumentRemoveInstance, BoxLayout.Y_AXIS));
        panelInstrumentRemoveInstance.setPreferredSize(stdPanelInstrumentRemoveSize);
        panelInstrumentRemoveInstance.setBackground(stdInstrumentBackground);
        panelInstrumentRemoveInstance.setBorder(new BasicBorders.FieldBorder(Color.gray, Color.darkGray, Color.gray, Color.lightGray));
        panelInstruments.add(panelInstrumentRemoveInstance);

        JLabel labelRemoveInstance = new JLabel("Delete element");
        labelRemoveInstance.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInstrumentRemoveInstance.add(labelRemoveInstance);

        JPanel panelRemoveInstanceRubbish = new JPanel();
        panelInstrumentRemoveInstance.add(panelRemoveInstanceRubbish);
        panelRemoveInstanceRubbish.setBackground(panelRemoveInstanceRubbish.getParent().getBackground());

        JButton buttonRemoveItem = new JButton("...");
        buttonRemoveItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonRemoveItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!listBurgers.isSelectionEmpty()) {
                    burgersPool.remove(listBurgers.getSelectedIndex());
                    listBurgers.setListData(burgersPool);
                }
            }
        });
        panelInstrumentRemoveInstance.add(buttonRemoveItem);

        //---------------------------------------------
        //-----------Instrument edit instance----------
        //---------------------------------------------
        panelInstrumentEdit = new JPanel();
        panelInstrumentEdit.setLayout(new BoxLayout(panelInstrumentEdit, BoxLayout.Y_AXIS));
        panelInstrumentEdit.setPreferredSize(stdPanelEditSize);
        panelInstrumentEdit.setBackground(stdInstrumentBackground);
        panelInstrumentEdit.setBorder(new BasicBorders.FieldBorder(Color.gray, Color.darkGray, Color.gray, Color.lightGray));
        panelInstruments.add(panelInstrumentEdit);

        JLabel labelEditInstance = new JLabel("Edit element");
        labelEditInstance.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInstrumentEdit.add(labelEditInstance);

        JPanel panelEditRubbish = new JPanel();
        panelInstrumentEdit.add(panelEditRubbish);
        panelEditRubbish.setBackground(panelEditRubbish.getParent().getBackground());

        JButton buttonEdit = new JButton("...");
        buttonEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonEdit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!listBurgers.isSelectionEmpty()) {
                    burgersPool.get(listBurgers.getSelectedIndex()).getEditDialog().setVisible(true);
                    listBurgers.setListData(burgersPool);
                }
            }
        });
        panelInstrumentEdit.add(buttonEdit);

        //---------------------------------------------
        //-----------Instrument load instance----------
        //---------------------------------------------
        panelInstrumentLoad = new JPanel();
        panelInstrumentLoad.setLayout(new BoxLayout(panelInstrumentLoad, BoxLayout.Y_AXIS));
        panelInstrumentLoad.setPreferredSize(stdPanelInstrumentLoadSize);
        panelInstrumentLoad.setBackground(stdInstrumentBackground);
        panelInstrumentLoad.setBorder(new BasicBorders.FieldBorder(Color.gray, Color.darkGray, Color.gray, Color.lightGray));
        panelInstruments.add(panelInstrumentLoad);

        JLabel labelLoad = new JLabel("Load");
        labelLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInstrumentLoad.add(labelLoad);

        JPanel panelLoadRubbish = new JPanel();
        panelInstrumentLoad.add(panelLoadRubbish);
        panelLoadRubbish.setBackground(panelLoadRubbish.getParent().getBackground());

        JButton buttonLoad = new JButton("...");
        buttonLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonLoad.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser openFileDialog = new JFileChooser();
                openFileDialog.setDialogTitle("Load");
                if (openFileDialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileReader fileReader = new FileReader(openFileDialog.getSelectedFile());
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        burgersPool.clear();
                        String record;
                        while ((record = bufferedReader.readLine()) != null) {
                            burgersPool.add(BurgerJSONFactory.getInstanceFromJSON(record));
                        }
                    } catch (IOException ioe) {
                        JOptionPane.showMessageDialog(null, "Error occurred while loading hamburgers (" + ioe.getMessage() + ")", "IO Exception", JOptionPane.ERROR_MESSAGE);
                    } catch (ClassNotFoundException cnfe) {
                        JOptionPane.showMessageDialog(null, "Error occurred while loading hamburgers (" + cnfe.getMessage() + ")", "CLass not found", JOptionPane.ERROR_MESSAGE);
                    } catch (InvalidParameterException ipe) {
                        JOptionPane.showMessageDialog(null, "Error occurred while loading hamburgers (" + ipe.getMessage() + ")", "Invalid parameter exception", JOptionPane.ERROR_MESSAGE);
                    } catch (NoSuchMethodException nsme) {
                        JOptionPane.showMessageDialog(null, "Error occurred while loading hamburgers (" + nsme.getMessage() + ")", "No constructor in burger", JOptionPane.ERROR_MESSAGE);
                    } catch (IllegalAccessException iae) {
                        JOptionPane.showMessageDialog(null, "Error occurred while loading hamburgers (" + iae.getMessage() + ")", "Illegal access exception", JOptionPane.ERROR_MESSAGE);
                    } catch (InstantiationException ie) {
                        JOptionPane.showMessageDialog(null, "Error occurred while loading hamburgers (" + ie.getMessage() + ")", "Impossible to create burger", JOptionPane.ERROR_MESSAGE);
                    } catch (InvocationTargetException ite) {
                        JOptionPane.showMessageDialog(null, "Error occurred while loading hamburgers (" + ite.getMessage() + ")", "Invocation target exception", JOptionPane.ERROR_MESSAGE);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Error occurred while loading hamburgers (" + nfe.getMessage() + ")", "Number Format Exception", JOptionPane.ERROR_MESSAGE);
                    }
                    listBurgers.setListData(burgersPool);
                }
            }
        });
        panelInstrumentLoad.add(buttonLoad);

        //---------------------------------------------
        //-----------Instrument save instance----------
        //---------------------------------------------
        panelInstrumentSave = new JPanel();
        panelInstrumentSave.setLayout(new BoxLayout(panelInstrumentSave, BoxLayout.Y_AXIS));
        panelInstrumentSave.setPreferredSize(stdPanelInstrumentSaveSize);
        panelInstrumentSave.setBackground(stdInstrumentBackground);
        panelInstrumentSave.setBorder(new BasicBorders.FieldBorder(Color.gray, Color.darkGray, Color.gray, Color.lightGray));
        panelInstruments.add(panelInstrumentSave);

        JLabel labelSave = new JLabel("Save");
        labelSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInstrumentSave.add(labelSave);

        JPanel panelSaveRubbish = new JPanel();
        panelInstrumentSave.add(panelSaveRubbish);
        panelSaveRubbish.setBackground(panelSaveRubbish.getParent().getBackground());

        JButton buttonSave = new JButton("...");
        buttonSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonSave.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser saveFileChooser = new JFileChooser();
                saveFileChooser.setDialogTitle("Save");
                if (saveFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileWriter fileWriter = new FileWriter(saveFileChooser.getSelectedFile(), false);
                        for (JSONable burger : burgersPool) {
                            fileWriter.append(burger.toJSON()).append(System.lineSeparator());
                            fileWriter.flush();
                        }
                    } catch (IOException ioe) {
                        JOptionPane.showMessageDialog(null, "Error occurred while saving hamburgers (" + ioe.getMessage() + ")", "IO Exception", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panelInstrumentSave.add(buttonSave);
        //---------------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------List of burgers----------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------
        listBurgers = new JList<>();
        listBurgers.setListData(burgersPool);
        listBurgers.setBorder(new BorderUIResource.BevelBorderUIResource(BevelBorder.RAISED));
        JScrollPane listBurgersScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listBurgersScrollPane.setViewportView(listBurgers);
        listBurgersScrollPane.setPreferredSize(new Dimension(0, 600));
        listBurgers.setLayoutOrientation(JList.VERTICAL);
        this.getContentPane().add(listBurgersScrollPane);
        //---------------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------The final step-----------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------
        this.pack();
    }
}