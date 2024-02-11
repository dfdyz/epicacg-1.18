package com.dfdyz.epicacg.efmextra.skills.GenShinInternal;


import com.dfdyz.epicacg.efmextra.skills.SimpleWeaponSASkill;
import com.dfdyz.epicacg.utils.SkillUtils;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;
import java.util.Map;

public class GSSpecialAttack extends SimpleWeaponSASkill {
    public GSSpecialAttack(Builder builder) {
        super(builder);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        if (executer.isLogicalClient()) {
            return (executer.getSkill(SkillSlots.WEAPON_INNATE).isFull() || (executer.getOriginal()).isCreative())
                    && !(executer.isUnstable())
                    && Math.abs(executer.getOriginal().getDeltaMovement().y) <= 0.3f
                    && closedGround(executer);
        } else {
            return SkillUtils.getMainHandSkill(executer) == this
                    && (executer.getOriginal()).getVehicle() == null
                    && (!executer.getSkill(SkillSlots.WEAPON_INNATE).isActivated() || this.activateType == ActivateType.TOGGLE)
                    && !(executer.isUnstable())
                    && Math.abs(executer.getOriginal().getDeltaMovement().y) <= 0.3f
                    && closedGround(executer);
        }
    }

    private boolean closedGround(PlayerPatch<?> executer){
        Vec3 epos = executer.getOriginal().position();
        ClipContext clipContext = new ClipContext(epos, epos.add(0,-2,0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, executer.getOriginal());
        Level level = executer.getOriginal().level;
        BlockHitResult result = level.clip(clipContext);
        return result.getType() == HitResult.Type.BLOCK;
    }
}
