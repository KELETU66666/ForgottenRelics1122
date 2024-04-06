package com.keletu.forgotten_relics.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.client.fx.FXDispatcher;

public class BurstMessage implements IMessage {
    
    private double x;
    private double y;
    private double z;
    
    private float size;

    public BurstMessage() { }

    public BurstMessage(double x, double y, double z, float size) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.size = size;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        
        this.size = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        
        buf.writeFloat(this.size);
    }

    public static class Handler implements IMessageHandler<BurstMessage, IMessage> {
        
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(BurstMessage message, MessageContext ctx) {
            FXDispatcher.INSTANCE.burst(message.x, message.y, message.z, message.size);
            
            return null;
        }
    }
    
}
