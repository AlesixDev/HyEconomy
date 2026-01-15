package com.dunystudios.hytale.plugins.config;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigManager {
    private ConfigData configData;
    private File configFile;

    public void setup() {
        File folder = new File("HyEconomy");
        if (!folder.exists()) folder.mkdirs();
        configFile = new File(folder, "config.yml");

        if (!configFile.exists()) {
            try (InputStream in = getClass().getResourceAsStream("/config.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        load();
    }

    public void load() {
        LoaderOptions options = new LoaderOptions();
        Constructor constructor = new Constructor(ConfigData.class, options);
        Yaml yaml = new Yaml(constructor);
        try (InputStream inputStream = new FileInputStream(configFile)) {
            this.configData = yaml.loadAs(inputStream, ConfigData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ConfigData getData() {
        return configData;
    }
}
