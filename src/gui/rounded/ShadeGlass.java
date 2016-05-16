package gui.rounded;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.ref.WeakReference;
import javax.swing.JFrame;
import javax.swing.JPanel;

class ShadeGlass extends JPanel {

    private static final long serialVersionUID = -4262324798054321635L;

    private final WeakReference<JFrame> weakOwner;

    public ShadeGlass(final JFrame owner) {
        super();
        super.setOpaque(false);
        super.setFocusable(false);
        this.weakOwner = new WeakReference<JFrame>(owner);
    }

    @Override
    public void paintComponent(final Graphics g) {
        JFrame strongOwner = weakOwner.get();
        if (strongOwner != null) {
            final int w = strongOwner.getWidth(), h = strongOwner.getHeight();
            super.setLocation(0, 0);
            super.setSize(w, h);
            g.setColor(new Color(0, 0, 0, .5f));
            g.fillRect(0, 0, w, h);
        }
    }
}
