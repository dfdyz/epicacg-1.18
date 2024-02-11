package com.dfdyz.epicacg.client.render;


import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.render.custom.BloomParticleRenderType;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EpicACGRenderType {
    public static final ResourceLocation ChildSkillnoSelected = GetTextures("gui/noselected");
    public static final ResourceLocation ChildSkillSelected = GetTextures("gui/selected");

    public static final ResourceLocation GS_BOW_SHOOT_PARTICLE_TEX = GetTextures("particle/genshin_bow");
    public static final ResourceLocation GS_BOW_LANDONG_PARTICLE_TEX = GetTextures("particle/genshin_bow_landing");
    public static final ResourceLocation GS_BOW_LANDONG_PARTICLE_TEX3 = GetTextures("particle/genshin_bow_landing3");

    public static final ResourceLocation BLACK_KNIGHT_PARTICLE_TEX = GetTextures("particle/blackknight_hit");
    public static final ResourceLocation BLOOD_THIRSTY_PARTICLE_TEX = GetTextures("particle/bloodthirsty_hit");

    public static final BloomParticleRenderType SAO_DEATH_PARTICLE = new BloomParticleRenderType(
            new ResourceLocation(EpicACG.MODID, "sao_death"),
            GetTextures("particle/sao_death")
    );
            //new EpicACGQuadParticleRenderType("textures/particle/sao_death", "SAO_DEATH");
    public static final BloomParticleRenderType GENSHIN_BOW_PARTICLE = new BloomParticleRenderType(
            new ResourceLocation(EpicACG.MODID, "genshin_bow"), GS_BOW_SHOOT_PARTICLE_TEX
    );

    public static final BloomParticleRenderType GENSHIN_BOW_LANDING = new BloomParticleRenderType(
            new ResourceLocation(EpicACG.MODID, "genshin_bow_landing"), GS_BOW_LANDONG_PARTICLE_TEX
    );

    public static final BloomParticleRenderType GENSHIN_BOW_LANDING2 = new BloomParticleRenderType(
            new ResourceLocation(EpicACG.MODID, "genshin_bow_landing2"), GS_BOW_LANDONG_PARTICLE_TEX3
    );

    public static final BloomParticleRenderType BLOOM_TRAIL = new BloomParticleRenderType(
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

            if(Texture != null) RenderUtils.GLSetTexture(Texture);

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
