package uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine;

import javax.annotation.Nullable;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;
import com.simibubi.create.foundation.utility.Iterate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.level.block.Block;



public abstract class EngineBlock extends WrenchableDirectionalBlock {

	protected EngineBlock(Properties builder) {
		super(builder);
	}

	public boolean canSurvive(BlockState state, Level worldIn, BlockPos pos) {
		return isValidPosition(state, worldIn, pos, state.getValue(FACING));
	}


	@Override
	public InteractionResult onWrenched(BlockState state, UseOnContext context) {
		return InteractionResult.FAIL;
	}



	@Override
	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		if (worldIn.isClientSide)
			return;

		if (fromPos.equals(getBaseBlockPos(state, pos))) {
			if (!canSurvive(state, worldIn, pos)) {
				worldIn.destroyBlock(pos, true);
				return;
			}
		}
	}

	private boolean isValidPosition(BlockState state, Level world, BlockPos pos, Direction facing) {
		BlockPos baseBlockPos = getBaseBlockPos(state, pos);
		if (!isValidBaseBlock(world.getBlockState(baseBlockPos), world, pos))
			return false;
		for (Direction otherFacing : Iterate.horizontalDirections) {
			if (otherFacing == facing)
				continue;
			BlockPos otherPos = baseBlockPos.relative(otherFacing);
			BlockState otherState = world.getBlockState(otherPos);
			if (otherState.getBlock() instanceof EngineBlock
					&& getBaseBlockPos(otherState, otherPos).equals(baseBlockPos))
				return false;
		}

		return true;
	}

	public static BlockPos getBaseBlockPos(BlockState state, BlockPos pos) {
		return pos.relative(state.getValue(FACING).getOpposite());
	}

	@Nullable
	@OnlyIn(Dist.CLIENT)
	public abstract PartialModel getFrameModel();

	protected abstract boolean isValidBaseBlock(BlockState baseBlock, Level world, BlockPos pos);

}
