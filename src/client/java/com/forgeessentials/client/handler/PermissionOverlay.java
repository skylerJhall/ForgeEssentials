package com.forgeessentials.client.handler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameData;

import org.lwjgl.opengl.GL11;

import com.forgeessentials.client.ForgeEssentialsClient;
import com.forgeessentials.commons.network.Packet3PlayerPermissions;

public class PermissionOverlay extends Gui implements IMessageHandler<Packet3PlayerPermissions, IMessage>
{

    protected ResourceLocation deniedPlaceTexture;

    protected ResourceLocation deniedBreakTexture;

    protected Packet3PlayerPermissions permissions = new Packet3PlayerPermissions();

    public PermissionOverlay()
    {
        MinecraftForge.EVENT_BUS.register(this);
        deniedPlaceTexture = new ResourceLocation(ForgeEssentialsClient.MODID.toLowerCase(), "textures/gui/denied_place.png");
        deniedBreakTexture = new ResourceLocation(ForgeEssentialsClient.MODID.toLowerCase(), "textures/gui/denied_break.png");
        zLevel = 100;
    }

    @Override
    public IMessage onMessage(Packet3PlayerPermissions message, MessageContext ctx)
    {
        if (message.reset)
        {
            permissions = message;
        }
        else
        {
            permissions.placeIds.addAll(message.placeIds);
            permissions.breakIds.addAll(message.breakIds);
        }
        return null;
    }

    @SubscribeEvent
    public void renderGameOverlayEvent(RenderGameOverlayEvent event)
    {
        if (!event.isCancelable() && event.type == ElementType.HOTBAR)
        {
            Minecraft.getMinecraft().renderEngine.bindTexture(deniedPlaceTexture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);

            ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            int width = res.getScaledWidth();
            int height = res.getScaledHeight();

            for (int i = 0; i < 9; ++i)
            {
                ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i];
                if (stack == null || !(stack.getItem() instanceof ItemBlock))
                    continue;
                Block block = ((ItemBlock) stack.getItem()).block;
                int blockId = GameData.getBlockRegistry().getId(block);
                if (!permissions.placeIds.contains(blockId))
                    continue;
                int x = width / 2 - 90 + i * 20 + 2;
                int y = height - 16 - 3;
                drawTexturedRect(x + 8, y + 1, 8, 8);
            }
        }
        else if (event.isCancelable() && event.type == ElementType.CROSSHAIRS)
        {
            ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            int width = res.getScaledWidth();
            int height = res.getScaledHeight();

            MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
            if (mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
            {
                IBlockState block = Minecraft.getMinecraft().theWorld.getBlockState(mop.func_178782_a());
                int blockId = GameData.getBlockRegistry().getId(block.getBlock());
                if (permissions.breakIds.contains(blockId))
                {
                    Minecraft.getMinecraft().renderEngine.bindTexture(deniedBreakTexture);
                    drawTexturedRect(width / 2 - 5, height / 2 - 5, 10, 10);
                    event.setCanceled(true);
                }
            }
        }
    }

    public void drawTexturedRect(double xPos, double yPos, double width, double height)
    {
        WorldRenderer renderer = Tessellator.getInstance().getWorldRenderer();
        renderer.startDrawingQuads();
        renderer.addVertexWithUV(xPos, yPos + height, zLevel, 0, 1);
        renderer.addVertexWithUV(xPos + width, yPos + height, zLevel, 1, 1);
        renderer.addVertexWithUV(xPos + width, yPos, zLevel, 1, 0);
        renderer.addVertexWithUV(xPos, yPos, zLevel, 0, 0);
        Tessellator.getInstance().draw();
    }

}
