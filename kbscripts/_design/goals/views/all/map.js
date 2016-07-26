function (doc){
	if(doc.type && doc.type=='goal'){
		emit(doc._id, doc	); 
		}
}