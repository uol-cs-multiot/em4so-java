function (keys, values, rereduce) {
	var min = 0, ks = values;
	for (var i = 1, len = ks.length; i < len; ++i) {
		if (ks[min].received > ks[i].received) {
			min = i;
		}
	}
	return ks[min];
}