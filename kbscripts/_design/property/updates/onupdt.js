function (doc,req) {
	var property = JSON.parse(req.body);
	if (!doc) {
		doc = {	_id :property.id};
		}
	doc['_id'] = doc._id;
	doc['scope'] = property.scope;
	doc['type'] = property.type;
	if(property.kind!= null && property.kind != '')
		doc['kind'] = property.kind;
	doc['name'] = property.name;
	doc['value' ] = property.value;
	for (var k in property.attributes)
		doc[k] = property.attributes[k];
	doc['_rev'] = doc._rev;
	
return [doc,	'Updated ' +doc._id];
}