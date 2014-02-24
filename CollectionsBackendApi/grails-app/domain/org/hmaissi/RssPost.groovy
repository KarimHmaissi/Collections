package org.hmaissi

class RssPost extends Post {

    String title
    String author

    static constraints = {
    }

    static mapping = {
        author type: "text"
        title type: "text"
    }
}
