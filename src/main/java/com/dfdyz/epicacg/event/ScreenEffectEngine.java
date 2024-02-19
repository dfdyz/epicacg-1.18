package com.dfdyz.epicacg.event;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.client.screeneffect.ScreenEffect;
import com.dfdyz.epicacg.config.ClientConfig;
import com.dfdyz.epicacg.utils.GlobalVal;
import com.google.common.collect.Sets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Set;

import static net.minecraftforge.client.event.RenderLevelStageEvent.Stage.AFTER_PARTICLES;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = EpicACG.MODID, value = Dist.CLIENT)
public class ScreenEffectEngine {
    static Set<ScreenEffect> effects = Sets.newConcurrentHashSet();

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void OnRender(RenderLevelStageEvent event){
        if(event.getStage() == AFTER_PARTICLES){
            effects.forEach((e) -> {
                PostEffectPipelines.PostEffectQueue.add(e.getPipeline());
            });
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderWorldLast(TickEvent.RenderTickEvent event) {
        effects.forEach((e) -> {
            e.tick();
        });
        effects.removeIf((e) -> e.shouldRemoved());
    }

    public static void PushScreenEffect(ScreenEffect screenEffect){
        if(effects.contains(screenEffect)){
            effects.remove(screenEffect);
        }
        effects.add(screenEffect);
    }

}
