package com.ric.steam_backlog_picker;

import com.ric.steam_backlog_picker.model.Game;
import com.ric.steam_backlog_picker.model.SteamResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Service class that handles the core logic for game data.
 * It processes the raw Steam API response to find unplayed games.
 */
@Service
public class DataProcessing {

    /**
     * Filters the user's library for games with 0 minutes of playtime.
     * @param response The mapped response object from the Steam API.
     * @return A list of Games found in the user's backlog.
     */
    public List<Game> filterBacklog(SteamResponse response) {
        if (response == null || response.response() == null || response.response().games() == null) {
            return Collections.emptyList();
        }

        return response.response().games().stream().toList();
    }

    /**
     * Randomly selects one game from a provided list.
     * Will be replaced with a more sophisticated algorithm later
     * @param games The list of unplayed games.
     * @return A single randomly selected Game object.
     */
    public static Game pickGame(List<Game> games) {
        Random ran = new Random();
        int pick = ran.nextInt(games.size());

        return games.get(pick);
    }

}
