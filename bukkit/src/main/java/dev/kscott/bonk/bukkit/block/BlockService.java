package dev.kscott.bonk.bukkit.block;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.kscott.bonk.bukkit.position.PositionService;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

/**
 * Handles interaction with game blocks.
 */
public final class BlockService {

    /**
     * The plugin.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The position service.
     */
    private final @NonNull PositionService positionService;

    /**
     * The game world.
     */
    private final @NonNull World world;

    /**
     * The random service.
     */
    private final @NonNull Random random;

    /**
     * Constructs {@code BlockService}.
     *
     * @param plugin          the plugin
     * @param positionService the position service
     * @param world           the game world
     */
    @Inject
    public BlockService(
            final @NonNull JavaPlugin plugin,
            final @NonNull @Named("gameWorld") World world,
            final @NonNull PositionService positionService
    ) {
        this.plugin = plugin;
        this.positionService = positionService;

        this.random = new Random();
        this.world = world;
    }

    /**
     * Destroys a block, regenerating it between {@code minRegenTime} and {@code maxRegenTime}, in Minecraft ticks.
     *
     * @param block        the block
     * @param minRegenTime the minimum regen time
     * @param maxRegenTime the maximum regen time
     */
    public void destroyBlock(Block block, final int minRegenTime, final int maxRegenTime) {
        final @NonNull BlockState state = block.getState(true);

        block.setType(Material.AIR);

        final int delay = minRegenTime == maxRegenTime ? minRegenTime : this.random.nextInt(maxRegenTime - minRegenTime) + minRegenTime;

        new BukkitRunnable() {
            @Override
            public void run() {
                state.update(true);
            }
        }.runTaskLater(this.plugin, delay);
    }

}
