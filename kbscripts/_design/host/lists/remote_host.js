function (head,req) {
	provides(	'json',function() {
		var results = {};
		var urls = null;
		var avoidHosts = [];
		var hostKeys = [];
		var theHost;
		var hosts = {};
		var avoidUrls = JSON.parse(req.query.params1);
//				for(var i=0;i<avoidUrls.length;i++){
//					avoidUrls[i]=avoidUrls[i].replace('127.0.0.1','localhost');
//				}
				while (row = getRow()) {
					if(avoidUrls.indexOf(row.value.urls)<0){
						if(hosts[row.value.id]==null ){
							urls = [];
							urls.push(row.value.urls);
							hosts[row.value.id] = {id:row.value.id,urls:urls};
						}else{
							theHost = hosts[row.value.id];
							urls = theHost.urls;
							urls.push(row.value.urls);
							hosts[row.value.id] = theHost;
						}						
					}else{
						avoidHosts.push(row.value.id);
					}
				}
	theHost = null;
	hostKeys = Object.keys(hosts);
	for(var i in hostKeys){
		if(avoidHosts.indexOf(hostKeys[i]) < 0){
			theHost = hostKeys[i];
			results[theHost]= hosts[theHost];
		}
	}
	send(JSON.stringify(results));
			});
}