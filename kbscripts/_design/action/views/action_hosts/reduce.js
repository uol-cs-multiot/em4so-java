/*
 * Copyright: marcoph (2015)
 * License: The Apache Software License, Version 2.0
 */
function(keys, values, rereduce) {var max = 0, ks = values; for (var i=1,len = ks.length; i < len; ++i) {if(ks[max].lastSuccessd < ks[i].lastSuccessd ){ max = i;}} return ks[max];}