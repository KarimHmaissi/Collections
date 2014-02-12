package org.hmaissi

class RedditPost extends Post {

    String redditId
    String author
    Integer redditScore
    String thumbnail
    String subredditId
    Integer redditDownVotes
    Integer redditUpvotes
//    Date postCreated
    String selfTextMarkup
    String title


    static constraints = {
        selfTextMarkup(maxSize: 10005)
        title(maxSize: 305)
    }
}
