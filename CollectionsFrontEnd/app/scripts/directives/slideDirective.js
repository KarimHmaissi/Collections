var mainModule = angular.module("collectionsFrontEndApp");

mainModule.directive("slide", function(feedService) {

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

         console.log("slide directive running");

         var $slideLeftClass = $("#" + attrs.left);
         var $slideRightClass = $("#" + attrs.right);
         element.on("click", function(e) {
            e.preventDefault();
            $slideLeftClass.toggleClass("col-md-12");
            $slideLeftClass.toggleClass("col-md-9");


            $slideRightClass.toggleClass("hide");
         });
      }
   };
});