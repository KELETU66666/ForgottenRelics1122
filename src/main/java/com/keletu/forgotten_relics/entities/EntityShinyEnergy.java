package com.keletu.forgotten_relics.entities;

import java.util.List;

import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;

public class EntityShinyEnergy extends EntityThrowable implements IEntityAdditionalSpawnData
{
    double lockX;
    double lockY;
    double lockZ;
    int targetID;
    EntityLivingBase thrower;
    EntityLivingBase target;
    
    
    public EntityShinyEnergy(final World par1World) {
        super(par1World);
        this.targetID = 0;
        setSize(0.0F, 0.0F);
    }
    
    public EntityShinyEnergy(final World par1World, final EntityLivingBase par2EntityLiving, EntityLivingBase t, double x, double y, double z) {
        super(par1World, par2EntityLiving);
        this.thrower = par2EntityLiving;
        this.targetID = 0;
        this.target = t;
        this.lockX = x;
        this.lockY = y;
        this.lockZ = z;
        setSize(0.0F, 0.0F);
    }
    
    protected float getGravityVelocity() {
        return 0.0f;
    }
    
    public void writeSpawnData(final ByteBuf data) {
    	
    	int id = -1;
        if (this.target != null) {
            id = this.target.getEntityId();
        }
        data.writeInt(id);
    	
        data.writeDouble(this.lockX);
        data.writeDouble(this.lockY);
        data.writeDouble(this.lockZ);
    }
    
    public void readSpawnData(final ByteBuf data) {
    	
    	final int id = data.readInt();
        try {
            if (id >= 0) {
                this.target = (EntityLivingBase)this.world.getEntityByID(id);
            }
        }
        catch (Exception ex) {}
    	
        this.lockX = data.readDouble();
        this.lockY = data.readDouble();
        this.lockZ = data.readDouble();
    }
    
    public void particleExplosion() {
    	for(int i = 0; i < 24; i++) {
			float r = 0.0F;
			float g = 0.8F + (float) Math.random() * 0.2F;
			float b = 0.4F + (float) Math.random() * 0.6F;
			float s = 0.3F + (float) Math.random() * 0.3F;
			float m = 0.4F;
			float xm = ((float) Math.random() - 0.5F) * m;
			float ym = ((float) Math.random() - 0.5F) * m;
			float zm = ((float) Math.random() - 0.5F) * m;
			
			Botania.proxy.setWispFXDistanceLimit(false);
			Botania.proxy.wispFX(this.lockX+0.5, this.lockY+1.25, this.lockZ+0.5, r, g, b, s, xm, ym, zm, 1.0F);
			Botania.proxy.setWispFXDistanceLimit(true);
		}
    }
    
    protected void onImpact(final RayTraceResult mop) {
    	
    }
    
    public float getShadowSize() {
        return 0.1f;
    }
    
	public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted > (30)) {
            this.setDead();
        }
		
        if (target == null) {
			this.setDead();
			return;
		}
		
		float size = (1/this.getDistance(target));
		
		if (size > 1.5F)
			size = 1.5F;
		
		for(int i = 0; i < 8; i++) {
			Botania.proxy.sparkleFX(this.posX + (Math.random() - 0.5) * 0.1, this.posY + (Math.random() - 0.5) * 0.1, this.posZ + (Math.random() - 0.5) * 0.1, (float) (0.9F + (Math.random() * 0.1F)), (float) (0.2F + (Math.random() * 0.2F)), 0.0F, size, 2);
		}
		
		Vector3 thisVec = Vector3.fromEntityCenter(this);
		Vector3 targetVec = Vector3.fromEntityCenter(target);
		Vector3 diffVec = targetVec.subtract(thisVec);
		Vector3 motionVec = diffVec.normalize().multiply(0.15);
		motionX = motionVec.x;
		motionY = motionVec.y;
		motionZ = motionVec.z;
        
		if (!this.world.isRemote) {
		
			if (target == null) {
				this.setDead();
				return;
			}
			
		List<EntityLivingBase> targetList = world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(0.1, 0.1, 0.1));
		if(targetList.contains(target)) {
			this.setDead();
		}
		}
		
    }
}
