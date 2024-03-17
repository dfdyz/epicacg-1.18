package com.dfdyz.epicacg.efmextra.skills.SAO;

import com.dfdyz.epicacg.efmextra.skills.EpicACGSkillSlot;
import com.dfdyz.epicacg.efmextra.skills.IMutiSpecialSkill;
import com.dfdyz.epicacg.efmextra.skills.MutiSpecialSkill;
import com.dfdyz.epicacg.efmextra.skills.SimpleWeaponSASkill;
import com.dfdyz.epicacg.registry.MyAnimations;
import com.dfdyz.epicacg.registry.MySkills;
import com.dfdyz.epicacg.utils.SkillUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.ArrayList;
import java.util.UUID;

import static yesman.epicfight.skill.Skill.Resource.WEAPON_INNATE_ENERGY;

public class SingleSwordSASkills  extends SimpleWeaponSASkill implements IMutiSpecialSkill {
    private final ArrayList<ResourceLocation> noPower = new ArrayList<>();
    private final ArrayList<ResourceLocation> morePower = new ArrayList<>();
    public static final SkillDataManager.SkillDataKey<Boolean> Invincible = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.BOOLEAN);;
    private final UUID EventUUID = UUID.fromString("eb69decf-48a1-5333-dacc-884fd345c02a");

    private final StaticAnimation noPowerAnimation1;
    private final StaticAnimation morePowerAnimation1;

    public SingleSwordSASkills(Builder builder) {
        super(builder);
        noPowerAnimation1 = Animations.SWEEPING_EDGE;
        morePowerAnimation1 = MyAnimations.DMC5_V_JC;

        ResourceLocation name = this.getRegistryName();
        noPower.add(new ResourceLocation(name.getNamespace(), "textures/gui/skills/" + name.getPath() + ".png"));
        noPower.add(new ResourceLocation(name.getNamespace(), "textures/gui/skills/single/judgement_cut.png"));

    }

    public static SimpleWeaponInnateSkill.Builder createBuilder(ResourceLocation resourceLocation) {
        return (SimpleWeaponInnateSkill.Builder) (new SimpleWeaponInnateSkill.Builder()).setCategory(SkillSlots.WEAPON_INNATE.category()).setRegistryName(resourceLocation).setResource(WEAPON_INNATE_ENERGY);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getSkillCapability()
                .skillContainers[EpicACGSkillSlot.SKILL_SELECTOR.universalOrdinal()]
                .setSkill(MySkills.MUTI_SPECIAL_ATTACK);

        container.getDataManager().registerData(Invincible);
        container.getDataManager().setData(Invincible, false);

        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.HURT_EVENT_PRE,EventUUID, (e)->{
            PlayerPatch playerPatch = e.getPlayerPatch();
            SkillContainer sc = playerPatch.getSkill(SkillSlots.WEAPON_INNATE);
            if(sc != null){
                boolean inv = sc.getDataManager().getDataValue(Invincible);
                if(inv){
                    e.setResult(AttackResult.ResultType.BLOCKED);
                    e.setCanceled(true);
                }
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getDataManager().setData(Invincible, false);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.HURT_EVENT_PRE,EventUUID);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        if (executer.isLogicalClient()) {
            boolean ok = false;
            SkillContainer skillContainer = executer.getSkill(SkillSlots.WEAPON_INNATE);
            int selected = executer.getSkill(EpicACGSkillSlot.SKILL_SELECTOR).getDataManager().getDataValue(MutiSpecialSkill.CHILD_SKILL_INDEX);

            ok = skillContainer.getStack() >= (selected == 0 ? 1:5);

            return ok || (executer.getOriginal()).isCreative();
        } else {
            return SkillUtils.getMainHandSkill(executer) == this && (executer.getOriginal()).getVehicle() == null && (!executer.getSkill(SkillSlots.WEAPON_INNATE).isActivated());
        }
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        SkillContainer skill = executer.getSkill(SkillSlots.WEAPON_INNATE);
        int selected = executer.getSkill(EpicACGSkillSlot.SKILL_SELECTOR).getDataManager().getDataValue(MutiSpecialSkill.CHILD_SKILL_INDEX);

        if(selected == 0){
            executer.playAnimationSynchronized(this.noPowerAnimation1, 0.0F);
            this.setStackSynchronize(executer, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 1);
        }
        else {
            executer.playAnimationSynchronized(this.morePowerAnimation1, 0.0F);
            this.setStackSynchronize(executer, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 5);
        }

        this.setDurationSynchronize(executer, this.maxDuration);
        skill.activate();
    }


    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }

    @Override
    public ArrayList<ResourceLocation> getSkillTextures(PlayerPatch<?> executer) {
        return noPower;
    }

    @Override
    public boolean isSkillActive(PlayerPatch<?> executer, int idx) {
        if(executer.getOriginal().isCreative()) return true;

        if(idx == 0){
            return true;
        }
        else if(idx == 1){
            if (executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() >= 5){
                return true;
            }
        }
        return false;
    }
}
