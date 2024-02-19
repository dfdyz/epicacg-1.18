package com.dfdyz.epicacg.client.render.custom;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType;
import com.dfdyz.epicacg.client.render.targets.ScaledTarget;
import com.dfdyz.epicacg.registry.PostEffects;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static net.minecraft.client.Minecraft.ON_OSX;

public class BloomParticleRenderType extends PostParticleRenderType {
    public BloomParticleRenderType(ResourceLocation renderTypeID, ResourceLocation tex) {
        super(renderTypeID, tex);
    }

    @Override
    public PostEffectPipelines.Pipeline getPipeline() {
        return ppl;
    }

    static final PostEffectPipelines.Pipeline ppl = new Pipeline(new ResourceLocation(EpicACG.MODID, "bloom_particle"));

    public static class Pipeline extends PostEffectPipelines.Pipeline{
        public Pipeline(ResourceLocation name) {
            super(name);
        }

        void handlePasses(RenderTarget src){
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL12.GL_LINEAR);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL12.GL_LINEAR);

            PostEffects.downSampler.process(src, blur[0]);    //2
            PostEffects.downSampler.process(blur[0], blur[1]);//4
            PostEffects.downSampler.process(blur[1], blur[2]);//8
            PostEffects.downSampler.process(blur[2], blur[3]);//16
            PostEffects.downSampler.process(blur[3], blur[4]);//32
            //PostEffects.downSampler.process(blur[4], blur[5]);//64

            //PostEffects.upSampler.process(blur[5], blur_[4], blur[4]);   // 64 -> 32_
            PostEffects.upSampler.process(blur[4], blur_[3], blur[3]);  // 32 -> 16_
            PostEffects.upSampler.process(blur_[3], blur_[2], blur[2]);   // 16 -> 8_
            PostEffects.upSampler.process(blur_[2], blur_[1], blur[1]);  // 8_ -> 4_
            PostEffects.upSampler.process(blur_[1], blur_[0], blur[0]);  // 4_ -> 2_

            PostEffects.unity_composite.process(blur_[0], temp, src, Minecraft.getInstance().getMainRenderTarget());

            PostEffects.blit.process(temp, Minecraft.getInstance().getMainRenderTarget());
        }

        //private static ResourceLocation bloom_particle_target = new ResourceLocation(EpicACG.MODID, "bloom_particle_target");
        //private static ResourceLocation bloom_particle_blur = new ResourceLocation(EpicACG.MODID, "bloom_particle_blur");
        //private static ResourceLocation bloom_particle_temp = new ResourceLocation(EpicACG.MODID, "bloom_particle_temp");

        RenderTarget[] blur;
        RenderTarget[] blur_;
        RenderTarget temp;

        void initTargets(){
            int cnt = 5;

            if(blur == null){
                blur = new RenderTarget[cnt];
                float s = 1.f;
                for (int i = 0; i < blur.length; i++) {
                    s /= 2;
                    blur[i] = new ScaledTarget(s, s, bufferTarget.width, bufferTarget.height, false, ON_OSX);
                    blur[i].setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                    blur[i].clear(ON_OSX);
                    if(bufferTarget.isStencilEnabled()) blur[i].enableStencil();
                }
            }

            if(blur_ == null){
                blur_ = new RenderTarget[cnt-1];
                float s = 1.f;
                for (int i = 0; i < blur_.length; i++) {
                    s /= 2;
                    blur_[i] = new ScaledTarget(s, s, bufferTarget.width, bufferTarget.height, false, ON_OSX);
                    blur_[i].setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                    blur_[i].clear(ON_OSX);
                    if(bufferTarget.isStencilEnabled()) blur[i].enableStencil();
                }
            }

            if(temp == null){
                temp = createTempTarget(bufferTarget);
            }

            if(temp.width != bufferTarget.width || temp.height != bufferTarget.height){
                for (int i = 0; i < blur.length; i++) {
                    blur[i].resize(bufferTarget.width, bufferTarget.height, ON_OSX);
                }

                for (int i = 0; i < blur_.length; i++) {
                    blur_[i].resize(bufferTarget.width, bufferTarget.height, ON_OSX);
                }
                temp.resize(bufferTarget.width, bufferTarget.height, ON_OSX);
            }
        }

        @Override
        public void PostEffectHandler() {
            initTargets();
            handlePasses(bufferTarget);
            //oldHandel(target);
        }
    }

    private static int NumMul(int a, float b){
        return (int)(a * Math.max(Math.min(b, 1.5f), 0.8f));
    }

}
