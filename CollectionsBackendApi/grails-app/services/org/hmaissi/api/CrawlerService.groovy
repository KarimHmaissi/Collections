package org.hmaissi.api

import grails.transaction.Transactional
import org.hmaissi.Feed
import org.hmaissi.Post
import java.net.URI
import java.text.SimpleDateFormat

@Transactional
class CrawlerService {

    def embedlyService
    def redditService
    def rssService
    def twitterService
    def youtubeService

    /**
     * If feed does not match any other type it is assumed to be rss
     * @param feedUrl
     * @return
     */
    def getFeedType(String feedUrl) {

        println "url " + feedUrl

        if(feedUrl) {

            //get domain from feedUrl
            URI uri = new URI(feedUrl)
            String domain = uri.getHost()
            String feedDomain = domain.startsWith("www.") ? domain.substring(4) : domain;

            println "domain: " + domain
            println "feedDomain: " + feedDomain

            def feedType = ""

            if(feedDomain.contains("youtube")) {
                feedType = "youtube"
            } else if(feedDomain.contains("reddit")) {
                feedType = "reddit"
            } else if(feedDomain.contains("twitter")) {
                feedType = "twitter"
            } else {
                feedType = "rss"
            }

            return feedType

        }
    }

    def crawlFeed(feedUrl, feedType, title) {
        def feed = new Feed(feedUrl: feedUrl, feedType: feedType, title: title)
        if(feed) {
            switch(feed.feedType) {
                case "reddit":
                    redditService.crawl(feed)
                    break;
                case "rss":
                    rssService.crawl(feed)
                    break;
                case "twitter":
                    twitterService.crawl(feed)
                    break;
                case "youtube":
                    youtubeService.crawl(feed)
                    break;
                default:
                    println("did not recognise feed type: " + feed.feedType)
            }
        }
    }

    def crawlPost(Post post) {
        //check if reddit has post
        //find reddit post with most upvotes
        //add reddit data
        //pull comments from reddit
        //save reddit comments url and cache comments
        //else crawl embedly
        //save embedly data
    }

    def getScore(post, score) {
        def order = Math.log10(Math.max(Math.abs(score), 1))
//        def order = Math.log10(score)
        def sign = 0
        if(score > 0) {
            sign = 1
        } else if(score < 0) {
            sign = -1
        }

        long seconds = post.posted - 1134028003

        def result = order + ((sign * seconds) / 45000)

        return  result


//        Date date = post.posted
//        SimpleDateFormat sdf = new SimpleDateFormat();
//        sdf.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
//        Date utcDate = sdf.parse(sdf.format(date));
//        println post.posted.getTime()



    }
}
