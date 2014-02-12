"use strict";

var mainModule = angular.module("collectionsFrontEndApp");

mainModule.service("submitService", function($http) {
	return {
		get: function(page) {
			if(!page) {
				var page = 1;

				var promise = $http.get()
					.then(function(result) {

					});

				return promise;
			}
		}
	}
});