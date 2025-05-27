#version 460 core
#define MAX_LIGHTS __MAX_LIGHTS__

in vec2 oUV;
in vec3 oFragPos;
out vec4 oColor;

struct Light {
    vec3 position;
    vec3 color;
    float intensity;
    float constant;
    float linear;
    float quadratic;
};

uniform float uAmbientIntensity;
uniform vec3 uAmbientColor;
uniform int uLightCount;
layout(std430, binding = 0) buffer uLights {
    Light lights[MAX_LIGHTS];
};

uniform sampler2D uTexture0;

uniform bool uTakingDamage;

vec3 calculateLight(Light light, vec3 fragPos);

void main() {
    vec4 textureColor = texture(uTexture0, oUV);
    if(textureColor.a < 0.01) discard;

    // Ambient
    vec3 ambient = uAmbientColor * uAmbientIntensity;

    // Lights
    vec3 lightSum = vec3(0.0);
    for(int i = 0; i < uLightCount; i++) {
        lightSum += calculateLight(lights[i], oFragPos);
    }

    vec3 totalLight = ambient + lightSum;

    if(uTakingDamage)
        textureColor.r = 0.8f;

    oColor = vec4(textureColor.rgb * totalLight, textureColor.a);
}

vec3 calculateLight(Light light, vec3 fragPos) {
    if(fragPos.z > light.position.z) return vec3(0);

    float distance = length(light.position - fragPos);
    float attenuation = 1.0 / (
        light.constant +
        (light.linear * distance) +
        (light.quadratic * distance * distance)
    );
    return light.color * light.intensity * attenuation;
}