package com.keletu.forgotten_relics.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ICanSwingMySwordMessage implements IMessage {
    
    private boolean swingTheSword;

    public ICanSwingMySwordMessage() { }

    public ICanSwingMySwordMessage(boolean swingTheSword) {
        this.swingTheSword = swingTheSword;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.swingTheSword = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.swingTheSword);
    }

    public static class Handler implements IMessageHandler<ICanSwingMySwordMessage, IMessage> {
        
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(ICanSwingMySwordMessage message, MessageContext ctx) {
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
            
            if (message.swingTheSword)
            	player.swingArm(player.getActiveHand());
            
            return null;
        }
    }
    
}
