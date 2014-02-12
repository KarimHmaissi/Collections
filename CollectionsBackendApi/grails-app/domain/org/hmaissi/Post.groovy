package org.hmaissi

class Post {

    static belongsTo = [feed: Feed]
    String embedData
    String redditCommentUrl
    Integer numberOfComments
    String link
    String postType
    Date dateCreated

    BigDecimal score

    BigDecimal posted

    static constraints = {
        score(scale:7)
    }
}
