function (
		doc) {
	if (doc.type
			&& doc.type == 'observation') {
		for ( var i in doc.readings)
			emit(
					doc.property,
					{
						property : {
							name : doc.property
						},
						time : doc.readings[i].time,
						timed : Date
								.parse(doc.readings[i].time),
						id : doc._id,
						value : doc.readings[i].value,
						sensor : {
							id : doc.readings[i].sensor
						}
					},
					1);
	}
}