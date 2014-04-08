package org.hmaissi.restClient

class RestClient {
	

	def sendRequest() {
		try {
            def http = new HTTPBuilder()

            http.request((feed.feedUrl + "/hot.json"), GET, ContentType.JSON) { req ->

                uri.query = [limit:50]

                headers.'User-Agent' = "Feed crawler bot"
                headers.Accept = 'application/json'

                response.success = { resp, json ->
                    assert resp.statusLine.statusCode == 200
                    println "Got response: ${resp.statusLine}"
                    println "Content-Type: ${resp.headers.'Content-Type'}"
                    save(json, feed)
                }

                response.failure = {
                    return 'error'
                }
            }
        } catch (Exception e) {
            return "error"
        }
	}


}