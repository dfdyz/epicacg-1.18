package com.dfdyz.epicacg.models.armature;

import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;

import java.util.Map;

public class TrashBinMasterArmature extends Armature {

    public TrashBinMasterArmature(int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(jointNumber, rootJoint, jointMap);
    }
}
