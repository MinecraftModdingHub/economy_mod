package org.randomcoders.economy.inventory;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.randomcoders.economy.blocks.TileEntityTrader;
import org.randomcoders.economy.handlers.trading.HandlerTradeDB;
import org.randomcoders.economy.handlers.trading.TradeInstance;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiTrader extends GuiContainer
{
	public static ResourceLocation noGui = new ResourceLocation("economy", "textures/gui/gui_blank.png");
	
	public static ResourceLocation buyGui = new ResourceLocation("economy", "textures/gui/gui_trader_buy.png");
	public static int buySizeX = 248;
	public static int buySizeY = 184;

	public static ResourceLocation sellGui = new ResourceLocation("economy", "textures/gui/gui_trader_sell.png");
	public static int sellSizeX = 248;
	public static int sellSizeY = 248;

	public static ResourceLocation tradesGui = new ResourceLocation("economy", "textures/gui/gui_trader_trades.png");
	public static int tradesSizeX = 248;
	public static int tradesSizeY = 184;

	public static ResourceLocation marketGui = new ResourceLocation("economy", "textures/gui/gui_trader_market.png");
	public static int marketSizeX = 248;
	public static int marketSizeY = 184;
	
	public GuiButton buyButton;
	public GuiButton sellButton;
	public GuiButton tradesButton;
	public GuiButton marketButton;
	
	public GuiButton confirmButton;
	
	public GuiButton deliverButton;
	public GuiButton pickupButton;
	
	public int curPage;
	public int scrollPos;
	
	public ItemStack sellItem;
	public String sellOffer;
	
	public ItemStack buyItem;
	public String buySearch;
	public String buyAmount;
	public String buyOffer;
	
	public String marketSearch;
	public ContainerTrader containerTrader;
	
	public EntityPlayer playerUser;
	
	public GuiTrader(InventoryPlayer playerInvo, TileEntityTrader tile)
	{
		super(new ContainerTrader(playerInvo, tile));
		playerUser = playerInvo.player;
		containerTrader = (ContainerTrader)this.inventorySlots;
		
		curPage = 0;
		
		xSize = buySizeX;
		ySize = buySizeY;
	}
	
	@Override
	public void initGui()
	{
		reloadButtons();
		reloadContainer();
		
		super.initGui();
	}
	
	public void actionPerformed(GuiButton button)
	{
		switch(button.id)
		{
			case 0:
			{
				if(curPage != 0)
				{
					xSize = buySizeX;
					ySize = buySizeY;
					curPage = 0;
					this.initGui();
				}
				break;
			}
			case 1:
			{
				if(curPage != 1)
				{
					xSize = sellSizeX;
					ySize = sellSizeY;
					curPage = 1;
					this.initGui();
				}
				break;
			}
			case 2:
			{
				if(curPage != 2)
				{
					xSize = tradesSizeX;
					ySize = tradesSizeY;
					curPage = 2;
					this.initGui();
				}
				break;
			}
			case 3:
			{
				if(curPage != 3)
				{
					xSize = marketSizeX;
					ySize = marketSizeY;
					curPage = 3;
					this.initGui();
				}
				break;
			}
		}
		
		scrollPos = 0;
	}
	
	public void reloadContainer()
	{
		containerTrader.updatePage(curPage);
	}

	@SuppressWarnings("unchecked")
	public void reloadButtons()
	{
		this.buttonList.clear();
		
		int posX = (this.width - xSize) / 2;
		int posY = (this.height - ySize) / 2;
		
		buyButton = new GuiButton(0, posX + 24, posY + 24, 40, 20, "Buy");
		sellButton = new GuiButton(1, posX + 72, posY + 24, 40, 20, "Sell");
		tradesButton = new GuiButton(2, posX + 120, posY + 24, 48, 20, "Trades");
		marketButton = new GuiButton(3, posX + 176, posY + 24, 48, 20, "Market");
		
		switch(curPage)
		{
			case 0:
			{
				deliverButton = new GuiButton(4, posX + 112, posY + 88, 48, 20, "Deliver");
				pickupButton = new GuiButton(5, posX + 176, posY + 88, 48, 20, "Pickup");
				confirmButton = new GuiButton(6, posX + 24, posY + 152, 200, 20, "Confirm");
				this.buttonList.add(deliverButton);
				this.buttonList.add(pickupButton);
				this.buttonList.add(confirmButton);
				break;
			}
			case 1:
			{
				confirmButton = new GuiButton(7, posX + 24, posY + 216, 200, 20, "Confirm");
				this.buttonList.add(confirmButton);
				break;
			}
			case 2:
			{
				break;
			}
			case 3:
			{
				break;
			}
		}
		
		this.buttonList.add(buyButton);
		this.buttonList.add(sellButton);
		this.buttonList.add(tradesButton);
		this.buttonList.add(marketButton);
	}
	
	@Override
	public void drawScreen(int x, int y, float f)
	{
		super.drawScreen(x, y, f);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2)
	{
		fontRenderer.drawString("Trader", 8, 8, 4210752);
		
		switch(curPage)
		{
			case 0:
			{
				fontRenderer.drawString("No.", 112, 52, 4210752);
				fontRenderer.drawString("Offer", 160, 52, 4210752);
				break;
			}
			case 1:
			{
				fontRenderer.drawString("Price Per Item", 24, 84, 4210752);
				fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 43, 120, 4210752);
				break;
			}
			case 2:
			{
				fontRenderer.drawString("Buying", 24, 52, 4210752);
				fontRenderer.drawString("Selling", 88, 52, 4210752);
				fontRenderer.drawString("Complete", 160, 52, 4210752);
				break;
			}
			case 3:
			{
				fontRenderer.drawString("Market History", 112, 52, 4210752);
				break;
			}
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		ResourceLocation texture = noGui;
		
		switch(curPage)
		{
			case 0:
			{
				texture = buyGui;
				break;
			}
			case 1:
			{
				texture = sellGui;
				break;
			}
			case 2:
			{
				texture = tradesGui;
				break;
			}
			case 3:
			{
				texture = marketGui;
				break;
			}
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
		if(curPage == 2)
		{
			for(int i = 0; i < HandlerTradeDB.buyList.size(); i++)
			{
				TradeInstance trade = HandlerTradeDB.buyList.get(i);
				
				this.drawItemStack(trade.tradeItem, x + 28, y + 68 + (i * 20), trade.tradeItem.stackSize > 1? "" + trade.tradeItem.stackSize : "");
				
				fontRenderer.drawString("$" + trade.getDisplayValue(), x + 52, y + 68 + (i * 20) + 4, 4210752);
			}
			
			for(int i = 0; i < HandlerTradeDB.sellList.size(); i++)
			{
				TradeInstance trade = HandlerTradeDB.sellList.get(i);
				
				this.drawItemStack(trade.tradeItem, x + 92, y + 68 + (i * 20), trade.tradeItem.stackSize > 1? "" + trade.tradeItem.stackSize : "");
				
				fontRenderer.drawString("$" + trade.getDisplayValue(), x + 116, y + 68 + (i * 20) + 4, 4210752);
			}
			
			for(int i = 0; i < HandlerTradeDB.buyList.size(); i++)
			{
				TradeInstance trade = HandlerTradeDB.buyList.get(i);
				
				if(this.isPointInRegion(x + 28, y + 68 + (i * 20), 16, 16, par2, par3))
				{
					this.drawItemStackTooltip(trade.tradeItem, par2, par3);
				}
			}
			
			for(int i = 0; i < HandlerTradeDB.sellList.size(); i++)
			{
				TradeInstance trade = HandlerTradeDB.sellList.get(i);
				
				if(this.isPointInRegion(x + 92, y + 68 + (i * 20), 16, 16, par2, par3))
				{
					this.drawItemStackTooltip(trade.tradeItem, par2, par3);
				}
			}
		}
	}

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
    }
    
    public void handleMouseInput()
    {
    	super.handleMouseInput();
    	
    	int scrollDir = (int)Math.signum(Mouse.getEventDWheel());
    	
    	if(scrollPos + scrollDir <= 0)
    	{
    		scrollPos = 0;
    	} else if(curPage == 0)
    	{
    		//Scroll through current query list
    	}
    }

    private void drawItemStack(ItemStack par1ItemStack, int par2, int par3, String par4Str)
    {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRenderer.zLevel = 200.0F;
        FontRenderer font = null;
        if (par1ItemStack != null) font = par1ItemStack.getItem().getFontRenderer(par1ItemStack);
        if (font == null) font = fontRenderer;
        itemRenderer.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3);
        itemRenderer.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3, par4Str);
        this.zLevel = 0.0F;
        itemRenderer.zLevel = 0.0F;
    }

	public int getPosX()
	{
		return (this.width - xSize) / 2;
	}

	public int getPosY()
	{
		return (this.height - ySize) / 2;
	}
	
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        return null;
    }
	
}
