package com.dfdyz.epicacg.client.screeneffect;

import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import net.minecraft.resources.ResourceLocation;

public abstract class ScreenEffect {
    protected int age = 0;
    public int lifetime = 20;

    public final ResourceLocation ID;

    public ScreenEffect(ResourceLocation id) {
        ID = id;
    }

    public void tick(){
        if(++age > lifetime){
        }
    }

    public boolean shouldRemoved(){
        return age > lifetime;
    }

    public void setRemove(){
        age = lifetime + 1;
    }

    public abstract PostEffectPipelines.Pipeline getPipeline();

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
