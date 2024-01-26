package com.dfdyz.epicacg.client.shaderpasses;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

public class CompositeEffect extends PostEffectBase{
    public CompositeEffect(ResourceManager resmgr) throws IOException {
        super(new EffectInstance(resmgr, "epicacg:simple_post_particle"));
    }


    /*
    public void process(RenderTarget passIn, //RenderTarget original,
                         RenderTarget outTarget){
        passIn.unbindWrite();
        RenderSystem.viewport(0, 0, outTarget.width, outTarget.height);

        this.effect.setSampler("DiffuseSampler", original::getColorTextureId);
        this.effect.setSampler("DiffuseSampler2", passIn::getColorTextureId);
        Matrix4f shaderOrthoMatrix = Matrix4f.orthographic(0.0F, outTarget.width, outTarget.height, 0.0F, 0.1F, 1000.0F);

        this.effect.safeGetUniform("ProjMat").set(shaderOrthoMatrix);
        this.effect.safeGetUniform("InSize").set((float) passIn.width, (float) passIn.height);
        this.effect.safeGetUniform("OutSize").set((float) outTarget.width, (float) outTarget.height);

        Minecraft minecraft = Minecraft.getInstance();
        this.effect.safeGetUniform("ScreenSize").set((float)minecraft.getWindow().getWidth(), (float)minecraft.getWindow().getHeight());
        //SetValue();
        this.effect.apply();

        outTarget.clear(Minecraft.ON_OSX);
        outTarget.bindWrite(false);
        RenderSystem.depthFunc(519);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        bufferbuilder.vertex(0.0D, 0.0D, 500.0D).endVertex();
        bufferbuilder.vertex(passIn.width, 0.0D, 500.0D).endVertex();
        bufferbuilder.vertex(passIn.width, passIn.height, 500.0D).endVertex();
        bufferbuilder.vertex(0.0D, passIn.height, 500.0D).endVertex();

        bufferbuilder.end();
        BufferUploader._endInternal(bufferbuilder);
        RenderSystem.depthFunc(515);

        this.effect.clear();
        outTarget.unbindWrite();
        passIn.unbindRead();

        //PassUtils.Blit(inTarget, outTarget);
    }*/


}
