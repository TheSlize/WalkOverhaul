package com.walkoverhaul.mixins;

import com.walkoverhaul.configs.CommonConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovementInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class WalkMixin {
    private int currentStamina = CommonConfig.staminaCount;
    private int recoveryCooldown = 0;
    private final Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "onLivingUpdate", at = @At("RETURN"))
    private void onLivingUpdate(CallbackInfo info) {
        EntityPlayerSP player = (EntityPlayerSP) (Object) this;
        MovementInput input = player.movementInput;
        boolean isTryingToSprint = player.isSprinting() || (mc.gameSettings.keyBindSprint.isKeyDown() && input.moveForward > 0);
        if (!player.capabilities.isFlying) {
            if (isTryingToSprint) {
                if (currentStamina > 0) {
                    currentStamina--;
                    if (input.moveForward != 0 && input.moveStrafe == 0) {
                        player.motionX *= CommonConfig.sprintMultiplier / 100.0F;
                        player.motionZ *= CommonConfig.sprintMultiplier / 100.0F;
                    }
                } else {
                    player.setSprinting(false);
                    if (input.moveForward != 0 && input.moveStrafe == 0) {
                        player.motionX *= CommonConfig.walkMultiplier / 100.0F;
                        player.motionZ *= CommonConfig.walkMultiplier / 100.0F;
                    }
                    recoveryCooldown = CommonConfig.recoveryTime + CommonConfig.staminaCount;
                }
            } else {
                if (input.moveForward != 0 && input.moveStrafe == 0) {
                    player.motionX *= CommonConfig.walkMultiplier / 100.0F;
                    player.motionZ *= CommonConfig.walkMultiplier / 100.0F;
                }
                if (recoveryCooldown > 0) {
                    recoveryCooldown--;
                    if (input.moveForward != 0 && input.moveStrafe == 0 && mc.gameSettings.keyBindSprint.isKeyDown()) {
                        player.motionX *= CommonConfig.walkMultiplier / 100.0F;
                        player.motionZ *= CommonConfig.walkMultiplier / 100.0F;
                    }
                } else if (currentStamina < CommonConfig.staminaCount) {
                    currentStamina++;
                }
            }
            if (!isTryingToSprint && recoveryCooldown > 0) {
                recoveryCooldown--;
            }
        }
    }
}
