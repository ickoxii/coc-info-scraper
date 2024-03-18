package io.github.ickoxii.interfaces;

import com.lycoon.clashapi.models.player.*;

import java.util.List;

public interface PlayerServices {

    public void printPlayerInformation(Player player);
    public List<Player> getPlayers(String[] playerTags);

}
