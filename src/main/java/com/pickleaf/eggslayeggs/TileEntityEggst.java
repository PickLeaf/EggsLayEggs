package com.pickleaf.eggslayeggs;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class TileEntityEggst extends TileEntity implements IInventory {
    private ItemStack[] inventory = new ItemStack[9];

    private boolean lay(ItemStack itemStack) {
        if (!this.worldObj.isAirBlock(this.xCoord, this.yCoord - 1, this.zCoord))
            return false;
        EntityItem egg = new EntityItem(this.worldObj,
                this.xCoord + 0.5,
                this.yCoord - 0.25,
                this.zCoord + 0.5,
                itemStack);
        egg.motionX = 0;
        egg.motionY = 0;
        egg.motionZ = 0;
        egg.delayBeforeCanPickup = 0;
        this.worldObj.spawnEntityInWorld(egg);
        return true;
    }

    @Override
    public void updateEntity() {
        int chance = 0;
        for (int i = 0; i < this.getSizeInventory(); i++) {
            if (inventory[i] == null)
                continue;
            if (inventory[i].getItem() != Items.egg) {
                if (lay(inventory[i])) {
                    inventory[i] = null;
                }
                continue;
            }
            chance += inventory[i].stackSize;
        }
        if ((new Random()).nextFloat() < chance * Config.getChancePerEgg()) {
            lay(new ItemStack(Items.egg));
        }
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return 9;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack;

            if (this.inventory[slot].stackSize <= count) {
                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
            } else {
                itemstack = this.inventory[slot].splitStack(count);
                if (this.inventory[slot].stackSize == 0) {
                    this.inventory[slot] = null;
                }

            }
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack = this.inventory[slot];
            this.inventory[slot] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        this.inventory[slot] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return "container.Eggst";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this
                && player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
                        (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList items = compound.getTagList("Items", 10);
        for (int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound item = items.getCompoundTagAt(i);
            byte b0 = item.getByte("Slot");

            if (b0 >= 0 && b0 < this.inventory.length) {
                this.inventory[b0] = ItemStack.loadItemStackFromNBT(item);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList items = new NBTTagList();
        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(item);
                items.appendTag(item);
            }
        }
        compound.setTag("Items", items);
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return true;
    }
}
