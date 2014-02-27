"use strict";

var mainModule = angular.module("collectionsFrontEndApp");


mainModule.service("embedUtilityService", function() {
	return {


        htmlDecode: function(input) {
        	if(input !== "undefined" && input !== null) {
        		var e = document.createElement("div");
	          	e.innerHTML = input;
	          	return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
        	}
        },

        containsStr: function(needle, haystack) {
			return (haystack.indexOf(needle) >= 0)
		},

        //change this
        fixImgur: function(url) {
			if (this.containsStr("imgur.com", url)) {
				//check if its a gallery
				url = url.replace('http://imgur.com', 'http://i.imgur.com')
				url = url.replace('/g/memes', '')
				if (this.containsStr("imgur.com/a/", url) === true || this.containsStr("gallery", url) === true) {
					return false
				} else {
					url = url.replace(/(\?.*)|(#.*)|(&.*)/g, "")
					//first remove query parameters from the url

					return url + ".jpg"
				}

			}
			return false;
		},

		checkIsImg: function(url) {
			return (url.match(/\.(jpeg|jpg|gif|png)$/) !== null);
		}

	}
});