"use strict";

// var mainModule = angular.module("collectionsFrontEndApp");
var mainModule = angular.module("collectionsFrontEndApp");

mainModule.service("feedService", function($http, $q, $angularCacheFactory) {

	$angularCacheFactory('dataCache', {
        maxAge: 1200000, // Items added to this cache expire 
        cacheFlushInterval: 1800000, // This cache will clear itself
        deleteOnExpire: 'aggressive' // Items will be deleted from this cache right when they expire.
    });

    // $http.defaults.cache = $angularCacheFactory.get('dataCache');

	return {

		// addMetaData: function(posts) {
		// 	var x = 0;
		// 	for(x; x < posts.length; x++) {
		// 		posts[x].timestamp = Date.parse(posts[x].posted);
		// 	}

		// 	return posts;
		// },

		get: function() {

			var defer = $q.defer(),
				dataCache = $angularCacheFactory.get('dataCache');
			 
			if(dataCache.get("/feed")) {

				defer.resolve(dataCache.get("/feed"));
			} else {

				$http.jsonp(
					"http://192.168.0.2:8080/CollectionsBackendApi/feed?callback=JSON_CALLBACK")

				.success(function(result) {
					dataCache.put("/feed", result);
					defer.resolve(result);
				})
				.error(function(result) {
					console.log("error");
					defer.resolve({error:"unable to download feeds"});
				});
			}

			return defer.promise;
		},

		getFeed: function(id) {

			var defer = $q.defer(),
				dataCache = $angularCacheFactory.get('dataCache');

			if(dataCache.get("/feed/" + id)) {

				defer.resolve(dataCache.get("/feed/" + id));
			} else {

				$http.jsonp("http://192.168.0.2:8080/CollectionsBackendApi/feed/get?id=" 
					+ id + "&callback=JSON_CALLBACK")
				.success(function(result) {
					dataCache.put("/feed/" + id, result);
					defer.resolve(result);
				})
				.error(function(result) {
					defer.resolve({error:"unable to download feed"});
				});
			}

			return defer.promise;
		},

		getFeedByUser: function(userId, feedType) {
			var defer = $q.defer(),
				dataCache = $angularCacheFactory.get('dataCache');

			console.log(feedType);

			if(feedType && !(feedType === "all")) {
				var cacheKey = "/getByUser/" + userId + "/" + feedType;
				var url = "http://192.168.0.2:8080/CollectionsBackendApi/user/getFeedsByUser?id=" 
				+ userId + "&feedType=" + feedType + "&callback=JSON_CALLBACK"
			} else {
				var cacheKey = "/getByUser/" + userId;
				var url = "http://192.168.0.2:8080/CollectionsBackendApi/user/getFeedsByUser?id=" 
				+ userId + "&callback=JSON_CALLBACK"
			}

			if(dataCache.get(cacheKey)) {
				defer.resolve(dataCache.get(cacheKey));
			} else {
				$http.jsonp(url)
				.success(function(result) {
					dataCache.put(cacheKey, result);
					defer.resolve(result);
				})
				.error(function(result) {
					defer.resolve({error:"unable to download feed"});
				});
			}

			
			return defer.promise;
		},

		//stubbed user auth not completed
		getUser: function(userId) {
			var defer = $q.defer();

			var user = {
				id: 1,
				feeds: [
					{id: 12165, title: "adviceanimals", feedType: "reddit"},
					{id: 12217, title: "AskReddit", feedType: "reddit"},
					{id: 12268, title: "aww", feedType: "reddit"},
					{id: 12319, title: "bestof", feedType: "reddit"},
					{id: 12417, title: "earthporn", feedType: "reddit"},
					{id: 12468, title: "explainlikeimfive", feedType: "reddit"},
					{id: 12519, title: "funny", feedType: "reddit"},
					{id: 12570, title: "gaming", feedType: "reddit"},
					{id: 12621, title: "gifs", feedType: "reddit"},
					{id: 12718, title: "movies", feedType: "reddit"},
					{id: 12770, title: "videos", feedType: "reddit"},
					{id: 12822, title: "smashingmagazine", feedType: "rss"},
					{id: 12833, title: "tech-talk", feedType: "rss"},
					{id: 12884, title: "LeagueOfSuperCritics", feedType: "youtube"},
					{id: 12935, title: "CGPGrey", feedType: "youtube"}
				],
				userName: "Karim Hmaissi",
				hasTwitter: true,
				twitterDetails: {
					userName: "KarimHmaissi"
				}


			}

			defer.resolve(user);

			return defer.promise;
		}
	}
});