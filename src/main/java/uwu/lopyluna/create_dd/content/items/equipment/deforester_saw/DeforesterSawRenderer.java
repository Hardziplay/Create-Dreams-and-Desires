package uwu.lopyluna.create_dd.content.items.equipment.deforester_saw;

import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.util.AnimationTickHolder;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import uwu.lopyluna.create_dd.DesiresCreate;

public class DeforesterSawRenderer extends CustomRenderedItemModelRenderer {
    protected static final PartialModel ITEM = new PartialModel(DesiresCreate.asResource("item/deforester_saw/item"));
    protected static final PartialModel GEAR = new PartialModel(DesiresCreate.asResource("item/deforester_saw/gear"));

    private static final Vec3 GEAR_ROTATION_OFFSET = new Vec3(-3.25 / 16f, -1.5 / 16f, 0);

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemTransforms.TransformType transformType,
                          PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        TransformStack stacker = TransformStack.cast(ms);
        float worldTime = AnimationTickHolder.getRenderTime();

        renderer.render(ITEM.get(), light);

        float angle = worldTime * .5f % 360;
        stacker.translate(GEAR_ROTATION_OFFSET)
                .rotateZ(angle)
                .translateBack(GEAR_ROTATION_OFFSET);
        renderer.render(GEAR.get(), light);


    }
}