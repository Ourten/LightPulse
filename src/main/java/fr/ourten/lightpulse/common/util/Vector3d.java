package fr.ourten.lightpulse.common.util;

import lombok.Getter;

public class Vector3d
{
    @Getter
    private double x, y, z;

    public Vector3d(final Vector3d vector)
    {
        this.set(vector);
    }

    public Vector3d(final double x, final double y, final double z)
    {
        this.set(x, y, z);
    }

    public void set(final Vector3d vector)
    {
        this.set(vector.getX(), vector.getY(), vector.getZ());
    }

    public void set(final double x, final double y, final double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d addVector(final double x, final double y, final double z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
}