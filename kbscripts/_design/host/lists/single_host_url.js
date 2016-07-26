function (head,req) {
	//To use when only one result is required and exprected
	provides(	'json',function() {
				var results = [];
				var host = null;
				var urls = [];
				while (row = getRow()) {
						if(host == null){
							urls.push(row.value.urls);
							host = {id:row.value.id,urls:urls};
						}else if (host.id == row.value.id){
							host.urls.push(row.value.urls);
						}						
				}
				results.push(host);
//				if(results.length==1)
					send(JSON.stringify(results[0]));
//				else
//					send(null);
		}
	);
}