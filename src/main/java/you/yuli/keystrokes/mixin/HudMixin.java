package you.yuli.keystrokes.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import you.yuli.keystrokes.Direction;
import you.yuli.keystrokes.Keystrokes;
import you.yuli.keystrokes.Type;

@Mixin(value = InGameHud.class)
public abstract class HudMixin {
    @Shadow
    public abstract TextRenderer getTextRenderer();

    @Inject(at = @At("TAIL"), method = "render")
    public void render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        Window window = client.getWindow();
        if (window == null || !Keystrokes.toggled) return;

        int baseX = window.getScaledWidth() / 2 - 3;
        int baseY = 31;
        int margin = 15;

        if (Keystrokes.client.options.forwardKey.isPressed()) display(Type.FORWARD, context, baseX, baseY, margin);
        if (Keystrokes.client.options.backKey.isPressed()) display(Type.BACKWARD, context, baseX, baseY, margin);
        if (Keystrokes.client.options.leftKey.isPressed()) display(Type.LEFT, context, baseX, baseY, margin);
        if (Keystrokes.client.options.rightKey.isPressed()) display(Type.RIGHT, context, baseX, baseY, margin);
        if (Keystrokes.client.options.attackKey.isPressed()) display(Type.ATTACK, context, baseX, baseY, margin);
        if (System.currentTimeMillis() - Keystrokes.lastDamaged <= 250) display(Type.DAMAGE, context, baseX, baseY, margin);
        if (Keystrokes.client.options.sneakKey.isPressed()) display(Type.SNEAK, context, baseX, baseY, margin);
        else if (Keystrokes.client.options.jumpKey.isPressed()) display(Type.JUMP, context, baseX, baseY, margin);
    }

    @Unique
    private void display(Type type, DrawContext context, int baseX, int baseY, int margin) {
        switch (type) {
            case FORWARD -> context.drawTextWithShadow(this.getTextRenderer(), Text.of("W"), baseX, baseY - margin, 0xFFFFFF);
            case BACKWARD -> context.drawTextWithShadow(this.getTextRenderer(), Text.of("S"), baseX, baseY, 0xFFFFFF);
            case LEFT -> context.drawTextWithShadow(this.getTextRenderer(), Text.of("A"), baseX - margin, baseY, 0xFFFFFF);
            case RIGHT -> context.drawTextWithShadow(this.getTextRenderer(), Text.of("D"), baseX + margin, baseY, 0xFFFFFF);
            case JUMP -> context.drawTextWithShadow(this.getTextRenderer(), Text.of("↑"), baseX, baseY + margin, 0xFFFFFF);
            case SNEAK -> context.drawTextWithShadow(this.getTextRenderer(), Text.of("↓"), baseX, baseY + margin, 0xFFFFFF);
            case ATTACK -> context.drawTextWithShadow(this.getTextRenderer(), Text.of("\uD83D\uDDE1"), baseX - margin, baseY + margin, 0xFFFFFF);
            case DAMAGE -> context.drawTextWithShadow(this.getTextRenderer(), Text.of("⚔"), baseX + margin, baseY + margin, 0xFFFFFF);
        }
    }

//    private void compassDisplay(Direction direction, DrawContext context, int baseX, int baseY, int margin, int diagonalMargin) {
//        switch (direction) {
//            case NORTH -> context.drawTextWithShadow(this.getTextRenderer(), Text.of("W"), baseX, baseY - margin, 0xFFFFFF);
//            case SOUTH -> context.drawTextWithShadow(this.getTextRenderer(), Text.of("S"), baseX, baseY + margin, 0xFFFFFF);
//            case WEST -> context.drawTextWithShadow(this.getTextRenderer(), Text.of("A"), baseX - margin, baseY, 0xFFFFFF);
//            case EAST -> context.drawTextWithShadow(this.getTextRenderer(), Text.of("D"), baseX + margin, baseY, 0xFFFFFF);
//            case NORTHWEST -> context.drawTexture(Keystrokes.nw, baseX - diagonalMargin, baseY - diagonalMargin, 0, 0, 7, 7, 7, 7);
//            case NORTHEAST -> context.drawTexture(Keystrokes.ne, baseX + diagonalMargin, baseY - diagonalMargin, 0, 0, 7, 7, 7, 7);
//            case SOUTHWEST -> context.drawTexture(Keystrokes.sw, baseX - diagonalMargin, baseY + diagonalMargin, 0, 0, 7, 7, 7, 7);
//            case SOUTHEAST -> context.drawTexture(Keystrokes.se, baseX + diagonalMargin, baseY + diagonalMargin, 0, 0, 7, 7, 7, 7);
//            case UP -> context.drawTextWithShadow(this.getTextRenderer(), Text.of("×"), baseX, baseY, 0xFFFFFF);
//        }
//
//        if (n && !w && !e) display(Direction.NORTH, context, baseX, baseY, margin, diagonalMargin);
//        else if (s && !w && !e) display(Direction.SOUTH, context, baseX, baseY, margin, diagonalMargin);
//        else if (w && !n && !s) display(Direction.WEST, context, baseX, baseY, margin, diagonalMargin);
//        else if (e && !n && !s) display(Direction.EAST, context, baseX, baseY, margin, diagonalMargin);
//        else if (n && w) display(Direction.NORTHWEST, context, baseX, baseY, margin, diagonalMargin);
//        else if (n) display(Direction.NORTHEAST, context, baseX, baseY, margin, diagonalMargin);
//        else if (s && w) display(Direction.SOUTHWEST, context, baseX, baseY, margin, diagonalMargin);
//        else if (s) display(Direction.SOUTHEAST, context, baseX, baseY, margin, diagonalMargin);
//        if (u) display(Direction.UP, context, baseX, baseY, margin, diagonalMargin);
//    }
}