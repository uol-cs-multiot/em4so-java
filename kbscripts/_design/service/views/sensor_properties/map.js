function (doc) {
	if (doc.type && doc.type == 'service' && doc.kind && doc.kind =='sensor' && doc.property)
		for (var i in doc.property)	
		emit( doc.property[i],	{
				id: doc._id,
				name: doc.name,
				result: doc.result,
				property:doc.property,
				args: doc.args
					},	1);
}