function (head,req) {
	provides(
			'json',
			function() {
				var results = [];
				var property;
				while (row = getRow()) {
					if (row.doc.type == 'units')
						property = {
							id : row.value._id,
							name : row.value.name,
							scope: row.value.scope,
							units : row.value.units,
							customUnits : row.doc.units
						};
					else
						property = {
							id : row.value.id,
							name : row.value.name,
							units : row.value.units,
							scope: row.value.scope
						};
					results
							.push(property);
				}
				send(JSON
						.stringify(results));
			});
}