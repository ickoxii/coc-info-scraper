package org.ickoxii;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class APIController() {

    public static void main(String[] args) {
        // Create a JWT
        String token = createJWT();
        System.out.println("Generated Token: " + token);

        // Parse and verify the JWT
        DecodedJWT decodedJWT = parseJWT(token);
        if (decodedJWT != null) {
            System.out.println("Decoded JWT: " + decodedJWT);
            // You can access the JWT claims here
            System.out.println("Subject: " + decodedJWT.getSubject());
        } else {
            System.out.println("Invalid JWT");
        }
    }

    // Method to create a JWT
    public static String createJWT() {
        Algorithm algorithm = Algorithm.HMAC256("secret"); // Use your own secret key here
        String token = JWT.create()
                .withSubject("user123")
                .sign(algorithm);
        return token;
    }

    // Method to parse and verify a JWT
    public static DecodedJWT parseJWT(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret"); // Use your own secret key here
            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return jwt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

