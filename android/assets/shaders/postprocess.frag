#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform sampler2D u_texture;

varying highp vec4 v_color;
varying highp vec2 v_texCoord;

uniform vec2 resolution;
uniform float radius;

const float SOFTNESS = 0.3;
const float VIGNETTE_OPACITY = 0.9;

void main() {
	vec4 texColor = texture2D(u_texture, v_texCoord);
	
	// Scanlines
	vec4 intensity;
	if (fract(gl_FragCoord.y * (0.5 * 4.0 / 3.0)) > 0.5) {
		intensity = vec4(0);
	} else {
		intensity = smoothstep(0.2, 0.8, texColor) + normalize(texColor);
	}
	
	texColor = intensity * -0.25 + texColor * 1.1;
	
	// Vignette
	vec2 position = gl_FragCoord.xy / resolution.xy - vec2(0.5);
	float len = length(position);
	float vignette = smoothstep(radius, radius-SOFTNESS, len);
	texColor.rgb = mix(texColor.rgb, texColor.rgb * vignette, VIGNETTE_OPACITY);
	
    gl_FragColor = vec4(texColor.r, texColor.g, texColor.b, texColor.a);
}