package com.walkoverhaul;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.io.File;

public class ClientProxy extends ServerProxy {

    @Override
    public File getDataDir() {
        return Minecraft.getMinecraft().gameDir;
    }

}
