package com.vanhal.recallstones.client;

import java.io.IOException;

import com.vanhal.recallstones.RecallStones;
import com.vanhal.recallstones.items.ItemRecallStoneBlank;
import com.vanhal.recallstones.networking.MessageMarkStone;
import com.vanhal.recallstones.networking.NetworkHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class GUINameStone extends GuiScreen  {
	public final ResourceLocation texture = new ResourceLocation("recallstones", "textures/gui/gui.png");
	public final int xSizeOfTexture = 176;
	public final int ySizeOfTexture = 88;
	
	private GuiTextField locationField;
	private String locationName = "";
	
	private EntityPlayer user;
	private EnumHand stoneHand;

	public GUINameStone(EntityPlayer player, EnumHand enumHand) {
		user = player;
		stoneHand = enumHand;
	}
	
	@Override
	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();
		
		this.mc.getTextureManager().bindTexture(this.texture);
		int startX = (width - xSizeOfTexture) / 2;
        int startY = (height - ySizeOfTexture) / 2;
		this.drawTexturedModalRect(startX, startY, 0, 0, xSizeOfTexture, ySizeOfTexture);
		
		this.drawString(fontRendererObj, "Enter a Name for this location:", startX + 6, startY + 12, 0xEEEEEE);
		
		locationField.drawTextBox();
		
		super.drawScreen(x, y, f);
	}
	
	@Override
	public void initGui() {
		int posX = (width - xSizeOfTexture) / 2;
        int posY = (height - ySizeOfTexture) / 2;

        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, posX + 6, posY + 60, 78, 20, "Cancel"));
        this.buttonList.add(new GuiButton(2, posX + 90, posY + 60, 78, 20, "Mark Location"));
        
        locationField = new GuiTextField(1, fontRendererObj, posX + 6, posY + 30, 162, 20);
        locationField.setFocused(true);
        locationField.setMaxStringLength(80);
        
		
	}
	
	@Override
	protected void keyTyped(char c, int i) throws IOException {
		super.keyTyped(c, i);
		if (locationField.isFocused()) {
			locationField.textboxKeyTyped(c, i);
		}
		if (i == 28) {
			submitButton();
		}
	}
	
	@Override
	public void mouseClicked(int i, int j, int k) throws IOException {
		super.mouseClicked(i, j, k);
		locationField.mouseClicked(i, j, k);
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if (button.id == 1) { //cancel
			user.closeScreen();
		} else if (button.id == 2) {
			submitButton();
		}
	}
	
	public void submitButton() {
		user.closeScreen();
		if (user.getHeldItem(stoneHand).getItem() instanceof ItemRecallStoneBlank) {
			//send pack to server with name.
			MessageMarkStone msg = new MessageMarkStone(locationField.getText(), stoneHand);
			NetworkHandler.sendToServer(msg);
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
}
