package org.hmaissi.api

import grails.converters.JSON
import grails.validation.ValidationException
import org.hmaissi.Feed
import org.hmaissi.Post

class FeedController {

    def index(Integer max) {
//        params.max = Math.min(max ?: 10, 100)
//        params.sort = "score"
//        params.order = "desc"

        def feedList =  Feed.list()
//        def json = [
//                "feed": feedList
//        ]
//        render json as JSON
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
//                def posts = Post.list(sort:"posted", order:"score")
                  def posts = Post.findAllByFeed(feed)
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


    //stubbed shows all posts (max: 100
    def getByUser() {

        def posts
        def feeds

        if(params.feedType) {
            posts = Post.findAllByPostType(params.feedType, [sort:"posted", order:"desc"])
            feeds = Feed.list()
        } else {
            posts = Post.list(sort:"posted", order:"desc")
            feeds = Feed.list()
        }

        def json = [
                "feeds": feeds,
                "posts": posts
        ]

        render "${params.callback}(${json as JSON})"
    }



}
