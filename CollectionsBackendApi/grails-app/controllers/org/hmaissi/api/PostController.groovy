package org.hmaissi.api

import grails.converters.JSON
import org.hmaissi.Feed
import org.hmaissi.Post

class PostController {

    def get() {
       if(params.long("id") > 0 && params.long("id") < Post.count() + 1) {

           render Post.get(params.long("id")) as JSON
       } else {
           println "error"
           def error = new LinkedHashMap();
           error.error = "error downloading post: invalid id"
           render error as JSON
       }
    }

    //return all posts by feedID
//    def getByFeed()              {
//        if(params.long("id") > 0) {
//            def posts = Post.findAllByFeed(Feed.get(params.long("id")))
//
//            render posts as JSON
//        } else {
//            def error = new LinkedHashMap();
//            error.error = "error downloading post: invalid id"
//            render error as JSON
//        }
//    }
}
