# StoneEngine #
StoneEngine - Open source 3D game engine written in Java and based on OpenGL(LWJGL library).

## Features: ##
1. **Core**:
  - Objects and component system
  - Keyboard and mouse input
2. **Rendering**:
  - Mesh rendering based on VBO
  - Shaders system
  - Textures
  - Materials:
    - Diffuse texture
    - Specular texture
    - Ambient occlussion
    - Normal map
  - Lights(Phong model):
    - Ambient
    - Directional
    - Point
    - Spot
  - Shadow maps
  - Adjustable camera
  - Render to texture
  - Post processing system, example effects:
    - Colors invertion
    - Greyscale
    - Kernel
  - Skyboxes, skybox reflections
  - HDR, tone mapping
3. **Components**:
  - First person controller based on keyboard and mouse input
  
## Future features: ##
- Dynamic lightmaps(lightmap is updated when the lighting changes or when any object changes; lightmap is simlified, so baking takes a few nanoseconds/miliseconds)
- Full lightmaps
- Basic audio system
- Physics
- Deferred lighting
- Cascaded Shadow Maps
- Particles
- Emissive texture
- Parallax mapping
- Bloom
- UI, Text rendering