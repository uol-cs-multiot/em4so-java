/*
 * Copyright: marcoph (2015)
 * License: The Apache Software License, Version 2.0
 */
function (
		head,
		req) {
	provides(
			'json',
			function() {
				var results = {};
				while (row = getRow()) {
					results[row.key] = row.value;
				}
				send(JSON
						.stringify(results));
			});
}