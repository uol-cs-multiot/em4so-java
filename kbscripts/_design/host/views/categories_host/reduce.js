function (keys, values,	rereduce) {
	var list = {};
	var k, c = 0;
	for (var i = 0, len = values.length;i<len;i++){
		k=values[i].url;
		if (!list[k]){
			list[k] = 1;
		}else{
			c = list[k];
			list[k] = c + 1;
		} 
	}
	return list;
}