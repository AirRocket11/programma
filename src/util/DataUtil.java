package util;

import java.util.Arrays;

/**
 *
 * @author Gi
 */
public class DataUtil {

    private DataUtil() {
        // static class
    }

    public static String[] getSimpleHeader() {
        return new String[] {
            "Druk (bar)",
            "Hoek (°)",
            "Gemiddelde afstand (m)"
        };
    }
    
    public static Object[] toSimpleRow(final Data data) {
        if (data != null) {
            return new Object[]{
                data.pressure(),
                data.angle(),
                data.avgRange(),
                Arrays.toString(data.ranges())
            };
        }
        return null;
    }
}
