var mainModule = angular.module("collectionsFrontEndApp");


mainModule.directive("menu", function() {
	return {
        	restrict: 'A',
                replace: false,
                scope: true,

                link: function($scope, element, attrs) {
                        new mlPushMenu( document.getElementById( 'mp-menu' ), document.getElementById( 'trigger' ) );      
                }
        };
});