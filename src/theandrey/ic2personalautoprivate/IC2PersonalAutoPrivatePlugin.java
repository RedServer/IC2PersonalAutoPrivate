package theandrey.ic2personalautoprivate;

import java.util.logging.Logger;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class IC2PersonalAutoPrivatePlugin extends JavaPlugin {

	public static Logger log;
	public Config config;

	@Override
	public void onEnable() {
		log = getLogger();
		config = new Config(this);
		config.load();
		config.save();
		getServer().getPluginManager().registerEvents(new PlaceBlockListener(this), this);
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
	}

}
