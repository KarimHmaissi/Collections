package org.hmaissi.api

class RedditPost extends Post {

    String redditId
    String author
    Integer redditScore

    String subredditId
    Integer redditDownVotes
    Integer redditUpvotes
//    Date postCreated
    String selfTextMarkup
    Boolean isSelf


    static constraints = {
//        selfTextMarkup(maxSize: 30000)
//        title(maxSize: 305)
//
//        redditId(maxSize: 30000)
//        author(maxSize: 30000)
//        thumbnail(maxSize: 30000)
//        subredditId(maxSize: 30000)
    }

    static mapping = {
        redditId type: "text"
        author type: "text"
        thumbnail type: "text"
        subredditId type: "text"
        selfTextMarkup type: "text"
    }
}
