function (doc) {
	if (doc.type	&& doc.type == 'message') {
		if(doc.processed==null)
			emit(doc.value,doc,1);
	}
}