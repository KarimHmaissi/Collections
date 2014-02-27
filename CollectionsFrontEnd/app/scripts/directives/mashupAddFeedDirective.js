var mainModule = angular.module("collectionsFrontEndApp");


mainModule.directive("add", function() {
   return {
      restrict: 'A',
      replace: false,
      scope: true,

      link: function($scope, element, attrs) { 
        console.log("add directive running");

        var clicked = false;
        var len = $scope.mashupFeeds.length
        if(len > 0) {
          while (len--) {
            if($scope.mashupFeeds[len].id === attrs.id) {
              element.text("remove");
              clicked = true;
            }   
          }
        }
        

        
         element.on("click", function(e) {
            e.preventDefault();

            if(!clicked) {
               
               element.text("remove");

               $scope.$apply(function() {
                  $scope.mashupFeeds.push({id: attrs.id, feedType: attrs.feedType, title: attrs.title});
               });

               clicked = true;

            } else {

               element.text("Add");

               $scope.$apply(function() {

                  var len = $scope.mashupFeeds.length
                  while (len--) {
                      if($scope.mashupFeeds[len].id === attrs.id) 
                         $scope.mashupFeeds.splice(len, 1);
                  }
                 
               });

               clicked = false;
            }
            
         });
      }
   };
});