package com.dfdyz.epicacg.efmextra.skills.GenShinInternal;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.List;
import java.util.UUID;


public class GSFallAttack extends Skill {
    public static final SkillDataManager.SkillDataKey<Integer> FALL_STATE = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    private static final UUID EVENT_UUID = UUID.fromString("2797461e-2bcc-7015-bf07-390a557fed81");
/*
    public Builder<GSFallAttack> GetBuilder(String registryName){
        return new Builder<GSFallAttack>(new ResourceLocation(EpicAddon.MODID, registryName)).setCategory(SkillCategories.AIR_ATTACK);
    }
 */
    public GSFallAttack(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public boolean isExecutableState(PlayerPatch<?> executer) {
        EntityState playerState = executer.getEntityState();
        Player player = executer.getOriginal();

        Vec3 epos = executer.getOriginal().position();
        ClipContext clipContext = new ClipContext(epos, epos.add(0,-3.6,0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, executer.getOriginal());
        Level level = executer.getOriginal().level;
        BlockHitResult result = level.clip(clipContext);
        boolean fallAttack = result.getType() == HitResult.Type.MISS || result.getType() == HitResult.Type.ENTITY;

        return !(player.isPassenger() || player.isSpectator() || !playerState.canBasicAttack()) && fallAttack;
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        List<StaticAnimation> motions = executer.getHoldingItemCapability(InteractionHand.MAIN_HAND).getAutoAttckMotion(executer);
        StaticAnimation attackMotion = motions.get(motions.size() - 1);

        if (attackMotion != null) {
            super.executeOnServer(executer, args);
            executer.playAnimationSynchronized(attackMotion, 0);
        }
    }


    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(FALL_STATE);

        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER,EVENT_UUID,(event) -> {
            //System.out.println("233333");
        });

    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getDataManager().setData(FALL_STATE, 0);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);
    }
}
