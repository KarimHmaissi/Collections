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

		getCollection: function(id) {
			var defer = $q.defer(),
				dataCache = $angularCacheFactory.get('dataCache');

			if(dataCache.get("/collection/" + id)) {

				defer.resolve(dataCache.get("/collection/" + id));
			} else {

				$http.jsonp("http://192.168.0.2:8080/CollectionsBackendApi/feed/getCollection?id=" 
					+ id + "&callback=JSON_CALLBACK")
				.success(function(result) {
					console.log(result);
					dataCache.put("/collection/" + id, result);
					defer.resolve(result);
				})
				.error(function(result) {
					defer.resolve({error:"unable to download feed collection"});
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

		getPostsByFeedIds: function(title, ids) {
			var defer = $q.defer(),
				dataCache = $angularCacheFactory.get('dataCache');

			// if(dataCache.get("/mashupPreview/" + title)) {

			// 	defer.resolve(dataCache.get("/mashupPreview/" + title));
			// } else {

				$http.jsonp(
					"http://192.168.0.2:8080/CollectionsBackendApi/feed/getPostsByIds/" + "",
					{params: {"ids": ids, "callback":"JSON_CALLBACK"}}
				)
				.success(function(result) {
					console.log(result);
					// dataCache.put("/mashupPreview/" + title, result);
					defer.resolve(result);
				})
				.error(function(result) {
					defer.resolve({error:"unable to download feed mashupPreview"});
				});
			// }

			return defer.promise;
		},

		//stubbed user auth not completed
		getUser: function(userId) {
			var defer = $q.defer();

			var user = {
				id: 1,
				feeds: [
					// {id: 13414, title: "adviceanimals", feedType: "reddit"},
					// {id: 13466, title: "AskReddit", feedType: "reddit"},
					// {id: 13517, title: "aww", feedType: "reddit"},
					// {id: 13568, title: "bestof", feedType: "reddit"},
					// {id: 13619, title: "books", feedType: "reddit"},
					// {id: 13670, title: "earthporn", feedType: "reddit"},
					// {id: 13721, title: "explainlikeimfive", feedType: "reddit"},
					// {id: 13773, title: "funny", feedType: "reddit"},
					// {id: 13824, title: "gaming", feedType: "reddit"},
					// {id: 13875, title: "gifs", feedType: "reddit"},
					// {id: 13926, title: "IAmA", feedType: "reddit"},
					// {id: 13978, title: "movies", feedType: "reddit"},
					// {id: 14030, title: "music", feedType: "reddit"},
					// {id: 14082, title: "news", feedType: "reddit"},
					// {id: 14133, title: "pics", feedType: "reddit"},
					// {id: 14184, title: "science", feedType: "reddit"},
					// {id: 14235, title: "technology", feedType: "reddit"},
					// {id: 14286, title: "television", feedType: "reddit"},
					// {id: 14337, title: "todayilearned", feedType: "reddit"},
					// {id: 14388, title: "videos", feedType: "reddit"},
					// {id: 14440, title: "worldnews", feedType: "reddit"},
					// {id: 14492, title: "wtf", feedType: "reddit"}
				],
				collections: [
					{id: 14543, title: "Reddit Defaults"},
					{id: 14657, title: "AngularJS Community "}
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