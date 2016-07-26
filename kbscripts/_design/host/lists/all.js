function (
		head,
		req) {
	provides(
			'json',
			function() {
				var results = [];
				while (row = getRow()) {
					results
							.push({id:row.value._id,name:row.value.name,urls:row.value.urls,categories:row.value.categories});
				}
				send(JSON
						.stringify(results));
			});
}