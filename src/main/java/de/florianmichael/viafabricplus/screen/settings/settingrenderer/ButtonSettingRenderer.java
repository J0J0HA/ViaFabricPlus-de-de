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
package de.florianmichael.viafabricplus.screen.settings.settingrenderer;

import de.florianmichael.viafabricplus.screen.settings.AbstractSettingRenderer;
import de.florianmichael.viafabricplus.settings.type_impl.ButtonSetting;
import de.florianmichael.viafabricplus.util.ScreenUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ButtonSettingRenderer extends AbstractSettingRenderer {
    private final ButtonSetting value;

    public ButtonSettingRenderer(ButtonSetting value) {
        this.value = value;
    }

    @Override
    public Text getNarration() {
        return this.value.getName();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.value.getValue().run();
        ScreenUtil.playClickSound();
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void renderSetting(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        DrawableHelper.drawCenteredTextWithShadow(matrices, textRenderer, this.value.displayValue(), entryWidth / 2, entryHeight / 2 - textRenderer.fontHeight / 2, -1);
    }
}
