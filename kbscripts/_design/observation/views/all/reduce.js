function (
		keys,
		values,
		rereduce) {
	var max = 0, ks = values;
	for (var i = 1, len = ks.length; i < len; ++i) {
		if (ks[max].timed < ks[i].timed) {
			max = i;
		}
	}
	return ks[max];
}