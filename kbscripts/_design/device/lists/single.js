function (
		head,
		req) {
	provides(
			'json',
			function() {
				var results = [];
				var device = null;
				var props = [];
				while (row = getRow()) {
					device = row.value;
					props =  row.value.property;
					device.property = [];
					for(var i in props){
						device.property.push({name:props[i]});
					}
					
					results
							.push(device);
				}
				if(results.lentgh = 1){
					send(JSON
							.stringify(results[0]));
				}
				
			});
}