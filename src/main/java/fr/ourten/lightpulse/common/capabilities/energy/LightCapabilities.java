package fr.ourten.lightpulse.common.capabilities.energy;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class LightCapabilities
{
    @CapabilityInject(ILightConsumer.class)
    public static Capability<ILightConsumer> CAPABILITY_CONSUMER = null;

    @CapabilityInject(ILightProducer.class)
    public static Capability<ILightProducer> CAPABILITY_PRODUCER = null;

    @CapabilityInject(ILightStorage.class)
    public static Capability<ILightStorage>  CAPABILITY_HOLDER   = null;

    public static class CapabilityLightConsumer<T extends ILightConsumer> implements IStorage<ILightConsumer>
    {

        @Override
        public NBTBase writeNBT(final Capability<ILightConsumer> capability, final ILightConsumer instance,
                final EnumFacing side)
        {

            return null;
        }

        @Override
        public void readNBT(final Capability<ILightConsumer> capability, final ILightConsumer instance,
                final EnumFacing side, final NBTBase nbt)
        {

        }
    }

    public static class CapabilityLightProducer<T extends ILightProducer> implements IStorage<ILightProducer>
    {

        @Override
        public NBTBase writeNBT(final Capability<ILightProducer> capability, final ILightProducer instance,
                final EnumFacing side)
        {

            return null;
        }

        @Override
        public void readNBT(final Capability<ILightProducer> capability, final ILightProducer instance,
                final EnumFacing side, final NBTBase nbt)
        {

        }
    }

    public static class CapabilityLightStorage<T extends ILightStorage> implements IStorage<ILightStorage>
    {

        @Override
        public NBTBase writeNBT(final Capability<ILightStorage> capability, final ILightStorage instance,
                final EnumFacing side)
        {
            final NBTTagCompound tag = new NBTTagCompound();

            tag.setLong("stored", instance.getStoredLight());
            tag.setLong("capacity", instance.getCapacity());
            return tag;
        }

        @Override
        public void readNBT(final Capability<ILightStorage> capability, final ILightStorage instance,
                final EnumFacing side, final NBTBase nbt)
        {
            final NBTTagCompound tag = (NBTTagCompound) nbt;
            instance.setCapacity(tag.getLong("capacity"));
            instance.setStoredLight(tag.getLong("stored"));
        }
    }

    public static class BaseLightProducer implements ILightProducer
    {
        private long outputRate;

        public BaseLightProducer()
        {

        }

        public BaseLightProducer(final long outputRate)
        {
            this.outputRate = outputRate;
        }

        @Override
        public long produce(final long quantity, final boolean simulated)
        {
            if (quantity >= this.outputRate)
                return this.outputRate;
            else
                return quantity;
        }
    }

    public static class BaseLightConsumer implements ILightConsumer
    {
        @Override
        public long consume(final long quantity, final boolean simulated)
        {
            return quantity;
        }
    }

    public static class BaseLightStorage implements ILightStorage
    {
        private long capacity;
        private long stored;

        @Override
        public long getStoredLight()
        {
            return this.stored;
        }

        @Override
        public long getCapacity()
        {
            return this.capacity;
        }

        @Override
        public void setStoredLight(final long light)
        {
            this.stored = light;
        }

        @Override
        public void setCapacity(final long capacity)
        {
            this.capacity = capacity;
        }
    }
}