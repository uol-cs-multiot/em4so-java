function (head,req) {
	provides(	'json',function() {
				var results = [];
				var exIns = {};
				var exIn = {};
				while (row = getRow()) {
//					for(var i in row.value.host){
//						exIn =  {host:{id: row.value.host[i]._id},ranking:row.value.host[i].ranking};
//						exIns.push(exIn);
//					}
					exIns = row.value.host;
						results.push({
								id: row.value._id,
								name: row.value.name,
								result: row.value.result,
								argTypes: row.value.args,
								potentialExIns: exIns 
						});
				}
				if(results.length==1)
					send(JSON.stringify(results[0]));
				else
					send('');
			});
}