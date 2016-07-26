function (
		doc) {
	if (doc.type
			&& doc.type == 'observation') {
		emit(
				doc._id,
				doc);
	}
}