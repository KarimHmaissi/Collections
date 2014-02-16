package org.hmaissi.api

import grails.converters.JSON
import org.hmaissi.Feed

class SubmitController {
    def crawlerService

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
}
