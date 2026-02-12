package com.github.gamerboysxdcrimejsate.crystalclientv1;
package src;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class Module {
    public String name;
    public boolean enabled;
    public int color;
    protected static Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public void toggle() {
        this.enabled = !this.enabled;

        // 1. Flight Cleanup: Stops you from falling or floating when turning off Flight
        if (this.name.equalsIgnoreCase("Flight") && !this.enabled) {
            mc.thePlayer.capabilities.isFlying = false;
        }

        // 2. GhostMode Sync: Tells the Client class to hide the HUD
        if (this.name.equalsIgnoreCase("GhostMode")) {
            Client.instance.ghostMode = this.enabled;
        }
    }

    // This method is called every game tick for every module
    public void onUpdate() {
        if (!enabled) return;

        // 1. SPRINT
        if (this.name.equalsIgnoreCase("Sprint")) {
            if (mc.thePlayer.moveForward > 0 && !mc.thePlayer.isCollidedHorizontally) {
                mc.thePlayer.setSprinting(true);
            }
        }

        // 2. FLIGHT
        if (this.name.equalsIgnoreCase("Flight")) {
            mc.thePlayer.capabilities.isFlying = true;
            mc.thePlayer.capabilities.setFlySpeed(0.1f);
        }

        // 3. KILLAURA (Simple Version)
        if (this.name.equalsIgnoreCase("KillAura")) {
            for (Object obj : mc.theWorld.loadedEntityList) {
                if (obj instanceof EntityLivingBase && obj != mc.thePlayer) {
                    EntityLivingBase entity = (EntityLivingBase) obj;
                    if (mc.thePlayer.getDistanceToEntity(entity) < 4.0F && entity.isEntityAlive()) {
                        mc.thePlayer.swingItem();
                        mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
                        break; 
                    }
                }
            }
        }
    }
}
