/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.dialog;

import gui.rounded.RoundedJDialog;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import gui.Glyphicon;

/**
 *
 * @author Gi
 */
public class MessageDialog extends RoundedJDialog {

    private static final long serialVersionUID = -8704709979990875506L;

    public MessageDialog(JFrame parent, Glyphicon icon, String title, Object body) {
        super(parent, true);
        if (icon != null) {
            this.setIconImage(icon);
        }
        this.setTitle(title);

        super.setLayout(new GridLayout(1, 1));
        Component c;
        if (body instanceof Component) {
            c = (Component) body;
        } else {
            final JTextArea txtInfo = new JTextArea(String.valueOf(body));
            txtInfo.setWrapStyleWord(true);
            txtInfo.setLineWrap(true);
            txtInfo.setEditable(false);
            txtInfo.setFocusable(false);
            txtInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            final JScrollPane scrollPane = new JScrollPane(txtInfo);
            scrollPane.setBorder(null);
            c = scrollPane;
        }
        super.add(c);
        
        super.pack();
        super.setSize(650, Math.min(400, super.getHeight()));
    }
}
