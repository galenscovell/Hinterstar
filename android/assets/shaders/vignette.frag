#ifdef GL_ES
    precision mediump float;
#endif

uniform sampler2D u_sampler2D;
uniform vec2 u_resolution;

varying vec4 v_color;
varying vec2 v_texCoord0;

const float outerRadius = 0.65, innerRadius = 0.4, intensity = 0.6;


void main() {
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;
    vec2 uv = gl_FragCoord.xy / u_resolution.xy - 0.5;
    float len = length(uv);
    float vignette = smoothstep(outerRadius, innerRadius, len);
    color.rgb = mix(color.rgb, color.rgb * vignette, intensity);

    gl_FragColor = color;
}