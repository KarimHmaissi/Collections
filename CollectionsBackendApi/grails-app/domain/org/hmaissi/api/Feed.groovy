package org.hmaissi.api

class Feed {
    static hasMany = [posts: Post, tags: Tag]
    static searchable = {
        except = 'posts'
//        tags reference: true
        tags component:true
    }

    String feedUrl
    List<Post> posts
    List<Tag> tags
    String feedType
    Integer upvotes
    Integer score
    Long submitterId
    String title

    Integer updates
    Integer percentNewPosts //percent of new posts each crawl

    Date lastCrawled
    Integer clients

    String thumbnail1
    String thumbnail2
    String thumbnail3
    String thumbnail4

    String lastPostTitle

    static constraints = {
        posts nullable:true
        feedType nullable:true
        submitterId nullable:true
        feedUrl unique: true
        thumbnail1 nullable: true
        thumbnail2 nullable: true
        thumbnail3 nullable: true
        thumbnail4 nullable: true
        lastPostTitle nullable: true
    }

    static mapping = {
        feedUrl type: "text"
        feedType type: "text"
        title type: "text"

        thumbnail1 type: "text"
        thumbnail2 type: "text"
        thumbnail3 type: "text"
        thumbnail4 type: "text"
        lastPostTitle type: "text"
    }
}
