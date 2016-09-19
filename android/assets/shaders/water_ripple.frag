#ifdef GL_ES
#define LOW lowp
#define MED mediump
#define HIGH highp
    precision mediump float;
#else
#define MED
#define LOW
#define HIGH
#endif

uniform sampler2D u_texture;
uniform vec2 u_resolution;
uniform float u_time;

varying vec4 v_color;
varying vec2 v_texCoords;

const int speed = 2, frequency = 25, amplitude = 1;


void main() {
    vec2 uv = gl_FragCoord.xy / u_resolution.xy - 0.5;

    vec2 ripple = vec2(
        sin((length(uv) * frequency) + (u_time * speed)),
        cos((length(uv) * frequency) + (u_time * speed))
    ) * (amplitude / 1400.0);

    gl_FragColor = v_color * vec4(texture2D(u_texture, v_texCoords + ripple));
}