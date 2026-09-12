package com.pla.epicfight_annoyingvillagers.mixin.plugin;

import java.util.List;
import java.util.Set;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public final class CompatMixinPlugin implements IMixinConfigPlugin {
    private static boolean isModLoadedEarly(String modId) {
        LoadingModList list = FMLLoader.getLoadingModList();
        return list != null && list.getModFileById(modId) != null;
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.startsWith("com.pla.epicfight_annoyingvillagers.mixin.compat.smartnpc.")) {
            return isModLoadedEarly("smart_npc");
        }
        if (mixinClassName.startsWith("com.pla.epicfight_annoyingvillagers.mixin.efclash_blade.")) {
            return isModLoadedEarly("efclash_blade");
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
