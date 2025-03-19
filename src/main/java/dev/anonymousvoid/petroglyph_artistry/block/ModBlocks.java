package dev.anonymousvoid.petroglyph_artistry.block;

import dev.anonymousvoid.petroglyph_artistry.PetroglyphArtistry;
import dev.anonymousvoid.petroglyph_artistry.block.custom.PetroglyphBlock;
import dev.anonymousvoid.petroglyph_artistry.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, PetroglyphArtistry.MOD_ID);

    public static final RegistryObject<Block> PETROGLYPH_STONE_BLOCK = registerBlock("petroglyph_stone_block",
            () -> new PetroglyphBlock(BlockBehaviour.Properties.of()
                    .strength(4F).requiresCorrectToolForDrops().sound(SoundType.STONE)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
