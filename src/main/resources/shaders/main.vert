#version 460 core
layout (location = 0) in vec3 iPos;
layout (location = 1) in vec2 iAtlasPosition;
layout (location = 2) in mat4 iTransform;  // Uses locations [2-5]

uniform mat4 uProjection;
uniform vec2 uTextureSize;
uniform int uTexturePadding;
uniform vec2 uSpriteSize;

out vec2 oUV;
out vec3 oFragPos;

void main() {
    vec4 worldPos = iTransform * vec4(iPos, 1.0);
    oFragPos = worldPos.xyz;

    // Select UV based on atlas position
    float xBleeding = 1.0 / uTextureSize.x;
    float yBleeding = 1.0 / uTextureSize.y;

    float xInternalPadding = uTexturePadding * iAtlasPosition.x;
    float yInternalPadding = uTexturePadding * iAtlasPosition.y;

    float u0 = ((iAtlasPosition.x * uSpriteSize.x) + uTexturePadding + xInternalPadding) / uTextureSize.x;
    float v0 = ((iAtlasPosition.y * uSpriteSize.y) + uTexturePadding + yInternalPadding) / uTextureSize.y;
    float u1 = u0 + (uSpriteSize.x / uTextureSize.x);
    float v1 = v0 + (uSpriteSize.y / uTextureSize.y);
    switch (gl_VertexID % 4) {
        case 0: oUV = vec2(u0 + xBleeding, v0 + yBleeding); break;
        case 1: oUV = vec2(u0 + xBleeding, v1 - yBleeding); break;
        case 2: oUV = vec2(u1 - xBleeding, v0 + yBleeding); break;
        case 3: oUV = vec2(u1 - xBleeding, v1 - yBleeding); break;
    }

    gl_Position = uProjection * worldPos;
}