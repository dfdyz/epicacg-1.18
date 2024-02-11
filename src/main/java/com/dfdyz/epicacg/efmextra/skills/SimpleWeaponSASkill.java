package com.dfdyz.epicacg.efmextra.skills;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;
import java.util.Map;

public class SimpleWeaponSASkill extends SimpleWeaponInnateSkill {
    public SimpleWeaponSASkill(Builder builder) {
        super(builder);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        String traslatableText = this.getTranslationKey();
        list.add((new TranslatableComponent(traslatableText)).withStyle(ChatFormatting.WHITE).append((new TextComponent(String.format("[%.0f]", this.consumption))).withStyle(ChatFormatting.AQUA)));
        list.add((new TranslatableComponent(traslatableText + ".tooltip")).withStyle(ChatFormatting.DARK_GRAY));

        //this.generateTooltipforPhase(list, itemStack, cap, playerCap, Maps.newHashMap(), "Each Strike:");

        return list;
    }
}
