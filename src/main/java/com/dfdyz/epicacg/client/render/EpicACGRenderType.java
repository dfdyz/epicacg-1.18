package com.dfdyz.epicacg.client.render;


import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.render.custom.BloomParticleRenderType;
import com.dfdyz.epicacg.client.render.custom.SpaceBrokenRenderType;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.google.common.collect.Maps;
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

import java.util.HashMap;

import static com.dfdyz.epicacg.utils.RenderUtils.GetTexture;

@OnlyIn(Dist.CLIENT)
public class EpicACGRenderType {
    public static final ResourceLocation ChildSkillnoSelected = GetTexture("gui/noselected");
    public static final ResourceLocation ChildSkillSelected = GetTexture("gui/selected");

    public static final ResourceLocation GS_BOW_SHOOT_PARTICLE_TEX = GetTexture("particle/genshin_bow");
    public static final ResourceLocation GS_BOW_LANDONG_PARTICLE_TEX = GetTexture("particle/genshin_bow_landing");
    public static final ResourceLocation GS_BOW_LANDONG_PARTICLE_TEX3 = GetTexture("particle/genshin_bow_landing3");

    public static final ResourceLocation BLACK_KNIGHT_PARTICLE_TEX = GetTexture("particle/blackknight_hit");
    public static final ResourceLocation BLOOD_THIRSTY_PARTICLE_TEX = GetTexture("particle/bloodthirsty_hit");

    public static final BloomParticleRenderType SAO_DEATH_PARTICLE = new BloomParticleRenderType(
            new ResourceLocation(EpicACG.MODID, "sao_death"),
            GetTexture("particle/sao_death")
    );
            //new EpicACGQuadParticleRenderType("textures/particle/sao_death", "SAO_DEATH");

    /*
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

    public static final BloomParticleRenderType BLOOM_QUAD_PARTICLE = new BloomParticleRenderType(
            new ResourceLocation(EpicACG.MODID, "bloom_quad_particle"), null
    );*/

    private static int bloomIdx = 0;
    public static final HashMap<ResourceLocation, BloomParticleRenderType> BloomRenderTypes = Maps.newHashMap();
    public static BloomParticleRenderType getBloomRenderTypeByTexture(ResourceLocation texture){
        if(BloomRenderTypes.containsKey(texture)){
            return BloomRenderTypes.get(texture);
        }
        else {
            BloomParticleRenderType bloomType = new BloomParticleRenderType(new ResourceLocation(EpicACG.MODID, "bloom_particle_" + bloomIdx++), texture);
            BloomRenderTypes.put(texture, bloomType);
            return bloomType;
        }
    }

    private static int quadIdx = 0;
    public static final HashMap<ResourceLocation, EpicACGQuadParticleRenderType> QuadRenderTypes = Maps.newHashMap();
    public static EpicACGQuadParticleRenderType getRenderTypeByTexture(ResourceLocation texture){
        if(QuadRenderTypes.containsKey(texture)){
            return QuadRenderTypes.get(texture);
        }
        else {
            EpicACGQuadParticleRenderType rdt = new EpicACGQuadParticleRenderType("epicacg:quad_particle_" + quadIdx++, texture);
            QuadRenderTypes.put(texture,rdt);
            return rdt;
        }
    }

    public static SpaceBrokenRenderType SpaceBroken1 = new SpaceBrokenRenderType(new ResourceLocation(EpicACG.MODID, "space_broken" ), 0);
    public static SpaceBrokenRenderType SpaceBroken2 = new SpaceBrokenRenderType(new ResourceLocation(EpicACG.MODID, "space_broken" ), 1);




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
