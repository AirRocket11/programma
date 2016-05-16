function format(objects) {
    if (objects[0]) {
        return (objects[0] * 0.98).toFixed(2) + " bar";
    } else {
        return "Geen data";
    }
}