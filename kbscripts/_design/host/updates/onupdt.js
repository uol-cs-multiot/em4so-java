function (doc,req) {
var host = JSON.parse(req.body);
var theRoles = [];
if (!doc) {
	doc = {	_id : host.id	};
	}
doc['_id'] = doc._id;
doc['type'] = host.type;
doc['name'] = host.name;
doc['categories'] = host.categories;
for (x in host.roles){
	theRoles.push(host.roles[x].id);
}
doc['roles'] = theRoles;
doc['ranking'] = host.ranking;
doc['urls'] = host.urls;
doc['_rev'] = doc._rev;
return [doc,	'Updated ' +doc._id];
}