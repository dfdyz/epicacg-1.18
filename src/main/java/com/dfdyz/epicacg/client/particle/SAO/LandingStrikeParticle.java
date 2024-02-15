package com.dfdyz.epicacg.client.particle.SAO;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.dfdyz.epicacg.client.render.pipeline.PostParticlePipelines;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import static com.dfdyz.epicacg.utils.RenderUtils.GetTextures;

public class LandingStrikeParticle extends Particle {

    public LandingStrikeParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
        lifetime = 8;
    }

    public LandingStrikeParticle(ClientLevel level, Vec3 pos) {
        this(level, pos.x, pos.y, pos.z);
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float pt) {
        if(!PostParticlePipelines.isActive()) return;
        EpicACGRenderType.getBloomRenderTypeByTexture(textures).callPipeline();

        Vec3 vec3 = camera.getPosition();
        //Quaternion quaternion = camera.rotation();

        float f = (float)(this.x - vec3.x());
        float f1 = (float)(this.y - vec3.y());
        float f2 = (float)(this.z - vec3.z());

        //Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
        //vector3f1.transform(quaternion);

        Vector3f[] avector3f = new Vector3f[]{
                new Vector3f(-1F, 0, -1F),
                new Vector3f(-1F, 0F, 1F),
                new Vector3f(1F, 0F, 1F),
                new Vector3f(1F, 0F, -1F)};
        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            //vector3f.add(f, f1, f2);
            //vector3f.transform(quaternion);
            vector3f.mul(1.5f);
            vector3f.add(f, f1, f2);
            //vector3f.mul(0f);
            //vector3f.add(f, f1, f2);
        }


        int frame = Math.min(6, age);

        float u0 = 0;
        float u1 = 1;

        float per = 1.f / 7;

        float v0 = 1.f - frame * per;
        float v1 = 1.f - (frame + 1) * per;

        //System.out.println(t);
        int light = this.getLightColor(pt);

        vertexConsumer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u0, v1).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u0, v0).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u1, v0).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u1, v1).uv2(light).endVertex();
    }

    private static final ResourceLocation textures = GetTextures("particle/landing_strike");

    @Override
    public ParticleRenderType getRenderType() {
        return EpicACGRenderType.getBloomRenderTypeByTexture(textures);
    }
}
