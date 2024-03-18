package io.github.ickoxii.core;

import io.github.ickoxii.interfaces.APIController;

import com.lycoon.clashapi.core.ClashAPI;
import com.lycoon.clashapi.core.auth.dtos.TokenValidation;
import com.lycoon.clashapi.core.auth.dtos.TokenValidationResponse;
import com.lycoon.clashapi.core.exceptions.ClashAPIException;
import com.lycoon.clashapi.core.interfaces.*;
import com.lycoon.clashapi.models.capital.CapitalRaidSeason;
import com.lycoon.clashapi.models.capital.CapitalRanking;
import com.lycoon.clashapi.models.clan.*;
import com.lycoon.clashapi.models.clan.enums.*;
import com.lycoon.clashapi.models.common.*;
import com.lycoon.clashapi.models.league.BuilderBaseLeague;
import com.lycoon.clashapi.models.league.CapitalLeague;
import com.lycoon.clashapi.models.league.League;
import com.lycoon.clashapi.models.league.LeagueSeason;
import com.lycoon.clashapi.models.player.*;
import com.lycoon.clashapi.models.player.enums.*;
import com.lycoon.clashapi.models.war.War;
import com.lycoon.clashapi.models.war.enums.*;
import com.lycoon.clashapi.models.war.WarlogEntry;
import com.lycoon.clashapi.models.warleague.WarLeague;
import com.lycoon.clashapi.models.warleague.enums.*;
import com.lycoon.clashapi.models.warleague.WarLeagueGroup;

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

import java.util.List;
import java.util.ArrayList;

class CocInfoScraper implements APIController {

    private static final boolean DEBUG = true;

    private static final String CLANS_URL = "https://api.clashofclans.com/v1/clans/";
    private static final String PLAYERS_URL = "https://api.clashofclans.com/v1/players/";
    private static final String LEAGUES_URL = "https://api.clashofclans.com/v1/leagues/";

    private static final String LAR_CLAN_TAG = "#902J8QYY";

    private static ClashAPI clashAPI;

    private static final String[] CLAN_TAGS = {
        "#902J8QYY",    // Loot and Run
        "#2YCVJLURP"    // IckoSystem
    };

    private static final String[] PLAYER_TAGS = {
        "#C2J2QRRQ",    // Lost Soul
        "#QLU982QYV",   // Dono Soul
        "#J9RG8PQY",    // Wo Cheng Wei Si Wang
        "#Q89C0GLG2",   // Neo Soul
        "#Q2VVP2V2G",   // Shi Jie De Po Huai Zhe
        "#Q2YRLL8PY",   // Xiao Yang
        "#Q22289JUC",   // th11 Soul
        "#QU0J2P98Y",   // tinysticky
        "#Q22Y0Y9Y8",   // th9 Soul
        "#G9UQPLQRJ",   // Tiny Soul
        // "#Q8J9J0QP"     // th2 Soul
    };

    private static final String API_TOKEN_FILE_NAME = "api-token";

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

            if (DEBUG) System.out.println("Reading token");
            token = br.readLine();
            if (DEBUG) System.out.println("Done reading token");

        } catch(IOException ex) {

            System.err.println("Error reading token");
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            return null;

        }

        return token;
    }

    private void printPlayerInformation(List<Player> players) {

        for(Player player : players) {
            int townhallLevel = player.getTownHallLevel();
            Role clanRole = player.getRole();
            List<Troop> heroes = player.getHeroes();

            System.out.println(player.getName());
            System.out.println("Town Hall: " + townhallLevel);
            System.out.println("Role: " + clanRole.name());
            for(Troop hero : heroes) {
                System.out.println(hero.getName() + ": " + hero.getLevel());
            }

            System.out.println("-----");
        }

    }

    private List<Player> getPlayers(String[] playerTags) {
        List<Player> players = new ArrayList<>();;

        try {

            for(String tag : playerTags) {
                Player player = clashAPI.getPlayer(tag);
                players.add(player);
            }

        } catch (IOException ex) {

            System.err.println("IOException");
            System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();

        } catch (ClashAPIException ex) {

            System.err.println("ClashAPIException");
            System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();
            return null;

        }

        return players;
    }

    public static void main(String args[]) {

        CocInfoScraper scraper = new CocInfoScraper();

        String apiToken = scraper.getAPIToken(API_TOKEN_FILE_NAME);
        if (DEBUG) System.out.println("apiToken: " + apiToken);

        clashAPI = new ClashAPI(apiToken);

        List<Player> players = scraper.getPlayers(PLAYER_TAGS);        

        scraper.printPlayerInformation(players);
    }
}
