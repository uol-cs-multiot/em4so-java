function (doc) {
	var theUrl;
	var arrayArgs = [];
	if (doc.type && doc.type == 'service' ){
		for (var j in doc.args){
			arrayArgs.push(doc.args[j]);
		}
		for(var i in doc.host){
			emit([doc.host[i]._id,[doc.name,doc.results,arrayArgs]],{host:doc.host[i]._id,id:doc._id,name:doc.name,args:doc.args,result:doc.result});
		}
	}
}