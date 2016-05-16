/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.rounded;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Point;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import gui.CustomLAF;
import gui.Glyphicon;

/**
 *
 * @author Gi
 */
class Handle extends JPanel {

    private static final long serialVersionUID = -1813653584529625340L;

    final JLabel lblTitle, lblTitleIcon;
    JPopupMenu popUp;

    Handle(final Window window) {
        super();
        super.setBackground(CustomLAF.BG_COLOR_ACCENT);
        super.addMouseMotionListener(new MouseAdapter() {

            private Point draggingAnchor = null;

            @Override
            public void mouseMoved(final MouseEvent e) {
                this.draggingAnchor = new Point(e.getX() + Handle.this.getX(), e.getY() + Handle.this.getY());
            }

            @Override
            public void mouseDragged(final MouseEvent e) {
                if (this.draggingAnchor != null) {
                    window.setLocation(e.getLocationOnScreen().x - this.draggingAnchor.x, e.getLocationOnScreen().y - this.draggingAnchor.y);
                }
            }
        });

        this.lblTitleIcon = new JLabel();
        this.lblTitleIcon.setForeground(CustomLAF.TEXT_COLOR_ACCENT);
        this.lblTitleIcon.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(final MouseEvent me) {
                showPopup(me);
            }

            @Override
            public void mousePressed(final MouseEvent me) {
                showPopup(me);
            }

            private void showPopup(MouseEvent e) {
                if (Handle.this.popUp != null) {
                    final Component comp = e.getComponent();
                    Handle.this.popUp.show(comp, comp.getX() - (comp.getWidth() / 2) + 5, comp.getY() + comp.getHeight());
                }
            }
        });

        this.lblTitle = new JLabel();
        this.lblTitle.setForeground(CustomLAF.TEXT_COLOR_ACCENT);

        HandleControl minimizeControl = null;
        if (window instanceof JFrame) {
            minimizeControl = new HandleControl(Glyphicon.MINUS, new Runnable() {

                @Override
                public void run() {
                    ((JFrame) window).setState(Frame.ICONIFIED);
                }
            });
        }

        final HandleControl closeControl = new HandleControl(Glyphicon.REMOVE, new Runnable() {
            @Override
            public void run() {
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }
        });

        super.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 5, 1, 0);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.ipady = 2;
        super.add(this.lblTitleIcon, c);

        c.insets = new Insets(2, 5, 3, 5);
        c.gridx++;
        c.weightx = 10;
        super.add(this.lblTitle, c);

        c.weightx = 0;
        c.ipadx = 5;
        if (minimizeControl != null) {
            c.gridx++;
            super.add(minimizeControl, c);
        }

        c.gridx++;
        super.add(closeControl, c);
    }

    private static class HandleControl extends JLabel {

        private static final long serialVersionUID = -1292113852300472143L;

        private HandleControl(final Glyphicon icon, final Runnable runnable) {
            super("<html>" + icon.getIcon() + "</html>");
            super.setForeground(CustomLAF.TEXT_COLOR_ACCENT);
            super.setCursor(new Cursor(Cursor.HAND_CURSOR));
            super.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    EventQueue.invokeLater(runnable);
                }
            });
        }
    }
}
