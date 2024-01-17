package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.client.postpasses.Blit;
import com.dfdyz.epicacg.client.postpasses.Blur;
import com.dfdyz.epicacg.client.postpasses.CompositeEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.event.RegisterShadersEvent;

import java.io.IOException;

public class PostEffects {

    public static Blit blit;
    public static CompositeEffect composite;
    public static Blur blur;
    //public static PostEffectBase composite;

    public static void register(RegisterShadersEvent event){
        try {
            System.out.println("Load Shader");
            ResourceManager rm = Minecraft.getInstance().getResourceManager();
            composite = new CompositeEffect(rm);
            blit = new Blit(rm);
            blur = new Blur(rm);
            //composite = new PostEffectBase(new EffectInstance(rm, "epicacg:composite"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
