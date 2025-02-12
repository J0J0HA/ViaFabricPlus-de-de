/*
 * This file is part of ViaFabricPlus - https://github.com/FlorianMichael/ViaFabricPlus
 * Copyright (C) 2021-2023 FlorianMichael/EnZaXD and contributors
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
package de.florianmichael.viafabricplus.definition.c0_30.command;

import de.florianmichael.viafabricplus.definition.c0_30.command.impl.HelpCommand;
import de.florianmichael.viafabricplus.definition.c0_30.command.impl.ListExtensionsCommand;
import de.florianmichael.viafabricplus.definition.c0_30.command.impl.SetTimeCommand;

import java.util.ArrayList;
import java.util.List;

public class ClassicProtocolCommands {
    public final static String COMMAND_PREFIX = "/v";
    public static ClassicProtocolCommands INSTANCE;

    public final List<ICommand> commands = new ArrayList<>();

    public static void create() {
        INSTANCE = new ClassicProtocolCommands();

        INSTANCE.commands.add(new HelpCommand());
        INSTANCE.commands.add(new SetTimeCommand());
        INSTANCE.commands.add(new ListExtensionsCommand());
    }
}
