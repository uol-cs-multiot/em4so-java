function (keys, values,rereduce) {
	if (values.length > 1) {
		var max = 0, ks = values;
		for (var i = 1, len = ks.length; i < len; ++i) {
			if (ks[max].ranking < ks[i].ranking) {
				max = i;
			}
		}
		return ks[max];
	} else {
		return values[0];
	}
}