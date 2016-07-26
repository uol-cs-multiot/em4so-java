function(doc){
	if(doc.scope ){
			emit([doc.scope,doc.name],doc	); 
	}
}