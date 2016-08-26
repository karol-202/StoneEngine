#version 330

layout (triangles) in;
layout (triangle_strip, max_vertices = 18) out;

uniform mat4 viewProjectionMatrices[6];

out vec4 pos;

void main()
{
    for(int face = 0; face < 6; face++)
    {
        gl_Layer = face;
        for(int i = 0; i < 3; i++)
        {
            pos = gl_in[i].gl_Position;
            gl_Position = viewProjectionMatrices[face] * pos;
            EmitVertex();
        }
        EndPrimitive();
    }
}