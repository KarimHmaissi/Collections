package org.hmaissi.api

import grails.converters.JSON
import grails.validation.ValidationException
import org.hmaissi.Feed
import org.hmaissi.Post

class FeedController {

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = "score"
        params.order = "desc"

        def feedList =  Feed.list(params)
//        render feedList as JSON
        render "${params.callback}(${feedList as JSON})"
    }

    def get() {
//        if(params.long("id") > 0 && params.long("id") < Feed.count() + 1) {
        if(params.long("id") > 0) {

            def feed = Feed.get(params.id)
            if(feed) {
//                def posts = Post.findAllByFeed(feed)
                //TODO CHANGE THIS!
//                def posts = Post.list(max:300, sort:"posted", order:"desc")
                def posts = Post.list(sort:"posted", order:"score")
                feed.posts = posts

                def json = [
                        "feed": feed,
                        "posts": posts
                ]
                render "${params.callback}(${json as JSON})"
            }  else {
                render "${params.callback}(${[error:"error"] as JSON})"
            }

        } else {
            println "error"
            def error = new LinkedHashMap();
            error.error = "error downloading feed: invalid id"
//            render error as JSON
            render "${params.callback}(${error as JSON})"
        }

    }



}
