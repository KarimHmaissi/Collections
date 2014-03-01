package org.hmaissi.api

import grails.converters.JSON

class UserController {

	def twitterService

    def index() {

    }

    //stubbed
    def getUser() {

    }

    def getTweets() {
    	//stubbed 

    	//get user
    	//check user has logged into twitter
    	//get access token details
    	//send details to service
    	println "api/getTweets"
    	def tweets = twitterService.downloadTweets();
    	def json = [
    		"tweets": tweets
    	]

    	render "${params.callback}(${json as JSON})"


    }

    //stubbed shows all posts (max: 100
    def getFeedsByUser() {

        def posts
        def feeds

        if(params.feedType) {
            posts = Post.findAllByPostType(params.feedType, [sort:"score", order:"desc"])
            feeds = Feed.list()
        } else {
            posts = Post.list(sort:"score", order:"desc", max:100)
            feeds = Feed.list()
        }

        def json = [
                "feeds": feeds,
                "posts": posts
        ]

        render "${params.callback}(${json as JSON})"
    }

    
}
