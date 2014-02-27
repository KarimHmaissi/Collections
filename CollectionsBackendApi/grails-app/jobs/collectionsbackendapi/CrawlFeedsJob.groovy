package collectionsbackendapi


class CrawlFeedsJob {

    def crawlerService

    static triggers = {
      simple repeatInterval: 1200000 // execute job once in 20 minutes
    }

    def execute() {
        // execute job
        crawlerService.crawlAllPosts();
    }
}
