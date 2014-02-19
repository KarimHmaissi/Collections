var mainModule = angular.module("collectionsFrontEndApp");

mainModule.filter('timestamp', function() {
  return function(input) {
    if(input) {
    	var fromNow = moment(input * 1000).fromNow();
    	return fromNow
    	
    } else {
    	return ""
    }
  };
});