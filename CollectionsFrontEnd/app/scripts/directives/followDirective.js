var mainModule = angular.module("collectionsFrontEndApp");

mainModule.directive("follow", function(feedService) {

   var isFollowed = function(id, feeds) {
      console.log("checking if feed is followed");
      console.log(feeds);
      console.log(id);
      var x = 0;
      for(x; x < feeds.length; x++) {
            console.log(feeds[x].id);
            console.log(id);
            if(feeds[x].id === id) {

                    console.log("feed followed");
                    return true;
            }
      }
      console.log("feed is not followed");
      return false;        
   };

	return {
   	restrict: 'A',
      // template: '<a>Follow</a>',
      // replace: false,
      // scope: true,

      // scope: {
      //       id: "@"
      // },

      // scope: {
      //    value: "@"
      // },

      // template: "{{value}}",

      link: function($scope, element, attrs) {
      	
            // console.log("running follow directive");

            // console.log($scope.user.feeds);
            // var itemId = attrs.id;

            // console.log(parseInt("13254", 10))


            // $scope.$watch('id', function () {
            //         // the following two statements are equivalent
            //         console.log("attrs.id");
            //         console.log(attrs.id);
            // });

            // console.log($scope[attrs.id]);
            // console.log(attrs.id);
            // console.log($scope[attrs.feedId]);
            // console.log(attrs.feedId);

            // console.log($scope[attrs.value]);

            // $scope.$watch(attrs.feedId, function(newValue) {
            //     console.log(newValue);
            // });

            // console.log(attrs);
            // console.log($scope);
            // console.log($scope.feed);
            // console.log(attrs.id);
            // console.log(attrs.class);
            // console.log(attrs["id"]);
            // console.log(parseInt(attrs.id, 10));



            // if(isFollowed(parseInt(attrs.id, 10), $scope.user.feeds)){ 
            //         // || isFollowed(parseInt(attrs.id, 10), $scope.user.collections)) {

            //         element.text("Followed");
            //         element.attr('disabled','disabled');
            // }

            // element.on("click", function(e) {
            //         e.preventDefault();
            //         // console.log("follow clicked: " + attrs.id);
            //         element.text("Followed");
            //         element.attr('disabled','disabled');

            //         feedService.getFeed(parseInt(attrs.id, 10)).then(function(result) {
            //                 if(result) {
            //                         $scope.user.feeds.push(result.feed);
            //                         // console.log($scope.user.feeds);    
            //                 }
                            
            //         });
                    

            // });

      	
      }
   };
});