package com.walkoverhaul.mixins;

import com.walkoverhaul.IStamina;
import com.walkoverhaul.WAMain;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(EntityLivingBase.class)
public class EntityLivingBaseMixin {
    @Final
    @Shadow
    private static final UUID SPRINTING_SPEED_BOOST_ID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
    @Final
    @Shadow
    private static final AttributeModifier SPRINTING_SPEED_BOOST = (new AttributeModifier(SPRINTING_SPEED_BOOST_ID, "Sprinting speed boost", 0.30000001192092896D, 2)).setSaved(false);
    @Shadow
    private AbstractAttributeMap attributeMap;

    @Shadow
    public IAttributeInstance getEntityAttribute(IAttribute attribute)
    {
        return this.getAttributeMap().getAttributeInstance(attribute);
    }

    @Shadow
    public AbstractAttributeMap getAttributeMap()
    {
        if (this.attributeMap == null)
        {
            this.attributeMap = new AttributeMap();
        }

        return this.attributeMap;
    }

    @Inject(method = "setSprinting", at = @At("JUMP"), cancellable = true)
    public void setSprinting(boolean sprinting, CallbackInfo info) {
        if ((Object) this instanceof EntityPlayer) {
            IStamina staminaHandler = (IStamina) this;
            if (sprinting && staminaHandler.getCurrentStamina() == 0) {
                info.cancel();
            }
        }
    }
}
