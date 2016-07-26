function (head,req) {
	provides(	'json', function() {
				var results = [];
				while (row = getRow()) {
					results.push(
							{received:row.value.received,
								value:row.value.value,
								from: row.value.from}
							);
				}
					send(JSON.stringify(results));
			});
}