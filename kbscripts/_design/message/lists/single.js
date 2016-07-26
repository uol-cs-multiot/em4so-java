function (head,req) {
		provides(	'json', function() {
					var result = {};
					while (row = getRow()) {
						result = 
								{received:row.value.received,
								kind:row.value.type,
									value:row.value.value,
									from: row.value.from};
					}
						send(JSON.stringify(result));
				});
	}