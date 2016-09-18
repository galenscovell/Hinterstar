#ifdef GL_ES
    precision mediump float;
#endif

uniform sampler2D u_sampler2D;

varying vec4 v_color;
varying vec2 v_texCoord0;


void main() {
    gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * v_color;
}