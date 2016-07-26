function (
		head,
		req) {
	provides(
			'json',
			function() {
				var results = [];
				while (row = getRow()) {
					results
							.push(row.value.host);
				}
				send(JSON
						.stringify(results));
			});
}