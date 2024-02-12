package com.dfdyz.epicacg.mixins;

import com.dfdyz.epicacg.config.ClientConfig;
import com.dfdyz.epicacg.utils.GlobalVal;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class)
public abstract class MixinLivingEntity {
    @Inject(at = @At("HEAD"),method = "makePoofParticles", cancellable = true)
    private void MixinPoof(CallbackInfo callbackInfo){
        if(ClientConfig.cfg.EnableDeathParticle)
            callbackInfo.cancel();
    }
}
