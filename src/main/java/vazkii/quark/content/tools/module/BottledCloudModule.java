package vazkii.quark.content.tools.module;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.base.module.QuarkModule;
import vazkii.quark.base.module.config.Config;
import vazkii.quark.content.tools.block.CloudBlock;
import vazkii.quark.content.tools.block.be.CloudBlockEntity;
import vazkii.quark.content.tools.client.render.be.CloudRenderer;
import vazkii.quark.content.tools.item.BottledCloudItem;

@LoadModule(category = ModuleCategory.TOOLS, hasSubscriptions = true)
public class BottledCloudModule extends QuarkModule {

	public static BlockEntityType<CloudBlockEntity> blockEntityType;
	public static Block cloud;
	public static Item bottled_cloud;
	
	@Config
	public static int cloudLevelBottom = 191;
	
	@Config 
	public static int cloudLevelTop = 196;

	@Override
	public void register() {
		cloud = new CloudBlock(this);
		bottled_cloud = new BottledCloudItem(this);
		
		blockEntityType = BlockEntityType.Builder.of(CloudBlockEntity::new, cloud).build(null);
		RegistryHelper.register(blockEntityType, "cloud", Registry.BLOCK_ENTITY_TYPE_REGISTRY);
	} 
	
	@Override
	public void clientSetup() {
		BlockEntityRenderers.register(blockEntityType, CloudRenderer::new);
	}
	
	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent.RightClickItem event) {
		ItemStack stack = event.getItemStack();
		Player player = event.getPlayer();
		if(stack.getItem() == Items.GLASS_BOTTLE && player.getY() > cloudLevelBottom && player.getY() < cloudLevelTop) {
			stack.shrink(1);
			
			ItemStack returnStack = new ItemStack(bottled_cloud);
			if(!player.addItem(returnStack))
				player.drop(returnStack, false);
			
			event.setCanceled(true);
			event.setCancellationResult(InteractionResult.SUCCESS);
		}
	}
	
}
