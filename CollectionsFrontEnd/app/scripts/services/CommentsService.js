"use strict";

var mainModule = angular.module("collectionsFrontEndApp");

console.log("comments service running");
mainModule.service("commentsService", function($http, $q) {
	return {
		get: function(id) {
			console.log("downloading comments");
			var url = "http://www.reddit.com/comments/" + id + ".json";
			console.log(url);

			var defer = $q.defer();
			 
			$http.get(url)
				
				.success(function(result) {
					defer.resolve(result);
				})
				.error(function(result) {
					console.log("error");
					console.log(result);
					defer.resolve({error:"unable to download feeds"});
				});

			return defer.promise;
		}
	}
});