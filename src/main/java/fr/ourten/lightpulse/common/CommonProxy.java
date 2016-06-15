package fr.ourten.lightpulse.common;

import fr.ourten.lightpulse.common.blocks.LightPulseBlockManager;
import fr.ourten.lightpulse.common.capabilities.energy.ILightConsumer;
import fr.ourten.lightpulse.common.capabilities.energy.ILightProducer;
import fr.ourten.lightpulse.common.capabilities.energy.ILightStorage;
import fr.ourten.lightpulse.common.capabilities.energy.LightCapabilities.BaseLightConsumer;
import fr.ourten.lightpulse.common.capabilities.energy.LightCapabilities.BaseLightProducer;
import fr.ourten.lightpulse.common.capabilities.energy.LightCapabilities.BaseLightStorage;
import fr.ourten.lightpulse.common.capabilities.energy.LightCapabilities.CapabilityLightConsumer;
import fr.ourten.lightpulse.common.capabilities.energy.LightCapabilities.CapabilityLightProducer;
import fr.ourten.lightpulse.common.capabilities.energy.LightCapabilities.CapabilityLightStorage;
import fr.ourten.lightpulse.common.items.LightPulseItemManager;
import fr.ourten.lightpulse.common.tiles.TileLightProducer;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy
{
    public void preInit(final FMLPreInitializationEvent e)
    {
        CapabilityManager.INSTANCE.register(ILightConsumer.class, new CapabilityLightConsumer<ILightConsumer>(),
                BaseLightConsumer.class);
        CapabilityManager.INSTANCE.register(ILightProducer.class, new CapabilityLightProducer<ILightProducer>(),
                BaseLightProducer.class);
        CapabilityManager.INSTANCE.register(ILightStorage.class, new CapabilityLightStorage<ILightStorage>(),
                BaseLightStorage.class);

        LightPulseBlockManager.getInstance().initBlocks();
        LightPulseItemManager.getInstance().initItems();

        GameRegistry.registerTileEntity(TileLightProducer.class, LightPulseVars.MODID + ":tileLightProducer");
    }

    public void init(final FMLInitializationEvent e)
    {

    }

    public void postInit(final FMLPostInitializationEvent e)
    {

    }
}