package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.efmextra.skills.EpicACGSkillCategories;
import com.dfdyz.epicacg.efmextra.skills.GenShinInternal.GSBasicAtkPatch;
import com.dfdyz.epicacg.efmextra.skills.GenShinInternal.GSBowInternal;
import com.dfdyz.epicacg.efmextra.skills.GenShinInternal.GSFallAttack;
import com.dfdyz.epicacg.efmextra.skills.GenShinInternal.GSSpecialAttack;
import com.dfdyz.epicacg.efmextra.skills.MutiSpecialSkill;
import com.dfdyz.epicacg.efmextra.skills.SAO.*;
import com.dfdyz.epicacg.efmextra.skills.SimpleWeaponSASkill;
import com.dfdyz.epicacg.efmextra.skills.TagSkill;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

import static yesman.epicfight.skill.Skill.Resource.WEAPON_INNATE_ENERGY;

public class MySkills {
    public static Skill MUTI_SPECIAL_ATTACK;


    public static Skill GS_Bow_FallAttackPatch;
    public static Skill GS_Bow_BasicAttackPatch;
    public static Skill GS_Bow_Internal;


    public static Skill SAO_SINGLESWORD_INTERNAL;
    public static Skill SAO_BASICATK_PATCH;
    public static Skill SAO_SINGLESWORD_SA;

    public static Skill BATTLE_SCYTHE_SA;

    public static Skill SAO_DUALSWORD;
    public static Skill SAO_SINGLESWORD;
    public static Skill SAO_RAPIER_A;
    public static Skill WEAPON_SKILL_RAPIER;
    public static Skill GS_YOIMIYA_SPECIALATK;
    public static void registerSkills() {
        SkillManager.register(MutiSpecialSkill::new,
                Skill.createBuilder().setRegistryName(new ResourceLocation(EpicACG.MODID,"muti_sa"))
                        .setCategory(EpicACGSkillCategories.MutiSpecialAttack)
                //.setActivateType(Skill.ActivateType.PASSIVE)
                , EpicACG.MODID, "muti_sa");

        SkillManager.register(GSSpecialAttack::new,
                (SimpleWeaponInnateSkill.Builder) SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder()
                        .setAnimations(new ResourceLocation(EpicACG.MODID,"biped/gs_yoimiya_sa"))
                        .setResource(WEAPON_INNATE_ENERGY)
                        .setRegistryName(new ResourceLocation(EpicACG.MODID, "gs_yoimiya_sa"))
                , EpicACG.MODID, "gs_yoimiya_sa");


        SkillManager.register(GSFallAttack::new,
                GSFallAttack.createBuilder()
                        .setCategory(SkillCategories.AIR_ATTACK)
                        .setRegistryName(new ResourceLocation(EpicACG.MODID,"gs_air_attack_patch"))
                        .setActivateType(Skill.ActivateType.ONE_SHOT)
                        .setResource(Skill.Resource.STAMINA)
                , EpicACG.MODID, "gs_air_attack_patch");

        SkillManager.register(GSBasicAtkPatch::new,
                GSBasicAtkPatch.createBuilder()
                        .setCategory(SkillCategories.BASIC_ATTACK)
                        .setRegistryName(new ResourceLocation(EpicACG.MODID,"gs_basic_attack_patch"))
                        .setActivateType(Skill.ActivateType.ONE_SHOT)
                        .setResource(Skill.Resource.NONE)
                , EpicACG.MODID, "gs_basic_attack_patch");

        SkillManager.register(GSBowInternal::new,
                GSBowInternal.GetBuilder("gs_bow_internal")
                        .setCategory(SkillCategories.WEAPON_PASSIVE)
                        //.setActivateType(Skill.)
                        .setResource(Skill.Resource.NONE)
                , EpicACG.MODID, "gs_bow_internal");

        SkillManager.register(TagSkill::new,
                TagSkill.createBuilder(
                        new ResourceLocation(EpicACG.MODID,"sao_single_sword"),
                        EpicACGSkillCategories.SAO_SINGLE_SWORD
                ).setCreativeTab(Items.ITEM_TAB)
                , EpicACG.MODID, "sao_single_sword");
        SkillManager.register(DualBladeSkill::new,
                DualBladeSkill.createBuilder(new ResourceLocation(EpicACG.MODID,"sao_dual_sword_skill"))
                        .setCreativeTab(Items.ITEM_TAB)
                , EpicACG.MODID, "sao_dual_sword_skill");


        SkillManager.register(RapierSkill::new,
                RapierSkill.createBuilder(new ResourceLocation(EpicACG.MODID,"sao_rapier_skill"))
                        .setCreativeTab(Items.ITEM_TAB)
                , EpicACG.MODID, "sao_rapier_skill");

        SkillManager.register(RapierSpicialAttackSkill::new,
                RapierSpicialAttackSkill.createBuilder(
                                new ResourceLocation(EpicACG.MODID, "weapon_skill_rapier"))
                        .setResource(WEAPON_INNATE_ENERGY)
                        .setCategory(SkillCategories.WEAPON_INNATE)
                        .setAnimation(new ResourceLocation(EpicACG.MODID, "biped/sao_rapier_dash_long"))
                , EpicACG.MODID, "weapon_skill_rapier");

        SkillManager.register(SAOSingleSwordInternal::new,
                SAOSingleSwordInternal.createBuilder()
                        .setCategory(SkillCategories.WEAPON_PASSIVE)
                        .setRegistryName(new ResourceLocation(EpicACG.MODID, "sao_single_sword_internal"))
                        //.setActivateType(Skill.ActivateType.PASSIVE)
                        .setResource(Skill.Resource.NONE)
                , EpicACG.MODID, "sao_single_sword_internal");

        SkillManager.register(SAOBasicAtkPatch::new,
                SAOBasicAtkPatch.createBuilder().setCategory(SkillCategories.BASIC_ATTACK)
                        .setActivateType(Skill.ActivateType.ONE_SHOT)
                        .setResource(Skill.Resource.NONE)
                        .setRegistryName(new ResourceLocation(EpicACG.MODID,"sao_basic_attack_patch"))
                , EpicACG.MODID, "sao_basic_attack_patch");

        SkillManager.register(SingleSwordSASkills::new,
                SingleSwordSASkills.createBuilder(new ResourceLocation(EpicACG.MODID, "single_sword_sa"))
                        .setCategory(SkillCategories.WEAPON_INNATE)
                , EpicACG.MODID, "single_sword_sa");

        SkillManager.register(SimpleWeaponSASkill::new,
                (SimpleWeaponInnateSkill.Builder) SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder()
                        .setAnimations(new ResourceLocation(EpicACG.MODID,"biped/battle_scythe/battle_scythe_sa1"))
                        .setResource(WEAPON_INNATE_ENERGY)
                        .setRegistryName(new ResourceLocation(EpicACG.MODID, "battle_scythe_sa"))
                , EpicACG.MODID, "battle_scythe_sa");

    }


    public static void BuildSkills(SkillBuildEvent event){
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("Build EpicACG Skill");

        MUTI_SPECIAL_ATTACK = event.build(EpicACG.MODID, "muti_sa");

        GS_YOIMIYA_SPECIALATK = event.build(EpicACG.MODID, "gs_yoimiya_sa");
        GS_Bow_FallAttackPatch = event.build(EpicACG.MODID,"gs_air_attack_patch");
        GS_Bow_BasicAttackPatch = event.build(EpicACG.MODID,"gs_basic_attack_patch");
        GS_Bow_Internal = event.build(EpicACG.MODID,"gs_bow_internal");

        SAO_BASICATK_PATCH = event.build(EpicACG.MODID,"sao_basic_attack_patch");
        SAO_SINGLESWORD_INTERNAL = event.build(EpicACG.MODID, "sao_single_sword_internal");
        SAO_SINGLESWORD_SA = event.build(EpicACG.MODID, "single_sword_sa");

        BATTLE_SCYTHE_SA = event.build(EpicACG.MODID, "battle_scythe_sa");

        SAO_SINGLESWORD = event.build(EpicACG.MODID, "sao_single_sword");
        SAO_DUALSWORD = event.build(EpicACG.MODID, "sao_dual_sword_skill");
        SAO_RAPIER_A = event.build(EpicACG.MODID,"sao_rapier_skill");
        WEAPON_SKILL_RAPIER = event.build(EpicACG.MODID, "weapon_skill_rapier");

    }

}
