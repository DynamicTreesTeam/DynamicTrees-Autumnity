package maxhyper.dtautumnity;

import com.ferreusveritas.dynamictrees.DynamicTrees;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.api.worldgen.FeatureCanceller;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.worldgen.cancellers.MushroomFeatureCanceller;
import maxhyper.dtautumnity.blocks.MapleLeavesProperties;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class DTAutumnityRegistries {

    public static void setup() {
    }

    @SubscribeEvent
    public static void registerLeavesPropertiesTypes(final TypeRegistryEvent<LeavesProperties> event) {
        event.registerType(DynamicTreesAutumnity.resLoc("maple"), MapleLeavesProperties.TYPE);
    }

//    public static final FeatureCanceller AUTUMNITY_TREE_CANCELLER = new MushroomFeatureCanceller<>(DynamicTrees.resLoc("mushroom"), BigMushroomFeatureConfig.class);
//
//    @SubscribeEvent
//    public static void onFeatureCancellerRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<FeatureCanceller> event) {
//        event.getRegistry().registerAll(AUTUMNITY_TREE_CANCELLER);
//    }

}
