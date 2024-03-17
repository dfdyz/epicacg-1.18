package com.dfdyz.epicacg.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.utils.math.OpenMatrix4f;

public interface IPatchedAnimatedMesh {
    void drawWithPoseNoTexture2(PoseStack poseStack, VertexConsumer builder, int packedLightIn, float r, float g, float b, float a, int overlayCoord, OpenMatrix4f[] poses);
}
