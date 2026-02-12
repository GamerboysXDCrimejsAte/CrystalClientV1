public class ClickGUI extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int x = 50, y = 50;
        
        for (Module m : Client.instance.modules) {
            // Draw a box for each module
            Gui.drawRect(x, y, x + 80, y + 15, m.enabled ? 0x9000FF00 : 0x90000000);
            fontRendererObj.drawString(m.name, x + 5, y + 4, -1);
            y += 16; // Stack buttons vertically
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        // Logic to check if mouse is over a button, then m.toggle()
    }
}