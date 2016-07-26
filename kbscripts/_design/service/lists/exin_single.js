function (head,req) {
	provides(	'json',function() {
				var results = [];
				while (row = getRow()) {
						results.push({host:{id:row.value.id},capability:row.value.capability,ranking:row.value.ranking});
				}
				if(results.length==1)
					send(JSON.stringify(results[0]));
				else
					send('');
			});
}