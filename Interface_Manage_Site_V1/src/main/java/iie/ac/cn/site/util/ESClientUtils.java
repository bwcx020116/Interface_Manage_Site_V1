package iie.ac.cn.site.util;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import static org.elasticsearch.client.RestClientBuilder.DEFAULT_CONNECT_TIMEOUT_MILLIS;


public class ESClientUtils {
    private static int TIME_OUT =  5 * 60 * 1000;


    /**
     * get client
     *
     * @return
     */
    public  static RestHighLevelClient getClient() {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost[]{new HttpHost("172.16.3.44", 9200, "http"),

                        })
                        .setMaxRetryTimeoutMillis(TIME_OUT)
                        .setHttpClientConfigCallback(httpClientBuilder -> {
                                    RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                                            //超时时间5分钟
                                            .setConnectTimeout(TIME_OUT)
                                            //这就是Socket超时时间设置
                                            .setSocketTimeout(TIME_OUT)
                                            .setConnectionRequestTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS);
                            httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());
                                    return httpClientBuilder;
                                }
                          ))
                ;

        return client;
    }
    /**
     * close client
     *
     * @param client
     */
    public static void close(RestHighLevelClient client) {
        try {
            if(client!=null){
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
