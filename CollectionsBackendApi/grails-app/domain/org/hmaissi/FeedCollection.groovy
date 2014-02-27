package org.hmaissi

class FeedCollection {
    static hasMany = [feeds: Feed]

    List<Feed> feeds
    Integer upvotes
    Integer score
    Long submitterId
    String title

    static constraints = {
        feeds nullable:true

        submitterId nullable:true
    }

    static mapping = {
        title type: "text"
    }
}
