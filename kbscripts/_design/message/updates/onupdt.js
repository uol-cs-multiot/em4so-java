function (doc,req) {
	var message = JSON.parse(req.body);
	if (!doc) {
		doc = {	_id :message.id};
		}
	doc['_id'] = doc._id;
	doc['scope'] = message.scope;
	doc['type'] = message.kind;
	doc['from'] = message.from;
	doc['received'] = message.receivedDate;
	doc['processed'] = null;
	doc['value' ] = message.value;
	doc['_rev'] = doc._rev;
	
return [doc,	'Updated ' +doc._id];
}