function (head,req) {
	provides(	'json',function() {
				var results = [];
				var ei = null;
				var roles = [];
				var sizeroles = 0;
				var roleid = null;
				while (row = getRow()) {
					if(row.value.roles) sizeroles = row.value.roles.length;
					for(var r = 0; r<sizeroles; r++){
						roleid = row.value.roles[r];
						roles.push({id:roleid});
					}
					ei = {
								id: row.value._id,
								name: row.value.name,
								categories: row.value.categories,
								urls:row.value.urls,
								ranking:row.value.ranking,
								roles:roles
						};
						
						results.push(ei);
				}
				if(results.length==1)
					send(JSON.stringify(results[0]));
				else
					send('');
			});
}