function(doc){
	if(doc.type && doc.type=='host' && doc.roles)
		for(var i = 0; i < doc.roles.length;i++)
			emit(doc.roles[i],doc);
	}