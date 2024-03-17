package com.dfdyz.epicacg.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public abstract class MyISTER extends BlockEntityWithoutLevelRenderer {

    public MyISTER() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels());
    }

    protected EntityModelSet getEntityModels() {
        return Minecraft.getInstance().getEntityModels();
    }

    @Override
    public abstract void onResourceManagerReload(@Nonnull ResourceManager resourceManager);

    @Override
    public abstract void renderByItem(@Nonnull ItemStack stack,
                                      @Nonnull ItemTransforms.TransformType transformType,
                                      @Nonnull PoseStack matrix,
                                      @Nonnull MultiBufferSource renderer,
                                      int light, int overlayLight);

    protected final void renderByItemDefault(@Nonnull ItemStack stack,
                                       @Nonnull ItemTransforms.TransformType transformType,
                                       @Nonnull PoseStack matrix,
                                       @Nonnull MultiBufferSource renderer,
                                       int light, int overlayLight){
        super.renderByItem(stack, transformType, matrix, renderer, light, overlayLight);
    }

}
