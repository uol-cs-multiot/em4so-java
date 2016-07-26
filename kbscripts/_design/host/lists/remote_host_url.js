function (head,req) {
	provides(	'json',function() {
		var results = [];
		var urls = [];
		var avoidHosts = [];
		var avoidUrls = JSON.parse(req.query.params1);
//				for(var i=0;i<avoidUrls.length;i++){
//					avoidUrls[i]=avoidUrls[i].replace('127.0.0.1','localhost');
//				}
				while (row = getRow()) {
					if(avoidUrls.indexOf(row.value.urls)<0){
						urls.push(row.value);
					}else{
						avoidHosts.push(row.value.id);
					}
				}
	
	for(var i in urls){
		if(avoidHosts.indexOf(urls[i].id) < 0)
			results.push(urls[i].urls);
	}
	send(JSON.stringify(results));
			});
}