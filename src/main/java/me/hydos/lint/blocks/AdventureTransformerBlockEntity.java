package me.hydos.lint.blocks;

import me.hydos.techrebornApi.TechRebornApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import reborncore.client.containerBuilder.IContainerProvider;
import reborncore.client.containerBuilder.builder.BuiltContainer;
import reborncore.client.containerBuilder.builder.ContainerBuilder;
import reborncore.common.recipes.RecipeCrafter;
import reborncore.common.util.RebornInventory;
import techreborn.blockentity.machine.GenericMachineBlockEntity;
import techreborn.blockentity.machine.multiblock.MultiblockChecker;

public class AdventureTransformerBlockEntity extends GenericMachineBlockEntity implements IContainerProvider {
    public MultiblockChecker multiblockChecker;
    int ticksSinceLastChange;

    public AdventureTransformerBlockEntity() {
        super(TechRebornApi.getBlockEntity("adventure_transformer"), "Adventure Transformer", 1000, 100000, TechRebornApi.getBlock("adventure_transformer"), 7);
        int[] inputs = new int[]{0, 1};
        int[] outputs = new int[]{2, 3, 4, 5};
        this.inventory = new RebornInventory<>(8, "AdventureTransformerBlockEntity", 64, this);
        this.crafter = new RecipeCrafter(TechRebornApi.getBlockRecipe("adventure_transformer"), this, 1, 4, this.inventory, inputs, outputs);
        this.ticksSinceLastChange = 0;
    }

    public void tick() {
        if (this.multiblockChecker == null) {
            BlockPos downCenter = this.pos.offset(this.getFacing().getOpposite(), 2).offset(Direction.DOWN, 1);
            this.multiblockChecker = new MultiblockChecker(this.world, downCenter);
        }

        ++this.ticksSinceLastChange;
        assert this.world != null;
        if (!this.world.isClient && this.ticksSinceLastChange >= 10) {
            this.ticksSinceLastChange = 0;
        }

        super.tick();
    }

    public void fromTag(CompoundTag tagCompound) {
        super.fromTag(tagCompound);
    }

    public CompoundTag toTag(CompoundTag tagCompound) {
        super.toTag(tagCompound);
        return tagCompound;
    }


    public BuiltContainer createContainer(int syncID, PlayerEntity player) {
        return (
                new ContainerBuilder("adventure_transformer"))
                .player(player.inventory)
                .inventory()
                .hotbar()
                .addInventory()
                .blockEntity(this)
                .energySlot(7, 8, 72)
                .syncEnergyValue()
                .addInventory()
                .create(this, syncID);
    }
}
