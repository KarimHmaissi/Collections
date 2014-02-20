'use strict';

// var mainModule = angular.module("collectionsFrontEndApp");
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
	});
});


mainModule.controller('UserCtrl', function ($scope, $routeParams, feedService) {
	// feedService.getFeedByUser($routeParams.id).then(function(result) {
	// 	console.log("got feeds");
	// 	console.log(result);
	// 	$scope.feeds = result.feeds;
	// 	$scope.posts = result.posts;
	// 	$scope.Math = window.Math;
	// });

	// $scope.getFeedByType = function(feedType) {
	// 	feedService.getFeedByUser($routeParams.id, feedType).then(function(result) {
	// 		$scope.feeds = result.feeds;
	// 		$scope.posts = result.posts;
	// 	});
	// }

	feedService.getUser().then(function(result) {
		$scope.user = result;
		// $scope.feeds = result.feeds;
	});

	$scope.filterFeeds = function(filter) {
		console.log("filtering feeds");
		if(filter === "All") {
			$scope.feedFilter = "";
		} else {
			$scope.feedFilter = filter;
		}
		
	}

	$scope.showFilter = false;
});

mainModule.controller('UserFeedsCtrl', function ($scope, $routeParams, feedService) {
	$scope.loading = true;

	feedService.getFeedByUser($routeParams.id).then(function(result) {
		$scope.feeds = result.feeds;
		$scope.posts = result.posts;
		$scope.Math = window.Math;
	});

	$scope.getFeedByType = function(feedType) {
		feedService.getFeedByUser($routeParams.id, feedType).then(function(result) {
			$scope.feeds = result.feeds;
			$scope.posts = result.posts;
		});
	}


});

mainModule.controller('CommentsCtrl', function ($scope, $routeParams, commentsService) {
	commentsService.get($routeParams.url).then(function(result) {
		console.log(result); 
		$scope.comments = result[1].data.children;
		
	});
});


