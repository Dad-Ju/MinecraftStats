package de.pdinklag.mcstats.bukkit;

import java.nio.file.Path;
import java.util.Optional;

import org.bukkit.plugin.Plugin;

import de.bluecolored.bluemap.api.BlueMapAPI;

public class BlueMapWebserver extends PluginWebserver {
    public static final String PLUGIN_NAME = "BlueMap";

    // private static final String CONFIG_FILENAME = "webserver.conf";
    private static final String DEFAULT_WEBROOT = "bluemap/web";

    private final Path documentRoot;

    public BlueMapWebserver(Plugin bluemapPlugin) {
        Optional<BlueMapAPI> optionalApi = BlueMapAPI.getInstance();

        if (optionalApi.isPresent()) {
            this.documentRoot = optionalApi.get().getWebApp().getWebRoot();
        } else {
            this.documentRoot = Path.of(DEFAULT_WEBROOT);
        }
    }

    @Override
    public Path getDocumentRoot() {
        return documentRoot;
    }
}
