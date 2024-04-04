package com.keletu.forgotten_relics.network;

import com.keletu.forgotten_relics.Main;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class IPlayerProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound>
{

	private final PlayerVariables playerVariables;

	public IPlayerProvider(PlayerVariables playerVariables)
	{
		this.playerVariables = playerVariables;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == Main.PLAYER;
	}

	@Override
	@SuppressWarnings("unchecked") // Yay...
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if (capability == Main.PLAYER)
		{
			return (T) this.playerVariables;
		}

		return null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound output = new NBTTagCompound();

		if (this.playerVariables != null)
		{
			this.playerVariables.saveNBTData(output);
		}

		return output;
	}

	@Override
	public void deserializeNBT(NBTTagCompound input)
	{
		if (this.playerVariables != null)
		{
			this.playerVariables.loadNBTData(input);
		}
	}

}