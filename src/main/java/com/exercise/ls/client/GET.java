package com.exercise.ls.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GET {


    public static void main(String[] args) {

        try {

            /* get Loan by following id*/
            int id = 1;

            /*
               AWS instance for the same:
               http://ec2-3-82-163-56.compute-1.amazonaws.com:8080/api/loans/
             */

            URL url = new URL("http://localhost:8080/api/loans/" + id);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Loan object with ID " + id + ":");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
