package io.github.ickoxii.core;

import com.lycoon.clashapi.models.capital.ClanCapital;
import com.lycoon.clashapi.models.clan.*;
import com.lycoon.clashapi.models.clan.enums.*;
import com.lycoon.clashapi.models.common.*;
import com.lycoon.clashapi.models.league.CapitalLeague;
import com.lycoon.clashapi.models.warleague.WarLeague;

import java.util.List;

public class ClanInfo {
    private InviteType inviteType;

    private boolean isFamilyFriendly;
    private String tag;
    private String name;
    private int clanLevel;
    private int clanPoints;
    private String description;
    private Language chatLanguage;
    private List<Label> labels;
    private Location location;
    private BadgeUrls badgeUrls;
    private int members;
    private List<ClanMember> clanMembers;

    private int requiredVersusTrophies;
    private int requiredBuilderBaseTrophies;
    private int requiredTrophies;
    private int requiredTownhallLevel;

    private int clanVersusPoints;
    private int clanBuilderBasePoints;
    private int clanCapitalPoints;
    private ClanCapital clanCapital;

    private boolean isWarLogPublic;
    private WarLeague warLeague;
    private CapitalLeague capitalLeague;
    private WarFrequency WarFrequency;
    private int warWinStreak;
    private int warWins;
    private int warTies;
    private int warLosses;

    public ClanInfo(Clan clan) {
        inviteType = clan.getInviteType();

        isFamilyFriendly = clan.isFamilyFriendly();
        tag = clan.getTag();
        name = clan.getName();
        clanLevel = clan.getClanLevel();
        clanPoints = clan.getClanPoints();
        description = clan.getDescription();
        chatLanguage = clan.getChatLanguage();
        labels = clan.getLabels();
        location = clan.getLocation();
        badgeUrls = clan.getBadgeUrls();
        members = clan.getMembers();
        clanMembers = clan.getMemberList();

        requiredVersusTrophies = clan.getRequiredVersusTrophies(); // Deprecated
        requiredBuilderBaseTrophies = clan.getRequiredBuilderBaseTrophies();
        requiredTrophies = clan.getRequiredTrophies();
        requiredTownhallLevel = clan.getRequiredTownhallLevel();

        clanVersusPoints = clan.getClanVersusPoints(); // Deprecated
        clanBuilderBasePoints = clan.getClanBuilderBasePoints();
        clanCapitalPoints = clan.getClanCapitalPoints();
        clanCapital = clan.getClanCapital();

        isWarLogPublic = clan.isWarLogPublic();
        warLeague = clan.getWarLeague();
        capitalLeague = clan.getCapitalLeague();
        WarFrequency = clan.getWarFrequency();
        warWinStreak = clan.getWarWinStreak();
        warWins = clan.getWarWins();
        warTies = clan.getWarTies();
        warLosses = clan.getWarLosses();
    }

    public InviteType getInviteType() {
        return inviteType;
    }

    public boolean isFamilyFriendly() {
        return isFamilyFriendly;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public int getClanLevel() {
        return clanLevel;
    }

    public int getClanPoints() {
        return clanPoints;
    }

    public String getDescription() {
        return description;
    }

    public Language getChatLanguage() {
        return chatLanguage;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public Location getLocation() {
        return location;
    }

    public BadgeUrls getBadgeUrls() {
        return badgeUrls;
    }

    public int getMembers() {
        return members;
    }

    public List<ClanMember> getClanMembers() {
        return clanMembers;
    }

    public int getRequiredVersusTrophies() {
        return requiredVersusTrophies;
    }

    public int getRequiredBuilderBaseTrophies() {
        return requiredBuilderBaseTrophies;
    }

    public int getRequiredTrophies() {
        return requiredTrophies;
    }

    public int getRequiredTownhallLevel() {
        return requiredTownhallLevel;
    }

    public int getClanVersusPoints() {
        return clanVersusPoints;
    }

    public int getClanBuilderBasePoints() {
        return clanBuilderBasePoints;
    }

    public int getClanCapitalPoints() {
        return clanCapitalPoints;
    }

    public ClanCapital getClanCapital() {
        return clanCapital;
    }

    public boolean isWarLogPublic() {
        return isWarLogPublic;
    }

    public WarLeague getWarLeague() {
        return warLeague;
    }

    public CapitalLeague getCapitalLeague() {
        return capitalLeague;
    }

    public com.lycoon.clashapi.models.clan.enums.WarFrequency getWarFrequency() {
        return WarFrequency;
    }

    public int getWarWinStreak() {
        return warWinStreak;
    }

    public int getWarWins() {
        return warWins;
    }

    public int getWarTies() {
        return warTies;
    }

    public int getWarLosses() {
        return warLosses;
    }
}
