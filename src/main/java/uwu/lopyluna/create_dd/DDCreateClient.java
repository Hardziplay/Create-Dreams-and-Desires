package uwu.lopyluna.create_dd;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;
import uwu.lopyluna.create_dd.ponder.DDPonderIndex;
import uwu.lopyluna.create_dd.ponder.DDPonderTags;

@SuppressWarnings({"unused"})
public class DDCreateClient {

    public static void onCtorClient(IEventBus eventBus, IEventBus forgeEventBus) {

        eventBus.addListener(DDCreateClient::clientInit);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        DDBlockPartialModel.init();
        DDPonderTags.register();
        DDPonderIndex.register();
    }
}
