package org.ickoxii;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class APIController {
    private static final String CLANS_URL = "https://api.clashofclans.com/v1/clans/";
    private static final String PLAYERS_URL = "https://api.clashofclans.com/v1/players/";
    private static final String LEAGUES_URL = "https://api.clashofclans.com/v1/leagues/";

    private static final String LAR_CLAN_TAG = "#902J8QYY";

    private static final String LOST_SOUL_PLAYER_TAG = "#C2J2QRRQ";

    private static final String API_TOKEN_FILE_NAME = "api-token";

    private String getSigningAlgorithm(String token) {
        String algorithm = new String();
        try {
            algorithm = JWT.decode(token).getAlgorithm();
        } catch (JWTDecodeException ex) {
            System.err.println("Error decoding JWT token: " + ex.getMessage());
        }
        return algorithm;
    }

    private String getAPIToken(String file) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);

        if(is == null) {
            System.err.println("File not found: " + file);
            return null;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String token = new String();


        try {
            token = br.readLine();
        } catch(IOException ex) {
            System.err.println("error reading token");
            ex.printStackTrace();
        }

        return token;
    }

    public static void main(String[] args) {
        APIController apic = new APIController();
        DecodedJWT decodedJWT;

        String apiUrl = CLANS_URL;
        String apiToken = apic.getAPIToken(API_TOKEN_FILE_NAME);
        System.out.println("apiToken: " + apiToken);

        System.out.println("Signing Algorithm: " + apic.getSigningAlgorithm(apiToken));

        try {
            Algorithm algo = Algorithm.HMAC512(apiToken);
            JWTVerifier verifier = JWT.require(algo)
                // specify any specific claim validations
                .withIssuer("auth0")
                // reusable verifier instance
                .build();

            decodedJWT = verifier.verify(apiToken);
            System.out.println("JWT Verification Successful!");
        } catch (JWTVerificationException ex) {
            System.err.println("Error: " + ex.getMessage());
            System.exit(-1);
        }
    }
}
