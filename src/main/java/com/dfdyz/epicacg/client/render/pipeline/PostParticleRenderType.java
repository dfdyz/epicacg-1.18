package com.dfdyz.epicacg.client.render.pipeline;

import com.dfdyz.epicacg.registry.PostEffects;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import static net.minecraft.client.Minecraft.ON_OSX;


public abstract class PostParticleRenderType implements ParticleRenderType {
    private final ResourceLocation renderTypeID;
    private final ResourceLocation texture;

    protected boolean started = false;

    public PostParticleRenderType(ResourceLocation renderTypeID, ResourceLocation texture){
        this.renderTypeID = renderTypeID;
        this.texture = texture;
    }

    @Override
    public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.setShader(this::getShader);

        if(texture != null) RenderUtils.GLSetTexture(texture);

        getPipeline().start();
        setupBufferBuilder(bufferBuilder);
    }

    ShaderInstance getShader(){
        return GameRenderer.positionColorTexLightmapShader;
    }

    public void callPipeline(){
        getPipeline().call();
    }

    //simple_post_particle
    @Override
    public void end(Tesselator tesselator) {
        tesselator.getBuilder().setQuadSortOrigin(0.0F, 0.0F, 0.0F);
        tesselator.end();

        getPipeline().suspend();

        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableCull();
    }

    public static RenderTarget createTempTarget(RenderTarget screenTarget) {
        RenderTarget rendertarget = new TextureTarget(screenTarget.width, screenTarget.height, true, ON_OSX);
        rendertarget.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        rendertarget.clear(ON_OSX);
        //if (screenTarget.isStencilEnabled()) { rendertarget.enableStencil(); }
        return rendertarget;
    }

    public void setupBufferBuilder(BufferBuilder bufferBuilder){
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
    }

    public abstract PostParticlePipelines.Pipeline getPipeline();

    public static void DepthCull(RenderTarget source, RenderTarget DepthBuffer, RenderTarget output){
        //RenderTarget blur = createTempTarget(source);
        //RenderTarget blur2 = createTempTarget(source);

        PostEffects.depth_cull.process(source, output,
                (effect) ->
                {
                    effect.setSampler("SrcDepth",
                            source::getDepthTextureId);
                    effect.setSampler("GlobalDepth",
                            DepthBuffer::getColorTextureId);
                });

        //blur1.destroyBuffers();
        //blur2.destroyBuffers();
    }

    public static void Blit(RenderTarget source, RenderTarget output){
        //RenderTarget blur = createTempTarget(source);
        //RenderTarget blur2 = createTempTarget(source);

        PostEffects.blit.process(source, output,
                (effect) ->
                {}
        );

        //blur1.destroyBuffers();
        //blur2.destroyBuffers();
    }

    public String toString() {
        return renderTypeID.toString();
    }
}
