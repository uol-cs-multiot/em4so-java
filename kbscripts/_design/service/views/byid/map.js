function (
		doc) {
	if (doc.type
			&& doc.type == 'service')
		emit(
				doc._id,
				doc,
				1);
}