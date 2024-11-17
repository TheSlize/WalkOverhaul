package com.walkoverhaul;

import com.walkoverhaul.configs.CommonConfig;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(
        modid = WAMain.MOD_ID,
        name = WAMain.MOD_NAME,
        version = WAMain.VERSION
)
public class WAMain {

    public static final String MOD_ID = "walkoverhaul";
    public static final String MOD_NAME = "Walk Overhaul";
    public static final String VERSION = "1.12.2-1.0";

    public static Logger logger;

    @Mod.Instance(MOD_ID)
    public static WAMain INSTANCE;

    @SidedProxy(clientSide = "com.walkoverhaul.ClientProxy", serverSide = "com.walkoverhaul.ServerProxy")
    public static ServerProxy proxy;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        if(logger == null)
            logger = event.getModLog();
        reloadConfig();
    }

    public static void reloadConfig() {
        Configuration config = new Configuration(new File(proxy.getDataDir().getPath() + "/config/walkoverhaul.cfg"));
        config.load();
        CommonConfig.loadFromConfig(config);
        config.save();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }
}
