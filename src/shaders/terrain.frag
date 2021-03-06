#version 330

uniform vec4 color;
uniform sampler2D texture_sampler;
uniform sampler2D bloom_sampler;

in vec2 texCoordsConst;
in vec2 texCoordsInterp;
in float fragOcclusion;
in float fog;

layout (location = 0) out vec4 finalColor;
layout (location = 1) out vec4 brightColor;

void main() {
    vec2 texBlockSize = 16. / textureSize(texture_sampler, 0);
    vec2 finalCoords = texCoordsConst + clamp(texCoordsInterp, vec2(.001, .001), texBlockSize - .001);
    vec4 textureColor = pow(texture(texture_sampler, finalCoords), vec4(2.2));

    //fragColor = textureColor * vec4(fragOcclusion, fragOcclusion, fragOcclusion, 1);
    //float maxDist = 2000;
    //float fog = pow(.01, pow(length(gViewSpace) / maxDist, 2));
    vec4 preFogColor = pow(color * vec4(vec3(mix(1, fragOcclusion, fog)), 1) * textureColor, vec4(1/2.2));
    finalColor = mix(vec4(.4, .7, 1., 1.), preFogColor, fog);
    brightColor = texture(bloom_sampler, finalCoords);
}