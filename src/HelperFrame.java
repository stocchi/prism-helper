import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class HelperFrame
extends JFrame {
    private static final long serialVersionUID = 6359843786380885160L;
    private static final double version = 0.99;
    private JPanel main;
    private JPanel settings;
    private JPanel totodile;
    private JPanel totodileDVSPanel;
    private JDialog optionDialog;
    private GSCDVCalculatorPanel calc;
    private final GoldDVCalculatorPanel goldCalc;
    private final CrystalDVCalculatorPanel crystalCalc;
    private String font = "";
    private JButton buttonOptions;
    private PreviewPane previewPane;
    private JColorChooser cc;
    private ActionListener okListener;
    private JLabel lastButtonPressed = null;
    private int baseWidth = 800;
    private int baseHeight = 740;
    private int totoWidth = 240;
    private int totoHeight = 80;
    int maxWidth = 432;
    int maxHeight = 108;
    private Color totoBackgroundColor = new Color(255, 255, 255);
    String totoTitleText = "Toto DVs (0-15)";
    private int totoTitleFontSize = 20;
    private String totoTitleFont = this.font;
    private int totoTitleFontExtra = Font.BOLD;
    private Color totoTitleColor = new Color(0, 0, 0);
    private int totoColumnHeadersFontSize = 14;
    private String totoColumnHeadersFont = this.font;
    private int totoColumnHeadersFontExtra = Font.BOLD;
    private Color totoColumnHeadersColor = new Color(0, 0, 0);
    private int totoDVNumbersFontSizeBig = 16;
    private int totoDVNumbersFontSizeSmall = 14;
    private String totoDVNumbersFont = this.font;
    private int totoDVNumbersFontExtra = Font.BOLD;
    private Color totoDVNumbersColor = new Color(0, 0, 0);
    private JLabel labelTitle;
    private JLabel labelHPDV;
    private JLabel labelAtkDV;
    private JLabel labelDefDV;
    private JLabel labelSpdDV;
    private JLabel labelSpcDV;
    private JLabel labelHP;
    private JLabel labelAtk;
    private JLabel labelDef;
    private JLabel labelSpd;
    private JLabel labelSpc;
    private JSpinner spinnerTotoAreaWidth;
    private JSpinner spinnerTotoAreaHeight;
    private JSpinner spinnerTotoTitleSize;
    private JSpinner spinnerTotoColumnHeadersFontSize;
    private JSpinner spinnerTotototoDVNumbersFontSizeBig;
    private JSpinner spinnerTotototoDVNumbersFontSizeSmall;
    private JLabel labelButtonTotoBackgroundColor;
    private JLabel labelButtonTotoTitleColor;
    private JLabel labelButtonTotoColumnHeadersColor;
    private JLabel labelButtonTotoDVNumbersColor;
    private JComboBox<String> comboBoxTotoTitleFont;
    private JComboBox<String> comboBoxTotoTitleFontExtra;
    private JComboBox<String> comboBoxTotoColumnHeadersFont;
    private JComboBox<String> comboBoxTotoColumnHeadersFontExtra;
    private JComboBox<String> comboBoxTotoDVNumbersFont;
    private JComboBox<String> comboBoxTotoDVNumbersFontExtra;
    private JTextField textFieldTotoTitleText;
    private JDialog editTotoDialog;
    private JRadioButton radioGold;
    private JRadioButton radioCrystal;
    private ButtonGroup radioGSC;
    private boolean international;
    private boolean isCrystal;
    private boolean initializing = true;
    private String executionPath;
    private boolean[] redHP = new boolean[16];
    private boolean[] redAtk = new boolean[16];
    private boolean[] redDef = new boolean[16];
    private boolean[] redSpd = new boolean[16];
    private boolean[] redSpc = new boolean[16];
    private int hpdv;
    private int atkdv;
    private int defdv;
    private int spddv;
    private int spcdv;

    public String getExecutionPath() {
        return this.executionPath;
    }

    private void setExecutionPath() {
        try {
            URL location = HelperFrame.class.getProtectionDomain().getCodeSource().getLocation();
            File fh = new File(URLDecoder.decode(location.getFile(), "UTF-8")).getParentFile();
            this.executionPath = String.valueOf(fh.getAbsolutePath()) + File.separator;
        }
        catch (Exception e) {
            e.printStackTrace();
            this.executionPath = "./";
        }
    }

    public HelperFrame(String font) {
        super("Pok\u00e9mon GSC DV Helper");
        if (UIManager.getSystemLookAndFeelClassName().contains("windows")) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception var2_2) {
                // empty catch block
            }
        }
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                HelperFrame.this.save();
            }
        });
        this.setResizable(false);
        this.setBounds(0, 0, baseWidth, baseHeight + Math.max(0, this.totoHeight - 100));
        this.setLocationRelativeTo(null);
        this.setExecutionPath();
        this.font = font;
        this.main = new JPanel();
        this.main.setLayout(null);
        this.main.setBounds(0, 0, baseWidth, baseHeight + Math.max(0, this.totoHeight - 100));
        goldCalc = new GoldDVCalculatorPanel(HelperFrame.this, this.font);
        crystalCalc = new CrystalDVCalculatorPanel(HelperFrame.this, this.font);
        this.calc = crystalCalc;
        this.settings = new JPanel();
        this.settings.setLayout(null);
        this.settings.setBounds(473, 590, 319, 160);
        this.settings.setBackground(null);
        this.initOptions();
        for (int i2 = 0; i2 < 16; ++i2) {
            this.redHP[i2] = false;
            this.redAtk[i2] = false;
            this.redDef[i2] = false;
            this.redSpd[i2] = false;
            this.redSpc[i2] = false;
        }
        this.hpdv = -1;
        this.atkdv = -1;
        this.defdv = -1;
        this.spddv = -1;
        this.spcdv = -1;
        this.buttonOptions = new JButton("Options");
        this.buttonOptions.setMargin(new Insets(1, 1, 1, 1));
        this.buttonOptions.setFont(new Font(font, Font.BOLD, 13));
        this.buttonOptions.setBounds(115, 39, 80, 28);
        this.buttonOptions.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                editTotoDialog.setLocation(optionDialog.getLocation().x, optionDialog.getLocation().y);
                HelperFrame.this.editTotoDialog.setVisible(true);
            }
/*
            @Override
            public void actionPerformed(ActionEvent evt) {
                optionDialog.setLocation(optionDialog.getParent().getLocation().x + 285, optionDialog.getParent().getLocation().y + 140);
                HelperFrame.this.optionDialog.setVisible(true);
            }
*/
        });
        this.settings.add(this.buttonOptions);
        JButton buttonAbout = new JButton("About");
        buttonAbout.setBounds(115, 71, 80, 28);
        buttonAbout.setFont(new Font(font, Font.BOLD, 13));
        buttonAbout.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HelperFrame.this.about();
            }
        });
        this.settings.add(buttonAbout);
        ImageIcon iconReset = new ImageIcon(String.valueOf(this.getExecutionPath()) + "/resources/reset.png");
        JButton buttonReset = new JButton(iconReset);
        buttonReset.setBounds(0, 10, 100, 100);
        buttonReset.setBorderPainted(false);
        buttonReset.setContentAreaFilled(false);
        buttonReset.setFocusPainted(false);
        buttonReset.setOpaque(false);
        buttonReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonReset.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HelperFrame.this.reset();
            }
        });
        this.settings.add(buttonReset);
        radioCrystal = new JRadioButton("Crystal");
        radioCrystal.setBounds(220,39,80,28);
        radioCrystal.setSelected(true);
        radioCrystal.setFont(new Font(font, Font.BOLD, 14));
        Color crystal = new Color(113,158,179);
        radioCrystal.setForeground(crystal);
        radioCrystal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!HelperFrame.this.isCrystal) {
                    HelperFrame.this.goldCalc.reset();
                    HelperFrame.this.main.remove(calc);
                    HelperFrame.this.calc = crystalCalc;
                    HelperFrame.this.main.add(calc);
                    HelperFrame.this.reset();
                    HelperFrame.this.repaint();
                    HelperFrame.this.revalidate();
                    HelperFrame.this.isCrystal = true;
                }
            }
        });
        radioGold = new JRadioButton("Gold");
        radioGold.setBounds(220,71,80,28);
        radioGold.setFont(new Font(font, Font.BOLD, 14));
        Color gold = new Color(179,151,0);
        radioGold.setForeground(gold);
        radioGold.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(HelperFrame.this.isCrystal) {
                    HelperFrame.this.crystalCalc.reset();
                    HelperFrame.this.main.remove(calc);
                    HelperFrame.this.calc = goldCalc;
                    HelperFrame.this.main.add(calc);
                    HelperFrame.this.reset();
                    HelperFrame.this.repaint();
                    HelperFrame.this.revalidate();
                    HelperFrame.this.isCrystal = false;
                }
            }
        });
        radioGSC = new ButtonGroup();
        radioGSC.add(radioCrystal);
        radioGSC.add(radioGold);
        this.settings.add(radioCrystal);
        this.settings.add(radioGold);
        this.initTotodile();
        this.main.add(this.calc);
        this.main.add(this.settings);
        this.main.add(this.totodile);
        this.add(this.main);
        this.international = true;
        this.isCrystal = true;
        this.load();
        this.initializing = false;
        this.updateTotoLookAndFeel();
    }

    private void initTotodile() {
        this.totodile = new JPanel();
        this.totodile.setBackground(this.totoBackgroundColor);
        this.totodile.setBounds(0, 612, this.totoWidth, this.totoHeight);
        this.totodile.setLayout(new BorderLayout());
        this.totodile.setBorder(new EmptyBorder(5, 5, 0, 5));
        this.labelTitle = new JLabel(this.totoTitleText, SwingConstants.CENTER);
        this.labelTitle.setFont(new Font(this.totoTitleFont, this.totoTitleFontExtra, this.totoTitleFontSize));
        this.totodile.add(this.labelTitle, "North");
        this.totodileDVSPanel = new JPanel();
        this.totodileDVSPanel.setBackground(this.totoBackgroundColor);
        this.totodileDVSPanel.setLayout(new GridLayout(2, 3));
        this.totodileDVSPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        this.labelHP = new JLabel("HP", SwingConstants.CENTER);
        this.labelHP.setFont(new Font(this.totoColumnHeadersFont, this.totoColumnHeadersFontExtra, this.totoColumnHeadersFontSize));
        this.totodileDVSPanel.add(this.labelHP);
        this.labelAtk = new JLabel("ATK", SwingConstants.CENTER);
        this.labelAtk.setFont(new Font(this.totoColumnHeadersFont, this.totoColumnHeadersFontExtra, this.totoColumnHeadersFontSize));
        this.totodileDVSPanel.add(this.labelAtk);
        this.labelDef = new JLabel("DEF", SwingConstants.CENTER);
        this.labelDef.setFont(new Font(this.totoColumnHeadersFont, this.totoColumnHeadersFontExtra, this.totoColumnHeadersFontSize));
        this.totodileDVSPanel.add(this.labelDef);
        this.labelSpd = new JLabel("SPD", SwingConstants.CENTER);
        this.labelSpd.setFont(new Font(this.totoColumnHeadersFont, this.totoColumnHeadersFontExtra, this.totoColumnHeadersFontSize));
        this.totodileDVSPanel.add(this.labelSpd);
        this.labelSpc = new JLabel("SPC", SwingConstants.CENTER);
        this.labelSpc.setFont(new Font(this.totoColumnHeadersFont, this.totoColumnHeadersFontExtra, this.totoColumnHeadersFontSize));
        this.totodileDVSPanel.add(this.labelSpc);
        this.labelHPDV = new JLabel("?", SwingConstants.CENTER);
        this.labelHPDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        this.totodileDVSPanel.add(this.labelHPDV);
        this.labelAtkDV = new JLabel("?", SwingConstants.CENTER);
        this.labelAtkDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        this.totodileDVSPanel.add(this.labelAtkDV);
        this.labelDefDV = new JLabel("?", SwingConstants.CENTER);
        this.labelDefDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        this.totodileDVSPanel.add(this.labelDefDV);
        this.labelSpdDV = new JLabel("?", SwingConstants.CENTER);
        this.labelSpdDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        this.totodileDVSPanel.add(this.labelSpdDV);
        this.labelSpcDV = new JLabel("?", SwingConstants.CENTER);
        this.labelSpcDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        this.totodileDVSPanel.add(this.labelSpcDV);
        this.totodile.add(this.totodileDVSPanel, "Center");
        this.settings.add(this.totodile);
    }

    private void initOptions() {
        this.optionDialog = new JDialog(this);
        this.optionDialog.setResizable(false);
        this.optionDialog.setLayout(null);
        this.optionDialog.setBackground(null);
        this.optionDialog.setModal(true);
        this.optionDialog.setAlwaysOnTop(true);
        this.optionDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.optionDialog.setBounds(this.getLocation().x + 285, this.getLocation().y + 140, 150, 155);
        this.optionDialog.setTitle("Options");
        this.optionDialog.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });

        ImageIcon iconInternational = new ImageIcon(String.valueOf(this.getExecutionPath()) + "/resources/international.png");
        ImageIcon iconGermany = new ImageIcon(String.valueOf(this.getExecutionPath()) + "/resources/germany.png");
        JButton buttonInternational = new JButton(iconInternational);
        buttonInternational.setBounds(5, 5, 32, 32);
        buttonInternational.setBorderPainted(false);
        buttonInternational.setContentAreaFilled(false);
        buttonInternational.setFocusPainted(false);
        buttonInternational.setOpaque(false);
        buttonInternational.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonInternational.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HelperFrame.this.setInternational(true);
            }
        });
        this.optionDialog.add(buttonInternational);
        JButton buttonGermany = new JButton(iconGermany);
        buttonGermany.setBounds(45, 5, 32, 32);
        buttonGermany.setBorderPainted(false);
        buttonGermany.setContentAreaFilled(false);
        buttonGermany.setFocusPainted(false);
        buttonGermany.setOpaque(false);
        buttonGermany.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonGermany.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HelperFrame.this.setInternational(false);
            }
        });
        this.optionDialog.add(buttonGermany);
        JLabel labelDVSelection = new JLabel("<html><u>DV Selection</u></html>");
        labelDVSelection.setBounds(5, 40, 150, 20);
        labelDVSelection.setFont(new Font(this.font, Font.BOLD, 14));
        this.optionDialog.add(labelDVSelection);
        JButton buttonEditToto = new JButton("Edit Toto Look");
        buttonEditToto.setFont(new Font(this.font, Font.BOLD, 12));
        buttonEditToto.setMargin(new Insets(2, 0, 2, 0));
        buttonEditToto.setBounds(5, 90, 130, 30);
        buttonEditToto.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                editTotoDialog.setLocation(optionDialog.getLocation().x, optionDialog.getLocation().y);
                HelperFrame.this.editTotoDialog.setVisible(true);
            }
        });
        this.optionDialog.add(buttonEditToto);
        this.editTotoDialog = new JDialog(this.optionDialog);
        this.editTotoDialog.setResizable(false);
        this.editTotoDialog.setLayout(null);
        this.editTotoDialog.setBackground(null);
        this.editTotoDialog.setModal(true);
        this.editTotoDialog.setAlwaysOnTop(true);
        this.editTotoDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.editTotoDialog.setBounds(this.optionDialog.getLocation().x, this.optionDialog.getLocation().y, 220, 440);
        this.editTotoDialog.setTitle("Toto look-and-feel");
        this.editTotoDialog.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String[] fontsExtra = new String[]{"Plain", "Bold", "Italic"};
        ChangeListener changeListenerToto = new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                HelperFrame.this.updateTotoLookAndFeel();
            }
        };
        JLabel labelTotoAreaWidth = new JLabel("Width (max " + maxWidth + ")");
        labelTotoAreaWidth.setBounds(5, 5, 120, 20);
        labelTotoAreaWidth.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelTotoAreaWidth);
        JLabel labelTotoAreaHeight = new JLabel("Height (max " + maxHeight + ")");
        labelTotoAreaHeight.setBounds(5, 25, 120, 20);
        labelTotoAreaHeight.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelTotoAreaHeight);
        JLabel labelTotoAreaBackground = new JLabel("Background");
        labelTotoAreaBackground.setBounds(5, 45, 100, 20);
        labelTotoAreaBackground.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelTotoAreaBackground);
        SpinnerNumberModel widthModel = new SpinnerNumberModel(this.totoWidth, 0, maxWidth, 1);
        this.spinnerTotoAreaWidth = new JSpinner(widthModel);
        this.spinnerTotoAreaWidth.setFont(new Font(this.font, Font.PLAIN, 11));
        this.spinnerTotoAreaWidth.setBounds(120, 5, 40, 20);
        this.spinnerTotoAreaWidth.addChangeListener(changeListenerToto);
        this.editTotoDialog.add(this.spinnerTotoAreaWidth);
        SpinnerNumberModel heightModel = new SpinnerNumberModel(this.totoHeight, 0, maxHeight, 1);
        this.spinnerTotoAreaHeight = new JSpinner(heightModel);
        this.spinnerTotoAreaHeight.setFont(new Font(this.font, Font.PLAIN, 11));
        this.spinnerTotoAreaHeight.setBounds(120, 25, 40, 20);
        this.spinnerTotoAreaHeight.addChangeListener(changeListenerToto);
        this.editTotoDialog.add(this.spinnerTotoAreaHeight);
        this.labelButtonTotoBackgroundColor = new JLabel("");
        this.labelButtonTotoBackgroundColor.setBackground(this.totoBackgroundColor);
        this.labelButtonTotoBackgroundColor.setOpaque(true);
        this.labelButtonTotoBackgroundColor.setBounds(120, 45, 40, 20);
        this.labelButtonTotoBackgroundColor.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                HelperFrame.changeLastButtonPressed(HelperFrame.this, HelperFrame.this.labelButtonTotoBackgroundColor);
                HelperFrame.this.cc.setColor(HelperFrame.this.labelButtonTotoBackgroundColor.getBackground());
                HelperFrame.this.previewPane.setForeground(HelperFrame.this.labelButtonTotoBackgroundColor.getBackground());
                JDialog d = JColorChooser.createDialog(HelperFrame.this.editTotoDialog, "Choose a color", true, HelperFrame.this.cc, HelperFrame.this.okListener, null);
                d.setVisible(true);
            }
        });
        this.editTotoDialog.add(this.labelButtonTotoBackgroundColor);
        JLabel labelTitle = new JLabel("Title");
        labelTitle.setBounds(5, 70, 150, 20);
        labelTitle.setFont(new Font(this.font, Font.BOLD, 14));
        this.editTotoDialog.add(labelTitle);
        JLabel labelTitleText = new JLabel("Text:");
        labelTitleText.setBounds(10, 90, 50, 20);
        labelTitleText.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelTitleText);
        JLabel labelTitleFont = new JLabel("Font:");
        labelTitleFont.setBounds(10, 110, 50, 20);
        labelTitleFont.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelTitleFont);
        JLabel labelTitleSize = new JLabel("Size:");
        labelTitleSize.setBounds(10, 130, 50, 20);
        labelTitleSize.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelTitleSize);
        JLabel labelTitleColor = new JLabel("Color:");
        labelTitleColor.setBounds(10, 150, 50, 20);
        labelTitleColor.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelTitleColor);
        this.textFieldTotoTitleText = new JTextField(this.totoTitleText);
        this.textFieldTotoTitleText.setFont(new Font(this.font, Font.PLAIN, 11));
        this.textFieldTotoTitleText.setBounds(50, 90, 150, 20);
        this.textFieldTotoTitleText.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void changedUpdate(DocumentEvent e) {
                this.update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                this.update();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                this.update();
            }

            public void update() {
                HelperFrame.this.updateTotoLookAndFeel();
            }
        });
        this.editTotoDialog.add(this.textFieldTotoTitleText);
        this.comboBoxTotoTitleFont = new JComboBox<String>(fonts);
        this.comboBoxTotoTitleFont.setFont(new Font(this.font, 0, 11));
        this.comboBoxTotoTitleFont.setBounds(50, 110, 150, 20);
        this.comboBoxTotoTitleFont.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                HelperFrame.this.updateTotoLookAndFeel();
            }
        });
        this.comboBoxTotoTitleFont.setSelectedItem(this.totoTitleFont);
        this.editTotoDialog.add(this.comboBoxTotoTitleFont);
        this.comboBoxTotoTitleFontExtra = new JComboBox<String>(fontsExtra);
        this.comboBoxTotoTitleFontExtra.setFont(new Font(this.font, 0, 11));
        this.comboBoxTotoTitleFontExtra.setBounds(140, 130, 60, 20);
        this.comboBoxTotoTitleFontExtra.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                HelperFrame.this.updateTotoLookAndFeel();
            }
        });
        this.comboBoxTotoTitleFontExtra.setSelectedIndex(this.totoTitleFontExtra);
        this.editTotoDialog.add(this.comboBoxTotoTitleFontExtra);
        SpinnerNumberModel totoTitleSizeModel = new SpinnerNumberModel(this.totoTitleFontSize, 0, 100, 1);
        this.spinnerTotoTitleSize = new JSpinner(totoTitleSizeModel);
        this.spinnerTotoTitleSize.setFont(new Font(this.font, 0, 11));
        this.spinnerTotoTitleSize.setBounds(80, 130, 40, 20);
        this.spinnerTotoTitleSize.addChangeListener(changeListenerToto);
        this.editTotoDialog.add(this.spinnerTotoTitleSize);
        this.labelButtonTotoTitleColor = new JLabel("");
        this.labelButtonTotoTitleColor.setBackground(this.totoTitleColor);
        this.labelButtonTotoTitleColor.setOpaque(true);
        this.labelButtonTotoTitleColor.setBounds(80, 150, 40, 20);
        this.labelButtonTotoTitleColor.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                HelperFrame.changeLastButtonPressed(HelperFrame.this, HelperFrame.this.labelButtonTotoTitleColor);
                HelperFrame.this.cc.setColor(HelperFrame.this.labelButtonTotoTitleColor.getBackground());
                HelperFrame.this.previewPane.setForeground(HelperFrame.this.labelButtonTotoTitleColor.getBackground());
                JDialog d = JColorChooser.createDialog(HelperFrame.this.editTotoDialog, "Choose a color", true, HelperFrame.this.cc, HelperFrame.this.okListener, null);
                d.setVisible(true);
            }
        });
        this.editTotoDialog.add(this.labelButtonTotoTitleColor);
        JLabel labelColumnHeaders = new JLabel("Column Headers");
        labelColumnHeaders.setBounds(5, 175, 150, 20);
        labelColumnHeaders.setFont(new Font(this.font, Font.BOLD, 14));
        this.editTotoDialog.add(labelColumnHeaders);
        JLabel labelColumnHeadersFont = new JLabel("Font:");
        labelColumnHeadersFont.setBounds(10, 195, 50, 20);
        labelColumnHeadersFont.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelColumnHeadersFont);
        JLabel labelColumnHeadersSize = new JLabel("Size:");
        labelColumnHeadersSize.setBounds(10, 215, 50, 20);
        labelColumnHeadersSize.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelColumnHeadersSize);
        JLabel labelColumnHeadersColor = new JLabel("Color:");
        labelColumnHeadersColor.setBounds(10, 235, 50, 20);
        labelColumnHeadersColor.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelColumnHeadersColor);
        this.comboBoxTotoColumnHeadersFont = new JComboBox<String>(fonts);
        this.comboBoxTotoColumnHeadersFont.setFont(new Font(this.font, Font.PLAIN, 11));
        this.comboBoxTotoColumnHeadersFont.setBounds(50, 195, 150, 20);
        this.comboBoxTotoColumnHeadersFont.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                HelperFrame.this.updateTotoLookAndFeel();
            }
        });
        this.comboBoxTotoColumnHeadersFont.setSelectedItem(this.totoColumnHeadersFont);
        this.editTotoDialog.add(this.comboBoxTotoColumnHeadersFont);
        this.comboBoxTotoColumnHeadersFontExtra = new JComboBox<String>(fontsExtra);
        this.comboBoxTotoColumnHeadersFontExtra.setFont(new Font(this.font, Font.PLAIN, 11));
        this.comboBoxTotoColumnHeadersFontExtra.setBounds(140, 215, 60, 20);
        this.comboBoxTotoColumnHeadersFontExtra.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                HelperFrame.this.updateTotoLookAndFeel();
            }
        });
        this.comboBoxTotoColumnHeadersFontExtra.setSelectedIndex(this.totoColumnHeadersFontExtra);
        this.editTotoDialog.add(this.comboBoxTotoColumnHeadersFontExtra);
        SpinnerNumberModel totoColumnHeadersSizeModel = new SpinnerNumberModel(this.totoColumnHeadersFontSize, 0, 100, 1);
        this.spinnerTotoColumnHeadersFontSize = new JSpinner(totoColumnHeadersSizeModel);
        this.spinnerTotoColumnHeadersFontSize.setFont(new Font(this.font, Font.PLAIN, 11));
        this.spinnerTotoColumnHeadersFontSize.setBounds(80, 215, 40, 20);
        this.spinnerTotoColumnHeadersFontSize.addChangeListener(changeListenerToto);
        this.editTotoDialog.add(this.spinnerTotoColumnHeadersFontSize);
        this.labelButtonTotoColumnHeadersColor = new JLabel("");
        this.labelButtonTotoColumnHeadersColor.setBackground(this.totoTitleColor);
        this.labelButtonTotoColumnHeadersColor.setOpaque(true);
        this.labelButtonTotoColumnHeadersColor.setBounds(80, 235, 40, 20);
        this.labelButtonTotoColumnHeadersColor.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                HelperFrame.changeLastButtonPressed(HelperFrame.this, HelperFrame.this.labelButtonTotoColumnHeadersColor);
                HelperFrame.this.cc.setColor(HelperFrame.this.labelButtonTotoColumnHeadersColor.getBackground());
                HelperFrame.this.previewPane.setForeground(HelperFrame.this.labelButtonTotoColumnHeadersColor.getBackground());
                JDialog d = JColorChooser.createDialog(HelperFrame.this.editTotoDialog, "Choose a color", true, HelperFrame.this.cc, HelperFrame.this.okListener, null);
                d.setVisible(true);
            }
        });
        this.editTotoDialog.add(this.labelButtonTotoColumnHeadersColor);
        JLabel labelDVNumbers = new JLabel("DV Numbers");
        labelDVNumbers.setBounds(5, 260, 150, 20);
        labelDVNumbers.setFont(new Font(this.font, Font.BOLD, 14));
        this.editTotoDialog.add(labelDVNumbers);
        JLabel labelDVNumbersFont = new JLabel("Font:");
        labelDVNumbersFont.setBounds(10, 280, 100, 20);
        labelDVNumbersFont.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelDVNumbersFont);
        JLabel labelDVNumbersSizeBig = new JLabel("Size 1 DV:");
        labelDVNumbersSizeBig.setBounds(10, 300, 100, 20);
        labelDVNumbersSizeBig.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelDVNumbersSizeBig);
        JLabel labelDVNumbersSizeSmall = new JLabel("Size >1 DV:");
        labelDVNumbersSizeSmall.setBounds(10, 320, 100, 20);
        labelDVNumbersSizeSmall.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelDVNumbersSizeSmall);
        JLabel labelDVNumbersColor = new JLabel("Color:");
        labelDVNumbersColor.setBounds(10, 340, 100, 20);
        labelDVNumbersColor.setFont(new Font(this.font, Font.BOLD, 12));
        this.editTotoDialog.add(labelDVNumbersColor);
        this.comboBoxTotoDVNumbersFont = new JComboBox<String>(fonts);
        this.comboBoxTotoDVNumbersFont.setFont(new Font(this.font, Font.PLAIN, 11));
        this.comboBoxTotoDVNumbersFont.setBounds(50, 280, 150, 20);
        this.comboBoxTotoDVNumbersFont.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                HelperFrame.this.updateTotoLookAndFeel();
            }
        });
        this.comboBoxTotoDVNumbersFont.setSelectedItem(this.totoDVNumbersFont);
        this.editTotoDialog.add(this.comboBoxTotoDVNumbersFont);
        this.comboBoxTotoDVNumbersFontExtra = new JComboBox<String>(fontsExtra);
        this.comboBoxTotoDVNumbersFontExtra.setFont(new Font(this.font, Font.PLAIN, 11));
        this.comboBoxTotoDVNumbersFontExtra.setBounds(140, 300, 60, 20);
        this.comboBoxTotoDVNumbersFontExtra.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                HelperFrame.this.updateTotoLookAndFeel();
            }
        });
        this.comboBoxTotoDVNumbersFontExtra.setSelectedIndex(this.totoDVNumbersFontExtra);
        this.editTotoDialog.add(this.comboBoxTotoDVNumbersFontExtra);
        SpinnerNumberModel totoDVNumbersSizeBigModel = new SpinnerNumberModel(this.totoDVNumbersFontSizeBig, 0, 100, 1);
        this.spinnerTotototoDVNumbersFontSizeBig = new JSpinner(totoDVNumbersSizeBigModel);
        this.spinnerTotototoDVNumbersFontSizeBig.setFont(new Font(this.font, Font.PLAIN, 11));
        this.spinnerTotototoDVNumbersFontSizeBig.setBounds(80, 300, 40, 20);
        this.spinnerTotototoDVNumbersFontSizeBig.addChangeListener(changeListenerToto);
        this.editTotoDialog.add(this.spinnerTotototoDVNumbersFontSizeBig);
        SpinnerNumberModel totoDVNumbersSizeSmallModel = new SpinnerNumberModel(this.totoDVNumbersFontSizeSmall, 0, 100, 1);
        this.spinnerTotototoDVNumbersFontSizeSmall = new JSpinner(totoDVNumbersSizeSmallModel);
        this.spinnerTotototoDVNumbersFontSizeSmall.setFont(new Font(this.font, Font.PLAIN, 11));
        this.spinnerTotototoDVNumbersFontSizeSmall.setBounds(80, 320, 40, 20);
        this.spinnerTotototoDVNumbersFontSizeSmall.addChangeListener(changeListenerToto);
        this.editTotoDialog.add(this.spinnerTotototoDVNumbersFontSizeSmall);
        this.labelButtonTotoDVNumbersColor = new JLabel("");
        this.labelButtonTotoDVNumbersColor.setBackground(this.totoDVNumbersColor);
        this.labelButtonTotoDVNumbersColor.setOpaque(true);
        this.labelButtonTotoDVNumbersColor.setBounds(80, 340, 40, 20);
        this.labelButtonTotoDVNumbersColor.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                HelperFrame.changeLastButtonPressed(HelperFrame.this, HelperFrame.this.labelButtonTotoDVNumbersColor);
                HelperFrame.this.cc.setColor(HelperFrame.this.labelButtonTotoDVNumbersColor.getBackground());
                HelperFrame.this.previewPane.setForeground(HelperFrame.this.labelButtonTotoDVNumbersColor.getBackground());
                JDialog d = JColorChooser.createDialog(HelperFrame.this.editTotoDialog, "Choose a color", true, HelperFrame.this.cc, HelperFrame.this.okListener, null);
                d.setVisible(true);
            }
        });
        this.editTotoDialog.add(this.labelButtonTotoDVNumbersColor);
        JButton buttonResetDefault = new JButton("Reset Default");
        buttonResetDefault.setFont(new Font(this.font, Font.BOLD, 12));
        buttonResetDefault.setMargin(new Insets(2, 0, 2, 0));
        buttonResetDefault.setBounds(10, 370, 110, 30);
        buttonResetDefault.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HelperFrame.this.spinnerTotoAreaWidth.setValue(240);
                HelperFrame.this.spinnerTotoAreaHeight.setValue(80);
                HelperFrame.this.labelButtonTotoBackgroundColor.setBackground(new Color(255, 255, 255));
                HelperFrame.this.textFieldTotoTitleText.setText("Toto DVs (0-15)");
                HelperFrame.this.spinnerTotoTitleSize.setValue(20);
                HelperFrame.this.comboBoxTotoTitleFont.setSelectedItem(HelperFrame.this.font);
                HelperFrame.this.comboBoxTotoTitleFontExtra.setSelectedIndex(1);
                HelperFrame.this.labelButtonTotoTitleColor.setBackground(new Color(0, 0, 0));
                HelperFrame.this.spinnerTotoColumnHeadersFontSize.setValue(14);
                HelperFrame.this.comboBoxTotoColumnHeadersFont.setSelectedItem(HelperFrame.this.font);
                HelperFrame.this.comboBoxTotoColumnHeadersFontExtra.setSelectedIndex(1);
                HelperFrame.this.labelButtonTotoColumnHeadersColor.setBackground(new Color(0, 0, 0));
                HelperFrame.this.spinnerTotototoDVNumbersFontSizeBig.setValue(16);
                HelperFrame.this.spinnerTotototoDVNumbersFontSizeSmall.setValue(14);
                HelperFrame.this.comboBoxTotoDVNumbersFont.setSelectedItem(HelperFrame.this.font);
                HelperFrame.this.comboBoxTotoDVNumbersFontExtra.setSelectedIndex(1);
                HelperFrame.this.labelButtonTotoDVNumbersColor.setBackground(new Color(0, 0, 0));
                HelperFrame.this.updateTotoLookAndFeel();
            }
        });
        this.editTotoDialog.add(buttonResetDefault);
        this.okListener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (HelperFrame.this.cc.getColor() != null) {
                    HelperFrame.this.lastButtonPressed.setBackground(HelperFrame.this.cc.getColor());
                    HelperFrame.this.lastButtonPressed.setForeground(HelperFrame.this.cc.getColor());
                    HelperFrame.changeTotoBackgroundColor(HelperFrame.this, HelperFrame.this.labelButtonTotoBackgroundColor.getBackground());
                    HelperFrame.changeTotoTitleColor(HelperFrame.this, HelperFrame.this.labelButtonTotoTitleColor.getBackground());
                    HelperFrame.changeTotoColumnHeadersColor(HelperFrame.this, HelperFrame.this.labelButtonTotoColumnHeadersColor.getBackground());
                    HelperFrame.changeTotoDVNumbersColor(HelperFrame.this, HelperFrame.this.labelButtonTotoDVNumbersColor.getBackground());
                    HelperFrame.this.updateTotoLookAndFeel();
                }
            }
        };
        this.cc = new JColorChooser();
        this.previewPane = new PreviewPane();
        this.cc.setPreviewPanel(this.previewPane);
        AbstractColorChooserPanel[] panels = this.cc.getChooserPanels();
        for (AbstractColorChooserPanel panel : panels) {
            if (panel.getDisplayName().contains("RGB") || panel.getDisplayName().contains("RVB")) continue;
            this.cc.removeChooserPanel(panel);
        }
        AbstractColorChooserPanel cp = this.cc.getChooserPanels()[0];
        try {
            Field f = cp.getClass().getDeclaredField("panel");
            f.setAccessible(true);
            Object colorPanel = f.get(cp);
            Field f2 = colorPanel.getClass().getDeclaredField("spinners");
            f2.setAccessible(true);
            Object spinners = f2.get(colorPanel);
            Object transpSlispinner = Array.get(spinners, 3);
            Field f3 = transpSlispinner.getClass().getDeclaredField("slider");
            f3.setAccessible(true);
            JSlider slider = (JSlider)f3.get(transpSlispinner);
            slider.setVisible(false);
            Field f4 = transpSlispinner.getClass().getDeclaredField("spinner");
            f4.setAccessible(true);
            JSpinner spinner = (JSpinner)f4.get(transpSlispinner);
            spinner.setVisible(false);
            Field f5 = transpSlispinner.getClass().getDeclaredField("label");
            f5.setAccessible(true);
            JLabel label = (JLabel)f5.get(transpSlispinner);
            label.setVisible(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load() {
        this.comboBoxTotoTitleFont.setSelectedItem(this.font);
        this.comboBoxTotoTitleFontExtra.setSelectedItem(1);
        this.comboBoxTotoColumnHeadersFont.setSelectedItem(this.font);
        this.comboBoxTotoColumnHeadersFontExtra.setSelectedItem(1);
        this.comboBoxTotoDVNumbersFont.setSelectedItem(this.font);
        this.comboBoxTotoDVNumbersFontExtra.setSelectedItem(1);
        File file = new File(String.valueOf(this.getExecutionPath()) + "/settings.txt");
        BufferedReader br;
        try {
            String s;
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            while ((s = br.readLine()) != null) {
                String[] sp;
                Color c;
                if (s.equals("") || s.substring(0, 1).equals(";") || (sp = s.split("=")).length <= 1) continue;
                if (sp[0].equals("Location")) {
                    String[] location = sp[1].split(",");
                    if (location.length != 2) continue;
                    this.setLocation(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
                    continue;
                }
                if (sp[0].equals("International") && sp[1].equals("false")) {
                    this.setInternational(false);
                    continue;
                }
                if (sp[0].equals("Game") && sp[1].equals("Gold")) {
                    this.setGame(false);
                    this.crystalCalc.reset();
                    this.main.remove(calc);
                    this.calc = goldCalc;
                    this.main.add(calc);
                    this.radioGold.setSelected(true);
                    this.radioCrystal.setSelected(false);
                    this.reset();
                    this.repaint();
                    this.revalidate();
                    continue;
                }
                if (sp[0].equals("totoWidth") && HelperFrame.isInteger(sp[1], 0, this.maxWidth)) {
                    this.spinnerTotoAreaWidth.setValue(Integer.parseInt(sp[1]));
                    continue;
                }
                if (sp[0].equals("totoHeight") && HelperFrame.isInteger(sp[1], 0, this.maxHeight)) {
                    this.spinnerTotoAreaHeight.setValue(Integer.parseInt(sp[1]));
                    continue;
                }
                if (sp[0].equals("totoBackgroundColor") && sp[1].split(",").length >= 3 && HelperFrame.isInteger(sp[1].split(",")[0], 0, 255) && HelperFrame.isInteger(sp[1].split(",")[1], 0, 255) && HelperFrame.isInteger(sp[1].split(",")[2], 0, 255)) {
                    c = new Color(Integer.parseInt(sp[1].split(",")[0]), Integer.parseInt(sp[1].split(",")[1]), Integer.parseInt(sp[1].split(",")[2]));
                    this.labelButtonTotoBackgroundColor.setBackground(c);
                    continue;
                }
                if (sp[0].equals("totoTitleText")) {
                    this.textFieldTotoTitleText.setText(sp[1]);
                    continue;
                }
                if (sp[0].equals("totoTitleFontSize") && HelperFrame.isInteger(sp[1], 0, 500)) {
                    this.spinnerTotoTitleSize.setValue(Integer.parseInt(sp[1]));
                    continue;
                }
                if (sp[0].equals("totoTitleFont")) {
                    this.comboBoxTotoTitleFont.setSelectedItem(sp[1]);
                    continue;
                }
                if (sp[0].equals("totoTitleFontExtra") && HelperFrame.isInteger(sp[1], 0, 2)) {
                    this.comboBoxTotoTitleFontExtra.setSelectedItem(Integer.parseInt(sp[1]));
                    continue;
                }
                if (sp[0].equals("totoTitleColor") && sp[1].split(",").length >= 3 && HelperFrame.isInteger(sp[1].split(",")[0], 0, 255) && HelperFrame.isInteger(sp[1].split(",")[1], 0, 255) && HelperFrame.isInteger(sp[1].split(",")[2], 0, 255)) {
                    c = new Color(Integer.parseInt(sp[1].split(",")[0]), Integer.parseInt(sp[1].split(",")[1]), Integer.parseInt(sp[1].split(",")[2]));
                    this.labelButtonTotoTitleColor.setBackground(c);
                    continue;
                }
                if (sp[0].equals("totoColumnHeadersFontSize") && HelperFrame.isInteger(sp[1], 0, 500)) {
                    this.spinnerTotoColumnHeadersFontSize.setValue(Integer.parseInt(sp[1]));
                    continue;
                }
                if (sp[0].equals("totoColumnHeadersFont")) {
                    this.comboBoxTotoColumnHeadersFont.setSelectedItem(sp[1]);
                    continue;
                }
                if (sp[0].equals("totoColumnHeadersFontExtra") && HelperFrame.isInteger(sp[1], 0, 2)) {
                    this.comboBoxTotoColumnHeadersFontExtra.setSelectedItem(Integer.parseInt(sp[1]));
                    continue;
                }
                if (sp[0].equals("totoColumnHeadersColor") && sp[1].split(",").length >= 3 && HelperFrame.isInteger(sp[1].split(",")[0], 0, 255) && HelperFrame.isInteger(sp[1].split(",")[1], 0, 255) && HelperFrame.isInteger(sp[1].split(",")[2], 0, 255)) {
                    c = new Color(Integer.parseInt(sp[1].split(",")[0]), Integer.parseInt(sp[1].split(",")[1]), Integer.parseInt(sp[1].split(",")[2]));
                    this.labelButtonTotoColumnHeadersColor.setBackground(c);
                    continue;
                }
                if (sp[0].equals("totoDVNumbersFontSizeBig") && HelperFrame.isInteger(sp[1], 0, 500)) {
                    this.spinnerTotototoDVNumbersFontSizeBig.setValue(Integer.parseInt(sp[1]));
                    continue;
                }
                if (sp[0].equals("totoDVNumbersFontSizeSmall") && HelperFrame.isInteger(sp[1], 0, 500)) {
                    this.spinnerTotototoDVNumbersFontSizeSmall.setValue(Integer.parseInt(sp[1]));
                    continue;
                }
                if (sp[0].equals("totoDVNumbersFont")) {
                    this.comboBoxTotoDVNumbersFont.setSelectedItem(sp[1]);
                    continue;
                }
                if (sp[0].equals("totoDVNumbersFontExtra") && HelperFrame.isInteger(sp[1], 0, 2)) {
                    this.comboBoxTotoDVNumbersFontExtra.setSelectedItem(Integer.parseInt(sp[1]));
                    continue;
                }
                if (!sp[0].equals("totoDVNumbersColor") || sp[1].split(",").length < 3 || !HelperFrame.isInteger(sp[1].split(",")[0], 0, 255) || !HelperFrame.isInteger(sp[1].split(",")[1], 0, 255) || !HelperFrame.isInteger(sp[1].split(",")[2], 0, 255)) continue;
                c = new Color(Integer.parseInt(sp[1].split(",")[0]), Integer.parseInt(sp[1].split(",")[1]), Integer.parseInt(sp[1].split(",")[2]));
                this.labelButtonTotoDVNumbersColor.setBackground(c);
            }
            fr.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isInteger(String s, int min, int max) {
        try {
            int i = Integer.parseInt(s);
            if (i < min || i > max) {
                System.out.println("false: " + i);
                return false;
            }
        }
        catch (NumberFormatException e) {
            return false;
        }
        catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    private void save() {
        block12 : {
            File file = new File(String.valueOf(this.getExecutionPath()) + "/settings.txt");
            BufferedWriter out = null;
            try {
                try {
                    FileWriter fw = new FileWriter(file, false);
                    out = new BufferedWriter(fw);
                    out.write("Location=" + this.getLocation().x + "," + this.getLocation().y);
                    out.newLine();
                    out.write("International=" + this.international);
                    out.newLine();
                    String game = this.isCrystal ? "Crystal" : "Gold";
                    out.write("Game=" + game);
                    out.newLine();
                    out.write(";Toto Look-and-feel Settings:");
                    out.newLine();
                    out.write("totoWidth=" + this.totoWidth);
                    out.newLine();
                    out.write("totoHeight=" + this.totoHeight);
                    out.newLine();
                    out.write("totoBackgroundColor=" + this.totoBackgroundColor.getRed() + "," + this.totoBackgroundColor.getGreen() + "," + this.totoBackgroundColor.getBlue());
                    out.newLine();
                    out.write("totoTitleText=" + this.totoTitleText);
                    out.newLine();
                    out.write("totoTitleFontSize=" + this.totoTitleFontSize);
                    out.newLine();
                    out.write("totoTitleFont=" + this.totoTitleFont);
                    out.newLine();
                    out.write("totoTitleFontExtra=" + this.totoTitleFontExtra);
                    out.newLine();
                    out.write("totoTitleColor=" + this.totoTitleColor.getRed() + "," + this.totoTitleColor.getGreen() + "," + this.totoTitleColor.getBlue());
                    out.newLine();
                    out.write("totoColumnHeadersFontSize=" + this.totoColumnHeadersFontSize);
                    out.newLine();
                    out.write("totoColumnHeadersFont=" + this.totoColumnHeadersFont);
                    out.newLine();
                    out.write("totoColumnHeadersFontExtra=" + this.totoColumnHeadersFontExtra);
                    out.newLine();
                    out.write("totoColumnHeadersColor=" + this.totoColumnHeadersColor.getRed() + "," + this.totoColumnHeadersColor.getGreen() + "," + this.totoColumnHeadersColor.getBlue());
                    out.newLine();
                    out.write("totoDVNumbersFontSizeBig=" + this.totoDVNumbersFontSizeBig);
                    out.newLine();
                    out.write("totoDVNumbersFontSizeSmall=" + this.totoDVNumbersFontSizeSmall);
                    out.newLine();
                    out.write("totoDVNumbersFont=" + this.totoDVNumbersFont);
                    out.newLine();
                    out.write("totoDVNumbersFontExtra=" + this.totoDVNumbersFontExtra);
                    out.newLine();
                    out.write("totoDVNumbersColor=" + this.totoDVNumbersColor.getRed() + "," + this.totoDVNumbersColor.getGreen() + "," + this.totoDVNumbersColor.getBlue());
                    out.newLine();
                    out.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    if (out == null) break block12;
                    try {
                        out.close();
                    }
                    catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            finally {
                if (out != null) {
                    try {
                        out.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void updateTotoLookAndFeel() {
        if (this.initializing) {
            return;
        }
        this.totoWidth = (Integer)this.spinnerTotoAreaWidth.getValue();
        this.totoHeight = (Integer)this.spinnerTotoAreaHeight.getValue();
        this.totoBackgroundColor = this.labelButtonTotoBackgroundColor.getBackground();
        this.totoTitleText = this.textFieldTotoTitleText.getText();
        this.totoTitleFontSize = (Integer)this.spinnerTotoTitleSize.getValue();
        this.totoTitleFont = (String)this.comboBoxTotoTitleFont.getSelectedItem();
        this.totoTitleFontExtra = this.comboBoxTotoTitleFontExtra.getSelectedIndex();
        this.totoTitleColor = this.labelButtonTotoTitleColor.getBackground();
        this.totoColumnHeadersFontSize = (Integer)this.spinnerTotoColumnHeadersFontSize.getValue();
        this.totoColumnHeadersFont = (String)this.comboBoxTotoColumnHeadersFont.getSelectedItem();
        this.totoColumnHeadersFontExtra = this.comboBoxTotoColumnHeadersFontExtra.getSelectedIndex();
        this.totoColumnHeadersColor = this.labelButtonTotoColumnHeadersColor.getBackground();
        this.totoDVNumbersFontSizeBig = (Integer)this.spinnerTotototoDVNumbersFontSizeBig.getValue();
        this.totoDVNumbersFontSizeSmall = (Integer)this.spinnerTotototoDVNumbersFontSizeSmall.getValue();
        this.totoDVNumbersFont = (String)this.comboBoxTotoDVNumbersFont.getSelectedItem();
        this.totoDVNumbersFontExtra = this.comboBoxTotoDVNumbersFontExtra.getSelectedIndex();
        this.totoDVNumbersColor = this.labelButtonTotoDVNumbersColor.getBackground();
        this.setSize(baseWidth, baseHeight + Math.max(0, this.totoHeight - 100));
        this.main.setSize(baseWidth, baseHeight + Math.max(0, this.totoHeight - 100));
        this.totodile.setSize(this.totoWidth, this.totoHeight);
        int borderLeftRight = this.totoWidth / 72;
        int borderTopBottom = this.totoHeight / 24;
        this.totodile.setBorder(new EmptyBorder(borderTopBottom, borderLeftRight, 0, borderLeftRight));
        this.totodile.setBackground(this.totoBackgroundColor);
        this.totodileDVSPanel.setBackground(this.totoBackgroundColor);
        this.totodileDVSPanel.setBorder(new EmptyBorder(borderTopBottom, 0, borderTopBottom, 0));
        this.labelTitle.setText(this.totoTitleText);
        this.labelTitle.setFont(new Font(this.totoTitleFont, this.totoTitleFontExtra, this.totoTitleFontSize));
        this.labelTitle.setForeground(this.totoTitleColor);
        this.labelHP.setFont(new Font(this.totoColumnHeadersFont, this.totoColumnHeadersFontExtra, this.totoColumnHeadersFontSize));
        this.labelHP.setForeground(this.totoColumnHeadersColor);
        this.labelAtk.setFont(new Font(this.totoColumnHeadersFont, this.totoColumnHeadersFontExtra, this.totoColumnHeadersFontSize));
        this.labelAtk.setForeground(this.totoColumnHeadersColor);
        this.labelDef.setFont(new Font(this.totoColumnHeadersFont, this.totoColumnHeadersFontExtra, this.totoColumnHeadersFontSize));
        this.labelDef.setForeground(this.totoColumnHeadersColor);
        this.labelSpd.setFont(new Font(this.totoColumnHeadersFont, this.totoColumnHeadersFontExtra, this.totoColumnHeadersFontSize));
        this.labelSpd.setForeground(this.totoColumnHeadersColor);
        this.labelSpc.setFont(new Font(this.totoColumnHeadersFont, this.totoColumnHeadersFontExtra, this.totoColumnHeadersFontSize));
        this.labelSpc.setForeground(this.totoColumnHeadersColor);
        this.labelHPDV.setForeground(this.totoDVNumbersColor);
        this.labelAtkDV.setForeground(this.totoDVNumbersColor);
        this.labelDefDV.setForeground(this.totoDVNumbersColor);
        this.labelSpdDV.setForeground(this.totoDVNumbersColor);
        this.labelSpcDV.setForeground(this.totoDVNumbersColor);
        if (this.labelHPDV.getText().length() <= 2) {
            this.labelHPDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        } else {
            this.labelHPDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeSmall));
        }
        if (this.labelAtkDV.getText().length() <= 2) {
            this.labelAtkDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        } else {
            this.labelAtkDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeSmall));
        }
        if (this.labelDefDV.getText().length() <= 2) {
            this.labelDefDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        } else {
            this.labelDefDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeSmall));
        }
        if (this.labelSpdDV.getText().length() <= 2) {
            this.labelSpdDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        } else {
            this.labelSpdDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeSmall));
        }
        if (this.labelSpcDV.getText().length() <= 2) {
            this.labelSpcDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        } else {
            this.labelSpcDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeSmall));
        }
    }

    public void updateToto(final int column, final int first, final int last, final int possibilities) {
        if (first < 0 || first > 15 || last < 0 || last > 15) {
            return;
        }
        JLabel labelColumn = null;
        if (column == 0) {
            labelColumn = this.labelHPDV;
        } else if (column == 1) {
            labelColumn = this.labelAtkDV;
        } else if (column == 2) {
            labelColumn = this.labelDefDV;
        } else if (column == 3) {
            labelColumn = this.labelSpdDV;
        } else if (column == 4) {
            labelColumn = this.labelSpcDV;
        } else {
            return;
        }
        if (first == last) {
            labelColumn.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
            labelColumn.setText("" + first);
        } else if (possibilities == 2) {
            labelColumn.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeSmall));
            labelColumn.setText(String.valueOf(first) + "/" + last);
        } else if (last - first < 10) {
            labelColumn.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeSmall));
            labelColumn.setText(String.valueOf(first) + "-" + last);
        }
         else {
            labelColumn.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
            labelColumn.setText("?");
        }
    }

    public void manuallySelectDV(int column, int dv) {
        boolean[] redValues = new boolean[16];
        Arrays.fill(redValues, Boolean.TRUE);
        redValues[dv] = false;
        if(column == 0) {
            externalRed(redValues, redAtk, redDef, redSpd, redSpc);
        } else if(column == 1) {
            externalRed(redHP, redValues, redDef, redSpd, redSpc);
        } else if(column == 2) {
            externalRed(redHP, redAtk, redValues, redSpd, redSpc);
        } else if(column == 3) {
            externalRed(redHP, redAtk, redDef, redValues, redSpc);
        } else if(column == 4) {
            externalRed(redHP, redAtk, redDef, redSpd, redValues);
        }
    }
    public void updateDVPanel(boolean[] redHP, boolean[] redAtk, boolean[] redDef, boolean[] redSpd, boolean[] redSpc) {
        this.externalRed(redHP, redAtk, redDef, redSpd, redSpc);
    }

    public void externalRed(boolean[] redHP, boolean[] redAtk, boolean[] redDef, boolean[] redSpd, boolean[] redSpc) {
        for (int i = 0; i < 16; ++i) {
            if (redHP[i]) {
                this.redHP[i] = redHP[i];
            }
            if (redAtk[i]) {
                this.redAtk[i] = redAtk[i];
            }
            if (redDef[i]) {
                this.redDef[i] = redDef[i];
            }
            if (redSpd[i]) {
                this.redSpd[i] = redSpd[i];
            }
            if (!redSpc[i]) continue;
            this.redSpc[i] = redSpc[i];
        }
        while (this.removeByRanges()) {}
        this.setOnlyDV();
        this.updateLabels();
    }

    private boolean removeByRanges() {
        boolean success = false;
        boolean[] hpPossible = new boolean[16];
        boolean[] atkPossible = new boolean[16];
        boolean[] defPossible = new boolean[16];
        boolean[] spdPossible = new boolean[16];
        boolean[] spcPossible = new boolean[16];
        if (this.hpdv == -1) {
            Arrays.fill(hpPossible, true);
        } else {
            Arrays.fill(hpPossible, false);
            hpPossible[this.hpdv] = true;
        }
        if (this.atkdv == -1) {
            Arrays.fill(atkPossible, true);
        } else {
            Arrays.fill(atkPossible, false);
            atkPossible[this.atkdv] = true;
        }
        if (this.defdv == -1) {
            Arrays.fill(defPossible, true);
        } else {
            Arrays.fill(defPossible, false);
            defPossible[this.defdv] = true;
        }
        if (this.spddv == -1) {
            Arrays.fill(spdPossible, true);
        } else {
            Arrays.fill(spdPossible, false);
            spdPossible[this.spddv] = true;
        }
        if (this.spcdv == -1) {
            Arrays.fill(spcPossible, true);
        } else {
            Arrays.fill(spcPossible, false);
            spcPossible[this.spcdv] = true;
        }
        for (int i = 0; i < 16; ++i) {
            if (this.redHP[i]) {
                hpPossible[i] = false;
            }
            if(this.redAtk[i]) {
                atkPossible[i] = false;
            }
            if(this.redDef[i]) {
                defPossible[i] = false;
            }
            if(this.redSpd[i]) {
                spdPossible[i] = false;
            }
            if(this.redSpc[i]) {
                spcPossible[i] = false;
            }
        }
        boolean[] newHPRed = new boolean[16];
        boolean[] newAtkRed = new boolean[16];
        boolean[] newDefRed = new boolean[16];
        boolean[] newSpdRed = new boolean[16];
        boolean[] newSpcRed = new boolean[16];
        Arrays.fill(newHPRed, false);
        Arrays.fill(newAtkRed, false);
        Arrays.fill(newDefRed, false);
        Arrays.fill(newSpdRed, false);
        Arrays.fill(newSpcRed, false);
        for (int i = 0; i < 16; ++i) {
            if (!atkPossible[i]) continue;
            for (int j = 0; j < 16; ++j) {
                if (!defPossible[j]) continue;
                for (int k = 0; k < 16; ++k) {
                    if (!spdPossible[k]) continue;
                    for (int l = 0; l < 16; ++l) {
                        if (!spcPossible[l]) continue;
                        if (!hpPossible[i % 2 * 8 + j % 2 * 4 + k % 2 * 2 + l % 2]) continue;
                        newHPRed[i % 2 * 8 + j % 2 * 4 + k % 2 * 2 + l % 2] = true;
                        newAtkRed[i] = true;
                        newDefRed[j] = true;
                        newSpdRed[k] = true;
                        newSpcRed[l] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 16; ++i) {
            if (hpPossible[i] && !newHPRed[i]) {
                success = true;
                this.redHP[i] = true;
                this.removeStat(0, i);
            }
            if (atkPossible[i] && !newAtkRed[i]) {
                success = true;
                this.redAtk[i] = true;
                this.removeStat(1, i);
            }
            if (defPossible[i] && !newDefRed[i]) {
                success = true;
                this.redDef[i] = true;
                this.removeStat(2, i);
            }
            if (spdPossible[i] && !newSpdRed[i]) {
                success = true;
                this.redSpd[i] = true;
                this.removeStat(3, i);
            }
            if (spcPossible[i] && !newSpcRed[i]) {
                success = true;
                this.redSpc[i] = true;
                this.removeStat(4, i);
            }

        }
        return success;
    }

    private void setOnlyDV() {
        boolean update = true;
        while (update) {
            int i;
            update = false;
            int possibleHP = 0;
            int possibleAtk = 0;
            int possibleDef = 0;
            int possibleSpd = 0;
            int possibleSpc = 0;
            int newHP = -1;
            int newAtk = -1;
            int newDef = -1;
            int newSpd = -1;
            int newSpc = -1;
            for (i = 0; i < 16; ++i) {
                if(!this.redHP[i]) {
                    ++possibleHP;
                    newHP = i;
                }
                if(!this.redAtk[i]) {
                    ++possibleAtk;
                    newAtk = i;
                }
                if(!this.redDef[i]) {
                    ++possibleDef;
                    newDef = i;
                }
                if(!this.redSpd[i]) {
                    ++possibleSpd;
                    newSpd = i;
                }
                if(!this.redSpc[i]) {
                    ++possibleSpc;
                    newSpc = i;
                }
            }
            if (possibleHP == 1) {
                if (this.hpdv != newHP) {
                    update = true;
                }
                this.hpdv = newHP;
            }
            if (possibleAtk == 1) {
                if (this.atkdv != newAtk) {
                    update = true;
                }
                this.atkdv = newAtk;
            }
            if (possibleDef == 1) {
                if (this.defdv != newDef) {
                    update = true;
                }
                this.defdv = newDef;
            }
            if (possibleSpd == 1) {
                if (this.spddv != newSpd) {
                    update = true;
                }
                this.spddv = newSpd;
            }
            if (possibleSpc == 1) {
                if (this.spcdv != newSpc) {
                    update = true;
                }
                this.spcdv = newSpc;
            }
            if (!update) continue;
            for (i = 0; i < 16; ++i) {
                if(this.redHP[i]) {
                    this.removeStat(0, i);
                }
                if(this.redAtk[i]) {
                    this.removeStat(1, i);
                }
                if(this.redDef[i]) {
                    this.removeStat(2, i);
                }
                if(this.redSpd[i]) {
                    this.removeStat(3, i);
                }
                if(this.redSpc[i]) {
                    this.removeStat(4, i);
                }
            }
        }
    }

    private void updateLabels() {
        int firstHP = -1;
        int firstAtk = -1;
        int firstDef = -1;
        int firstSpd = -1;
        int firstSpc = -1;
        int lastHP = -1;
        int lastAtk = -1;
        int lastDef = -1;
        int lastSpd = -1;
        int lastSpc = -1;
        int possibleHP = 0;
        int possibleAtk = 0;
        int possibleDef = 0;
        int possibleSpd = 0;
        int possibleSpc = 0;
        for (int i = 0; i < 16; ++i) {
            if(!this.redHP[i]) {
                if (firstHP == -1) {
                    firstHP = i;
                }
                lastHP = i;
                ++possibleHP;
            }
            if(!this.redAtk[i]) {
                if (firstAtk == -1) {
                    firstAtk = i;
                }
                lastAtk = i;
                ++possibleAtk;
            }
            if(!this.redDef[i]) {
                if (firstDef == -1) {
                    firstDef = i;
                }
                lastDef = i;
                ++possibleDef;
            }
            if(!this.redSpd[i]) {
                if (firstSpd == -1) {
                    firstSpd = i;
                }
                lastSpd = i;
                ++possibleSpd;
            }
            if(!this.redSpc[i]) {
                if (firstSpc == -1) {
                    firstSpc = i;
                }
                lastSpc = i;
                ++possibleSpc;
            }
        }
        this.updateToto(0, firstHP, lastHP, possibleHP);
        this.updateToto(1, firstAtk, lastAtk, possibleAtk);
        this.updateToto(2, firstDef, lastDef, possibleDef);
        this.updateToto(3, firstSpd, lastSpd, possibleSpd);
        this.updateToto(4, firstSpc, lastSpc, possibleSpc);
    }

    public void removeStat(int column, int index) {
        this.calc.removeStat(column, index);
    }

    public void reset() {
        this.calc.reset();
        for (int i = 0; i < 16; ++i) {
            this.redHP[i] = false;
            this.redAtk[i] = false;
            this.redDef[i] = false;
            this.redSpd[i] = false;
            this.redSpc[i] = false;
        }
        this.hpdv = -1;
        this.atkdv = -1;
        this.defdv = -1;
        this.spddv = -1;
        this.spcdv = -1;
        this.labelHPDV.setText("?");
        this.labelHPDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        this.labelAtkDV.setText("?");
        this.labelAtkDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        this.labelDefDV.setText("?");
        this.labelDefDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        this.labelSpdDV.setText("?");
        this.labelSpdDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
        this.labelSpcDV.setText("?");
        this.labelSpcDV.setFont(new Font(this.totoDVNumbersFont, this.totoDVNumbersFontExtra, this.totoDVNumbersFontSizeBig));
    }

    public void setInternational(boolean international) {
        this.international = international;
    }

    public void setGame(boolean isCrystal) {
        this.isCrystal = isCrystal;
    }

    private void about() {
        String s = "<html><body><p style=\"font-family:'arial';font-size:9px;width:405px\">Pok\u00e9mon GSC DV Helper<br>Version: " + this.version + "<br><br>This is a GSC helper program developed by <a href='http://www.twitch.tv/entrpntr'>entrpntr</a>, derived from the Yellow Helper program designed by <a href='http://www.twitch.tv/Dailyleaf'>Dailyleaf</a>. The goal is making it easier for Pok\u00e9mon Gold & Crystal glitchless speedrunners to figure out DVs and plan ahead based on this knowledge." + "<br><br>DV Calculation:" + "<br><br>Stats Panel:" + "<br>Click what you see ingame, the program will do the math and eliminate impossible combinations." + "<br>Select (in order) the trainer Pokémon and wild encounters you killed and the program will keep track of your experience and stat experience automatically."  + "<br><br>Questions:" + "<br>If you have any questions, feel free to contact entrpntr on the PSR forums or on discord. The thread for GSC DV Helper can be found <a href='http://forums.pokemonspeedruns.com/viewtopic.php?f=116&t=512'>here</a>." + "<br><br>entrpntr's Credits:" + "<br><a href='http://www.twitch.tv/Dailyleaf'>Dailyleaf</a> - For making the original Red Helper and Yellow Helper programs." + "<br><br><em>Daily's Credits:" + "<br><a href='http://www.twitch.tv/BobChao87'>BobChao87</a> - For giving me the idea of how to make the DV calculator." + "<br><a href='http://www.twitch.tv/ExarionU'>ExarionU</a> - For providing the <a href='https://docs.google.com/spreadsheets/d/1mc4MIi2FWYsAoft1srthtEP--Oo36TXddMhQbG1NnB0/edit?pli=1#gid=0'>late-game strategy chart</a> that is used." + "<br><a href='http://www.twitch.tv/Masteri_Mori'>Masteri_Mori</a> - For providing a <a href='http://pastebin.com/Dnw1PE2U'>full list</a> of all possible DV combinations. Which helped me test my code. There are 364 possibilities missing in that list." + "<br><a href='http://www.twitch.tv/Masteri_Mori'>HRoll</a> - For making the amazing <a href='https://github.com/HRoll/poke-router'>RouteOne</a> pogram. It gave me a great insight in how all the calculations are done." + "<br><br>Daily's Shoutouts:" + "<br>Shoutouts to all the people in the Pok\u00e9mon Red skype-group for giving me feedback. Especially <a href='http://www.twitch.tv/dabomstew'>dabomstew</a> for some great help explaining how the calculations were done in RouteOne.</em>";
        JEditorPane ep = new JEditorPane();
        ep.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
        ep.setEditable(false);
        ep.setBackground(null);
        int caretPosition = ep.getCaretPosition();
        ep.setText(s);
        ep.setAlignmentX(0.0f);
        ep.addHyperlinkListener(new HyperlinkListener(){

            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED && Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        ep.setCaretPosition(Math.min(caretPosition, s.length()));
        JScrollPane sp = new JScrollPane(ep);
        sp.setPreferredSize(new Dimension(550, 300));
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.setBorder(null);
        JOptionPane.showMessageDialog(this, sp, "About", JOptionPane.PLAIN_MESSAGE);
    }

    static void changeLastButtonPressed(HelperFrame helperFrame, JLabel jLabel) {
        helperFrame.lastButtonPressed = jLabel;
    }

    static void changeTotoBackgroundColor(HelperFrame helperFrame, Color color) {
        helperFrame.totoBackgroundColor = color;
    }

    static void changeTotoTitleColor(HelperFrame helperFrame, Color color) {
        helperFrame.totoTitleColor = color;
    }

    static void changeTotoColumnHeadersColor(HelperFrame helperFrame, Color color) {
        helperFrame.totoColumnHeadersColor = color;
    }

    static void changeTotoDVNumbersColor(HelperFrame helperFrame, Color color) {
        helperFrame.totoDVNumbersColor = color;
    }

    public class PreviewPane
    extends JPanel {
        private static final long serialVersionUID = 3424012701561505236L;
        private JLabel labelBackground;

        public PreviewPane() {
            super(new GridLayout(0, 1));
            this.setBounds(10, 365, 200, 100);
            this.labelBackground = new JLabel("N", SwingConstants.CENTER);
            this.labelBackground.setPreferredSize(new Dimension(50, 50));
            this.labelBackground.setOpaque(true);
            this.add(this.labelBackground);
        }

        @Override
        public void setForeground(Color c) {
            super.setForeground(c);
            if (this.labelBackground != null && c != null) {
                if (HelperFrame.this.lastButtonPressed == HelperFrame.this.labelButtonTotoBackgroundColor) {
                    this.labelBackground.setFont(new Font((String)HelperFrame.this.comboBoxTotoTitleFont.getSelectedItem(), HelperFrame.this.comboBoxTotoTitleFontExtra.getSelectedIndex(), (Integer)HelperFrame.this.spinnerTotoTitleSize.getValue()));
                    this.labelBackground.setForeground(HelperFrame.this.labelButtonTotoTitleColor.getBackground());
                    this.labelBackground.setBackground(c);
                    this.labelBackground.setPreferredSize(this.getTextSize());
                    this.labelBackground.setText(HelperFrame.this.textFieldTotoTitleText.getText());
                } else if (HelperFrame.this.lastButtonPressed == HelperFrame.this.labelButtonTotoTitleColor) {
                    this.labelBackground.setFont(new Font((String)HelperFrame.this.comboBoxTotoTitleFont.getSelectedItem(), HelperFrame.this.comboBoxTotoTitleFontExtra.getSelectedIndex(), (Integer)HelperFrame.this.spinnerTotoTitleSize.getValue()));
                    this.labelBackground.setForeground(c);
                    this.labelBackground.setBackground(HelperFrame.this.labelButtonTotoBackgroundColor.getBackground());
                    this.labelBackground.setPreferredSize(this.getTextSize());
                    this.labelBackground.setText(HelperFrame.this.textFieldTotoTitleText.getText());
                } else if (HelperFrame.this.lastButtonPressed == HelperFrame.this.labelButtonTotoColumnHeadersColor) {
                    this.labelBackground.setFont(new Font((String)HelperFrame.this.comboBoxTotoColumnHeadersFont.getSelectedItem(), HelperFrame.this.comboBoxTotoColumnHeadersFontExtra.getSelectedIndex(), (Integer)HelperFrame.this.spinnerTotoColumnHeadersFontSize.getValue()));
                    this.labelBackground.setForeground(c);
                    this.labelBackground.setBackground(HelperFrame.this.labelButtonTotoBackgroundColor.getBackground());
                    this.labelBackground.setPreferredSize(this.getTextSize());
                    this.labelBackground.setText("SPC");
                } else if (HelperFrame.this.lastButtonPressed == HelperFrame.this.labelButtonTotoDVNumbersColor) {
                    this.labelBackground.setFont(new Font((String)HelperFrame.this.comboBoxTotoDVNumbersFont.getSelectedItem(), HelperFrame.this.comboBoxTotoDVNumbersFontExtra.getSelectedIndex(), (Integer)HelperFrame.this.spinnerTotototoDVNumbersFontSizeSmall.getValue()));
                    this.labelBackground.setForeground(c);
                    this.labelBackground.setBackground(HelperFrame.this.labelButtonTotoBackgroundColor.getBackground());
                    this.labelBackground.setPreferredSize(this.getTextSize());
                    this.labelBackground.setText("10/15");
                }
            }
        }

        private Dimension getTextSize() {
            Dimension size = new Dimension();
            FontMetrics fm = this.labelBackground.getFontMetrics(this.labelBackground.getFont());
            size.width = 0;
            size.width = HelperFrame.this.lastButtonPressed == HelperFrame.this.labelButtonTotoBackgroundColor || HelperFrame.this.lastButtonPressed == HelperFrame.this.labelButtonTotoTitleColor ? fm.stringWidth(HelperFrame.this.textFieldTotoTitleText.getText()) + 15 : (HelperFrame.this.lastButtonPressed == HelperFrame.this.labelButtonTotoColumnHeadersColor ? fm.stringWidth("SPC") + 15 : fm.stringWidth("10/15") + 15);
            size.height = fm.getHeight() + 5;
            return size;
        }
    }

}

