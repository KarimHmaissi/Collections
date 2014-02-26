package org.hmaissi.api

import grails.converters.JSON
import org.hmaissi.Feed
import groovy.json.JsonSlurper

class SubmitController {
    def crawlerService

    //static allowedMethods = [submitCollection:['POST']]

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
            //println "result parsed: "
            //println request.JSON.get("title")
            //println request.JSON.get("feeds")


            //def id = crawlerService.crawlFeedCollection(request.JSON.get("feeds"), request.JSON.get("title"))


            println "received submit collection request"
            println params.title
            println params.feeds

            //def slurper = new JsonSlurper()
            //def feeds = slurper.parseText("{feeds: " + params.feeds + "}")
            //println "{feeds: " + params.feeds + "}"
            //println feeds
            def feeds = [params.feeds].flatten().findAll{ it != null }
            println feeds

            def id = crawlerService.crawlFeedCollection(params.title, feeds)

            def message = [message:"successfully added feed", id: id]
            //render message as JSON
            render "${params.callback}(${message as JSON})"
            
        } catch(Exception e) {
            def error = [error:"error adding feed"]
            println "error: " + e.message
            render error as JSON
        }
    }





    
}
