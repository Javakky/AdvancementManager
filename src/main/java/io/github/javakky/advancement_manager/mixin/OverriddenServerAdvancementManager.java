package io.github.javakky.advancement_manager.mixin;


import com.google.gson.JsonElement;
import io.github.javakky.advancement_manager.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import static io.github.javakky.advancement_manager.AdvancementManager.LOGGER;

@Mixin(ServerAdvancementManager.class)
public class OverriddenServerAdvancementManager {

    @Inject(
            method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At("HEAD")
    )
    public void overrideAdvancement(
            Map<ResourceLocation, JsonElement> resourceMap,
            ResourceManager resourceManager,
            ProfilerFiller profilerFiller,
            CallbackInfo callbackInfo
    ) {
        LOGGER.info("Advancement List >>");
        for (var entry : resourceMap.entrySet()) {
            LOGGER.info(String.format("%s:%s", entry.getKey().getNamespace() + "/" + entry.getKey().getPath(), entry.getValue().toString()));
        }
        resourceMap.entrySet().removeIf(entry -> Config.disableNamespace.contains(entry.getKey().getNamespace()));
    }

}
