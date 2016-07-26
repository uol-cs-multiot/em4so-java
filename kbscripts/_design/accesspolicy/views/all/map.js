/*
 * Copyright: marcoph (2015)
 * License: The Apache Software License, Version 2.0
 */
function(doc){
	if(doc.type && doc.type=='accesspolicy')
		emit(doc.resource,doc.access);
	}