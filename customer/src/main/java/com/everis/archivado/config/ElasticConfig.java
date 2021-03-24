package com.everis.archivado.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(RestHighLevelClient.class)
public class ElasticConfig {

    @Value("${elasticsearch.host}")
    private String EsHost;

    @Value("${elasticsearch.port}")
    private int EsPort;

    @Value("${elasticsearch.clustername}")
    private String EsClusterName;

    @Value("${elasticsearch.http.security.user}")
    private String EsHttpSecurityUser;

	@Value("${elasticsearch.http.security.pass}")
	private String EsHttpSecurityPass;
	
	@Value("${elasticsearch.request.timeout}")
	private int CTE_REQUEST_TIMEOUT;

	@Bean(destroyMethod = "close")
	public RestHighLevelClient client() throws Exception {
		final CredentialsProvider credentialsProvider =	new BasicCredentialsProvider();
		
		credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(EsHttpSecurityUser, EsHttpSecurityPass));
		
		RestClientBuilder builder = RestClient.builder( new HttpHost(EsHost, EsPort,"http"))	
				.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
				.setRequestConfigCallback(
			        new RestClientBuilder.RequestConfigCallback() {
			            @Override
			            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
			                return requestConfigBuilder
			                    .setConnectTimeout(CTE_REQUEST_TIMEOUT)
			                    .setSocketTimeout(CTE_REQUEST_TIMEOUT);
			            }
			        })
				;

        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;

    }
}



