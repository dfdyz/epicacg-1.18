package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.world.entity.projectile.GenShinArrow;
import com.dfdyz.epicacg.world.entity.projectile.YoimiyaSAArrow;
import com.dfdyz.epicacg.world.item.GenShinImpact.BowWeaponItem;
import com.dfdyz.epicacg.world.item.SimpleWeaponItem;
import com.dfdyz.epicacg.world.item.SpecialWeaponItem;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.item.WeaponItem;

import java.util.function.Supplier;

public class Items {
    public static final CreativeModeTab ITEM_TAB = new CreativeModeTab(EpicACG.MODID + ".items") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Elucidator.get());
        }
    };

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EpicACG.MODID);
    public static final RegistryObject<Item> TrainingBow = ITEMS.register("training_bow", () -> new BowWeaponItem(new  Item.Properties().durability(600).tab(ITEM_TAB)));
    public static final RegistryObject<Item> BattleScythe = ITEMS.register("battle_scythe", () -> new SimpleWeaponItem(new Item.Properties().tab(ITEM_TAB), Tiers.DIAMOND, 8));
    public static final RegistryObject<Item> Elucidator = ITEMS.register("elucidator", () -> new SpecialWeaponItem(new Item.Properties().tab(ITEM_TAB), EpicACGTier.SAO_Special, 15));
    public static final RegistryObject<Item> DarkRepulsor = ITEMS.register("dark_repulsor", () -> new SimpleWeaponItem(new Item.Properties().tab(ITEM_TAB), EpicACGTier.SAO_Normal, 15));


    public enum EpicACGTier implements Tier {
        SAO_Normal(4, 9999, 9.0F, 6.0F, 22, () -> {
            return Ingredient.of(net.minecraft.world.item.Items.DIAMOND_BLOCK);
        }),

        SAO_IRON(2, 380, 6.0F, 2.5F, 17, () -> {
            return Ingredient.of(net.minecraft.world.item.Items.IRON_INGOT);
        }),

        SAO_Special(4, Integer.MAX_VALUE, 9.0F, 6.0F, 22, () -> {
            return Ingredient.of(net.minecraft.world.item.Items.DIAMOND_BLOCK);
        }),
        ;


        private final int harvestLevel;
        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final int enchantability;
        private final LazyLoadedValue<Ingredient> repairMaterial;

        private EpicACGTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier repairMaterialIn) {
            this.harvestLevel = harvestLevelIn;
            this.maxUses = maxUsesIn;
            this.efficiency = efficiencyIn;
            this.attackDamage = attackDamageIn;
            this.enchantability = enchantabilityIn;
            this.repairMaterial = new LazyLoadedValue(repairMaterialIn);
        }

        public int getUses() {
            return this.maxUses;
        }

        public float getSpeed() {
            return this.efficiency;
        }

        public float getAttackDamageBonus() {
            return this.attackDamage;
        }

        public int getLevel() {
            return this.harvestLevel;
        }

        public int getEnchantmentValue() {
            return this.enchantability;
        }

        public Ingredient getRepairIngredient() {
            return (Ingredient)this.repairMaterial.get();
        }
    }

}
