package com.ric.steam_backlog_picker;

import com.ric.steam_backlog_picker.model.Game;
import com.ric.steam_backlog_picker.model.SteamResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import java.util.List;

/**
 * REST Controller that exposes the Steam Backlog Picker API.
 * Handles incoming HTTP requests from the frontend and delegates business logic to the DataProcessing service.
 */
@RestController
@RequestMapping("/api")
public class Controller {

    private final DataProcessing dataProcessing;

    /**
     * Constructor injection for the DataProcessing service.
     * @param dataProcessing The service used for filtering and picking games.
     */
    public Controller(DataProcessing dataProcessing) {
        this.dataProcessing = dataProcessing;
    }

    /**
     * Picks a random unplayed game from the user's Steam library.
     * Fetches the library from the Steam API using the provided credentials,
     * filters for backlog games, and returns one at random.
     * @param apiKey The user's Steam Web API key (Do not hardcode it!)
     * @param steamId The user's Steam ID
     * @return The name of the randomly selected game
     */
    @GetMapping("/pick")
    public ResponseEntity<String> pickGame(
            @RequestParam String apiKey,
            @RequestParam String steamId
    ) {
        String url = "https://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/" +
                "?key=" + apiKey +
                "&steamid=" + steamId +
                "&format=json&include_appinfo=true";

        // Perform the HTTP GET request and map the JSON response to a SteamResponse record
        RestClient restClient = RestClient.create();
        SteamResponse response = restClient.get()
                .uri(url)
                .retrieve()
                .body(SteamResponse.class);

        List<Game> backlog = dataProcessing.filterBacklog(response);
        Game picked = DataProcessing.pickGame(backlog);

        return ResponseEntity.ok(picked.name());
    }
}