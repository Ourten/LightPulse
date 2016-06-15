package fr.ourten.lightpulse.common.blocks.energy;

import fr.ourten.lightpulse.common.tiles.TileLightProducer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLightProducer extends BlockContainer
{
    public BlockLightProducer()
    {
        super(Material.IRON);
    }

    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta)
    {
        return new TileLightProducer();
    }
}