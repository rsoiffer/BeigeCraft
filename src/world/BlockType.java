package world;

import util.vectors.Vec2d;
import util.vectors.Vec3d;

public enum BlockType {

    GRASS,
    DIRT,
    STONE,
    WOOD,
    LOG,
    LEAVES,
    SAND,
    SNOWY_GRASS,
    TUNDRA_GRASS,
    CACTUS,
    IRON_ORE;

    public static Vec2d spritesheetPos(BlockType bt, Vec3d dir) {
        switch (bt) {
            case GRASS:
                if (dir.z > 0) {
                    return new Vec2d(0, 0);
                } else if (dir.z < 0) {
                    return new Vec2d(16, 0);
                } else {
                    return new Vec2d(48, 0);
                }
            case DIRT:
                return new Vec2d(16, 0);
            case STONE:
                return new Vec2d(32, 0);
            case WOOD:
                return new Vec2d(64, 0);
            case LOG:
                if (dir.z == 0) {
                    return new Vec2d(0, 16);
                } else {
                    return new Vec2d(16, 16);
                }
            case LEAVES:
                return new Vec2d(32, 16);
            case SAND:
                return new Vec2d(48, 16);
            case SNOWY_GRASS:
                if (dir.z > 0) {
                    return new Vec2d(0, 32);
                } else if (dir.z < 0) {
                    return new Vec2d(16, 0);
                } else {
                    return new Vec2d(16, 32);
                }
            case TUNDRA_GRASS:
                if (dir.z > 0) {
                    return new Vec2d(32, 32);
                } else if (dir.z < 0) {
                    return new Vec2d(16, 0);
                } else {
                    return new Vec2d(48, 32);
                }
            case CACTUS:
                if (dir.z == 0) {
                    return new Vec2d(64, 16);
                } else {
                    return new Vec2d(64, 32);
                }
            case IRON_ORE:
                return new Vec2d(0, 48);
            default:
                throw new RuntimeException("Unknown BlockType: " + bt);
        }
    }
}
