function (
		head,
		req) {
	provides(
			'json',
			function() {
				var results = [];
				var service = null;
				while (row = getRow()) {
					service = {
							id: row.value._id,
							name: row.value.name,
							result: row.value.result,
							argTypes: row.value.args,
							potentialExIns: exIns
					};
					results.push(service);
				}
				send(JSON
						.stringify(results));
			});
}