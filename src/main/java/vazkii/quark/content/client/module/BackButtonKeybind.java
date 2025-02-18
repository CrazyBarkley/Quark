package vazkii.quark.content.client.module;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.ScreenEvent.KeyboardKeyPressedEvent;
import net.minecraftforge.client.event.ScreenEvent.MouseClickedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.quark.base.client.handler.ModKeybindHandler;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.base.module.QuarkModule;

import java.util.List;

@LoadModule(category = ModuleCategory.CLIENT, hasSubscriptions = true, subscribeOn = Dist.CLIENT)
public class BackButtonKeybind extends QuarkModule {

	@OnlyIn(Dist.CLIENT)
	private static KeyMapping backKey;

	@OnlyIn(Dist.CLIENT)
	private static List<GuiEventListener> listeners;

	@Override
	public void clientSetup() {
		backKey = ModKeybindHandler.initMouse("back", 4, ModKeybindHandler.MISC_GROUP, (modifier, key) -> key.getType() != Type.MOUSE || key.getValue() != 0);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void openGui(ScreenEvent.InitScreenEvent event) {
		listeners = event.getListenersList();
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onKeyInput(KeyboardKeyPressedEvent.Post event) {
		if(backKey.getKey().getType() == Type.KEYSYM && event.getKeyCode() == backKey.getKey().getValue())
			clicc();
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onMouseInput(MouseClickedEvent.Post event) {
		if(backKey.getKey().getType() == Type.MOUSE && event.getButton() == backKey.getKey().getValue())
			clicc();
	}

	private void clicc() {
		ImmutableSet<String> buttons = ImmutableSet.of(
				I18n.get("gui.back"),
				I18n.get("gui.done"),
				I18n.get("gui.cancel"),
				I18n.get("gui.toTitle"),
				I18n.get("gui.toMenu"),
				I18n.get("quark.gui.config.save"));

		// Iterate this way to ensure we match the more important back buttons first
		for(String b : buttons)
			for(GuiEventListener listener : listeners) {
				if(listener instanceof Button w) {
					if(w.getMessage().getString().equals(b) && w.visible && w.active) {
						w.onClick(0, 0);
						return;
					}
				}
			}

		Minecraft mc = Minecraft.getInstance();
		if(mc.level != null)
			mc.setScreen(null);
	}

}
