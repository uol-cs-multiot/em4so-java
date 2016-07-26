function(doc){
	if(	doc.type && doc.type=='activityrole')
		for(var i =0; i<doc.responsibilities.length;i++)
				emit(doc.responsibilities[i],doc.role);	
}