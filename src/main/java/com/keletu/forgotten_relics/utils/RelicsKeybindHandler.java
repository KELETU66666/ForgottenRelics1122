package com.keletu.forgotten_relics.utils;

import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.packets.DiscordKeybindMessage;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;

public class RelicsKeybindHandler {

	@SideOnly(Side.CLIENT)
	public static boolean checkVariable;

	public static KeyBinding discordRingKey;

	@SideOnly(Side.CLIENT)
	public static void registerKeybinds() {
		discordRingKey = new KeyBinding("key.discordRing", Keyboard.KEY_X, "key.categories.forgottenrelics");

		ClientRegistry.registerKeyBinding(discordRingKey);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (this.discordRingKey.isPressed() & checkVariable == false) {
			Main.packetInstance.sendToServer(new DiscordKeybindMessage(true));
			checkVariable = true;
		} else if (!this.discordRingKey.isPressed() & checkVariable == true) {
			checkVariable = false;
		}
			//if (ItemVoidGrimoire.localCooldown > 0)
			//	ItemVoidGrimoire.localCooldown--;

	}

	/*
	@SubscribeEvent
    public void onKeyInputLul(InputEvent.KeyInputEvent event) {
		//System.out.println("HEY BOY: " + event.getPhase());

		if (this.discordRingKey.isPressed()) {
			//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Hello there! You've just pressed: " + Keyboard.getKeyName(discordRingKey.getKeyCode())));
			Main.packetInstance.sendToServer(new DiscordKeybindMessage(true));
		}

    }
	*/
}
