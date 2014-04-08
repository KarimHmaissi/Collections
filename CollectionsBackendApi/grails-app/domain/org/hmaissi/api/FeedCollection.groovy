package org.hmaissi.api

class FeedCollection {
    static hasMany = [feeds: Feed, tags: Tag]

    static searchable = {
        except = 'feeds'
        tags component:true
    }

    List<Feed> feeds
    Integer upvotes
    Integer score
    Long submitterId
    String title

    List<Tag> tags

    String thumbnail1
    String thumbnail2
    String thumbnail3
    String thumbnail4

    static constraints = {
        feeds nullable:true

        submitterId nullable:true

        thumbnail1 nullable: true
        thumbnail2 nullable: true
        thumbnail3 nullable: true
        thumbnail4 nullable: true
    }

    static mapping = {
        title type: "text"
    }
}
