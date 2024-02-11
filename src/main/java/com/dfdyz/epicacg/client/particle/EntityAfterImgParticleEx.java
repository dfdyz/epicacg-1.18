package com.dfdyz.epicacg.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.particle.CustomModelParticle;
import yesman.epicfight.client.particle.EntityAfterImageParticle;
import yesman.epicfight.client.particle.EpicFightParticleRenderTypes;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.awt.*;

public class EntityAfterImgParticleEx extends CustomModelParticle<AnimatedMesh> {
    private OpenMatrix4f[] poseMatrices;
    private Matrix4f modelMatrix;

    public EntityAfterImgParticleEx(ClientLevel level, double x, double y, double z, double xd, double yd, double zd,
                                    int lft,
                                    AnimatedMesh particleMesh,
                                    OpenMatrix4f[] matrices,
                                    Matrix4f modelMatrix) {
        super(level, x, y, z, xd, yd, zd, particleMesh);
        this.poseMatrices = matrices;
        this.modelMatrix = modelMatrix;
        this.lifetime = lft;
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.alpha = 0.3F;
    }

    public ParticleRenderType getRenderType() {
        return EpicFightParticleRenderTypes.TRANSLUCENT;
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.pitchO = this.pitch;
            this.yawO = this.yaw;
            this.oRoll = this.roll;
            this.scaleO = this.scale;
        }
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        PoseStack poseStack = new PoseStack();
        this.setupPoseStack(poseStack, camera, partialTicks);
        poseStack.mulPoseMatrix(this.modelMatrix);
        this.particleMesh.drawWithPoseNoTexture(poseStack, vertexConsumer, 0xffffffff,
                this.rCol, this.gCol, this.bCol, alpha,
                OverlayTexture.NO_OVERLAY, this.poseMatrices);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        // x: entityId      y,z: animationId(namespaceId & animId)    xSpeed: elapsedTime       ySpeed: colorInt      zSpeed: lifeTime
        public Provider(SpriteSet spriteSet) {

        }
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            Entity entity = level.getEntity((int)Double.doubleToLongBits(x));
            LivingEntityPatch<?> entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            int modid = (int)Double.doubleToRawLongBits(y);
            int animid = (int)Double.doubleToRawLongBits(z);
            StaticAnimation animation = EpicFightMod.getInstance().animationManager.findAnimationById(modid, animid);

            Color color = new Color((int)Double.doubleToRawLongBits(ySpeed), true);
            int lt = (int)Double.doubleToRawLongBits(zSpeed);

            if (entitypatch != null && ClientEngine.getInstance().renderEngine.hasRendererFor(entitypatch.getOriginal())) {
                PatchedEntityRenderer renderer = ClientEngine.getInstance().renderEngine.getEntityRenderer(entitypatch.getOriginal());
                Armature armature = entitypatch.getArmature();

                Pose modelPose = animation.getPoseByTime(entitypatch, (float) xSpeed, 0);

                PoseStack poseStack = new PoseStack();
                OpenMatrix4f[] matrices = armature.getPoseAsTransformMatrix(modelPose);
                        //renderer.getPoseMatrices(entitypatch, armature, 1.0F);
                renderer.mulPoseStack(poseStack, armature, entitypatch.getOriginal(), entitypatch, 1.0F);

                for (int i = 0; i < matrices.length; i++) {
                    matrices[i] = OpenMatrix4f.mul(matrices[i], armature.searchJointById(i).getToOrigin(), null);
                }

                AnimatedMesh mesh = renderer.getMesh(entitypatch);

                Vec3 pos = entity.getPosition(0.5f);
                EntityAfterImgParticleEx particle =
                        new EntityAfterImgParticleEx(level, pos.x() , pos.y(), pos.z(), 0, 0, 0, lt,
                                mesh, matrices, poseStack.last().pose());
                particle.setColor(color.getRed() / 255.f, color.getGreen() / 255.f, color.getBlue() / 255.f);
                particle.setAlpha(color.getAlpha() / 255.f);

                //System.out.println("particle");

                return particle;
            }

            return null;
        }
    }

}
