function (head,req) {
	provides(	'json', function() {
				var results = {};
				while (row = getRow()) {
					goal = {
							id: row.value._id,
							description: row.value.description,
							priority: row.value.priority,
							goalType: row.value.goalType,
							properties:  row.value.properties
					};
					if(row.value.parent != null &&  row.value.parent != "")
						 goal.parent = { id: row.value.parent};
					results[row.value._id] = goal;
				}
				send(JSON.stringify(results));
			});
}