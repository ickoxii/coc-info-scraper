package io.github.ickoxii.interfaces.implementations;

import io.github.ickoxii.interfaces.JWTAPIController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class JWTAPIImpl implements JWTAPIController {

    private static final String CLANS_URL = "https://api.clashofclans.com/v1/clans/";
    private static final String PLAYERS_URL = "https://api.clashofclans.com/v1/players/";
    private static final String LEAGUES_URL = "https://api.clashofclans.com/v1/leagues/";

    private static final String LAR_CLAN_TAG = "#902J8QYY";

    private static final String LOST_SOUL_PLAYER_TAG = "#C2J2QRRQ";

    private static final String API_TOKEN_FILE_NAME = "api-token";

    @Override
    public String getSigningAlgorithm(String token) {
        String algorithm = new String();
        try {
            algorithm = JWT.decode(token).getAlgorithm();
            System.out.println("Algorithm Decoded");
        } catch (JWTDecodeException ex) {
            System.err.println("Error decoding JWT token: " + ex.getMessage());
            return null;
        }
        return algorithm;
    }

    @Override
    public String getAPIToken(String file) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);

        if(is == null) {
            System.err.println("File not found: " + file);
            return null;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String token = new String();


        try {
            token = br.readLine();
            System.out.println("Finished reading token");
        } catch(IOException ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }

        return token;
    }

    JWTAPIImpl() {
        DecodedJWT decodedJWT;

        String apiUrl = CLANS_URL + LAR_CLAN_TAG;
        String apiToken = getAPIToken(API_TOKEN_FILE_NAME);
        System.out.println("apiToken: " + apiToken);

        System.out.println("Signing Algorithm: " + getSigningAlgorithm(apiToken));
    }
}
