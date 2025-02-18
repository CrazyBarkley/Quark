package vazkii.quark.content.tweaks.module;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.quark.base.Quark;
import vazkii.quark.base.item.QuarkItem;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.base.module.QuarkModule;
import vazkii.quark.content.tweaks.recipe.ElytraDuplicationRecipe;

@LoadModule(category = ModuleCategory.TWEAKS, hasSubscriptions = true)
public class DragonScalesModule extends QuarkModule {

	public static Item dragon_scale;

	@Override
	public void register() {
		ForgeRegistries.RECIPE_SERIALIZERS.register(Quark.MOD_ID + ":elytra_duplication", ElytraDuplicationRecipe.SERIALIZER);

		dragon_scale = new QuarkItem("dragon_scale", this, new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
	}

	@SubscribeEvent
	public void onEntityTick(LivingUpdateEvent event) {
		if(event.getEntityLiving() instanceof EnderDragon dragon && !event.getEntity().getCommandSenderWorld().isClientSide) {
			if(dragon.getDragonFight() != null && dragon.getDragonFight().hasPreviouslyKilledDragon() && dragon.dragonDeathTime == 100) {
				Vec3 pos = dragon.position();
				ItemEntity item = new ItemEntity(dragon.level, pos.x, pos.y, pos.z, new ItemStack(dragon_scale, 1));
				dragon.level.addFreshEntity(item);
			}
		}
	}

}
