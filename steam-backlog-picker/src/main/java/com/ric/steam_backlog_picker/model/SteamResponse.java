package com.ric.steam_backlog_picker.model;

/**
 * The very top level of the Steam reply. It just holds the raw response of the Steam Web API.
 */
public record SteamResponse(ResponseData response) {}

