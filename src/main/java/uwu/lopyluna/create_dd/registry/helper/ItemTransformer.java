package uwu.lopyluna.create_dd.registry.helper;

import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import uwu.lopyluna.create_dd.DesiresCreate;

import static uwu.lopyluna.create_dd.DesiresCreate.REGISTRATE;

@SuppressWarnings({"removal", "unused"})
public class ItemTransformer {

    @Deprecated(forRemoval=true)
    public static ItemEntry<uwu.lopyluna.create_dd.content.items.port.ConversionItem> itemConversion(String name, ItemEntry<? extends Item> conversion, boolean isBlock) {return
            REGISTRATE.item(name, p -> new uwu.lopyluna.create_dd.content.items.port.ConversionItem(p, conversion.get()))
            .model((c, p) -> p.withExistingParent(c.getId().getPath(),
                    new ResourceLocation("item/generated")).texture("layer0",
                    new ResourceLocation(DesiresCreate.MOD_ID, (isBlock ? "block/" : "item/") + conversion.getId().getPath())))
            .lang("NA | INVALID ITEM")
            .register();
    }
    @Deprecated(forRemoval=true)
    public static ItemEntry<uwu.lopyluna.create_dd.content.items.port.ConversionItem> itemConversion(String name, BlockEntry<? extends Block> conversion, boolean isBlock) {return
            REGISTRATE.item(name, p -> new uwu.lopyluna.create_dd.content.items.port.ConversionItem(p, conversion.get()))
            .model((c, p) -> p.withExistingParent(c.getId().getPath(),
                    new ResourceLocation("item/generated")).texture("layer0",
                    new ResourceLocation(DesiresCreate.MOD_ID, (isBlock ? "block/" : "item/") + conversion.getId().getPath())))
            .lang("NA | INVALID ITEM")
            .register();
    }

    public static ItemEntry<Item> itemEntry(String name) {return REGISTRATE.item(name, Item::new)
            .register();
    }

    public static ItemEntry<Item> itemEntry(String name, String lang) {return REGISTRATE.item(name, Item::new)
            .lang(lang)
            .register();
    }

    @SafeVarargs
    public static ItemEntry<Item> itemEntryTagged(String name, TagKey<Item>... tags) {
        return REGISTRATE.item(name, Item::new)
            .tag(tags)
            .register();
    }

    @SafeVarargs
    public static ItemEntry<Item> itemEntryTagged(String name, String lang, TagKey<Item>... tags) {
        return REGISTRATE.item(name, Item::new)
                .tag(tags)
                .lang(lang)
                .register();
    }
}
