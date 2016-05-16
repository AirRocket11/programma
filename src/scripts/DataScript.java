package scripts;

import java.util.Map;
import util.Data;
import util.Pair;

/**
 *
 * @author Gi
 */
public interface DataScript extends Script {

    /**
     * 
     * @return The test data
     */
    public Map<String, Data[]> getData();
    
    /**
     * @param rocketId id of the rocket
     * @return The test data for the specified rocket
     */
    public Data[] getData(final String rocketId);

    /**
     * @param rocketId Id of the rocket
     * @param range [m]
     * @return pressure [bar]
     */
    public Pair<Integer, Double> calculate(final String rocketId, final double range);
}
