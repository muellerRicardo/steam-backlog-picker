package com.ric.steam_backlog_picker.model;

/**
 * Represents an individual game in a user's Steam library.
 * This record is mapped directly from the 'games' array in the Steam API JSON response.
 *
 * @param appid The unique identifier for the game on the Steam platform.
 * @param name The display title of the game.
 * @param playtime_forever The total playtime recorded in minutes.
 * @param img_icon_url The hash string used to construct the game's icon URL.
 */
public record Game(
        int appid,
        String name,
        int playtime_forever,
        String img_icon_url
) {}
