var mainModule = angular.module("collectionsFrontEndApp");

mainModule.directive("video", function() {
	return {
        	restrict: 'A',
                replace: false,
                scope: true,

                link: function($scope, element, attrs) {
                        
                        element.on("click", function(e) {
                                e.preventDefault();
                                element.html("<iframe width=\"560\" height=\"315\" src=\"//www.youtube.com/embed/" 
                                        + attrs.id + "\" frameborder=\"0\" allowfullscreen></iframe>");
                                console.log(attrs.id);

                        });       
                }
        };
});