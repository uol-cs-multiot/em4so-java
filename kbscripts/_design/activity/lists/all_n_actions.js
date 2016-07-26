function (head,req) {
	provides(	'json',
			function() {
				var results = [];
				var theArgTypes;
				var theArgValues;
				var resultValue;
				var resultType;
				var theArgTypesObj;
				var activity = null;
				var seq = 0;
				while (row = getRow()) {
					if (row.value != null) {
						if (activity == null || activity.id != row.id)
							activity = {
								id : row.id,
								description : {},
								input : {},
								inputKnowledge:{},
								actions : [],
								categories : {},
								output:{},
								seq: seq
							};
						if (row.value.name) {
							theArgTypes = {};
							theArgValues = {};
							theArgNames = [];
							resultValue = null;
							resultType = "";
							for ( var i in row.value.args) {
								theArg = row.value.args[i];
								for ( var j in theArg) {
									theArgTypesObj = theArg[j];
									for ( var k in theArgTypesObj) {
										theArgTypes[j] = k;
										theArgValues[j] = theArgTypesObj[k];
									}
								}
							}
							
							for ( var x in row.value.result) {
								resultValue = row.value.result[x];
								resultType = x;
							}
							
							
							activity.actions
									.push({
										id: row.value.id,
										prereq:row.value.prereq,
										argValues : theArgValues,
										name : row.value.name,
										result : resultValue,
										service : {
											name : row.value.name,
											result: resultType,
											argTypes : theArgTypes,
											categories: row.value.categories
										}
									});
						} else {
							seq = seq + 1;
							activity.input = row.value.input;
							activity.inputKnowledge = row.value.inputKnowledge;
							activity.description = row.value.description;
							activity.categories = row.value.categories;
							activity.output = row.value.output;
							activity.seq = seq;
							results	.push(activity);
						}
					}
				}
				send(JSON
						.stringify(results));
			});
}