package mod.akrivus.kagic.entity.pepo;

import mod.akrivus.kagic.entity.EntityPepo;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMelon extends EntityPepo {
	public EntityMelon(World worldIn) {
		super(new ItemStack(Items.MELON), worldIn);
	}
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote) {
			int total = this.rand.nextInt(5) + 3;
			for (int i = 0; i < total; ++i) {
				this.entityDropItem(this.dropItem, 0.0F);
			}
		}
	}
}
