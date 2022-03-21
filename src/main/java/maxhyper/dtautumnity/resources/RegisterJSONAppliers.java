package maxhyper.dtautumnity.resources;

import com.ferreusveritas.dynamictrees.api.treepacks.ApplierRegistryEvent;
import com.ferreusveritas.dynamictrees.deserialisation.PropertyAppliers;
import com.ferreusveritas.dynamictrees.trees.Family;
import com.google.gson.JsonElement;
import maxhyper.dtautumnity.DynamicTreesAutumnity;
import maxhyper.dtautumnity.trees.MapleFamily;
import net.minecraft.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DynamicTreesAutumnity.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class RegisterJSONAppliers {

    @SubscribeEvent
    public static void registerAppliersFamily(final ApplierRegistryEvent.Reload<Family, JsonElement> event) {
        registerFamilyAppliers(event.getAppliers());
    }

    public static void registerFamilyAppliers(PropertyAppliers<Family, JsonElement> appliers) {
        appliers.register("primitive_sappy_log", MapleFamily.class, Block.class,
                MapleFamily::setPrimitiveSappyLog)
                .register("sappy_branch_chance", MapleFamily.class, Float.class,
                        MapleFamily::setSappyBranchChance);
    }

    @SubscribeEvent public static void registerAppliersFamily(final ApplierRegistryEvent.GatherData<Family, JsonElement> event) { registerFamilyAppliers(event.getAppliers()); }

}