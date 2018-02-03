package mod.akrivus.kagic.init;

import java.util.HashMap;

import mod.akrivus.kagic.entity.gem.EntityAgate;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import mod.akrivus.kagic.entity.gem.EntityAquamarine;
import mod.akrivus.kagic.entity.gem.EntityBismuth;
import mod.akrivus.kagic.entity.gem.EntityCarnelian;
import mod.akrivus.kagic.entity.gem.EntityHessonite;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.akrivus.kagic.entity.gem.EntityLapisLazuli;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.entity.gem.EntityPeridot;
import mod.akrivus.kagic.entity.gem.EntityRoseQuartz;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.gem.EntityRutile;
import mod.akrivus.kagic.entity.gem.EntitySapphire;
import mod.akrivus.kagic.entity.gem.EntityTopaz;
import mod.akrivus.kagic.entity.gem.EntityZircon;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class OreDictListener {
	// The purpose of this class is to catch oredict entries registered after we register the crux list
	// in ModEntities. This shouldn't be necessary, since oredict registration *should* be done in
	// preInit, but some mods (such as Simple Gravel Ores) do it in postInit for some reason.
	@SubscribeEvent
	public void onOreRegister(OreRegisterEvent event) {
		ItemStack ore = event.getOre();
		String name = event.getName();
		
		KAGIC.instance.logger.warn("Note! The mod " + ore.getItem().getRegistryName().getResourceDomain() + 
				" is registering the ore " + name + 
				" to the ore dictionary after preInit. This is a violation of best practices and should be reported to the mod author.");
		
		switch (name) {
		case "blockAgate":
			OreDictListener.registerOreDict(EntityAgate.AGATE_YIELDS, 5.99, ore);
			break;
		case "blockAmber":
			OreDictListener.registerOreDict(EntityPearl.PEARL_YIELDS, 5.99, ore);
			break;
		case "blockAmethyst":
			OreDictListener.registerOreDict(EntityAmethyst.AMETHYST_YIELDS, 5.99, ore);
			break;
		case "blockApatite":
			OreDictListener.registerOreDict(EntityRutile.RUTILE_YIELDS, 5.99, ore);
			break;
		case "blockAquamarine":
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 5.99, ore);
			break;
		case "blockBeryl":
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 5.99, ore);
			break;
		case "blockBismuth":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 5.99, ore);
			break;
		case "blockBlueTopaz":
			OreDictListener.registerOreDict(EntityTopaz.TOPAZ_YIELDS, 5.99, ore);
			break;
		case "blockBauxite":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 5.99, ore);
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 5.99, ore);
			break;
		case "blockCalcite":
			OreDictListener.registerOreDict(EntityPearl.PEARL_YIELDS, 5.99, ore);
			break;
		case "blockCarnelian":
			OreDictListener.registerOreDict(EntityCarnelian.CARNELIAN_YIELDS, 5.99, ore);
			break;
		case "blockChalcedony":
			OreDictListener.registerOreDict(EntityAgate.AGATE_YIELDS, 5.99, ore);
			break;
		case "blockChromite":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 5.99, ore);
			break;
		case "blockChromium":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 5.99, ore);
			break;
		case "blockCinnabar":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 5.99, ore);
			break;
		case "blockCoal":
			OreDictListener.registerOreDict(EntityPearl.PEARL_YIELDS, 5.99, ore);
			break;
		case "blockCorundum":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 5.99, ore);
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 5.99, ore);
			break;
		case "blockDiamond":
			OreDictListener.registerOreDict(EntityZircon.ZIRCON_YIELDS, 5.99, ore);
			break;
		case "blockEmerald":
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 5.99, ore);
			break;
		case "blockGalena":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 5.99, ore);
			break;
		case "blockGarnet":
			OreDictListener.registerOreDict(EntityHessonite.HESSONITE_YIELDS, 5.99, ore);
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 1.99, ore);
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 1.99, ore);
			break;
		case "blockGold":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 5.99, ore);
			break;
		case "blockGoldenBeryl":
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 5.99, ore);
			break;
		case "blockHeliodor":
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 5.99, ore);
			break;
		case "blockIron":
			OreDictListener.registerOreDict(EntityHessonite.HESSONITE_YIELDS, 0.55, ore);
			OreDictListener.registerOreDict(EntityPeridot.PERIDOT_YIELDS, 5.99, ore);
			break;
		case "blockJasper":
			OreDictListener.registerOreDict(EntityJasper.JASPER_YIELDS, 5.99, ore);
			break;
		case "blockLapis":
			OreDictListener.registerOreDict(EntityLapisLazuli.LAPIS_LAZULI_YIELDS, 5.99, ore);
			break;
		case "blockMorganite":
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 5.99, ore);
			break;
		case "blockOlivine":
			OreDictListener.registerOreDict(EntityPeridot.PERIDOT_YIELDS, 5.99, ore);
			break;
		case "blockOnyx":
			OreDictListener.registerOreDict(EntityAgate.AGATE_YIELDS, 5.99, ore);
			break;
		case "blockPearl":
			OreDictListener.registerOreDict(EntityPearl.PEARL_YIELDS, 5.99, ore);
			break;
		case "blockPeridot":
			OreDictListener.registerOreDict(EntityPeridot.PERIDOT_YIELDS, 5.99, ore);
			break;
		case "blockPlatinum":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 5.99, ore);
			break;
		case "blockPrismarine":
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 1.99, ore);
			OreDictListener.registerOreDict(EntityLapisLazuli.LAPIS_LAZULI_YIELDS, 0.44, ore);
			break;
		case "blockPyrite":
			OreDictListener.registerOreDict(EntityLapisLazuli.LAPIS_LAZULI_YIELDS, 5.99, ore);
			break;
		case "blockQuartz":
			OreDictListener.registerOreDict(EntityAgate.AGATE_YIELDS, 5.99, ore);
			OreDictListener.registerOreDict(EntityAmethyst.AMETHYST_YIELDS, 5.99, ore);
			OreDictListener.registerOreDict(EntityCarnelian.CARNELIAN_YIELDS, 5.99, ore);
			OreDictListener.registerOreDict(EntityJasper.JASPER_YIELDS, 5.99, ore);
			OreDictListener.registerOreDict(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 5.99, ore);
			break;
		case "blockRedstone":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 5.99, ore);
			OreDictListener.registerOreDict(EntityRutile.RUTILE_YIELDS, 5.99, ore);
			break;
		case "blockRoseQuartz":
			OreDictListener.registerOreDict(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 5.99, ore);
			break;
		case "blockRuby":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 5.99, ore);
			break;
		case "blockRutile":
			OreDictListener.registerOreDict(EntityRutile.RUTILE_YIELDS, 5.99, ore);
			break;
		case "blockSalt":
			OreDictListener.registerOreDict(EntityPearl.PEARL_YIELDS, 5.99, ore);
			break;
		case "blockSapphire":
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 5.99, ore);
			break;
		case "blockSilver":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 5.99, ore);
			break;
		case "blockSodalite":
			OreDictListener.registerOreDict(EntityLapisLazuli.LAPIS_LAZULI_YIELDS, 5.99, ore);
			break;
		case "blockSulfur":
			OreDictListener.registerOreDict(EntityTopaz.TOPAZ_YIELDS, 5.99, ore);
			break;
		case "blockTopaz":
			OreDictListener.registerOreDict(EntityTopaz.TOPAZ_YIELDS, 5.99, ore);
			break;
		case "blockTungsten":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 5.99, ore);
			break;
		case "blockVanadium":
			OreDictListener.registerOreDict(EntityPeridot.PERIDOT_YIELDS, 5.99, ore);
			break;
		case "blockVioletSapphire":
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 5.99, ore);
			break;
		case "blockZircon":
			OreDictListener.registerOreDict(EntityZircon.ZIRCON_YIELDS, 5.99, ore);
			break;
		case "endstone":
			OreDictListener.registerOreDict(EntityAgate.AGATE_YIELDS, 0.82, ore);
			OreDictListener.registerOreDict(EntityPearl.PEARL_YIELDS, 0.55, ore);
			OreDictListener.registerOreDict(EntityPeridot.PERIDOT_YIELDS, 0.99, ore);
			OreDictListener.registerOreDict(EntityTopaz.TOPAZ_YIELDS, 0.22, ore);
			break;
		case "glowstone":
			OreDictListener.registerOreDict(EntityRutile.RUTILE_YIELDS, 0.99, ore);
			OreDictListener.registerOreDict(EntityTopaz.TOPAZ_YIELDS, 0.99, ore);
			break;
		case "netherrack":
			OreDictListener.registerOreDict(EntityCarnelian.CARNELIAN_YIELDS, 0.77, ore);
			OreDictListener.registerOreDict(EntityPeridot.PERIDOT_YIELDS, 0.22, ore);
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 0.99, ore);
			break;
		case "obsidian":
			OreDictListener.registerOreDict(EntityAgate.AGATE_YIELDS, 0.99, ore);
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 0.66, ore);
			OreDictListener.registerOreDict(EntityPeridot.PERIDOT_YIELDS, 0.33, ore);
			break;
		case "oreAgate":
			OreDictListener.registerOreDict(EntityAgate.AGATE_YIELDS, 1.99, ore);
			break;
		case "oreAluminum":
			OreDictListener.registerOreDict(EntityHessonite.HESSONITE_YIELDS, 1.99, ore);
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 0.33, ore);
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 0.33, ore);
			break;
		case "oreAluminium":
			OreDictListener.registerOreDict(EntityHessonite.HESSONITE_YIELDS, 1.99, ore);
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 0.33, ore);
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 0.33, ore);
			break;
		case "oreAmber":
			OreDictListener.registerOreDict(EntityPearl.PEARL_YIELDS, 1.99, ore);
			break;
		case "oreAmethyst":
			OreDictListener.registerOreDict(EntityAmethyst.AMETHYST_YIELDS, 1.99, ore);
			break;
		case "oreApatite":
			OreDictListener.registerOreDict(EntityRutile.RUTILE_YIELDS, 1.99, ore);
			break;
		case "oreAquamarine":
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 1.99, ore);
			break;
		case "oreBeryl":
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 1.99, ore);
			break;
		case "oreBismuth":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 1.99, ore);
			break;
		case "oreBlueTopaz":
			OreDictListener.registerOreDict(EntityTopaz.TOPAZ_YIELDS, 1.99, ore);
			break;
		case "oreBauxite":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 1.99, ore);
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 1.99, ore);
			break;
		case "oreCalcite":
			OreDictListener.registerOreDict(EntityPearl.PEARL_YIELDS, 1.99, ore);
			break;
		case "oreCarnelian":
			OreDictListener.registerOreDict(EntityCarnelian.CARNELIAN_YIELDS, 1.99, ore);
			break;
		case "oreChalcedony":
			OreDictListener.registerOreDict(EntityAgate.AGATE_YIELDS, 1.99, ore);
			break;
		case "oreChromite":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 1.99, ore);
			break;
		case "oreChromium":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 1.99, ore);
			break;
		case "oreCinnabar":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 1.99, ore);
			break;
		case "oreCoal":
			OreDictListener.registerOreDict(EntityPearl.PEARL_YIELDS, 0.99, ore);
			break;
		case "oreCopper":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 0.33, ore);
			OreDictListener.registerOreDict(EntityPeridot.PERIDOT_YIELDS, 0.33, ore);
			break;
		case "oreCorundum":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 1.99, ore);
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 1.99, ore);
			break;
		case "oreDiamond":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 0.99, ore);
			OreDictListener.registerOreDict(EntityZircon.ZIRCON_YIELDS, 1.99, ore);
			break;
		case "oreEmerald":
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 1.99, ore);
			break;
		case "oreGalena":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 1.99, ore);
			break;
		case "oreGarnet":
			OreDictListener.registerOreDict(EntityHessonite.HESSONITE_YIELDS, 1.99, ore);
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 0.99, ore);
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 0.99, ore);
			break;
		case "oreGold":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 0.99, ore);
			OreDictListener.registerOreDict(EntityTopaz.TOPAZ_YIELDS, 0.45, ore);
			break;
		case "oreGoldenBeryl":
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 1.99, ore);
			break;
		case "oreHeliodor":
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 1.99, ore);
			break;
		case "oreIron":
			OreDictListener.registerOreDict(EntityAmethyst.AMETHYST_YIELDS, 0.66, ore);
			OreDictListener.registerOreDict(EntityCarnelian.CARNELIAN_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntityHessonite.HESSONITE_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntityJasper.JASPER_YIELDS, 0.33, ore);
			OreDictListener.registerOreDict(EntityPeridot.PERIDOT_YIELDS, 0.99, ore);
			OreDictListener.registerOreDict(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 0.77, ore);
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 0.22, ore);
			break;
		case "oreJasper":
			OreDictListener.registerOreDict(EntityJasper.JASPER_YIELDS, 1.99, ore);
			break;
		case "oreLapis":
			OreDictListener.registerOreDict(EntityLapisLazuli.LAPIS_LAZULI_YIELDS, 1.99, ore);
			break;
		case "oreLead":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 0.33, ore);
			break;
		case "oreMorganite":
			OreDictListener.registerOreDict(EntityAquamarine.AQUAMARINE_YIELDS, 1.99, ore);
			break;
		case "oreOlivine":
			OreDictListener.registerOreDict(EntityPeridot.PERIDOT_YIELDS, 1.99, ore);
			break;
		case "oreOnyx":
			OreDictListener.registerOreDict(EntityAgate.AGATE_YIELDS, 1.99, ore);
			break;
		case "orePearl":
			OreDictListener.registerOreDict(EntityPearl.PEARL_YIELDS, 1.99, ore);
			break;
		case "orePeridot":
			OreDictListener.registerOreDict(EntityPeridot.PERIDOT_YIELDS, 1.99, ore);
			break;
		case "orePlatinum":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 1.99, ore);
			break;
		case "orePyrite":
			OreDictListener.registerOreDict(EntityLapisLazuli.LAPIS_LAZULI_YIELDS, 1.99, ore);
			break;
		case "oreQuartz":
			OreDictListener.registerOreDict(EntityAgate.AGATE_YIELDS, 1.99, ore);
			OreDictListener.registerOreDict(EntityAmethyst.AMETHYST_YIELDS, 1.99, ore);
			OreDictListener.registerOreDict(EntityCarnelian.CARNELIAN_YIELDS, 1.99, ore);
			OreDictListener.registerOreDict(EntityJasper.JASPER_YIELDS, 1.99, ore);
			OreDictListener.registerOreDict(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 1.99, ore);
			break;
		case "oreRedstone":
			OreDictListener.registerOreDict(EntityCarnelian.CARNELIAN_YIELDS, 0.22, ore);
			OreDictListener.registerOreDict(EntityPeridot.PERIDOT_YIELDS, 0.55, ore);
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 0.99, ore);
			OreDictListener.registerOreDict(EntityRutile.RUTILE_YIELDS, 0.99, ore);
			break;
		case "oreRoseQuartz":
			OreDictListener.registerOreDict(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 1.99, ore);
			break;
		case "oreRuby":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 1.99, ore);
			break;
		case "oreRutile":
			OreDictListener.registerOreDict(EntityRutile.RUTILE_YIELDS, 1.99, ore);
			break;
		case "oreSalt":
			OreDictListener.registerOreDict(EntityPearl.PEARL_YIELDS, 1.99, ore);
			break;
		case "oreSilver":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 1.99, ore);
			break;
		case "oreSodalite":
			OreDictListener.registerOreDict(EntityLapisLazuli.LAPIS_LAZULI_YIELDS, 1.99, ore);
			break;
		case "oreSulfur":
			OreDictListener.registerOreDict(EntityTopaz.TOPAZ_YIELDS, 1.99, ore);
			break;
		case "oreSapphire":
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 1.99, ore);
			break;
		case "oreTin":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 0.33, ore);
			break;
		case "oreTitanium":
			OreDictListener.registerOreDict(EntityRutile.RUTILE_YIELDS, 0.99, ore);
			break;
		case "oreTopaz":
			OreDictListener.registerOreDict(EntityTopaz.TOPAZ_YIELDS, 1.99, ore);
			break;
		case "oreTungsten":
			OreDictListener.registerOreDict(EntityBismuth.BISMUTH_YIELDS, 1.99, ore);
			break;
		case "oreVanadium":
			OreDictListener.registerOreDict(EntityPeridot.PERIDOT_YIELDS, 1.99, ore);
			break;
		case "oreVioletSapphire":
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 1.99, ore);
			break;
		case "oreZircon":
			OreDictListener.registerOreDict(EntityZircon.ZIRCON_YIELDS, 1.99, ore);
			break;
		case "sand":
			OreDictListener.registerOreDict(EntityPearl.PEARL_YIELDS, 0.77, ore);
			break;
		case "sandstone":
			OreDictListener.registerOreDict(EntityJasper.JASPER_YIELDS, 0.99, ore);
			break;
		case "stoneAndesite":
			OreDictListener.registerOreDict(EntityHessonite.HESSONITE_YIELDS, 0.33, ore);
			break;
		case "stoneAndesitePolished":
			OreDictListener.registerOreDict(EntityHessonite.HESSONITE_YIELDS, 0.33, ore);
			break;
		case "stoneDiorite":
			OreDictListener.registerOreDict(EntityAmethyst.AMETHYST_YIELDS, 0.44, ore);
			OreDictListener.registerOreDict(EntityCarnelian.CARNELIAN_YIELDS, 0.33, ore);
			OreDictListener.registerOreDict(EntityJasper.JASPER_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 0.11, ore);
			break;
		case "stoneDioritePolished":
			OreDictListener.registerOreDict(EntityAmethyst.AMETHYST_YIELDS, 0.44, ore);
			OreDictListener.registerOreDict(EntityCarnelian.CARNELIAN_YIELDS, 0.33, ore);
			OreDictListener.registerOreDict(EntityJasper.JASPER_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 0.11, ore);
			break;
		case "stoneGranite":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 0.99, ore);
			OreDictListener.registerOreDict(EntityTopaz.TOPAZ_YIELDS, 0.99, ore);
			break;
		case "stoneGranitePolished":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 0.99, ore);
			OreDictListener.registerOreDict(EntityTopaz.TOPAZ_YIELDS, 1.99, ore);
			break;
		case "stoneMarble":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 0.11, ore);
			break;
		case "stoneMarblePolished":
			OreDictListener.registerOreDict(EntityRuby.RUBY_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntitySapphire.SAPPHIRE_YIELDS, 0.11, ore);
			break;
		case "stoneLimestone":
			OreDictListener.registerOreDict(EntityAgate.AGATE_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntityAmethyst.AMETHYST_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntityCarnelian.CARNELIAN_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntityJasper.JASPER_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 0.11, ore);
			break;
		case "stoneLimestonePolished":
			OreDictListener.registerOreDict(EntityAgate.AGATE_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntityAmethyst.AMETHYST_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntityCarnelian.CARNELIAN_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntityJasper.JASPER_YIELDS, 0.11, ore);
			OreDictListener.registerOreDict(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 0.11, ore);
			break;
		default:
			break;
		}
	}
	
	public static void registerOreDict(HashMap<IBlockState, Double> yields, double value, ItemStack ore) {
		if (ore.getItem() instanceof ItemBlock) {
			IBlockState blockState = ((ItemBlock) ore.getItem()).getBlock().getStateFromMeta(ore.getMetadata());
			yields.put(blockState, value);
		}
	}
}
