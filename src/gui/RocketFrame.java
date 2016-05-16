package gui;

import gui.dialog.TrajectoryDialog;
import gui.dialog.DataDialog;
import gui.dialog.SourceDialog;
import gui.rounded.RoundedJDialog;
import gui.rounded.RoundedJFrame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import scripts.DataScript;
import util.Pair;
import util.lazy.LazyFactories;
import util.lazy.LazyInitializer;
import util.lazy.LazyListener;
import util.lazy.LazyLoader;

/**
 *
 * @author Gi
 */
public class RocketFrame extends RoundedJFrame implements LazyListener<DataScript> {

    private static final long serialVersionUID = -5575776622261921055L;

    private final JComboBox<String> cmbRocketsIn;
    private final JSlider sldrAfstandIn;
    private final FormattedJLabel lblAfstandIn, lblHoekOut, lblSpeedOut, lblPressureOut;

    public RocketFrame() {
        super();
        super.setTitle(LazyFactories.PROPERTIES_TEXT.get().getProperty("FRAME_TITLE"));
        this.setIconImage(new ImageIcon("resources/rocket-icon.png").getImage());

        super.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 100;
        c.weighty = 100;
        c.insets.bottom = c.insets.left = c.insets.right = c.insets.top = 5;

        c.gridx = c.gridy = 0;
        c.gridwidth = 2;
        JTextArea txtInfo = new JTextArea(LazyFactories.PROPERTIES_TEXT.get().getProperty("FRAME_INFO"));
        txtInfo.setWrapStyleWord(true);
        txtInfo.setLineWrap(true);
        txtInfo.setEditable(false);
        txtInfo.setFocusable(false);
        txtInfo.setBorder(new EmptyBorder(10, 0, 10, 0));
        super.add(txtInfo, c);

        this.addSection("Variabelen", Glyphicon.EDIT, c);

        this.addInfoLabel(new JLabel("Raket"), c);

        c.gridx++;
        this.cmbRocketsIn = new JComboBox<String>();
        for (String rocket : LazyFactories.DATA.get().getData().keySet()) {
            this.cmbRocketsIn.addItem(rocket);
        }
        this.cmbRocketsIn.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                RocketFrame.this.recalcPressure();
            }
        });
        this.add(this.cmbRocketsIn, c);

        this.lblAfstandIn = new FormattedJLabel(LazyFactories.FORMATTER_RANGE_IN, 200);
        this.addInfoLabel(this.lblAfstandIn, c);

        c.gridx++;
        this.sldrAfstandIn = new JSlider(200, 800, 200);
        Dictionary dictonary = this.sldrAfstandIn.createStandardLabels(100);
        Enumeration<JLabel> enumeration = dictonary.elements();
        while (enumeration.hasMoreElements()) {
            JLabel next = enumeration.nextElement();
            next.setText(Integer.parseInt(next.getText()) / 100 + "m");
        }
        this.sldrAfstandIn.setLabelTable(dictonary);
        this.sldrAfstandIn.setPaintLabels(true);
        this.sldrAfstandIn.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                RocketFrame.this.lblAfstandIn.changeValue(((JSlider) e.getSource()).getValue());
                RocketFrame.this.recalcPressure();
            }
        });
        super.add(this.sldrAfstandIn, c);

        this.addSection("Experimentele waarden", Glyphicon.SCALE, c);

        this.addInfoLabel(new JLabel("Druk"), c);

        c.gridx++;
        this.lblPressureOut = new FormattedJLabel(LazyFactories.FORMATTER_PRESSURE_OUT, 0);
        super.add(this.lblPressureOut, c);

        this.addInfoLabel(new JLabel("Hoek"), c);

        c.gridx++;
        this.lblHoekOut = new FormattedJLabel(LazyFactories.FORMATTER_ANGLE_OUT, 0);
        super.add(this.lblHoekOut, c);

        this.addSection("Theoretisch waarden", Glyphicon.BLACKBOARD, c);

        this.addInfoLabel(new JLabel("Beginsnelheid"), c);

        c.gridx++;
        this.lblSpeedOut = new FormattedJLabel(LazyFactories.FORMATTER_SPEED_OUT, 0);
        this.add(this.lblSpeedOut, c);

//        c.gridy++;
//        c.fill = GridBagConstraints.NONE;
//        c.anchor = GridBagConstraints.EAST;
//        final JButton btnHerlaad = new JButton("Herlaad");
//        btnHerlaad.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                LazyFactories.reload();
//            }
//        });
//        super.add(btnHerlaad, c);
        super.pack();
        super.setSize(super.getWidth() + 100, super.getHeight());
        super.setPopUp(
//                new Pair<String, LazyLoader<RoundedJDialog>>("De kogelbaan – theoretisch", new LazyLoader<RoundedJDialog>() {
//
//                    @Override
//                    public RoundedJDialog create() {
//                        return new TrajectoryDialog(RocketFrame.this, "De kogelbaan");
//                    }
//                }),
                new Pair<String, LazyLoader<RoundedJDialog>>("Test data", new LazyLoader<RoundedJDialog>(new LazyInitializer<RoundedJDialog>() {

                    @Override
                    public void postInit(RoundedJDialog t) {
                        LazyFactories.DATA.addListener((DataDialog) t);
                    }
                }) {
                    @Override
                    public RoundedJDialog create() {
                        return new DataDialog(RocketFrame.this);
                    }
                }),
                new Pair<String, LazyLoader<RoundedJDialog>>(null, null),
                new Pair<String, LazyLoader<RoundedJDialog>>("Source code viewer", new LazyLoader<RoundedJDialog>() {

                    @Override
                    public RoundedJDialog create() {
                        return new SourceDialog(RocketFrame.this);
                    }
                })
        );

        this.recalcPressure();
    }

    private void addSection(final String title, final Glyphicon icon, GridBagConstraints c) {
        final int widht = 2;

        double tmpWeighty = c.weighty;
        int tmpWidht = c.gridwidth;
        Insets i = new Insets(c.insets.top, c.insets.left, c.insets.bottom, c.insets.right);

        c.insets.left = c.insets.right = 0;
        c.gridy++;
        c.gridwidth = widht;
        c.gridx = 0;
        c.weighty = 0;
        super.add(new TitleLabel(title, icon), c);
        c.gridwidth = 1;
        c.weighty = tmpWeighty;
        c.insets = i;

    }

    private void addInfoLabel(final JLabel label, GridBagConstraints c) {
        c.gridy++;
        c.gridx = 0;
        c.weightx = 0;
        super.add(label, c);
        c.weightx = 100;
    }

    @Override
    public void loaderChanged(final DataScript loader) {
        this.recalcPressure();
    }

    private void recalcPressure() {
        final Pair<Integer, Double> calc = LazyFactories.DATA.get().calculate(
                String.valueOf(this.cmbRocketsIn.getSelectedItem()),
                this.sldrAfstandIn.getValue() / 100d
        );
        this.lblPressureOut.changeValue(calc.getRight());
        this.lblHoekOut.changeValue(calc.getLeft());
        this.lblSpeedOut.changeValue(
                LazyFactories.PHYSICS.get().theoreticalSpeed(this.sldrAfstandIn.getValue() / 100d, calc.getLeft())
        );
    }
}
