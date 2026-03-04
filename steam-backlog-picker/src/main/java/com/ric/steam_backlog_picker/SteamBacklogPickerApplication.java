package com.ric.steam_backlog_picker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Steam Backlog Picker application.
 * This class initializes the Spring Boot context, auto-configures the
 * embedded Tomcat server, and triggers the Command Line Interface (CLI).
 */
@SpringBootApplication
public class SteamBacklogPickerApplication {

	static void main(String[] args) {
		// Launches the Spring application and starts the internal bean lifecycle
		// For now this happens in the terminal
		SpringApplication.run(SteamBacklogPickerApplication.class, args);
	}
}