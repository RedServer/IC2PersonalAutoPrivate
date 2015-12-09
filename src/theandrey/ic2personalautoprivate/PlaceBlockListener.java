package theandrey.ic2personalautoprivate;

import java.lang.reflect.Method;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceBlockListener implements Listener {

	private final IC2PersonalAutoPrivatePlugin plugin;

	public PlaceBlockListener(IC2PersonalAutoPrivatePlugin pl) {
		plugin = pl;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlace(final BlockPlaceEvent event) {
		final Block block = event.getBlockPlaced();
		if(block.getTypeId() == plugin.config.blockId) {
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				@Override
				public void run() {
					try {
						World world = block.getWorld();
						Object te = world.getClass().getMethod("getTileEntityAt", int.class, int.class, int.class).invoke(world, block.getX(), block.getY(), block.getZ());

						// Устанавливаем владельца
						te.getClass().getField("owner").set(te, event.getPlayer().getName());

						// Отправляем обновление клиентам
						Object ic2network = Class.forName("ic2.core.IC2").getField("network").get(null);
						for(Method method : ic2network.getClass().getMethods()) {
							if(method.getName().equals("updateTileEntityField")) {
								method.invoke(ic2network, te, "owner");
								break;
							}
						}
					} catch (Throwable ex) {
						IC2PersonalAutoPrivatePlugin.log.log(Level.SEVERE, "Не удалось установить владельца блока " + block.toString(), ex);
					}
				}
			}, 1); // задержка нужна, так как в момент отправки события блок ещё не установлен
		}
	}

}
