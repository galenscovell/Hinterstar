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

const float speed = 1.0;

// the amount of shearing (shifting of a single column or row)
// 1.0 = entire screen height offset (to both sides, meaning it's 2.0 in total)
#define xDistMag 0.00125
#define yDistMag 0.00125

// cycle multiplier for a given screen height
// 2*PI = you see a complete sine wave from top..bottom
#define xSineCycles 6.126
#define ySineCycles 6.126


void main() {
    vec2 uv = gl_FragCoord.xy / u_resolution.xy;

    // the value for the sine has 2 inputs:
    // 1. the time, so that it animates.
    // 2. the y-row, so that ALL scanlines do not distort equally.
    float time = u_time * speed;
    float xAngle = time + uv.y * ySineCycles;
    float yAngle = time + uv.x * xSineCycles;

    vec2 distortOffset = vec2(sin(xAngle), sin(yAngle)) * vec2(xDistMag, yDistMag);

    gl_FragColor = texture2D(u_texture, v_texCoords + distortOffset);
}