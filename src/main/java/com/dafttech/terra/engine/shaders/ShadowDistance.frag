#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoord;
varying vec2 v_blurTexCoords[14];

uniform sampler2D u_texture;
uniform vec3 u_lightCoord;

void main() {    
	gl_FragColor = vec4(distance(gl_FragCoord, vec3(0,0,0)), 0, 0, 1);
}