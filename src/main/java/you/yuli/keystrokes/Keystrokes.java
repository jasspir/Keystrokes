package you.yuli.keystrokes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Keystrokes implements ModInitializer {
    public static MinecraftClient client;
    public static Identifier nw;
    public static Identifier ne;
    public static Identifier sw;
    public static Identifier se;
    public static long lastDamaged;
    public static boolean toggled;

    @Override
    public void onInitialize() {
        client = MinecraftClient.getInstance();
        nw = Identifier.of("keystrokes", "nw.png");
        ne = Identifier.of("keystrokes", "ne.png");
        sw = Identifier.of("keystrokes", "sw.png");
        se = Identifier.of("keystrokes", "se.png");

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, a) ->
                dispatcher.register(ClientCommandManager.literal("keystrokes").executes(context -> toggle())));
    }

    public int toggle() {
        toggled = !toggled;
        MinecraftClient.getInstance().player.sendMessage(Text.of("§7Keystrokes " + (toggled ? "enabled" : "disabled")));
        return 1;
    }
}
