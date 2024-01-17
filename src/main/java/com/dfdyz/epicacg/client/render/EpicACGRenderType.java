package com.dfdyz.epicacg.client.render;


import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.render.pipeline.PostParticlePipelines;
import com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType;
import com.dfdyz.epicacg.registry.PostEffects;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.dfdyz.epicacg.utils.TestUtils;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@OnlyIn(Dist.CLIENT)
public class EpicACGRenderType {
    public static final ResourceLocation ChildSkillnoSelected = GetTextures("gui/noselected");
    public static final ResourceLocation ChildSkillSelected = GetTextures("gui/selected");

    public static final ResourceLocation GS_BOW_SHOOT_PARTICLE_TEX = GetTextures("particle/genshin_bow");
    public static final ResourceLocation GS_BOW_LANDONG_PARTICLE_TEX = GetTextures("particle/genshin_bow_landing");
    public static final ResourceLocation GS_BOW_LANDONG_PARTICLE_TEX3 = GetTextures("particle/genshin_bow_landing3");

    public static final ResourceLocation BLACK_KNIGHT_PARTICLE_TEX = GetTextures("particle/blackknight_hit");
    public static final ResourceLocation BLOOD_THIRSTY_PARTICLE_TEX = GetTextures("particle/bloodthirsty_hit");


    public static final PostQuadParticleRT SAO_DEATH_PARTICLE = new PostQuadParticleRT(
            new ResourceLocation(EpicACG.MODID, "sao_death"),
            GetTextures("particle/sao_death")
    );
            //new EpicACGQuadParticleRenderType("textures/particle/sao_death", "SAO_DEATH");
    public static final PostQuadParticleRT GENSHIN_BOW_PARTICLE = new PostQuadParticleRT(
            new ResourceLocation(EpicACG.MODID, "genshin_bow"), GS_BOW_SHOOT_PARTICLE_TEX
    );

    public static final PostQuadParticleRT GENSHIN_BOW_LANDING = new PostQuadParticleRT(
            new ResourceLocation(EpicACG.MODID, "genshin_bow_landing"), GS_BOW_LANDONG_PARTICLE_TEX
    );

    public static final PostQuadParticleRT GENSHIN_BOW_LANDING2 = new PostQuadParticleRT(
            new ResourceLocation(EpicACG.MODID, "genshin_bow_landing2"), GS_BOW_LANDONG_PARTICLE_TEX3
    );

    public static final PostQuadParticleRT BLOOM_TRAIL = new PostQuadParticleRT(
            new ResourceLocation(EpicACG.MODID, "efm_trail"), null
    );

    public static final EpicACGQuadParticleRenderType QUAD_PARTICLE = new EpicACGQuadParticleRenderType("quad_particle", null);



    /*
            //new EpicACGQuadParticleRenderType("textures/particle/genshin_bow", "GENSHIN_BOW");
    public static final ParticleRenderType GENSHIN_BOW_LANDING_PARTICLE = new PostQuadParticleRT(
                    new ResourceLocation(EpicACG.MODID, "genshin_bow")
//                    GetTextures("particle/genshin_bow_landing")
            );
                    //new EpicACGQuadParticleRenderType("textures/particle/genshin_bow_landing", "GENSHIN_BOW");

    public static final ParticleRenderType GENSHIN_BOW_LANDING_PARTICLE3 = new PostQuadParticleRT(
            new ResourceLocation(EpicACG.MODID, "genshin_bow")
//            GetTextures("particle/genshin_bow_landing3")
    );
            //new EpicACGQuadParticleRenderType("textures/particle/genshin_bow_landing3", "GENSHIN_BOW");
*/
    public static ResourceLocation GetTextures(String path){
        return new ResourceLocation(EpicACG.MODID, "textures/" + path + ".png");
    }

    public static class PostQuadParticleRT extends PostParticleRenderType{
        public PostQuadParticleRT(ResourceLocation renderTypeID, ResourceLocation tex) {
            super(renderTypeID, tex);
        }

        @Override
        public PostParticlePipelines.Pipeline getPipeline() {
            return ppl;
        }

        static final PostParticlePipelines.Pipeline ppl = new PostParticlePipelines.Pipeline(){
            static void compositePass(RenderTarget up, RenderTarget down){
                RenderTarget target = createTempTarget(down);
                PostEffects.blit.process(down, target);
                PostEffects.composite.process(up, down,
                        (effect) ->
                        {
                            effect.setSampler("DiffuseSampler2",
                                    target::getColorTextureId);
                        });
                target.destroyBuffers();
            }
            static void _blurPass(RenderTarget source, RenderTarget output, int radiu, float dirx, float diry, float alpha, float bright){
                PostEffects.blur.process(source, output,
                        (effect) ->
                        {
                            effect.safeGetUniform("BlurDir").set(dirx, diry);
                            effect.safeGetUniform("Radius").set(radiu);
                            effect.safeGetUniform("_alpha").set(alpha);
                            effect.safeGetUniform("_bright").set(bright);
                        });
            }

            static void blurPass(RenderTarget source,RenderTarget blur, RenderTarget tmp, RenderTarget output, int radiu, float[] dir, float alpha, float bright){
                //RenderTarget blur = createTempTarget(source);
                //RenderTarget blur2 = createTempTarget(source);

                _blurPass(source, blur, radiu, dir[0], dir[1], alpha, bright);
                PostEffects.composite.process(blur, tmp,
                        (effect) ->
                        {
                            effect.setSampler("DiffuseSampler2",
                                    output::getColorTextureId);
                        });

                _blurPass(source, blur, radiu, dir[2], dir[3], alpha, bright);
                PostEffects.composite.process(blur, output,
                        (effect) ->
                        {
                            effect.setSampler("DiffuseSampler2",
                                    tmp::getColorTextureId);
                        });

                //blur1.destroyBuffers();
                //blur2.destroyBuffers();
            }



            @Override
            public void PostEffectHandler() {
                RenderTarget target = createTempTarget(sourceTarget);
                //PostEffects.blit.process(sourceTarget, target);
                //1

                PostEffects.composite.process(sourceTarget, target,
                        (effect) ->
                        {
                            effect.setSampler("DiffuseSampler2",
                                    Minecraft.getInstance().getMainRenderTarget()::getColorTextureId);
                        });

                //blurPass(sourceTarget, blur4_1, 4, new float[]{ 0, 2f}, 0.7f, 0f);
                //blurPass(sourceTarget, blur4_2, 2, new float[]{ 1.5, 2f}, 0.7f, 0f);
                //blurPass(sourceTarget, target, 16, new float[]{ 1, 1, -1, 1 });
                RenderTarget blur = createTempTarget(sourceTarget);
                RenderTarget temp = createTempTarget(sourceTarget);

                blurPass(sourceTarget, blur, temp, target, 32, new float[]{ 0, 2f, 2f, 0 }, 1f, 0.5f);
                blurPass(sourceTarget, blur, temp, target, 16, new float[]{ 1.7f, 1.7f, -1.7f, 1.7f }, 1f, 0.5f);
                blurPass(sourceTarget, blur, temp, target, 8, new float[]{ 0f, 1.5f, 1.5f, 0f }, 1f, 0.5f);
                //blurPass(sourceTarget, blur, temp, target, 2, new float[]{ 1f, 1f, -1f, 1f }, 1f, 0.5f);
                //blurPass(sourceTarget, blur, temp, target, 2, new float[]{ 0, 2f, 2f, 0 }, 0.6f, 0.3f);


                //blurPass(sourceTarget, target, 4, new float[]{ 1.2f, 1.2f, -1.2f, 1.2f }, 0.9f, 0.1f);
                //blurPass(sourceTarget, target, 4, new float[]{ 0, 1.7f, 1.7f, 0 }, 0.9f, 0.1f);
                //blurPass(sourceTarget, target, 4, new float[]{ 0, 1.2f, 1.2f, 0 }, 0.9f, 0.1f);

                //blurPass(sourceTarget, target, 2, new float[]{ 1, 1, -1, 1 });

                PostEffects.blit.process(target, Minecraft.getInstance().getMainRenderTarget());
                target.destroyBuffers();
                blur.destroyBuffers();
                temp.destroyBuffers();
            }
        };
    }


    public static class EpicACGQuadParticleRenderType implements ParticleRenderType {
        private final ResourceLocation Texture;
        private final String Name;

        public EpicACGQuadParticleRenderType(String name, ResourceLocation tex) {
            this.Texture = tex;
            Name = name;
        }

        public void begin(BufferBuilder p_107448_, TextureManager p_107449_) {
            //RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            //System.out.println("aaaaaaaaa");
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);

            if(Texture != null) RenderUtils.SetCurrentTexture(Texture);

            p_107448_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
        }

        public void end(Tesselator tesselator) {
            tesselator.getBuilder().setQuadSortOrigin(0.0F, 0.0F, 0.0F);
            tesselator.end();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();
        }

        public String toString() {
            return Name;
        }
    };



}
