package io.github.ickoxii.interfaces;

import com.lycoon.clashapi.models.clan.*;

import java.util.List;

public interface ClanServices {

    public void printClanInformation(Clan clan);
    public List<Clan> getClans(String[] clanTags);

}
