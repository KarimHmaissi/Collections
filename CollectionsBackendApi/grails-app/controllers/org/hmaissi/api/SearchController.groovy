package org.hmaissi.api

import grails.converters.JSON
import org.codehaus.jackson.map.ObjectMapper
import org.elasticsearch.client.Client
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.FilterBuilders.*
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryBuilders.*;


class SearchController {

    def elasticSearchService
    def elasticSearchHelper

    def index() {

        SearchResponse esResponse
        println params.q

        if(params.q) {
            println params.q
            elasticSearchHelper.withElasticSearch {Client client ->
                // Do some stuff with the ElasticSearch client
                esResponse = client.prepareSearch()
                        .setTypes("feed")
                        .setSize(100)
                        .setQuery(QueryBuilders.queryString(params.q + "*"))
                        .execute()
                        .actionGet();
            }
        } else {
            println "no query found using default"
            elasticSearchHelper.withElasticSearch {Client client ->
                // Do some stuff with the ElasticSearch client
                esResponse = client.prepareSearch()
                        .setTypes("feed")
                        .setSize(100)
                        .setQuery(QueryBuilders.queryString("*"))
                        .execute()
                        .actionGet();
            }
        }

        if(esResponse != null) {
//            println esResponse

            ObjectMapper mapper = new ObjectMapper()
            String jsonString = mapper.writeValueAsString(esResponse.toString())

            response.contentType = "text/json"
            render "${params.callback}(${jsonString})"
        }  else {
            render "${params.callback}(${[error:"error"] as JSON})"
        }


    }

    def mashups() {

        SearchResponse esResponse
        println params.q

        if(params.q) {
            println params.q
            elasticSearchHelper.withElasticSearch {Client client ->
                // Do some stuff with the ElasticSearch client
                esResponse = client.prepareSearch()
                        .setTypes("feedCollection")
                        .setSize(100)
                        .setQuery(QueryBuilders.queryString(params.q + "*"))
                        .execute()
                        .actionGet();
            }
        } else {
            println "no query found using default"
            elasticSearchHelper.withElasticSearch {Client client ->
                // Do some stuff with the ElasticSearch client
                esResponse = client.prepareSearch()
                        .setTypes("feedCollection")
                        .setSize(100)
                        .setQuery(QueryBuilders.queryString("*"))
                        .execute()
                        .actionGet();
            }
        }

        if(esResponse != null) {
//            println esResponse

            ObjectMapper mapper = new ObjectMapper()
            String jsonString = mapper.writeValueAsString(esResponse.toString())

            response.contentType = "text/json"
            render "${params.callback}(${jsonString})"
        }  else {
            render "${params.callback}(${[error:"error"] as JSON})"
        }


    }
}
