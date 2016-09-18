#ifdef GL_ES
    precision mediump float;
#endif

uniform sampler2D u_sampler2D;
uniform vec2 u_resolution;

varying vec4 v_color;
varying vec2 v_texCoord0;


void main() {
    vec2 onePixel = vec2(1.0 / u_resolution);
    vec2 texCoord = v_texCoord0;
    vec3 color = vec3(0.5);

    color += texture2D(u_sampler2D, texCoord - onePixel).rgb * 5.0;
    color -= texture2D(u_sampler2D, texCoord + onePixel).rgb * 5.0;

    color.rgb = vec3(color.r + color.g + color.b) / 3.0;

    gl_FragColor = vec4(color, 1.0);
}