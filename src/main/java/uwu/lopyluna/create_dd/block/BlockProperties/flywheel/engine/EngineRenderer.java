package uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.utility.AngleHelper;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;

public class EngineRenderer<T extends EngineTileEntity> extends SafeBlockEntityRenderer<T> {

	public EngineRenderer(BlockEntityRendererProvider.Context context) {
		super();
	}

	@Override
	protected void renderSafe(EngineTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

		if (Backend.canUseInstancing(te.getLevel()))
			return;

		Block block = te.getBlockState()
				.getBlock();
		if (block instanceof EngineBlock) {
			EngineBlock engineBlock = (EngineBlock) block;
			PartialModel frame = engineBlock.getFrameModel();

			if (frame != null) {
				Direction facing = te.getBlockState()
						.getValue(EngineBlock.FACING);
				float angle = AngleHelper.rad(AngleHelper.horizontalAngle(facing));
				PartialBufferer.get(frame, te.getBlockState())
						.rotateCentered(Direction.UP, angle)
						.translate(0, 0, -1)
						.light(light)
						.renderInto(ms, buffer.getBuffer(RenderType.solid()));
			}
		}
	}

}
