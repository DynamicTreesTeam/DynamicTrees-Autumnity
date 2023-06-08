package maxhyper.dtautumnity;

import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import maxhyper.dtautumnity.blocks.MapleLeavesProperties;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTAutumnityRegistries {

    public static void setup() {
    }

    @SubscribeEvent
    public static void registerLeavesPropertiesTypes(final TypeRegistryEvent<LeavesProperties> event) {
        event.registerType(DynamicTreesAutumnity.resLoc("maple"), MapleLeavesProperties.TYPE);
    }

}
