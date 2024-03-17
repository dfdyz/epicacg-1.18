package com.dfdyz.epicacg.event;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.model.item.BakedModelWithISTER;
import com.dfdyz.epicacg.client.render.item.FireFlySwordRenderer;
import com.dfdyz.epicacg.registry.Items;
import com.dfdyz.epicacg.world.item.FireFlySwordItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = EpicACG.MODID, value = Dist.CLIENT)
public class LoadingEvents {

    @SubscribeEvent
    public static void RegisterModelLoader(ModelRegistryEvent event){
        //ModelLoaderRegistry.registerLoader();
    }

    public static void onModelBaked(ModelBakeEvent event) {
        EpicACG.LOGGER.info("Reg Item Renderer");
        ModelResourceLocation location = new ModelResourceLocation("epicacg:firefly_sword#inventory");
        BakedModel existingModel = event.getModelRegistry().get(location);
        if (existingModel != null && !(existingModel instanceof BakedModelWithISTER)) {
            BakedModelWithISTER isterModel = new BakedModelWithISTER(existingModel);
            event.getModelRegistry().put(location, isterModel);

            event.getModelLoader().getModel(new ResourceLocation("epicacg:item/firefly_sword_emissive"));
            FireFlySwordRenderer.layer1 = event.getModelLoader().bake(new ResourceLocation("epicacg:item/firefly_sword_emissive"), BlockModelRotation.X0_Y0);

            event.getModelLoader().getModel(new ResourceLocation("epicacg:item/firefly_sword_unlit"));
            FireFlySwordRenderer.layer0 = event.getModelLoader().bake(new ResourceLocation("epicacg:item/firefly_sword_unlit"), BlockModelRotation.X0_Y0);

            EpicACG.LOGGER.info("OK.");
        }
        else {
            EpicACG.LOGGER.info("Error while baking model.");
        }
    }

    public static void RegItemModelOverride(){
        try {

            EpicACG.LOGGER.info("Reg Item Model Override");
            /*
            ItemProperties.register(Items.FireFlySword.get(), new ResourceLocation(EpicACG.MODID, "part"),
                    (itemStack, clientWorld, livingEntity, i) ->
                    {
                        CompoundTag tags = itemStack.getOrCreateTag();
                        return tags.getShort("render_part");
                    });*/
        }catch (Exception e){

        }
    }


}
