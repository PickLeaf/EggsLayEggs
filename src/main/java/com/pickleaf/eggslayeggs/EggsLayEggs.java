package com.pickleaf.eggslayeggs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = EggsLayEggs.MODID, version = EggsLayEggs.VERSION, acceptedMinecraftVersions = "1.7.10")
public class EggsLayEggs {
    public static final String MODID = "Eggs Lay Eggs";
    public static final String VERSION = "1.1";

    @Mod.Instance(EggsLayEggs.MODID)
    public static EggsLayEggs instance;

    @SidedProxy(clientSide = "com.pickleaf.eggslayeggs.ClientProxy", serverSide = "com.pickleaf.eggslayeggs.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
