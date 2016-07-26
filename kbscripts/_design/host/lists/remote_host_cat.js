function (head,req) {
	provides(	'json',function() {
				var results = [];
				var urls = [];
				var avoidHosts = [];
				var avoidUrls = JSON.parse(req.query.params1);
				var knownUrls;
//				for(var i=0;i<avoidUrls.length;i++){
//					avoidUrls[i]=avoidUrls[i].replace('127.0.0.1','localhost');
//					send(JSON.stringify(i+" - "+avoidUrls[i]));
//				}
				while (row = getRow()) {
								if(avoidUrls.indexOf(row.value.urls)<0){
									urls.push(row.value);
								}else{
									avoidHosts.push(row.value._id);
								}
				}
				for(var i =0; i<urls.length;i++){
					if(avoidHosts.indexOf(urls[i]._id) < 0)
						results.push(urls[i]);
				}
				send(JSON.stringify(results));
			});
}