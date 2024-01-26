package com.dfdyz.epicacg.client.shaderpasses;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

public class Blit extends PostEffectBase{
    public Blit(ResourceManager rsmgr) throws IOException {
        super(new EffectInstance(rsmgr, "epicacg:blit"));
    }

}
