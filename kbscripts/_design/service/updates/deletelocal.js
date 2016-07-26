function (doc,req) {
var ser = JSON.parse(req.body);
//	if (!doc) {
//		doc = {
//			_id : ser._id
//		};
//	}
	doc['_id'] = doc._id;
	doc['_delete'] = true;
	return [
			doc,
			'Deleted '
					+ doc._id ]
}