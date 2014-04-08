package org.hmaissi.api

import grails.converters.JSON
import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import org.apache.commons.lang.StringEscapeUtils
import org.jsoup.nodes.Document

import static groovyx.net.http.Method.GET

@Transactional
class TumblrService {

    static rabbitSubscribe = 'crawler.tumblr'

    def crawlerService
    def webCrawlerService

    def crawl(Feed feed) {
        println "about to crawl tumblr feed"

        //TODO check feed is not already in system.
        //http://api.tumblr.com/v2/blog/beatonna.tumblr.com/posts?api_key=ScAlAiCyOlhobflfAh6p0S07pYcyspg9H6h9m8NoCT4R04G5It
        try {
            def blogName = "" //somehow get the blog hostname eg beatonna.tumblr.com
            def http = new HTTPBuilder()

            http.request('http://api.tumblr.com/v2/blog/', GET, ContentType.JSON) { req ->

                uri.query = [api_key: "ScAlAiCyOlhobflfAh6p0S07pYcyspg9H6h9m8NoCT4R04G5It", limit: 20]

                headers.'User-Agent' = "Collections"
                headers.Accept = 'application/json'

                response.success = { resp, json ->
                    assert resp.statusLine.statusCode == 200
                    println "Got response: ${resp.statusLine}"
                    println "Content-Type: ${resp.headers.'Content-Type'}"
                    return buildPosts(json, feed)
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
    def buildPosts(result, Feed feed) {


        def list = result.response.posts
        if(list.size() > 0) {

            def posts = []
            println("about to loop over posts")
            for(int x = 0; x < list.size(); x++) {

                try {
                    TumblrPost post = new TumblrPost();
                    post.feed = feed

                    post.postType = feed.feedType

                    post.numberOfComments = 0

                    post.embedData = "none"
                    post.redditCommentUrl = "none"

                    post.posted = list[x].timestamp

//                    if(list[x].author == null || list[x].author == "") {
//                        post.author="none"
//                    } else {
//                        post.author = list[x].author
//                    }

                    switch(list[x].type) {
                        case "text":
                            break
                        case "photo":
                            break
                        case "quote":
                            break
                        case "link":
                            break
                        case "chat":
                            break
                        case "audio":
                            break
                        case "video":
                            break
                        case "answer":
                            break
                        default:
                            break
                    }

                    post.author = list[x].blog_name
                    post.title = list[x].source_title
                    post.link = StringEscapeUtils.unescapeHtml(list[x].source_url)


                    //add image and description from post content or add to web crawler queue
//                    if(list[x].content) {
//                        String htmlFragment = StringEscapeUtils.unescapeHtml(list[x].content)
//                        Document doc = webCrawlerService.getDoc(htmlFragment)
//
//                        String thumbnail = webCrawlerService.getImages(doc)
//                        String description = webCrawlerService.getDescription(doc)
//
//                        if(thumbnail != null) {
//                            post.description = description
//                            post.thumbnail = thumbnail
////                            post.description = description
//                        } else {
//                            //no thumbnail, request page is crawled by sending message
//                            post.description = ""
//                            post.thumbnail = ""
//                        }
//
//
//                    }



//                    if(list[x].publishedDate) {
//                        post.posted = new Date(list[x].publishedDate).getTime() / 1000
//                    } else {
//                        println "no date :("
//                        post.posted = new Date().getTime()
//                    }

                    post.score = crawlerService.getScore(post, getScore(x, list.size(),  post))

                    posts.add(post)

                } catch (Exception e) {
                    println "error saving rss feed: " + e.message
                    println "error saving rss feed: " + e.toString()
                }
            }

            return posts
        }


    }


    private getScore(index, size, post) {
        def indexReversed = Math.abs(index - size)
        def random = new Random()
        def upperBound = (size * 2) - index
        def lowerBound = indexReversed
        def score = random.nextInt(upperBound - lowerBound ) + lowerBound
        return score * 35
    }


    //called by quartz job
    //send rabbitmq messages
    def crawlAllTumblrPosts() {
        //send rabbitmq message for each post
        println "sending messages"
        def c = Feed.createCriteria()
        def feeds = c.list {
            eq("feedType", "tumblr")
            order("percentNewPosts", "desc")
        }

        for(def feed: feeds) {
            print "sending message: " + feed.title
            def message = [feedUrl: feed.feedUrl, feedType: feed.feedType, feedTitle: feed.title] as JSON
            rabbitSend "crawler.tumblr", "crawler.tumblr.high", message.toString()
        }

    }

    //handle rabbitmq messaging
    void handleMessage(message) {
        println "recieved message: " + message
        def json = JSON.parse(message)
        crawlerService.crawlFeed(json.feedUrl, json.feedType, json.title)

        Thread.sleep(5000)
    }
}
