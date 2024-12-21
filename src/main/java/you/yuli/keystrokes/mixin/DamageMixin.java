package you.yuli.keystrokes.mixin;

import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntityDamageS2CPacket;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import you.yuli.keystrokes.Keystrokes;

@Mixin(EntityDamageS2CPacket.class)
public abstract class DamageMixin {
    @Shadow @Final private int entityId;

    @Inject(at = @At("TAIL"), method = "apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V")
    public void apply(ClientPlayPacketListener clientPlayPacketListener, CallbackInfo ci) {
        if (Keystrokes.client.player != null && entityId == Keystrokes.client.player.getId()) Keystrokes.lastDamaged = System.currentTimeMillis();
    }
}