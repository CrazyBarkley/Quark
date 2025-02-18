package vazkii.quark.base.network;

import net.minecraft.network.protocol.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import vazkii.arl.network.IMessage;
import vazkii.arl.network.NetworkHandler;
import vazkii.quark.base.Quark;
import vazkii.quark.base.network.message.*;
import vazkii.quark.base.network.message.oddities.HandleBackpackMessage;
import vazkii.quark.base.network.message.oddities.MatrixEnchanterOperationMessage;
import vazkii.quark.base.network.message.oddities.ScrollCrateMessage;

public final class QuarkNetwork {

	private static final int PROTOCOL_VERSION = 1;

	private static NetworkHandler network;

	public static void setup() {
		network = new NetworkHandler(Quark.MOD_ID, PROTOCOL_VERSION);

		network.register(SortInventoryMessage.class, NetworkDirection.PLAY_TO_SERVER);
		network.register(InventoryTransferMessage.class, NetworkDirection.PLAY_TO_SERVER);
		network.register(DoubleDoorMessage.class, NetworkDirection.PLAY_TO_SERVER);
		network.register(HarvestMessage.class, NetworkDirection.PLAY_TO_SERVER);
		network.register(RequestEmoteMessage.class, NetworkDirection.PLAY_TO_SERVER);
		network.register(LinkItemMessage.class, NetworkDirection.PLAY_TO_SERVER);
		network.register(ChangeHotbarMessage.class, NetworkDirection.PLAY_TO_SERVER);
		network.register(SetLockProfileMessage.class, NetworkDirection.PLAY_TO_SERVER);

		network.register(HandleBackpackMessage.class, NetworkDirection.PLAY_TO_SERVER);
		network.register(MatrixEnchanterOperationMessage.class, NetworkDirection.PLAY_TO_SERVER);
		network.register(ScrollCrateMessage.class, NetworkDirection.PLAY_TO_SERVER);

		network.register(DoEmoteMessage.class, NetworkDirection.PLAY_TO_CLIENT);
		network.register(SpamlessChatMessage.class, NetworkDirection.PLAY_TO_CLIENT);
		network.register(EditSignMessage.class, NetworkDirection.PLAY_TO_CLIENT);
		network.register(UpdateTridentMessage.class, NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void sendToPlayer(IMessage msg, ServerPlayer player) {
		network.sendToPlayer(msg, player);
	}

	public static void sendToServer(IMessage msg) {
		network.sendToServer(msg);
	}

	public static void sendToPlayers(IMessage msg, Iterable<ServerPlayer> players) {
		for(ServerPlayer player : players)
			network.sendToPlayer(msg, player);
	}

	public static void sendToAllPlayers(IMessage msg, MinecraftServer server) {
		sendToPlayers(msg, server.getPlayerList().getPlayers());
	}

	public static Packet<?> toVanillaPacket(IMessage msg, NetworkDirection direction) {
		return network.channel.toVanillaPacket(msg, direction);
	}

}
