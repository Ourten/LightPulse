package fr.ourten.lightpulse.common.tiles;

import fr.ourten.lightpulse.common.capabilities.energy.LightCapabilities;
import fr.ourten.lightpulse.common.capabilities.energy.LightCapabilities.BaseLightStorage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TileLightProducer extends TileEntity
{
    private final BaseLightStorage energy;

    public TileLightProducer()
    {
        this.energy = new BaseLightStorage();
    }

    @Override
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing)
    {
        if (capability == LightCapabilities.CAPABILITY_PRODUCER)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing)
    {
        if (capability == LightCapabilities.CAPABILITY_PRODUCER)
            return (T) this.energy;
        return super.getCapability(capability, facing);
    }
}