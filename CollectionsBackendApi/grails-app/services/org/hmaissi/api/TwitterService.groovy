package org.hmaissi.api

import grails.transaction.Transactional
import org.hmaissi.Feed
import org.hmaissi.TweetPost
import twitter4j.Paging
import twitter4j.Query
import twitter4j.QueryResult
import twitter4j.ResponseList
import twitter4j.Status

import twitter4j.Twitter
import twitter4j.TwitterException

@Transactional
class TwitterService {

    def twitter4jService

    def downloadTweets() {
        //stubbed requires auth etc..
        Twitter twitter = twitter4jService.connect()

        List<Status> statuses = twitter.getHomeTimeline()

        def jsonStatuses = []

        for(def status: statuses) {
            //text
            //tweetowner
            //link
            //posted
            def jsonStatus = [
                tweetText: status.getText(),
                tweetOwner: status.getUser().getScreenName(),
                tweetLink: "http://twitter.com/status/" + status.getId(),
                tweetId: status.getId() + "",
                posted: status.getCreatedAt(),
            ]

            println status.getId()

            jsonStatuses.add(jsonStatus)

        }
        
        return jsonStatuses


    }    

    /*def crawlerService

    def crawl(Feed feed) {

        def twitterUsername =  getTwitterUsernameFromUrl(feed.feedUrl)

        if(twitterUsername != null & twitterUsername != "") {

            try {
                def tweets = twitter4jService.getUserTimeline(twitterUsername, new Paging(1, 50));
                if (tweets.size() != 0 & tweets != null) {
                    save(tweets, feed)
                }
            } catch(Exception e) {
                println "error downloading tweets: " + e.message
            }
        }


    }

    def save(result, feed) {
        def posts = []

        try {

            for(Status status: result) {
                def post = new TweetPost()
                post.feed = feed
                post.tweetText = status.getText()
                post.tweetOwner = status.getUser().getScreenName()
                post.link = "https://twitter.com/" + post.tweetOwner + "/status/" + status.getId()
                post.numberOfComments = 0
                post.embedData = "none"
                post.redditCommentUrl = "none"
                post.postType = feed.feedType

                post.tweetId = status.getId()
                post.isRetweet = status.isRetweet()
                post.posted = status.getCreatedAt()
                post.retweetCount = status.getRetweetCount()

                post.score = crawlerService.getScore(post, 50)

                posts.add(post)

            }

            feed.posts = posts
            feed.upvotes = 1

            Date date = new Date()
            feed.score = feed.upvotes + (date.getTime() / 1000 / 60) //millisecond to seconds to minutes

            feed.save(failOnError: true)

        } catch(Exception e) {
            println "error saving tweets:" + e.message
        }
    }

    private getTwitterUsernameFromUrl(String url) {
        String filePart = new URL(url).getFile();
        filePart = filePart.replace("/", "")
        println filePart
        return filePart
    }*/

}
