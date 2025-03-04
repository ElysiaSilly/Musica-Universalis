#version 150

#moj_import <minecraft:projection.glsl>

in vec3 Position;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
in vec2 UV0;
out vec4 texProj0;
out vec2 texCoord0;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    texCoord0 = UV0;
    texProj0 = projection_from_position(gl_Position);
}
