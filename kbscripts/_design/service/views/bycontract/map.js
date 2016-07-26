function (
		doc) {
	var argValues = [];
	var result = null;
	if (doc.type
			&& doc.type == 'service'){
		for (var j in  doc.args){
			argValues.push(doc.args[j]);
		}
		if(!doc.result || doc.result==null || doc.result == "") 
			result = "";
		else
			result = doc.result;
		emit(
				[doc.name,result,argValues],
				doc,
				1);
	}
}