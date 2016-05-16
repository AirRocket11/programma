package gui.dialog;

import gui.rounded.RoundedJDialog;
import javax.swing.JFrame;

/**
 *
 * @author Gi
 */
public class TrajectoryDialog extends RoundedJDialog {

    private static final long serialVersionUID = -664892281535269062L;

    public TrajectoryDialog(final JFrame parent, final String title) {
        super(parent, true);
        super.setTitle(title);
        super.setSize(500, 200);
    }
}
