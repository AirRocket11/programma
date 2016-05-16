package gui.dialog;

import util.Data;
import scripts.DataScript;
import gui.rounded.RoundedJDialog;
import gui.rounded.RoundedJFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import gui.Glyphicon;
import java.awt.FlowLayout;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import util.DataUtil;
import util.lazy.LazyFactories;
import util.lazy.LazyListener;

/**
 *
 * @author Gi
 */
public class DataDialog extends RoundedJDialog implements LazyListener<DataScript> {

    private static final long serialVersionUID = 2514776413929740640L;

    private final JTabbedPane tbdPane;
    private final DefaultTableModel[] models;

    public DataDialog(final RoundedJFrame parent) {
        super(parent, true);
        super.setTitle("Testdata");
        super.setIconImage(Glyphicon.STATS);
        super.setLayout(new BorderLayout());
        JTextArea txtInfo = new JTextArea(LazyFactories.PROPERTIES_TEXT.get().getProperty("DATA_INFO"));
        txtInfo.setWrapStyleWord(true);
        txtInfo.setLineWrap(true);
        txtInfo.setEditable(false);
        txtInfo.setFocusable(false);
        txtInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        super.add(txtInfo, BorderLayout.NORTH);

        this.tbdPane = new JTabbedPane();
        super.add(this.tbdPane, BorderLayout.CENTER);

        final Map<String, Data[]> data = LazyFactories.DATA.get().getData();
        this.models = new DefaultTableModel[data.size()];
        int i = 0;
        for (final String rocket : data.keySet()) {
            DefaultTableModel model = new DefaultTableModel() {

                private static final long serialVersionUID = 2793957061432039023L;

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return getValueAt(0, columnIndex).getClass();
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            this.models[i++] = model;
            model.addColumn("Druk (bar)");
            model.addColumn("Hoek (°)");
            model.addColumn("Gemiddelde afstand (m)");
            model.addColumn("Alle waarden (m)");

            JTable table = new JTable(model);
            table.setAutoCreateRowSorter(true);

            this.tbdPane.addTab(rocket, new JScrollPane(table));
        }

        super.bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton btnReload = new JButton("Reload data");
        btnReload.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    LazyFactories.DATA.reload();
                } catch (Throwable t) {
                    main.Main.LOGGER.log(Level.SEVERE, null, t);
                }
            }
        });
        super.bottom.add(btnReload);
        super.setSize(650, 400);
    }

    @Override
    public void loaderChanged(final DataScript loader) {
        clearTables();
        try {
            Map<String, Data[]> data = loader.getData();
            int i = 0;
            for (Entry<String, Data[]> entry : data.entrySet()) {
                DefaultTableModel model = this.models[i++];
                Data[] d = loader.getData(entry.getKey());
                if (d != null && d.length != 0) {
                    for (Data d2 : d) {
                        if (d2 != null) {
                            model.addRow(DataUtil.toSimpleRow(d2));
                        }
                    }
                }
            }
        } catch (final Throwable t) {
            main.Main.LOGGER.log(Level.SEVERE, null, t);
        }
    }

    private void clearTables() {
        // remove all rows
        for (DefaultTableModel model : this.models) {
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
        }
    }
}
