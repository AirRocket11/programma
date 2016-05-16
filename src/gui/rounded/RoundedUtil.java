package gui.rounded;

import gui.CustomLAF;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Gi
 */
class RoundedUtil {

    private RoundedUtil() {
        // static class
    }

    /**
     * Utility function to initialize a rounded Frame or Dialog
     *
     * @param w the window
     * @return Array of Handle, JPanel content, JPanel bottom
     */
    public static JPanel[] initRoundedWindow(final Window w) {
        if (w instanceof JDialog) {
            ((JDialog) w).setUndecorated(true);
            ((JDialog) w).setResizable(false);
            ((JDialog) w).setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        } else if (w instanceof JFrame) {
            ((JFrame) w).setUndecorated(true);
            ((JFrame) w).setResizable(false);
            ((JFrame) w).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }

        final JPanel handle = new Handle(w);

        final JPanel content = new JPanel();
        content.setBorder(BorderFactory.createLineBorder(CustomLAF.BORDER_COLOR, 1));
        content.setBackground(CustomLAF.BG_COLOR);

        final JPanel bottom = new JPanel();
        bottom.setBackground(CustomLAF.BG_COLOR_ACCENT);

        w.setLayout(new BorderLayout());
        w.add(handle, BorderLayout.NORTH);
        w.add(content, BorderLayout.CENTER);
        w.add(bottom, BorderLayout.SOUTH);

        return new JPanel[]{handle, content, bottom};
    }
}
