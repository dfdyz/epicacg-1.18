package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.client.shaderpasses.*;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.event.RegisterShadersEvent;

import java.io.IOException;

public class PostEffects {

    public static PostEffectBase blit;
    public static PostEffectBase composite;
    public static SpaceBroken space_broken;
    public static DepthCull depth_cull;

    public static DownSampling downSampler;
    public static UpSampling upSampler;

    public static UnityCompsite unity_composite;

    public static void register(RegisterShadersEvent event){
        try {
            System.out.println("Load Shader");
            ResourceManager rm = Minecraft.getInstance().getResourceManager();
            composite = new PostEffectBase("epicacg:composite", rm);
            blit = new PostEffectBase("epicacg:blit",rm);
            downSampler = new DownSampling("epicacg:down_sampling",rm);
            upSampler = new UpSampling("epicacg:up_sampling",rm);
            unity_composite = new UnityCompsite("epicacg:unity_composite",rm);
            space_broken = new SpaceBroken("epicacg:space_broken",rm);
            depth_cull = new DepthCull("epicacg:depth_cull", rm);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
