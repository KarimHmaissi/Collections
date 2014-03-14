"use strict";

// var mainModule = angular.module("collectionsFrontEndApp");
var mainModule = angular.module("collectionsFrontEndApp");

mainModule.service("twitterService", function($http, $q, $angularCacheFactory) {

	$angularCacheFactory('twitterCache', {
        maxAge: 1200000, // Items added to this cache expire 
        cacheFlushInterval: 1800000, // This cache will clear itself
        deleteOnExpire: 'aggressive' // Items will be deleted from this cache right when they expire.
    });

	return {
		getTweets: function(userName) {
			console.log("/user/getTweets/" + userName);

			var defer= $q.defer(),
				dataCache = $angularCacheFactory.get('twitterCache');

			if(dataCache.get("/user/getTweets/" + userName)) {
				console.log("loaded from cache");
				defer.resolve(dataCache.get("/user/getTweets/" + userName));
			} else {
				$http.jsonp("http://localhost:8080/CollectionsBackendApi/user/getTweets?userName=" 
					+ userName + "&callback=JSON_CALLBACK")
				.success(function(result) {
					dataCache.put("/user/getTweets/" + userName, result);
					console.log(dataCache.info());
					defer.resolve(result);
				})
				.error(function(result) {
					defer.resolve({error:"unable to download feed"});
				});
			}

			return defer.promise;
		}
	}
});