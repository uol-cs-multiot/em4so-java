function (head, req) {
	provides('json', function(	) {
		var results = [];
		while (row = getRow()){ 
			results.push(
					{id:row.value._id,args:row.value.args}
					);
			}
			send(JSON.stringify(results));
			}
	);
}