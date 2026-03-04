package com.ric.steam_backlog_picker;

import com.ric.steam_backlog_picker.model.Game;
import com.ric.steam_backlog_picker.model.SteamResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Scanner;

/**
 * The CLI handler for the application.
 * This component runs automatically on startup, prompts the user for credentials,
 * fetches data from the Steam API, and selects a random game to play.
 * Will be replaced later down the line with a proper front end.
 */
@Component
public class TerminalRunner implements CommandLineRunner {

    private final DataProcessing dataProcessing;
    private final ConfigurableApplicationContext context;

    /**
     * Constructor injection for required dependencies.
     * @param dataProcessing The service used for filtering and picking games.
     * @param context The Spring application context, required for a graceful shutdown.
     */
    public TerminalRunner(DataProcessing dataProcessing, ConfigurableApplicationContext context) {
        this.dataProcessing = dataProcessing;
        this.context = context;
    }

    /**
     * Main execution logic.#
     * Does the credential input, API call, data filtering, and final output.
     * @param args Command line arguments (not currently used).
     */
    @Override
    public void run(String... args) {

        // Collect credentials
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Steam API Key: ");
        String apiKey = scanner.nextLine();
        System.out.print("Enter your Steam ID: ");
        String steamId = scanner.nextLine();

        System.out.println("\n--- Initialized with ---");
        System.out.println("Key: [HIDDEN]");
        System.out.println("ID: " + steamId);

        // Construct the Steam API URL for the IPlayerService
        System.out.println("Fetching your game library...");

        String url = "https://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/" +
                "?key=" + apiKey +
                "&steamid=" + steamId +
                "&format=json&include_appinfo=true";

        try {
            // REST Client initiates the HTTP GET request and maps JSON to SteamResponse record
            RestClient restClient = RestClient.create();
            SteamResponse response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(SteamResponse.class);

            System.out.println("Connection Successful! Data received.");
            // Format the Reponse into a list of Games
            List<Game> backlog = dataProcessing.filterBacklog(response);

            // Select one game from the list
            Game pickedGame = DataProcessing.pickGame(backlog);

            System.out.println("You should play:" + pickedGame.name());


        } catch (Exception e) {
            System.out.println("Error: Could not connect to Steam. Check your Key/ID or Privacy Settings.");
            System.err.println("Details: " + e.getMessage());
        } finally {
            SpringApplication.exit(context, () -> 0);
        }
    }
}