package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;

@Mod.EventBusSubscriber(modid= EpicACG.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class Sounds {
    public static final HashSet<SoundEvent> SOUND_EVENTS = Sets.newHashSet();
    public static final SoundEvent GENSHIN_BOW = RegSound("weapon.genshin_bow");
    public static final SoundEvent GENSHIN_BOW_FALLATK = RegSound("weapon.genshin_bow_fallatk");
    //public static final SoundEvent Yoimiya_Combo1 = RegSound("character.yoimiya.genshin_bow");
    //public static final SoundEvent Yoimiya_Combo2 = RegSound("character.yoimiya.genshin_bow");

    public static final SoundEvent Yoimiya_Skill1 = RegSound("character.yoimiya.skill1");
    public static final SoundEvent Yoimiya_Skill2 = RegSound("character.yoimiya.skill2");
    public static final SoundEvent Yoimiya_Skill3 = RegSound("character.yoimiya.skill3");

    public static final SoundEvent DualSword_SA1_1 = RegSound("weapon_skill.dual_sword.sa1_1");
    public static final SoundEvent DualSword_SA1_2 = RegSound("weapon_skill.dual_sword.sa1_2");
    public static final SoundEvent DMC5_JC_1 = RegSound("weapon_skill.dmc5_jc_1");
    public static final SoundEvent DMC5_JC_2 = RegSound("weapon_skill.dmc5_jc_2");


    private static SoundEvent RegSound(String name) {
        ResourceLocation r = new ResourceLocation(EpicACG.MODID, name);
        SoundEvent s = new SoundEvent(r).setRegistryName(name);
        SOUND_EVENTS.add(s);
        return s;
    }

    @SubscribeEvent
    public static void onSoundRegistry(final RegistryEvent.Register<SoundEvent> event) {
        SOUND_EVENTS.forEach((s) -> event.getRegistry().register(s));
    }
}
