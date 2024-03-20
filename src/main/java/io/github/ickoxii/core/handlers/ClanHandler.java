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

package io.github.ickoxii.core.handlers;

import kotlinx.serialization.MissingFieldException;

import com.lycoon.clashapi.models.capital.ClanCapital;
import com.lycoon.clashapi.models.clan.*;
import com.lycoon.clashapi.models.clan.enums.*;
import com.lycoon.clashapi.models.common.*;
import com.lycoon.clashapi.models.league.CapitalLeague;
import com.lycoon.clashapi.models.warleague.WarLeague;

import java.util.List;

public class ClanHandler {
    private Clan clan;

    private void handle(MissingFieldException ex, String msg) {
        System.err.println(msg);
        System.err.println("Error in ClanHandler: " + ex.getMessage());
        ex.printStackTrace();
    }

    public ClanHandler(Clan clan_) {
        clan = clan_;
    }

    // >>>> Getters >>>>
    public InviteType getInviteType() {
        return clan.getInviteType();
    }

    public boolean isFamilyFriendly() {
        return clan.isFamilyFriendly();
    }

    public String getTag() {
        return clan.getTag();
    }

    public String getName() {
        return clan.getName();
    }

    public int getClanLevel() {
        return clan.getClanLevel();
    }

    public int getClanPoints() {
        return clan.getClanPoints();
    }

    public String getDescription() {
        return clan.getDescription();
    }

    public Language getChatLanguage() {
        return clan.getChatLanguage();
    }

    public List<Label> getLabels() {
        return clan.getLabels();
    }

    public Location getLocation() {
        return clan.getLocation();
    }

    public BadgeUrls getBadgeUrls() {
        try {
            return clan.getBadgeUrls();
        } catch (MissingFieldException ex) {
            handle(ex, "in ClanHandler.getBadgeUrls");
        }
        return null;
    }

    public int getMembers() {
        return clan.getMembers();
    }

    public List<ClanMember> getClanMembers() {
        return clan.getMemberList();
    }

    public int getRequiredVersusTrophies() {
        try {
            return clan.getRequiredVersusTrophies();
        } catch (MissingFieldException ex) {
            handle(ex, "in ClanHandler.getRequiredVersusTrophies");
        }
        return 0;
    }

    public int getRequiredBuilderBaseTrophies() {
        try {
            return clan.getRequiredBuilderBaseTrophies();
        } catch (MissingFieldException ex) {
            handle(ex, "in ClanHandler.getRequiredBuilderBaseTrophies");
        }
        return 0;
    }

    public int getRequiredTrophies() {
        return clan.getRequiredTrophies();
    }

    public int getRequiredTownhallLevel() {
        return clan.getRequiredTownhallLevel();
    }

    public int getClanVersusPoints() {
        return clan.getClanVersusPoints();
    }

    public int getClanBuilderBasePoints() {
        return clan.getClanBuilderBasePoints();
    }

    public int getClanCapitalPoints() {
        try {
            return clan.getClanCapitalPoints();
        } catch (MissingFieldException ex) {
            handle(ex, "in ClanHandler.getClanCapitalPoints");
        }
        return 0;
    }

    public ClanCapital getClanCapital() {
        try {
            return clan.getClanCapital();
        } catch (MissingFieldException ex) {
            handle(ex, "in ClanHandler.getClanCapital");
        }
        return null;
    }

    public boolean isWarLogPublic() {
        return clan.isWarLogPublic();
    }

    public WarLeague getWarLeague() {
        try {
            return clan.getWarLeague();
        } catch (MissingFieldException ex) {
            handle(ex, "in ClanHandler.getWarLeague");
        }
        return null;
    }

    public CapitalLeague getCapitalLeague() {
        try {
            return clan.getCapitalLeague();
        } catch (MissingFieldException ex) {
            handle(ex, "in ClanHandler.getCapitalLeague");
        }
        return null;
    }

    public WarFrequency getWarFrequency() {
        return clan.getWarFrequency();
    }

    public int getWarWinStreak() {
        return clan.getWarWinStreak();
    }

    public int getWarWins() {
        return clan.getWarWins();
    }

    public int getWarTies() {
        return clan.getWarTies();
    }

    public int getWarLosses() {
        return clan.getWarLosses();
    }
    // <<<< Getters <<<<
    
}
