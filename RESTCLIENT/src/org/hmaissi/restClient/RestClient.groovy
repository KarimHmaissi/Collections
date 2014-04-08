package org.hmaissi.restClient

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.TEXT

class RestClient {

    def sendRequest(feedUrl, title) {
        try {
            def http = new HTTPBuilder()

            http.request(("http://localhost:8080/CollectionsBackendApi/submit?"), GET, ContentType.JSON) { req ->

                uri.query = [feedUrl: feedUrl, title: title]

                headers.'User-Agent' = "Feed crawler bot"
                headers.Accept = 'application/json'

                response.success = { resp, json ->
                    assert resp.statusLine.statusCode == 200
                    println "Got response: ${resp.statusLine}"
                    println "Content-Type: ${resp.headers.'Content-Type'}"
                }

                response.failure = {
                    return 'error'
                }
            }
        } catch (Exception e) {
            return "error"
        }
    }


    def run() {
        def feeds = Feeds.feeds

        for(def feed: feeds) {
            sendRequest(feed.feedUrl, feed.title)
            Thread.sleep(6000)
        }
    }

    static void main(String[] args) {
        RestClient client = new RestClient()
        client.run()
    }


}