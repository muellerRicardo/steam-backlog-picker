package com.ric.steam_backlog_picker.model;

import java.util.List;

/**
 * This record represents the respond from Steam but cleaned up into a list of the games.
 * @param games The list of individual Game objects returned by the API.
 */
public record ResponseData(
        List<Game> games
) {}
