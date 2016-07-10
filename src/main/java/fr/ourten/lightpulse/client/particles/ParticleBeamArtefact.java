package fr.ourten.lightpulse.client.particles;

import java.util.Queue;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Queues;

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

public class ParticleBeamArtefact extends Particle
{
    public static Queue<ParticleBeamArtefact> queuedRenders = Queues.newArrayDeque();

    private float                             partialTicks;

    private float                             innerWidth;
    private final float                       innerHeight;
    private float                             currentAlpha;
    private final float                       startWidth;
    private final double                      startY;

    private static final ResourceLocation     TEXTURE       = new ResourceLocation(LightPulseVars.MODID,
            "textures/particles/ring.png");

    public ParticleBeamArtefact(final World worldIn, final Vector3d pos)
    {
        super(worldIn, pos.getX(), pos.getY(), pos.getZ());
        this.setMaxAge(30);
        this.startY = this.posY;
        this.innerWidth = this.startWidth = .8f;
        this.particleAlpha = this.currentAlpha = .5f;
        this.innerHeight = .5f;
    }

    @Override
    public void onUpdate()
    {
        if (this.particleAge++ >= this.particleMaxAge)
            this.setExpired();
        this.prevPosY = this.posY;
        this.posY = this.startY + (1f * ((float) this.particleAge / this.particleMaxAge));
        this.currentAlpha = this.particleAlpha - (.5f * ((float) this.particleAge / this.particleMaxAge));
        this.innerWidth = this.startWidth - (.4f * ((float) this.particleAge / this.particleMaxAge));
    }

    public static void dispatchQueuedRenders(final Tessellator tessellator)
    {
        ParticleRenderDispatcher.beamArtefactCount = 0;

        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        Minecraft.getMinecraft().renderEngine.bindTexture(ParticleBeamArtefact.TEXTURE);

        if (!ParticleBeamArtefact.queuedRenders.isEmpty())
        {
            tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            for (final ParticleBeamArtefact beam : ParticleBeamArtefact.queuedRenders)
                beam.renderQueued(tessellator, true);
            tessellator.draw();
        }
        ParticleBeamArtefact.queuedRenders.clear();
    }

    private void renderQueued(final Tessellator tessellator, final boolean depthEnabled)
    {
        ParticleRenderDispatcher.beamArtefactCount++;

        final float posX = (float) (this.prevPosX + (this.posX - this.prevPosX) * this.partialTicks
                - Particle.interpPosX);
        final float posY = (float) (this.prevPosY + (this.posY - this.prevPosY) * this.partialTicks
                - Particle.interpPosY);
        final float posZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * this.partialTicks
                - Particle.interpPosZ);

        final VertexBuffer buff = tessellator.getBuffer();

        // Front Front
        buff.pos(posX - (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();

        // Front Back
        buff.pos(posX - (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();

        // Back Front
        buff.pos(posX + (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();

        // Back back
        buff.pos(posX + (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();

        // Left Front
        buff.pos(posX - (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();

        // Left Back
        buff.pos(posX + (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ - (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY, posZ - (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();

        // Right Front
        buff.pos(posX - (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();

        // Right Back
        buff.pos(posX + (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX + (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 1)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY + this.innerHeight, posZ + (this.innerWidth / 2)).tex(1, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
        buff.pos(posX - (this.innerWidth / 2), posY, posZ + (this.innerWidth / 2)).tex(0, 0)
                .color(this.particleRed, this.particleGreen, this.particleBlue, this.currentAlpha).endVertex();
    }

    @Override
    public void renderParticle(final VertexBuffer wr, final Entity entity, final float partialTicks,
            final float rotationX, final float rotationZ, final float rotationYZ, final float rotationXY,
            final float rotationXZ)
    {
        this.partialTicks = partialTicks;

        ParticleBeamArtefact.queuedRenders.add(this);
    }
}