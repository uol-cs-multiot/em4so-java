function (doc) {
	if (doc.type && doc.type == 'host'){
		var catgs = [];
		catgs = doc.categories;
		if(!catgs || catgs.length == 0){
			catgs = ['none'];
		}
		for ( var j in catgs){
			for(var i in doc.urls){
				 doc.urls[i].replace('127.0.0.1','localhost');
				if(doc.urls[i].lastIndexOf('/') == doc.urls[i].length -1 )
					doc.urls[i] = doc.urls[i].substring(0,doc.urls[i].length -1);
				emit({category: catgs[j], urls:doc.urls[i]},{urls:doc.urls[i],id:doc._id},1);
				}
		}
		}
}