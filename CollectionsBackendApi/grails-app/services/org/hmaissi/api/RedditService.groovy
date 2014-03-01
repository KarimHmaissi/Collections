package org.hmaissi.api

import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.Method.GET

@Transactional
class RedditService {

    def crawlerService

    def crawl(Feed feed) {
        try {
            def http = new HTTPBuilder()

            http.request((feed.feedUrl + "/hot.json"), GET, ContentType.JSON) { req ->

                uri.query = [limit:50]

                headers.'User-Agent' = "Feed crawler bot"
                headers.Accept = 'application/json'

                response.success = { resp, json ->
                    assert resp.statusLine.statusCode == 200
                    println "Got response: ${resp.statusLine}"
                    println "Content-Type: ${resp.headers.'Content-Type'}"
                    save(json, feed)
                }

                response.failure = {
                    return 'error'
                }
            }
        } catch (Exception e) {
            return "error"
        }
    }

    def save(result, feed) {
        if(result.data.children.size() > 0) {

            def posts = []


            for(def redditPost: result.data.children) {
                try{
                    def post = new RedditPost()
                    post.feed = feed

                    if(!(redditPost.data.media == null)) {
                        post.embedData = redditPost.data.media.oembed.html
//                        post.embedData = "none"
                    } else {
                        post.embedData = "none"
                    }

                    post.redditCommentUrl = "http://reddit.com" + redditPost.data.permalink
                    post.numberOfComments = redditPost.data.num_comments
                    post.link = redditPost.data.url
                    post.postType = feed.feedType

                    post.posted = redditPost.data.created_utc

                    post.author = redditPost.data.author
                    post.redditScore = redditPost.data.score
                    post.thumbnail = redditPost.data.thumbnail
                    post.subredditId = redditPost.data.subreddit_id
                    post.redditDownVotes = redditPost.data.downs
                    post.redditUpvotes = redditPost.data.ups
                    post.title = redditPost.data.title
                    post.isSelf = redditPost.data.is_self

                    if(redditPost.data.selftext_html == "" || redditPost.data.selftext_html == null) {
                        post.selfTextMarkup = "none"
                    } else {

                        //TODO split markup to below set size to prevent exceptions
                        post.selfTextMarkup = redditPost.data.selftext_html
                    }

                    post.redditId = redditPost.data.id

                    post.score = crawlerService.getScore(post, post.redditScore)

                    posts.add(post)
                }

                catch(Exception e) {
                    println "error saving:" + e.message
                    println "error saving:" + e.toString()
                }
            }

            return posts

        }
    }
}


