package com.keletu.forgotten_relics.recipes;

import java.util.ArrayList;
import java.util.List;

import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.config.RelicsConfigHandler;
import com.keletu.forgotten_relics.proxy.CommonProxy;
import com.keletu.forgotten_relics.utils.SuperpositionHandler;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipeOblivionStone extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	public RecipeOblivionStone(){
		super();
		setRegistryName("forge:oblivion_stone");
	}
	public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
		ItemStack repairedStack = ItemStack.EMPTY;
		List<ItemStack> stackList = new ArrayList<ItemStack>();
		ItemStack voidStone = ItemStack.EMPTY;

		for (int i = 0; i < par1InventoryCrafting.getSizeInventory(); i++) {
			ItemStack checkedItemStack = par1InventoryCrafting.getStackInSlot(i);

			if (checkedItemStack != ItemStack.EMPTY) {
				if (checkedItemStack.getItem() == CommonProxy.oblivionStone) {
					if (voidStone == ItemStack.EMPTY)
						voidStone = checkedItemStack;
					else
						return ItemStack.EMPTY;
				} else {
					stackList.add(checkedItemStack);
				}

			}

		}

		if (voidStone != ItemStack.EMPTY & stackList.size() == 1) {
			ItemStack savedStack = stackList.get(0).copy();

			NBTTagCompound nbt;

			if (voidStone.hasTagCompound())
				nbt = voidStone.getTagCompound().copy();
			else
				nbt = new NBTTagCompound();

			int[] arr = nbt.getIntArray("SupersolidID");
			int[] meta = nbt.getIntArray("SupersolidMetaID");
			int counter = 0;

			if (arr.length >= RelicsConfigHandler.oblivionStoneHardCap)
				return ItemStack.EMPTY;

			for (int s : arr) {
				int metaD = meta[counter];
				counter++;
				if (s == Item.getIdFromItem(savedStack.getItem()) & metaD != -1
						& metaD == savedStack.getItemDamage())
					return ItemStack.EMPTY;
				else if (s == Item.getIdFromItem(savedStack.getItem()) & metaD == -1)
					return ItemStack.EMPTY;
			}

			arr = SuperpositionHandler.addInt(arr, Item.getIdFromItem(savedStack.getItem()));

			if (!savedStack.isItemStackDamageable())
				meta = SuperpositionHandler.addInt(meta, savedStack.getItemDamage());
			else
				meta = SuperpositionHandler.addInt(meta, -1);

			nbt.setIntArray("SupersolidID", arr);
			nbt.setIntArray("SupersolidMetaID", meta);

			ItemStack returnedStack = voidStone.copy();
			returnedStack.setTagCompound(nbt);

			return returnedStack;
		} else if (voidStone != ItemStack.EMPTY & stackList.size() == 0) {
			return new ItemStack(CommonProxy.oblivionStone, 1, voidStone.getItemDamage());
		} else
			return ItemStack.EMPTY;

	}

	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	public int getRecipeSize() {
		return 10;
	}

	public boolean matches(InventoryCrafting par1InventoryCrafting, World arg1) {

		ItemStack repairedStack = ItemStack.EMPTY;
		List<ItemStack> stackList = new ArrayList<>();
		ItemStack voidStone = ItemStack.EMPTY;

		for (int i = 0; i < par1InventoryCrafting.getSizeInventory(); i++) {
			ItemStack checkedItemStack = par1InventoryCrafting.getStackInSlot(i);

			if (checkedItemStack != ItemStack.EMPTY) {
				if (checkedItemStack.getItem() == CommonProxy.oblivionStone) {
					if (voidStone == ItemStack.EMPTY)
						voidStone = checkedItemStack;
					else
						return false;
				} else {
					stackList.add(checkedItemStack);
				}

			}

		}

		if (voidStone != ItemStack.EMPTY & stackList.size() == 1) {
			ItemStack savedStack = stackList.get(0).copy();

			NBTTagCompound nbt;

			if (voidStone.hasTagCompound())
				nbt = voidStone.getTagCompound().copy();
			else
				nbt = new NBTTagCompound();

			int[] arr = nbt.getIntArray("SupersolidID");
			int[] meta = nbt.getIntArray("SupersolidMetaID");
			int counter = 0;

			if (arr.length >= RelicsConfigHandler.oblivionStoneHardCap)
				return false;

			for (int s : arr) {
				int metaD = meta[counter];
				counter++;
				if (s == Item.getIdFromItem(savedStack.getItem()) & metaD != -1
						& metaD == savedStack.getItemDamage())
					return false;
				else if (s == Item.getIdFromItem(savedStack.getItem()) & metaD == -1)
					return false;
			}

			return true;
		} else return voidStone != ItemStack.EMPTY & stackList.size() == 0;

	}

	public boolean canFit(int width, int height){
		return true;
	}
}
