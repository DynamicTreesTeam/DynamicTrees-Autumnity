package maxhyper.dtautumnity.trees;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.blocks.branches.BasicBranchBlock;
import com.ferreusveritas.dynamictrees.blocks.branches.BranchBlock;
import com.ferreusveritas.dynamictrees.init.DTConfigs;
import com.ferreusveritas.dynamictrees.systems.nodemappers.NetVolumeNode;
import com.ferreusveritas.dynamictrees.trees.Family;
import com.ferreusveritas.dynamictrees.util.Optionals;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class MapleFamily extends Family {

    public static final TypedRegistry.EntryType<Family> TYPE = TypedRegistry.newType(MapleFamily::new);

    protected BranchBlock altStrippedBranchBlock;
    protected float sappyBranchChance = 0.25f;

    public MapleFamily(ResourceLocation name) {
        super(name);
    }

    @Override
    public void setupBlocks() {
        super.setupBlocks();

        this.altStrippedBranchBlock = setupBranch(
                createBranch(getBranchName("sappy_")),
                false
        );
    }

    public Family setPrimitiveSappyLog(Block primitiveLog) {
        altStrippedBranchBlock.setPrimitiveLogDrops(new ItemStack(primitiveLog));
        return this;
    }

    public Optional<BranchBlock> getSappyBranch() {
        return Optionals.ofBlock(altStrippedBranchBlock);
    }

    public float getSappyBranchChance(ItemStack heldStack) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, heldStack);
        return 1 - 1f / ((float) i * (1 / 3f) + (1 / (1 - sappyBranchChance)));
    }

    public void setSappyBranchChance(Float chance) {
        sappyBranchChance = chance;
    }

    @Override
    protected BranchBlock createBranchBlock(ResourceLocation name) {
        final BasicBranchBlock branch = new BasicBranchBlock(name, this.getProperties()) {
            @Override
            public void stripBranch(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack heldItem) {
                final int radius = this.getRadius(state);
                this.damageTool(player, heldItem, radius / 2, new NetVolumeNode.Volume((radius * radius * 64) / 2), false);

                if (!world.isClientSide()) {
                    Family fam = this.getFamily();
                    if (fam instanceof MapleFamily) {
                        MapleFamily mapleFam = (MapleFamily) fam;
                        Optional<BranchBlock> stripBranch = (world.getRandom().nextFloat() < mapleFam.getSappyBranchChance(heldItem)) ? mapleFam.getSappyBranch() : mapleFam.getStrippedBranch();
                        stripBranch.ifPresent(strippedBranch ->
                                strippedBranch.setRadius(
                                        world,
                                        pos,
                                        Math.max(1, radius - (DTConfigs.ENABLE_STRIP_RADIUS_REDUCTION.get() ? 1 : 0)),
                                        null
                                )
                        );
                    }
                }
            }
        };
        if (this.isFireProof()) branch.setFireSpreadSpeed(0).setFlammability(0);
        return branch;
    }

    @Override
    public boolean onTreeActivated(TreeActivationContext context) {
        if (TreeHelper.getBranch(context.hitState) == altStrippedBranchBlock) {
            collectSap(context.world, context.hitPos, context.player, context.hand);
        }
        return super.onTreeActivated(context);
    }

    public void collectSap(World worldIn, BlockPos pos, PlayerEntity player, Hand handIn) {
        ItemStack itemstack = player.getItemInHand(handIn);
        BranchBlock strippedBranch = getStrippedBranch().orElse(null);
        int radius = TreeHelper.getRadius(worldIn, pos);
        if (strippedBranch == null) return;
        if (itemstack.isEmpty()) return;
        if (radius == 0) return;

        Item item = itemstack.getItem();
        if (item == Items.GLASS_BOTTLE) {
            if (!worldIn.isClientSide) {
                if (!player.abilities.instabuild) {
                    ItemStack itemstack2 = new ItemStack(AutumnityItems.SAP_BOTTLE.get());
                    itemstack.shrink(1);
                    if (itemstack.isEmpty())
                        player.setItemInHand(handIn, itemstack2);
                    else if (!player.inventory.add(itemstack2))
                        player.drop(itemstack2, false);
                    else if (player instanceof ServerPlayerEntity)
                        ((ServerPlayerEntity) player).refreshContainer(player.inventoryMenu);
                }

                worldIn.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                strippedBranch.setRadius(worldIn, pos, radius, null, 11);
            } else {
                player.swing(handIn);
            }
        }
    }

}
