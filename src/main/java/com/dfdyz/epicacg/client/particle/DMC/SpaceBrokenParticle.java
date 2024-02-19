package com.dfdyz.epicacg.client.particle.DMC;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.registry.MyModels;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SpaceBrokenParticle extends Particle {

    final int layer;
    public SpaceBrokenParticle(ClientLevel level, double x, double y, double z, int lifetime, int layer) {
        super(level, x, y, z);
        hasPhysics = false;
        this.lifetime = lifetime;
        this.layer = layer;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float pt) {
        //float at = this.age+pt;
        if(!PostEffectPipelines.isActive()) return;
        if(layer == 0){
            EpicACGRenderType.SpaceBroken1.callPipeline();
        }
        else {
            EpicACGRenderType.SpaceBroken2.callPipeline();
        }

        Vec3 vec3 = camera.getPosition();


        float f = (float)(this.x - vec3.x());
        float f1 = (float)(this.y - vec3.y() + (layer == 0 ? 0.1f : 0.4f));
        float f2 = (float)(this.z - vec3.z());

        float u0 = 0;
        float u1 = 1;
        float v0 = 0;
        float v1 = 1;
        int light = RenderUtils.DefaultLightColor;

        float sss = layer == 0 ? 1.7f : 1.4f;

        Vector3f camNormal = camera.getLookVector().copy();
        camNormal.normalize();

        float camYaw = camera.getYRot() + (layer == 0 ? 0 : 90);
        camYaw = (((camYaw % 360) + 360) % 360) / 360f;

        camYaw = camYaw < 0.5 ? camYaw * 2 : - camYaw * 2 + 2;

        float camPitch = camera.getXRot();
        //System.out.println(camPitch);

        camPitch = camPitch / 90;
        camPitch = camPitch > 0 ? camPitch : -camPitch;

        Quaternion rot = layer == 0 ? Quaternion.ONE : Quaternion.fromXYZDegrees(new Vector3f(45, 90, 45));

        for(int index = 0; index < MyModels.SpaceBrokenModel.Face.size(); ++index) {
            OBJ_JSON.Triangle triangle = MyModels.SpaceBrokenModel.Face.get(index);
            Vector3f vertex1 = MyModels.SpaceBrokenModel.Positions.get(triangle.x-1).toBugJumpFormat();
            Vector3f vertex2 = MyModels.SpaceBrokenModel.Positions.get(triangle.y-1).toBugJumpFormat();
            Vector3f vertex3 = MyModels.SpaceBrokenModel.Positions.get(triangle.z-1).toBugJumpFormat();

            vertex1.transform(rot);

            vertex1.mul(sss);
            vertex2.mul(sss);
            vertex3.mul(sss);

            vertex1.add(f, f1, f2);
            vertex2.add(f, f1, f2);
            vertex3.add(f, f1, f2);

            // normal to color
            Vector3f col_normal = triangle.Normal.toBugJumpFormat();
            col_normal.transform(rot);
            col_normal.normalize();
            float offset = Math.abs(camNormal.dot(col_normal));

            //buffer.vertex(vertex1.x(), vertex1.y(), vertex1.z()).color(offset,camYaw,camPitch,1).uv(u1, v1).uv2(light).endVertex();
            buffer.vertex(vertex1.x(), vertex1.y(), vertex1.z()).color(offset,camYaw,camPitch,1).uv(u1, v0).uv2(light).endVertex();
            buffer.vertex(vertex2.x(), vertex2.y(), vertex2.z()).color(offset,camYaw,camPitch,1).uv(u0, v0).uv2(light).endVertex();
            buffer.vertex(vertex3.x(), vertex3.y(), vertex3.z()).color(offset,camYaw,camPitch,1).uv(u0, v1).uv2(light).endVertex();
        }

    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return EpicACGRenderType.SpaceBroken1;
    }

    public static class OBJ_JSON{
        public static class Triangle {
            public int x,y,z;
            public vec3f Normal;
            public void UpdateNormal(List<vec3f> pos){
                Vector3f p1 = pos.get(x-1).toBugJumpFormat();
                Vector3f p2 = pos.get(y-1).toBugJumpFormat();
                Vector3f p3 = pos.get(z-1).toBugJumpFormat();


                p1.sub(p2);  //v1
                p1.normalize();

                p2.sub(p3);  //v2
                p2.normalize();

                p1.cross(p2); //normal
                p1.normalize();

                Normal = new vec3f(p1.x(), p2.y(), p3.z());
            }
        }

        public static class vec3f {
            public float x,y,z;
            public vec3f(float x, float y, float z){
                this.x = x;
                this.y = y;
                this.z = z;
            }
            public Vector3f toBugJumpFormat(){
                return new Vector3f(x,y,z);
            }
        }

        public List<vec3f> Positions = new ArrayList<>();
        public List<Triangle> Face = new ArrayList<>();

        public static OBJ_JSON loadFromJson(ResourceLocation location){
            OBJ_JSON obj;
            try {
                String str = "";
                Resource resource = Minecraft.getInstance().getResourceManager().getResource(location);

                InputStreamReader isr = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);

                int c;
                while((c = isr.read()) != -1){
                    str += (char)c;
                }

                Gson gson = new Gson();
                obj = gson.fromJson(str,new TypeToken<OBJ_JSON>(){}.getType());

                for (int i = 0; i < obj.Face.size(); i++) {
                    obj.Face.get(i).UpdateNormal(obj.Positions);
                }

                //EpicAddon.LOGGER.info(gson.toJson(obj));

            }catch(IOException e) {
                throw new RuntimeException(e);
            }

            return obj;
        }
    }


}
