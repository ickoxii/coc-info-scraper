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
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.function.Function;

import java.io.File;
import java.io.FileWriter;

public class ClanLeaderboardHandler {
    ClashAPI clashAPI;
    Clan clan;
    Set<Player> clanMembers;

    private static final int MAX_TOWN_HALL_LEVEL = 16;

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
    private static final int HEROIC_HEIST = 16;
    private static final int LEAGUE_ALL_STAR = 17;
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
            // Deprecated
            // writeElement(writer, "Versus Trophies", getHighestVersusTrophies());
            // writeElement(writer, "Best Versus Trophies", getHighestBestVersusTrophies());
            writeElement(writer, "Builder Base Trophies", getHighestBuilderBaseTrophies());
            writeElement(writer, "Best Builder Base Trophies", getHighestBestBuilderBaseTrophies());
            writeElement(writer, "Attack Wins", getMostAttackWins());
            writeElement(writer, "Defense Wins", getMostDefenseWins());
            // Deprecated
            // writeElement(writer, "Versus Battle Wins", getMostVersusBattleWins());
            writer.write("-----" + System.lineSeparator());

            writer.write("Highest EXP Levels" + System.lineSeparator());
            Map<Integer, Pair<Integer, List<Player>>> leaders = getHighestEXPLvlPerThLevel();
            for(int i = 1; i <= MAX_TOWN_HALL_LEVEL; ++i) {
                Pair<Integer, List<Player>> currLeaders = leaders.get(i);
                if(currLeaders != null) {
                    writer.write("th " + i + "," + currLeaders.getFirst() + ",");
                    for(Player player : currLeaders.getSecond()) {
                        writer.write(player.getName() + ",");
                    }
                    writer.write(System.lineSeparator());
                }
            }
            writer.write("-----" + System.lineSeparator());

            writer.write("Achievement Leaderboards" + System.lineSeparator());
            writeElement(writer, "Gold Grab", getHighestFromNdx(GOLD_GRAB));
            writeElement(writer, "Elixir Escapade", getHighestFromNdx(ELIXIR_ESCAPADE));
            writeElement(writer, "Heroic Heist", getHighestFromNdx(HEROIC_HEIST));
            writeElement(writer, "Well Seasoned", getHighestFromNdx(WELL_SEASONED));
            writeElement(writer, "Nice and Tidy", getHighestFromNdx(NICE_AND_TIDY));
            writeElement(writer, "Clan War Wealth", getHighestFromNdx(CLAN_WAR_WEALTH));
            writeElement(writer, "Friend in Need", getHighestFromNdx(FRIEND_IN_NEED));
            writeElement(writer, "Sharing is Caring", getHighestFromNdx(SHARING_IS_CARING));
            writeElement(writer, "Siege Sharer", getHighestFromNdx(SIEGE_SHARER));
            writeElement(writer, "War Hero", getHighestFromNdx(WAR_HERO));
            writeElement(writer, "War League Legend", getHighestFromNdx(WAR_LEAGUE_LEGEND));
            writeElement(writer, "Games Champion", getHighestFromNdx(GAMES_CHAMPION));
            writeElement(writer, "Unbreakable", getHighestFromNdx(UNBREAKABLE));
            writeElement(writer, "Sweet Victory", getHighestFromNdx(SWEET_VICTORY));
            writeElement(writer, "Conqueror", getHighestFromNdx(CONQUEROR));
            writeElement(writer, "Humiliator", getHighestFromNdx(HUMILIATOR));
            writeElement(writer, "Not So Easy This Time", getHighestFromNdx(NOT_SO_EASY_THIS_TIME));
            writeElement(writer, "Union Bustor", getHighestFromNdx(UNION_BUSTOR));
            writeElement(writer, "Bust This", getHighestFromNdx(BUST_THIS));
            writeElement(writer, "Mortar Mauler", getHighestFromNdx(MORTAR_MAULER));
            writeElement(writer, "X-Bow Exterminator", getHighestFromNdx(XBOW_EXTERMINATOR));
            writeElement(writer, "Firefighter", getHighestFromNdx(FIREFIGHTER));
            writeElement(writer, "Anti-Artillery", getHighestFromNdx(ANTI_ARTILLERY));
            writeElement(writer, "Shattered and Scattered", getHighestFromNdx(SHATTERED_AND_SCATTERED));
            writeElement(writer, "Counterspell", getHighestFromNdx(COUNTERSPELL));
            writeElement(writer, "Monolith Masher", getHighestFromNdx(MONOLITH_MASHER));
            writeElement(writer, "Superb Work", getHighestFromNdx(SUPERB_WORK));
            writeElement(writer, "Unbuild It", getHighestFromNdx(UNBUILD_IT));
            writeElement(writer, "Champion Builder", getHighestFromNdx(CHAMPION_BUILDER));
            writeElement(writer, "Aggressive Capitalism", getHighestFromNdx(AGGRESSIVE_CAPITALISM));
            writeElement(writer, "Most Valuable Clanmate", getHighestFromNdx(MOST_VALUABLE_CLANMATE));

            // writer.flush();
            writer.close();
        } catch (IOException ex) {
            handle(ex, "IOException writing to leaderboards");
        }
    }

    private void handle(Exception ex, String msg) {
        if (!msg.isEmpty()) System.err.println(msg);
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    }

    private Pair<Integer, List<Player>> getHighestFromNdx(int ndx) {
        int greatest = 0;
        List<Player> leaders = new ArrayList<>();

        for(Player player : clanMembers) {
            try {
                List<Achievement> achievements = player.getAchievements();
                int val = achievements.get(ndx).getValue();
                if(val > greatest) {
                    greatest = val;
                    leaders = new ArrayList<>();
                    leaders.add(player);
                } else if (val == greatest) {
                    leaders.add(player);
                }
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, leaders);
    }

    private Pair<Integer, List<Player>> getHighestFromValueFunction(Function<Player, Integer> vf) {
        int greatest = 0;
        List<Player> leaders = new ArrayList<>();

        for (Player player : clanMembers) {
            try {
                int val = vf.apply(player);
                if (val > greatest) {
                    greatest = val;
                    leaders = new ArrayList<>();
                    leaders.add(player);
                } else if (val == greatest) {
                    leaders.add(player);
                }
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<>(greatest, leaders);
    }

    public ClanLeaderboardHandler(ClashAPI clashAPI_, Clan clan_) {
        clashAPI = clashAPI_;
        clan = clan_;
        clanMembers = new HashSet<>();

        for(ClanMember clanMember : clan.getMemberList()) {
            try {
                Player player = clashAPI.getPlayer(clanMember.getTag());
                clanMembers.add(player);
            } catch (IOException | ClashAPIException | MissingFieldException ex) {
                handle(ex, "");
            }
        }
    }

    // >>>> Current Leaderboards >>>>
    public Pair<Integer, List<Player>> getHighestDonations() {
        return getHighestFromValueFunction(Player::getDonations);
    }

    public Pair<Integer, List<Player>> getHighestDonationsReceived() {
        return getHighestFromValueFunction(Player::getDonationsReceived);
    }

    public Pair<Integer, List<Player>> getHighestTrophies() {
        return getHighestFromValueFunction(Player::getTrophies);
    }

    public Pair<Integer, List<Player>> getHighestBestTrophies() {
        return getHighestFromValueFunction(Player::getBestTrophies);
    }

    public Pair<Integer, List<Player>> getHighestLegendTrophies() {
        int greatest = 0;
        List<Player> leaders = new ArrayList<>();

        for(Player player : clanMembers) {
            try {
                PlayerLegendStatistics pls = player.getLegendStatistics();
                if(pls != null) {
                    int val = pls.getLegendTrophies();
                    if(val > greatest) {
                        greatest = val;
                        leaders = new ArrayList<>();
                        leaders.add(player);
                    }
                }
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return new Pair<Integer, List<Player>>(greatest, leaders);
    }

    public Pair<Integer, List<Player>> getHighestBuilderBaseTrophies() {
        return getHighestFromValueFunction(Player::getBuilderBaseTrophies);
    }

    public Pair<Integer, List<Player>> getHighestBestBuilderBaseTrophies() {
        return getHighestFromValueFunction(Player::getBestBuilderBaseTrophies);
    }

    public Pair<Integer, List<Player>> getMostAttackWins() {
        return getHighestFromValueFunction(Player::getAttackWins);
    }

    public Pair<Integer, List<Player>> getMostDefenseWins() {
        return getHighestFromValueFunction(Player::getDefenseWins);
    }

    public Map<Integer, Pair<Integer, List<Player>>> getHighestEXPLvlPerThLevel() {
        // <th, explvl>
        Map<Integer, Integer> best = new HashMap<>();
        // <th, <explvl, players>>
        Map<Integer, Pair<Integer, List<Player>>> leaders = new HashMap<>();

        for(int i = 1; i <= MAX_TOWN_HALL_LEVEL; ++i) {
            best.put(i, 0);
            leaders.put(i, null);
        }

        for(Player player : clanMembers) {
            try {
                int th = player.getTownHallLevel();
                int explvl = player.getExpLevel();

                if(!best.containsKey(th) || explvl > best.get(th)) {
                    best.put(th, explvl);
                    List<Player> playerList = new ArrayList<>();
                    playerList.add(player);
                    leaders.put(th, new Pair<>(explvl, playerList));
                } else if (explvl == best.get(th)) {
                    Pair<Integer, List<Player>> leaderInfo = leaders.get(th);
                    if(leaderInfo == null) {
                        List<Player> playerList = new ArrayList<>();
                        playerList.add(player);
                        leaders.put(th, new Pair<>(explvl, playerList));
                    } else {
                        leaders.get(th).getSecond().add(player);
                    }
                }
            } catch (MissingFieldException ex) {
                handle(ex, "");
            }
        }

        return leaders;
    }
    // <<<< Current Leaderboards <<<<

    // >>>> Achievement Leaderboards >>>>
    public Pair<Integer, List<Player> > getMostGoldGrab() {
        return getHighestFromNdx(GOLD_GRAB);
    }

    public Pair<Integer, List<Player> > getMostElixirEscapade() {
        return getHighestFromNdx(ELIXIR_ESCAPADE);
    }

    public Pair<Integer, List<Player> > getMostHeroicHeist() {
        return getHighestFromNdx(HEROIC_HEIST);
    }

    public Pair<Integer, List<Player> > getMostWellSeasoned() {
        return getHighestFromNdx(WELL_SEASONED);
    }

    public Pair<Integer, List<Player> > getMostNiceAndTidy() {
        return getHighestFromNdx(NICE_AND_TIDY);
    }

    public Pair<Integer, List<Player> > getMostClanWarWealth() {
        return getHighestFromNdx(CLAN_WAR_WEALTH);
    }

    public Pair<Integer, List<Player> > getMostFriendInNeed() {
        return getHighestFromNdx(FRIEND_IN_NEED);
    }

    public Pair<Integer, List<Player> > getMostSharingIsCaring() {
        return getHighestFromNdx(SHARING_IS_CARING);
    }

    public Pair<Integer, List<Player> > getMostSiegeSharer() {
        return getHighestFromNdx(SIEGE_SHARER);
    }

    public Pair<Integer, List<Player> > getMostWarHero() {
        return getHighestFromNdx(WAR_HERO);
    }

    public Pair<Integer, List<Player> > getMostWarLeagueLegend() {
        return getHighestFromNdx(WAR_LEAGUE_LEGEND);
    }

    public Pair<Integer, List<Player> > getMostGamesChampion() {
        return getHighestFromNdx(GAMES_CHAMPION);
    }

    public Pair<Integer, List<Player> > getMostUnbreakable() {
        return getHighestFromNdx(UNBREAKABLE);
    }

    public Pair<Integer, List<Player> > getMostSweetVictory() {
        return getHighestFromNdx(SWEET_VICTORY);
    }

    public Pair<Integer, List<Player> > getMostConqueror() {
        return getHighestFromNdx(CONQUEROR);
    }

    public Pair<Integer, List<Player> > getMostHumiliator() {
        return getHighestFromNdx(HUMILIATOR);
    }

    public Pair<Integer, List<Player> > getMostNotSoEasyThisTime() {
        return getHighestFromNdx(NOT_SO_EASY_THIS_TIME);
    }

    public Pair<Integer, List<Player> > getMostUnionBuster() {
        return getHighestFromNdx(UNION_BUSTOR);
    }

    public Pair<Integer, List<Player> > getMostBustThis() {
        return getHighestFromNdx(BUST_THIS);
    }

    public Pair<Integer, List<Player> > getMostMortarMauler() {
        return getHighestFromNdx(MORTAR_MAULER);
    }

    public Pair<Integer, List<Player> > getMostXbowExterminator() {
        return getHighestFromNdx(XBOW_EXTERMINATOR);
    }

    public Pair<Integer, List<Player> > getMostFirefighter() {
        return getHighestFromNdx(FIREFIGHTER);
    }

    public Pair<Integer, List<Player> > getMostAntiArtillery() {
        return getHighestFromNdx(ANTI_ARTILLERY);
    }

    public Pair<Integer, List<Player> > getMostShatteredAndScattered() {
        return getHighestFromNdx(SHATTERED_AND_SCATTERED);
    }

    public Pair<Integer, List<Player> > getMostCounterSpell() {
        return getHighestFromNdx(COUNTERSPELL);
    }

    public Pair<Integer, List<Player> > getMostMonolithMasher() {
        return getHighestFromNdx(MONOLITH_MASHER);
    }

    public Pair<Integer, List<Player> > getMostSuperbWork() {
        return getHighestFromNdx(SUPERB_WORK);
    }

    public Pair<Integer, List<Player> > getMostUnbuildIt() {
        return getHighestFromNdx(UNBUILD_IT);
    }

    public Pair<Integer, List<Player> > getMostChampionBuilder() {
        return getHighestFromNdx(CHAMPION_BUILDER);
    }

    public Pair<Integer, List<Player> > getMostAggressiveCapitalism() {
        return getHighestFromNdx(AGGRESSIVE_CAPITALISM);
    }

    public Pair<Integer, List<Player> > getMostMostValuableClanmate() {
        return getHighestFromNdx(MOST_VALUABLE_CLANMATE);
    }
    // <<<< Achievements Leaderboards <<<<
}
