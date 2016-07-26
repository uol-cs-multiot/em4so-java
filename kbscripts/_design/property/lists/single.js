function (head,req) {
	provides(	'json',function() {
				var results = [];
				var key;
				while (row = getRow()) {
					key = row.key[2];
					results.push(
							{
								scope: row.value.scope,
								name: row.value.name,
								attributeName: key,
								value: row.value[key],
								kind:row.value.kind
								});
				}
					send(JSON.stringify(results[0])); //IT'S SINGLE
			}
	);
}