package org.hmaissi.api

import grails.transaction.Transactional

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
        //check if feed is already in DB TODO FINDALLBYLINK would be better
        def feeds = Feed.findAllByFeedUrl(feedUrl)
        def update = false;
        def feed

        if(feeds.size() > 0) {
            feed = feeds.get(0)
            update = true
        } else {
            feed = new Feed(feedUrl: feedUrl, feedType: feedType, title: title)
        }

        def posts = []

        if(feed) {
            switch(feed.feedType) {
                case "reddit":
                    posts = redditService.crawl(feed)
                    break;
                case "rss":
                    posts = rssService.crawl(feed)
                    break;
                case "twitter":
                    posts = twitterService.crawl(feed)
                    break;
                case "youtube":
                    posts = youtubeService.crawl(feed)
                    break;
                default:
                    println("did not recognise feed type: " + feed.feedType)
            }
        }

        if(posts.size() > 0) {

            return saveFeed(feed, posts, update)
        }

        return null
    }

    def crawlFeedCollection(title, feeds) {

        try {
            FeedCollection feedCollection = new FeedCollection()
            feedCollection.upvotes = 1
            Date date = new Date()
            feedCollection.score = feedCollection.upvotes + (date.getTime() / 1000 / 60)
            feedCollection.title = title

            for(def feed : feeds) {
                println feed
                def feedCheck = Feed.get(feed.toInteger())
                def newFeed = null
                print feedCheck
                /*if(feedCheck != null) {
                    newFeed = feedCheck.get(0)
                } else {
                    //newFeed = crawlFeed(feed.feedUrl, getFeedType(feed.feedUrl), feed.title)
                }*/

                //Thread.sleep(6000)
                if(feedCheck != null) {
                    feedCollection.addToFeeds(feedCheck)
                }
            }
            println "saving feed collection"
            return feedCollection.save(failOnError: true).id

        } catch(Exception e) {
            println "error: " + e.message
            println "error: " + e.getCause()
        }

    }

    def saveFeed(Feed feed, posts, update) {
        try {

            //feed is already present, update with new posts
            if(update) {

                def savedPosts = Post.findAllByFeed(feed, [sort:"posted", order:"desc", max:1])

                for(Post post: posts) {

                    //check if post is newer than last post
                    if(savedPosts.size() > 0) {
                        if(post.posted > savedPosts.get(0).posted) {
                            println "new post!"
                            feed.addToPosts(post)
                        }
                    } else {
                        println post.link
                        feed.addToPosts(post)

                    }
                }
                println feed.title
                return feed.save(failOnError: true)

            } else {

                feed.posts = posts
                feed.upvotes = 1
                Date date = new Date()
                feed.score = feed.upvotes + (date.getTime() / 1000 / 60) //millisecond to seconds to minutes

                return feed.save(failOnError: true)
            }

        } catch(Exception e ) {
            println "error: " + e.message
            println "error: " + e.getCause()
        }

    }

    def crawlAllPosts() {
//        def feeds = Feed.list()
//        for(def feed: feeds) {
//            println "crawling feed: " + feed.feedUrl
//            crawlFeed(feed.feedUrl, feed.feedType, feed.title)
//            Thread.sleep(30000)
//        }
    }

//    def savePosts(feed, posts) {
//        for(Post post: posts) {
//            try {
//                post.save(failOnError: true)
//            } catch (Exception e) {
//                println "error: " + e.message
//                println "error: " + e.getCause()
//            }
//        }
//    }

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
