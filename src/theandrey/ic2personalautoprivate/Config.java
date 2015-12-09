package theandrey.ic2personalautoprivate;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	public int blockId = 4039;
	private final File file;

	public Config(IC2PersonalAutoPrivatePlugin plugin) {
		file = new File(plugin.getDataFolder(), "config.yml");
	}

	public void load() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		blockId = config.getInt("block-id", blockId);
	}

	public void save() {
		YamlConfiguration config = new YamlConfiguration();
		config.set("block-id", blockId);
		try {
			config.save(file);
		} catch (IOException ex) {
			IC2PersonalAutoPrivatePlugin.log.log(Level.SEVERE, "Произошла ошибка при сохранении конфигурации плагина.", ex);
		}
	}

}
