function(
		head,
		req) {
	provides(
			'json',
			function() {
				var results = [];
				var observation;
				while (row = getRow()) {
					results
							.push(row.value);
				}
				send(JSON
						.stringify(results));
			});
}