package org.hmaissi

class TweetPost extends Post {

    Long tweetId
    Boolean isRetweet
    String tweetText
    String tweetOwner
    Integer retweetCount
    Date dateCreated

    static constraints = {
    }

    static mapping = {
        tweetText type: "text"
        tweetOwner type: "text"
    }
}
