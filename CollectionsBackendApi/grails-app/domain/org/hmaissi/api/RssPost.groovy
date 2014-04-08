package org.hmaissi.api

class RssPost extends Post {

    String author

    static constraints = {
    }

    static mapping = {
        author type: "text"
    }
}
