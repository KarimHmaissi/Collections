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

mainModule.controller('CollectionCtrl', function ($scope, $routeParams, feedService) {
	console.log("collections controller running");
	feedService.getCollection($routeParams.id).then(function(result) {
		$scope.feed = result.feed;
		$scope.posts = result.posts;
		$scope.mashupFeeds = result.feeds;
		$scope.Math = window.Math;
		console.log(result.feeds);
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

mainModule.controller('TwitterCtrl', function ($scope, $routeParams, twitterService) {
	twitterService.getTweets($scope.user.twitterDetails.userName).then(function(result) {
		console.log(result); 
		$scope.tweets = result.tweets;
		console.log($scope.tweets);
		// console.log("calling twttr.widgets.load()");
		// twttr.widgets.load();
	});
});

mainModule.controller('NewMashupCtrl', function ($scope, $timeout, feedService, feedSearchService) {
	// feedService.get().then(function(result) {
	// 	console.log(result)
	// 	$scope.feeds = result;
	// });

	// feedSearchService.search({
	// 	q: "*:*",
	// 	size: 100
	// }).then(function(result) {
	// 	console.log(result);
	// 	$scope.feeds = result.hits.hits
	// }, function(error) {
	// 	console.trace(error.message);
	// });

	// $scope.searchFeeds = function(q) {

	// 	console.log("running search: " + q);
	// 	feedSearchService.search({
	// 		q: q + "*",
	// 		size: 100
	// 	}).then(function(result) {
	// 		console.log(result);
	// 		$scope.feeds = result.hits.hits
	// 	}, function(error) {
	// 		console.trace(error.message);
	// 	});
		
	// };

	// feedSearchService.search().then(function(result) {
	// 	console.log(result);
	// });

	
	//SEARCH
	// $scope.searchFeeds = function(query) {
	// 	console.log(query);
	// 	feedSearchService.search(query).then(function(result) {
	// 		console.log(result);
	// 		$scope.feeds = result.hits.hits
	// 	});
	// };

	// $scope.searchFeeds();

	$scope.$watch("query", function(newQuery) {
		// console.log("query changed");
		//if query has not changed in the last second 
		//(user stopped typing) then do a search
		$timeout(function() {
			if($scope.query === newQuery) {
				feedSearchService.search(newQuery).then(function(result) {
					console.log(result);
					$scope.feeds = result.hits.hits
				});
			}
		}, 300);
	});


	$scope.title = "Your new mashup";
	$scope.mashupFeeds = [];
	$scope.showfeeds = true;
	

	$scope.preview = function() {
		
		var ids = [];
		for(var x = 0; x < $scope.mashupFeeds.length; x++) {
			ids.push($scope.mashupFeeds[x].id);
		}

		feedService.getPostsByFeedIds($scope.title, ids).then(function(result) {
			$scope.posts = result.posts;
			$scope.showfeeds = false;
		});
	};

	//hide search by default
	$scope.showSearch = true;
	$scope.showSubmit = false;
	$scope.editTitle = false;

	$scope.filterFeeds = function(filter) {
		console.log("filtering feeds");
		if(filter === "All") {
			$scope.feedFilter = "";
		} else {
			$scope.feedFilter = filter;
		}
		
	}

	$scope.submit = function(title, url) {
		feedService.submitFeed(title, url).then(function(result) {
			console.log(result);
			feedService.get().then(function(result) {
				$scope.feeds = result;
			});
		});
	};

	$scope.save = function() {
		var ids = [];
		for(var x = 0; x < $scope.mashupFeeds.length; x++) {
			ids.push($scope.mashupFeeds[x].id);
		}
		//add to user.collections
		//send back to db
		feedService.submitMashup($scope.title, ids).then(function(result) {
			console.log(result);
			// $scope.$apply(function() { 
				if(result.id !== undefined || result.id !== null) 
					$scope.user.collections.push({id: result.id, title: $scope.title});
				// $location.path("/collection/" + result.id); 
			// });
			

		});
	};

	



	
});




