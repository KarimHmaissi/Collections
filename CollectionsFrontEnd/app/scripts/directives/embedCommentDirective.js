var mainModule = angular.module("collectionsFrontEndApp");


mainModule.directive("comment", function() {
    return {
            restrict: 'A',
            replace: false,
            scope: true,

            link: function($scope, element, attrs) { 

              var htmlDecode = function (input){
                var e = document.createElement('div');
                e.innerHTML = input;
                return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
              }  

              console.log("emebed comment");
              $(element).html(htmlDecode(attrs.data));

                       
            }
        };
});