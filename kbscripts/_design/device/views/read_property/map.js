function (
		doc) {
	if (doc.type
			&&  doc.type == 'sensor' && doc.property )
		for (var i in doc.property)	
		emit(doc.property[i],
				{id:doc._id,
				 description:doc.description,
				mode:doc.mode,
				property:doc.property,
				propertyNames:doc.property,
				capability:doc.capability,
				kind:doc.kind});
}