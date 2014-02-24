package org.hmaissi.api

import grails.converters.JSON
import grails.validation.ValidationException
import org.hmaissi.Feed
import org.hmaissi.FeedCollection
import org.hmaissi.Post
import org.hmaissi.utility.CriteriaAggregator

class FeedController {

    def index() {
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

            def feed = Feed.get(params.long("id"))
            if(feed) {
//                def posts = Post.findAllByFeed(feed)
                //TODO CHANGE THIS!
//                def posts = Post.list(max:300, sort:"posted", order:"desc")
//                def posts = Post.list(sort:"posted", order:"score")
                def posts = Post.findAllByFeed(feed, [max:100])
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

    def getCollection() {
        if(params.long("id") > 0) {
            def collection = FeedCollection.get(params.long("id"))

            if(collection) {
//                println collection.feeds.id
//                def feeds = Feed.getAll(collection.feeds.id)
//                def posts = Post.findAllByFeed(collection.feeds.id)
                //find all posts in feed where feed.id = [1,2,3,4 etc]

//                //TODO really needs to be completely rewritten very slow
//                // SOOOOOOO SLOW REWRITE!
//                def feeds = Feed.getAll(collection.feeds.id)
//                def posts = Post.getAll(feeds.id)
//
//                //sort by score
//                posts.sort { a, b ->
//                    a.score <=> b.score
//                }
//
//                //get first 100 posts
//                posts = posts[0..100]
//                //end slow code


                /*def feeds = Feed.getAll(collection.feeds.id)

                
                def postQueryAggregator = new CriteriaAggregator(Post)

                for(def feed : feeds) {
                    postQueryAggregator.addCriteria {
                            eq('feedId', feed.id)
                        }
                    }
                }*/

                def feeds = Feed.getAll(collection.feeds.id)

                def allPosts = []

                for(def feed: feeds) {
                    def posts = Post.findAllByFeed(feed);
                    allPosts += posts
                }

                //sort by score
                allPosts.sort { a, b ->
                    a.score <=> b.score
                }

                //get first 100 posts
                allPosts = allPosts[allPosts.size() - 101..allPosts.size() - 1]



                def json = [
                        "feed": collection,
                        "posts": allPosts
                ]
                render "${params.callback}(${json as JSON})"
            }
        } else {
            render "${params.callback}(${[error:"error"] as JSON})"
        }
    }
}