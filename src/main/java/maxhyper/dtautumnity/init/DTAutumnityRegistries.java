package maxhyper.dtautumnity.init;

import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import maxhyper.dtautumnity.DynamicTreesAutumnity;
import maxhyper.dtautumnity.trees.MapleFamily;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTAutumnityRegistries {

    @SubscribeEvent
    public static void registerFamilyTypes(final TypeRegistryEvent<Family> event) {
        event.registerType(DynamicTreesAutumnity.resLoc("maple"), MapleFamily.TYPE);
    }

}
