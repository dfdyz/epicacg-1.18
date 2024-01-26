package com.dfdyz.epicacg.client.render.custom;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.render.pipeline.PostParticlePipelines;
import com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType;
import com.dfdyz.epicacg.client.render.pipeline.TargetManager;
import com.dfdyz.epicacg.registry.PostEffects;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class BloomParticleRenderType extends PostParticleRenderType {
    public BloomParticleRenderType(ResourceLocation renderTypeID, ResourceLocation tex) {
        super(renderTypeID, tex);
    }

    @Override
    public PostParticlePipelines.Pipeline getPipeline() {
        return ppl;
    }

    static final PostParticlePipelines.Pipeline ppl = new Pipeline(new ResourceLocation(EpicACG.MODID, "bloom_particle"));

    public static class Pipeline extends PostParticlePipelines.Pipeline{
        public Pipeline(ResourceLocation name) {
            super(name);
        }

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


        private static ResourceLocation bloom_particle_target = new ResourceLocation(EpicACG.MODID, "bloom_particle_target");
        private static ResourceLocation bloom_particle_blur = new ResourceLocation(EpicACG.MODID, "bloom_particle_blur");
        private static ResourceLocation bloom_particle_temp = new ResourceLocation(EpicACG.MODID, "bloom_particle_temp");

        @Override
        public void PostEffectHandler() {
            RenderTarget target = TargetManager.getTarget(bloom_particle_target);

            //DepthCull(bufferTarget, Minecraft.getInstance().getMainRenderTarget(), target);
            //Blit(target, bufferTarget);

            //PostEffects.blit.process(sourceTarget, target);
            //1

            PostEffects.composite.process(bufferTarget, target,
                    (effect) ->
                    {
                        effect.setSampler("DiffuseSampler2",
                                Minecraft.getInstance().getMainRenderTarget()::getColorTextureId);
                    });

            RenderTarget blur = TargetManager.getTarget(bloom_particle_blur);
            RenderTarget temp = TargetManager.getTarget(bloom_particle_temp);


            //blurPass(sourceTarget, blur4_1, 4, new float[]{ 0, 2f}, 0.7f, 0f);
            //blurPass(sourceTarget, blur4_2, 2, new float[]{ 1.5, 2f}, 0.7f, 0f);
            //blurPass(sourceTarget, target, 16, new float[]{ 1, 1, -1, 1 });
            blurPass(bufferTarget, blur, temp, target, 32, new float[]{ 1, 2f, 2f, -1 }, 1.2f, 1.2f);
            blurPass(bufferTarget, blur, temp, target, 32, new float[]{ 0, 2f, 2f, 0 }, 1.1f, 0.7f);
            blurPass(bufferTarget, blur, temp, target, 16, new float[]{ 1.7f, 0.7f, 0.7f, -1.7f }, 1f, 0.5f);
            blurPass(bufferTarget, blur, temp, target, 8, new float[]{ 0.5f, 1.5f, 1.5f, -0.5f }, 1f, 0.2f);
            //blurPass(sourceTarget, blur, temp, target, 2, new float[]{ 1f, 1f, -1f, 1f }, 1f, 0.5f);
            //blurPass(sourceTarget, blur, temp, target, 2, new float[]{ 0, 2f, 2f, 0 }, 0.6f, 0.3f);

            //blurPass(sourceTarget, target, 4, new float[]{ 1.2f, 1.2f, -1.2f, 1.2f }, 0.9f, 0.1f);
            //blurPass(sourceTarget, target, 4, new float[]{ 0, 1.7f, 1.7f, 0 }, 0.9f, 0.1f);
            //blurPass(sourceTarget, target, 4, new float[]{ 0, 1.2f, 1.2f, 0 }, 0.9f, 0.1f);

            //blurPass(sourceTarget, target, 2, new float[]{ 1, 1, -1, 1 });

            Blit(target, Minecraft.getInstance().getMainRenderTarget());
            TargetManager.ReleaseTarget(bloom_particle_target);
            TargetManager.ReleaseTarget(bloom_particle_temp);
            TargetManager.ReleaseTarget(bloom_particle_blur);
        }
    };

}
