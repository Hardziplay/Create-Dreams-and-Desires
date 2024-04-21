package uwu.lopyluna.create_dd;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.FurnaceEngineBlock;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;
import uwu.lopyluna.create_dd.block.BlockPalette.DDPaletteBlocks;
import uwu.lopyluna.create_dd.configs.DDConfigs;
import uwu.lopyluna.create_dd.fluid.DDFluids;
import uwu.lopyluna.create_dd.item.DDItems;
import uwu.lopyluna.create_dd.creative.DDItemTab;
import uwu.lopyluna.create_dd.recipe.DDRecipesTypes;
import uwu.lopyluna.create_dd.sounds.DDSoundEvents;


@SuppressWarnings({"all"})
@Mod(DDCreate.MOD_ID)
public class DDCreate
{
    public static final String NAME = "Create: Dreams n' Desire's";
    public static final String MOD_ID = "create_dd";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(DDCreate.MOD_ID);

    @Nullable
    public static KineticStats create(Item item) {
        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof IRotate || block instanceof FurnaceEngineBlock) {
                return new KineticStats(block);
            }
        }
        return null;
    }

    static {
        REGISTRATE.setTooltipModifierFactory(item -> {
            return new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
                    .andThen(TooltipModifier.mapNull(DDCreate.create(item)));
        });
    }

    public DDCreate()
    {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        REGISTRATE.registerEventListeners(eventBus);

        DDSoundEvents.register(eventBus);
        DDItemTab.init();
        DDBlockPartialModel.init();
        DDBlockEntityTypes.register();
        DDBlocks.register();
        DDItems.register();
        DDFluids.register();
        DDPaletteBlocks.register();

        DDRecipesTypes.register(eventBus);

        DDTags.init();

        DDConfigs.register(modLoadingContext);

        eventBus.addListener(DDCreate::init);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> DDCreateClient.onCtorClient(eventBus, forgeEventBus));

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }

    public static void init(final FMLCommonSetupEvent event) {
        DDFluids.registerFluidInteractions();
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(DDCreate.MOD_ID, path);
    }

}
