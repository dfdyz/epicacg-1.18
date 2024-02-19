package com.dfdyz.epicacg.event;


import com.dfdyz.epicacg.EpicACG;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = EpicACG.MODID, value = Dist.CLIENT)
public class RenderEvents {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void OnLevelRender(RenderLevelStageEvent event){
        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES){
            //PostParticlePipelines.close();
        }
        //else if(event.getStage() == RenderLevelStageEvent.Stage.OUTLINE)
    }



}
