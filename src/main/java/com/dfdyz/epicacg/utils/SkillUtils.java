package com.dfdyz.epicacg.utils;

import net.minecraft.world.InteractionHand;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class SkillUtils {

    public static Skill getMainHandSkill(PlayerPatch pp){
        return pp.getHoldingItemCapability(InteractionHand.MAIN_HAND).getInnateSkill(pp, pp.getValidItemInHand(InteractionHand.MAIN_HAND));
    }

}
