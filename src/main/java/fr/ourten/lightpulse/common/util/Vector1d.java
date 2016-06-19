package fr.ourten.lightpulse.common.util;

import lombok.Getter;

public class Vector1d
{
    @Getter
    private double x;

    public Vector1d(final Vector1d vector)
    {
        this.set(vector);
    }

    public Vector1d(final double x)
    {
        this.set(x);
    }

    public void set(final Vector1d vector)
    {
        this.set(vector.getX());
    }

    public void set(final double x)
    {
        this.x = x;
    }

    public Vector1d addVector(final double x)
    {
        this.x += x;
        return this;
    }
}