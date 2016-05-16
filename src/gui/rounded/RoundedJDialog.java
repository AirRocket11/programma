package gui.rounded;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.geom.RoundRectangle2D;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import gui.Glyphicon;

/**
 *
 * @author Gi
 */
public abstract class RoundedJDialog extends JDialog {

    private static final long serialVersionUID = -826461293009319341L;

    private final Handle handle;
    private final JPanel content;
    protected final JPanel bottom;

    private boolean clearGlassOnDispose = true;

    protected RoundedJDialog(final JFrame parent, final boolean modal) {
        super(parent, modal);
        final JPanel[] panels = RoundedUtil.initRoundedWindow(RoundedJDialog.this);
        this.handle = (Handle) panels[0];
        this.content = panels[1];
        this.bottom = panels[2];
        super.setMinimumSize(new Dimension(100, 50));
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
    public Component add(final String name, final Component com) {
        if (this.content != null) {
            return this.content.add(name, this);
        } else {
            return super.add(name, com);
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
    
    public void setBorder(final Border border) {
        if (this.content != null) {
            this.content.setBorder(border);
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

    @Override
    public void setVisible(final boolean bln) {
        Window w = super.getOwner();
        if (bln && w != null && w instanceof JFrame) {
            JFrame myFrame = (JFrame) w;

            Component c = myFrame.getGlassPane();
            if (c == null || !(c instanceof ShadeGlass)) {
                c = new ShadeGlass(myFrame);
                myFrame.setGlassPane(c);
                this.clearGlassOnDispose = true;
            } else {
                this.clearGlassOnDispose = !c.isVisible();
            }
            c.setVisible(true);
            myFrame.setFocusable(false);
        }
        super.setVisible(bln);
    }

    @Override
    public void dispose() {
        Window w = super.getOwner();
        if (w != null && w instanceof JFrame) {
            JFrame myFrame = (JFrame) w;
            if (this.clearGlassOnDispose) {
                myFrame.getGlassPane().setVisible(false);
                myFrame.setFocusable(true);
            }
            
        }
        super.dispose();
    }

    public void setIconImage(final Glyphicon icon) {
        this.handle.lblTitleIcon.setText("<html>" + icon.getIcon() + "</html>");
    }
}
