package util;

import gui.Glyphicon;
import gui.rounded.RoundedJFrame;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author Gi
 */
public class LogHandler extends Handler {

    private RoundedJFrame frame;

    public LogHandler() {
        super.setFormatter(new SimpleFormatter());
        super.setLevel(Level.WARNING);
    }

    public void setFrame(final RoundedJFrame frame) {
        this.frame = frame;
    }

    @Override
    public void publish(final LogRecord record) {
        final String msg = this.getFormatter().format(record);
        if (this.frame != null && this.frame.isVisible()) {
            frame.showInternalMessageDialog(Glyphicon.WARNING_SIGN, "Error logger - " + record.getLevel().getLocalizedName(), msg);
        } else {
            JOptionPane.showMessageDialog(null, msg);
        }
        if (record.getLevel() == Level.SEVERE) {
            System.exit(0);
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }
}
