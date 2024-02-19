package com.dfdyz.epicacg.efmextra.entitypatch;

import com.dfdyz.epicacg.world.entity.mob.TrashBinMasterEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

public class TrashBinMasterPatch extends LivingEntityPatch<TrashBinMasterEntity> {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initAnimator(ClientAnimator clientAnimator) {
        //clientAnimator.addLivingAnimation(LivingMotions.IDLE, Animations.BIPED_IDLE);
        //clientAnimator.addLivingAnimation(LivingMotions.WALK, Animations.BIPED_WALK);
        //clientAnimator.addLivingAnimation(LivingMotions.RUN, Animations.BIPED_RUN);
        clientAnimator.setCurrentMotionsAsDefault();
    }

    @Override
    public void updateMotion(boolean b) {

    }

    @Override
    public StaticAnimation getHitAnimation(StunType stunType) {
        return null;
    }
}
