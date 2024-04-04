package com.keletu.forgotten_relics.packets;

import com.keletu.forgotten_relics.Main;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NotificationMessage implements IMessage {
    
    private int type;

    public NotificationMessage() { }

    public NotificationMessage(int type) {
        this.type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.type = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.type);
    }

    public static class Handler implements IMessageHandler<NotificationMessage, IMessage> {
        
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(NotificationMessage message, MessageContext ctx) {
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
            
            String notification;
            
            switch (message.type) {
            
            case 1: notification = I18n.format("notification.fate_cooldown_over");
            	break;
            	
            case 2: notification = I18n.format("notification.overdamage_block");
            	break;
            	
            default:
            	Main.log.error("Recived invalid notification!");
            	return null;
            
            }

            player.sendStatusMessage(new TextComponentString(I18n.format(notification)), true);
            
            return null;
        }
    }
    
}
