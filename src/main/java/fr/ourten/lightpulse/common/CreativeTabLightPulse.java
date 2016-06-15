package fr.ourten.lightpulse.common;

import fr.ourten.lightpulse.common.blocks.LightPulseBlockManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabLightPulse extends CreativeTabs
{
    public CreativeTabLightPulse()
    {
        super("tabLightPulse");
    }

    @Override
    public Item getTabIconItem()
    {
        return Item.getItemFromBlock(LightPulseBlockManager.getInstance().lightProducer);
    }
}