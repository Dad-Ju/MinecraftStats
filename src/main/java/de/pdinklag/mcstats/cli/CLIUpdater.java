package de.pdinklag.mcstats.cli;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import de.pdinklag.mcstats.Config;
import de.pdinklag.mcstats.DataSource;
import de.pdinklag.mcstats.LogWriter;
import de.pdinklag.mcstats.Updater;

public class CLIUpdater extends Updater {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public CLIUpdater(Config config, LogWriter log) {
        super(config, log);
    }

    @Override
    protected String getServerMotd() {
        for (DataSource dataSource : config.getDataSources()) {
            final Path propertiesPath = dataSource.getServerPath().resolve("server.properties");
            if (Files.exists(propertiesPath)) {
                final Properties properties = new Properties();
                try (final InputStream fis = Files.newInputStream(propertiesPath)) {
                    properties.load(fis);
                } catch (IOException e) {
                }
                final String motd = properties.getProperty("motd");
                if(motd != null) {
                    return motd;
                }
            }
        }
        return null;
    }

    @Override
    protected String getVersion() {
        return MinecraftStatsCLI.getVersion();
    }

    @Override public void run() {
        super.run();
        log.writeLine("[" + LocalDateTime.now().format(DATE_FORMAT) + "] update finished");
    }
}
