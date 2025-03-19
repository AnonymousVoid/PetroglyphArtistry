package dev.anonymousvoid.petroglyph_artistry.item;

import dev.anonymousvoid.petroglyph_artistry.PetroglyphArtistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PetroglyphArtistry.MOD_ID);

    public static final RegistryObject<Item> STONE_CARVING_TOOL = ITEMS.register("stone_carving_tool",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STONE_CARVING_DESIGN = ITEMS.register("stone_carving_design",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
