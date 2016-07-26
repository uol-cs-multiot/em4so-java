function (
		head,
		req) {
	provides(
			'json',
			function() {
				var results = {};
				while (row = getRow()) {
					results[row.key] = {
							id: row.value._id,
							name: row.value.name,
							type: row.value.type,
							categories: row.value.categories,
							argTypes: row.value.args,
							result: row.value.result,
							capability: row.value.capability,
							host: row.value.host
							};
				}
				send(JSON
						.stringify(results));
			});
}