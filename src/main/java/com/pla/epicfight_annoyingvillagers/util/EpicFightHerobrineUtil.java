package com.pla.epicfight_annoyingvillagers.util;

import com.pla.annoyingvillagers.blockentity.*;
import com.pla.annoyingvillagers.clazz.HerobrineObsidianBlock;
import com.pla.annoyingvillagers.clazz.ProjectileBreakableBlocks;
import com.pla.annoyingvillagers.init.*;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.Vec3f;

public class EpicFightHerobrineUtil {
    public static void placeIfReplaceable(ServerLevel level, BlockPos pos, BlockState state, Entity ownerEntity) {
        if (!level.isLoaded(pos)) return;

        BlockState existingState = level.getBlockState(pos);
        if (!existingState.canBeReplaced()) {
            ProjectileBreakableBlocks rule = ProjectileBreakableBlocks.find(existingState);
            if (rule == null) return;
            boolean requiresTool = existingState.requiresCorrectToolForDrops();
            boolean destroyed = level.destroyBlock(pos, true, ownerEntity);
            if (!destroyed) return;
            if (requiresTool) {
                Item item = existingState.getBlock().asItem();
                if (item != Items.AIR) {
                    Block.popResource(level, pos, new ItemStack(item));
                }
            }
        }
        if (!level.getBlockState(pos).canBeReplaced()) return;

        level.setBlockAndUpdate(pos, state);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) return;

        if (blockEntity instanceof ObsidianBlockEntity obsidianBlockEntity) {
            obsidianBlockEntity.setOwner(ownerEntity.getUUID());
        } else if (blockEntity instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
            shadowObsidianBlockEntity.setOwner(ownerEntity.getUUID());
        } else if (blockEntity instanceof CryingObsidianBlockEntity cryingObsidianBlockEntity) {
            cryingObsidianBlockEntity.setOwner(ownerEntity.getUUID());
        } else if (blockEntity instanceof ShadowObsidianShortPillarBlockEntity shadowObsidianShortPillarBlockEntity) {
            shadowObsidianShortPillarBlockEntity.setOwner(ownerEntity.getUUID());
        } else if (blockEntity instanceof ShadowObsidianMiddlePillarBlockEntity shadowObsidianMiddlePillarBlockEntity) {
            shadowObsidianMiddlePillarBlockEntity.setOwner(ownerEntity.getUUID());
        } else if (blockEntity instanceof ShadowObsidianLongPillarBlockEntity shadowObsidianLongPillarBlockEntity) {
            shadowObsidianLongPillarBlockEntity.setOwner(ownerEntity.getUUID());
        }

        blockEntity.setChanged();
        level.sendBlockUpdated(pos, state, state, 3);
    }

    private static Basis basisFromEntity(Entity e) {
        Vec3 forward = e.getLookAngle().normalize();

        Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);
        Vec3 right = forward.cross(worldUp);
        if (right.lengthSqr() < 1.0e-6) {
            right = new Vec3(1.0, 0.0, 0.0);
        } else {
            right = right.normalize();
        }
        Vec3 up = right.cross(forward).normalize();
        return new Basis(forward, right, up);
    }

    private record Basis(Vec3 fwd, Vec3 right, Vec3 up) {}

    public static void spawnObsidianEyeLineStaggered(ServerLevel level, Entity entity, BlockState state, int tickGap) {
        if (level == null || entity == null) return;

        Basis b = basisFromEntity(entity);
        Vec3 eye = entity.getEyePosition(1.0F);

        BlockPos[] sequence = new BlockPos[1 + 6];
        sequence[0] = BlockPos.containing(eye.add(b.fwd().scale(1.0)).add(b.up().scale(-1.0)));
        for (int i = 1; i <= 6; i++) {
            sequence[i] = BlockPos.containing(eye.add(b.fwd().scale(i)));
        }

        for (int i = 0; i < sequence.length; i++) {
            final BlockPos pos = sequence[i];
            new DelayedTask(i * Math.max(1, tickGap)) {
                @Override public void run() {
                    placeIfReplaceable(level, pos, state, entity);
                }
            };
        }
    }

    private static final class Pattern2D {
        final int w, h;
        final int[][] cells;
        Pattern2D(int w, int h, int[][] cells) { this.w = w; this.h = h; this.cells = cells; }
        int centerX() { return w / 2; }
    }

    private static final Pattern2D[] OBSIDIAN_PATTERNS = new Pattern2D[] {
            new Pattern2D(1, 3, new int[][] { {0,0},{0,1},{0,2} }),
            new Pattern2D(2, 3, new int[][] { {0,0},{0,1},{0,2},{1,2} }),
            new Pattern2D(2, 3, new int[][] { {1,0},{1,1},{1,2},{0,1} }),
            new Pattern2D(3, 3, new int[][] { {0,0},{1,0},{2,0},{1,1},{1,2} }),
            new Pattern2D(3, 3, new int[][] { {0,2},{1,2},{2,2},{1,1},{1,0} }),
            new Pattern2D(3, 3, new int[][] { {1,1},{1,2},{1,0},{0,1},{2,1} }),
            new Pattern2D(3, 4, new int[][] { {1,0},{1,1},{1,2},{1,3},{0,2},{2,2} }),
            new Pattern2D(2, 2, new int[][] { {0,0},{1,0},{0,1},{1,1} }),
            new Pattern2D(3, 3, new int[][] { {0,0},{0,1},{1,1},{2,1},{0,2},{1,2} }),
            new Pattern2D(3, 3, new int[][] { {0,0},{1,0},{1,1},{1,2},{2,2} }),
            new Pattern2D(3, 2, new int[][] { {0,0},{1,0},{2,0},{0,1} }),
    };

    private static boolean hasGroundWithin(ServerLevel level, Entity e, int maxDown) {
        Vec3 start = new Vec3(e.getX(), e.getBoundingBox().minY + 1.0E-3D, e.getZ());
        Vec3 end = start.add(0.0D, -maxDown, 0.0D);

        BlockHitResult hit = level.clip(new ClipContext(
                start, end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                e
        ));
        return hit.getType() != HitResult.Type.MISS;
    }

    public static void spawnObsidianPatternAtBody(ServerLevel level, Entity entity, BlockState state) {
        if (level == null || entity == null) return;
        if (!hasGroundWithin(level, entity, 3)) return;

        int minY = level.getMinBuildHeight();
        int maxY = level.getMaxBuildHeight() - 1;

        BlockPos feet = BlockPos.containing(entity.getX(), entity.getBoundingBox().minY, entity.getZ());

        var rand = level.getRandom();
        Pattern2D pat = OBSIDIAN_PATTERNS[rand.nextInt(OBSIDIAN_PATTERNS.length)];

        Direction face = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
        boolean mirror = rand.nextBoolean();
        BlockPos origin = feet.relative(face);

        Direction side = mirror ? face.getCounterClockWise() : face.getClockWise();
        int cx = pat.centerX();

        for (int[] c : pat.cells) {
            int localX = c[0] - cx;
            int localY = c[1];

            int y = origin.getY() + localY;
            if (y < minY || y > maxY) continue;

            BlockPos p = origin.offset(
                    side.getStepX() * localX,
                    localY,
                    side.getStepZ() * localX
            );

            placeIfReplaceable(level, p, state, entity);
        }
    }

    public static void summonObsidianBlocksInfrontOf(ServerLevel level,
                                                     LivingEntity caster,
                                                     BlockState obsidianState,
                                                     int amount,
                                                     Joint joint) {
        if (level == null || caster == null) return;

        final Vec3[] lockedEye = { null };
        final Vec3[] lockedDir = { null };
        final int[] anchorY = { Integer.MIN_VALUE };

        for (int i = 1; i <= amount; i++) {
            final int forwardBlock = i + 1;

            new DelayedTask(i) {
                @Override public void run() {
                    if (!caster.isAlive()) return;
                    if (caster.level() != level) return;

                    if (lockedDir[0] == null) {
                        lockedEye[0] = caster.getEyePosition(1.0F);
                        lockedDir[0] = caster.getLookAngle().normalize();
                    }

                    Vec3 placeVec;

                    if (forwardBlock == 2) {
                        Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                caster, new Vec3f(0, 0, 0),
                                joint, 0.0F, 0.0F
                        );
                        if (jointVec == null) return;

                        placeVec = jointVec.add(lockedDir[0].scale(1.0D));
                        anchorY[0] = BlockPos.containing(placeVec).getY();
                    } else {
                        if (anchorY[0] == Integer.MIN_VALUE) return;

                        Vec3 target = lockedEye[0].add(lockedDir[0].scale(forwardBlock));
                        placeVec = new Vec3(target.x, anchorY[0] + 0.5D, target.z);
                    }

                    placeIfReplaceable(level, BlockPos.containing(placeVec), obsidianState, caster);
                }
            };
        }
    }

    public static void summonObsidianWall(ServerLevel level, LivingEntity caster, BlockState obsidianState) {
        if (level == null || caster == null) return;

        final Vec3 eye = caster.getEyePosition(1.0F);
        final Vec3 fwd = caster.getLookAngle().normalize();

        Vec3 left = new Vec3(fwd.z, 0.0D, -fwd.x);
        if (left.lengthSqr() < 1.0E-6D) left = new Vec3(1.0D, 0.0D, 0.0D);
        else left = left.normalize();

        final Vec3 up = fwd.cross(left).normalize();
        final BlockPos p1 = BlockPos.containing(
                eye.add(left.scale(-2)).add(up.scale(-1)).add(fwd.scale(3))
        );
        final BlockPos p2 = BlockPos.containing(
                eye.add(left.scale( 2)).add(up.scale( 2)).add(fwd.scale(3))
        );

        if (!caster.isAlive()) return;

        int minX = Math.min(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int minZ = Math.min(p1.getZ(), p2.getZ());
        int maxX = Math.max(p1.getX(), p2.getX());
        int maxY = Math.max(p1.getY(), p2.getY());
        int maxZ = Math.max(p1.getZ(), p2.getZ());

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    pos.set(x, y, z);
                    placeIfReplaceable(level, pos, obsidianState, caster);
                }
            }
        }
    }

    private static void placePillarWorldOffsets(ServerLevel level, Vec3 eye, int dx, int dz, BlockState state, LivingEntity caster) {
        for (int dy = -1; dy <= 1; dy++) {
            BlockPos pos = BlockPos.containing(eye.x + dx, eye.y + dy, eye.z + dz);
            placeIfReplaceable(level, pos, state, caster);
        }
    }

    private static void placeSingleWorldOffset(ServerLevel level, Vec3 eye, int dx, int dy, int dz, BlockState state, LivingEntity caster) {
        BlockPos pos = BlockPos.containing(eye.x + dx, eye.y + dy, eye.z + dz);
        placeIfReplaceable(level, pos, state, caster);
    }

    public static void summonObsidianCross(ServerLevel level, LivingEntity caster, BlockState obsidianState) {
        if (level == null || caster == null) return;

        new DelayedTask(2) {
            @Override public void run() {
                if (!caster.isAlive()) return;
                Vec3 eye = caster.getEyePosition(1.0F);

                placePillarWorldOffsets(level, eye, 0,  3, obsidianState, caster);
                placePillarWorldOffsets(level, eye, 0, -3, obsidianState, caster);

                placePillarWorldOffsets(level, eye,  3, 0, obsidianState, caster);
                placePillarWorldOffsets(level, eye, -3, 0, obsidianState, caster);
            }
        };

        new DelayedTask(4) {
            @Override public void run() {
                if (!caster.isAlive()) return;
                Vec3 eye = caster.getEyePosition(1.0F);

                placeSingleWorldOffset(level, eye, 0, 2,  3, obsidianState, caster);
                placeSingleWorldOffset(level, eye, 0, 2, -3, obsidianState, caster);
                placeSingleWorldOffset(level, eye, 3, 2,  0, obsidianState, caster);
                placeSingleWorldOffset(level, eye,-3, 2,  0, obsidianState, caster);
            }
        };

        new DelayedTask(6) {
            @Override public void run() {
                if (!caster.isAlive()) return;
                Vec3 eye = caster.getEyePosition(1.0F);

                int[] dist = {5, 7};
                for (int d : dist) {
                    placePillarWorldOffsets(level, eye, 0,  d, obsidianState, caster);
                    placePillarWorldOffsets(level, eye, 0, -d, obsidianState, caster);
                    placePillarWorldOffsets(level, eye,  d, 0, obsidianState, caster);
                    placePillarWorldOffsets(level, eye, -d, 0, obsidianState, caster);
                }
            }
        };

        new DelayedTask(8) {
            @Override public void run() {
                if (!caster.isAlive()) return;
                Vec3 eye = caster.getEyePosition(1.0F);

                int[] dists = {5, 7};
                for (int d : dists) {
                    placeSingleWorldOffset(level, eye, 0, 2,  d, obsidianState, caster);
                    placeSingleWorldOffset(level, eye, 0, 2, -d, obsidianState, caster);
                    placeSingleWorldOffset(level, eye,  d, 2, 0, obsidianState, caster);
                    placeSingleWorldOffset(level, eye, -d, 2, 0, obsidianState, caster);
                }
            }
        };
    }

    private static void placePillarWorldOffsetsHeight(ServerLevel level, Vec3 eye,
                                                      int dx, int dz,
                                                      int minDy, int maxDy,
                                                      BlockState state, LivingEntity caster) {
        for (int dy = minDy; dy <= maxDy; dy++) {
            BlockPos pos = BlockPos.containing(eye.x + dx, eye.y + dy, eye.z + dz);
            placeIfReplaceable(level, pos, state, caster);
        }
    }

    public static void summonObsidianSmallCross(ServerLevel level, LivingEntity caster, BlockState obsidianState) {
        if (level == null || caster == null) return;

        new DelayedTask(2) {
            @Override public void run() {
                if (!caster.isAlive()) return;
                if (caster.level() != level) return;

                Vec3 eye = caster.getEyePosition(1.0F);

                boolean isLongPillar = obsidianState.is(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get());
                int minDy = -1;
                int maxDy = isLongPillar ? -1 : 0;
                int d = 3;

                placePillarWorldOffsetsHeight(level, eye, 0,  d, minDy, maxDy, obsidianState, caster);
                placePillarWorldOffsetsHeight(level, eye, 0, -d, minDy, maxDy, obsidianState, caster);

                placePillarWorldOffsetsHeight(level, eye,  d, 0, minDy, maxDy, obsidianState, caster);
                placePillarWorldOffsetsHeight(level, eye, -d, 0, minDy, maxDy, obsidianState, caster);
            }
        };
    }

    public static void summonObsidianPillar(ServerLevel level, LivingEntity caster, BlockState obsidianState) {
        if (level == null || caster == null) return;

        final Vec3 eye = caster.getEyePosition(1.0F);
        final Vec3 fwd = caster.getLookAngle().normalize();

        Vec3 ahead = eye.add(fwd.scale(2.0D));
        Vec3 bodyLevelAhead = new Vec3(ahead.x, caster.getY(), ahead.z);

        final BlockPos base = BlockPos.containing(bodyLevelAhead).below(1);

        for (int delay = 1; delay <= 12; delay++) {
            final int yOffset = delay - 1;

            new DelayedTask(delay) {
                @Override public void run() {
                    if (!caster.isAlive()) return;

                    BlockPos pos = base.above(yOffset);
                    placeIfReplaceable(level, pos, obsidianState, caster);
                }
            };
        }
    }

    public static void summonShadowObsidianShortPillarShootToward(ServerLevel level, Entity ownerEntity, int maxDistance, Joint joint) {
        if (level == null || ownerEntity == null) return;

        BlockState baseState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR.get()
                .defaultBlockState()
                .setValue(HerobrineObsidianBlock.FROM_PLAYER, ownerEntity instanceof Player)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, ownerEntity.getDirection());

        summonPillarsTowardJoint(level, ownerEntity, baseState, Math.max(2, maxDistance), joint);
    }

    public static void summonShadowObsidianMiddlePillarShootToward(ServerLevel level, Entity ownerEntity, int maxDistance, Joint joint) {
        if (level == null || ownerEntity == null) return;

        BlockState baseState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get()
                .defaultBlockState()
                .setValue(HerobrineObsidianBlock.FROM_PLAYER, ownerEntity instanceof Player)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, ownerEntity.getDirection());
        summonPillarsTowardJoint(level, ownerEntity, baseState, maxDistance, joint);
    }

    private static void summonPillarsTowardJoint(ServerLevel level,
                                                 Entity ownerEntity,
                                                 BlockState blockState,
                                                 int maxDistance,
                                                 Joint joint) {
        final Vec3[] lockedDir = { null };
        final Vec3[] lockedJoint = { null };
        final Direction[] lockedFacing = { null };
        final int[] anchorY = { Integer.MIN_VALUE };

        for (int dist = 2; dist <= maxDistance + 1; dist++) {
            final int d = dist;

            new DelayedTask(d) {
                @Override public void run() {
                    if (!ownerEntity.isAlive()) return;
                    if (ownerEntity.level() != level) return;
                    if (lockedDir[0] == null) {
                        lockedDir[0] = ownerEntity.getLookAngle().normalize();
                        lockedFacing[0] = ownerEntity.getDirection();

                        lockedJoint[0] = EpicfightUtil.getJointWithTranslation(
                                ownerEntity, new Vec3f(0, 0, 0),
                                joint, 0.0F, 0.0F
                        );
                        if (lockedJoint[0] == null) return;
                    }

                    BlockState stateNow = blockState;
                    if (stateNow.hasProperty(BlockStateProperties.HORIZONTAL_FACING) && lockedFacing[0] != null) {
                        stateNow = stateNow.setValue(BlockStateProperties.HORIZONTAL_FACING, lockedFacing[0]);
                    }

                    Vec3 raw = lockedJoint[0].add(lockedDir[0].scale(d));

                    if (d == 2) {
                        anchorY[0] = BlockPos.containing(raw).getY();
                    } else if (anchorY[0] == Integer.MIN_VALUE) {
                        return;
                    }

                    Vec3 placeVec = (d == 2)
                            ? raw
                            : new Vec3(raw.x, anchorY[0] + 0.5D, raw.z);

                    placeIfReplaceable(level, BlockPos.containing(placeVec), stateNow, ownerEntity);
                }
            };
        }
    }

    public static void summonShadowObsidianLongPillarDefense(ServerLevel level, Entity ownerEntity) {
        if (level == null || ownerEntity == null) return;

        if (!ownerEntity.isAlive()) return;
        if (ownerEntity.level() != level) return;

        BlockState longPillarState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get()
                .defaultBlockState()
                .setValue(HerobrineObsidianBlock.FROM_PLAYER, ownerEntity instanceof Player)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, ownerEntity.getDirection());
        Vec3 origin = ownerEntity.getEyePosition(1.0F);
        Vec3 forward = ownerEntity.getLookAngle().normalize();
        Vec3 worldUp = new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 left = forward.cross(worldUp);
        if (left.lengthSqr() < 1.0E-6D) {
            Direction facing = ownerEntity.getDirection();
            Direction leftDir = facing.getCounterClockWise();
            left = new Vec3(leftDir.getStepX(), 0.0D, leftDir.getStepZ());
        } else {
            left = left.normalize();
        }

        Vec3 up = left.cross(forward).normalize();
        int[][] localOffsets = {
                { 0, -1, 2},
                {-1, -1, 2},
                { 1, -1, 2},
                {-2, -1, 2},
                { 2, -1, 2},

                { 0, -1, 3},
                {-1, -1, 3},
                { 1, -1, 3},
        };

        for (int[] o : localOffsets) {
            int dx = o[0];
            int dy = o[1];
            int dz = o[2];

            Vec3 target = origin
                    .add(left.scale(dx))
                    .add(up.scale(dy))
                    .add(forward.scale(dz));

            BlockPos pos = BlockPos.containing(target);
            if (level.getBlockState(pos).isAir()) {
                placeIfReplaceable(level, pos, longPillarState, ownerEntity);
            }
        }
    }

    public static void summonShadowObsidianLongPillarDefenseWide(ServerLevel level, Entity ownerEntity) {
        int startDistance = 2;
        int depth = 5;
        int maxHalfWidth = 4;
        int dy = -1;
        int startDelay = 2;
        if (level == null || ownerEntity == null) return;
        if (!ownerEntity.isAlive()) return;
        if (ownerEntity.level() != level) return;
        BlockState longPillarState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get()
                .defaultBlockState()
                .setValue(HerobrineObsidianBlock.FROM_PLAYER, ownerEntity instanceof Player)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, ownerEntity.getDirection());

        final Vec3 origin = ownerEntity.getEyePosition(1.0F);
        Vec3 look = ownerEntity.getLookAngle();
        Vec3 forward = new Vec3(look.x, 0.0D, look.z);
        if (forward.lengthSqr() < 1.0E-6D) {
            Direction dir = ownerEntity.getDirection();
            forward = new Vec3(dir.getStepX(), 0.0D, dir.getStepZ());
        } else {
            forward = forward.normalize();
        }

        final Vec3 worldUp = new Vec3(0.0D, 1.0D, 0.0D);
        final Vec3 left = forward.cross(worldUp).normalize();
        for (int dz = startDistance; dz < startDistance + depth; dz++) {
            final int fdz = dz;
            final int halfWidth = Math.max(0, maxHalfWidth - (fdz - startDistance));

            final int rowDelay = startDelay + (fdz - startDistance);

            for (int dx = -halfWidth; dx <= halfWidth; dx++) {
                final int fdx = dx;

                Vec3 finalForward = forward;
                new DelayedTask(rowDelay) {
                    @Override
                    public void run() {
                        if (!ownerEntity.isAlive()) return;
                        if (ownerEntity.level() != level) return;

                        Vec3 target = origin
                                .add(left.scale(fdx))
                                .add(worldUp.scale(dy))
                                .add(finalForward.scale(fdz));

                        BlockPos pos = BlockPos.containing(target);
                        placeIfReplaceable(level, pos, longPillarState, ownerEntity);
                    }
                };
            }
        }
    }

    public static void summonShadowObsidianLongPillarShootToward(ServerLevel level, Entity ownerEntity) {
        if (level == null || ownerEntity == null) return;

        BlockState baseState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get()
                .defaultBlockState()
                .setValue(HerobrineObsidianBlock.FROM_PLAYER, ownerEntity instanceof Player)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, ownerEntity.getDirection());

        final Vec3[] lockedEye = { null };
        final Basis[] lockedBasis = { null };
        final Direction[] lockedFacing = { null };

        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 2, 1,  1,  lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 3, 2,  3,  lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 4, 4,  5,  lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 5, 6,  7,  lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 6, 8,  9,  lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 7, 10, 11, lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 8, 12, 13, lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 9, 14, 15, lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 10, 16, 17, lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 11, 18, 25, lockedEye, lockedBasis, lockedFacing);
    }

    private static void scheduleLocalEyesForwardLine(ServerLevel level,
                                                     Entity ownerEntity,
                                                     BlockState baseState,
                                                     int delayTicks,
                                                     int zStart,
                                                     int zEnd,
                                                     Vec3[] lockedEye,
                                                     Basis[] lockedBasis,
                                                     Direction[] lockedFacing) {
        new DelayedTask(delayTicks) {
            @Override public void run() {
                if (!ownerEntity.isAlive()) return;
                if (ownerEntity.level() != level) return;
                if (lockedEye[0] == null) {
                    lockedEye[0] = ownerEntity.getEyePosition(1.0F);
                    lockedBasis[0] = basisFromEntity(ownerEntity);
                    lockedFacing[0] = ownerEntity.getDirection();
                }

                BlockState stateNow = baseState;
                if (stateNow.hasProperty(BlockStateProperties.HORIZONTAL_FACING) && lockedFacing[0] != null) {
                    stateNow = stateNow.setValue(BlockStateProperties.HORIZONTAL_FACING, lockedFacing[0]);
                }

                Basis basis = lockedBasis[0];
                Vec3 eye = lockedEye[0];

                int from = Math.min(zStart, zEnd);
                int to = Math.max(zStart, zEnd);

                for (int z = from; z <= to; z++) {
                    Vec3 world = eye
                            .add(basis.up().scale(-1.0))
                            .add(basis.fwd().scale(z));

                    placeIfReplaceable(level, BlockPos.containing(world), stateNow, ownerEntity);
                }
            }
        };
    }

    public static void summonShadowObsidianLongPillarCircle(ServerLevel level, Entity ownerEntity, BlockPos centerPos) {
        if (level == null || ownerEntity == null || centerPos == null) return;
        if (!ownerEntity.isAlive()) return;
        if (ownerEntity.level() != level) return;

        BlockState longPillarState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get()
                .defaultBlockState()
                .setValue(HerobrineObsidianBlock.FROM_PLAYER, ownerEntity instanceof Player)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, ownerEntity.getDirection());

        scheduleRing(level, ownerEntity, centerPos, longPillarState,
                0, 6,  2.5D, (float) (Math.PI * 2F / 5F));
        scheduleRing(level, ownerEntity, centerPos, longPillarState,
                2, 11, 3.5D, (float) (Math.PI * 2F / 10F));
        scheduleRing(level, ownerEntity, centerPos, longPillarState,
                4, 14, 4.5D, (float) (Math.PI * 2F / 20F));
        scheduleRing(level, ownerEntity, centerPos, longPillarState,
                6, 19, 5.5D, (float) (Math.PI * 2F / 25F));
    }

    private static void scheduleRing(ServerLevel level,
                                     Entity ownerEntity,
                                     BlockPos centerPos,
                                     BlockState blockState,
                                     int delayTicks,
                                     int points,
                                     double radius,
                                     float angleOffset) {

        new DelayedTask(delayTicks) {
            @Override
            public void run() {
                if (!ownerEntity.isAlive()) return;
                if (ownerEntity.level() != level) return;

                int centerX = centerPos.getX();
                int centerZ = centerPos.getZ();

                for (int k = 0; k < points; k++) {
                    float angle = (float) k * ((float) Math.PI * 2.0F / (float) points) + angleOffset;

                    double worldX = centerX + (double) Mth.cos(angle) * radius;
                    double worldZ = centerZ + (double) Mth.sin(angle) * radius;

                    int x = Mth.floor(worldX);
                    int z = Mth.floor(worldZ);

                    int groundY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z) - 1;

                    BlockPos placePos = new BlockPos(x, groundY, z);
                    if (!level.getBlockState(placePos).canBeReplaced()) {
                        placePos = placePos.above();
                    }

                    placeIfReplaceable(level, placePos, blockState, ownerEntity);
                }
            }
        };
    }
}
