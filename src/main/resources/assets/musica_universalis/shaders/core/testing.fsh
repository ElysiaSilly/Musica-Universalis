#version 150

#moj_import <minecraft:matrix.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;
uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;
uniform sampler2D MainSampler;
uniform sampler2D InSampler;
uniform sampler2D DepthBuffer;

uniform vec4 Test;

uniform vec4 ColorModulator;
uniform vec2 ScreenSize;
uniform vec3 Colour;

uniform float GameTime;

in vec2 texCoord0;
in vec4 vertexColor;
in vec4 texProj0;

out vec4 fragColor;

void main() {


    //vec3 color = vec3(1.0, 1.0, 1.0);

    vec2 uv = texCoord0.xy;
    //uv = uv - 0.5;
    uv = uv * 2;


    //float d = length(uv);

    //d -= time;
    //d = sin(d*8. + (GameTime * 1200))/8.;
    //d = abs(d);
    //d = smoothstep(0.1, 0.11, d) / 2;

    //if( d > 0.5 ) { color = vec3(0.0, 0.0, 0.0); };

    //fragColor = vec4(uv.x, uv.y, 1.0, 1.0);
    //fragColor = vec4(d, d, d, 1.0);

    //float d = length(uv);

    //float d = sin((GameTime * 1200))/10.;

        //vec4 test = Test;

    fragColor = vec4(uv.x / 2, uv.y / 2, 0.5, Test.x);
//fragColor = vec4(test.xy, 0.0, 1.0);
}
