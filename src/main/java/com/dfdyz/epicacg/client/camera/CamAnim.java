package com.dfdyz.epicacg.client.camera;

import com.google.common.base.Utf8;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

public class CamAnim {
    public final float linkTime;
    public final List<Key> keys_ = Lists.newArrayList();
    public final ResourceLocation resourceLocation;

    public CamAnim(float linkTime, ResourceLocation resourceLocation){
        this.resourceLocation = resourceLocation;
        this.linkTime = linkTime;
    }

    public CamAnim(float linkTime, String namespace, String path){
        this.resourceLocation = new ResourceLocation(namespace,path);
        this.linkTime = linkTime;
    }

    public void load(){
        try {
            keys_.clear();
            String str = "";
            Resource resource = Minecraft.getInstance().getResourceManager().getResource(resourceLocation);

            InputStreamReader isr = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);

            int c = 0;
            while((c = isr.read()) != -1){
                str += (char)c;
            }

            //System.out.println(str);

            Gson gson = new Gson();
            List<Key.KeyFrameInfo> kfis = gson.fromJson(str,new TypeToken<List<Key.KeyFrameInfo>>(){}.getType());
            if(kfis == null){
                System.out.println("[EpicAddon] CamAnimLoad Failed: "+resourceLocation.getNamespace()+":"+resourceLocation.getPath());
                return;
            }
            for(int i =0; i< kfis.size(); ++i){
                keys_.add(Key.ReadFromText(kfis.get(i)));
            }
            System.out.println("[EpicAddon] CamAnimLoad: "+resourceLocation.getNamespace()+":"+resourceLocation.getPath());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pose getPose(float t){
        //System.out.println("t = "+ t);
        if(t >= getMaxTime()) return keys_.get(keys_.size() - 1).pose;
        Pose p1 = null;
        Pose p2 = null;
        float t_ = 0f;
        float _t = 0f;
        for (int i = 0; i < keys_.size(); i++) {
            if(keys_.get(i).time <= t){
                p1 = keys_.get(i).pose;
                t_ = keys_.get(i).time;
            }
            else{
                p2 = keys_.get(i).pose;
                _t = keys_.get(i).time;
                break;
            }
        }

        return Pose.lerp(p1,p2,(t-t_)/(_t - t_));
    }

    public float getMaxTime(){
        if (keys_.size() == 0) return 0;
        return keys_.get(keys_.size() - 1).time;
    }


    public static class Pose{
        public final Vec3 pos;
        public final float rotY;
        public final float rotX;
        public final float fov;
        public Pose(Vec3 pos, float rotX, float rotY, float fov){
            this.pos = pos;
            this.rotY = rotY;
            this.rotX = rotX;
            this.fov = fov;
        }

        public static Pose lerp(Pose p1, Pose p2, float t){
            Vec3 p = lerpVec3(p1.pos,p2.pos,t);
            float _rotY = p1.rotY*(1-t) + p2.rotY*t;
            float _rotX = p1.rotX*(1-t) + p2.rotX*t;
            float fov = p1.fov*(1-t) + p2.fov*t;
            return new Pose(p, _rotX, _rotY, fov);
        }

        public static Vec3 lerpVec3(Vec3 v1, Vec3 v2, float t){
            double x = (v1.x*(1-t) + v2.x*t);
            double y = (v1.y*(1-t) + v2.y*t);
            double z = (v1.z*(1-t) + v2.z*t);
            return new Vec3(x,y,z);
        }

        @Override
        public String toString() {
            return "Pose{" +
                    "pos=" + pos +
                    ", rotY=" + rotY +
                    ", rotX=" + rotX +
                    ", fov=" + fov +
                    '}';
        }
    }

    public static class Key{
        public final Pose pose;
        public final float time;

        public Key(Vec3 pos, float rotX, float rotY, float fov, float time){
            this.pose = new Pose(pos,rotX,rotY,fov);
            this.time = time;
        }

        static Comparator<Key> cmp = new Comparator<Key>(){
            @Override
            public int compare(Key o1, Key o2) {
                return o1.time - o2.time > 0 ? 1 : -1;
            }
        };

        public static Key ReadFromText(KeyFrameInfo kfi){


            return new Key(new Vec3(kfi.x,kfi.y,kfi.z), kfi.rx, kfi.ry, kfi.fov, kfi.t);
        }

        public static class KeyFrameInfo{
            public float x,y,z,rx,ry,fov,t;
        }
    }
}
