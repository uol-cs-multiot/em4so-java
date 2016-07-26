function (head, req) {
	provides('json', function() {
		var results = [];
		while (row = getRow()){
			results.push(row.value);
			}
		if(results.length==1)
			send(JSON.stringify({id:results[0]._id}));
		else
			send(null);
		});}