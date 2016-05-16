package scripts;

/**
 *
 * @author Gi
 */
public interface FormatterScript extends Script {
    
    /**
     * 
     * @param objs to format
     * @return formatted String
     */
    public String format(final Object... objs);
}
