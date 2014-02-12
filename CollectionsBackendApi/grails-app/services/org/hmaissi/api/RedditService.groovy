package org.hmaissi.api

import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import org.hmaissi.Feed
import org.hmaissi.RedditPost

import java.text.SimpleDateFormat

import static groovyx.net.http.Method.GET

@Transactional
class RedditService {

    def crawlerService

    def crawl(Feed feed) {
        try {
            def http = new HTTPBuilder()

            http.request((feed.feedUrl + "/hot.json"), GET, ContentType.JSON) { req ->

                uri.query = [limit:50]

                headers.'User-Agent' = "Collections"
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

            try{
                for(def redditPost: result.data.children) {

                    def post = new RedditPost()
                    post.feed = feed
                    post.embedData = "none"
                    post.redditCommentUrl = "http://reddit.com" + redditPost.data.permalink
                    post.numberOfComments = redditPost.data.num_comments
                    post.link = redditPost.data.url
                    post.postType = feed.feedType

                    //convert reddit utc timestamp to date object
//                    long redditDate = redditPost.data.created_utc
//                    def longDate = redditDate.longValue()
                    //java expects milliseconds
//                    longDate *= 1000

//                    println redditDate
//                    Date date = new Date(redditDate * 1000)
//                    println date.toString()


//                    SimpleDateFormat sdf = new SimpleDateFormat();
//                    sdf.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
//                    Date utcDate = sdf.parse(sdf.format(date));
//
//                    println utcDate.getTime()
//                    println "space"

                    post.posted = redditPost.data.created_utc

                    post.author = redditPost.data.author
                    post.redditScore = redditPost.data.score
                    post.thumbnail = redditPost.data.thumbnail
                    post.subredditId = redditPost.data.subreddit_id
                    post.redditDownVotes = redditPost.data.downs
                    post.redditUpvotes = redditPost.data.ups
                    post.title = redditPost.data.title


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

                feed.posts = posts
                feed.upvotes = 1

                Date date = new Date()
                feed.score = feed.upvotes + (date.getTime() / 1000 / 60) //millisecond to seconds to minutes

                feed.save(failOnError: true)

            } catch(Exception e) {
                println "error saving:" + e.message
                println "error saving:" + e.toString()
            }
        }
    }

}
