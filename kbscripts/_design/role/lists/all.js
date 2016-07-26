function (
		head,
		req) {
	provides(
			'json',
			function() {
				var results = [];
				var indexes = {};
				var role;
				var theIndex = 0;
				var theActions;
				var seq = 0;
				while (row = getRow()) {
					if (row.value != null
							&& row.doc != null) {
						if (row.id in indexes) {
							theIndex = indexes[row.id];
							role = results[theIndex];
						} else {
							role = {
								id : row.id,
								name : '',
								goals : [],
								activity : []
							};
							theIndex = results.length;
							indexes[row.id] = theIndex;
						}
						if (row.value._id) {
							 if (row.doc.type == 'activity') {
								theActions = [];
								for ( var i in row.doc.actions) {
									theActions
											.push({
												service : {
													name : row.doc.actions[i].name
												}
											});
								}
								seq = seq + 1;
								role.activity
										.push({
											seq: seq,
											id : row.doc._id,
											input : row.doc.input,
											description : row.doc.description,
											actions : theActions
										});
							}else if (row.doc.type == 'goal') {
								seq = 0;
								role.goals
										.push({
											id : row.doc._id,
											property : row.doc.properties.property,
											intendedState : row.doc.properties.intendedState,
											achieved : row.doc.properties.achieved
										});
							} 
						} else {
							role.name = row.value.name;
							role.function = row.value.function;
						}
						results[theIndex] = role;
					}
				}
				send(JSON
						.stringify(results));
			});
}