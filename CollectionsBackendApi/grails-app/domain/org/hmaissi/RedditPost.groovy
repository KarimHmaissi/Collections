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
    Boolean isSelf


    static constraints = {
        selfTextMarkup(maxSize: 30000)
        title(maxSize: 305)

        redditId(maxSize: 30000)
        author(maxSize: 30000)
        thumbnail(maxSize: 30000)
        subredditId(maxSize: 30000)
    }
}
