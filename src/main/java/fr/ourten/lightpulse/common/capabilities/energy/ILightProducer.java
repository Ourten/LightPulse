package fr.ourten.lightpulse.common.capabilities.energy;

public interface ILightProducer
{
    /**
     * Ask to produce a quantity of light.
     *
     * @param quantity
     *            The amount of light to produce.
     * @param simulated
     *            Used for grid calculations.
     * @return The amount of light actually produced.
     */
    abstract long produce(long quantity, boolean simulated);
}