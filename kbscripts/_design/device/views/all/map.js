function (
		doc) {
	if (doc.type
			&&  doc.type == 'sensor' )
		emit(doc._id,
				{id:doc._id,
				 description:doc.description,
				mode:doc.mode,
				property:doc.property,
				propertyNames:doc.property,
				capability:doc.capability,
				kind:doc.kind});
}