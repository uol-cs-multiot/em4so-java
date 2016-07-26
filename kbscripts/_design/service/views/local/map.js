function (
		doc) {
	if (doc.type
			&& doc.type == 'service' )
		for(var i in doc.host)
		if(doc.host[i].capability)
			emit(
				doc.name,doc
		);
}