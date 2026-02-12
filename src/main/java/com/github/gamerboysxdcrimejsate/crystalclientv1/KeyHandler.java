package com.github.gamerboysxdcrimejsate.crystalclientv1;
package src;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;

public class KeyHandler {
    
    /**
     * Call this method from Minecraft.java inside the runTick() 
     * loop where Keyboard.next() is handled.
     */
    public static void onKey(int key) {
        // 54 is the LWJGL code for Right Shift
        if (key == Keyboard.KEY_RSHIFT) {
            // Check if the game is already in a menu to avoid double-opening
            if (Minecraft.getMinecraft().currentScreen == null) {
                Minecraft.getMinecraft().displayGuiScreen(Client.instance.clickGui);
            }
        }
    }
}
