package fr.ourten.lightpulse.common.blocks.energy;

import fr.ourten.lightpulse.common.LightPulseVars;
import fr.ourten.lightpulse.common.blocks.ILightPulseBlockModel;
import fr.ourten.lightpulse.common.tiles.TileLightConsumer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLightConsumer extends BlockContainer implements ILightPulseBlockModel
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockLightConsumer()
    {
        super(Material.IRON);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ILightPulseBlockModel initItemModel()
    {
        final Item itemBlock = Item.getItemFromBlock(this);
        final ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(
                LightPulseVars.MODID + ":lightconsumer", "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, 0, itemModelResourceLocation);
        return this;
    }

    @Override
    public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state,
            final EntityLivingBase placer, final ItemStack stack)
    {
        worldIn.setBlockState(pos,
                state.withProperty(BlockLightConsumer.FACING, BlockLightConsumer.getFacingFromEntity(pos, placer)), 2);
    }

    public static EnumFacing getFacingFromEntity(final BlockPos pos, final EntityLivingBase entity)
    {
        if (MathHelper.abs((float) entity.posX - pos.getX()) < 2.0F
                && MathHelper.abs((float) entity.posZ - pos.getZ()) < 2.0F)
        {
            final double d0 = entity.posY + entity.getEyeHeight();

            if (d0 - pos.getY() > 2.0D)
                return EnumFacing.UP;
            if (pos.getY() - d0 > 0.0D)
                return EnumFacing.DOWN;
        }

        return entity.getHorizontalFacing().getOpposite();
    }

    @Override
    public IBlockState getStateFromMeta(final int meta)
    {
        EnumFacing enumfacing;

        switch (meta & 7)
        {
            case 0:
                enumfacing = EnumFacing.DOWN;
                break;
            case 1:
                enumfacing = EnumFacing.EAST;
                break;
            case 2:
                enumfacing = EnumFacing.WEST;
                break;
            case 3:
                enumfacing = EnumFacing.SOUTH;
                break;
            case 4:
                enumfacing = EnumFacing.NORTH;
                break;
            case 5:
            default:
                enumfacing = EnumFacing.UP;
        }

        return this.getDefaultState().withProperty(BlockLightConsumer.FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(final IBlockState state)
    {
        int i;

        switch (state.getValue(BlockLightConsumer.FACING))
        {
            case EAST:
                i = 1;
                break;
            case WEST:
                i = 2;
                break;
            case SOUTH:
                i = 3;
                break;
            case NORTH:
                i = 4;
                break;
            case UP:
            default:
                i = 5;
                break;
            case DOWN:
                i = 0;
        }
        return i;
    }

    @Override
    public IBlockState withRotation(final IBlockState state, final Rotation rot)
    {
        return state.withProperty(BlockLightConsumer.FACING, rot.rotate(state.getValue(BlockLightConsumer.FACING)));
    }

    @Override
    public IBlockState withMirror(final IBlockState state, final Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(BlockLightConsumer.FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { BlockLightConsumer.FACING });
    }

    @Override
    public EnumBlockRenderType getRenderType(final IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta)
    {
        return new TileLightConsumer();
    }
}