package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.world.entity.projectile.GenShinArrow;
import com.dfdyz.epicacg.world.entity.projectile.YoimiyaSAArrow;
import com.dfdyz.epicacg.world.item.GenShinImpact.BowWeaponItem;
import com.dfdyz.epicacg.world.item.SimpleWeaponItem;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.item.WeaponItem;

public class Items {
    public static final CreativeModeTab ITEM_TAB = new CreativeModeTab(EpicACG.MODID + ".items") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(TrainingBow.get());
        }
    };

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EpicACG.MODID);
    public static final RegistryObject<Item> TrainingBow = ITEMS.register("training_bow", () -> new BowWeaponItem(new  Item.Properties().durability(600).tab(ITEM_TAB)));
    public static final RegistryObject<Item> BattleScythe = ITEMS.register("battle_scythe", () -> new SimpleWeaponItem(new Item.Properties().tab(ITEM_TAB), Tiers.DIAMOND, 8));
}
