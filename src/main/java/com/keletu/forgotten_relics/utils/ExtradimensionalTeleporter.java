package com.keletu.forgotten_relics.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class ExtradimensionalTeleporter extends Teleporter {
	
	private double X;
	private double Y;
	private double Z;
	
	public ExtradimensionalTeleporter(WorldServer p_i1963_1_, double x, double y, double z) {
		super(p_i1963_1_);
		this.X = x;
		this.Y = y;
		this.Z = z;
	}

	@Override
	public void placeInPortal(Entity entity, float rotationYaw) {
		if (entity instanceof EntityPlayer) { 
			EntityPlayer player = (EntityPlayer) entity;
			player.setPositionAndUpdate(this.X, this.Y, this.Z);
		} else {
			entity.posX = this.X;
			entity.posY = this.Y;
			entity.posZ = this.Z;
		}
		/*
		if (entity instanceof EntityPlayer) {
			if (entity.capabilities.allowFlying)
				entity.capabilities.isFlying = true;
		}
		*/
		return;
	}

}
