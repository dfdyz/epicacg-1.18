#version 150

uniform sampler2D DiffuseSampler; //up
uniform sampler2D DiffuseSampler2; //down

uniform float UpAlpha;

in vec2 texCoord;
out vec4 fragColor;

void main(){
    vec4 down = texture(DiffuseSampler2, texCoord);
    vec4 up = texture(DiffuseSampler, texCoord);

    fragColor = vec4(
        up.rgb * UpAlpha + down.rgb * (1 - UpAlpha)
    , down.a > 0 ? down.a : UpAlpha * up.a);
    //fragColor = vec4(cut.x > 0.1 ? vec3(1,1,1) : col.rgb,1);
}
