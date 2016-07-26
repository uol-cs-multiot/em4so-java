function (doc){
	if(doc.type && doc.type=='role'){
		if(doc.responsibilities){  
			doc.responsibilities.forEach(
					function(activity){
						emit(activity._id,doc);
						}
					);
		}
		}
	}