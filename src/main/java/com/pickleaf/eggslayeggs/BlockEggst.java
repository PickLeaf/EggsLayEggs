package com.pickleaf.eggslayeggs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.block.Block;

public final class BlockEggst extends BlockContainer {

    private static IIcon icon_side = null;
    private static IIcon icon_top = null;
    private static IIcon icon_bottom = null;

    public BlockEggst() {
        super(Material.rock);
        this.setHardness(2.5F);
        this.setResistance(5.0F);
        this.setBlockName("Eggst");
        this.setTickRandomly(false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        BlockEggst.icon_top = iconRegister.registerIcon("eggslayeggs:eggst_top");
        BlockEggst.icon_bottom = iconRegister.registerIcon("eggslayeggs:eggst_bottom");
        BlockEggst.icon_side = iconRegister.registerIcon("eggslayeggs:eggst_side");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        switch (side) {
            case 0:
                return BlockEggst.icon_bottom;
            case 1:
                return BlockEggst.icon_top;
        }
        return BlockEggst.icon_side;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_,
            float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (world.isRemote) {
            return true;
        } else {
            TileEntityEggst tileEntityEggst = (TileEntityEggst) world.getTileEntity(x, y, z);
            if (tileEntityEggst != null) {
                player.displayGUIChest(tileEntityEggst);
            }
            return true;
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
        TileEntityEggst eggst = (TileEntityEggst) world.getTileEntity(x, y, z);
        for (int i = 0; i < eggst.getSizeInventory(); i++) {
            if (eggst.getStackInSlot(i) == null)
                continue;
            else {
                EntityItem entityItem = new EntityItem(world, x+0.5, y+0.5, z+0.5,
                        eggst.getStackInSlot(i));
                world.spawnEntityInWorld(entityItem);
            }
        }
        world.removeTileEntity(x, y, z);
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int p_149736_5_) {
        return Container.calcRedstoneFromInventory((TileEntityEggst) world.getTileEntity(x, y, z));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityEggst();
    }
}
