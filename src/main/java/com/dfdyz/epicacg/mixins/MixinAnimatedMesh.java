package com.dfdyz.epicacg.mixins;

import com.dfdyz.epicacg.client.render.IPatchedAnimatedMesh;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.ModelPart;
import yesman.epicfight.api.client.model.VertexIndicator;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec4f;

import java.util.Iterator;
import java.util.Map;

@Mixin(value = AnimatedMesh.class, remap = false)
public abstract class MixinAnimatedMesh implements IPatchedAnimatedMesh {
    @Shadow
    float[] weights;

    @Override
    public void drawWithPoseNoTexture2(PoseStack poseStack, VertexConsumer builder, int packedLightIn, float r, float g, float b, float a, int overlayCoord, OpenMatrix4f[] poses) {
        //patch();

        MeshAccessor meshAccessor = (MeshAccessor) this;

        Matrix4f matrix4f = poseStack.last().pose();
        Matrix3f matrix3f = poseStack.last().normal();
        OpenMatrix4f[] posesNoTranslation = new OpenMatrix4f[poses.length];

        for(int i = 0; i < poses.length; ++i) {
            posesNoTranslation[i] = poses[i].removeTranslation();
        }

        Iterator var29 = meshAccessor.getParts().values().iterator();

        while(true) {
            ModelPart part;
            do {
                if (!var29.hasNext()) {
                    return;
                }

                part = (ModelPart)var29.next();
            } while(part.hidden);

            Iterator var15 = part.getVertices().iterator();

            while(var15.hasNext()) {
                VertexIndicator.AnimatedVertexIndicator vi = (VertexIndicator.AnimatedVertexIndicator)var15.next();
                int pos = vi.position * 3;
                int norm = vi.normal * 3;
                Vec4f position = new Vec4f(meshAccessor.getPositions()[pos], meshAccessor.getPositions()[pos + 1], meshAccessor.getPositions()[pos + 2], 1.0F);
                Vec4f normal = new Vec4f(meshAccessor.getNormals()[norm], meshAccessor.getNormals()[norm + 1], meshAccessor.getNormals()[norm + 2], 1.0F);
                Vec4f totalPos = new Vec4f(0.0F, 0.0F, 0.0F, 0.0F);
                Vec4f totalNorm = new Vec4f(0.0F, 0.0F, 0.0F, 0.0F);

                for(int i = 0; i < vi.joint.size(); ++i) {
                    int jointIndex = vi.joint.get(i);
                    int weightIndex = vi.weight.get(i);
                    float weight = this.weights[weightIndex];
                    Vec4f.add(OpenMatrix4f.transform(poses[jointIndex], position, null).scale(weight), totalPos, totalPos);
                    Vec4f.add(OpenMatrix4f.transform(posesNoTranslation[jointIndex], normal, null).scale(weight), totalNorm, totalNorm);
                }
                int uv = vi.uv * 2;

                Vector4f posVec = new Vector4f(totalPos.x, totalPos.y, totalPos.z, 1.0F);
                Vector3f normVec = new Vector3f(totalNorm.x, totalNorm.y, totalNorm.z);
                posVec.transform(matrix4f);
                normVec.transform(matrix3f);
                builder.vertex(posVec.x(), posVec.y(), posVec.z());
                builder.color(r, g, b, a);
                builder.uv(meshAccessor.getUvs()[uv], meshAccessor.getUvs()[uv + 1]);
                builder.uv2(packedLightIn);
                builder.endVertex();
            }
        }
    }
}
