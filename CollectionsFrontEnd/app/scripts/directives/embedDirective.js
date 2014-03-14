var mainModule = angular.module("collectionsFrontEndApp");

mainModule.directive("embed", function(embedUtilityService) {
	return {
        	restrict: 'A',
            replace: false,
            scope: true,

            link: function($scope, element, attrs) {
                var post = $scope.post;

                var embed = null;
                var isImage = false;
                var isVideo = false;
                var isSelf = false;
                var isGallery = false;
                var isEmbedly = false; 
                var largeThumbnail = false;  

                //check type of embed 

                //embed data
                if(post.embedData !== "none") {
                    //Reddit has provided embed data from embedly we can directly embed
                    embed = post.embedData;
                    isEmbedly = true;
                }

                //self
                else if(post.isSelf) {
                    //post is a self post embed selfTextMarkup
                    if(post.selfTextMarkup !== "none") {
                        embed = post.selfTextMarkup;
                        isSelf = true;
                    } else {
                        //empty self post- hide show button
                        element.hide();
                    }
                }

                //img
                else if(embedUtilityService.checkIsImg(post.link)) {
                    //link is an image
                    embed = post.link;
                    isImage = true;
                }

               //video emebed
                else if(attrs.video === "true") {
                    // console.log("youtube video detected");
                    embed = "<iframe width=\"560\" height=\"315\" src=\"//www.youtube.com/embed/" 
                                        + post.videoId + "\" frameborder=\"0\" allowfullscreen></iframe>";
                    isVideo = true;
                }


                else {

                    //Is imgur link? Try and fix 
                    var updatedImgurLink = embedUtilityService.fixImgur(post.link);
                    if(updatedImgurLink) {
                        embed = updatedImgurLink;
                        isImage = true;
                    } else {
                        //test if large thumbnail

                        // var img = new Image();
                        // img.onLoad = function() {
                        //     console.log("loading thumbnail");
                        //     if(img.height > 200) {
                        //         largeThumbnail = true;
                        //         console.log("large thumbnail found");
                        //     } else {
                        //         //no media to embed hide element
                        //         console.log("no content found");
                        //         element.hide();
                        //     }
                        // };

                        // Check original size of thumbnail and if large embed (for comics etc..)
                        var newImage = new Image();
                        newImage.src = post.thumbnail;
                        // console.log(post.link);
                        // console.log(newImage.width);
                        if(newImage.width > 205) {
                            largeThumbnail = true;
                        } else {
                            //no media to embed hide element
                            element.hide();
                        }
                    }
                }


                var shown = false;
                
                element.on("click", function(e) {
                    console.log("show");

                    e.preventDefault();

                    if(!shown) {
                        shown = true;

                        var embedContainer = $("<div/>", {
                            class: "embed-container margin-top-sm"
                        });

                        //this line
                        $(this).text("close");

                        if(isImage) {
                            var embedElement = $('<img src="' + embed + '" />');

                            var fullResoLink = $("<a/>", {
                                href: post.link,
                                text: "View full resolution",
                                class: "row"
                            });

                            embedElement.appendTo(embedContainer);
                            fullResoLink.appendTo(embedContainer);

                            //these lines
                            $(this).children(".feed-preview-img").hide(300);
                            $(this).append(embedContainer);

                            // $(this).closest(".post").append(embedContainer);

                            embedElement.load(function() {
                                //loaded image
                            }).error(function() {
                                //image failed to load
                            });
                        } else if(isEmbedly || isSelf) {
                            //decode html of emebely data 
                            var embedElement = $(embedUtilityService.htmlDecode(embed));

                            embedElement.appendTo(embedContainer);
                            $(this).closest(".post").append(embedContainer);
                        }  else if(isVideo) {
                            //Youtube video
                            var embedElement = $(embed);

                            embedElement.appendTo(embedContainer);


                            //these lines
                            $(this).children(".feed-preview-img").hide(300);
                            $(this).append(embedContainer);

                            
                            // $(this).closest(".post").append(embedContainer);

                        } else if(largeThumbnail) {
                            // console.log("appending large thumbnail");

                            var embedElement = $('<img />').attr({ 'src': post.thumbnail, 'alt':post.title })
                            embedElement.appendTo(embedContainer);
                            $(this).closest(".post").append(embedContainer);
                        }

                    } else {
                        shown = false;
                        $(this).text("show");
                        $(this).closest(".post").find(".embed-container").remove();

                    }
                });//./element.on click
                        
            }
        };
});
