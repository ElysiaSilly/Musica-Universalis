#version 150

//#moj_import <minecraft:matrix.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;
uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;
uniform sampler2D MainSampler;
uniform sampler2D InSampler;
uniform sampler2D DepthBuffer;

uniform vec4 ColorModulator;
uniform vec2 ScreenSize;
uniform vec3 Colour;
uniform int seed;

uniform float GameTime;

in vec2 texCoord0;
in vec4 vertexColor;
in vec4 texProj0;

out vec4 fragColor;

vec4 rotate2d(float angle){
    return vec4(cos(angle),-sin(angle),
    sin(angle),cos(angle));
}

float variation(vec2 v1, vec2 v2, float strength, float speed) {
    return sin(
        dot(normalize(v1), normalize(v2)) * strength + (GameTime * 300) * speed * (seed * 0.00000001)
    ) / 100.0;
}

vec3 paintCircle (vec2 uv, vec2 center, float rad, float width) {

    vec2 diff = center-uv;
    float len = length(diff);

    float val = seed * 0.00000001;

    len += variation(diff, vec2(0.0, 1.0), 5.0, 2.0) * val; // 5
    len -= variation(diff, vec2(1.0, 0.0), 5.0, 2.0) * val;

    float circle = smoothstep(rad-width, rad, len) - smoothstep(rad, rad+width, len);
    return vec3(circle);
}

void main() {

    //vec2 uv = texCoord0.xy;
    // Normalized coordinates : ranges from 0.0 to 1.0 (??)
    int res = 48;
    vec2 uv = floor(texCoord0.xy * res) / res;

    uv = uv - 0.5;
    // shifts 0.0 to be centered, but now coordinates range from -0.5 to 0.5
    uv = uv * 2;
    // coordinates now range from -1 to 1


    vec3 color;
    float radius = 0.8;
    vec2 center = vec2(0);

    //paint color circle
    //for(int i = -2; i < 2; i++) {

        //radius += i * 0.1;

        color = paintCircle(uv, center, radius, 0.1);

        //color with gradient
        vec2 v = rotate2d(GameTime * 300).xy * uv;
        color *= vec3(v.x, v.y, (seed * 0.00001)-v.y*v.x);

        //paint white circle
        color += paintCircle(uv, center, radius, 0.03);
    //}

    fragColor = vec4(color, 1.0);
}