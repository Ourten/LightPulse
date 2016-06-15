package fr.ourten.lightpulse.common.capabilities.energy;

public interface ILightStorage
{
    abstract long getStoredLight();

    abstract long getCapacity();

    abstract void setStoredLight(long light);

    abstract void setCapacity(long capacity);
}