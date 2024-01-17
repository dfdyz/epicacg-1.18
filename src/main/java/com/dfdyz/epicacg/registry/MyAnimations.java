package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.camera.CamAnim;
import com.dfdyz.epicacg.efmextra.anims.*;
import com.dfdyz.epicacg.efmextra.skills.GenShinInternal.skillevents.YoimiyaSkillFunction;
import com.dfdyz.epicacg.efmextra.weapon.WeaponCollider;
import com.dfdyz.epicacg.event.CameraEvents;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionHand;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;

public class MyAnimations {
    public static List<CamAnim> CamAnimRegistry = Lists.newArrayList();
    public static StaticAnimation GS_Yoimiya_Auto1;
    public static StaticAnimation GS_Yoimiya_Auto2;
    public static StaticAnimation GS_Yoimiya_Auto3;
    public static StaticAnimation GS_Yoimiya_Auto4;
    public static StaticAnimation GS_Yoimiya_Auto5;
    public static StaticAnimation GS_Yoimiya_SA;

    public static StaticAnimation GS_Yoimiya_FallAtk_Start;
    public static StaticAnimation GS_Yoimiya_FallAtk_Last;
    public static StaticAnimation GS_Yoimiya_FallAtk_Loop;


    public static StaticAnimation SAO_SCYTHE_IDLE;
    public static StaticAnimation SAO_SCYTHE_RUN;
    public static StaticAnimation SAO_SCYTHE_WALK;

    public static StaticAnimation BATTLE_SCYTHE_AUTO1;
    public static StaticAnimation BATTLE_SCYTHE_AUTO2;
    public static StaticAnimation BATTLE_SCYTHE_AUTO3;
    public static StaticAnimation BATTLE_SCYTHE_AUTO4;
    public static StaticAnimation BATTLE_SCYTHE_AUTO5;
    public static StaticAnimation BATTLE_SCYTHE_DASH;

    public static StaticAnimation SAO_DUAL_SWORD_HOLD;
    public static StaticAnimation SAO_DUAL_SWORD_NORMAL;
    public static StaticAnimation SAO_DUAL_SWORD_RUN;
    public static StaticAnimation SAO_SINGLE_SWORD_GUARD;
    //public static StaticAnimation SAO_DUAL_SWORD_WALK;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO1;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO2;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO3;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO4;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO5;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO6;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO7;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO8;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO9;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO10;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO11;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO12;
    public static StaticAnimation SAO_DUAL_SWORD_DASH;

    public static void registerAnimations(AnimationRegistryEvent event) {
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("EpicAddon AnimLoadingEvent");
        event.getRegistryMap().put(EpicACG.MODID, MyAnimations::register);
    }

    public static void register(){
        HumanoidArmature biped = Armatures.BIPED;

        GS_Yoimiya_Auto1 = new ScanAttackAnimation(0.1F, 0,0.62F, 0.8333F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.rootJoint, "biped/gs_yoimiya_auto1", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0.4F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolL);
                        }, AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(0.585F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolL);
                        }, AnimationEvent.Side.BOTH),
                })
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(2.75f));

        GS_Yoimiya_Auto2 = new ScanAttackAnimation(0.1F, 0,0.7F, 0.98F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.rootJoint, "biped/gs_yoimiya_auto2", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0.6F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolR);
                        }, AnimationEvent.Side.BOTH),
                });

        GS_Yoimiya_Auto3 = new ScanAttackAnimation(0.1F, 0,0.88F, 1.03F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.rootJoint, "biped/gs_yoimiya_auto3", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(2.95f))
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0.84F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolL);
                        }, AnimationEvent.Side.BOTH),
                });

        GS_Yoimiya_Auto4 = new ScanAttackAnimation(0.05F, 0,2.12F, 2.733F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.rootJoint, "biped/gs_yoimiya_auto4", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(1.2083F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolL);
                        }, AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(1.7916F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolR);
                        }, AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(2.0416F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolL);
                        }, AnimationEvent.Side.BOTH),
                })
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(3.1f));

        GS_Yoimiya_Auto5 = new ScanAttackAnimation(0.02F, 0,0.2F, 1.51F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.rootJoint, "biped/gs_yoimiya_auto5", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER,MSpeed( 3.1f))
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0.7083F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep, biped.toolL);
                        }, AnimationEvent.Side.BOTH),
                });

        GS_Yoimiya_SA = new YoimiyaSAAnimation(0.02F, 0.5F, 4.56F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.rootJoint, "biped/gs_yoimiya_sa", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(2.4f))
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0f, (ep, anim, objs) -> {
                            CameraEvents.SetAnim(Yoimiya, ep.getOriginal(), true);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.TimeStampedEvent.create(0f, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.YoimiyaSAFirework(ep);
                        }, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.375F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.YoimiyaSA(ep);
                        }, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0f, (ep, anim, objs) -> {
                            if(ep instanceof PlayerPatch){
                                ep.setMaxStunShield(114514.0f);
                                ep.setStunShield(ep.getMaxStunShield());
                            }
                        }, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(4f, (ep, anim, objs) -> {
                            if(ep instanceof PlayerPatch){
                                ep.setMaxStunShield(0f);
                                ep.setStunShield(ep.getMaxStunShield());
                            }
                        }, AnimationEvent.Side.SERVER)
                });

        GS_Yoimiya_FallAtk_Last = new FallAtkFinalAnim(0.05F, 0.5F, 0.8F, 2.1F, WeaponCollider.GenShin_Bow_FallAttack, biped.rootJoint, "biped/gs_yoimiya_fall_atk_last", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, Sounds.GENSHIN_BOW_FALLATK)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, Sounds.GENSHIN_BOW_FALLATK)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(114514))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(7f))
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0.45f, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.SendParticle(
                                    ep.getOriginal().getLevel(),
                                    Particles.GENSHIN_BOW_LANDING.get(),
                                    ep.getOriginal().position()
                            );
                        }, AnimationEvent.Side.SERVER)
                });

        GS_Yoimiya_FallAtk_Loop = new FallAtkLoopAnim(0.1f,"biped/gs_yoimiya_fall_atk_loop", biped, GS_Yoimiya_FallAtk_Last);

        GS_Yoimiya_FallAtk_Start = new FallAtkStartAnim(0.1f,"biped/gs_yoimiya_fall_atk_start", biped, GS_Yoimiya_FallAtk_Loop)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(3.6f));

        //SCYTHES
        SAO_SCYTHE_IDLE = new StaticAnimation(true, "biped/battle_scythe/living/battle_scythe_idle", biped);
        SAO_SCYTHE_RUN = new MovementAnimation(true, "biped/battle_scythe/living/battle_scythe_run", biped);
        SAO_SCYTHE_WALK = new MovementAnimation(true, "biped/battle_scythe/living/battle_scythe_walk", biped);
        SAO_SINGLE_SWORD_GUARD = new StaticAnimation(0.25F, true, "biped/skill/sao_single_sword_guard", biped);

        BATTLE_SCYTHE_AUTO1 = new BasicAttackAnimation(0.08F, 0.2F, 0.4F, 0.5F, WeaponCollider.SAO_SWORD, biped.toolR, "biped/battle_scythe/battle_scythe_auto1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.85f, 5, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        BATTLE_SCYTHE_AUTO2 = new BasicAttackAnimation(0.08F, 0.2F, 0.3F, 0.4F, WeaponCollider.SAO_SWORD, biped.toolR, "biped/battle_scythe/battle_scythe_auto2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.45f, 5, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        BATTLE_SCYTHE_AUTO3 = new BasicAttackAnimation(0.04F, 0.3F, 0.4F, 0.55F, WeaponCollider.SAO_SWORD, biped.toolR, "biped/battle_scythe/battle_scythe_auto3", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.55f, 5, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        BATTLE_SCYTHE_AUTO4 = new BasicAttackAnimation(0.06F,  "biped/battle_scythe/battle_scythe_auto4", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.15F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.2F, 0.3F, 0.35F, 0.4F, 0.4F, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.4F, 0.5F, 0.55F, 0.6F, 0.6F, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.6F, 0.9F, 1F, 1.1F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,2.28f, 5, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        BATTLE_SCYTHE_AUTO5 = new BasicAttackAnimation(0.06F, 0.25F, 0.4F, 0.8F, WeaponCollider.SAO_SWORD, biped.toolR, "biped/battle_scythe/battle_scythe_auto5", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.65f, 5, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        BATTLE_SCYTHE_DASH = new BasicAttackAnimation(0.1F,  "biped/battle_scythe/battle_scythe_dash", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.15F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD_HUGE_R)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.2F, 0.3F, 0.35F, 0.4F, 0.4F, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD_HUGE_R)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.4F, 0.5F, 0.6F, 0.85F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD_HUGE_R)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.17f, 5, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );


        SAO_DUAL_SWORD_HOLD = new StaticAnimation(true, "biped/living/sao_dual_sword_hold", biped);
        SAO_DUAL_SWORD_NORMAL = new StaticAnimation(true, "biped/living/sao_dual_sword_hold_normal", biped);
        SAO_DUAL_SWORD_RUN = new MovementAnimation(true, "biped/living/sao_dual_sword_hold_run", biped);

        SAO_DUAL_SWORD_AUTO1 = new BasicAttackAnimation(0.05F, 0.2F, 0.3F, 0.5F, null, biped.toolR, "biped/sao_dual_sword/sao_dual_sword_auto1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0f,0.35f, 7, biped.toolR, InteractionHand.MAIN_HAND)
                ));

        SAO_DUAL_SWORD_AUTO2 = new BasicAttackAnimation(0.05F, 0.01F, 0.2F, 0.2F, InteractionHand.OFF_HAND ,null, biped.toolL, "biped/sao_dual_sword/sao_dual_sword_auto2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0f,0.2f, 7, biped.toolL, InteractionHand.OFF_HAND)
                ));

        SAO_DUAL_SWORD_AUTO3 = new BasicAttackAnimation(0.05F, 0.01F, 0.2F, 0.2F, InteractionHand.OFF_HAND ,null, biped.toolL, "biped/sao_dual_sword/sao_dual_sword_auto3", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0f,0.2f, 7, biped.toolL, InteractionHand.OFF_HAND)
                ));

        SAO_DUAL_SWORD_AUTO4 = new BasicAttackAnimation(0.05F, "biped/sao_dual_sword/sao_dual_sword_auto4", biped,
                new AttackAnimation.Phase(0.0F, 0.05F, 0.15F, 0.15F, 0.15F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.15F, 0.15F, 0.3F, 0.5F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0f,0.2f, 7, biped.toolR, InteractionHand.MAIN_HAND),
                        newTF(0.1f,0.35f, 7, biped.toolL, InteractionHand.OFF_HAND)
                ));
        //auto4:右手，antic:0.05F,contact:0.15F
        //auto4:左手，antic:0.15F,contact:0.3F

        SAO_DUAL_SWORD_AUTO5 = new BasicAttackAnimation(0.05F, "biped/sao_dual_sword/sao_dual_sword_auto5", biped,
                new AttackAnimation.Phase(0.0F, 0.15F, 0.2F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.2F, 0.2F, 0.3F, 0.4F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.1f,0.25f, 7, biped.toolR, InteractionHand.MAIN_HAND),
                        newTF(0.15f,0.35f, 7, biped.toolL, InteractionHand.OFF_HAND)
                ));
        //auto5:右手，antic:0.15F,contact:0.2F
        //auto5:左手，antic:0.2F,contact:0.3F
        SAO_DUAL_SWORD_AUTO6 = new BasicAttackAnimation(0.05F, "biped/sao_dual_sword/sao_dual_sword_auto6", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.2F, 0.2F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.0F, 0.1F, 0.2F, 0.2F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.05f,0.25f, 7, biped.toolR, InteractionHand.MAIN_HAND),
                        newTF(0.05f,0.25f, 7, biped.toolL, InteractionHand.OFF_HAND)
                ));
        //auto6:右手，antic:0.1F,contact:0.2F
        //auto6:左手，antic:0.1F,contact:0.2F
        SAO_DUAL_SWORD_AUTO7 = new BasicAttackAnimation(0.05F, "biped/sao_dual_sword/sao_dual_sword_auto7", biped,
                new AttackAnimation.Phase(0.0F, 0.01F, 0.1F, 0.2F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.0F, 0.01F, 0.1F, 0.2F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.31F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.15F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.15f, 7, biped.toolR, InteractionHand.MAIN_HAND),
                        newTF(0.0f,0.15f, 7, biped.toolL, InteractionHand.OFF_HAND)
                ));
        //auto7:右手，antic:0.01F,contact:0.1F
        //autO7:左手，antic:0.01F,contact:0.1F
        SAO_DUAL_SWORD_AUTO8 = new BasicAttackAnimation(0.05F, "biped/sao_dual_sword/sao_dual_sword_auto8", biped,
                new AttackAnimation.Phase(0.0F, 0.05F, 0.1F, 0.15F, 0.15F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.85F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.0F, 0.05F, 0.1F, 0.15F, 0.15F, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.85F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.15F, 0.15F, 0.2F, 0.2F,  Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.85F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.15F, 0.15F, 0.2F, 0.2F,  Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.85F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.4F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.9F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.12f, 5, biped.toolR, InteractionHand.MAIN_HAND),
                        newTF(0.0f,0.12f, 5, biped.toolL, InteractionHand.OFF_HAND),
                        newTF(0.12f,0.25f, 7, biped.toolR, InteractionHand.MAIN_HAND),
                        newTF(0.12f,0.25f, 7, biped.toolL, InteractionHand.OFF_HAND)
                ));
        //auto8:右手，antic:0.05F,contact:0.1F\antic:0.15F,contact:0.2F(单手两段攻击)
        //autO8:左手，antic:0.05F,contact:0.1F\antic:0.15F,contact:0.2F(单手两段攻击)
        SAO_DUAL_SWORD_AUTO9 = new BasicAttackAnimation(0.05F,  "biped/sao_dual_sword/sao_dual_sword_auto9", biped,
                new AttackAnimation.Phase(0.0F, 0.01F, 0.1F, 0.2F,  Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.0F, 0.01F, 0.1F, 0.2F,  Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.05F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.15f, 7, biped.toolR, InteractionHand.MAIN_HAND),
                        newTF(0.0f,0.15f, 7, biped.toolL, InteractionHand.OFF_HAND)
                ));
        //auto9:右手，antic:0.01F,contact:0.1F
        //autO9:左手，antic:0.01F,contact:0.1F
        SAO_DUAL_SWORD_AUTO10 = new BasicAttackAnimation(0.05F, 0.01F, 0.1F, 0.2F, WeaponCollider.SAO_SWORD_DUAL_AUTO10, biped.rootJoint, "biped/sao_dual_sword/sao_dual_sword_auto10", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.45F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.05F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.15f, 7, biped.toolR, InteractionHand.MAIN_HAND),
                        newTF(0.0f,0.15f, 7, biped.toolL, InteractionHand.OFF_HAND)
                ));

        SAO_DUAL_SWORD_AUTO11 = new BasicAttackAnimation(0.05F, 0.01F,0.1F, 0.3F, WeaponCollider.SAO_SWORD_AIR, biped.rootJoint, "biped/sao_dual_sword/sao_dual_sword_auto11", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.08F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.15f, 7, biped.toolR, InteractionHand.MAIN_HAND),
                        newTF(0.0f,0.15f, 7, biped.toolL, InteractionHand.OFF_HAND)
                ));

        SAO_DUAL_SWORD_AUTO12 = new BasicAttackAnimation(0.05F, 0.01F, 0.1F, 0.5F, WeaponCollider.SAO_SWORD_AIR, biped.rootJoint, "biped/sao_dual_sword/sao_dual_sword_auto12", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.15f, 7, biped.toolR, InteractionHand.MAIN_HAND),
                        newTF(0.0f,0.15f, 7, biped.toolL, InteractionHand.OFF_HAND)
                ));

        SAO_DUAL_SWORD_DASH = new DashAttackAnimation(0.02F, "biped/sao_dual_sword/sao_dual_sword_dash", biped,
                new AttackAnimation.Phase(0.0F, 0.07F, 0.6F, 0.7F,  Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.7F, 0.75F, 0.9F, 0.9F,  Float.MAX_VALUE, InteractionHand.OFF_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.82F, 0.84F, 1.02F, 1.1666F,  Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.65f, 7, biped.toolR, InteractionHand.MAIN_HAND),
                        newTF(0.65f,0.95f, 7, biped.toolL, InteractionHand.OFF_HAND),
                        newTF(0.75f,1.05f, 7, biped.toolR, InteractionHand.MAIN_HAND)
                ));




    }
    public static AnimationProperty.PlaybackTimeModifier MSpeed(float t){
        return (a,b,c,d) -> t;
    }


    public static List<TrailInfo> newTFL(TrailInfo... tfs){
        return Lists.newArrayList(tfs);
    }

    public static TrailInfo newTF(float start, float end, Joint joint, InteractionHand hand){
        JsonObject je = new JsonObject();
        je.addProperty("joint", joint.getName());
        je.addProperty("startTime", start);
        je.addProperty("endTime", end);
        je.addProperty("item_skin_hand", hand.toString());

        //System.out.println(je);

        return TrailInfo.deserialize(je);
    }

    public static TrailInfo newTF(float start, float end, int lifetime, Joint joint, InteractionHand hand){
        JsonObject je = new JsonObject();
        je.addProperty("joint", joint.getName());
        je.addProperty("startTime", start);
        je.addProperty("endTime", end);
        je.addProperty("item_skin_hand", hand.toString());
        je.addProperty("lifetime", (int)((end - start) * 20 + lifetime));
        //System.out.println(je);

        return TrailInfo.deserialize(je);
    }

    public static CamAnim Yoimiya;
    public static void RegCamAnims(){
        Yoimiya = regCamAnim(new CamAnim(0.3f , EpicACG.MODID, "camanim/yoimiya.json"));
        //SAO_RAPIER_SA2_CAM = regCamAnim(new CamAnim(0.3f ,EpicAddon.MODID, "camanim/sao_rapier_sa2.json"));
        //SAO_RAPIER_SA2_CAM2 = regCamAnim(new CamAnim(0.3f ,EpicAddon.MODID, "camanim/sao_rapier_sa2_post.json"));
        //DMC_V_PREV = regCamAnim(new CamAnim(0.4f, EpicAddon.MODID, "camanim/dmc_v_prev.json"));
    }

    public static CamAnim regCamAnim(CamAnim anim){
        CamAnimRegistry.add(anim);
        return anim;
    }

}
