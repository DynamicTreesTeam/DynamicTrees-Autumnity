package maxhyper.dtautumnity;

import com.ferreusveritas.dynamictrees.api.GatherDataHelper;
import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.init.DTConfigs;
import com.teamabnormals.autumnity.core.AutumnityConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.LinkedList;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DynamicTreesAutumnity.MOD_ID)
public class DynamicTreesAutumnity {
    public static final String MOD_ID = "dtautumnity";

    public DynamicTreesAutumnity() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::gatherData);

        MinecraftForge.EVENT_BUS.register(this);

        RegistryHandler.setup(MOD_ID);

        DTAutumnityRegistries.setup();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        if (DTConfigs.WORLD_GEN.get()){
            AutumnityConfig.COMMON.generateSpottedForests.set(false);
            AutumnityConfig.COMMON.mapleTreeBiomes.set(new LinkedList<>());
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
    }

    public void gatherData(final GatherDataEvent event) {
        GatherDataHelper.gatherTagData(MOD_ID, event);
        GatherDataHelper.gatherLootData(MOD_ID, event);
    }

    public static ResourceLocation resLoc(final String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
