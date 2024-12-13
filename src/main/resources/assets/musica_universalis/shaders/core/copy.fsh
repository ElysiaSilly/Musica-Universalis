#version 150

#moj_import <minecraft:matrix.glsl>

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

uniform float GameTime;

in vec2 texCoord0;
in vec4 vertexColor;
in vec4 texProj0;

out vec4 fragColor;


void main() {

    vec2 uv = texCoord0.xy;
    // Normalized coordinates : ranges from 0.0 to 1.0 (??)

    uv = uv - 0.5;
    // shifts 0.0 to be centered, but now coordinates range from -0.5 to 0.5
    uv = uv * 2;
    // coordinates now range from -1 to 1

    vec3 wave_color = vec3(0.0);
    vec3 final_color = vec3(1.0);
    vec3 bg_color = vec3(0.0);

    float wave_width = 0.001;
    //uv  = -1.0 + 2.0 * uv;
    //uv.y += 0.1;
    for(float i = 0.0; i < 2.0; i++) {
        uv.y += (0.07 * sin((uv.x / 2) + i/7.0 + GameTime * 600));
        wave_width = abs(1.0 / (100.0 * uv.y));
        wave_color += vec3(wave_width * 1.9, wave_width, wave_width * 1.5);
    }

    final_color = bg_color + wave_color;

    fragColor = vec4(final_color, 1.0);
}