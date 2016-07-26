function (doc,req) {
	var ser = JSON.parse(req.body);
	var hosts = {};
	var ei = {};
	if (!doc) {
		doc = {	_id : ser.id	};
	}
	doc['type'] = ser.type;
	doc['categories'] = ser.categories;
	doc['name'] = ser.name;
	doc['args'] = ser.argTypes;
	doc['result'] = ser.result;
	doc['kind'] = ser.kind;
	doc['property'] = ser.property;
	if(ser.executionInstance.capability)
		doc['capability'] = ser.executionInstance.capability;
	if(ser.potentialExIns )
		doc['host'] =ser.potentialExIns; 
	else { if(!doc['host'])
					doc['host'] = {};
				else
					hosts =doc['host']; 
	ei = {id:ser.executionInstance.host.id,
			ranking: ser.executionInstance.ranking,
			lastSuccess: ser.executionInstance.lastSuccess,
			capability: ser.executionInstance.capability};
	hosts[ser.executionInstance.host.id] = ei;
	doc['host'] = hosts;
	}
	doc['_rev'] = doc._rev;
	
	return [doc,	'Updated '+ doc._id ];
}