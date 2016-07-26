function (
		head,
		req) {
	provides(
			'json',
			function() {
				var results = [];
				while (row = getRow()) {
					results
							.push({_id:row.id,_rev:row.value._rev,_deleted:true});
					}
				send(JSON
						.stringify({docs:results}));
			});
}