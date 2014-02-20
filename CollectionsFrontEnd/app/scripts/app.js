'use strict';
angular.module('collectionsFrontEndApp', [
  'ngCookies',
  'ngResource',
  'ngSanitize',
  'ngRoute',
  'jmdobry.angular-cache'
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
      .when("/userFeed/:id", {
        templateUrl: "views/userFeeds.html",
        controller: "UserFeedsCtrl"
      })
      .when("/comments/:url", {
        templateUrl: "views/comments.html",
        controller: "CommentsCtrl"
      })
      .otherwise({
        redirectTo: '/'
      });
  });
