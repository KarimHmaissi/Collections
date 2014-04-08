"use strict";

var mainModule = angular.module("collectionsFrontEndApp");


mainModule.service("feedSearchService", function($q, $http) {
	// return esFactory ({

	// 	host: 'localhost:9200'
      

	// });

	return {
		search: function(query) {
			var defer = $q.defer();
			var url = "";
			console.log("searching...");
			console.log(query);

			if(query === undefined || query === null) {
				url = "http://localhost:8080/CollectionsBackendApi/search/?callback=JSON_CALLBACK"
			} else {
				url = "http://localhost:8080/CollectionsBackendApi/search/?q=" 
				+ query + "&callback=JSON_CALLBACK"
			}

			$http.jsonp(url)
			.success(function(result) {
				defer.resolve(result);
			})
			.error(function(result) {
				defer.resolve({error:"unable to download feeds"});
			});
			

			return defer.promise;
		},

		searchMashups: function(query) {
			var defer = $q.defer();
			var url = "";
			console.log("searching...");
			console.log(query);

			if(query === undefined || query === null) {
				url = "http://localhost:8080/CollectionsBackendApi/search/mashups/?callback=JSON_CALLBACK"
			} else {
				url = "http://localhost:8080/CollectionsBackendApi/search/mashups/?q=" 
				+ query + "&callback=JSON_CALLBACK"
			}

			$http.jsonp(url)
			.success(function(result) {
				defer.resolve(result);
			})
			.error(function(result) {
				defer.resolve({error:"unable to download feeds"});
			});
			

			return defer.promise;
		}
	};
});