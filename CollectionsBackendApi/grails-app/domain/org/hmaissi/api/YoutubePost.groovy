package org.hmaissi.api

class YoutubePost extends Post {

    String videoId
    Integer numYoutubeViews
    Integer numYoutubeComments
    String author


//    static constraints = {
//        description(maxSize: 10005)
//    }

    static mapping = {
        videoId type: "text"
        thumbnail type: "text"
        author type: "text"
        description type: "text"
    }
}
