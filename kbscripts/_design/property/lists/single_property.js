function (head,req) {
	provides(	'json',function() {
			var row = getRow() ;
		send(JSON.stringify(
							{
								scope: row.value.scope,
								name: row.value.name,
								value: row.value.value,
								kind:row.value.kind
								}));
				}
	);
}