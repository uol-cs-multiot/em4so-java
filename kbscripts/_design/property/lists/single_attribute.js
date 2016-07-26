function (head,req) {
	provides(	'json',function() {
				var results = [];
				var atttributes = {};
				var property = null;
				var theValue = null;
				var requiredProp = JSON.parse(req.query.params1);
				while (row = getRow()) {
					for (var key in row.value){
						if(key == requiredProp){
							property = key;
						theValue = row.value[key];
						}
				}
					results.push({
								name: row.value.name,
								scope: row.value.scope,
								attributeName: property,
								value: theValue,
								kind:row.value.kind
						});
				}
				if(results.length==1)
					send(JSON.stringify(results[0]));
				else
					send('');
			});
}