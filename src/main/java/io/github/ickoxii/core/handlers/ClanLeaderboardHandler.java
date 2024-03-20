 /**
    Author: Icko Iben
    Date Created: 3/20/2024
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

package io.github.ickoxii.core.handlers;

import kotlinx.serialization.MissingFieldException;
import com.lycoon.clashapi.core.exceptions.ClashAPIException;
import java.io.IOException;

import com.lycoon.clashapi.core.ClashAPI;

import com.lycoon.clashapi.models.common.*;
import com.lycoon.clashapi.models.league.BuilderBaseLeague;
import com.lycoon.clashapi.models.league.League;
import com.lycoon.clashapi.models.player.*;
import com.lycoon.clashapi.models.player.enums.*;
import com.lycoon.clashapi.models.capital.ClanCapital;
import com.lycoon.clashapi.models.clan.*;
import com.lycoon.clashapi.models.clan.enums.*;
import com.lycoon.clashapi.models.common.*;
import com.lycoon.clashapi.models.league.CapitalLeague;
import com.lycoon.clashapi.models.warleague.WarLeague;

import io.github.ickoxii.models.Pair;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import java.io.File;
import java.io.FileWriter;

public class ClanLeaderboardHandler {
    ClashAPI clashAPI;
    Clan clan;

    private static final int BIGGER_COFFERS_NDX = 0;
    private static final int GET_THOSE_GOBLINS = 1;
    private static final int BIGGER_AND_BETTER = 2;
    private static final int NICE_AND_TIDY = 3;
    private static final int DISCOVER_NEW_TROOPS = 4;
    private static final int GOLD_GRAB = 5;
    private static final int ELIXIR_ESCAPADE = 6;
    private static final int SWEET_VICTORY = 7;
    private static final int EMPIRE_BUILDER = 8;
    private static final int WALL_BUSTER = 9;
    private static final int HUMILIATOR = 10;
    private static final int UNION_BUSTOR = 11;
    private static final int CONQUEROR = 12;
    private static final int UNBREAKABLE = 13;
    private static final int FRIEND_IN_NEED = 14;
    private static final int MORTAR_MAULER = 15;
    private static final int HEROIC_HEIST = 17;
    private static final int XBOW_EXTERMINATOR = 18;
    private static final int FIREFIGHTER = 19;
    private static final int WAR_HERO = 20;
    private static final int CLAN_WAR_WEALTH = 21;
    private static final int ANTI_ARTILLERY = 22;
    private static final int SHARING_IS_CARING = 23;
    private static final int KEEP_YOUR_ACCOUNT_SAFE = 24;
    private static final int MASTER_ENGINEERING = 25;
    private static final int NEXT_GENERATION_MODEL = 26;
    private static final int UNBUILD_IT = 27;
    private static final int CHAMPION_BUILDER = 28;
    private static final int HIGH_GEAR = 29;
    private static final int HIDDEN_TREASURES = 30;
    private static final int GAMES_CHAMPION = 31;
    private static final int DRAGON_SLAYER = 32;
    private static final int WAR_LEAGUE_LEGEND = 33;
    private static final int KEEP_YOUR_ACCOUNT_SAFE2 = 34;
    private static final int WELL_SEASONED = 35;
    private static final int SHATTERED_AND_SCATTERED = 36;
    private static final int NOT_SO_EASY_THIS_TIME = 37;
    private static final int BUST_THIS = 38;
    private static final int SUPERB_WORK = 39;
    private static final int SIEGE_SHARER = 40;
    private static final int AGGRESSIVE_CAPITALISM = 41;
    private static final int MOST_VALUABLE_CLANMATE = 42;
    private static final int COUNTERSPELL = 43;
    private static final int MONOLITH_MASHER = 44;
    private static final int UNGRATEFUL_CHILD = 45;

    private void writeElement(FileWriter writer,
                              String achievement,
                              Pair<Integer,
                              List<Player>> leaders) throws IOException{
        String playersList = leaders.getSecond().stream().map(Player::getName).collect(Collectors.joining(","));
        writer.write(achievement + "," + leaders.getFirst() + "," + playersList + System.lineSeparator());
        writer.flush();
    }

    public void printLeaderboards(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(clan.getName() + "," + "Clan lvl " + clan.getClanLevel() + System.lineSeparator());
            writer.write("-----" + System.lineSeparator());

            writer.write("Current Leaderboards" + System.lineSeparator());
            writeElement(writer, "Donations", getHighestDonations());
            writeElement(writer, "Donations Received", getHighestDonationsReceived());
            writeElement(writer, "Trophies", getHighestTrophies());
            writeElement(writer, "Best Trophies", getHighestBestTrophies());
            writeElement(writer, "Legend Trophies", getHighestLegendTrophies());
            // writeElement(writer, "Versus Trophies", getHighestVersusTrophies());
            // writeElement(writer, "Best Versus Trophies", getHighestBestVersusTrophies());
            writeElement(writer, "Builder Base Trophies", getHighestBuilderBaseTrophies());
            writeElement(writer, "Best Builder Base Trophies", getHighestBestBuilderBaseTrophies());
            writeElement(writer, "Attack Wins", getMostAttackWins());
            writeElement(writer, "Defense Wins", getMostDefenseWins());
            // writeElement(writer, "Versus Battle Wins", getMostVersusBattleWins());

            writer.write("-----" + System.lineSeparator());
            writer.write("Achievement Leaderboards" + System.lineSeparator());
            writeElement(writer, "Gold Grab", getHighest(GOLD_GRAB));
            writeElement(writer, "Elixir Escapade", getHighest(ELIXIR_ESCAPADE));
            writeElement(writer, "Heroic Heist", getHighest(HEROIC_HEIST));
            writeElement(writer, "Well Seasoned", getHighest(WELL_SEASONED));
            writeElement(writer, "Nice and Tidy", getHighest(NICE_AND_TIDY));
            writeElement(writer, "Clan War Wealth", getHighest(CLAN_WAR_WEALTH));
            writeElement(writer, "Friend in Need", getHighest(FRIEND_IN_NEED));
            writeElement(writer, "Sharing is Caring", getHighest(SHARING_IS_CARING));
            writeElement(writer, "Siege Sharer", getHighest(SIEGE_SHARER));
            writeElement(writer, "War Hero", getHighest(WAR_HERO));
            writeElement(writer, "War League Legend", getHighest(WAR_LEAGUE_LEGEND));
            writeElement(writer, "Games Champion", getHighest(GAMES_CHAMPION));
            writeElement(writer, "Unbreakable", getHighest(UNBREAKABLE));
            writeElement(writer, "Sweet Victory", getHighest(SWEET_VICTORY));
            writeElement(writer, "Conqueror", getHighest(CONQUEROR));
            writeElement(writer, "Humiliator", getHighest(HUMILIATOR));
            writeElement(writer, "Not So Easy This Time", getHighest(NOT_SO_EASY_THIS_TIME));
            writeElement(writer, "Union Bustor", getHighest(UNION_BUSTOR));
            writeElement(writer, "Bust This", getHighest(BUST_THIS));
            writeElement(writer, "Mortar Mauler", getHighest(MORTAR_MAULER));
            writeElement(writer, "X-Bow Exterminator", getHighest(XBOW_EXTERMINATOR));
            writeElement(writer, "Firefighter", getHighest(FIREFIGHTER));
            writeElement(writer, "Anti-Artillery", getHighest(ANTI_ARTILLERY));
            writeElement(writer, "Shattered and Scattered", getHighest(SHATTERED_AND_SCATTERED));
            writeElement(writer, "Counterspell", getHighest(COUNTERSPELL));
            writeElement(writer, "Monolith Masher", getHighest(MONOLITH_MASHER));
            writeElement(writer, "Superb Work", getHighest(SUPERB_WORK));
            writeElement(writer, "Unbuild It", getHighest(UNBUILD_IT));
            writeElement(writer, "Champion Builder", getHighest(CHAMPION_BUILDER));
            writeElement(writer, "Aggressive Capitalism", getHighest(AGGRESSIVE_CAPITALISM));
            writeElement(writer, "Most Valuable Clanmate", getHighest(MOST_VALUABLE_CLANMATE));

            // writer.flush();
            writer.close();
        } catch (IOException ex) {
            handle(ex, "IOException writing to leaderboards");
        }
    }

    private void handle(IOException ex, String msg) {
        if (!msg.isEmpty()) System.err.println(msg);
        System.err.println("IOException in ClanLeaderboardHandler: " + ex.getMessage());
        ex.printStackTrace();
    }

    private void handle(ClashAPIException ex, String msg) {
        if (!msg.isEmpty()) System.err.println(msg);
        System.err.println("ClashAPIException in ClanLeaderboardHandler: " + ex.getMessage());
        ex.printStackTrace();
    }

    private void handle(MissingFieldException ex, String msg) {
        if (!msg.isEmpty()) System.err.println(msg);
        System.err.println("MissingFieldException in ClanLeaderboardHandler: " + ex.getMessage());
        ex.printStackTrace();
    }

    private Pair<Integer, List<Player>> getHighest(int ndx) {
        int greatest = 0;
        List<Player> best = new ArrayList<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                List<Achievement> achievements = player.getAchievements();
                int val = achievements.get(ndx).getValue();
                if(val > greatest) {
                    greatest = val;
                    best = new ArrayList<>();
                    best.add(player);
                }
            } catch (IOException ex) {
                handle(ex, "");
            } catch (ClashAPIException ex) {
                handle(ex, "");
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, best);
    }

    public ClanLeaderboardHandler(ClashAPI clashAPI_, Clan clan_) {
        clashAPI = clashAPI_;
        clan = clan_;
    }

    // >>>> Current Leaderboards >>>>
    public Pair<Integer, List<Player>> getHighestDonations() {
        int greatest = 0;
        List<Player> best = new ArrayList<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                int val = player.getDonations();
                if(val > greatest) {
                    greatest = val;
                    best = new ArrayList<>();
                    best.add(player);
                }
            } catch (IOException ex) {
                handle(ex, "");
            } catch (ClashAPIException ex) {
                handle(ex, "");
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, best);
    }

    public Pair<Integer, List<Player>> getHighestDonationsReceived() {
        int greatest = 0;
        List<Player> best = new ArrayList<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                int val = player.getDonationsReceived();
                if(val > greatest) {
                    greatest = val;
                    best = new ArrayList<>();
                    best.add(player);
                }
            } catch (IOException ex) {
                handle(ex, "");
            } catch (ClashAPIException ex) {
                handle(ex, "");
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, best);
    }

    public Pair<Integer, List<Player>> getHighestTrophies() {
        int greatest = 0;
        List<Player> best = new ArrayList<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                int val = player.getTrophies();
                if(val > greatest) {
                    greatest = val;
                    best = new ArrayList<>();
                    best.add(player);
                }
            } catch (IOException ex) {
                handle(ex, "");
            } catch (ClashAPIException ex) {
                handle(ex, "");
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, best);
    }

    public Pair<Integer, List<Player>> getHighestBestTrophies() {
        int greatest = 0;
        List<Player> best = new ArrayList<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                int val = player.getBestTrophies();
                if(val > greatest) {
                    greatest = val;
                    best = new ArrayList<>();
                    best.add(player);
                }
            } catch (IOException ex) {
                handle(ex, "");
            } catch (ClashAPIException ex) {
                handle(ex, "");
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, best);
    }

    public Pair<Integer, List<Player>> getHighestLegendTrophies() {
        int greatest = 0;
        List<Player> best = new ArrayList<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                PlayerLegendStatistics pls = player.getLegendStatistics();
                if(pls != null) {
                    int val = pls.getLegendTrophies();
                    if(val > greatest) {
                        greatest = val;
                        best = new ArrayList<>();
                        best.add(player);
                    }
                }
            } catch (IOException ex) {
                handle(ex, "");
            } catch (ClashAPIException ex) {
                handle(ex, "");
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, best);
    }

    public Pair<Integer, List<Player>> getHighestBuilderBaseTrophies() {
        int greatest = 0;
        List<Player> best = new ArrayList<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                int val = player.getBuilderBaseTrophies();
                if(val > greatest) {
                    greatest = val;
                    best = new ArrayList<>();
                    best.add(player);
                }
            } catch (IOException ex) {
                handle(ex, "");
            } catch (ClashAPIException ex) {
                handle(ex, "");
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, best);
    }

    public Pair<Integer, List<Player>> getHighestBestBuilderBaseTrophies() {
        int greatest = 0;
        List<Player> best = new ArrayList<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                int val = player.getBestBuilderBaseTrophies();
                if(val > greatest) {
                    greatest = val;
                    best = new ArrayList<>();
                    best.add(player);
                }
            } catch (IOException ex) {
                handle(ex, "");
            } catch (ClashAPIException ex) {
                handle(ex, "");
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, best);
    }

    public Pair<Integer, List<Player>> getHighestVersusTrophies() {
        int greatest = 0;
        List<Player> best = new ArrayList<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                int val = player.getVersusTrophies();
                if(val > greatest) {
                    greatest = val;
                    best = new ArrayList<>();
                    best.add(player);
                }
            } catch (IOException ex) {
                handle(ex, "");
            } catch (ClashAPIException ex) {
                handle(ex, "");
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, best);
    }

    public Pair<Integer, List<Player>> getHighestBestVersusTrophies() {
        int greatest = 0;
        List<Player> best = new ArrayList<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                int val = player.getBestVersusTrophies();
                if(val > greatest) {
                    greatest = val;
                    best = new ArrayList<>();
                    best.add(player);
                }
            } catch (IOException ex) {
                handle(ex, "");
            } catch (ClashAPIException ex) {
                handle(ex, "");
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, best);
    }

    public Pair<Integer, List<Player>> getMostAttackWins() {
        int greatest = 0;
        List<Player> best = new ArrayList<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                int val = player.getAttackWins();
                if(val > greatest) {
                    greatest = val;
                    best = new ArrayList<>();
                    best.add(player);
                }
            } catch (IOException ex) {
                handle(ex, "");
            } catch (ClashAPIException ex) {
                handle(ex, "");
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, best);
    }

    public Pair<Integer, List<Player>> getMostDefenseWins() {
        int greatest = 0;
        List<Player> best = new ArrayList<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                int val = player.getDefenseWins();
                if(val > greatest) {
                    greatest = val;
                    best = new ArrayList<>();
                    best.add(player);
                }
            } catch (IOException ex) {
                handle(ex, "");
            } catch (ClashAPIException ex) {
                handle(ex, "");
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, best);
    }

    public Pair<Integer, List<Player> > getMostVersusBattleWins() {
        int greatest = 0;
        List<Player> best = new ArrayList<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                int val = player.getVersusBattleWins();
                if(val > greatest) {
                    greatest = val;
                    best = new ArrayList<>();
                    best.add(player);
                }
            } catch (IOException ex) {
                handle(ex, "");
            } catch (ClashAPIException ex) {
                handle(ex, "");
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, best);
    }
    // <<<< Current Leaderboards <<<<

    // >>>> Achievement Leaderboards >>>>
    public Pair<Integer, List<Player> > getMostGoldGrab() {
        return getHighest(GOLD_GRAB);
    }

    public Pair<Integer, List<Player> > getMostElixirEscapade() {
        return getHighest(ELIXIR_ESCAPADE);
    }

    public Pair<Integer, List<Player> > getMostHeroicHeist() {
        return getHighest(HEROIC_HEIST);
    }

    public Pair<Integer, List<Player> > getMostWellSeasoned() {
        return getHighest(WELL_SEASONED);
    }

    public Pair<Integer, List<Player> > getMostNiceAndTidy() {
        return getHighest(NICE_AND_TIDY);
    }

    public Pair<Integer, List<Player> > getMostClanWarWealth() {
        return getHighest(CLAN_WAR_WEALTH);
    }

    public Pair<Integer, List<Player> > getMostFriendInNeed() {
        return getHighest(FRIEND_IN_NEED);
    }

    public Pair<Integer, List<Player> > getMostSharingIsCaring() {
        return getHighest(SHARING_IS_CARING);
    }

    public Pair<Integer, List<Player> > getMostSiegeSharer() {
        return getHighest(SIEGE_SHARER);
    }

    public Pair<Integer, List<Player> > getMostWarHero() {
        return getHighest(WAR_HERO);
    }

    public Pair<Integer, List<Player> > getMostWarLeagueLegend() {
        return getHighest(WAR_LEAGUE_LEGEND);
    }

    public Pair<Integer, List<Player> > getMostGamesChampion() {
        return getHighest(GAMES_CHAMPION);
    }

    public Pair<Integer, List<Player> > getMostUnbreakable() {
        return getHighest(UNBREAKABLE);
    }

    public Pair<Integer, List<Player> > getMostSweetVictory() {
        return getHighest(SWEET_VICTORY);
    }

    public Pair<Integer, List<Player> > getMostConqueror() {
        return getHighest(CONQUEROR);
    }

    public Pair<Integer, List<Player> > getMostHumiliator() {
        return getHighest(HUMILIATOR);
    }

    public Pair<Integer, List<Player> > getMostNotSoEasyThisTime() {
        return getHighest(NOT_SO_EASY_THIS_TIME);
    }

    public Pair<Integer, List<Player> > getMostUnionBuster() {
        return getHighest(UNION_BUSTOR);
    }

    public Pair<Integer, List<Player> > getMostBustThis() {
        return getHighest(BUST_THIS);
    }

    public Pair<Integer, List<Player> > getMostMortarMauler() {
        return getHighest(MORTAR_MAULER);
    }

    public Pair<Integer, List<Player> > getMostXbowExterminator() {
        return getHighest(XBOW_EXTERMINATOR);
    }

    public Pair<Integer, List<Player> > getMostFirefighter() {
        return getHighest(FIREFIGHTER);
    }

    public Pair<Integer, List<Player> > getMostAntiArtillery() {
        return getHighest(ANTI_ARTILLERY);
    }

    public Pair<Integer, List<Player> > getMostShatteredAndScattered() {
        return getHighest(SHATTERED_AND_SCATTERED);
    }

    public Pair<Integer, List<Player> > getMostCounterSpell() {
        return getHighest(COUNTERSPELL);
    }

    public Pair<Integer, List<Player> > getMostMonolithMasher() {
        return getHighest(MONOLITH_MASHER);
    }

    public Pair<Integer, List<Player> > getMostSuperbWork() {
        return getHighest(SUPERB_WORK);
    }

    public Pair<Integer, List<Player> > getMostUnbuildIt() {
        return getHighest(UNBUILD_IT);
    }

    public Pair<Integer, List<Player> > getMostChampionBuilder() {
        return getHighest(CHAMPION_BUILDER);
    }

    public Pair<Integer, List<Player> > getMostAggressiveCapitalism() {
        return getHighest(AGGRESSIVE_CAPITALISM);
    }

    public Pair<Integer, List<Player> > getMostMostValuableClanmate() {
        return getHighest(MOST_VALUABLE_CLANMATE);
    }
    // <<<< Achievements Leaderboards <<<<
}
