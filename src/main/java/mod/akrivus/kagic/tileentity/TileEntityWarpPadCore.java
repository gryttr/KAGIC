package mod.akrivus.kagic.tileentity;

import java.util.Iterator;
import java.util.List;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModSounds;
import mod.heimrarnadalr.kagic.networking.EntityTeleportMessage;
import mod.heimrarnadalr.kagic.networking.KTPacketHandler;
import mod.heimrarnadalr.kagic.worlddata.WorldDataWarpPad;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.block.BlockStairs.EnumShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityWarpPadCore extends TileEntity implements ITickable {
	public static final int warpTicks = 2 * 20;
	public static final int warpCooldownTicks = 1 * 20;
	private int warpTicksLeft = 0;
	private int cooldownTicksLeft = 0;
	private BlockPos destination = null;
	public int renderTicks = 0;
	public int renderCooldown = 0;
	
	private int ticksSinceLastCheck = 0;
	private final int clearanceHeight = 6;
	private boolean isClear = false;
	private boolean isPadValid = false;
	public String name = "";
	private boolean warping = false;
	private boolean cooling = false;

	private void setDirty() {
		//KAGIC.instance.chatInfoMessage("Setting dirty");
		this.markDirty();
		IBlockState state = this.world.getBlockState(this.pos);
		world.notifyBlockUpdate(this.pos, state, state, 3);
		if (!this.world.isRemote) {
			WorldDataWarpPad.get(this.world).addWarpPadEntry(name, this.isPadValid, this.isClear, this.pos);
		}
	}
	
	private boolean isStair(IBlockState state) {
		if (state.getBlock() != Blocks.QUARTZ_STAIRS || state.getValue(BlockStairs.HALF) != EnumHalf.BOTTOM) {
			return false;
		}
		return true;
	}
	
	private boolean validateStairs() {
		IBlockState state = this.world.getBlockState(this.pos.add(2, 0, 0));
		if (state.getBlock() != Blocks.QUARTZ_STAIRS || state.getValue(BlockStairs.FACING) != EnumFacing.WEST || state.getValue(BlockStairs.HALF) != EnumHalf.BOTTOM || state.getValue(BlockStairs.SHAPE) != EnumShape.STRAIGHT) {
			//KAGICTech.instance.chatInfoMessage("Pad has no west quartz stair");	
			return false;
		}
		state = this.world.getBlockState(this.pos.add(-2, 0, 0));
		if (state.getBlock() != Blocks.QUARTZ_STAIRS || state.getValue(BlockStairs.FACING) != EnumFacing.EAST || state.getValue(BlockStairs.HALF) != EnumHalf.BOTTOM || state.getValue(BlockStairs.SHAPE) != EnumShape.STRAIGHT) {
			//KAGICTech.instance.chatInfoMessage("Pad has no east quartz stair");	
			return false;
		}
		state = this.world.getBlockState(this.pos.add(0, 0, 2));
		if (state.getBlock() != Blocks.QUARTZ_STAIRS || state.getValue(BlockStairs.FACING) != EnumFacing.NORTH || state.getValue(BlockStairs.HALF) != EnumHalf.BOTTOM || state.getValue(BlockStairs.SHAPE) != EnumShape.STRAIGHT) {
			//KAGICTech.instance.chatInfoMessage("Pad has no north quartz stair");	
			return false;
		}
		state = this.world.getBlockState(this.pos.add(0, 0, -2));
		if (state.getBlock() != Blocks.QUARTZ_STAIRS || state.getValue(BlockStairs.FACING) != EnumFacing.SOUTH || state.getValue(BlockStairs.HALF) != EnumHalf.BOTTOM || state.getValue(BlockStairs.SHAPE) != EnumShape.STRAIGHT) {
			//KAGICTech.instance.chatInfoMessage("Pad has no south quartz stair");	
			return false;
		}
		
		//Corners
		state = this.world.getBlockState(this.pos.add(-2, 0, -1));
		if (!isStair(state)) {
			//KAGICTech.instance.chatInfoMessage("Pad has no NW quartz stair");	
			return false;
		}
		state = state.withProperty(BlockStairs.FACING, EnumFacing.SOUTH);
		state = state.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT);
		this.world.setBlockState(this.pos.add(-2, 0, -1), state);
		state = this.world.getBlockState(this.pos.add(-1, 0, -2));
		if (!isStair(state)) {
			//KAGICTech.instance.chatInfoMessage("Pad has no NW quartz stair");	
			return false;
		}
		state = state.withProperty(BlockStairs.FACING, EnumFacing.EAST);
		state = state.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT);
		this.world.setBlockState(this.pos.add(-1, 0, -2), state);

		state = this.world.getBlockState(this.pos.add(2, 0, -1));
		if (!isStair(state)) {
			//KAGICTech.instance.chatInfoMessage("Pad has no NE quartz stair");	
			return false;
		}
		state = state.withProperty(BlockStairs.FACING, EnumFacing.SOUTH);
		state = state.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT);
		this.world.setBlockState(this.pos.add(2, 0, -1), state);
		state = this.world.getBlockState(this.pos.add(1, 0, -2));
		if (!isStair(state)) {
			//KAGICTech.instance.chatInfoMessage("Pad has no NE quartz stair");	
		return false;
		}
		state = state.withProperty(BlockStairs.FACING, EnumFacing.WEST);
		state = state.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT);
		this.world.setBlockState(this.pos.add(1, 0, -2), state);
		
		state = this.world.getBlockState(this.pos.add(2, 0, 1));
		if (!isStair(state)) {
			//KAGICTech.instance.chatInfoMessage("Pad has no SE quartz stair");	
			return false;
		}
		state = state.withProperty(BlockStairs.FACING, EnumFacing.NORTH);
		state = state.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT);
		this.world.setBlockState(this.pos.add(2, 0, 1), state);
		state = this.world.getBlockState(this.pos.add(1, 0, 2));
		if (!isStair(state)) {
			//KAGICTech.instance.chatInfoMessage("Pad has no SE quartz stair");	
			return false;
		}
		state = state.withProperty(BlockStairs.FACING, EnumFacing.WEST);
		state = state.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT);
		this.world.setBlockState(this.pos.add(1, 0, 2), state);
		
		state = this.world.getBlockState(this.pos.add(-2, 0, 1));
		if (!isStair(state)) {
			//KAGICTech.instance.chatInfoMessage("Pad has no SW quartz stair");	
			return false;
		}
		state = state.withProperty(BlockStairs.FACING, EnumFacing.NORTH);
		state = state.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT);
		this.world.setBlockState(this.pos.add(-2, 0, 1), state);
		state = this.world.getBlockState(this.pos.add(-1, 0, 2));
		if (!isStair(state)) {
			//KAGICTech.instance.chatInfoMessage("Pad has no SW quartz stair");	
			return false;
		}
		state = state.withProperty(BlockStairs.FACING, EnumFacing.EAST);
		state = state.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT);
		this.world.setBlockState(this.pos.add(-1, 0, 2), state);
		
		//KAGICTech.instance.chatInfoMessage("Pad has all quartz stairs");	
		return true;
	}
	
	private boolean validatePad() {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (j == 0 && i == 0) {
					continue;
				}
				IBlockState state = this.world.getBlockState(this.pos.add(i, 0, j));		
				if (state.getBlock() != Blocks.QUARTZ_BLOCK || state.getValue(BlockQuartz.VARIANT) != BlockQuartz.EnumType.LINES_Y) {
					//KAGICTech.instance.chatInfoMessage("Pad is NOT surrounded by vertical quartz columns");
					return false;
				}				
			}
		}

		//KAGICTech.instance.chatInfoMessage("Pad is surrounded by vertical quartz columns");
		return true && validateStairs();
	}
	
	private boolean validateClearance() {
		for (int i = -1; i <= 1; ++i) {
			for (int j = 1; j <= clearanceHeight; ++j) {
				for (int k = -1; k <= 1; ++k) {
					if (!this.world.isAirBlock(this.pos.add(i, j, k))) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public void validateWarpPad() {
		boolean valid = this.validatePad();
		if (this.isPadValid != valid) {
			this.isPadValid = valid;
			this.setDirty();
		}
		
		boolean clear = this.validateClearance();
		if (this.isClear != clear) {
			this.isClear = clear;
			this.setDirty();
		}
	}

	public boolean isValidPad() {
		return this.isPadValid && this.isClear;
	}
	
	public boolean isClear() {
		return this.isClear;
	}
	
	public boolean isValid() {
		return this.isPadValid;
	}
	
	@Override
	public void update() {
		++(this.ticksSinceLastCheck);
		if (this.isValid() && (ticksSinceLastCheck & 10) == 0) {
			this.validateWarpPad();
			this.ticksSinceLastCheck = 0;
		}
		
		if (this.warpTicksLeft > 0) {
			--this.warpTicksLeft;
			if (this.warpTicksLeft <= 0) {
				this.WARP();
			}
		} 
		
		if (this.cooldownTicksLeft >= 0) {
			--this.cooldownTicksLeft;
		} else if (this.cooling) {
			this.cooling = false;
			this.setDirty();
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setBoolean("valid", this.isPadValid);
		compound.setBoolean("clear", this.isClear);
		compound.setBoolean("warping", this.warping);
		compound.setBoolean("cooling", this.cooling);
		compound.setString("name", this.name);
		compound.setInteger("renderTicks", this.warpTicksLeft);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.isPadValid = compound.getBoolean("valid");
		this.isClear = compound.getBoolean("clear");
		this.warping = compound.getBoolean("warping");
		if (this.warping == false) {
			this.renderCooldown = 0;
		}
		this.cooling = compound.getBoolean("cooling");
		this.name = compound.getString("name");
		this.renderTicks = compound.getInteger("renderTicks");
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 1, this.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
	}
	
	public void setName(String name) {
		if (!this.world.isRemote) {
			KAGIC.instance.chatInfoMessage("Name set");
			this.name = name;
			this.setDirty();
		} else {
			KAGIC.instance.chatInfoMessage("ERROR: setName called on client");
		}
	}
	
	public boolean canInteractWith(EntityPlayer player) {
		return !this.isInvalid() && player.getDistanceSq(this.pos.add(0.5D, 0.5D, 0.5D)) <= 3.0D;
	}
	
	public void destroy() {
		WorldDataWarpPad.get(this.world).removeWarpPadEntry(this.pos);
	}
	
	public void beginWarp(BlockPos destination) {
		this.warpTicksLeft = this.warpTicks;
		this.destination = destination;
		this.warping = true;
		this.setDirty();
		this.world.playSound(null, this.pos, ModSounds.WARP_PAD, SoundCategory.BLOCKS, 20.0f, 1.0f);
	}
	
	public void WARP() {
		BlockPos minorCorner = new BlockPos(this.pos.getX() - 1, this.pos.getY() + 1, this.pos.getZ() - 1);
		BlockPos majorCorner = new BlockPos(this.pos.getX() + 2, this.pos.getY() + 5, this.pos.getZ() + 2);
		AxisAlignedBB warpArea = new AxisAlignedBB(minorCorner, majorCorner);
		List<Entity> entitiesToWarp = this.world.getEntitiesWithinAABB(Entity.class, warpArea);
		Iterator<Entity> it = entitiesToWarp.iterator();
		TileEntityWarpPadCore destPad = (TileEntityWarpPadCore) this.world.getTileEntity(this.destination);
		ChunkPos cPos = destPad.world.getChunkFromBlockCoords(destPad.pos).getPos();
		if (!destPad.isValidPad()) {
			this.cooldownTicksLeft = 1;
			return;
		}
		
		while (it.hasNext()) {
			Entity entity = (Entity) it.next();
			double offsetX = entity.posX - this.pos.getX();
			double offsetY = entity.posY - this.pos.getY();
			double offsetZ = entity.posZ - this.pos.getZ();
			
			if (entity instanceof EntityPlayerMP) {
				((EntityPlayerMP) entity).connection.setPlayerLocation(this.destination.getX() + offsetX, this.destination.getY() + offsetY, this.destination.getZ() + offsetZ, entity.rotationYaw, entity.rotationPitch);
				KTPacketHandler.INSTANCE.sendTo(new EntityTeleportMessage(entity.getEntityId(), this.destination.getX() + offsetX, this.destination.getY() + offsetY, this.destination.getZ() + offsetZ), (EntityPlayerMP) entity);
			}
			else if (entity instanceof EntityLivingBase){
				entity.setPositionAndUpdate(this.destination.getX() + offsetX, this.destination.getY() + offsetY, this.destination.getZ() + offsetZ);
			}
			else {
				entity.setLocationAndAngles(this.destination.getX() + offsetX, this.destination.getY() + offsetY, this.destination.getZ() + offsetZ, entity.rotationYaw, entity.rotationPitch);
				entity.setRotationYawHead(entity.rotationYaw);
			}
			
			for (EntityPlayer player : ((WorldServer) this.world).getEntityTracker().getTrackingPlayers(entity)) {
				KTPacketHandler.INSTANCE.sendTo(new EntityTeleportMessage(entity.getEntityId(), this.destination.getX() + offsetX, this.destination.getY() + offsetY, this.destination.getZ() + offsetZ), (EntityPlayerMP) player);
			}
		}
		this.warping = false;
		this.cooling = true;
		this.cooldownTicksLeft = this.warpCooldownTicks;
		this.setDirty();
	}
	
	public static TileEntityWarpPadCore getEntityPad(Entity entityIn) {
		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				TileEntity te =  entityIn.getEntityWorld().getTileEntity(new BlockPos(entityIn.posX + i, entityIn.posY - 1, entityIn.posZ + j));
				if (te instanceof TileEntityWarpPadCore) {
					return (TileEntityWarpPadCore) te;
				}
			}
		}
		return null;
	}
	
	public boolean isWarping() {
		return this.warping || this.cooling;
	}
	
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(this.pos.add(-1, 0, -1), this.pos.add(1, 6, 1));
	}
}
