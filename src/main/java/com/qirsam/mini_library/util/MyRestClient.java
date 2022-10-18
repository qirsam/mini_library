package com.qirsam.mini_library.util;

import com.squareup.okhttp.OkHttpClient;
import com.yandex.disk.rest.Credentials;
import com.yandex.disk.rest.OkHttpClientFactory;
import com.yandex.disk.rest.exceptions.http.NotFoundException;
import com.yandex.disk.rest.json.Link;
import com.yandex.disk.rest.retrofit.CloudApi;
import com.yandex.disk.rest.retrofit.ErrorHandlerImpl;
import com.yandex.disk.rest.retrofit.RequestInterceptorImpl;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

import java.net.MalformedURLException;
import java.net.URL;

public class MyRestClient {

    private static final Logger logger = LoggerFactory.getLogger(com.yandex.disk.rest.RestClient.class);

    private static final RestAdapter.LogLevel LOG_LEVEL = logger.isDebugEnabled()
            ? RestAdapter.LogLevel.FULL
            : RestAdapter.LogLevel.NONE;

    private final Credentials credentials;
    private final OkHttpClient client;
    private final String serverURL;
    private final CloudApi cloudApi;
    protected final RestAdapter.Builder builder;

    public MyRestClient(final Credentials credentials) {
        this(credentials, OkHttpClientFactory.makeClient());
    }

    public MyRestClient(final Credentials credentials, final OkHttpClient client) {
        this(credentials, client, "https://cloud-api.yandex.net");
    }

    public MyRestClient(final Credentials credentials, final OkHttpClient client, final String serverUrl) {
        this.credentials = credentials;
        this.client = client;
        try {
            this.serverURL = new URL(serverUrl).toExternalForm();
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }

        this.builder = new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setEndpoint(getUrl())
                .setRequestInterceptor(new RequestInterceptorImpl(credentials.getHeaders()))
                .setErrorHandler(new ErrorHandlerImpl())
                .setLogLevel(LOG_LEVEL);

        this.cloudApi = builder
                .build()
                .create(CloudApi.class);
    }

    /* package */ String getUrl() {
        return serverURL;
    }

    /* package */ OkHttpClient getClient() {
        return client;
    }

    @SneakyThrows
    public String getDownloadLink(String folder, String filename) {
        String path = "app:/images/" + folder + "/" + filename;
        Link link  = null;
        try {
            link = cloudApi.getDownloadLink(path);
        } catch (NotFoundException ex) {
            return getDownloadLink("book", "notFound.png");
        }

        return link.getHref();
    }
}
