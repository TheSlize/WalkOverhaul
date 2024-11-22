package com.walkoverhaul.mixins;

import com.walkoverhaul.IStamina;
import com.walkoverhaul.configs.CommonConfig;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class WalkMixin implements IStamina {
    private int currentStamina = CommonConfig.staminaCount;
    private int recoveryCooldown = 0;

    @Override
    public int getCurrentStamina() {
        return currentStamina;
    }

    @Inject(method = "onLivingUpdate", at = @At("RETURN"))
    private void onLivingUpdate(CallbackInfo info) {
        EntityPlayerSP player = (EntityPlayerSP) (Object) this;
        boolean isTryingToSprint = player.isSprinting();
        if (!player.capabilities.isFlying) {
            if (isTryingToSprint && currentStamina > 0) {
                currentStamina--;
                float sprintMultiplier = CommonConfig.sprintMultiplier;
                if(player.lastTickPosY != player.posY){
                    if(CommonConfig.sprintMultiplier > 100) sprintMultiplier = 100;
                }
                player.motionX *= sprintMultiplier / 100.0F;
                player.motionZ *= sprintMultiplier / 100.0F;
            } else {
                player.setSprinting(false);
                player.motionX *= CommonConfig.walkMultiplier / 100.0F;
                player.motionZ *= CommonConfig.walkMultiplier / 100.0F;
                if (isTryingToSprint && currentStamina == 0) {
                    recoveryCooldown = CommonConfig.recoveryTime;
                }
            }
        }
        if (!isTryingToSprint) {
            if(recoveryCooldown > 0) recoveryCooldown--;
            else if(currentStamina < CommonConfig.staminaCount) {
                currentStamina++;
            }
        }
    }
}
