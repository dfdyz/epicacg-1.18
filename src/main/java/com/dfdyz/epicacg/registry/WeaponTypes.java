package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.efmextra.weapon.GenShinBow;
import com.dfdyz.epicacg.efmextra.weapon.WeaponCollider;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.function.Function;

public class WeaponTypes {

    public static final Function<Item, CapabilityItem.Builder> GENSHIN_BOW = (item) -> {
        WeaponCapability.Builder builder = (WeaponCapability.Builder)WeaponCapability.builder()
                .category(CapabilityItem.WeaponCategories.RANGED)
                .styleProvider((playerpatch) -> CapabilityItem.Styles.ONE_HAND)
                .collider(WeaponCollider.GenShin_Bow_scan)
                .swingSound(EpicFightSounds.WHOOSH_BIG)
                .hitSound(EpicFightSounds.BLADE_HIT)
                .canBePlacedOffhand(false)
                .passiveSkill(MySkills.GS_Bow_Internal)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                        MyAnimations.GS_Yoimiya_Auto1,
                        MyAnimations.GS_Yoimiya_Auto2,
                        MyAnimations.GS_Yoimiya_Auto3,
                        MyAnimations.GS_Yoimiya_Auto4,
                        MyAnimations.GS_Yoimiya_Auto5,
                        MyAnimations.GS_Yoimiya_Auto2, MyAnimations.GS_Yoimiya_FallAtk_Start)
                .innateSkill(CapabilityItem.Styles.ONE_HAND, (itemStack) -> MySkills.GS_YOIMIYA_SPECIALATK)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, yesman.epicfight.gameasset.Animations.BIPED_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, yesman.epicfight.gameasset.Animations.BIPED_WALK)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, yesman.epicfight.gameasset.Animations.BIPED_RUN)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.JUMP, yesman.epicfight.gameasset.Animations.BIPED_JUMP)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.KNEEL, yesman.epicfight.gameasset.Animations.BIPED_KNEEL)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.SNEAK, yesman.epicfight.gameasset.Animations.BIPED_SNEAK)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.SWIM, yesman.epicfight.gameasset.Animations.BIPED_SWIM)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.AIM, yesman.epicfight.gameasset.Animations.BIPED_BOW_AIM)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.BIPED_BLOCK)
                .constructor(GenShinBow::new);

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> BATTLE_SCYTHE = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(EpicACGWeaponCategories.SCYTHE)
                .styleProvider((playerpatch) -> CapabilityItem.Styles.TWO_HAND)
                .collider(ColliderPreset.LONGSWORD)
                .hitSound(EpicFightSounds.BLADE_HIT)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        MyAnimations.BATTLE_SCYTHE_AUTO1,
                        MyAnimations.BATTLE_SCYTHE_AUTO2,
                        MyAnimations.BATTLE_SCYTHE_AUTO3,
                        MyAnimations.BATTLE_SCYTHE_AUTO4,
                        MyAnimations.BATTLE_SCYTHE_AUTO5,
                        MyAnimations.BATTLE_SCYTHE_DASH, Animations.SPEAR_TWOHAND_AIR_SLASH)
                .innateSkill(CapabilityItem.Styles.TWO_HAND,(itemStack) -> EpicFightSkills.SWEEPING_EDGE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, MyAnimations.SAO_SCYTHE_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, MyAnimations.SAO_SCYTHE_WALK)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, MyAnimations.SAO_SCYTHE_RUN)
                .weaponCombinationPredicator((entitypatch) -> false);

        return builder;
    };


    public static final Function<Item, CapabilityItem.Builder> SAO_SINGLE_SWORD = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(EpicACGWeaponCategories.SINGLE_SWORD)
                .styleProvider((playerpatch) -> {
                    //if(playerpatch instanceof PlayerPatch){
                        if(playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == EpicACGWeaponCategories.SINGLE_SWORD
                                //&& ((PlayerPatch)playerpatch).getSkill(EpicAddonSkillSlots.SAO_SINGLE_SWORD).getSkill() != null
                                //&& ((PlayerPatch)playerpatch).getSkill(EpicAddonSkillSlots.SAO_SINGLE_SWORD).getSkill().getRegistryName().getPath().equals("sao_dual_sword_skill")
                        ){
                            return EpicACGStyles.SAO_DUAL_SWORD;
                        }
                        /*
                        if(((PlayerPatch)playerpatch).getSkill(EpicAddonSkillSlots.SAO_SINGLE_SWORD).getSkill() != null
                                && ((PlayerPatch)playerpatch).getSkill(EpicAddonSkillSlots.SAO_SINGLE_SWORD).getSkill().getRegistryName().getPath().equals("sao_rapier_skill")){
                            return EpicACGStyles.SAO_RAPIER;
                        }*/
                    //}
                    /*
                    else if (playerpatch instanceof HumanoidMobPatch) {
                        Set<String> tags = playerpatch.getOriginal().getTags();
                        for (String tag : tags) {
                            String[] arg = tag.split(":");
                            if(arg.length > 2 && arg[0] == EpicAddon.MODID){
                                if(arg[1] == "sao_single_sword"){
                                    switch (arg[3]){
                                        case "dual_sword":
                                            return EpicACGStyles.SAO_DUAL_SWORD;
                                        case "rapier":
                                            return EpicACGStyles.SAO_RAPIER;
                                        default:
                                            return EpicACGStyles.SAO_SINGLE_SWORD;
                                    }
                                }
                            }
                            return CapabilityItem.Styles.ONE_HAND;
                        }
                        return EpicACGStyles.SAO_SINGLE_SWORD;
                    }

                     */
                    return EpicACGStyles.SAO_SINGLE_SWORD;
                })
                .passiveSkill(MySkills.SAO_SINGLESWORD_INTERNAL)
                .collider(WeaponCollider.SAO_SWORD)
                .hitSound(EpicFightSounds.BLADE_HIT)
                .newStyleCombo(EpicACGStyles.SAO_SINGLE_SWORD,
                        Animations.SWORD_AUTO1,
                        Animations.SWORD_AUTO2,
                        Animations.SWORD_AUTO3,
                        Animations.SWORD_DASH, Animations.SWORD_DASH,
                        Animations.SWORD_AIR_SLASH)
                /*
                .newStyleCombo(EpicACGStyles.SAO_RAPIER,
                        MyAnimations.SAO_RAPIER_AUTO1,
                        MyAnimations.SAO_RAPIER_AUTO2,
                        MyAnimations.SAO_RAPIER_AUTO3,
                        MyAnimations.SAO_RAPIER_AUTO4,
                        MyAnimations.SAO_RAPIER_AUTO5,
                        MyAnimations.SAO_RAPIER_DASH, MyAnimations.SAO_RAPIER_DASH,
                        MyAnimations.SAO_RAPIER_AIR)
                 */
                .newStyleCombo(EpicACGStyles.SAO_DUAL_SWORD,
                        MyAnimations.SAO_DUAL_SWORD_AUTO1,
                        MyAnimations.SAO_DUAL_SWORD_AUTO2,
                        MyAnimations.SAO_DUAL_SWORD_AUTO3,
                        MyAnimations.SAO_DUAL_SWORD_AUTO4,
                        MyAnimations.SAO_DUAL_SWORD_AUTO5,
                        MyAnimations.SAO_DUAL_SWORD_AUTO6,
                        MyAnimations.SAO_DUAL_SWORD_AUTO7,
                        MyAnimations.SAO_DUAL_SWORD_AUTO8,
                        MyAnimations.SAO_DUAL_SWORD_AUTO9,
                        MyAnimations.SAO_DUAL_SWORD_AUTO10,
                        MyAnimations.SAO_DUAL_SWORD_AUTO11,
                        MyAnimations.SAO_DUAL_SWORD_AUTO12,
                        /*
                        MyAnimations.SAO_DUAL_SWORD_AUTO13,
                        MyAnimations.SAO_DUAL_SWORD_AUTO14,
                        MyAnimations.SAO_DUAL_SWORD_AUTO15,
                        MyAnimations.SAO_DUAL_SWORD_AUTO16,
                         */
                        MyAnimations.SAO_DUAL_SWORD_DASH, Animations.SPEAR_DASH,
                        Animations.GREATSWORD_AIR_SLASH)
                .innateSkill(EpicACGStyles.SAO_SINGLE_SWORD,(itemstack) ->  MySkills.SAO_SINGLESWORD_SA)
                .innateSkill(EpicACGStyles.SAO_DUAL_SWORD,(itemstack) ->  EpicFightSkills.DANCING_EDGE)
                //.innateSkill(EpicACGStyles.SAO_RAPIER,(itemstack) ->  MySkills.WEAPON_SKILL_RAPIER)
                .livingMotionModifier(EpicACGStyles.SAO_SINGLE_SWORD, LivingMotions.IDLE, Animations.BIPED_IDLE)
                .livingMotionModifier(EpicACGStyles.SAO_SINGLE_SWORD, LivingMotions.BLOCK, MyAnimations.SAO_SINGLE_SWORD_GUARD)
                /*
                .livingMotionModifier(EpicACGStyles.SAO_RAPIER, LivingMotions.BLOCK, MyAnimations.SAO_SINGLE_SWORD_GUARD)
                .livingMotionModifier(EpicACGStyles.SAO_RAPIER, LivingMotions.IDLE, MyAnimations.SAO_RAPIER_IDLE)
                .livingMotionModifier(EpicACGStyles.SAO_RAPIER, LivingMotions.WALK, MyAnimations.SAO_RAPIER_WALK)
                .livingMotionModifier(EpicACGStyles.SAO_RAPIER, LivingMotions.RUN, MyAnimations.SAO_RAPIER_RUN)*/
                //.livingMotionModifier(EpicAddonStyles.SAO_RAPIER, LivingMotions.KNEEL, MyAnimations.SAO_RAPIER_IDLE)
                //.livingMotionModifier(EpicAddonStyles.SAO_RAPIER_LOCKED, LivingMotions.BLOCK, MyAnimations.SAO_SINGLE_SWORD_GUARD)
                .livingMotionModifier(EpicACGStyles.SAO_DUAL_SWORD, LivingMotions.IDLE, MyAnimations.SAO_DUAL_SWORD_HOLD)
                .livingMotionModifier(EpicACGStyles.SAO_DUAL_SWORD, LivingMotions.WALK, MyAnimations.SAO_DUAL_SWORD_HOLD)
                .livingMotionModifier(EpicACGStyles.SAO_DUAL_SWORD, LivingMotions.CHASE, MyAnimations.SAO_DUAL_SWORD_HOLD)
                .livingMotionModifier(EpicACGStyles.SAO_DUAL_SWORD, LivingMotions.RUN, MyAnimations.SAO_DUAL_SWORD_RUN)
                .livingMotionModifier(EpicACGStyles.SAO_DUAL_SWORD, LivingMotions.JUMP, MyAnimations.SAO_DUAL_SWORD_NORMAL)
                .livingMotionModifier(EpicACGStyles.SAO_DUAL_SWORD, LivingMotions.KNEEL, MyAnimations.SAO_DUAL_SWORD_NORMAL)
                .livingMotionModifier(EpicACGStyles.SAO_DUAL_SWORD, LivingMotions.SNEAK, MyAnimations.SAO_DUAL_SWORD_HOLD)
                .livingMotionModifier(EpicACGStyles.SAO_DUAL_SWORD, LivingMotions.SWIM, MyAnimations.SAO_DUAL_SWORD_NORMAL)
                .livingMotionModifier(EpicACGStyles.SAO_DUAL_SWORD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                .weaponCombinationPredicator((entitypatch) -> {
                    boolean tag = false;
                    if (entitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == EpicACGWeaponCategories.SINGLE_SWORD){
                        tag = true;
                        /*
                        if(entitypatch instanceof PlayerPatch){

                            if (((PlayerPatch)entitypatch).getSkill(EpicAddonSkillSlots.SAO_SINGLE_SWORD).getSkill() != null
                                    && ((PlayerPatch)entitypatch).getSkill(EpicAddonSkillSlots.SAO_SINGLE_SWORD).getSkill().getRegistryName().getPath().equals("sao_dual_sword_skill")){
                                tag = true;
                            }

                        }*/
                    }
                    return tag;
                });
        if (item instanceof TieredItem) {
            int harvestLevel = ((TieredItem)item).getTier().getLevel();
            builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.5D + 0.2D * harvestLevel)));
            builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of(EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
        }
        return builder;
    };
    

    public enum EpicACGWeaponCategories implements WeaponCategory {
        SCYTHE, SINGLE_SWORD;//GREAT_SWORD,ES_WIND_SNEAKER,SAO_PALADIN,SINGLE_SWORD;
        final int id;

        private EpicACGWeaponCategories() {
            this.id = WeaponCategory.ENUM_MANAGER.assign(this);
        }

        public int universalOrdinal() {
            return this.id;
        }
    }

    public enum EpicACGStyles implements Style {
        SAO_DUAL_SWORD(true),
        //SAO_DUAL_SWORD_LOCKED(true),
        SAO_SINGLE_SWORD(true);
        //SAO_RAPIER(true);

        //SAO_RAPIER_LOCKED(false);

        final boolean canUseOffhand;
        final int id;

        EpicACGStyles(boolean canUseOffhand) {
            this.id = Style.ENUM_MANAGER.assign(this);
            this.canUseOffhand = canUseOffhand;
        }

        @Override
        public int universalOrdinal() {
            return this.id;
        }

        public boolean canUseOffhand() {
            return this.canUseOffhand;
        }




    }


    public static void register(WeaponCapabilityPresetRegistryEvent event){
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("Loading WeaponCapability");
        event.getTypeEntry().put("sao_single_sword", SAO_SINGLE_SWORD);
        //event.getTypeEntry().put("destiny", DESTINY);
        event.getTypeEntry().put("genshin_bow", GENSHIN_BOW);
        //event.getTypeEntry().put("sr_baseball_bat", SR_BaseBallBat);
        event.getTypeEntry().put("battle_scythe", BATTLE_SCYTHE);


        //event.getTypeEntry().put("sao_greatsword", SAO_GREATSWORD);
        //event.getTypeEntry().put("es_wind_sneaker", ES_WIND_SNEAKER);
        //event.getTypeEntry().put("sao_paladin", SAO_PALADIN);
        LOGGER.info("WeaponCapability Loaded");
    }
}
