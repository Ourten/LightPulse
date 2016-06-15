package fr.ourten.lightpulse.common.blocks;

import fr.ourten.lightpulse.common.LightPulse;
import fr.ourten.lightpulse.common.blocks.energy.BlockLightProducer;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class LightPulseBlockManager
{
    private static volatile LightPulseBlockManager instance = null;

    public static final LightPulseBlockManager getInstance()
    {
        if (LightPulseBlockManager.instance == null)
            synchronized (LightPulseBlockManager.class)
            {
                if (LightPulseBlockManager.instance == null)
                    LightPulseBlockManager.instance = new LightPulseBlockManager();
            }
        return LightPulseBlockManager.instance;
    }

    public final BlockLightProducer lightProducer = (BlockLightProducer) new BlockLightProducer()
            .setCreativeTab(LightPulse.TAB_LIGHT_PULSE).setUnlocalizedName("BlockLightPulse")
            .setRegistryName("BlockLightPulse");

    public void initBlocks()
    {
        this.registerBlockWithItemBlock(this.lightProducer);
    }

    private void registerBlockWithItemBlock(final Block b)
    {
        GameRegistry.register(b);
        GameRegistry.register(new ItemBlock(b));
    }
}