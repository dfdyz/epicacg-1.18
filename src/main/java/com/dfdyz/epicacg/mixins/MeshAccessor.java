package com.dfdyz.epicacg.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.api.client.model.Mesh;
import yesman.epicfight.api.client.model.ModelPart;

import java.util.Map;

@Mixin(value = Mesh.class, remap = false)
public interface MeshAccessor {

    @Accessor("positions")
    float[] getPositions();

    @Accessor("uvs")
    float[] getUvs();

    @Accessor("normals")
    float[] getNormals();

    @Accessor("totalVertices")
    int getTotalVertices();

    @Accessor("parts")
    Map<String, ModelPart<?>> getParts();
}
