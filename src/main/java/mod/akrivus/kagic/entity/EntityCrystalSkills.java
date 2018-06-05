package mod.akrivus.kagic.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.skills.SkillBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityCrystalSkills extends EntityCreature {
	private static final DataParameter<Boolean> SELECTED = EntityDataManager.<Boolean>createKey(EntityCrystalSkills.class, DataSerializers.BOOLEAN);
	public static final ArrayList<Class<? extends SkillBase>> SKILLS = new ArrayList<Class<? extends SkillBase>>();
	public HashMap<Class<? extends SkillBase>, SkillBase> skills = new HashMap<Class<? extends SkillBase>, SkillBase>();
	public HashMap<String, Object> savedVariables = new HashMap<String, Object>();
	public EntityPlayer lastPlayerSpokenTo;
	public EntityCrystalSkills(World world) {
		super(world);
		this.dataManager.register(SELECTED, false);
	}
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setHealth(this.getMaxHealth());
		for (SkillBase skill : this.skills.values()) {
			skill.create(this);
		}
		return livingdata;
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("selected", this.isSelected());
        for (SkillBase skill : this.skills.values()) {
			skill.write(this, compound);
		}
	}
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setSelected(compound.getBoolean("selected"));
		for (SkillBase skill : this.skills.values()) {
			skill.read(this, compound);
		}
	}
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		boolean result = false;
		ItemStack stack = player.getHeldItem(hand);
		for (Class<? extends SkillBase> skillClass : SKILLS) {
			try {
				SkillBase skill;
				if (this.skills.containsKey(skillClass)) {
					skill = this.skills.get(skillClass);
				}
				else {
					skill = skillClass.newInstance();
				}
				if (skill.interact(this, player, hand, stack)) {
					this.addSkill(skill, player);
					result = true;
				}
			}
			catch (Exception ex) {
				CrashReport.makeCrashReport(ex, "Something went wrong loading skills.");
			}
		}
		return result ? result : super.processInteract(player, hand);
	}
	public boolean spokenTo(EntityPlayer player, String message) {
		boolean result = false;
		this.lastPlayerSpokenTo = player;
		boolean canRunCommands = this.isSelected();
		for (String name : this.getNames(new ArrayList<String>())) {
			if (name != null) {
				canRunCommands = Pattern.compile("\\b" + name.toLowerCase() + "\\b").matcher(message.toLowerCase()).find() || canRunCommands;
			}
		}
		if (canRunCommands) {
			for (Class<? extends SkillBase> skillClass : SKILLS) {
				try {
					SkillBase skill = skillClass.newInstance();
					if (skill.speak(this, player, message)) {
						skill.commandingPlayer = player;
						this.addSkill(skill, player);
						result = true;
					}
				}
				catch (Exception ex) {
					CrashReport.makeCrashReport(ex, "Something went wrong loading skills.");
				}
			}
		}
		return result;
	}
	public void setCallibleNames(ArrayList<String> list) {
		list.add(this.getName());
	}
	public String[] getNames(ArrayList<String> list) {
		this.setCallibleNames(list);
		return list.toArray(new String[0]);
	}
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		for (int i = 0; i < this.skills.size(); ++i) {
			ArrayList<SkillBase> skills = new ArrayList<SkillBase>(Arrays.asList(this.skills.values().toArray(new SkillBase[0])));
			if (skills.get(i).readyForRemoval) {
				this.removeSkill(skills.get(i));
				--i;
			}
		}
		for (SkillBase skill : this.skills.values()) {
			skill.update(this);
			if (skill.continueExecuting(this)) {
				skill.run(this);
			}
			else {
				skill.resetTask(this);
			}
		}
		this.setGlowing(this.isSelected());
	}
	public void addSkill(SkillBase skill, EntityPlayer player) {
		skill.commandingPlayer = player;
		this.setSelected(false);
		this.skills.put(skill.getClass(), skill);
		if (skill.shouldExecute(this)) {
			skill.init(this);
		}
		KAGIC.instance.chatInfoMessage("Task Enlisted: " + skill.getClass().getSimpleName());
	}
	public void removeSkill(SkillBase skill) {
		this.skills.remove(skill.getClass());
	}
	public void feedback(String message) {
		for (EntityPlayer player : this.world.playerEntities) {
			if (player.getDistance(this) < 16) {
				String name = this.getName();
				new Thread(new Runnable() {
			        public void run()  {
			        	try {
			        		Thread.sleep(1000);
			        	} catch (InterruptedException e) { }
			        	finally {
			        		player.sendMessage(new TextComponentString("<" + name + "> " + message));
			        	}
			        }
				}).start();
			}
		}
	}
	public void feedback(EntityPlayer player, String message) {
		String name = this.getName();
		new Thread(new Runnable() {
	        public void run()  {
	        	try {
	        		Thread.sleep(1000);
	        	} catch (InterruptedException e) { }
	        	finally {
	        		player.sendMessage(new TextComponentString("<" + name + "> " + message));
	        	}
	        }
		}).start();
	}
	public boolean tryToMoveTo(int x, int y, int z) {
		return this.getNavigator().tryMoveToXYZ(x, y, z, 0.8F);
	}
	public boolean tryToMoveTo(BlockPos pos) {
		return this.tryToMoveTo(pos.getX(), pos.getY() + 1, pos.getZ());
	}
	public void lookAt(Entity entity) {
		if (entity != null) {
			this.getLookHelper().setLookPositionWithEntity(entity, 60.0F, 60.0F);
		}
	}
	public void lookAt(int x, int y, int z) {
		this.getLookHelper().setLookPosition(x, y, z, 60.0F, 60.0F);
	}
	public void lookAt(BlockPos pos) {
		this.lookAt(pos.getX(), pos.getY(), pos.getZ());
	}
	public boolean placeBlock(IBlockState block, int x, int y, int z) {
		boolean canPlace = this.world.mayPlace(block.getBlock(), new BlockPos(x, y, z), true, EnumFacing.UP, null);
		if (canPlace) {
			this.swingArm(EnumHand.MAIN_HAND);
			return this.world.setBlockState(new BlockPos(x, y, z), block, 3);
		}
		return false;
	}
	public boolean placeBlock(IBlockState block, BlockPos pos) {
		return this.placeBlock(block, pos.getX(), pos.getY(), pos.getZ());
	}
	public boolean tryToBreakBlock(int x, int y, int z) {
		IBlockState state = this.world.getBlockState(new BlockPos(x, y, z));
		if (!state.getMaterial().isToolNotRequired()) {
			return this.getHeldItemMainhand().canHarvestBlock(state);
		}
		return true;
	}
	public boolean tryToBreakBlock(BlockPos pos) {
		return this.tryToBreakBlock(pos.getX(), pos.getY(), pos.getZ());
	}
	public boolean breakBlock(int x, int y, int z) {
		if (this.tryToBreakBlock(x, y, z)) {
			this.swingArm(EnumHand.MAIN_HAND);
			return this.world.destroyBlock(new BlockPos(x, y, z), true);
		}
		return false;
	}
	public boolean breakBlock(BlockPos pos) {
		return this.breakBlock(pos.getX(), pos.getY(), pos.getZ());
	}
	public boolean isSelected() {
		return this.dataManager.get(SELECTED);
	}
	public void setSelected(boolean selected) {
		this.dataManager.set(SELECTED, selected);
	}
}
