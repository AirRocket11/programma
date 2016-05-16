var Packages;

var $data = {
    "Raket 2": [
        new Data(2.5, 60, [2.00, 1.95, 1.98]),
        new Data(3.0, 60, [3.58, 3.55, 2.28, 3.65, 3.50, 3.60, 3.57, 3.43]),
        new Data(3.5, 60, [4.30, 4.70, 4.50, 4.39, 4.30, 4.30, 4.40, 4.58]),
        new Data(4.0, 60, [5.80, 5.62, 6.12, 6.00, 6.10, 6.10, 6.00]),
        new Data(4.5, 60, [7.70, 7.30, 7.40, 7.30, 7.50, 7.63, 7.60, 7.60]),
        new Data(5.0, 60, [9.06, 9.17, 9.20, 9.16, 9.20])
    ],
    "Raket 9": [
        new Data(2.0, 60, [1.20, 1.20, 1.15, 1.25, 1.20]),
        new Data(2.5, 60, [3.15, 3.00, 3.10, 2.98, 3.05, 3.05, 3.05, 3.00, 3.05]),
        new Data(3.0, 60, [4.30, 4.20, 4.40, 4.35, 4.35, 4.20, 4.30, 4.30]),
        new Data(3.5, 60, [5.70, 5.80, 5.80, 5.85, 6.00, 5.80, 5.95, 5.70]),
        new Data(4.0, 60, [7.40, 7.50, 7.50, 7.45, 7.40, 7.55, 7.40, 7.50]),
		new Data(5.0, 60, [10.6, 10.7, 10.7, 10.6, 10.8])
    ], 
	"Raket 8": [
		new Data(2.0, 60, [1.33, 1.23, 1.25, 1.30, 1.28]),
		new Data(2.5, 60, [3.03, 3.00, 3.05, 3.04, 3.00]),
		new Data(3.0, 60, [4.60, 4.60, 4.60, 4.65, 4.55]),
		new Data(3.5, 60, [6.20, 6.00, 6.20, 6.10, 6.25]),
		new Data(4.0, 60, [8.00, 8.00, 8.08, 8.15, 8.20, 8.20])
	],
	"Raket 4": [
		new Data(2.0, 60, [1.97, 2.00, 1.99, 2.05, 1.98]),
		new Data(2.5, 60, [4.00, 4.20, 4.15, 4.20, 4.25]),
		new Data(3.0, 60, [6.50, 6.45, 6.45, 6.49, 6.45]),
		new Data(3.5, 60, [8.60, 8.65, 8.65, 8.60, 8.50])
	],
	"Raket 10": [
		new Data(2.0, 60, [2.05, 2.10, 2.17, 2.10, 2.05]),
		new Data(2.5, 60, [4.20, 4.15, 4.05, 4.20, 4.05, 4.15]),
		new Data(3.0, 60, [5.80, 6.00, 6.00, 6.00, 5.96, 5.95]),
		new Data(3.5, 60, [8.20, 8.20, 8.10, 8.20, 8.15, 8.20])
	]
};

function Data(pressure, angle, range) {
    return new Packages.util.Data(pressure, angle, range);
}

function getData(rocket) {
    if (rocket) {
        return $data[rocket];
    }
    return $data;
}

function calculate(rocketId, range) {
    var data = $data[rocketId];
    for (var i = 0; i < data.length; i++) {
        var data2 = data[i];
        var data1 = i > 0 ? data[i - 1] : new Data(0, data2.angle(), [0]);
        if (data2.avgRange() > range) {
            var angle = data2.angle();
            var pressure = map(range, data1.avgRange(), data2.avgRange(), data1.pressure(), data2.pressure())
            return new Packages.util.Pair(angle, pressure);
        }
    }
    return new Packages.util.Pair(data ? data[0].angle() : 0, 0);
}

function map(x, in_min, in_max, out_min, out_max) {
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
}
