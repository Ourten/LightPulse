package fr.ourten.lightpulse.client.particles;

import dorkbox.tweenengine.Tween;
import dorkbox.tweenengine.TweenManager;
import fr.ourten.lightpulse.common.util.Vector3d;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public class ParticleVolatileLightBeam extends Particle
{
    private final Vector3d pos;

    final TweenManager     manager = new TweenManager();

    public ParticleVolatileLightBeam(final World w, final Vector3d pos, final Vector3d dest, final double range,
            final double speed)
    {
        super(w, pos.getX(), pos.getY(), pos.getZ());
        this.particleScale = 0.75F;
        this.setSize(0.001f, 0.001f);
        this.setRBGColorF(0.96078431372f, 0.88235294117f, 0.36470588235f);
        this.motionX = 0;
        this.motionZ = 0;

        this.pos = pos;
        this.setMaxAge((int) (range / speed));
        Tween.to(this.pos, 3, (float) (range / speed) / 20)
                .target((float) dest.getX(), (float) dest.getY(), (float) dest.getZ()).start(this.manager);
        this.manager.pause();
    }

    @Override
    public void onUpdate()
    {
        this.manager.resume();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.manager.killAll();
            this.setExpired();
        }

        this.setParticleTextureIndex(7 - this.particleAge * 7 / this.particleMaxAge);
        this.posX = this.pos.getX();
        this.posY = this.pos.getY();
        this.posZ = this.pos.getZ();
        this.manager.update(1 / 20.0f);
    }
}