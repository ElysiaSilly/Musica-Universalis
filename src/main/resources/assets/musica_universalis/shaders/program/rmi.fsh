#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

uniform float InverseAmount;

out vec4 fragColor;

void main(){
    vec4 diffuseColor = texture(DiffuseSampler, texCoord);

    vec4 colour = vec4(diffuseColor.x * diffuseColor.z, diffuseColor.y * diffuseColor.z, diffuseColor.z * diffuseColor.z , 1);
    vec4 invertColor = 1.0 - diffuseColor;

    float r = diffuseColor.x;
    float g = diffuseColor.y ;
    float b = diffuseColor.z;

    float threshold = .8;

    float rr = diffuseColor.x * 0.299;
    float gg = diffuseColor.y * 0.587;
    float bb = diffuseColor.z * 0.114;

    if(r > threshold) {
        rr = r * 2;
    } else {
        rr = r * .5;
    }
    if(g > threshold) {
        gg = g * 2;
    } else {
        gg = g * .5;
    }
    if(b > threshold) {
        bb = b * 2;
    } else {
        bb = b * .5;
    }

    float gray = rr + gg + bb;
    threshold = .55;


    if(r > threshold) {
        r = r * 4;
    }
    if(g > threshold) {
        g = g * 4;
    }
    if(b > threshold) {
        b = b * 4;
    }

    diffuseColor = vec4(gray / 3 + (r * rr / 3), gray / 3 + (g * gg / 3), gray / 3 + (b * bb / 3), 1);

    fragColor = diffuseColor;
}
