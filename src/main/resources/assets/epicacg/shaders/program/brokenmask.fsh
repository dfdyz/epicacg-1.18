#version 150

#define _PI_ 3.14159265359

uniform float Time;

in vec2 texCoord;
//in vec3 VNormal;
out vec4 fragColor;

void main(){
    float depth = pow(gl_FragCoord.z, exp(1.0));
    //float depth = abs(dot(VNormal, vec3(0.0,0.0,1.0)));

    fragColor = vec4(depth,depth,depth,1.0);
}
