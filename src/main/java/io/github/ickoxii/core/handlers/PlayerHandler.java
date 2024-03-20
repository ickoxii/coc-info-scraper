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

 /**
  * 1. Store playertag in class, call API in getters
  *     * Probably slow as shit
  *
  * 2. Store Player in class, try catch getting information?
  * */

package io.github.ickoxii.core.handlers;

import kotlinx.serialization.MissingFieldException;

import com.lycoon.clashapi.models.common.*;
import com.lycoon.clashapi.models.league.BuilderBaseLeague;
import com.lycoon.clashapi.models.league.League;
import com.lycoon.clashapi.models.player.*;
import com.lycoon.clashapi.models.player.enums.*;

import java.util.List;

public class PlayerHandler {
    private Player player;

    private void handle(MissingFieldException ex, String msg) {
        if(!msg.isEmpty()) System.err.println(msg);
        System.err.println("Error in PlayerHandler: " + ex.getMessage());
        ex.printStackTrace();
    }

    public PlayerHandler(Player player_) {
        player = player_;
    }

    public String getTag() {
        return player.getTag();
    }

    public String getName() {
        return player.getName();
    }

    public PlayerHouse getPlayerHouse() {
        try {
            return player.getPlayerHouse();
        } catch (MissingFieldException ex) {
            handle(ex, "in getPlayerHouse");            
        }
        return null;
    }

    public PlayerClan getClan() {
        try {
            return player.getClan();
        } catch (MissingFieldException ex) {
            handle(ex, "in PlayerHandler.getClan");
        }
        return null;
    }

    public Role getRole() {
        try {
            return player.getRole();
        } catch (MissingFieldException ex) {
            handle(ex, "in PlayerHandler.getRole");
        }
        return null;
    }

    public int getExpLevel() {
        return player.getExpLevel();
    }

    public int getTrophies() {
        return player.getTrophies();
    }

    public int getBuilderHallLevel() {
        return player.getBuilderHallLevel();
    }

    public int getTownHallWeaponLevel() {
        return player.getTownHallWeaponLevel();
    }

    public int getTownHallLevel() {
        return player.getTownHallLevel();
    }

    public WarPreference getWarPreference() {
        return player.getWarPreference();
    }

    public int getWarStars() {
        return player.getWarStars();
    }

    public List<Achievement> getAchievements() {
        return player.getAchievements();
    }

    public List<Label> getLabels() {
        try {
            return player.getLabels();
        } catch (MissingFieldException ex) {
            handle(ex, "in PlayerHandler.getLabels");
        }
        return null;
    }

    public int getClanCapitalContributions() {
        return player.getClanCapitalContributions();
    }

    public BuilderBaseLeague getBuilderBaseLeague() {
        try {
            return player.getBuilderBaseLeague();
        } catch (MissingFieldException ex) {
            handle(ex, "in PlayerHandler.getBuilderBaseLeague");
        }
        return null;
    }

    public League getLeague() {
        try {
            return player.getLeague();
        } catch (MissingFieldException ex) {
            handle(ex, "in PlayerHandler.getLeague");
        }
        return null;
    }

    public PlayerLegendStatistics getLegendStatistics() {
        try {
            return player.getLegendStatistics();
        } catch (MissingFieldException ex) {
            handle(ex, "in PlayerHandler.getLegendStatistics");
        }
        return null;
    }

    public List<Troop> getTroops() {
        return player.getTroops();
    }

    public List<Troop> getHeroes() {
        try {
            return player.getHeroes();
        } catch (MissingFieldException ex) {
            handle(ex, "in PlayerHandler.getHeroes");
        }
        return null;
    }

    public List<Troop> getHeroEquipment() {
        try {
            return player.getHeroEquipment();
        } catch (MissingFieldException ex) {
            handle(ex, "in PlayerHandler.getHeroEquipment");
        }
        return null;
    }

    public List<Troop> getSpells() {
        try {
            return player.getSpells();
        } catch (MissingFieldException ex) {
            handle(ex, "in PlayerHandler.getSpells");
        }
        return null;
    }

    public int getBestTrophies() {
        return player.getBestTrophies();
    }

    public int getBuilderBaseTrophies() {
        try {
            return player.getBuilderBaseTrophies();
        } catch (MissingFieldException ex) {
            handle(ex, "in PlayerHandler.getBuilderBaseTrophies");
        }
        return 0;
    }

    public int getBestBuilderBaseTrophies() {
        try {
            return player.getBestBuilderBaseTrophies();
        } catch (MissingFieldException ex) {
            handle(ex, "in PlayerHandler.getBestBuilderBaseTrophies");
        }
        return 0;
    }

    public int getVersusTrophies() {
        try {
            return player.getVersusTrophies();
        } catch (MissingFieldException ex) {
            handle(ex, "in PlayerHandler.getVersusTrophies");
        }
        return 0;
    }

    public int getBestVersusTrophies() {
        try {
            return player.getBestVersusTrophies();
        } catch (MissingFieldException ex) {
            handle(ex, "in PlayerHandler.getBestVersusTrophies");
        }
        return 0;
    }

    public int getAttackWins() {
        return player.getAttackWins();
    }

    public int getDefenseWins() {
        return player.getDefenseWins();
    }

    public int getVersusBattleWins() {
        return player.getVersusBattleWins();
    }

    public int getDonations() {
        return player.getDonations();
    }

    public int getDonationsReceived() {
        return player.getDonationsReceived();
    }
}
