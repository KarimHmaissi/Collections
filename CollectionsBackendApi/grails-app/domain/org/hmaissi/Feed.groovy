package org.hmaissi

class Feed {
    static hasMany = [posts: Post]

    String feedUrl
    List<Post> posts
    String feedType
    Integer upvotes
    Integer score
    Long submitterId
    String title

    static constraints = {
        posts nullable:true
        feedType nullable:true
        submitterId nullable:true
        feedUrl unique: true
    }
}
