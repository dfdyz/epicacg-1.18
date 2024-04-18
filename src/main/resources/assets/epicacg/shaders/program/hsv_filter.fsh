#version 150

#define _PI_ 3.14159265359

uniform sampler2D DiffuseSampler;
uniform vec2 Hue_W;

in vec2 texCoord;
out vec4 fragColor;

vec3 rgb2hsv(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

float F(float x){
    float a = Hue_W.x;
    float b = min(Hue_W.y, 0.999f);
    return max(0.f, (cos(2*_PI_*(x - a)) - b) / (1 - b));
}

void main(){
    vec4 org = texture(DiffuseSampler, texCoord);
    vec3 hsv = rgb2hsv(org.rgb);

    hsv.y *= F(hsv.x);
    org.rgb = hsv2rgb(hsv);

    fragColor = org;
}
