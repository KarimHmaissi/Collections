'use strict';

// var mainModule = angular.module("collectionsFrontEndApp");
var mainModule = angular.module("collectionsFrontEndApp");

// mainModule.controller('MainCtrl', function ($scope) {
    
// });

mainModule.controller('FeedsCtrl', function ($scope, $timeout, feedService, feedSearchService) {
	// feedSearchService.search().then(function(result) {
	// 	$scope.feeds = result.hits.hits;
	// 	$scope.$broadcast("Data_Ready");
	// });

	$scope.query = "";

	$scope.first = true;

	$scope.$watch("query", function(newQuery) {
		// console.log("query changed");
		//if query has not changed in the last second 
		//(user stopped typing) then do a search

		if($scope.first) {
			feedSearchService.search(newQuery).then(function(result) {
				// console.log(result);
				console.log("query changed");
				$scope.feeds = result.hits.hits
				$scope.first = false;
				$scope.$broadcast("Data_Ready");
			});
		} else {
			$timeout(function() {
				if($scope.query === newQuery) {
					feedSearchService.search(newQuery).then(function(result) {
						// console.log(result);
						console.log("query changed");
						$scope.feeds = result.hits.hits
						$scope.$broadcast("Data_Ready");
					});
				}
			}, 700);
		}

		
	});

	$scope.search = function(query) {
		if($scope.query === query) {
			feedSearchService.search(query).then(function(result) {
				console.log(result);
				$scope.feeds = result.hits.hits
				$scope.$broadcast("Data_Ready");
			});
		}
	}

	//show search by default
	$scope.showSearch = true;
});

mainModule.controller('CollectionsCtrl', function ($scope, $timeout, feedService, feedSearchService) {
	feedService.getAllCollections().then(function(result) {
		$scope.feeds = result;
		$scope.$broadcast("Data_Ready");
	});

	$scope.$watch("query", function(newQuery) {
		// console.log("query changed");
		//if query has not changed in the last second 
		//(user stopped typing) then do a search
		$timeout(function() {
			if($scope.query === newQuery) {
				feedSearchService.searchMashups(newQuery).then(function(result) {
					console.log(result);
					$scope.feeds = result.hits.hits
				});
			}
		}, 300);
	});

	//show search by default
	$scope.showSearch = true;
});



mainModule.controller('FeedCtrl', function ($scope, $routeParams, feedService) {
	feedService.getFeed($routeParams.id).then(function(result) {
		$scope.feed = result.feed;
		$scope.posts = result.posts;
		$scope.Math = window.Math;
		$scope.$broadcast("Data_Ready");
	});

	// $scope.followFeed = function(id, title, feedType) { 
	// 	console.log("following feed");
	// 	$scope.user.feeds.push({id: id, title: title, feedType: feedType});
	// 	console.log($scope.user);
	// };

});

mainModule.controller('CollectionCtrl', function ($scope, $routeParams, feedService) {
	console.log("collections controller running");
	feedService.getCollection($routeParams.id).then(function(result) {
		$scope.feed = result.feed;
		$scope.posts = result.posts;
		$scope.mashupFeeds = result.feeds;
		$scope.Math = window.Math;
		$scope.$broadcast("Data_Ready");
		console.log(result.feeds);
	});

	$scope.getFeedByType = function(feedType) {
		feedService.getCollection($routeParams.id, feedType).then(function(result) {
			$scope.feeds = result.feeds;
			$scope.posts = result.posts;
		});
	}
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

	// $scope.$watch("user.feeds.length", function(newArray) {
	// 	console.log("feeds updated");
	// });

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

	//show search by default
	$scope.showSearch = true;


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

	$scope.showSave = false;

	$scope.save = function() {

		// $scope.showSave = !$scope.showSave;

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


	//experimental
	$scope.addAll= function() {
		for(var x = 0; x < $scope.feeds.length; x++) {
			console.log($scope.feeds[x]._id);
			var feed = $scope.feeds[x];
			$scope.mashupFeeds.push({
				id: feed._id, 
				feedType: feed._source.feedType, 
				title: feed._source.title});
		}
	};

	$scope.removeAll = function() {
		$scope.mashupFeeds.length = 0;
	};

	



	
});




