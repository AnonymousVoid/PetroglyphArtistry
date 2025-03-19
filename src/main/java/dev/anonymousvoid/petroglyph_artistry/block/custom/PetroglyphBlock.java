package dev.anonymousvoid.petroglyph_artistry.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class PetroglyphBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<PetroglyphBlock> CODEC = simpleCodec(PetroglyphBlock::new);
    public static final ResourceLocation DESIGNS_DYNAMIC_DROP_ID = ResourceLocation.withDefaultNamespace("designs");
    private static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty CRACKED = BlockStateProperties.CRACKED;

    @Override
    public MapCodec<PetroglyphBlock> codec() {
        return CODEC;
    }

    public PetroglyphBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(HORIZONTAL_FACING, Direction.NORTH)
                        .setValue(CRACKED, Boolean.valueOf(false))
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(HORIZONTAL_FACING, context.getHorizontalDirection())
                .setValue(CRACKED, Boolean.valueOf(false));
    }

//    @Override
//    protected ItemInteractionResult useItemOn(
//            ItemStack p_335411_, BlockState p_334873_, Level p_328717_, BlockPos p_332886_, Player p_331165_, InteractionHand p_330433_, BlockHitResult p_330105_
//    ) {
//        if (p_328717_.getBlockEntity(p_332886_) instanceof PetroglyphBlockEntity petroglyphblockentity) {
//            if (p_328717_.isClientSide) {
//                return ItemInteractionResult.CONSUME;
//            } else {
//                ItemStack itemstack1 = petroglyphblockentity.getTheItem();
//                if (!p_335411_.isEmpty()
//                        && (itemstack1.isEmpty() || ItemStack.isSameItemSameComponents(itemstack1, p_335411_) && itemstack1.getCount() < itemstack1.getMaxStackSize())) {
//                    petroglyphblockentity.wobble(PetroglyphBlockEntity.WobbleStyle.POSITIVE);
//                    p_331165_.awardStat(Stats.ITEM_USED.get(p_335411_.getItem()));
//                    ItemStack itemstack = p_335411_.consumeAndReturn(1, p_331165_);
//                    float f;
//                    if (petroglyphblockentity.isEmpty()) {
//                        petroglyphblockentity.setTheItem(itemstack);
//                        f = (float)itemstack.getCount() / (float)itemstack.getMaxStackSize();
//                    } else {
//                        itemstack1.grow(1);
//                        f = (float)itemstack1.getCount() / (float)itemstack1.getMaxStackSize();
//                    }
//
//                    p_328717_.playSound(null, p_332886_, SoundEvents.DECORATED_POT_INSERT, SoundSource.BLOCKS, 1.0F, 0.7F + 0.5F * f);
//                    if (p_328717_ instanceof ServerLevel serverlevel) {
//                        serverlevel.sendParticles(
//                                ParticleTypes.DUST_PLUME,
//                                (double)p_332886_.getX() + 0.5,
//                                (double)p_332886_.getY() + 1.2,
//                                (double)p_332886_.getZ() + 0.5,
//                                7,
//                                0.0,
//                                0.0,
//                                0.0,
//                                0.0
//                        );
//                    }
//
//                    petroglyphblockentity.setChanged();
//                    p_328717_.gameEvent(p_331165_, GameEvent.BLOCK_CHANGE, p_332886_);
//                    return ItemInteractionResult.SUCCESS;
//                } else {
//                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
//                }
//            }
//        } else {
//            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
//        }
//    }

//    @Override
//    protected InteractionResult useWithoutItem(BlockState p_329061_, Level p_331143_, BlockPos p_332658_, Player p_330362_, BlockHitResult p_330700_) {
//        if (p_331143_.getBlockEntity(p_332658_) instanceof PetroglyphBlockEntity petroglyphblockentity) {
//            p_331143_.playSound(null, p_332658_, SoundEvents.DECORATED_POT_INSERT_FAIL, SoundSource.BLOCKS, 1.0F, 1.0F);
//            petroglyphblockentity.wobble(PetroglyphBlockEntity.WobbleStyle.NEGATIVE);
//            p_331143_.gameEvent(p_330362_, GameEvent.BLOCK_CHANGE, p_332658_);
//            return InteractionResult.SUCCESS;
//        } else {
//            return InteractionResult.PASS;
//        }
//    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(HORIZONTAL_FACING, CRACKED);
    }

//    @Nullable
//    @Override
//    public BlockEntity newBlockEntity(BlockPos p_273396_, BlockState p_272674_) {
//        return new PetroglyphBlockEntity(p_273396_, p_272674_);
//    }

//    @Override
//    protected void onRemove(BlockState p_312694_, Level p_313251_, BlockPos p_312873_, BlockState p_312133_, boolean p_311809_) {
//        Containers.dropContentsOnDestroy(p_312694_, p_312133_, p_313251_, p_312873_);
//        super.onRemove(p_312694_, p_313251_, p_312873_, p_312133_, p_311809_);
//    }

//    @Override
//    protected List<ItemStack> getDrops(BlockState p_287683_, LootParams.Builder p_287582_) {
//        BlockEntity blockentity = p_287582_.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
//        if (blockentity instanceof PetroglyphBlockEntity petroglyphblockentity) {
//            p_287582_.withDynamicDrop(SHERDS_DYNAMIC_DROP_ID, p_327259_ -> {
//                for (Item item : petroglyphblockentity.getDecorations().ordered()) {
//                    p_327259_.accept(item.getDefaultInstance());
//                }
//            });
//        }
//
//        return super.getDrops(p_287683_, p_287582_);
//    }

//    @Override
//    public BlockState playerWillDestroy(Level p_273590_, BlockPos p_273343_, BlockState p_272869_, Player p_273002_) {
//        ItemStack itemstack = p_273002_.getMainHandItem();
//        BlockState blockstate = p_272869_;
//        if (itemstack.is(ItemTags.BREAKS_DECORATED_POTS) && !EnchantmentHelper.hasTag(itemstack, EnchantmentTags.PREVENTS_DECORATED_POT_SHATTERING)) {
//            blockstate = p_272869_.setValue(CRACKED, Boolean.valueOf(true));
//            p_273590_.setBlock(p_273343_, blockstate, 4);
//        }
//
//        return super.playerWillDestroy(p_273590_, p_273343_, blockstate, p_273002_);
//    }

    @Override
    protected SoundType getSoundType(BlockState state) {
        return state.getValue(CRACKED) ? SoundType.DECORATED_POT_CRACKED : SoundType.DECORATED_POT;
    }

    @Override
    public void appendHoverText(ItemStack p_285238_, Item.TooltipContext p_331405_, List<Component> p_285448_, TooltipFlag p_284997_) {
        super.appendHoverText(p_285238_, p_331405_, p_285448_, p_284997_);
        PotDecorations potdecorations = p_285238_.getOrDefault(DataComponents.POT_DECORATIONS, PotDecorations.EMPTY);
        if (!potdecorations.equals(PotDecorations.EMPTY)) {
            p_285448_.add(CommonComponents.EMPTY);
            Stream.of(potdecorations.front(), potdecorations.left(), potdecorations.right(), potdecorations.back())
                    .forEach(p_327261_ -> p_285448_.add(new ItemStack(p_327261_.orElse(Items.BRICK), 1).getHoverName().plainCopy().withStyle(ChatFormatting.GRAY)));
        }
    }

    @Override
    protected void onProjectileHit(Level p_310477_, BlockState p_309479_, BlockHitResult p_309542_, Projectile p_309867_) {
        BlockPos blockpos = p_309542_.getBlockPos();
        if (!p_310477_.isClientSide && p_309867_.mayInteract(p_310477_, blockpos) && p_309867_.mayBreak(p_310477_)) {
            p_310477_.setBlock(blockpos, p_309479_.setValue(CRACKED, Boolean.valueOf(true)), 4);
            p_310477_.destroyBlock(blockpos, true, p_309867_);
        }
    }

//    @Override
//    public ItemStack getCloneItemStack(LevelReader p_312375_, BlockPos p_300759_, BlockState p_297348_) {
//        return p_312375_.getBlockEntity(p_300759_) instanceof PetroglyphBlockEntity petroglyphblockentity
//                ? petroglyphblockentity.getPotAsItem()
//                : super.getCloneItemStack(p_312375_, p_300759_, p_297348_);
//    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(HORIZONTAL_FACING)));
    }
}
