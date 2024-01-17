package com.dfdyz.epicacg.event;


import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.camera.CamAnim;
import com.dfdyz.epicacg.utils.MathUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = EpicACG.MODID, value = Dist.CLIENT)
public class CameraEvents {
    private static float yawLock = 0f;
    private static Vec3 posLock = new Vec3(0,0,0);
    public static CamAnim currentAnim;
    private static int tick = 0;
    private static int linkTick = 0;
    private static int maxLinkTick = 3;
    private static boolean isEnd = true;
    private static boolean linking = false;
    private static LivingEntity orginal;
    private static final Vec3 Vec3UP = new Vec3(0,1f,0);
    private static float fovO = 0;
    private static boolean isLockPos = false;

    private static CamAnim.Pose pose_;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void TransformCam(EntityViewRenderEvent.CameraSetup event){
        if (!(!isEnd || linking) || currentAnim == null) {
            return;
        }

        //System.out.println(isEnd + "   " + linking + " " + linkTick);

        if (orginal == null){
            isEnd = true;
            linking = false;
            return;
        }
        else {
            if(orginal.isRemoved()){
                isEnd = true;
                linking = false;
                return;
            }
        }

        Camera camera = event.getCamera();
        double partialTicks = event.getPartialTicks();

        CamAnim.Pose pose;
        if(linking){
            pose = pose_;
            float t = (linkTick + (float) partialTicks)/maxLinkTick;
            Vec3 Coord = orginal.getPosition((float) partialTicks);
            Vec3 targetPos = camera.getPosition();
            Vec3 lastFramePos = (pose.pos.yRot((float) Math.toRadians(-yawLock-90f)))
                    .add(isLockPos ? posLock : Coord);

            Vec3 targetRelate = targetPos.subtract(Coord);
            Vec3 lastRelate = lastFramePos.subtract(Coord);

            targetRelate = MathUtils.ToCylindricalCoordinate(targetRelate);
            lastRelate = MathUtils.ToCylindricalCoordinate(lastRelate);

            Vec3 camPos = MathUtils.LerpMinCylindrical(lastRelate,targetRelate,t);
            camPos = MathUtils.ToCartesianCoordinates(camPos).add(Coord);


            //camPos = CamAnim.Pose.lerpVec3(lastFramePos, targetPos, t);

            float tmp = event.getYaw() - (yawLock-pose.rotY);
            tmp = tmp%360f;

            if(tmp > 0){
                tmp -= 360;
                tmp = Math.abs(tmp) > tmp+360f ? tmp+360 : tmp;
            }

            float _rot_y =  yawLock-pose.rotY + tmp*t;
            float _rot_x = MathUtils.lerpBetween(pose.rotX, event.getPitch(), t);

            camera.setRotation(_rot_y, _rot_x);
            camera.setPosition(camPos.x,camPos.y,camPos.z);
            Minecraft.getInstance().options.fov = MathUtils.lerpBetween(pose.fov,fovO,t);

            //camera.
            //event.move
            event.setYaw(_rot_y);
            event.setPitch(_rot_x);
        }
        else {
            pose = currentAnim.getPose((tick + (float) partialTicks) / 20f);
            pose_ = pose;

            //Vec3 curPos = camera.getPosition();

            Vec3 camPos = (pose.pos.yRot((float) Math.toRadians(-yawLock-90f))).add(isLockPos ? posLock : orginal.getPosition((float) partialTicks));

            //Vec3 p = camPos.subtract(curPos);

            //System.out.println(pose);

            //cameraAccessor.invokeMove(p.x,p.y,p.z);
            camera.setRotation(yawLock-pose.rotY, pose.rotX);
            camera.setPosition(camPos.x,camPos.y,camPos.z);
            Minecraft.getInstance().options.fov = pose.fov;
            event.setYaw(yawLock-pose.rotY);
            event.setPitch(pose.rotX);
            //event.move
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void Tick(TickEvent.ClientTickEvent event){
        if(event.phase == TickEvent.Phase.START){
            if (!isEnd) tick++;
            if (linking) linkTick++;
        }
        else return;

        if(!isEnd && currentAnim != null && tick/20f >= currentAnim.getMaxTime()){
            isEnd = true;
            linking = true;
            tick = 0;
            linkTick = 0;
        }

        if(linking && currentAnim != null && linkTick >= maxLinkTick) {
            isEnd = true;
            linking = false;
            linkTick = 0;
            tick = 0;
            Minecraft.getInstance().options.fov = fovO;
        }
    }
    public static void SetAnim(CamAnim anim, LivingEntity org, boolean lockOrgPos){
        if(org instanceof Player){
            if (!((Player) org).isLocalPlayer()) return;
        }
        else {
            return;
        }

        if (!isEnd || linking)
            Minecraft.getInstance().options.fov = fovO;

        orginal = org;
        yawLock = org.getViewYRot(0);
        posLock = org.position();
        linking = false;
        isEnd = false;
        tick = 0;
        linkTick = 0;
        maxLinkTick = 8;
        currentAnim = anim;
        isLockPos = lockOrgPos;
        fovO = (float) Minecraft.getInstance().options.fov;
    }


}
