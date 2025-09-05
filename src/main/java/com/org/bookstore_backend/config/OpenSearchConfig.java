/*
package com.org.bookstore_backend.config;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
// ⭐ REVISION: Correct import for RestClient
import org.opensearch.client.RestClient;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.transport.OpenSearchTransport;
// ⭐ REVISION: Correct import for RestClientTransport (note 'restclient' without underscore)
import org.opensearch.client.transport.restclient.RestClientTransport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@Configuration
public class OpenSearchConfig {

    @Value("${spring.elasticsearch.uris}")
    private String openSearchUri;

    @Value("${spring.elasticsearch.username}")
    private String openSearchUsername;

    @Value("${spring.elasticsearch.password}")
    private String openSearchPassword;

    */
/**
     * Configures and provides the low-level RestClient for OpenSearch.
     * This client handles HTTP communication and authentication.
     *
     * @return Configured RestClient instance.
     * @throws NoSuchAlgorithmException if the SSLContext algorithm is not found.
     * @throws KeyManagementException   if there is an issue with SSL key management.
     *//*

    @Bean
    public RestClient getRestClient() throws NoSuchAlgorithmException, KeyManagementException {
        // Create a credentials provider for basic authentication
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(openSearchUsername, openSearchPassword));

        // Create a trust manager that accepts all certificates (for self-signed or development environments)
        // In production, you should use a proper trust strategy with trusted CAs.
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        // Initialize SSLContext with the custom trust manager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        // Build the RestClient with authentication and SSL context
        return RestClient.builder(
                        new HttpHost(openSearchUri.replace("https://", ""), 443, "https"))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder
                                .setDefaultCredentialsProvider(credentialsProvider)
                                .setSSLContext(sslContext) // Set the custom SSL context
                                .setSSLHostnameVerifier((hostname, session) -> true) // Disable hostname verification for self-signed certs
                )
                .build();
    }

    */
/**
     * Configures and provides the high-level OpenSearchClient.
     * This client uses the RestClient for transport and provides a convenient API
     * for interacting with OpenSearch.
     *
     * @param restClient The low-level RestClient bean.
     * @return Configured OpenSearchClient instance.
     *//*

    @Bean
    public OpenSearchClient openSearchClient(RestClient restClient) {
        // Create the transport with a Jackson JSON mapper
        OpenSearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // Create the API client
        return new OpenSearchClient(transport);
    }
}*/
