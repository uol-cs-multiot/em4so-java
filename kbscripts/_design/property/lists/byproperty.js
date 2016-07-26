function (head, req) {
	provides('json', function() {
		var results = [];
		while (row = getRow()) {
			if (row.value.id.indexOf(req.query.property) > -1)
				results.push(row)
		}
		;
		send(JSON.stringify(results));
	});
}