var mainModule = angular.module("collectionsFrontEndApp");

mainModule.directive("tweet", function($http) {
	return {
		restrict: 'A',
                template: '<div></div>',
                replace: true,
                scope: true,

                link: function($scope, element, attrs) {
                        console.log("running tweet directive with id: " + attrs.id);
                 //        var html = ""

                	// $http.get("https://api.twitter.com/1/statuses/oembed.json?id=" + attrs.tweetId)
                 //                .success(function(result) {
                 //                        html = result.html
                 //                        element[0].outerHtml = html
                 //                })
                 //                .error(function(result) {
                 //                        html = "error downloading tweet"
                 //                        element[0].outerHtml = html
                 //                });
                }
        };
});