package com.dfdyz.epicacg.event;



import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.network.Client.C_RollSkillSelect;
import com.dfdyz.epicacg.network.Netmgr;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = EpicACG.MODID, value = Dist.CLIENT)
public class ControllerEvent {
    private static boolean isCtrlPressed = false;
    private static int roll_unit = 0;
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void mouseEvent(InputEvent.MouseScrollEvent event) {
        if (isCtrlPressed){
            double delta = event.getScrollDelta();
            if(delta > 0){
                Netmgr.sendToServer(new C_RollSkillSelect(-1));
            }
            else if(delta < 0){
                Netmgr.sendToServer(new C_RollSkillSelect(1));
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void keyboardEvent(InputEvent.KeyInputEvent event) {
        InputConstants.Key input = InputConstants.Type.KEYSYM.getOrCreate(event.getKey());

        int act = event.getAction();

        if(input == EpicAddonKeyMapping.SwitchSkill.getKey()){
            if (act == GLFW.GLFW_PRESS){
                isCtrlPressed = true;
            }
            else if(act == GLFW.GLFW_RELEASE){
                isCtrlPressed = false;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class EpicAddonKeyMapping{
        public static final KeyMapping SwitchSkill = new KeyMapping("key." + EpicACG.MODID + ".switch_skill", InputConstants.KEY_B, "key." + EpicACG.MODID + ".gui");
        public static void Reg(){
            ClientRegistry.registerKeyBinding(SwitchSkill);
        }
    }
}
