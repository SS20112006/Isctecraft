package jogo.voxel;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;

public abstract class VoxelBlockType {
    private final String name;
    private final float hardness;

    protected VoxelBlockType(String name, float hardness) {
        this.name = name;
        this.hardness=hardness;
    }

    public String getName() {
        return name;
    }

    /** Whether this block is physically solid (collides/occludes). */
    public boolean isSolid() { return true; }

    /**
     * Returns the Material for this block type. Override in subclasses for custom materials.
     */
    public abstract Material getMaterial(AssetManager assetManager);

    public float hardness(){return 1.0f;}

    /**
     * Returns the Material for this block type at a specific block position.
     * Default implementation ignores the position for backward compatibility.
     * Subclasses can override to use blockPos.
     */
    public Material getMaterial(AssetManager assetManager, jogo.framework.math.Vec3 blockPos) {
        return getMaterial(assetManager);
    }
}
