package collectionsbackendapi

/**
 * Created by Karim on 09/03/14.
 */
class CrawlTumblrJob {

    def tumblrService

    static triggers = {
        simple repeatInterval: 3600000 // execute job once every hour
    }

    def execute() {
        // execute job
        println "exec quartz job tumblr ..."
        tumblrService.crawlAllTumblrPosts();
    }
}
