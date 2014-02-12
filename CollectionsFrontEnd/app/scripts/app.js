'use strict';

angular.module('collectionsFrontEndApp', [
  'ngCookies',
  'ngResource',
  'ngSanitize',
  'ngRoute'
])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'FeedsCtrl'
      })
      .when("/feed/:id", {
        templateUrl: "views/feed.html",
        controller: "FeedCtrl"
      })
      .otherwise({
        redirectTo: '/'
      });
  });
