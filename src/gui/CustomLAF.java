package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import javax.swing.Painter;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import main.Main;
import util.lazy.LazyFactories;

/**
 *
 * @author Gi
 */
public class CustomLAF {

    public static final Color TEXT_COLOR = getColor(LazyFactories.PROPERTIES_UI.get().getProperty("TEXT_COLOR"), Color.DARK_GRAY);
    public static final Color BG_COLOR = getColor(LazyFactories.PROPERTIES_UI.get().getProperty("BG_COLOR"), Color.WHITE);

    public static final Color TEXT_COLOR_ACCENT = getColor(LazyFactories.PROPERTIES_UI.get().getProperty("TEXT_COLOR_ACCENT"), Color.WHITE);
    public static final Color BG_COLOR_ACCENT = getColor(LazyFactories.PROPERTIES_UI.get().getProperty("BG_COLOR_ACCENT"), Color.DARK_GRAY);

    public static final Color BORDER_COLOR = BG_COLOR_ACCENT;

    public static void set() {
        // Load custom font
        Font latoFont = null;
        InputStream is = null;
        try {
            is = new FileInputStream("resources/Lato-Regular.ttf");
            latoFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(12f);
        } catch (FontFormatException ex) {
            Main.LOGGER.log(Level.WARNING, null, ex);
        } catch (IOException ex) {
            Main.LOGGER.log(Level.WARNING, null, ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignore) {
                }
            }
        }

        // Apply custom look and feel based on Nimbus.
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            final UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            if (latoFont != null) {
                defaults.put("defaultFont", latoFont);
            }
            defaults.put("smallFont", defaults.getFont("defaultFont").deriveFont(11f));

            //defaults.put("nimbusOrange", new Color(255, 44, 30));
            defaults.put("textForeground", TEXT_COLOR);
            defaults.put("MenuItem[Enabled].textForeground", TEXT_COLOR);
            defaults.put("MenuItem.contentMargins", new Insets(3, 12, 3, 13));
            defaults.put("PopupMenu[Enabled].backgroundPainter", new Painter() {

                public void paint(Graphics2D g, Object object, int width, int height) {
                    g.setColor(BG_COLOR_ACCENT);
                    g.fillRect(0, 0, ((Component) object).getWidth(), ((Component) object).getHeight());
                }
            });

            defaults.put("Panel.background", BG_COLOR);
            defaults.put("OptionPane.background", BG_COLOR);

            defaults.put("Button.background", BG_COLOR);
            defaults.put("Button.textForeground", TEXT_COLOR);
            defaults.put("Button[Disabled].backgroundPainter", defaults.get("Button[Enabled].backgroundPainter"));
            defaults.put("Button[Default+Focused+MouseOver].backgroundPainter", defaults.get("Button[Default+Pressed].backgroundPainter"));
            defaults.put("Button[Default+Focused].backgroundPainter", defaults.get("Button[Default+MouseOver].backgroundPainter"));
            defaults.put("Button[Focused+Pressed].backgroundPainter", defaults.get("Button[Default+Pressed].backgroundPainter"));
            defaults.put("Button[Focused].backgroundPainter", defaults.get("Button[Enabled].backgroundPainter"));
            defaults.put("Button[Focused+MouseOver].backgroundPainter", defaults.get("Button[Default].backgroundPainter"));

            defaults.put("SplitPane.contentMargins", new Insets(0, 0, 0, 0));
            defaults.put("SplitPane:SplitPaneDivider[Enabled].backgroundPainter", new Painter() {

                @Override
                public void paint(Graphics2D g, Object object, int width, int height) {
                    g.setColor(BG_COLOR_ACCENT);
                    g.fillRect(0, 0, ((Component) object).getWidth(), ((Component) object).getHeight());
                }
            });
        } catch (UnsupportedLookAndFeelException ex) {
            Main.LOGGER.log(Level.WARNING, null, ex);
        } catch (Exception ex) {
            Main.LOGGER.log(Level.WARNING, null, ex);
        }
    }

    private static Color getColor(final String name, final Color def) {
        try {
            return (Color) Class.forName("java.awt.Color").getField(name).get(null);
        } catch (ClassNotFoundException ignore) {
        } catch (NoSuchFieldException ignore) {
        } catch (SecurityException ignore) {
        } catch (IllegalArgumentException ignore) {
        } catch (IllegalAccessException ignore) {
        }
        return def;
    }
}
