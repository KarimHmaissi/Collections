var mainModule = angular.module("collectionsFrontEndApp");


mainModule.directive("tile", function() {
	return {
        	restrict: 'A',
                replace: false,
                scope: true,

                link: function($scope, element, attrs) {
                	console.log("running tile");

                	element.on("click", function() {
                		console.log("element clicked");
                		element.width("400px");
                	});
                }
        };
});