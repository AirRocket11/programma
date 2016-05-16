package gui.rounded;

import gui.CustomLAF;
import gui.dialog.MessageDialog;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import gui.Glyphicon;
import util.Pair;
import util.lazy.LazyLoader;

/**
 *
 * @author Gi
 */
public abstract class RoundedJFrame extends JFrame {

    private static final long serialVersionUID = 1040601459418567304L;

    private final Handle handle;
    private final JPanel content;
    protected final JPanel bottom;

    protected RoundedJFrame() {
        super();
        final JPanel[] panels = RoundedUtil.initRoundedWindow(RoundedJFrame.this);
        this.handle = (Handle) panels[0];
        this.content = panels[1];
        this.bottom = panels[2];
    }

    /*
     * Override the default add and setLayout methods to effect the 
     * content panel. 
     *
     * When the content panel is null, use the default implementation to 
     * prevent NullPointerExceptions during initializiation of the super() 
     * call.
     */
    @Override
    public Component add(final Component comp) {
        if (this.content != null) {
            return this.content.add(comp);
        } else {
            return super.add(comp);
        }
    }

    @Override
    public Component add(final Component comp, final int index) {
        if (this.content != null) {
            return this.content.add(comp, index);
        } else {
            return super.add(comp, index);
        }
    }

    @Override
    public Component add(final String name, final Component comp) {
        if (this.content != null) {
            return this.content.add(name, this);
        } else {
            return super.add(name, comp);
        }
    }

    @Override
    public void add(final Component comp, final Object contraints) {
        if (this.content != null) {
            this.content.add(comp, contraints);
        } else {
            super.add(comp, contraints);
        }
    }

    @Override
    public void add(final Component comp, final Object contraints, final int index) {
        if (this.content != null) {
            this.content.add(comp, contraints, index);
        } else {
            super.add(comp, contraints, index);
        }
    }

    @Override
    public void setLayout(final LayoutManager manager) {
        if (this.content != null) {
            this.content.setLayout(manager);
        } else {
            super.setLayout(manager);
        }
    }

    @Override
    public void setTitle(final String title) {
        super.setTitle(title);
        this.handle.lblTitle.setText(title);
    }

    @Override
    public void setSize(final int width, final int height) {
        super.setSize(width, height);
        super.setShape(new RoundRectangle2D.Double(0, 0, width, height, 15, 15));
    }

    @Override
    public void pack() {
        super.pack();
        this.setSize(this.getWidth() + 10, this.getHeight() + 10);
    }

    @Override
    public void setIconImage(final Image image) {
        super.setIconImage(image);
        this.handle.lblTitleIcon.setIcon(new ImageIcon(image.getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
    }

    public void setIconImage(final Glyphicon icon) {
        this.handle.lblTitleIcon.setText("<html>" + icon.getIcon() + "</html>");
        this.handle.lblTitleIcon.setIcon(null);
    }

    public void setPopUp(final Pair<String, LazyLoader<RoundedJDialog>>... items) {
        if (items == null || items.length == 0) {
            if (this.handle.popUp != null) {
                this.handle.popUp.setVisible(false);
                this.handle.popUp = null;
            }
        } else {
            final JPopupMenu popup = new JPopupMenu();
            for (final Pair<String, LazyLoader<RoundedJDialog>> item : items) {
                if (item.getRight() == null) {
                    popup.add(new JSeparator());
                } else {
                    JMenuItem menuItem = new JMenuItem(item.getLeft());
                    menuItem.setForeground(CustomLAF.TEXT_COLOR_ACCENT);
                    menuItem.getAccessibleContext().setAccessibleDescription(item.getLeft());
                    menuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(final ActionEvent ae) {
                            try {
                                final RoundedJDialog dialog = item.getRight().get();
                                dialog.setLocationRelativeTo(RoundedJFrame.this);
                                dialog.setVisible(true);
                            } catch (final Throwable t) {
                                t.printStackTrace(System.err);
                                main.Main.LOGGER.log(Level.SEVERE, null, t);
                            }
                        }
                    });
                    popup.add(menuItem);
                }
            }
            this.handle.popUp = popup;
        }
    }

    public MessageDialog showInternalMessageDialog(final String title, final Object body) {
        return this.showInternalMessageDialog(null, title, body);
    }

    public MessageDialog showInternalMessageDialog(final Glyphicon icon, final String title, final Object body) {
        final MessageDialog dialog = new MessageDialog(this, icon, title, body);
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                dialog.setLocationRelativeTo(RoundedJFrame.this);
                dialog.setVisible(true);
            }
        });
        return dialog;
    }
}
