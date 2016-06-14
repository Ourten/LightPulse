package fr.ourten.lightpulse.common;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LightPulseVars.MODID, version = LightPulseVars.VERSION, name = LightPulseVars.MODNAME)
public class LightPulse
{
    @Instance(LightPulseVars.MODID)
    public static LightPulse  instance;

    @SidedProxy(clientSide = "fr.ourten.lightpulse.client.ClientProxy", serverSide = "fr.ourten.lightpulse.server.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event)
    {
        LightPulse.proxy.preInit(event);
    }

    @EventHandler
    public void init(final FMLInitializationEvent event)
    {
        LightPulse.proxy.init(event);
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event)
    {
        LightPulse.proxy.postInit(event);
    }
}