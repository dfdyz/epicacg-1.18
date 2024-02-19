package com.dfdyz.epicacg.efmextra.anims;

import com.dfdyz.epicacg.efmextra.anims.property.MyProperties;
import com.dfdyz.epicacg.utils.Function.ScanAttackConsumer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;
import java.util.Locale;

public class ScanAttackAnimation extends AttackAnimation{
    protected ScanAttackConsumer attackConsumer = (animation, entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase) -> {};

    public ScanAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, preDelay, contact, recovery, collider, colliderJoint, path, armature);
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);
    }

    public ScanAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, path, armature);
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);
    }

    public ScanAttackAnimation(float convertTime, String path, Armature armature, Phase... phases) {
        super(convertTime, path, armature, phases);
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);
    }

    public ScanAttackAnimation(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @javax.annotation.Nullable Collider collider, Joint scanner, String path, Armature model) {
        super(convertTime, path, model,
                new Phase(0.0F, antic, contact, recovery, Float.MAX_VALUE, hand, scanner, collider));
        //Hjoint = shoot;
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true);
        //this.Aid = aid;
    }

    public ScanAttackAnimation setAttackConsumer(ScanAttackConsumer consumer){
        attackConsumer = consumer;
        return this;
    }

    @Override
    public void modifyPose(DynamicAnimation animation, Pose pose, LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
            if (this.getProperty(AnimationProperty.ActionAnimationProperty.COORD).isEmpty()) {
                JointTransform jt = pose.getOrDefaultTransform("Root");
                Vec3f jointPosition = jt.translation();
                OpenMatrix4f toRootTransformApplied = entitypatch.getArmature().searchJointByName("Root").getLocalTrasnform().removeTranslation();
                OpenMatrix4f toOrigin = OpenMatrix4f.invert(toRootTransformApplied, null);
                Vec3f worldPosition = OpenMatrix4f.transform3v(toRootTransformApplied, jointPosition, null);

                if(this.getProperty(MyProperties.MOVE_ROOT_PHASE).isPresent() && this.getProperty(MyProperties.MOVE_ROOT_PHASE).get().isInPhase(time)){

                }
                else {
                    worldPosition.x = 0.0F;
                    worldPosition.y = this.getProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL).orElse(false) && worldPosition.y > 0.0F ? 0.0F : worldPosition.y;
                    worldPosition.z = 0.0F;
                }


                OpenMatrix4f.transform3v(toOrigin, worldPosition, worldPosition);
                jointPosition.x = worldPosition.x;
                jointPosition.y = worldPosition.y;
                jointPosition.z = worldPosition.z;

                if(this.getProperty(MyProperties.INVISIBLE_PHASE).isPresent() && this.getProperty(MyProperties.INVISIBLE_PHASE).get().isInPhase(time)){
                    jt.scale().set(0,0,0);
                }
            }


        AnimationProperty.PoseModifier modifier = this.getProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER).orElse(null);
        if (modifier != null) {
            modifier.modify(animation, pose, entitypatch, time, partialTicks);
        }
    }

    //private final int Aid;
    //public final String Hjoint;

    /*
    protected final int maxStrikes;
    protected final boolean moveRootY;
    protected final boolean shouldMove;

    public ScanAttackAnimation(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint scanner, String path, Armature model) {
        super(convertTime, path, model,
                new Phase(0.0F, antic, contact, recovery, Float.MAX_VALUE, hand, scanner, collider));

        //Hjoint = shoot;
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true);
        maxStrikes = 1;
        moveRootY = false;
        shouldMove = true;
        //this.Aid = aid;
    }

    public ScanAttackAnimation(float convertTime, float antic, float contact, float recovery, InteractionHand hand, boolean MoveCancel, int maxStrikes, @Nullable Collider collider, Joint scanner, String path, Armature model) {
        super(convertTime, path, model,
                new Phase(0.0F, antic, contact, recovery, Float.MAX_VALUE, hand, scanner, collider));

        //Hjoint = shoot;
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, MoveCancel);
        this.maxStrikes = maxStrikes;
        moveRootY = true;
        shouldMove = true;

        //this.Aid = aid;
    }

    public ScanAttackAnimation(float convertTime, float antic,float contact, float recovery, InteractionHand hand, int maxStrikes, @Nullable Collider collider, Joint scanner, String path, Armature model) {
        super(convertTime, path, model,
                new Phase(0.0F, antic, contact, recovery, Float.MAX_VALUE, hand, scanner, collider));

        //Hjoint = shoot;
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.maxStrikes = maxStrikes;
        this.shouldMove = false;
        moveRootY = true;
        //this.Aid = aid;
    }

     */

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.properties.containsKey(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float basisSpeed = Float.parseFloat(String.format(Locale.US, "%.2f", (1.0F / this.totalTime)));
            this.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, basisSpeed);
        }
    }

    @Override
    protected Vec3 getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        //entitypatch.getOriginal().setDeltaMovement(0,0,0);
        /*
        if (!shouldMove){
            return Vec3.ZERO;
        }*/
        Vec3 vec3 = super.getCoordVector(entitypatch, dynamicAnimation);

        float t = entitypatch.getAnimator().getPlayerFor(dynamicAnimation).getElapsedTime();
        if(this.getProperty(MyProperties.MOVE_ROOT_PHASE).isPresent() &&
                this.getProperty(MyProperties.MOVE_ROOT_PHASE).get().isInPhase(t)){
            vec3 = vec3.multiply(0,0,0);
        }

        return vec3;
    }

    @Override
    public void attackTick(LivingEntityPatch<?> entitypatch) {
        AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this);
        float elapsedTime = player.getElapsedTime();
        float prevElapsedTime = player.getPrevElapsedTime();
        EntityState state = this.getState(entitypatch, elapsedTime);
        EntityState prevState = this.getState(entitypatch, prevElapsedTime);
        Phase phase = this.getPhaseByTime(elapsedTime);
        if (state.getLevel() == 1 && !state.turningLocked() && entitypatch instanceof MobPatch<?> mobpatch) {
            mobpatch.getOriginal().getNavigation().stop();
            entitypatch.getOriginal().attackAnim = 2.0F;
            LivingEntity target = entitypatch.getTarget();
            if (target != null) {
                entitypatch.rotateTo(target, entitypatch.getYRotLimit(), false);
            }
        }

        if (prevState.attacking() || state.attacking() || prevState.getLevel() < 2 && state.getLevel() > 2) {
            if (!prevState.attacking()) {
                //entitypatch.playSound(this.getSwingSound(entitypatch, phase), 0.0F, 0.0F);
                //attackConsumer.consume(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
                entitypatch.removeHurtEntities();
            }

            //this.hurtCollidingEntities(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
            ScanTarget(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
        }

        /*
        AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this);
        float elapsedTime = player.getElapsedTime();
        float prevElapsedTime = player.getPrevElapsedTime();
        EntityState state = this.getState(entitypatch,elapsedTime);
        EntityState prevState = this.getState(entitypatch,prevElapsedTime);
        Phase phase = this.getPhaseByTime(elapsedTime);

        if (state.getLevel() == 1 && !state.turningLocked()) {
            if (entitypatch instanceof MobPatch) {
                ((Mob)entitypatch.getOriginal()).getNavigation().stop();
                entitypatch.getOriginal().attackAnim = 2;
                LivingEntity target = entitypatch.getTarget();

                if (target != null) {
                    entitypatch.rotateTo(target, entitypatch.getYRotLimit(), false);
                }
            }
        } else if (prevState.attacking() || state.attacking() || (prevState.getLevel() < 2 && state.getLevel() > 2)) {
            if (!prevState.attacking()) {
                //entitypatch.playSound(this.getSwingSound(entitypatch, phase), 0.0F, 0.0F);
                entitypatch.getCurrenltyAttackedEntities().clear();
            }

            //EpicAddon.LOGGER.info(String.valueOf(prevElapsedTime));
            this.ScanTarget(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
        }*/
    }

    public void ScanTarget(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, Phase phase){
        LivingEntity entity = entitypatch.getOriginal();
        entitypatch.getArmature().initializeTransform();
        float poseTime = state.attacking() ? elapsedTime : phase.contact;
        List<Entity> list = phase.getCollidingEntities(entitypatch, this, prevElapsedTime, poseTime, this.getPlaySpeed(entitypatch));

        if (list.size() > 0) {
            HitEntityList hitEntities = new HitEntityList(entitypatch, list, HitEntityList.Priority.DISTANCE);
            //int maxStrikes = 1;
            entitypatch.getOriginal().setLastHurtMob(list.get(0));

            while (entitypatch.getCurrenltyAttackedEntities().size() < getMaxStrikes(entitypatch, phase) && hitEntities.next()) {
                Entity e = hitEntities.getEntity();
                if(!e.isAlive()) continue;

                LivingEntity trueEntity = this.getTrueEntity(e);
                if (!entitypatch.isTeammate(e) && trueEntity != null) {
                    if (e instanceof LivingEntity || e instanceof PartEntity) {
                        if (entity.hasLineOfSight(e)) {
                            entitypatch.getCurrenltyAttackedEntities().add(trueEntity);
                        }
                    }
                }
            }
        }
        if(!entitypatch.getCurrenltyAttackedEntities().contains(entitypatch.getOriginal())){
            entitypatch.getCurrenltyAttackedEntities().add(entitypatch.getOriginal());
        }
    }

    /*
    public void ShootProjectile(LivingEntityPatch<?> entitypatch, float time, String joint){

        if(entitypatch.currentlyAttackedEntity.size() > 0){
            Entity target = entitypatch.currentlyAttackedEntity.get(0);
            Level worldIn = entitypatch.getOriginal().getLevel();
            if(target.equals(entitypatch.getOriginal())){
                float ang = (float) ((entitypatch.getOriginal().getViewYRot(1)+90)/180 * Math.PI);
                Vec3 shootVec = new Vec3(Math.cos(ang), 0 , Math.sin(ang));
                Vec3 shootPos = entitypatch.getOriginal().position().add((shootVec).normalize());

                Projectile projectile = new GenShinArrow(worldIn, entitypatch.getOriginal());
                projectile.setPos(shootPos.x, entitypatch.getOriginal().getEyeHeight(), shootPos.z);
                ((Arrow) projectile).pickup = AbstractArrow.Pickup.DISALLOWED;
                projectile.shoot(shootVec.x(), 0.1f, shootVec.z(), 4.0f, 1.0f);
                worldIn.addFreshEntity(projectile);
            }
            else {
                Vec3 shootPos = entitypatch.getOriginal().position();
                shootPos = new Vec3(shootPos.x, entitypatch.getOriginal().getEyeY() ,shootPos.z);
                Vec3 shootTarget = target.position();
                shootTarget = new Vec3(shootTarget.x,target.getEyeY(),shootTarget.z);
                Vec3 shootVec = shootTarget.subtract(shootPos);
                shootPos = shootPos.add((new Vec3(shootVec.x,0,shootVec.z)).normalize());

                Projectile projectile = new GenShinArrow(worldIn, entitypatch.getOriginal());
                projectile.setPos(shootPos);
                ((Arrow) projectile).pickup = AbstractArrow.Pickup.DISALLOWED;
                //((Arrow) projectile).setPierceLevel((byte) 2);
                projectile.shoot(shootVec.x(), shootVec.y(), shootVec.z(), 3.0f, 1.0f);
                worldIn.addFreshEntity(projectile);
            }

            if(worldIn instanceof ServerLevel){
                Vec3 vec3 = getPosByTick(entitypatch,0.4f,"Tool_L");
                ((ServerLevel)worldIn).sendParticles(RegParticle.GENSHIN_BOW.get() ,vec3.x,vec3.y,vec3.z,0,1D,1D,0.9019607D,1D);
            }
            //entitypatch.playSound(EpicAddonSounds.GENSHIN_BOW, 0.0F, 0.0F);
            //entitypatch.currentlyAttackedEntity.clear();
        //}
    }*/

    @Override
    public void hurtCollidingEntities(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, Phase phase) {

    }

    @Override
    public boolean isBasicAttackAnimation() {
        return true;
    }

}
