function (
		head,
		req) {
	provides(
			'json',
			function() {
				var results = {};
				while (row = getRow()) {
					results[row.key] = row.value;
				}
				send(JSON
						.stringify(results));
			});
}