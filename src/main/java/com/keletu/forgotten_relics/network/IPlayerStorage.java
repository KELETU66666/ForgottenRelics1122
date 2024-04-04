package com.keletu.forgotten_relics.network;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class IPlayerStorage implements IStorage<PlayerVariables>
{

	@Override
	public NBTBase writeNBT(Capability<PlayerVariables> capability, PlayerVariables instance, EnumFacing side)
	{
		NBTTagCompound compound = new NBTTagCompound();

		instance.saveNBTData(compound);

		return compound;
	}

	@Override
	public void readNBT(Capability<PlayerVariables> capability, PlayerVariables instance, EnumFacing side, NBTBase nbt)
	{
		if (nbt instanceof NBTTagCompound)
		{
			instance.loadNBTData((NBTTagCompound) nbt);
		}
	}

}