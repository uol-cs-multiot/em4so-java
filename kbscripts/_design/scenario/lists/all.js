function (head,req) {
	provides('json',
			function() {
				var results = [];
				var scenario = null;
				while (row = getRow()) {
					scenario = {
							id: row.value._id,
							goal: row.value.goal,
							description: row.value.description,
							steps: row.value.steps
					};
					results.push(scenario);
				}
				send(JSON.stringify(results));
			});
}