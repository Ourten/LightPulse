package fr.ourten.lightpulse.common.tiles;

import java.util.Random;

import fr.ourten.lightpulse.common.LightPulse;
import fr.ourten.lightpulse.common.capabilities.energy.LightCapabilities;
import fr.ourten.lightpulse.common.capabilities.energy.LightCapabilities.BaseLightProducer;
import fr.ourten.lightpulse.common.util.Vector3d;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;

public class TileLightProducer extends TileEntity implements ITickable
{
    private final BaseLightProducer energy;
    private final Random            rand     = new Random();
    private long                    lastTime = 0;

    public TileLightProducer()
    {
        this.energy = new BaseLightProducer(50);
    }

    @Override
    public void update()
    {
        if (this.rand.nextBoolean())
            LightPulse.proxy.generateParticles("volatileLightBeam",
                    new Vector3d(this.getPos().getX() + .5 + (this.rand.nextFloat() - .5) / 4, this.getPos().getY() + 1,
                            this.getPos().getZ() + .5 + (this.rand.nextFloat() - .5) / 4));

        if (System.currentTimeMillis() - this.lastTime > 20000)
        {
            LightPulse.proxy.generateParticles("wallBeam",
                    new Vector3d(this.getPos().getX() + .5, this.getPos().getY() + 1, this.getPos().getZ() + .5));
            this.lastTime = System.currentTimeMillis();
        }
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