function(doc){
	if(doc.type && doc.type=='accesspolicy')
		emit(doc.resource,doc.access);
	}