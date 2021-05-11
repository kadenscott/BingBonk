package dev.kscott.bonk.bukkit.listeners;

import com.google.inject.Inject;
import dev.kscott.bonk.bukkit.player.PlayerService;
import dev.kscott.bonk.bukkit.utils.ArrayHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Listens on player join events.
 */
public final class PlayerJoinListener implements Listener {

    private final @NonNull PlayerService playerService;

    /**
     * Constructs PlayerJoinListener.
     *
     * @param playerService the PlayerService
     */
    @Inject
    public PlayerJoinListener(final @NonNull PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Handles the player join event.
     * @param event {@link PlayerJoinEvent}
     */
    @EventHandler
    public void handlePlayerJoin(final @NonNull PlayerJoinEvent event) {
        this.playerService.player(event.getPlayer());
    }
}
