function(doc){
	if(doc.scope ){
		for(var key in doc)
			if(key!= "_id" && key!= "type" && key!= "name" && key!= "_rev" && key!= "kind" && key!= "scope")
			emit([doc.scope,doc.name,key],doc); 
		}
	}