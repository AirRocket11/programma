package gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author Gi
 */
public class TitleLabel extends JLabel {

    private static final long serialVersionUID = 7250284598859781593L;

    private final Glyphicon icon;
    
    public TitleLabel(final String title, final Glyphicon icon) {
        super();
        this.icon = icon;
        super.setForeground(CustomLAF.TEXT_COLOR_ACCENT);
        super.setBackground(CustomLAF.BG_COLOR_ACCENT);
        super.setOpaque(true);
        super.setFont(super.getFont().deriveFont(20f));
        super.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, CustomLAF.BORDER_COLOR));
        super.setHorizontalAlignment(SwingConstants.CENTER);
        this.setText(title);
    }
    
    @Override
    public final void setText(final String text) {
        super.setText("<html><font size=\"5.5\">" + (this.icon == null ? "" : this.icon.getIcon()) + "</font> " + text);
    }
}
