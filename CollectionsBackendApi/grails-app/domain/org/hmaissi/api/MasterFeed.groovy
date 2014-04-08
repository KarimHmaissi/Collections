package org.hmaissi.api

class MasterFeed {

    Long feedId
    String feedUrl
    Date lastCrawled
    Integer clients
    Integer updates
    Long latestPostId

    static constraints = {
    }
}
