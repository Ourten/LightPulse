package fr.ourten.lightpulse.client;

import org.apache.commons.lang3.StringUtils;

import dorkbox.tweenengine.Tween;
import dorkbox.tweenengine.TweenAccessor;
import fr.ourten.lightpulse.client.particles.ParticleRenderDispatcher;
import fr.ourten.lightpulse.client.particles.ParticleVolatileLightBeam;
import fr.ourten.lightpulse.client.particles.ParticleWallBeam;
import fr.ourten.lightpulse.common.CommonProxy;
import fr.ourten.lightpulse.common.blocks.LightPulseBlockManager;
import fr.ourten.lightpulse.common.util.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy
{
    @Override
    public void init(final FMLInitializationEvent e)
    {
        super.init(e);

        MinecraftForge.EVENT_BUS.register(new ParticleRenderDispatcher());
        Tween.registerAccessor(Vector3d.class, new VectorAccessor());
        LightPulseBlockManager.getInstance().blockModels.forEach(model -> model.initItemModel());
    }

    @Override
    public void generateParticles(final String identifier, final Vector3d pos)
    {
        if (Minecraft.getMinecraft().theWorld != null)
            if (StringUtils.equals(identifier, "volatileLightBeam"))
            {
                final Particle particleLight = new ParticleVolatileLightBeam(Minecraft.getMinecraft().theWorld, pos,
                        new Vector3d(pos).addVector(0, 10, 0), 20, 0.4);
                Minecraft.getMinecraft().effectRenderer.addEffect(particleLight);
            }
            else if (StringUtils.equals(identifier, "wallBeam"))
            {
                final Particle particleWall = new ParticleWallBeam(Minecraft.getMinecraft().theWorld, pos);
                Minecraft.getMinecraft().effectRenderer.addEffect(particleWall);
            }
            else
                System.out.println("Unknown particle [" + identifier + "] has been requested");
    }

    private static class VectorAccessor implements TweenAccessor<Vector3d>
    {
        @Override
        public int getValues(final Vector3d target, final int tweenType, final float[] returnValues)
        {
            if (tweenType == 3)
            {
                returnValues[0] = (float) target.getX();
                returnValues[1] = (float) target.getY();
                returnValues[2] = (float) target.getZ();
                return 3;
            }
            return 0;
        }

        @Override
        public void setValues(final Vector3d target, final int tweenType, final float[] newValues)
        {
            if (tweenType == 3)
                target.set(newValues[0], newValues[1], newValues[2]);
        }
    }
}