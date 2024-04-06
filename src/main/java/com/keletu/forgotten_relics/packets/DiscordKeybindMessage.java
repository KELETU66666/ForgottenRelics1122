package com.keletu.forgotten_relics.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DiscordKeybindMessage implements IMessage {
    
    private boolean doIt;

    public DiscordKeybindMessage() { }

    public DiscordKeybindMessage(boolean doIt) {
        this.doIt = doIt;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.doIt = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.doIt);
    }

    public static class Handler implements IMessageHandler<DiscordKeybindMessage, IMessage> {
        
        //@SideOnly(Side.SERVER)
        public IMessage onMessage(DiscordKeybindMessage message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
         //   ItemStack stack = SuperpositionHandler.findFirst(player, CommonProxy.itemTeleportationTome);
         //
         //   if (message.doIt & stack != null & SuperpositionHandler.hasBauble(player, CommonProxy.itemDiscordRing)) {
         //       CommonProxy.itemTeleportationTome.onItemRightClick(player.world, player, player.getActiveHand());
         //   }

            return null;
        }
    }
    
}
