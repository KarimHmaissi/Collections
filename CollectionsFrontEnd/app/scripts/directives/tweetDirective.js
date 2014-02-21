var mainModule = angular.module("collectionsFrontEndApp");


mainModule.directive("tweet", function($http, $timeout) {
	return {
		restrict: 'A',
                replace: false,
                scope: true,

                link: function($scope, element, attrs) {
                        $scope.$watchCollection('tweets', function(n, o) {
                               $timeout(runTweetRender, 0);
                        });


                        var runTweetRender = function() {
                                twttr.widgets.load(); 
                                console.log("called twttr.widgets.load();");
                        }

                        //timer add a new event to the browser event queue. 
                        //The rendering engien for the dom is also in this queue. 
                        //The function will run after the dom is rendered. 
                        //http://blog.brunoscopelliti.com/run-a-directive-after-the-dom-has-finished-rendering
                        
                        
                        

                        // console.log("running tweet directive with id: " + attrs.id);
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