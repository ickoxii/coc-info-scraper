 /**
    Author: Icko Iben
    Date Created: 3/16/2024
    Version: 1.0.0

    This file is part of CocInfoScraper.

    CocInfoScraper is free software: you can redistribute it and/or modify it under
    the terms of the GNU General Public License as published by the Free Software
    Foundation, either version 3 of the License, or (at your option) any later version.

    CocInfoScraper is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
    FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along with
    CocInfoScraper. If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.ickoxii.core;

import io.github.ickoxii.interfaces.api.APIController;
import io.github.ickoxii.interfaces.PlayerServices;
import io.github.ickoxii.interfaces.ClanServices;

import kotlinx.serialization.MissingFieldException;

import com.lycoon.clashapi.core.ClashAPI;
import com.lycoon.clashapi.core.exceptions.ClashAPIException;
import com.lycoon.clashapi.models.clan.*;
import com.lycoon.clashapi.models.league.League;
import com.lycoon.clashapi.models.player.*;
import com.lycoon.clashapi.models.player.enums.*;
// import com.lycoon.clashapi.models.warleague.WarLeague;
// import com.lycoon.clashapi.models.warleague.WarLeagueClan;
// import com.lycoon.clashapi.models.warleague.WarLeagueGroup;
// import com.lycoon.clashapi.models.warleague.WarLeagueMember;
// import com.lycoon.clashapi.models.warleague.WarLeagueRound;
// import com.lycoon.clashapi.models.warleague.enums.*;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import okhttp3.OkHttpClient;
import java.util.logging.Level;
import java.util.logging.Logger;

class CocInfoScraper 
    implements APIController {

    private static final boolean DEBUG = false;

    private static String BASE_OUTPUT_PATH = "target/output/";
    private static final String API_TOKEN_FILE_NAME = "api-token";


    private static final String[] CLAN_TAGS = {
        "#902J8QYY",    // Loot and Run
        "#298CYCUYP",   // DA_NYCTOPHILIA
        "#2YCVJLURP"    // IckoSystem
    };

    private static final String[] ACCOUNT_TAGS = {
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
        "#Q8J9J0QP"     // th2 Soul
    };

    private static ClashAPI clashAPI;
    private static Player Souls;
    private static List<Player> myAccounts;
    private Set<Clan> myClans;

    // >>>> APIController >>>>
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
            handle(ex, "IOException in getAPIToken");
            return null;
        }

        return token;
    }
    // <<<< APIController <<<<

    // >>>> CocInfoScraper >>>>
    public CocInfoScraper() {

        String apiToken = getAPIToken(API_TOKEN_FILE_NAME);
        myClans = new HashSet<>();
        myAccounts = new ArrayList<>();
        if (DEBUG) System.out.println("apiToken: " + apiToken);

        clashAPI = new ClashAPI(apiToken);

        try {
            for(String tag : ACCOUNT_TAGS) {
                Player player = clashAPI.getPlayer(tag);
                myAccounts.add(player);
                PlayerClan pClan = player.getClan();
                String cTag = pClan.getTag();
                Clan clan = clashAPI.getClan(cTag);
                myClans.add(clan);
                Souls = clashAPI.getPlayer(ACCOUNT_TAGS[0]);
            }
            // Souls = clashAPI.getPlayer("#C2J2QRRQ");
        } catch (IOException ex) {
            handle(ex, "IOException getting account");
        } catch (ClashAPIException ex) {
            handle(ex, "ClashAPIException getting account");
        } catch (MissingFieldException ex) {
            handle(ex, "MissingFieldException getting something");
        }
    }

    List<Player> clanMembersToPlayers(List<ClanMember> members) {
        List<Player> players = new ArrayList<>();

        for(ClanMember member : members) {
            String tag = member.getTag();
            try {
                Player player = clashAPI.getPlayer(tag);            
                players.add(player);
            } catch (IOException ex) {
                handle(ex, "IOException in clanMembersToPlayers");
                return null;
            } catch (ClashAPIException ex) {
                handle(ex, "ClashAPIException in clanMembersToPlayers");
                return null;
            }
        }

        return players;
    }

    private static void handle(IOException ex, String msg) {
        if(msg != null) {
            System.err.println(msg);
        }
        System.err.println(ex.getMessage());
        System.err.println("Printing Stack Trace");
        ex.printStackTrace();
    }

    private static void handle(ClashAPIException ex, String msg) {
        if(msg != null) {
            System.err.println(msg);
        }
        System.err.println(ex.getMessage());
        System.err.println("Printing Stack Trace");
        ex.printStackTrace();
    }

    private static void handle(MissingFieldException ex, String msg) {
        if(msg != null) {
            System.err.println(msg);
        }
        System.err.println(ex.getMessage());
        System.err.println("Printing Stack Trace");
        ex.printStackTrace();
    }

    private String getStandardizedClanFileName(Clan clan) {
        String clanName = clan.getName();
        clanName = clanName.replaceAll("[^a-zA-z ]", "");
        clanName = clanName.replaceAll(" ", "-");
        clanName = clanName.toLowerCase();
        String tag = clan.getTag();
        if(!tag.isEmpty() && tag.charAt(0) == '#') {
            tag = tag.substring(1);
        }
        return clanName + "-" + tag + ".csv";
    }
    // <<<< CocInfoScraper <<<<

    public static void main(String args[]) {

        // Dont ask
        Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);

        CocInfoScraper scraper = new CocInfoScraper();

        Set<Clan> myClans = new HashSet<>();
        Set<PlayerClan> myPlayerClans = new HashSet<>();

        // get clans associated w/ my account
        for(String account : ACCOUNT_TAGS) {
            String tag = new String();
            try {
                Player player = clashAPI.getPlayer(account);
                if(player.getClan() != null) {
                    tag = player.getClan().getTag();
                    myPlayerClans.add(player.getClan());
                    try {
                        Clan clan = clashAPI.getClan(tag);
                        myClans.add(clan);
                    } catch (MissingFieldException ex) { 
                        // Just in case theres no FUCKING Clan Capital
                        // Jesus Christ I spent so much of my life trying
                        // to figure out this god-forsaken "ohhh kotlin cant do this, kotlin cant do that" error
                        // how bout "kotlin can suck my ass"
                        System.err.println("Skipping player " + tag);
                    } catch (IOException ex) {
                        handle(ex, "IOException getting clan " + tag);
                    } catch (ClashAPIException ex) {
                        handle(ex, "ClashAPIException getting clan " + tag);
                    }
                }
            } catch (IOException ex) {
                handle(ex, "IOException getting player " + account);
            } catch (ClashAPIException ex) {
                handle(ex, "ClashAPIException getting player " + account);
            }
        }
        System.out.println("-----");

        // Leaderboards
        for(Clan clan : myClans) {
            String fileName = scraper.getStandardizedClanFileName(clan);
            String filePath = BASE_OUTPUT_PATH + fileName;

            ClanLeaderboardHandler clh = new ClanLeaderboardHandler(clashAPI, clan);

            clh.printLeaderboards(filePath);
        }
    }
}
