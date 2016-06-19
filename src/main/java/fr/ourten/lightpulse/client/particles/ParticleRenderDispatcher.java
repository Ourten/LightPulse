package fr.ourten.lightpulse.client.particles;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.profiler.Profiler;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class ParticleRenderDispatcher
{
    public static int beamArtefactCount = 0;
    public static int wallBeamCount     = 0;

    @SubscribeEvent
    public void onRenderWorldLast(final RenderWorldLastEvent event)
    {
        final Tessellator tessellator = Tessellator.getInstance();

        final Profiler profiler = Minecraft.getMinecraft().mcProfiler;

        GL11.glPushAttrib(GL11.GL_LIGHTING);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);
        GlStateManager.disableLighting();

        profiler.startSection("lightpulse-particles");
        profiler.startSection("wallbeam");
        ParticleWallBeam.dispatchQueuedRenders(tessellator);
        profiler.endStartSection("beamartefact");
        ParticleBeamArtefact.dispatchQueuedRenders(tessellator);
        profiler.endSection();
        profiler.endSection();

        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GL11.glPopAttrib();
    }
}