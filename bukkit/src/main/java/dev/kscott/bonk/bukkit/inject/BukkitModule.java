package dev.kscott.bonk.bukkit.inject;

import com.google.inject.AbstractModule;
import dev.kscott.bonk.bukkit.BukkitBonkPlugin;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides Bukkit-related classes.
 */
public class BukkitModule extends AbstractModule {

    /**
     * The {@link BukkitBonkPlugin} to provide.
     */
    private final @NonNull BukkitBonkPlugin plugin;

    /**
     * The {@link AudienceProvider} to provide.
     */
    private final @NonNull BukkitAudiences audienceProvider;

    /**
     * Constructs {@code BukkitModule}.
     *
     * @param plugin the plugin
     */
    public BukkitModule(final @NonNull BukkitBonkPlugin plugin) {
        this.plugin = plugin;
        this.audienceProvider = BukkitAudiences.create(plugin);
    }

    /**
     * Binds plugin and audience classes.
     */
    @Override
    public void configure() {
        this.bind(Plugin.class).toInstance(this.plugin);
        this.bind(JavaPlugin.class).toInstance(this.plugin);
        this.bind(BukkitBonkPlugin.class).toInstance(this.plugin);

        this.bind(AudienceProvider.class).toInstance(this.audienceProvider);
        this.bind(BukkitAudiences.class).toInstance(this.audienceProvider);
    }
}
