package dev.kscott.bonk.bukkit;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.kscott.bonk.bukkit.box.BoxService;
import dev.kscott.bonk.bukkit.command.CommandService;
import dev.kscott.bonk.bukkit.game.BonkGame;
import dev.kscott.bonk.bukkit.inject.BukkitModule;
import dev.kscott.bonk.bukkit.inject.CommandModule;
import dev.kscott.bonk.bukkit.inject.GameModule;
import dev.kscott.bonk.bukkit.listeners.*;
import dev.kscott.bonk.bukkit.utils.ArrayHelper;
import dev.kscott.bonk.bukkit.utils.BonkPlaceholders;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.interfaces.paper.PaperInterfaceListeners;

/**
 * The main Bonk entrypoint.
 */
public final class BukkitBonkPlugin extends JavaPlugin {

    /**
     * An array of {@link Listener} classes to initialize during startup.
     */
    private static final @NonNull Class<? extends Listener>[] LISTENERS = ArrayHelper.create(
            PlayerConnectionListeners.class,
            BlockInteractListener.class,
            PlayerMovementListeners.class,
            PlayerDeathListeners.class,
            PlayerInteractListeners.class,
            PlayerAttributeListeners.class,
            FireworkListeners.class,
            ChatListener.class
    );

    /**
     * The Guice injector.
     */
    private @NonNull Injector injector;

    /**
     * The {@code BonkGame} instance.
     */
    private @MonotonicNonNull BonkGame bonkGame;

    /**
     * Constructs {@code BukkitBonkPlugin}.
     */
    public BukkitBonkPlugin() {
        // Initialize Guice shenanigans
        this.injector = Guice.createInjector(
                new BukkitModule(this)
        );
    }

    @Override
    public void onEnable() {
        try {
            this.injector = this.injector.createChildInjector(
                    new CommandModule(this),
                    new GameModule(this)
            );

            this.bonkGame = injector.getInstance(BonkGame.class);

            PaperInterfaceListeners.install(this);

            this.bonkGame.enable();

            this.injector.getInstance(CommandService.class); // Initialize command service

            // Register events
            for (final @NonNull Class<? extends Listener> klazz : LISTENERS) {
                this.getServer().getPluginManager().registerEvents(
                        this.injector.getInstance(klazz),
                        this
                );
            }

            final @NonNull BonkPlaceholders placeholderExpansion = this.injector.getInstance(BonkPlaceholders.class);
            final @Nullable Plugin placeholderApiPlugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");

            if (placeholderApiPlugin != null && placeholderApiPlugin.isEnabled()) {
                System.out.println("REgsitering placehodlers");
                placeholderExpansion.register();
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getPluginLoader().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        this.injector.getInstance(BoxService.class).cleanup();
    }

}
