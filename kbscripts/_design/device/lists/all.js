function (
		head,
		req) {
	provides(
			'json',
			function() {
				var results = [];
				while (row = getRow()) {
					results
							.push(row.value);
				}
				send(JSON
						.stringify(results));
			});
}