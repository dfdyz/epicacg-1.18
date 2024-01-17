package com.dfdyz.epicacg.client.render.pipeline;

import com.dfdyz.epicacg.command.ClientCommands;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;

import java.util.Queue;

import static com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType.createTempTarget;

public class PostParticlePipelines {
    //public static final HashMap<ResourceLocation, RenderTarget> TempTargets = Maps.newHashMap();
    public static RenderTarget rtRecord;
    public static final Queue<Pipeline> PostEffectQueue = Queues.newConcurrentLinkedQueue();

    public static void RenderPost(){

        if(!PostEffectQueue.isEmpty()){
            RenderTarget depth = createTempTarget(Minecraft.getInstance().getMainRenderTarget());
            depth.copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
            depth.unbindWrite();
            //
            //RenderSystem.disableDepthTest();
            Pipeline renderType;

            while (!PostEffectQueue.isEmpty()){
                renderType = PostEffectQueue.poll();
                renderType.HandlePostEffect();
            }
            //RenderSystem.enableDepthTest();
            //RenderSystem.enableDepthTest();
            Minecraft.getInstance().getMainRenderTarget().copyDepthFrom(depth);
            depth.destroyBuffers();
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
            //target = null;
        }

        close();
    }

    protected static boolean Active = false;

    public static boolean isActive(){ return Active; }
    public static void active(){
        //System.out.println("frame");
        Active = true;
    }

    public static void close(){
        Active = false;
    }

    public static RenderTarget getSource(){
        if(Minecraft.getInstance().levelRenderer.transparencyChain == null){
            return Minecraft.getInstance().getMainRenderTarget();
        }
        else {
            return Minecraft.getInstance().levelRenderer.getParticlesTarget();
        }
    }

    public static abstract class Pipeline{
        protected boolean called = false;
        protected boolean started = false;
        protected RenderTarget sourceTarget;

        public void start(){
            if(started){
                if(Active){
                    //ClientCommands.Debug();
                    sourceTarget.copyDepthFrom(getSource());
                }
                sourceTarget.bindWrite(false);
            }
            else {
                if(sourceTarget != null){
                    sourceTarget.destroyBuffers();
                }

                RenderTarget main = getSource();
                sourceTarget = createTempTarget(main);
                if(Active){
                    //System.out.println("push")
                    sourceTarget.copyDepthFrom(main);
                }
                //System.out.println("push");
                PostEffectQueue.add(this);
                sourceTarget.bindWrite(false);
                started = true;
            }
        }

        public void call(){
            if(Active) {
                //ClientCommands.Debug();
                called = true;
            }
        }

        public void suspend(){
            if(Active){
                //System.out.println("aaaaa");
                sourceTarget.unbindWrite();
                sourceTarget.unbindRead();
                RenderTarget rt = getSource();
                rt.copyDepthFrom(sourceTarget);
                rt.bindWrite(false);
            }
            else {
                sourceTarget.clear(Minecraft.ON_OSX);
                getSource().bindWrite(false);
            }

            //ClientCommands.Debug();
        }
        public abstract void PostEffectHandler();
        public final void HandlePostEffect(){
            if(called) PostEffectHandler();
            sourceTarget.destroyBuffers();
            sourceTarget = null;
            started = false;
            called = false;
        }
    }


}
