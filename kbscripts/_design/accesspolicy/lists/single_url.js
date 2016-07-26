/*
 * Copyright: marcoph (2015)
 * License: The Apache Software License, Version 2.0
 */
function (head,req) {
	provides(	'json',function() {
				var result = false;
				var a ;
				var url = JSON.parse(req.query.param1);
				
				
				while (row = getRow()) {
					a = row.value;
					if(a.toString()=="public")
						result = true;
					else
						if (a.indexOf(url) >= 0)
							result = true;
					//result += ' - ' +a+' >'+a.indexOf('http://localhost:8080/somanager/agents/managerag1')+' ->'+url+"<-" ;	
				}
				
				send(JSON.stringify(result));
			});
}