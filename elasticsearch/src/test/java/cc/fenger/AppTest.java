package cc.fenger;

import cc.fenger.es.Product;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AppTest {

    // create the low-level client
    public static RestClient restClient = RestClient.builder(
            new HttpHost("10.128.106.94", 9200)
    ).build();

    // create the transport with jackson mapper
    public static ElasticsearchTransport transport = new RestClientTransport(
            restClient, new JacksonJsonpMapper()
    );

    // And create the api client
    public static ElasticsearchClient client = new ElasticsearchClient(transport);

    @Before
    public void init() {

    }

    @Test
    public void testSingleDocumentDSL() throws IOException {
        Product product = new Product("bk-1", "City bike", 123.0);

        IndexResponse response = client.index( i -> i.index("products").id(product.getSku()).document(product));

        System.out.println(response.version());
    }
}