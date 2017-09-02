package mod.akrivus.kagic.init;

import java.util.HashMap;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.EntityLaser;
import mod.akrivus.kagic.entity.EntitySlag;
import mod.akrivus.kagic.entity.gem.EntityAgate;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import mod.akrivus.kagic.entity.gem.EntityAquamarine;
import mod.akrivus.kagic.entity.gem.EntityBismuth;
import mod.akrivus.kagic.entity.gem.EntityBlueDiamond;
import mod.akrivus.kagic.entity.gem.EntityCarnelian;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.akrivus.kagic.entity.gem.EntityLapisLazuli;
import mod.akrivus.kagic.entity.gem.EntityPadparadscha;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.entity.gem.EntityPeridot;
import mod.akrivus.kagic.entity.gem.EntityRoseQuartz;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.gem.EntityRutile;
import mod.akrivus.kagic.entity.gem.EntitySapphire;
import mod.akrivus.kagic.entity.gem.EntityTopaz;
import mod.akrivus.kagic.entity.gem.EntityYellowDiamond;
import mod.akrivus.kagic.entity.gem.EntityZircon;
import mod.akrivus.kagic.entity.pepo.EntityCactus;
import mod.akrivus.kagic.entity.pepo.EntityMelon;
import mod.akrivus.kagic.entity.pepo.EntityPumpkin;
import mod.akrivus.kagic.entity.vehicles.EntityRoamingEye;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class ModEntities {
	public static final HashMap<String, Class<? extends EntityGem>> GEMS = new HashMap<String, Class<? extends EntityGem>>();
	private static int currentID = 0;
	public static void register() {
		registerGem("ruby", EntityRuby.class, 0xE52C5C, 0x3A0015);
		registerGem("sapphire", EntitySapphire.class, 0xBAF5FD, 0x7298EC);
		registerGem("padparadscha", EntityPadparadscha.class, 0xFF8065, 0xFFB3A4);
		registerGem("pearl", EntityPearl.class, 0xFCCCB1, 0x92EAD9);
		registerGem("bismuth", EntityBismuth.class, 0x91A8CF, 0x9C5867);
		registerGem("peridot", EntityPeridot.class, 0x98FF72, 0x13BA54);
		registerGem("jasper", EntityJasper.class, 0xF89E57, 0xE13941);
		registerGem("amethyst", EntityAmethyst.class, 0xEAE2FF, 0xC49EDB);
		registerGem("rose_quartz", EntityRoseQuartz.class, 0xFEDED3, 0xE99CBE);
		registerGem("lapis_lazuli", EntityLapisLazuli.class, 0x4FEEFB, 0x1B6AD6);
		registerGem("carnelian", EntityCarnelian.class, 0xC13178, 0x510245);
		registerGem("agate", EntityAgate.class, 0xB3DEFF, 0x1B50D0);
		registerGem("aquamarine", EntityAquamarine.class, 0x8AFFFF, 0x0487E3);
		registerGem("topaz", EntityTopaz.class, 0xF5FC51, 0xFDFEA4);
		registerGem("rutile", EntityRutile.class, 0xD2508C, 0x23020D);
		registerGem("zircon", EntityZircon.class, 0x458FBE, 0x57C7CF);
		registerDiamond("yellow_diamond", EntityYellowDiamond.class);//, 0xFEFF6E, 0xFCFF22);
		registerDiamond("blue_diamond", EntityBlueDiamond.class);//, 0x2E3674, 0x2AA1FB);
		registerMob("melon", EntityMelon.class, 0xB5B128, 0x5A671A);
		registerMob("pumpkin", EntityPumpkin.class, 0xD58116, 0x744E03);
		registerMob("cactus", EntityCactus.class, 0x138622, 0xD9DB9F);
		registerMob("slag", EntitySlag.class, 0xFFFFFF, 0x00FF5D);
		registerEntity("roaming_eye", EntityRoamingEye.class);
		registerEntity("laser", EntityLaser.class);
		registerGemYields();
		registerGemAddons();
	}
	public static void registerGemYields() {
		EntityRuby.RUBY_YIELDS.put(Blocks.IRON_ORE, 0.77);
		EntityRuby.RUBY_YIELDS.put(Blocks.LAVA, 0.55);
		EntityRuby.RUBY_YIELDS.put(Blocks.MAGMA, 0.77);
		EntityRuby.RUBY_YIELDS.put(Blocks.NETHERRACK, 0.99);
		EntityRuby.RUBY_YIELDS.put(Blocks.REDSTONE_ORE, 0.99);
		EntityRuby.RUBY_YIELDS.put(Blocks.STONE, 0.99);
		EntitySapphire.SAPPHIRE_YIELDS.put(Blocks.ICE, 0.99);
		EntitySapphire.SAPPHIRE_YIELDS.put(Blocks.IRON_ORE, 0.22);
		EntitySapphire.SAPPHIRE_YIELDS.put(Blocks.PACKED_ICE, 0.99);
		EntitySapphire.SAPPHIRE_YIELDS.put(Blocks.SNOW, 0.77);
		EntitySapphire.SAPPHIRE_YIELDS.put(Blocks.SNOW_LAYER, 0.44);
		EntitySapphire.SAPPHIRE_YIELDS.put(Blocks.STONE, 0.33);
		EntityPearl.PEARL_YIELDS.put(Blocks.BONE_BLOCK, 0.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.CLAY, 0.77);
		EntityPearl.PEARL_YIELDS.put(Blocks.COAL_ORE, 0.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.END_STONE, 0.55);
		EntityPearl.PEARL_YIELDS.put(Blocks.GRASS, 0.22);
		EntityPearl.PEARL_YIELDS.put(Blocks.LEAVES, 0.33);
		EntityPearl.PEARL_YIELDS.put(Blocks.LEAVES2, 0.33);
		EntityPearl.PEARL_YIELDS.put(Blocks.LOG, 0.33);
		EntityPearl.PEARL_YIELDS.put(Blocks.LOG2, 0.33);
		EntityPearl.PEARL_YIELDS.put(Blocks.SAND, 0.77);
		EntityPearl.PEARL_YIELDS.put(Blocks.SOUL_SAND, 0.44);
		EntityPearl.PEARL_YIELDS.put(Blocks.WATER, 0.55);
		EntityBismuth.BISMUTH_YIELDS.put(Blocks.DIAMOND_ORE, 0.99);
		EntityBismuth.BISMUTH_YIELDS.put(Blocks.GOLD_BLOCK, 0.99);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.EMERALD_ORE, 0.11);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.IRON_ORE, 0.99);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.LAVA, 0.33);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.MAGMA, 0.99);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.NETHERRACK, 0.22);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.OBSIDIAN, 0.33);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.QUARTZ_ORE, 0.11);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.REDSTONE_ORE, 0.55);
		EntityJasper.JASPER_YIELDS.put(Blocks.CLAY, 0.22);
		EntityJasper.JASPER_YIELDS.put(Blocks.HARDENED_CLAY, 0.44);
		EntityJasper.JASPER_YIELDS.put(Blocks.IRON_ORE, 0.33);
		EntityJasper.JASPER_YIELDS.put(Blocks.QUARTZ_ORE, 0.99);
		EntityJasper.JASPER_YIELDS.put(Blocks.RED_SANDSTONE, 0.99);
		EntityJasper.JASPER_YIELDS.put(Blocks.SANDSTONE, 0.99);
		EntityJasper.JASPER_YIELDS.put(Blocks.STAINED_HARDENED_CLAY, 0.99);
		EntityJasper.JASPER_YIELDS.put(Blocks.STONE, 0.11);
		EntityAmethyst.AMETHYST_YIELDS.put(Blocks.IRON_ORE, 0.66);
		EntityAmethyst.AMETHYST_YIELDS.put(Blocks.QUARTZ_ORE, 0.99);
		EntityAmethyst.AMETHYST_YIELDS.put(Blocks.STONE, 0.44);
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.IRON_ORE, 0.11);
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.LEAVES, 0.22);
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.LEAVES2, 0.22);
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.LOG, 0.22);
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.LOG2, 0.22);
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.QUARTZ_ORE, 0.66);
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.SANDSTONE, 0.33);
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.STONE, 0.11);
		EntityLapisLazuli.LAPIS_LAZULI_YIELDS.put(Blocks.LAPIS_ORE, 0.99);
		EntityLapisLazuli.LAPIS_LAZULI_YIELDS.put(Blocks.PRISMARINE, 0.44);
		EntityLapisLazuli.LAPIS_LAZULI_YIELDS.put(Blocks.WATER, 0.22);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.CLAY, 0.22);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.HARDENED_CLAY, 0.99);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.IRON_ORE, 0.11);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.NETHERRACK, 0.77);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.QUARTZ_ORE, 0.99);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.RED_SANDSTONE, 0.66);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.REDSTONE_ORE, 0.22);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.STONE, 0.33);
		EntityAgate.AGATE_YIELDS.put(Blocks.END_STONE, 0.82);
		EntityAgate.AGATE_YIELDS.put(Blocks.OBSIDIAN, 0.99);
		EntityAgate.AGATE_YIELDS.put(Blocks.QUARTZ_ORE, 0.99);
		EntityAgate.AGATE_YIELDS.put(Blocks.SOUL_SAND, 0.77);
		EntityAquamarine.AQUAMARINE_YIELDS.put(Blocks.ICE, 0.33);
		EntityAquamarine.AQUAMARINE_YIELDS.put(Blocks.OBSIDIAN, 0.66);
		EntityAquamarine.AQUAMARINE_YIELDS.put(Blocks.PACKED_ICE, 0.99);
		EntityAquamarine.AQUAMARINE_YIELDS.put(Blocks.PRISMARINE, 0.99);
		EntityAquamarine.AQUAMARINE_YIELDS.put(Blocks.WATER, 0.11);
		EntityTopaz.TOPAZ_YIELDS.put(Blocks.END_STONE, 0.22);
		EntityTopaz.TOPAZ_YIELDS.put(Blocks.GLOWSTONE, 0.99);
		EntityTopaz.TOPAZ_YIELDS.put(Blocks.GOLD_ORE, 0.45);
		EntityRutile.RUTILE_YIELDS.put(Blocks.GLOWSTONE, 0.99);
		EntityRutile.RUTILE_YIELDS.put(Blocks.LAVA, 0.50);
		EntityRutile.RUTILE_YIELDS.put(Blocks.MAGMA, 0.75);
		EntityRutile.RUTILE_YIELDS.put(Blocks.REDSTONE_ORE, 0.99);
		EntityZircon.ZIRCON_YIELDS.put(Blocks.DIAMOND_ORE, 0.99);
	}
	public static void registerGemAddons() {
		EntityAgate.AGATE_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/hair/holly.png"));
		EntityAgate.AGATE_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/hair/back.png"));
		EntityAgate.AGATE_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/hair/top.png"));
		EntityAgate.AGATE_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/hair/spikes.png"));
		EntityAgate.AGATE_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/hair/axis.png"));
		EntityAgate.AGATE_BAND_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/band/holly.png"));
		EntityAgate.AGATE_BAND_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/band/back.png"));
		EntityAgate.AGATE_BAND_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/band/top.png"));
		EntityAgate.AGATE_BAND_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/band/spikes.png"));
		EntityAgate.AGATE_BAND_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/band/axis.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/blue.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/brows.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/curly.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/messy.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/mop.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/sides.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/swoop.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/yellow.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/dumpy.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/flower.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/jumpsuit.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/leather.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/legacy.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/maid.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/seethrough.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/servant.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/spacesuit.png"));
		EntityZircon.ZIRCON_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/zircon/hair/cowl.png"));
		EntityZircon.ZIRCON_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/zircon/hair/combover.png"));
		EntityZircon.ZIRCON_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/zircon/hair/smooth.png"));
	}
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static <T extends EntityGem> void registerGem(String name, Class<T> entity, int back, int fore) {
		EntityRegistry.registerModEntity(new ResourceLocation("kagic:kagic." + name), entity, "kagic." + name, currentID, KAGIC.instance, 256, 1, true, back, fore);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			try {
				Class<Render<? extends T>> render = (Class<Render<? extends T>>) KAGIC.class.getClassLoader().loadClass("mod/akrivus/kagic/client/render/" + entity.getName().replaceAll(".+?Entity", "Render"));
				RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		GEMS.put(name, entity);
		++currentID;
	}
	
	//Just like registerGem, but no spawn egg
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static <T extends EntityGem> void registerDiamond(String name, Class<T> entity) {
		EntityRegistry.registerModEntity(new ResourceLocation("kagic:kagic." + name), entity, "kagic." + name, currentID, KAGIC.instance, 256, 1, true);
	 	if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			try {
				Class<Render<? extends T>> render = (Class<Render<? extends T>>) KAGIC.class.getClassLoader().loadClass("mod/akrivus/kagic/client/render/" + entity.getName().replaceAll(".+?Entity", "Render"));
				RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		GEMS.put(name, entity);
		++currentID;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
    public static <T extends EntityGem> void registerExternalGem(String prefix, String name, Class<T> entity, String renderpath, int back, int fore) {
        EntityRegistry.registerModEntity(new ResourceLocation(prefix + ":" + name), entity, name, currentID, KAGIC.instance, 256, 1, true, back, fore);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            try {
                Class<Render<? extends T>> render = (Class<Render<? extends T>>) KAGIC.class.getClassLoader().loadClass(renderpath);
                RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        GEMS.put(name, entity);
        ++currentID;
    }
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static <T extends Entity> void registerMob(String name, Class<T> entity, int back, int fore) {
		EntityRegistry.registerModEntity(new ResourceLocation("kagic:kagic." + name), entity, "kagic." + name, currentID, KAGIC.instance, 256, 1, true, back, fore);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			try {
				Class<Render<? extends T>> render = (Class<Render<? extends T>>) KAGIC.class.getClassLoader().loadClass("mod/akrivus/kagic/client/render/" + entity.getName().replaceAll(".+?Entity", "Render"));
				RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		++currentID;
	}
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static <T extends Entity> void registerEntity(String name, Class<T> entity) {
		EntityRegistry.registerModEntity(new ResourceLocation("kagic:kagic." + name), entity, "kagic." + name, currentID, KAGIC.instance, 256, 1, true);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			try {
				Class<Render<? extends T>> render = (Class<Render<? extends T>>) KAGIC.class.getClassLoader().loadClass("mod/akrivus/kagic/client/render/" + entity.getName().replaceAll(".+?Entity", "Render"));
				RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		++currentID;
	}
}
