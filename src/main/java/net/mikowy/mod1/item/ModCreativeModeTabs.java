package net.mikowy.mod1.item;

import net.mikowy.mod1.Mod1;
import net.mikowy.mod1.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Mod1.MOD_ID);

    public static final Supplier<CreativeModeTab> MIKOWY_MOD_TAB = CREATIVE_MODE_TAB.register("mikowy_mod_tab", ()-> CreativeModeTab
            .builder()
            .icon(()-> new ItemStack(ModItems.LEAD_INGOT.get()))
            //.withTabsBefore(ResourceLocation.fromNamespaceAndPath(Mod1.MOD_ID, OTHERTABNAME))   <--- ZEBY ZMIENIC KOLEJNOSC ITEM TABOW
            .title(Component.translatable("creativetab.mikowymod.items"))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.LEAD_INGOT);
                output.accept(ModItems.LEAD_NUGGET);
                output.accept(ModItems.RAW_LEAD);
                output.accept(ModItems.CHISEL);
                output.accept(ModItems.RADISH);

                output.accept(ModBlocks.LEAD_BLOCK);
                output.accept(ModBlocks.LEAD_ORE);
                output.accept(ModBlocks.ASPHALT);
                output.accept(ModBlocks.MAGIC_BLOCK);
                output.accept(ModBlocks.BLOCK_DESTROYER);

            })
            .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
