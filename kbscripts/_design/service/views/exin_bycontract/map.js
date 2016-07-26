function (doc) {
	var argValues = [];
	var hostKeys = {};
	var result = null;
	var k;
	if (doc.type && doc.type == 'service'){
		for (var j in  doc.args){
			argValues.push(doc.args[j]);
		}
		if(!doc.result || doc.result==null || doc.result == "") 
			result = "";
		else
			result = doc.result;
		hostKeys = Object.keys(doc.host);
		for(var i=0,l=hostKeys.length;i<l;i++){
//				emit([doc.name,doc.result,argValues], {id:doc.host[i].id, capability: doc.host[i].capability, ranking:doc.host[i].ranking},1);
			k = hostKeys[i];
			emit([doc.name,result,argValues], {id:k, capability: doc.host[k].capability, ranking:doc.host[k].ranking},1);
		}
	}
}