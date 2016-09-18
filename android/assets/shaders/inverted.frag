#ifdef GL_ES
    precision mediump float;
#endif

uniform sampler2D u_sampler2D;

varying vec4 v_color;
varying vec2 v_texCoord0;
varying vec2 v_fragCoord;


void main() {
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;
    color.rgb = 1.0 - color.rgb;
    gl_FragColor = color;
}