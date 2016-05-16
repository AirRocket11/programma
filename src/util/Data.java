package util;

import java.util.Arrays;

/**
 * Object for holding data.
 *
 * @author Gi
 */
public class Data {

    private final double pressure;
    private final int angle;
    private final double[] ranges;

    /**
     *
     * @param pressure in bar
     * @param angle in degrees
     * @param ranges in m
     */
    public Data(final double pressure, final int angle, final double[] ranges) {
        this.pressure = pressure;
        this.angle = angle;
        this.ranges = ranges;
    }

    /**
     *
     * @return pressure in bar
     */
    public double pressure() {
        return this.pressure;
    }

    /**
     *
     * @return angle in degrees
     */
    public int angle() {
        return this.angle;
    }

    /**
     *
     * @return ranges in m
     */
    public double[] ranges() {
        return this.ranges;
    }

    /**
     *
     * @return average of ranges in m
     */
    public double avgRange() {
        double total = 0;
        for (double d : this.ranges) {
            total += d;
        }
        return total / this.ranges.length;
    }

    /**
     *
     * @return standard deviation of ranges in m
     */
    public double deviation() {
        double avg = this.avgRange();
        double sum = 0;
        for (double d : this.ranges) {
            double dif = d - avg;
            sum += (dif * dif);
        }
        sum /= (this.ranges.length - 1d);
        return Math.sqrt(sum);
    }

    @Override
    public String toString() {
        return String.format("%,.2fbar & %d° > %,.2fm (avg) %n%s", this.pressure, this.angle, this.avgRange(), Arrays.toString(this.ranges));
    }
}
