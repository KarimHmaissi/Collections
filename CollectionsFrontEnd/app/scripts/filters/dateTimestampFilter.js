var mainModule = angular.module("collectionsFrontEndApp");

mainModule.filter('timestamp', function() {
  return function(input) {
    if(input) {
    	return Date.parse(input) 
    } else {
    	return ""
    }
  };
});