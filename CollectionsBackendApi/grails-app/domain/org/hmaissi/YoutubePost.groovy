package org.hmaissi

class YoutubePost extends Post {

    String title
    String videoId
    String thumbnail
    Integer numYoutubeViews
    Integer numYoutubeComments
    String author
    String description


    static constraints = {
        description(maxSize: 10005)
    }
}
