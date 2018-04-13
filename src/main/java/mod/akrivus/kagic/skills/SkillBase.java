package mod.akrivus.kagic.skills;

import mod.akrivus.kagic.entity.EntityCrystalSkills;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public class SkillBase<T extends EntityCrystalSkills> {
	public EntityPlayer commandingPlayer;
	public boolean isAllowedToRun = false;
	public boolean canBeStopped;
	public boolean killsOnEnd;
	public boolean readyForRemoval;
	public boolean notified;
	public enum RunWith {
		EVERYTHING,
		TARGETTING,
		LOOKING,
		ATTACKING,
		SWIMMING,
		RESTING,
		NOTHING
	}
	public enum Priority {
		CORE,
		HIGH,
		NORMAL,
		LOW
	}
	private Priority priority = Priority.NORMAL;
	private boolean isTask;
	private int mutexMask;
	
	/**
	 * Gets the load order, or priority, of the task.
	 * @return the skill's priority.
	 */
	public Priority priority() {
		return this.priority;
	}
	/**
	 * Sets the load order, or priority, of the task.
	 * @param priority the set load order or priority.
	 */
	public void priority(Priority priority) {
		this.priority = priority;
	}
	/**
	 * Gets what tasks the skill can run with.
	 * @return the task the skill can run with.
	 */
	public RunWith runsWith() {
		return RunWith.values()[this.mutexMask];
	}
	/**
	 * Sets what tasks the skill can run with.
	 * @param runWith the types of tasks the skill can run with.
	 */
	public void can(RunWith runWith) {
		this.mutexMask = runWith.ordinal();
	}
	/**
	 * Gets if the skill is a task.
	 * @return true means the skill is a task.
	 */
	public boolean isTask() {
		return this.isTask;
	}
	/**
	 * Sets if the skill is a task.
	 * @param task true means the skill is a task.
	 */
	public void task(boolean task) {
		this.isTask = task;
	}
	/**
	 * Called on entity creation.
	 * @param gem the entity the skill is being used by.
	 */
	public void create(T gem) {
		
	}
	/**
	 * Called on entity loading.
	 * @param gem the entity the skill is being used by.
	 * @param compound the NBT tag compound of the entity.
	 */
	public void read(T gem, NBTTagCompound compound) {
		
	}
	/**
	 * Called on entity saving.
	 * @param gem the entity the skill is being used by.
	 * @param compound the NBT tag compound of the entity.
	 */
	public void write(T gem, NBTTagCompound compound) {
		
	}
	/**
	 * Called on entity interaction (right click.)
	 * @param gem the entity the skill is being used by.
	 * @param player the player interacting.
	 * @param hand the hand used to interact.
	 * @param stack the stack in hand; can be empty.
	 * @return true means the interaction passed.
	 */
	public boolean interact(T gem, EntityPlayer player, EnumHand hand, ItemStack stack) {
		return false;
	}
	/**
	 * Called on entity voice interaction.
	 * @param gem the entity the skill is being used by.
	 * @param player the player speaking.
	 * @param message the message spoken.
	 * @return true means the voice interaction was correct.
	 */
	public boolean speak(T gem, EntityPlayer player, String message) {
		return false;
	}
	/**
	 * Called on entity picking up items.
	 * @param gem the entity the skill is being used by.
	 * @param stack the stack picked up.
	 * @return true means the item was picked up.
	 */
	public boolean item(T gem, ItemStack stack) {
		return false;
	}
	/**
	 * Called on entity update.
	 * @param gem the entity the skill is being used by.
	 */
	public void update(T gem) {
		
	}
	/**
	 * Called when the skill is ran through the entity's task manager for the first time.
	 * @param gem the entity the skill is being used by.
	 * @return true means the task will run.
	 */
	public boolean triggered(T gem) {
		return this.isAllowedToRun;
	}
	/**
	 * Called when the skill is ran through the entity's task manager after the first time.
	 * @param gem the entity the skill is being used by.
	 * @return true means the task will run.
	 */
	public boolean proceed(T gem) {
		return false;
	}
	/**
	 * Called when the skill is ran for the first time in a cycle.
	 * @param gem the entity the skill is being used by.
	 */
	public void init(T gem) {
		
	}
	/**
	 * Called when the skill is ran continuously after the first time.
	 * @param gem the entity the skill is being used by.
	 */
	public void run(T gem) {
		
	}
	/**
	 * Called when the skill is halted.
	 * @param gem the entity the skill is being used by.
	 */
	public void reset(T gem) {
		
	}
	/**
	 * Called to handle gems speaking.
	 * @param gem the entity the skill is being used by.
	 * @param message the message being sent.
	 */
	public void feedback(T gem, String message) {
		gem.feedback(message);
		this.notified = true;
	}
	@Deprecated
	public boolean shouldExecute(T gem) {
		try {
			return this.triggered(gem) && this.isAllowedToRun;
		}
		catch (ClassCastException e) {
			return false;
		}
	}
	@Deprecated
	public boolean continueExecuting(T gem) {
		try {
			return this.proceed(gem) && this.isAllowedToRun;
		}
		catch (ClassCastException e) {
			return false;
		}
	}
	@Deprecated
	public void resetTask(T gem) {
		gem.getNavigator().clearPath();
		try {
			this.isAllowedToRun = false;
			this.readyForRemoval = true;
			this.notified = false;
			this.reset(gem);
		}
		catch (ClassCastException e) {
			this.isAllowedToRun = false;
			this.readyForRemoval = true;
			this.notified = false;
		}
	}
}
