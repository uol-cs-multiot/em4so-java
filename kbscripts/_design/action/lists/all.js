/*
 * Copyright: marcoph (2015)
 * License: The Apache Software License, Version 2.0
 */
function (head, req) {
	provides('json', function(	) {
		var results = [];
		while (row = getRow()){ 
			results.push(
					{id:row.value._id,args:row.value.args}
					);
			}
			send(JSON.stringify(results));
			}
	);
}