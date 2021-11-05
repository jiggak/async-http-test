package com.opstack;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

public class Test {
   public static void main(String[] args) {
      test("a");
      test("b");
      test("c");
      test("d");
      System.out.println("got here");
   }

   static void test(String s) {
      var t = new Thread(new Runnable() {
         @Override
         public void run() {
            var config = RequestConfig.custom()
               .setSocketTimeout(60000)
               .setConnectTimeout(60000)
               .setConnectionRequestTimeout(60000)
               .build();

            var clientBuilder = HttpClientBuilder.create()
                    .setDefaultRequestConfig(config);

            try (var client = clientBuilder.build()) {
               var method = new HttpGet(String.format("http://localhost:3000/x/%s", s));
               var response = client.execute(method);
            } catch (Exception e) {
               System.err.println("error " + e);
            }
         }
      });

      System.out.println("run " + s);

      t.start();
   }
}
