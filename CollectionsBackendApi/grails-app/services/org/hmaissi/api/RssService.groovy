package org.hmaissi.api

//import com.sun.syndication.feed.synd.SyndContent
import grails.converters.JSON
import grails.transaction.Transactional
import org.hmaissi.Feed

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import org.hmaissi.Post
import org.hmaissi.RssPost

import static groovyx.net.http.Method.GET


@Transactional
class RssService {

    def crawlerService

    def crawl(Feed feed) {
        println "about to crawl rss feed"

        //TODO check feed is not already in system.

        try {
            def http = new HTTPBuilder()

            http.request('http://ajax.googleapis.com/ajax/services/feed/load', GET, ContentType.JSON) { req ->

                uri.query = [q: feed.feedUrl, num: 50, v:"1.0"]

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

    /**
     * Save feed, and posts returned from feed crawled
     * @param result
     * @param feed
     */
    def save(result, Feed feed) {

        try {
            def list = result.responseData.feed.entries
            if(list.size() > 0) {

                def posts = []
                println("about to loop over posts")
                for(int x = 0; x < list.size(); x++) {

                    RssPost post = new RssPost();
                    post.feed = feed
                    post.title = list[x].title
                    post.postType = feed.feedType

                    if(list[x].author == null || list[x].author == "") {
                        post.author="none"
                    } else {
                        post.author = list[x].author
                    }

                    post.link = list[x].link
                    post.numberOfComments = 0

                    post.embedData = "none"
                    post.redditCommentUrl = "none"

                    if(list[x].publishedDate) {
                        post.posted = new Date(list[x].publishedDate).getTime() / 1000
                    } else {
                        println "no date :("
                        post.posted = new Date().getTime()
                    }

                    post.score = crawlerService.getScore(post, getScore(x, list.size(),  post))

                    posts.add(post)

                }

                feed.posts = posts
                feed.upvotes = 1

                Date date = new Date()
                feed.score = feed.upvotes + (date.getTime() / 1000 / 60) //millisecond to seconds to minutes

                feed.save(failOnError: true)
            }

        }   catch(Exception e) {
            println "error saving rss feed: " + e.message
            println "error saving rss feed: " + e.toString()

        }

    }


    private getScore(index, size, post) {
        def indexReversed = Math.abs(index - size)
        def random = new Random()
        def upperBound = (size * 2) - index
        def lowerBound = indexReversed
        def score = random.nextInt(upperBound - lowerBound ) + lowerBound
        return score * 10
    }

}

