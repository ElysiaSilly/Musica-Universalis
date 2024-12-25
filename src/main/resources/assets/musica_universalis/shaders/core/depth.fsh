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

uniform float nearPlaneDistance;
uniform float farPlaneDistance;

uniform int Seed;
uniform int Resolution;

uniform float GameTime;

in vec2 texCoord0;
in vec4 vertexColor;
in vec4 texProj0;

out vec4 fragColor;


float linearize_depth(float d) {
    float z_n = 2.0 * d - 1.0;
    float z_eye = 2.0 * nearPlaneDistance * farPlaneDistance / (farPlaneDistance + nearPlaneDistance - z_n * (farPlaneDistance - nearPlaneDistance));
    return (z_eye - nearPlaneDistance) / (farPlaneDistance - nearPlaneDistance);
}

float sdBox(vec2 p, vec2 b) {
    vec2 d = abs(p)-b;
    return length(max(d,0.0)) + min(max(d.x,d.y),0.0);
}

void main() {
    vec2 uv = texCoord0;

    vec4 texColor = vec4(0.0125);
    vec4 edgeColor = vec4(0.5);

    vec2 screenUV = gl_FragCoord.xy / ScreenSize;

    float sceneDepth = linearize_depth(texture(DepthBuffer, screenUV).r);
    float pixelDepth = linearize_depth(gl_FragCoord.z);
    float difference = abs(pixelDepth - sceneDepth);

    //float difference = sceneDepth;

    float threshold = 0.0005;
    float brightness = 0;
    if (difference > 0) {
        brightness = 1. - (smoothstep(0.00001, threshold, difference) + (1.0 - smoothstep(0.0, 0.00001, difference)));
    }
    float edge = sdBox(uv - vec2(0.5, 0.5), vec2(0.475));
    edge = smoothstep(0.0, 0.025, edge);
    brightness = max(brightness, edge);

    //float scanlineDomain = fract(uv.y * 25.0);
    //float scan = (smoothstep(0.4, 0.5, scanlineDomain) - smoothstep(0.5, 0.6, scanlineDomain)) * 0.75;
    //brightness = max(brightness, scan);

    vec4 color = mix(texColor, edgeColor, pow(brightness, 4));
    //vec4 color = transformColor(mix(texColor, edgeColor, pow(brightness, 3)), LumiTransparency);
    //color.a = 1;
    fragColor = color; //applyFog(color, FogStart, FogEnd, FogColor, vertexDistance);

}