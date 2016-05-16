package scripts;

/**
 *
 * @author Gi
 */
public interface PhysicsScript extends Script {

    /**
     * @param range [m]
     * @param angle [°]
     * @return speed [m/s]
     */
    public double theoreticalSpeed(final double range, final int angle);
}
