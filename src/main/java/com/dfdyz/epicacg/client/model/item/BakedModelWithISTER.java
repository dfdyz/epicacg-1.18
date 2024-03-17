package com.dfdyz.epicacg.client.model.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class BakedModelWithISTER implements BakedModel {
    private final BakedModel parent;

    public BakedModelWithISTER(BakedModel parent){
        this.parent = parent;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, Random random) {
        return parent.getQuads(state, direction, random);
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData) {
        return parent.getQuads(state, side, rand, extraData);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return parent.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return parent.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return parent.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return parent.getParticleIcon();
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@NotNull IModelData data) {
        return parent.getParticleIcon(data);
    }

    @Override
    public ItemOverrides getOverrides() {
        return parent.getOverrides();
    }

    @Override
    public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack) {
        parent.handlePerspective(cameraTransformType, poseStack);
        return this;
    }

    @Override
    public boolean doesHandlePerspectives() {
        return false;
    }
}
