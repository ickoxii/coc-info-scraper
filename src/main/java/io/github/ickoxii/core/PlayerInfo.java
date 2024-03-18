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

import com.lycoon.clashapi.models.common.*;
import com.lycoon.clashapi.models.league.BuilderBaseLeague;
import com.lycoon.clashapi.models.league.League;
import com.lycoon.clashapi.models.player.*;
import com.lycoon.clashapi.models.player.enums.*;

import java.util.List;

public class PlayerInfo {
    private String tag;
    private String name;
    private PlayerHouse playerHouse;
    private PlayerClan clan;
    private Role role;
    private int expLevel;
    private int trophies;
    private int builderHallLevel;
    private int townHallWeaponLevel;
    private int townHallLevel;
    private WarPreference warPreference;
    private int warStars;
    private List<Achievement> achievements;
    private List<Label> labels;
    private int clanCapitalContributions;
    
    private BuilderBaseLeague builderBaseLeague;
    private League league;
    private PlayerLegendStatistics legendStatistics;

    private List<Troop> troops;
    private List<Troop> heroes;
    private List<Troop> heroEquipment;
    private List<Troop> spells;

    private int bestTrophies;
    private int builderBaseTrophies;
    private int bestBuilderBaseTrophies;
    private int versusTrophies;
    private int bestVersusTrophies;

    private int attackWins;
    private int defenseWins;
    private int versusBattleWins;
    private int donations;
    private int donationsreceived;

    public PlayerInfo(Player player) {
        tag = player.getTag();
        name = player.getName();
        playerHouse = player.getPlayerHouse();
        clan = player.getClan();
        role = player.getRole();
        expLevel = player.getExpLevel();
        trophies = player.getTrophies();
        builderHallLevel = player.getBuilderHallLevel();
        townHallWeaponLevel = player.getTownHallWeaponLevel();
        townHallLevel = player.getTownHallLevel();
        warPreference = player.getWarPreference();
        warStars = player.getWarStars();
        achievements = player.getAchievements();
        labels = player.getLabels();
        clanCapitalContributions = player.getClanCapitalContributions();

        builderBaseLeague = player.getBuilderBaseLeague();
        league = player.getLeague();
        legendStatistics = player.getLegendStatistics();

        troops = player.getTroops();
        heroes = player.getHeroes();
        heroEquipment = player.getHeroEquipment();
        spells = player.getSpells();

        bestTrophies = player.getBestTrophies();
        builderBaseTrophies = player.getBuilderBaseTrophies();
        bestBuilderBaseTrophies = player.getBestBuilderBaseTrophies();
        versusTrophies = player.getVersusTrophies();
        bestVersusTrophies = player.getBestVersusTrophies();

        attackWins = player.getAttackWins();
        defenseWins = player.getDefenseWins();
        versusBattleWins = player.getVersusBattleWins();
        donations = player.getDonations();
        donationsreceived = player.getDonationsReceived();

    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public PlayerHouse getPlayerHouse() {
        return playerHouse;
    }

    public PlayerClan getClan() {
        return clan;
    }

    public Role getRole() {
        return role;
    }

    public int getExpLevel() {
        return expLevel;
    }

    public int getTrophies() {
        return trophies;
    }

    public int getBuilderHallLevel() {
        return builderHallLevel;
    }

    public int getTownHallWeaponLevel() {
        return townHallWeaponLevel;
    }

    public int getTownHallLevel() {
        return townHallLevel;
    }

    public WarPreference getWarPreference() {
        return warPreference;
    }

    public int getWarStars() {
        return warStars;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public int getClanCapitalContributions() {
        return clanCapitalContributions;
    }

    public BuilderBaseLeague getBuilderBaseLeague() {
        return builderBaseLeague;
    }

    public League getLeague() {
        return league;
    }

    public PlayerLegendStatistics getLegendStatistics() {
        return legendStatistics;
    }

    public List<Troop> getTroops() {
        return troops;
    }

    public List<Troop> getHeroes() {
        return heroes;
    }

    public List<Troop> getHeroEquipment() {
        return heroEquipment;
    }

    public List<Troop> getSpells() {
        return spells;
    }

    public int getBestTrophies() {
        return bestTrophies;
    }

    public int getBuilderBaseTrophies() {
        return builderBaseTrophies;
    }

    public int getBestBuilderBaseTrophies() {
        return bestBuilderBaseTrophies;
    }

    public int getVersusTrophies() {
        return versusTrophies;
    }

    public int getBestVersusTrophies() {
        return bestVersusTrophies;
    }

    public int getAttackWins() {
        return attackWins;
    }

    public int getDefenseWins() {
        return defenseWins;
    }

    public int getVersusBattleWins() {
        return versusBattleWins;
    }

    public int getDonations() {
        return donations;
    }

    public int getDonationsreceived() {
        return donationsreceived;
    }
}
