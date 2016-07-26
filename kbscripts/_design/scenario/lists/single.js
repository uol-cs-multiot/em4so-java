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
				if(results.length==1)
					send(JSON.stringify(results[0]));
				else
					send(null);
			});
}