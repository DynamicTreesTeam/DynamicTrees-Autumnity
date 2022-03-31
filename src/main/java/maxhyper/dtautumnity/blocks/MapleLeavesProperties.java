package maxhyper.dtautumnity.blocks;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.blocks.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.minecraftabnormals.autumnity.core.registry.AutumnityParticles;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class MapleLeavesProperties extends LeavesProperties {

    public static final TypedRegistry.EntryType<LeavesProperties> TYPE = TypedRegistry.newType(MapleLeavesProperties::new);

    public MapleLeavesProperties(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected DynamicLeavesBlock createDynamicLeaves(final AbstractBlock.Properties properties) {
        return new DynamicLeavesBlock(this, properties){
            @OnlyIn(Dist.CLIENT)
            public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
                super.animateTick(stateIn, worldIn, pos, rand);
                if (rand.nextInt(100) == 0) {
                    BlockPos downPos = pos.below();
                    if (worldIn.isEmptyBlock(downPos)) {
                        int color = colorNumber == null ? properties.foliageColorMultiplier(stateIn, worldIn, pos) : colorNumber;
                        double d0 = ((float)(color >> 16 & 255) / 255.0F);
                        double d1 = ((float)(color >> 8 & 255) / 255.0F);
                        double d2 = ((float)(color & 255) / 255.0F);
                        double d3 = ((float)pos.getX() + rand.nextFloat());
                        double d4 = (double)pos.getY() - 0.05D;
                        double d6 = ((float)pos.getZ() + rand.nextFloat());
                        worldIn.addParticle(AutumnityParticles.FALLING_MAPLE_LEAF.get(), d3, d4, d6, d0, d1, d2);
                    }
                }

            }
        };
    }
}
