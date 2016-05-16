function format(objects) {
    if (objects[0] < 0) {
        return "<html>Onmogelijke hoek<br/>Onmogelijke hoek</html>";
    }
    return "<html>" + objects[0].toFixed(2) + " m/s<br/>"
            + (objects[0] * 3.6).toFixed(2) + " km/h</html>";
}