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
import com.lycoon.clashapi.models.warleague.WarLeague;
import com.lycoon.clashapi.models.warleague.WarLeagueClan;
import com.lycoon.clashapi.models.warleague.WarLeagueGroup;
import com.lycoon.clashapi.models.warleague.WarLeagueMember;
import com.lycoon.clashapi.models.warleague.WarLeagueRound;
import com.lycoon.clashapi.models.warleague.enums.*;

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
import java.util.Map;
import java.util.HashMap;


class CocInfoScraper 
    implements APIController, 
               PlayerServices, 
               ClanServices {

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
        // "#Q8J9J0QP"     // th2 Soul
    };

    private static ClashAPI clashAPI;

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

    // >>>> PlayerServices >>>>
    @Override
    public void printPlayerInformation(Player player) {
        int townhallLevel = player.getTownHallLevel();
        Role clanRole = player.getRole();

        League league_ = player.getLeague();
        String league = "unranked";

        if(league_ != null) {
            league = league_.getName();
        }

        System.out.println(player.getName() + " | " + league);
        System.out.println("Town Hall: " + townhallLevel);
        System.out.println("Role: " + clanRole.name());

        System.out.println("-----");
    }

    @Override
    public List<Player> getPlayers(String[] playerTags) {
        List<Player> players = new ArrayList<>();

        try {
            for(String tag : playerTags) {
                Player player = clashAPI.getPlayer(tag);
                players.add(player);
            }
        } catch (IOException ex) {
            handle(ex, "IOException in getPlayers");
        } catch (ClashAPIException ex) {
            handle(ex, "ClashAPIException in getPlayers");
            return null;
        }

        return players;
    }
    // <<<< PlayerServices <<<<

    // >>>> ClanServices >>>>
    @Override
    public void printClanInformation(Clan clan) {
        System.out.println(clan.getName());
        System.out.println(clan.getMembers());

        List<ClanMember> members = clan.getMemberList();

        for(ClanMember member : members) {
            System.out.println(member.getName() + ": " + member.getRole());
        }

        System.out.println("-----");
    }

    @Override
    public List<Clan> getClans(String[] clanTags) {
        List<Clan> clans = new ArrayList<>();

        try {
            for(String tag : clanTags) {
                Clan clan = clashAPI.getClan(tag);
                clans.add(clan);
            }
        } catch (IOException ex) {
            handle(ex, "IOException in getClans");
            return null;
        } catch (ClashAPIException ex) {
            handle(ex, "ClashAPIException in getClans");
            return null;
        }

        return clans;

    }
    // <<<< ClanServices <<<<
    
    // >>>> CocInfoScraper >>>>
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

    private String getClanFileName(Clan clan) {
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

        CocInfoScraper scraper = new CocInfoScraper();

        String apiToken = scraper.getAPIToken(API_TOKEN_FILE_NAME);
        if (DEBUG) System.out.println("apiToken: " + apiToken);

        clashAPI = new ClashAPI(apiToken);

        Set<Clan> myClans = new HashSet<>();
        Set<PlayerClan> myPlayerClans = new HashSet<>();

        // Get PlayerClans associated with my accounts
        for(String account : ACCOUNT_TAGS) {
            String tag = new String();
            try {
                Player player = clashAPI.getPlayer(account);
                tag = player.getClan().getTag();
                
            } catch (IOException ex) {
                handle(ex, "IOException getting player " + tag);
            } catch (ClashAPIException ex) {
                handle(ex, "ClashAPIException getting player " + tag);
            }
        }

        // get Clans associated with my accounts
        for(String account : ACCOUNT_TAGS) {
            String tag = new String();
            try {
                Player player = clashAPI.getPlayer(account);
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
                    System.err.println("Skipping " + tag);
                } catch (IOException ex) {
                    handle(ex, "IOException getting clan " + tag);
                } catch (ClashAPIException ex) {
                    handle(ex, "ClashAPIException getting clan " + tag);
                }
            } catch (IOException ex) {
                handle(ex, "IOException getting player " + account);
            } catch (ClashAPIException ex) {
                handle(ex, "ClashAPIException getting player " + account);
            }
        }
        System.out.println("-----");

        for(Clan clan : myClans) {
            // int levelWidth = 3;
            // int nameWidth = 15;
            // String formatted = String.format("%-" + levelWidth + "s%-" + nameWidth + "s %s", clan.getClanLevel(), clan.getName(), clan.getWarLeague().getName());
            // System.out.println(formatted);

            String fileName = "target/output/" + scraper.getClanFileName(clan);
            System.out.println("printing to " + fileName);
            try (FileWriter writer = new FileWriter(fileName)) {
                File outputFile = new File(fileName);
                writer.write("Member Name,Member Tag,Town Hall,War Stars");
                // List<ClanMember> clanMember = scraper.get
            } catch (IOException ex) {
                handle(ex, "Error writting to file " + clan.getName() + ".csv");
            }
        }
        System.out.println("-----");
    }
}
