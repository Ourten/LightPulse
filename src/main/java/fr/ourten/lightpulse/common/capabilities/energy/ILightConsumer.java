package fr.ourten.lightpulse.common.capabilities.energy;

public interface ILightConsumer
{
    /**
     * Ask to consume a quantity of light.
     *
     * @param quantity
     *            The amount of light to consume.
     * @param simulated
     *            Used for grid calculations.
     * @return The amount of light actually consumed.
     */
    abstract long consume(long quantity, boolean simulated);
}