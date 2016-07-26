/*
 * Copyright: marcoph (2015)
 * License: The Apache Software License, Version 2.0
 */
function (doc) {
	if (doc.type
			&& doc.type == 'capability')
		for ( var i in doc.actions)
			for ( var j in doc.hosts)
				emit(
						doc.actions[i].id,
						{
							lastSuccessd : Date
									.parse(doc.hosts[j].lastSuccess),
							executionInstance : {
								url : doc.hosts[j].url,
								lastSuccess : doc.hosts[j].lastSuccess,
								wrapping : doc.wrapping
							}
						},
						1);
}