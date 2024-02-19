package com.dfdyz.epicacg.client.particle.SAO;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.api.client.model.ItemSkin;
import yesman.epicfight.api.client.model.ItemSkins;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Optional;

public class KiritoWingParticle extends Particle {
    private final Joint joint;
    private final TrailInfo trailInfo;
    private final StaticAnimation animation;
    private final LivingEntityPatch<?> entitypatch;
    private boolean animationEnd = false;

    private static RenderUtils.Quad quad1 = new RenderUtils.Quad(1.2f,1.2f)
                    .rotate(Vec3f.X_AXIS, -90)
                    .move(-0.58f, 0, 0)
                    .rotate(Vec3f.Y_AXIS, 30)
                    .move(0,0.2f,0.2f);
    private static RenderUtils.Quad quad2 = new RenderUtils.Quad(1.2f,1.2f)
            .rotate(Vec3f.X_AXIS, -90)
            .move(-0.58f, 0, 0)
            .rotate(Vec3f.Y_AXIS, 150)
            .move(0,0.2f,0.2f);


    private static RenderUtils.Quad quad1_ = new RenderUtils.Quad(1.2f,1.2f)
            .rotate(Vec3f.X_AXIS, -90)
            .move(-0.58f, 0, 0)
            .rotate(Vec3f.Y_AXIS, 20)
            .rotate(Vec3f.Z_AXIS, 40)
            .move(0,0.2f,0.2f);
    private static RenderUtils.Quad quad2_ = new RenderUtils.Quad(1.2f,1.2f)
            .rotate(Vec3f.X_AXIS, -90)
            .move(-0.58f, 0, 0)
            .rotate(Vec3f.Y_AXIS, 160)
            .rotate(Vec3f.Z_AXIS, -40)
            .move(0,0.2f,0.2f);

    public KiritoWingParticle(ClientLevel level, LivingEntityPatch<?> entitypatch, Joint joint, StaticAnimation animation, TrailInfo trailInfo) {
        super(level, 0,0,0);

        this.joint = joint;
        this.entitypatch = entitypatch;
        this.animation = animation;
        this.hasPhysics = false;
        this.trailInfo = trailInfo;

        this.lifetime = trailInfo.trailLifetime;

        this.rCol = 1;
        this.gCol = 1;
        this.bCol = 1;
        this.alpha = 1;
        oAlpha = alpha;

        if(this.entitypatch.getOriginal().isAlive()){
            Vec3 pos = entitypatch.getOriginal().position();
            this.setPos(pos.x, pos.y, pos.z);
        }
    }


    float oAlpha;

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        AnimationPlayer animPlayer = this.entitypatch.getAnimator().getPlayerFor(this.animation);

        if (this.animationEnd) {
            oAlpha = alpha;
            alpha -= 1.0f / trailInfo.trailLifetime;
            if (this.lifetime-- == 0) {
                this.remove();
            }
        } else {
            if (!this.entitypatch.getOriginal().isAlive()
                    || this.animation != animPlayer.getAnimation().getRealAnimation()
                    || animPlayer.getElapsedTime() > this.trailInfo.endTime) {
                this.animationEnd = true;
                this.lifetime = this.trailInfo.trailLifetime;
            }
        }

        if(this.entitypatch.getOriginal().isAlive()){
            Vec3 pos = entitypatch.getOriginal().position();
            this.setPos(pos.x, pos.y, pos.z);
        }
    }

    private float getAlpha(float pt){
        return Mth.lerp(pt,oAlpha, alpha);
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float pt) {
        if(!PostEffectPipelines.isActive() || !this.entitypatch.getOriginal().isAlive()) return;
        EpicACGRenderType.getBloomRenderTypeByTexture(trailInfo.texturePath).callPipeline();
        AnimationPlayer animPlayer = this.entitypatch.getAnimator().getPlayerFor(this.animation);
        float elapsedTime = Mth.lerp(pt, animPlayer.getPrevElapsedTime(), animPlayer.getElapsedTime());

        //System.out.println("sssss");

        if(animationEnd && elapsedTime < trailInfo.startTime){
            return;
        }
        //RenderUtils.GLSetTexture(trailInfo.texturePath);
        LivingEntity le =  entitypatch.getOriginal();
        Pose pose = this.entitypatch.getArmature().getPose(pt);
        Vec3 pos = le.getPosition(pt);
        OpenMatrix4f modelTf = OpenMatrix4f.createTranslation((float)pos.x, (float)pos.y, (float)pos.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(this.entitypatch.getModelMatrix(pt)));
        OpenMatrix4f tf = this.entitypatch.getArmature().getBindedTransformFor(pose, this.joint).mulFront(modelTf);

        int light = 15728880;
        if(le.getPosition(1).subtract(le.getPosition(0)).y() > 0.5){
            quad1_.PushVertex(vertexConsumer, Vec3f.fromDoubleVector(camera.getPosition()),tf, rCol, gCol, bCol, getAlpha(pt), light);
            quad2_.PushVertex(vertexConsumer, Vec3f.fromDoubleVector(camera.getPosition()),tf, rCol, gCol, bCol, getAlpha(pt), light);

        }
        else {
            quad1.PushVertex(vertexConsumer, Vec3f.fromDoubleVector(camera.getPosition()),tf, rCol, gCol, bCol, getAlpha(pt), light);
            quad2.PushVertex(vertexConsumer, Vec3f.fromDoubleVector(camera.getPosition()),tf, rCol, gCol, bCol, getAlpha(pt), light);
        }
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return EpicACGRenderType.getBloomRenderTypeByTexture(trailInfo.texturePath);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        public Provider(SpriteSet spriteSet) {
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            int eid = (int)Double.doubleToRawLongBits(x);
            int modid = (int)Double.doubleToRawLongBits(y);
            int animid = (int)Double.doubleToRawLongBits(z);
            int jointId = (int)Double.doubleToRawLongBits(xSpeed);
            int idx = (int)Double.doubleToRawLongBits(ySpeed);
            Entity entity = level.getEntity(eid);

            if (entity != null) {
                LivingEntityPatch<?> entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                StaticAnimation animation = EpicFightMod.getInstance().animationManager.findAnimationById(modid, animid);
                Optional<List<TrailInfo>> trailInfo = animation.getProperty(ClientAnimationProperties.TRAIL_EFFECT);
                TrailInfo result = trailInfo.get().get(idx);

                if (result.hand != null) {
                    ItemStack stack = entitypatch.getOriginal().getItemInHand(result.hand);
                    ItemSkin itemSkin = ItemSkins.getItemSkin(stack.getItem());

                    if (itemSkin != null) {
                        result = itemSkin.trailInfo.overwrite(result);
                    }
                }

                if (entitypatch != null && animation != null && trailInfo.isPresent()) {
                    return new KiritoWingParticle(level, entitypatch, entitypatch.getArmature().searchJointById(jointId), animation, result);
                }
            }

            return null;
        }
    }
}
