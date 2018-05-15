#version 330 core
layout (location = 0) in vec2 aPos;
//layout (location = 1) in vec3 vertexColor;

out vec3 fragmentColor;

uniform mat4 projection;
uniform vec3 vertexColor;

void main()
{
	fragmentColor = vertexColor;
    gl_Position = projection * vec4(aPos.x, aPos.y, 0.0, 1.0);
}