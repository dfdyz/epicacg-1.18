package com.dfdyz.epicacg.client.particle.GenshinImpact;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.dfdyz.epicacg.client.render.pipeline.PostParticlePipelines;
import com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType;
import com.dfdyz.epicacg.registry.Particles;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.dfdyz.epicacg.client.render.EpicACGRenderType.GS_BOW_LANDONG_PARTICLE_TEX;

public class GenShinBowLandingParticle extends TextureSheetParticle {
    //protected final int lifeTick;
    protected GenShinBowLandingParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, int lifeTick) {
        super(level, x, y, z, xd, yd, zd);
        this.x = x;
        this.y = y+0.05f;
        this.z = z;
        this.rCol = (float) xd;
        this.gCol = (float) yd;
        this.bCol = (float) zd;
        this.lifetime = lifeTick;

        level.addParticle(Particles.GENSHIN_BOW_LANDING2.get(), this.x,this.y,this.z+0.3,30,0,0);
        level.addParticle(Particles.GENSHIN_BOW_LANDING2.get(), this.x+0.5,this.y,this.z,20,90,0);
        level.addParticle(Particles.GENSHIN_BOW_LANDING2.get(), this.x-0.35,this.y,this.z-0.35,25,-135,0);

        for (int i = 0; i < 9; i++) {
            double offd = random.nextDouble(0,2.5);
            double offr = random.nextDouble(0,360);
            int offt = random.nextInt(2,8);
            level.addParticle(Particles.GENSHIN_BOW_LANDING3.get(),this.x+Math.cos(offr)*offd, this.y, this.z-Math.sin(offr)*offd, offt,0,0);
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return EpicACGRenderType.GENSHIN_BOW_LANDING;
    }

    private Vector3f[] avector3f = new Vector3f[]{new Vector3f(-3.0F, -3.0F, 0.0F), new Vector3f(-3.0F, 3.0F, 0.0F), new Vector3f(3.0F, 3.0F, 0.0F), new Vector3f(3.0F, -3.0F, 0.0F)};

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float tick) {
        if(!PostParticlePipelines.isActive()) return;
        EpicACGRenderType.GENSHIN_BOW_LANDING.callPipeline();
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

        int t = Math.min(9,(int) Math.floor((age + tick) /lifetime * 10));

        float u0 = (t%4) * 0.25f;
        float u1 = ((t%4)+1) * 0.25f;

        float v0 = (t/4) * 0.25f;
        float v1 = (t/4 + 1) * 0.25f;

        //System.out.println(t);

        int light = this.getLightColor(tick);

        vertexConsumer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u1, v1).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u1, v0).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u0, v0).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u0, v1).uv2(light).endVertex();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new GenShinBowLandingParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed,3);
        }
    }
}
