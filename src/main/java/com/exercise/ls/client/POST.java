package com.exercise.ls.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class POST {


    public static void main(String[] args) {

        try {

            URL url = new URL("http://localhost:8080/api/loans/");

            /*
               AWS instance for the same:
               http://ec2-3-82-163-56.compute-1.amazonaws.com:8080/api/loans/
             */

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String json = "{\"amount\":555,\"interestRate\":15,\"length\":6,\"monthlyPayment\":30.0}";

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
