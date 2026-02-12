// Inside the render method
int yOffset = 2;
// 1. Watermark
mc.fontRendererObj.drawStringWithShadow(Client.instance.name, 2, 2, 0x55FFFF);

// 2. ArrayList (loops through the modules)
for (Module m : Client.instance.modules) {
    if (m.enabled) {
        int width = new ScaledResolution(mc).getScaledWidth();
        int strWidth = mc.fontRendererObj.getStringWidth(m.name);

        // Draw the module name at the top right
        mc.fontRendererObj.drawStringWithShadow(m.name, width - strWidth - 2, yOffset, m.color);
        // Move Down for the next module
    }
}