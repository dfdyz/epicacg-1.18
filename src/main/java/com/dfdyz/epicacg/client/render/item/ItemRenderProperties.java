package com.dfdyz.epicacg.client.render.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.IItemRenderProperties;

public class ItemRenderProperties {

    public static IItemRenderProperties firefly_sword(){
        return new IRProperty(FireFlySwordRenderer.INSTANCE);
    }

    public static class IRProperty implements IItemRenderProperties{
        private final MyISTER ister;
        public IRProperty(MyISTER ister){
            this.ister = ister;
        }

        @Override
        public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
            return ister;
        }
    }

}
