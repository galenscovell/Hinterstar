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


void main() {
    vec2 uv = gl_FragCoord.xy / u_resolution.xy;
    vec2 ripple = sin(uv.y * 5.0 + u_time) / 150.0;
    vec3 color = texture2D(u_texture, v_texCoords + ripple).rgb;
    gl_FragColor = vec4(color, 1.0);
}