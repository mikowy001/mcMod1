package net.mikowy.mod1.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

public class BlockDestroyerBlock extends Block {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public static final MapCodec<BlockDestroyerBlock> CODEC =
            simpleCodec(BlockDestroyerBlock::new);


    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction side) {
        return state.getValue(LIT) ? 15 : 0;
    }

    @Override
    protected @NotNull MapCodec<? extends Block> codec() {
        return CODEC;
    }


    public BlockDestroyerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(LIT, false)
        );
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            boolean flag = state.getValue(LIT);
            boolean powered = level.hasNeighborSignal(pos);

            if (powered == true && flag == false) {
                level.setBlock(pos, state.setValue(LIT, true), BlockDestroyerBlock.UPDATE_CLIENTS);
                level.scheduleTick(pos, this, 2);
            }
        }
    }


    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.updateNeighborsAt(pos, this);
        level.destroyBlock(pos, true);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if(!level.isClientSide && !state.is(oldState.getBlock())){
            this.neighborChanged(state, level, pos, this, pos, movedByPiston);
        }
    }
}

