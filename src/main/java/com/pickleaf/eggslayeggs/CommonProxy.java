package com.pickleaf.eggslayeggs;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;

public class CommonProxy {

    private static Block blockEggst;

    public void preInit(FMLPreInitializationEvent event) {
        Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
        float chancePerEgg = cfg.getFloat("chancePerEgg", Configuration.CATEGORY_GENERAL, (float) 0.02 / (16 * 9), 0, 1, "每个蛋在每个Tick增加的产出蛋的概率,最终概率=蛋的数量*这个概率,默认为0.02/(16*9)");
        Config.setChancePerEgg(chancePerEgg);
        if (cfg.hasChanged()) cfg.save();

        blockEggst = new BlockEggst().setCreativeTab(CreativeTabs.tabRedstone);
        TileEntity.addMapping(TileEntityEggst.class, "EggsLayEggs:Eggst");
        GameRegistry.registerBlock(blockEggst, ItemBlockEggst.class, blockEggst.getUnlocalizedName());
    }

    public void init(FMLInitializationEvent event) {
        GameRegistry.addRecipe(new ItemStack(blockEggst, 1),
                "CIC", "CHC", "CDC",
                'C', Blocks.cobblestone,
                'H', Blocks.hay_block,
                'I', Blocks.iron_block,
                'D', Blocks.dropper);
    }
}
