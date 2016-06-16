package fr.ourten.lightpulse.client;

import fr.ourten.lightpulse.common.CommonProxy;
import fr.ourten.lightpulse.common.blocks.LightPulseBlockManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy
{
    @Override
    public void init(final FMLInitializationEvent e)
    {
        super.init(e);

        LightPulseBlockManager.getInstance().blockModels.forEach(model -> model.initItemModel());
    }
}