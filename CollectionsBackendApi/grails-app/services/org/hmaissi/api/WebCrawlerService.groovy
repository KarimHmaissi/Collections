package org.hmaissi.api

import grails.transaction.Transactional
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

@Transactional
class WebCrawlerService {

    static rabbitSubscribe = 'crawler.web'

    def serviceMethod() {
        /*
        Send each post that isn't an image or known type eg articles
        If image and/or description cant be pulled from api
            download page and pull image and/or description
         */
    }

    def getDoc(String htmlFragment) {
        if(htmlFragment != null && htmlFragment != "undefined")  {
            Document doc = Jsoup.parseBodyFragment(htmlFragment);
            return doc

        } else {
            return null
        }

    }

//    def crawlWebPage(String url) {
//        if(url != null || url != "undefined") {
//            Document doc = Jsoup.connect(url).get();
//            def imageUrl = getImages(doc)
//            def description = getDescription(doc)
//        }
//
//    }

    def getImages(Document doc) {
        Elements imageElements = doc.select("img");

        if(imageElements.size() > 0) {
             return imageElements.get(0).attr("src")
        } else {
            return null
        }

    }

    def getDescription(Document doc) {
        Elements textElements = doc.select("p");

//        textElements.get(0).text()

        if(textElements.size() > 0) {
            return textElements.get(0).text()
        } else {
            return null
        }


    }
}
