package io.github.ickoxii;

import com.lycoon.clashapi.core.ClashAPI;
import com.lycoon.clashapi.core.exceptions.ClashAPIException;
import com.lycoon.clashapi.models.player.Player;
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
            System.out.println("Algorithm Decoded");
        } catch (JWTDecodeException ex) {
            System.err.println("Error decoding JWT token: " + ex.getMessage());
            return null;
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
            System.out.println("Finished reading token");
        } catch(IOException ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }

        return token;
    }

    public static void main(String[] args) {
        APIController apic = new APIController();
        DecodedJWT decodedJWT;

        String apiUrl = CLANS_URL + LAR_CLAN_TAG;
        String apiToken = apic.getAPIToken(API_TOKEN_FILE_NAME);
        System.out.println("apiToken: " + apiToken);

        ClashAPI clashAPI = new ClashAPI(apiToken);
    }
}
