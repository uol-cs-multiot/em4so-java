function (doc) {
	if (doc.type	&& doc.type == 'message') {
				emit(doc.received,doc,1);
	}
}