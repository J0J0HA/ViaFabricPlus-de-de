/*
 * This file is part of ViaFabricPlus - https://github.com/FlorianMichael/ViaFabricPlus
 * Copyright (C) 2021-2023 FlorianMichael/MrLookAtMe (EnZaXD) and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.florianmichael.viafabricplus.settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.florianmichael.viafabricplus.ViaFabricPlus;
import de.florianmichael.viafabricplus.event.InitializeSettingsListener;
import de.florianmichael.viafabricplus.settings.base.AbstractSetting;
import de.florianmichael.viafabricplus.settings.base.SettingGroup;
import de.florianmichael.viafabricplus.settings.groups.*;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.vialoadingbase.platform.InternalProtocolList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsSystem {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final File CONFIG_FILE = new File(ViaFabricPlus.RUN_DIRECTORY, "settings.json");

    private final List<SettingGroup> groups = new ArrayList<>();

    public void init() {
        addGroup(
                GeneralSettings.getClassWrapper(),
                BridgeSettings.getClassWrapper(),
                MPPassSettings.getClassWrapper(),
                VisualSettings.getClassWrapper(),
                DebugSettings.getClassWrapper()
        );

        ViaFabricPlus.INSTANCE.getEventDispatcher().post(new InitializeSettingsListener.InitializeSettingsEvent());

        loadConfig();
        Runtime.getRuntime().addShutdownHook(new Thread(this::save));
    }

    public void addGroup(final SettingGroup... groups) {
        Collections.addAll(this.groups, groups);
    }

    public void loadConfig() {
        if (CONFIG_FILE.exists()) {
            final JsonObject parentNode;
            try {
                parentNode = GSON.fromJson(new FileReader(CONFIG_FILE), JsonObject.class).getAsJsonObject();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (parentNode.has("protocol")) {
                ViaLoadingBase.getClassWrapper().reload(InternalProtocolList.fromProtocolId(parentNode.get("protocol").getAsInt()));
            }
            for (SettingGroup group : groups) {
                for (AbstractSetting<?> setting : group.getSettings()) {
                    setting.read(parentNode);
                }
            }
        }
    }

    public void save() {
        CONFIG_FILE.delete();
        try {
            CONFIG_FILE.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (final FileWriter fw = new FileWriter(CONFIG_FILE)) {
            final JsonObject parentNode = new JsonObject();
            parentNode.addProperty("protocol", ViaLoadingBase.getClassWrapper().getTargetVersion().getVersion());
            for (SettingGroup group : groups) {
                for (AbstractSetting<?> setting : group.getSettings()) {
                    setting.write(parentNode);
                }
            }
            fw.write(GSON.toJson(parentNode));
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SettingGroup> getGroups() {
        return groups;
    }
}