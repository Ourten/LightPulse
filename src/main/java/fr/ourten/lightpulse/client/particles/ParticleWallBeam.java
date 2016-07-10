package fr.ourten.lightpulse.client.particles;

import java.util.Queue;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Queues;

import dorkbox.tweenengine.Tween;
import dorkbox.tweenengine.TweenManager;
import fr.ourten.lightpulse.common.LightPulseVars;
import fr.ourten.lightpulse.common.util.Vector1d;
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

public class ParticleWallBeam extends Particle
{
    public static Queue<ParticleWallBeam> queuedRenders = Queues.newArrayDeque();
    private float                         partialTicks;

    private final Vector1d                alpha;
    private final float                   innerWidth, innerHeight;
    private final TweenManager            manager       = new TweenManager();

    private static final ResourceLocation TEXTURE       = new ResourceLocation(LightPulseVars.MODID,
            "textures/particles/lolilol.png");

    public ParticleWallBeam(final World worldIn, final Vector3d pos)
    {
        super(worldIn, pos.getX(), pos.getY(), pos.getZ());
        this.setMaxAge(400);
        this.innerWidth = 0.4f;
        this.particleAlpha = 0.2f;
        this.alpha = new Vector1d(this.particleAlpha);
        this.innerHeight = 5;
        Tween.to(this.alpha, 1, 2).target(0.6f).repeatAutoReverse(-1, .5f).start(this.manager);
    }

    @Override
    public void onUpdate()
    {
        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.manager.killAll();
            this.setExpired();
        }
        this.particleAlpha = (float) this.alpha.getX();
        this.manager.update(1 / 20.0f);
    }

    public static void dispatchQueuedRenders(final Tessellator tessellator)
    {
        ParticleRenderDispatcher.wallBeamCount = 0;

        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        Minecraft.getMinecraft().renderEngine.bindTexture(ParticleWallBeam.TEXTURE);

        if (!ParticleWallBeam.queuedRenders.isEmpty())
        {
            tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            for (final ParticleWallBeam beam : ParticleWallBeam.queuedRenders)
                beam.renderQueued(tessellator, true);
            tessellator.draw();
        }
        ParticleWallBeam.queuedRenders.clear();
    }

    private void renderQueued(final Tessellator tessellator, final boolean depthEnabled)
    {
        ParticleRenderDispatcher.wallBeamCount++;

        final float posX = (float) (this.prevPosX + (this.posX - this.prevPosX) * this.partialTicks
                - Particle.interpPosX);
        final float posY = (float) (this.prevPosY + (this.posY - this.prevPosY) * this.partialTicks
                - Particle.interpPosY);
        final float posZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * this.partialTicks
                - Particle.interpPosZ);

        final VertexBuffer buff = tessellator.getBuffer();

        // Front Front
        buff.pos(posX - (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();

        // Front Back
        buff.pos(posX - (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();

        // Back Front
        buff.pos(posX + (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();

        // Back back
        buff.pos(posX + (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();

        // Left Front
        buff.pos(posX - (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();

        // Left Back
        buff.pos(posX + (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();

        // Right Front
        buff.pos(posX - (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();

        // Right Back
        buff.pos(posX + (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
    }

    @Override
    public void renderParticle(final VertexBuffer wr, final Entity entity, final float partialTicks,
            final float rotationX, final float rotationZ, final float rotationYZ, final float rotationXY,
            final float rotationXZ)
    {
        this.partialTicks = partialTicks;

        ParticleWallBeam.queuedRenders.add(this);
    }
}