package collectionsbackendapi

import grails.converters.JSON
import org.hmaissi.api.Feed
import grails.transaction.Transactional

@Transactional
class CrawlRssJob {



    def rssService

    static triggers = {
        simple repeatInterval: 3600000 // execute job once every hour
    }

    def execute() {
        // execute job
        println "exec quartz job rss ..."
        rssService.crawlAllRssPosts();
    }



}
