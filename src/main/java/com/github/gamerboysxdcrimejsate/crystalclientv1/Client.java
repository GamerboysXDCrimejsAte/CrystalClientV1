package src;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class Client {
    public static Client instance = new Client();
    public String name = "Crystal", version = "1.0";
    public ArrayList<Module> modules = new ArrayList<>();
    public boolean ghostMode = false;
    public ClickGUI clickGui;
    private Minecraft mc = Minecraft.getMinecraft();

    public void init() {
        this.clickGui = new ClickGUI();
        
        // Modules list
        modules.add(new Module("Flight", 0x55FFFF));
        modules.add(new Module("KillAura", 0xFF5555));
        modules.add(new Module("Sprint", 0x55FF55));
        modules.add(new Module("GhostMode", 0xAAAAAA));
    }

    // Call this inside your client.java's runTick() loop
    public void onTick() {
        // 1. Handle the Keybind (Right Shift = 54)
        if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            if (mc.currentScreen == null) {
                mc.displayGuiScreen(this.clickGui);
            }
        }

        // 2. Handle Module Logic
        for (Module m : modules) {
            if (!m.enabled) continue;

            // Sprint Logic
            if (m.name.equalsIgnoreCase("Sprint")) {
                if (mc.thePlayer.moveForward > 0 && !mc.thePlayer.isCollidedHorizontally) {
                    mc.thePlayer.setSprinting(true);
                }
            }

            // Flight Logic
            if (m.name.equalsIgnoreCase("Flight")) {
                mc.thePlayer.capabilities.isFlying = true;
            }

            // KillAura Logic
            if (m.name.equalsIgnoreCase("KillAura")) {
                for (Object obj : mc.theWorld.loadedEntityList) {
                    if (obj instanceof EntityLivingBase && obj != mc.thePlayer) {
                        EntityLivingBase target = (EntityLivingBase) obj;
                        if (mc.thePlayer.getDistanceToEntity(target) <= 4.0F && target.isEntityAlive()) {
                            mc.thePlayer.swingItem();
                            mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                            break; 
                        }
                    }
                }
            }
        }
    }

    // Helper to sync GhostMode state
    public void updateGhostMode(boolean state) {
        this.ghostMode = state;
    }
}