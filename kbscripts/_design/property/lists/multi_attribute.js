function (head,req) {
	provides(	'json',function() {
				var results = [];
				var attributes = {};
				var property = {};
				var theValue = null;
				
				var requiredProp = null;
				var requiredPropArray = [];
				requiredPropArray = JSON.parse(req.query.params1);
				while (row = getRow()) {
					for (var i = 0; i<requiredPropArray.length; i++){
						requiredProp = requiredPropArray[i];
						if(row.value[requiredProp]){
							key = requiredProp;
						theValue = row.value[key]
						attributes[key] = theValue;
						}
				}
					
					results.push({
								name: row.value.name,
								scope: row.value.scope,
								attributes: attributes,
								kind:row.value.kind
						});
				}
				if(results.length==1)
					send(JSON.stringify(results[0]));
				else
					send('');
			});
}