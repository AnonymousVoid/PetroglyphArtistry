package dev.anonymousvoid.petroglyph_artistry.item;

import dev.anonymousvoid.petroglyph_artistry.PetroglyphArtistry;
import dev.anonymousvoid.petroglyph_artistry.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PetroglyphArtistry.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PETROGLYPH_ARTISTRY_TAB =
            CREATIVE_MODE_TABS.register("petroglyph_artistry",
                    () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.STONE_PICKAXE))
                            .title(Component.translatable("creativetab.petroglyph_artistry.petroglyph_artistry_tab"))
                            .displayItems((itemDisplayParameters, output) -> {
                                output.accept(ModItems.STONE_CARVING_DESIGN.get());
                                output.accept(ModBlocks.PETROGLYPH_STONE_BLOCK.get());

                            }).build());


    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
