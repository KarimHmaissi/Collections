'use strict';

var mainModule = angular.module("collectionsFrontEndApp");

mainModule.controller('MainCtrl', function ($scope) {
    
});

mainModule.controller('FeedsCtrl', function ($scope, feedService) {
	feedService.get().then(function(result) {
		$scope.feeds = result;
	});
});

mainModule.controller('FeedCtrl', function ($scope, $routeParams, feedService) {
	feedService.getFeed($routeParams.id).then(function(result) {
		$scope.feed = result.feed;
		$scope.posts = result.posts;
		$scope.Math = window.Math;


		//TODO move this
		//hide reddit posts with a score of less than 10

		// var x = 0;
		// for(x; x < $scope.posts.length; x++) {
		// 	if($scope.posts[x].postType === "reddit") {
		// 		// console.log("reddit post score: " + $scope.posts[x].redditScore + " id: " + x)
		// 		console.log($scope.posts[x].title);
		// 		console.log($scope.posts[x].redditUpvotes);

		// 		if($scope.posts[x].redditUpvotes < 10) {
		// 			// $scope.posts.splice(x, 1);
		// 			 console.log("removing reddit post from posts: " + x);

		// 		} 
		// 	}
		// }

		// var length = $scope.posts.length
		// while(length--) {
		// 	if($scope.posts[length].postType === "reddit") {

		// 		if($scope.posts[length].redditUpvotes < 10) {
		// 			 $scope.posts.splice(length, 1);
		// 		} 
		// 	}
		// }


	});
});


