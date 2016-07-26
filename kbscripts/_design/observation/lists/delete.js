function (
		head,
		req) {
	provides(
			'json',
			function() {
				var results = [];
				var observation;
				while (row = getRow()) {
					results.push({_id:row.value._id,_rev:row.value._rev,_deleted:true});
				}
				send(JSON
						.stringify({docs:results}));
			});
}