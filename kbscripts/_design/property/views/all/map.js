function (doc) {
	if (doc.type
			&& doc.type == 'property') {
		if (!doc.custom)
			emit(
					doc._id,
					{
						id : doc._id,
						name : doc.name,
						units : doc.units,
						scope: doc.scope
					});
		else
			emit(
					doc._id,
					{
						name : doc.name,
						units : doc.units,
						_id : doc.custom._id,
						scope: doc.scope
					});
	}
}