function (head,req) {
	provides(	'json',function() {
		var results = {};
		var hosts;
		var role;
			while (row = getRow()) {
					hosts = results[row.key];
					if(!hosts)		
						hosts = [];
				hosts.push(
						{id:row.value._id,name:row.value.name, urls:row.value.urls,roles:row.value.roles}
						);
				results[row.key] = hosts;
			}
			if(req.query.key){
				send(JSON.stringify(results[req.query.key]));
			}else{
				send(null);
			}
			
//	send(JSON.stringify(results));
			});
}