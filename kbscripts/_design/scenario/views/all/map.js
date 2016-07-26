function (
		doc) {
	if (doc.type
			&& doc.type == 'scenario')
		emit(doc.goal,doc,	1);
}