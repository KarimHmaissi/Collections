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
//        embedData(maxSize: 30000)
//        embedData(maxSize: 30000)
//        redditCommentUrl(maxSize: 30000)
//        link(maxSize: 30000)
//        postType(maxSize: 30000)
    }

    static mapping = {
//        id composite: ["link", "posted"], type:"string"
//        id name: 'posted'
//        posted(unique: 'link')

        embedData type: "text"
        redditCommentUrl type: "text"
        link type: "text"
        postType type: "text"
    }
}
