"use strict";

var mainModule = angular.module("collectionsFrontEndApp");

mainModule.service("feedService", function($http, $q) {
	return {

		addMetaData: function(posts) {
			var x = 0;
			for(x; x < posts.length; x++) {
				posts[x].timestamp = Date.parse(posts[x].posted);
			}

			return posts;
		},

		get: function() {

			var defer = $q.defer();
			 
			$http.jsonp(
				"http://localhost:8080/CollectionsBackendApi/feed?callback=JSON_CALLBACK")
				// .then(function(result) {
				// 	return result;	
				// });

				.success(function(result) {
					defer.resolve(result);
				})
				.error(function(result) {
					console.log("error");
					defer.resolve({error:"unable to download feeds"});
				});

			return defer.promise;
			
		},

		getFeed: function(id) {
			var defer = $q.defer();
			var self = this;

			$http.jsonp("http://localhost:8080/CollectionsBackendApi/feed/get?id=" 
				+ id + "&callback=JSON_CALLBACK")
				.success(function(result) {
					console.log("feed result:");
					console.log(result);
					// result.posts = self.addMetaData(result.posts)
					defer.resolve(result);
				})
				.error(function(result) {
					defer.resolve({error:"unable to download feed"});
				});
			return defer.promise;
		},

		getFeedByUser: function(userId, feedType) {
			var defer = $q.defer();
			var self = this;

			console.log(feedType);

			if(feedType && !(feedType === "all")) {
				var url = "http://localhost:8080/CollectionsBackendApi/feed/getByUser?id=" 
				+ userId + "&feedType=" + feedType + "&callback=JSON_CALLBACK"
			} else {
				var url = "http://localhost:8080/CollectionsBackendApi/feed/getByUser?id=" 
				+ userId + "&callback=JSON_CALLBACK"
			}

			$http.jsonp(url)
				.success(function(result) {
					console.log("feed result:");
					console.log(result);
					// result.posts = self.addMetaData(result.posts)
					defer.resolve(result);
				})
				.error(function(result) {
					defer.resolve({error:"unable to download feed"});
				});
			return defer.promise;
		}
	}
});