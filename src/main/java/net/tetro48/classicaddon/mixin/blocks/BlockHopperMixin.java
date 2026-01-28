package net.tetro48.classicaddon.mixin.blocks;

import api.block.ModStepSound;
import btw.block.BTWBlocks;
import btw.community.classicaddon.ClassicAddon;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.src.BlockHopper.*;

@Mixin(BlockHopper.class)
public abstract class BlockHopperMixin extends BlockContainer {

	@Shadow @Final private Random field_94457_a;

	@Shadow private Icon hopperIcon;

	@Shadow private Icon hopperTopIcon;

	@Shadow private Icon hopperInsideIcon;

	protected BlockHopperMixin(int i, Material material) {
		super(i, material);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void injectInit(int par1, CallbackInfo ci) {
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setStepSound(BTWBlocks.soulforgedSteelStepSound);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int i, int j, int k) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
		super.addCollisionBoxesToList(world, i, j, k, axisAlignedBB, list, entity);
		float var8 = 0.125F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, var8, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, i, j, k, axisAlignedBB, list, entity);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var8);
		super.addCollisionBoxesToList(world, i, j, k, axisAlignedBB, list, entity);
		this.setBlockBounds(1.0F - var8, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, i, j, k, axisAlignedBB, list, entity);
		this.setBlockBounds(0.0F, 0.0F, 1.0F - var8, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, i, j, k, axisAlignedBB, list, entity);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public int onBlockPlaced(World world, int i, int j, int k, int l, float f, float g, float h, int m) {
		int var10 = Facing.oppositeSide[l];
		if (var10 == 1) {
			var10 = 0;
		}

		if (!world.getData(ClassicAddon.VANILLA_HOPPERS_IN_WORLD) && !world.isRemote) {
			world.setData(ClassicAddon.VANILLA_HOPPERS_IN_WORLD, true);
			ClassicAddon.sendPacketToAllPlayers(new Packet3Chat(
					ChatMessageComponent.createFromTranslationKey("tile.hopper.warning")
							.setColor(EnumChatFormatting.DARK_RED)
							.setBold(true)));
			ClassicAddon.sendPacketToAllPlayers(new Packet3Chat(
					ChatMessageComponent.createFromTranslationKey("tile.hopper.warning.detail.placed")
							.setColor(EnumChatFormatting.GRAY)));
			ClassicAddon.sendPacketToAllPlayers(new Packet62LevelSound("mob.wither.spawn", i, j, k, Float.POSITIVE_INFINITY, 0.5f   ));
		}
		return var10;
	}

	public TileEntity createNewTileEntity(World world) {
		return new TileEntityHopper();
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLivingBase, ItemStack itemStack) {
		super.onBlockPlacedBy(world, i, j, k, entityLivingBase, itemStack);
		if (itemStack.hasDisplayName()) {
			TileEntityHopper var7 = getHopperTile(world, i, j, k);
			var7.setInventoryName(itemStack.getDisplayName());
		}

	}

	public void onBlockAdded(World world, int i, int j, int k) {
		super.onBlockAdded(world, i, j, k);
		this.updateMetadata(world, i, j, k);
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int l, float f, float g, float h) {
		if (world.isRemote) {
			return true;
		} else {
			TileEntityHopper var10 = getHopperTile(world, i, j, k);
			if (var10 != null) {
				entityPlayer.displayGUIHopper(var10);
			}

			return true;
		}
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		this.updateMetadata(world, i, j, k);
	}

	private void updateMetadata(World world, int i, int j, int k) {
		int var5 = world.getBlockMetadata(i, j, k);
		int var6 = getDirectionFromMetadata(var5);
		boolean var7 = !world.isBlockIndirectlyGettingPowered(i, j, k);
		boolean var8 = getIsBlockNotPoweredFromMetadata(var5);
		if (var7 != var8) {
			world.setBlockMetadataWithNotify(i, j, k, var6 | (var7 ? 0 : 8), 4);
		}

	}

	public void breakBlock(World world, int i, int j, int k, int l, int m) {
		TileEntityHopper var7 = (TileEntityHopper)world.getBlockTileEntity(i, j, k);
		if (var7 != null) {
			for(int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
				ItemStack var9 = var7.getStackInSlot(var8);
				if (var9 != null) {
					float var10 = this.field_94457_a.nextFloat() * 0.8F + 0.1F;
					float var11 = this.field_94457_a.nextFloat() * 0.8F + 0.1F;
					float var12 = this.field_94457_a.nextFloat() * 0.8F + 0.1F;

					while(var9.stackSize > 0) {
						int var13 = this.field_94457_a.nextInt(21) + 10;
						if (var13 > var9.stackSize) {
							var13 = var9.stackSize;
						}

						var9.stackSize -= var13;
						EntityItem var14 = new EntityItem(world, (double)((float)i + var10), (double)((float)j + var11), (double)((float)k + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));
						if (var9.hasTagCompound()) {
							var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
						}

						float var15 = 0.05F;
						var14.motionX = (double)((float)this.field_94457_a.nextGaussian() * var15);
						var14.motionY = (double)((float)this.field_94457_a.nextGaussian() * var15 + 0.2F);
						var14.motionZ = (double)((float)this.field_94457_a.nextGaussian() * var15);
						world.spawnEntityInWorld(var14);
					}
				}
			}

			world.func_96440_m(i, j, k, l);
		}

		super.breakBlock(world, i, j, k, l, m);
	}

	@Environment(EnvType.CLIENT)
	@Inject(method = "getIcon", at = @At("HEAD"), cancellable = true)
	public void getIcon(int par1, int par2, CallbackInfoReturnable<Icon> cir) {
		cir.setReturnValue(par1 == 1 ? this.hopperTopIcon : this.hopperIcon);
	}

	public boolean hasComparatorInputOverride() {
		return true;
	}

	public int getComparatorInputOverride(World world, int i, int j, int k, int l) {
		return Container.calcRedstoneFromInventory(getHopperTile(world, i, j, k));
	}


	@Environment(EnvType.CLIENT)
	@Inject(method = "registerIcons", at = @At("HEAD"), cancellable = true)
	public void registerIcons(IconRegister iconRegister, CallbackInfo ci) {
		this.hopperIcon = iconRegister.registerIcon("hopper_outside");
		this.hopperTopIcon = iconRegister.registerIcon("hopper_top");
		this.hopperInsideIcon = iconRegister.registerIcon("hopper_inside");
		ci.cancel();
	}


	@Override
	@Environment(EnvType.CLIENT)
	public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
		renderer.setRenderBounds(getBlockBoundsFromPoolBasedOnState(renderer.blockAccess, i, j, k));

		return renderer.renderBlockHopper((BlockHopper)(Object)this, i, j, k);
	}

	@Environment(EnvType.CLIENT)
	public String getItemIconName() {
		return "hopper";
	}
}
