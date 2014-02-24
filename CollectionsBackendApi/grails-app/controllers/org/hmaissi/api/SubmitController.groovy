package org.hmaissi.api

import grails.converters.JSON
import org.hmaissi.Feed
import groovy.json.JsonSlurper

class SubmitController {
    def crawlerService

    static allowedMethods = [submitCollection:['POST']]

    def index() {
        def feedUrl = params.feedUrl

        println "submit controller feedUrl: " + feedUrl
        //TODO check feedUrl is valid and generate errors

        try {
            def feedType = crawlerService.getFeedType(feedUrl)
            println "feed type: " + feedType
            crawlerService.crawlFeed(feedUrl, feedType, params.title)
            def message = [message:"successfully added feed"]
            render message as JSON
        } catch(Exception e) {
            def error = [error:"error adding feed"]
            println "error: " + e.message
            render error as JSON
        }
    }

    def submitCollection() {
        try {

            println request.JSON

            //def jsonSlurper = new JsonSlurper()
            //def result = jsonSlurper.parseText(request.JSON)
            println "result parsed: "
            println request.JSON.get("title")
            println request.JSON.get("feeds")


            crawlerService.crawlFeedCollection(request.JSON.get("feeds"), request.JSON.get("title"))
            def message = [message:"successfully added feed"]
            render message as JSON
            
        } catch(Exception e) {
            def error = [error:"error adding feed"]
            println "error: " + e.message
            render error as JSON
        }
    }





    
}
