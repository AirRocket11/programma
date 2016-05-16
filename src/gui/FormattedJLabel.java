package gui;

import javax.swing.JLabel;
import scripts.FormatterScript;
import util.lazy.LazyFactory;
import util.lazy.LazyListener;

/**
 *
 * @author Gi
 */
public class FormattedJLabel extends JLabel implements LazyListener<FormatterScript>{

    private static final long serialVersionUID = 5749957189093329528L;

    private FormatterScript formatter;
    private Object[] lastObjs;

    public FormattedJLabel(final LazyFactory<FormatterScript> factory, final Object... objs) {
        this.formatter = factory.get();
        this.lastObjs = objs;
        factory.addListener(FormattedJLabel.this);
    }

    public final void changeValue(final Object... objs) {
        this.lastObjs = objs;
        super.setText(this.formatter.format(objs));
    }

    public void loaderChanged(final FormatterScript formatter) {
        this.formatter = formatter;
        this.changeValue(this.lastObjs);
    }
}
