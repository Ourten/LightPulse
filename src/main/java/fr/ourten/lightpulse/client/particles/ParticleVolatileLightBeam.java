package fr.ourten.lightpulse.client.particles;

import java.util.Queue;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Queues;

import dorkbox.tweenengine.Tween;
import dorkbox.tweenengine.TweenManager;
import fr.ourten.lightpulse.common.LightPulseVars;
import fr.ourten.lightpulse.common.util.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleVolatileLightBeam extends Particle
{
    public static Queue<ParticleVolatileLightBeam> queuedRenders = Queues.newArrayDeque();

    private float                                  partialTicks;
    private float                                  rotationX;
    private float                                  rotationZ;
    private float                                  rotationYZ;
    private float                                  rotationXY;
    private float                                  rotationXZ;

    private final Vector3d                         pos;

    private final TweenManager                     manager       = new TweenManager();

    private static final ResourceLocation          TEXTURE       = new ResourceLocation(LightPulseVars.MODID,
            "textures/particles/particle.png");

    public ParticleVolatileLightBeam(final World w, final Vector3d pos, final Vector3d dest, final double range,
            final double speed)
    {
        super(w, pos.getX(), pos.getY(), pos.getZ());
        this.particleScale = 0.75F;
        this.setSize(0.001f, 0.001f);
        this.setRBGColorF(0.96078431372f, 0.88235294117f, 0.36470588235f);
        this.motionX = 0;
        this.motionZ = 0;

        this.setParticleTextureIndex(0);
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

        this.posX = this.pos.getX();
        this.posY = this.pos.getY();
        this.posZ = this.pos.getZ();
        this.manager.update(1 / 20.0f);
    }

    public static void dispatchQueuedRenders(final Tessellator tessellator)
    {
        ParticleRenderDispatcher.volatileLightCount = 0;

        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        Minecraft.getMinecraft().renderEngine.bindTexture(ParticleVolatileLightBeam.TEXTURE);

        if (!ParticleVolatileLightBeam.queuedRenders.isEmpty())
        {
            tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            for (final ParticleVolatileLightBeam beam : ParticleVolatileLightBeam.queuedRenders)
                beam.renderQueued(tessellator, true);
            tessellator.draw();
        }
        ParticleVolatileLightBeam.queuedRenders.clear();
    }

    private void renderQueued(final Tessellator tessellator, final boolean depthEnabled)
    {
        ParticleRenderDispatcher.volatileLightCount++;

        final float uMin = 16 * (this.particleAge / this.particleMaxAge);
        final float uMax = 16 * (this.particleAge / this.particleMaxAge) + (1 / 16f);
        final float f4 = 0.1F * this.particleScale;
        final float posX = (float) (this.prevPosX + (this.posX - this.prevPosX) * this.partialTicks
                - Particle.interpPosX);
        final float posY = (float) (this.prevPosY + (this.posY - this.prevPosY) * this.partialTicks
                - Particle.interpPosY);
        final float posZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * this.partialTicks
                - Particle.interpPosZ);

        final VertexBuffer buff = tessellator.getBuffer();

        buff.pos(posX - this.rotationX * f4 - this.rotationXY * f4, posY - this.rotationZ * f4,
                posZ - this.rotationYZ * f4 - this.rotationXZ * f4).tex(uMax, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX - this.rotationX * f4 + this.rotationXY * f4, posY + this.rotationZ * f4,
                posZ - this.rotationYZ * f4 + this.rotationXZ * f4).tex(uMax, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + this.rotationX * f4 + this.rotationXY * f4, posY + this.rotationZ * f4,
                posZ + this.rotationYZ * f4 + this.rotationXZ * f4).tex(uMin, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + this.rotationX * f4 - this.rotationXY * f4, posY - this.rotationZ * f4,
                posZ + this.rotationYZ * f4 - this.rotationXZ * f4).tex(uMin, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
    }

    @Override
    public void renderParticle(final VertexBuffer wr, final Entity entity, final float partialTicks,
            final float rotationX, final float rotationZ, final float rotationYZ, final float rotationXY,
            final float rotationXZ)
    {
        this.partialTicks = partialTicks;
        this.rotationX = rotationX;
        this.rotationZ = rotationZ;
        this.rotationYZ = rotationYZ;
        this.rotationXY = rotationXY;
        this.rotationXZ = rotationXZ;

        ParticleVolatileLightBeam.queuedRenders.add(this);
    }
}