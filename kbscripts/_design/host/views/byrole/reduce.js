function (keys, values,	rereduce) {
	var max = values[0];
	var k = 0;
	for (var i = 0, len = values.length;i<len;i++){
		k=max.ranking;
		if (values[i].ranking >= k){
			max = values[i];
		} 
	}
	return max;
}