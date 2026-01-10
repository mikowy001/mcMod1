package net.mikowy.mod1.item.custom;


import net.minecraft.world.level.block.Block;


public class AsphaltBlock extends Block {

    public AsphaltBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getSpeedFactor() {
        return super.getSpeedFactor() * 1.5f;
    }

    @Override
    public float getFriction() {
        return super.getFriction() * 0.1f;
    }
}
