package com.dfdyz.epicacg.client.render.pipeline;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.Queue;

import static com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType.createTempTarget;
import static net.minecraft.client.Minecraft.ON_OSX;

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
        TargetManager.ReleaseAll();
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
        protected RenderTarget bufferTarget;
        protected final ResourceLocation name;

        public Pipeline(ResourceLocation name){
            this.name = name;
        }

        public void start(){
            if(started){
                if(Active){
                    //ClientCommands.Debug();
                    bufferTarget.copyDepthFrom(getSource());
                }
                bufferTarget.bindWrite(false);
            }
            else {
                if(bufferTarget == null){
                    bufferTarget = TargetManager.getTarget(name);
                    bufferTarget.clear(ON_OSX);
                }

                RenderTarget main = getSource();
                if(Active){
                    //System.out.println("push")
                    bufferTarget.copyDepthFrom(main);
                }
                //System.out.println("push");
                PostEffectQueue.add(this);
                bufferTarget.bindWrite(false);
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
                bufferTarget.unbindWrite();
                bufferTarget.unbindRead();
                RenderTarget rt = getSource();
                rt.copyDepthFrom(bufferTarget);
                rt.bindWrite(false);
            }
            else {
                bufferTarget.clear(Minecraft.ON_OSX);
                getSource().bindWrite(false);
            }

            //ClientCommands.Debug();
        }
        public abstract void PostEffectHandler();
        public final void HandlePostEffect(){
            if(called) PostEffectHandler();
            bufferTarget = null;
            started = false;
            called = false;
        }
    }


}
