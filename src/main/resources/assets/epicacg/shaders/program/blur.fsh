//copied from https://github.com/Low-Drag-MC/Shimmer/blob/1.18.2/Common/src/main/resources/assets/shimmer/shaders/program/seperable_blur.fsh
#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 OutSize;
uniform vec2 BlurDir;
uniform int Radius;
uniform float _alpha;
uniform float _bright;

in vec2 texCoord;
out vec4 fragColor;

float gaussianPdf(in float x, in float sigma) {
    return 0.39894 * exp( -0.5 * x * x/( sigma * sigma))/sigma;
}

void main(){
    vec2 invSize = 1.0 / OutSize;
    float fSigma = float(Radius);
    float weightSum = gaussianPdf(0.0, fSigma);
    vec4 diffuseSum = texture(DiffuseSampler, texCoord) * weightSum;
    for( int i = 1; i < Radius; i ++) {
        float x = float(i);
        float w = gaussianPdf(x, fSigma);
        vec2 uvOffset = BlurDir * invSize * x;
        vec4 sample1 = texture(DiffuseSampler, texCoord + uvOffset);
        vec4 sample2 = texture(DiffuseSampler, texCoord - uvOffset);
        diffuseSum += (sample1 + sample2) * w;
        weightSum += 2.0 * w;
    }
    fragColor = vec4(diffuseSum.rgb/weightSum * (1 + _bright), diffuseSum.a * _alpha);
}