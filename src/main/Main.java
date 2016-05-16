package main;

import gui.CustomLAF;
import gui.RocketFrame;
import java.awt.EventQueue;
import java.util.logging.Logger;
import util.LogHandler;
import util.lazy.LazyFactories;

/**
 *
 * @author Gi
 */
public class Main {

    public static final Logger LOGGER = Logger.getLogger(RocketFrame.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        /**
         * Add a simple logging handler that will show logs as an internal
         * message dialog.
         */
        final LogHandler errorHandler = new LogHandler();
        LOGGER.addHandler(errorHandler);
        CustomLAF.set();
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                final RocketFrame frame = new RocketFrame();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                errorHandler.setFrame(frame);
                
                LazyFactories.DATA.addListener(frame);
                } catch (Throwable t) {
                    t.printStackTrace(System.err);
                }
            }
        });
    }
    
    private Main() {
        // no constructor
    }
}
