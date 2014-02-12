package org.hmaissi.api

import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import org.hmaissi.Feed
import org.hmaissi.RedditPost
import org.hmaissi.YoutubePost

import java.text.DateFormat

import static groovyx.net.http.Method.GET

/**
 * Decided not to use the youtube java libraries and use http builder to download directly.
 */
@Transactional
class YoutubeService {

    def crawlerService

    def crawl(Feed feed) {
        try {
            println "about to crawl youtube"

            def http = new HTTPBuilder()

            def channelName = getChannelNameFromUrl(feed.feedUrl)

            println "channelName: " + channelName

            http.request('http://gdata.youtube.com/feeds/api/users/'+channelName+'/uploads?alt=json&max-results=50', GET, ContentType.JSON) { req ->

                headers.'User-Agent' = "Collections"
                headers.Accept = 'application/json'

                response.success = { resp, json ->
                    assert resp.statusLine.statusCode == 200
                    println "Got response: ${resp.statusLine}"
                    println "Content-Type: ${resp.headers.'Content-Type'}"
                    save(json, feed)
                }

                response.failure = {
                    println "error downloading from youtube"
                    return 'error'
                }
            }
        } catch (Exception e) {
            println "error: " + e.message
            println "error: " + e.getCause()
            return "error"
        }
    }

    def save(result, feed) {
        def posts = []
        println "saving youtube feed"

        try {
            for(def video : result.feed.entry) {

                def post = new YoutubePost()
                post.feed = feed
                post.embedData = "none"
                post.redditCommentUrl = "none"
                post.numberOfComments = 0
                post.link = video.link[0].href
                post.postType = "youtube"

                //http://stackoverflow.com/questions/2201925/converting-iso8601-compliant-string-to-java-util-date
                post.posted = javax.xml.bind.DatatypeConverter.parseDateTime(video.published.$t).getTime().getTime() / 1000

                post.title = video.title.$t
                post.videoId = getIdFromUrl(video.id.$t)

                if(video.media$group.media$thumbnail.size() > 0) {
                    post.thumbnail = video.media$group.media$thumbnail[0].url
                } else {
                    post.thumbnail = "none"
                }

                post.numYoutubeViews = Integer.parseInt(video.yt$statistics.viewCount)
                post.numYoutubeComments = video.gd$comments.gd$feedLink.countHint
                post.author = video.author[0].name.$t
                post.description = video.media$group.media$description.$t

                post.score = crawlerService.getScore(post, getScore(post.numYoutubeViews))

                posts.add(post)
            }

            feed.posts = posts
            feed.upvotes = 1

            Date date = new Date()
            feed.score = feed.upvotes + (date.getTime() / 1000 / 60) //millisecond to seconds to minutes

            feed.save(failOnError: true)

        } catch(Exception e) {
           println "error: " + e.message
           println "error: " + e.getCause()
            println e
        }
    }

    private getChannelNameFromUrl(String url) {
        String filePart = new URL(url).getFile();
        def fileParts = filePart.split("/")
        if(fileParts.length > 2) {
            filePart = fileParts[2]
        } else {
            filePart = fileParts[1]
        }

//        filePart = filePart.replace("/", "")
        return filePart
    }

    private getIdFromUrl(String url) {
        String filePart = new URL(url).getFile();
        def fileParts = filePart.split("/")
        return fileParts[4]
    }

    private getScore(numViews) {
        if(numViews == 301) {
            return 1000
        }
        if(numViews / 2000 > -1) {
            return numViews / 2000
        }

        return 50
    }



}
