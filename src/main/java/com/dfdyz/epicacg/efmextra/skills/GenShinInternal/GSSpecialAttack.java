package com.dfdyz.epicacg.efmextra.skills.GenShinInternal;


import com.dfdyz.epicacg.utils.SkillUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class GSSpecialAttack extends SimpleWeaponInnateSkill {
    public GSSpecialAttack(Builder builder) {
        super(builder);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        if (executer.isLogicalClient()) {
            return (executer.getSkill(SkillSlots.WEAPON_INNATE).isFull() || ((Player)executer.getOriginal()).isCreative())
                    && !(executer.isUnstable())
                    && Math.abs(executer.getOriginal().getDeltaMovement().y) <= 0.3f
                    && closedGround(executer);
        } else {
            return SkillUtils.getMainHandSkill(executer) == this
                    && ((Player)executer.getOriginal()).getVehicle() == null
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
