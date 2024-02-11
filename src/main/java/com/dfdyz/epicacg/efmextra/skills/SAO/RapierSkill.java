package com.dfdyz.epicacg.efmextra.skills.SAO;

import com.dfdyz.epicacg.efmextra.skills.EpicACGSkillCategories;
import com.dfdyz.epicacg.registry.MySkills;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class RapierSkill extends PassiveSkill {
    //private static float OrgStunShield = 0.0f;
    //public static final UUID EVENT_UUID = UUID.fromString("5d267390-b46f-41d4-940d-a1b2fb2481bd");
    public RapierSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    public static Builder<PassiveSkill> createBuilder(ResourceLocation resourceLocation) {
        return (new Builder<PassiveSkill>()).setCategory(EpicACGSkillCategories.SAO_SINGLE_SWORD).setRegistryName(resourceLocation).setResource(Resource.NONE).setActivateType(ActivateType.TOGGLE);
    }


    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        PlayerPatch pp = container.getExecuter();
        pp.getSkillCapability().addLearnedSkill(MySkills.SAO_SINGLESWORD);
    }

    @Override
    public void drawOnGui(BattleModeGui gui, SkillContainer container, PoseStack poseStack, float x, float y) {

    }

}
