package fr.ourten.lightpulse.common.blocks;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import fr.ourten.lightpulse.common.LightPulse;
import fr.ourten.lightpulse.common.blocks.energy.BlockLightProducer;
import fr.ourten.lightpulse.common.blocks.energy.ILightPulseBlockModel;
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

    public final ArrayList<ILightPulseBlockModel> blockModels   = Lists.newArrayList();

    public final BlockLightProducer               lightProducer = (BlockLightProducer) new BlockLightProducer()
            .setCreativeTab(LightPulse.TAB_LIGHT_PULSE).setUnlocalizedName("BlockLightProducer");

    public void initBlocks()
    {
        this.registerBlockWithItemBlock(this.lightProducer, "lightproducer");

        this.blockModels.add(this.lightProducer);
    }

    private void registerBlockWithItemBlock(final Block b, final String registryName)
    {
        b.setRegistryName(registryName);
        GameRegistry.register(b);
        final ItemBlock itemBlock = new ItemBlock(b);
        itemBlock.setRegistryName(registryName);
        GameRegistry.register(itemBlock);
    }
}