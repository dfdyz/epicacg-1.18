package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.client.shaderpasses.Blit;
import com.dfdyz.epicacg.client.shaderpasses.Blur;
import com.dfdyz.epicacg.client.shaderpasses.CompositeEffect;
import com.dfdyz.epicacg.client.shaderpasses.PostEffectBase;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.event.RegisterShadersEvent;

import java.io.IOException;

public class PostEffects {

    public static PostEffectBase blit;
    public static PostEffectBase composite;
    public static PostEffectBase blur;
    public static PostEffectBase depth_cull;

    public static void register(RegisterShadersEvent event){
        try {
            System.out.println("Load Shader");
            ResourceManager rm = Minecraft.getInstance().getResourceManager();
            composite = new PostEffectBase("epicacg:composite", rm);
            blit = new PostEffectBase("epicacg:blit",rm);
            blur = new PostEffectBase("epicacg:blur",rm);
            depth_cull = new PostEffectBase("epicacg:depth_cull", rm);
            //composite = new PostEffectBase(new EffectInstance(rm, "epicacg:composite"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
