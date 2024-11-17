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
    private boolean didChangeSpeed;

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
                if(player.lastTickPosY == player.posY || player.onGround){
                    player.motionX *= sprintMultiplier / 100.0F;
                    player.motionZ *= sprintMultiplier / 100.0F;
                    didChangeSpeed = false;
                } else {
                    if(CommonConfig.sprintMultiplier > 100) sprintMultiplier = 100;
                    if(!didChangeSpeed) {
                        player.motionX *= sprintMultiplier / 100.0F;
                        player.motionZ *= sprintMultiplier / 100.0F;
                        didChangeSpeed = true;
                    }
                }
            } else {
                float walkMultiplier = CommonConfig.walkMultiplier;
                player.setSprinting(false);
                if(player.lastTickPosY == player.posY || player.onGround){
                    player.motionX *= walkMultiplier / 100.0F;
                    player.motionZ *= walkMultiplier / 100.0F;
                    didChangeSpeed = false;
                } else {
                    if(CommonConfig.walkMultiplier > 100) walkMultiplier = 100;
                    if(!didChangeSpeed) {
                        player.motionX *= walkMultiplier / 100.0F;
                        player.motionZ *= walkMultiplier / 100.0F;
                        didChangeSpeed = true;
                    }
                }
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
