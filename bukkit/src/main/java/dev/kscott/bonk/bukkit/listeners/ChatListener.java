package dev.kscott.bonk.bukkit.listeners;

import com.google.inject.Inject;
import dev.kscott.bonk.bukkit.chat.ChatService;
import dev.kscott.bonk.bukkit.player.PlayerService;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Listens on chat event.
 */
public class ChatListener implements Listener {

    /**
     * The chat service.
     */
    private final @NonNull ChatService chatService;

    /**
     * The player service.
     */
    private final @NonNull PlayerService playerService;

    @Inject
    public ChatListener(
            final @NonNull ChatService chatService,
            final @NonNull PlayerService playerService
    ) {
        this.chatService = chatService;
        this.playerService = playerService;
    }

    @EventHandler
    public void chat(final @NonNull AsyncChatEvent event) {
        this.chatService.chat(event);
    }

}
