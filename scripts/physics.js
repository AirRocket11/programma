function theoreticalSpeed(range, angle) {
    if (angle <= 0 || angle >= 90) {
        return -1;
    }
    return Math.sqrt(range * 9.81 / Math.sin(2 * (angle * (Math.PI / 180))));
}
