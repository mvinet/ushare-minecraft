package fr.ushare.fanor.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * This file (c) by : - Mickael VINET alias fanor79
 *
 * This file is licensed under a
 * GNU GENERAL PUBLIC LICENSE V3.0
 *
 * See the LICENSE file to learn more.
 *
 * If you have contributed to this file, add your name to authors list.
*/

@SideOnly(Side.CLIENT)
public class GuiTextPassword extends Gui
{
    private final int field_175208_g;
    private final FontRenderer fontRendererInstance;
    public int xPosition;
    public int yPosition;
    /** The width of this text field. */
    public int width;
    public int height;
    /** Has the current text being edited on the textbox. */
    private String text = "";
    private String realText = "";
    private int maxStringLength = 32;
    private int cursorCounter;
    private boolean enableBackgroundDrawing = true;
    /** if true the textbox can lose focus by clicking elsewhere on the screen */
    private boolean canLoseFocus = true;
    /** If this value is true along with isEnabled, keyTyped will process the keys. */
    private boolean isFocused;
    /** If this value is true along with isFocused, keyTyped will process the keys. */
    private boolean isEnabled = true;
    /** The current character index that should be used as start of the rendered text. */
    private int lineScrollOffset;
    private int cursorPosition;
    /** other selection position, maybe the same as the cursor */
    private int selectionEnd;
    private int enabledColor = 14737632;
    private int disabledColor = 7368816;
    /** True if this textbox is visible */
    private boolean visible = true;
    private GuiPageButtonList.GuiResponder field_175210_x;
	private Predicate<String> field_175209_y = Predicates.alwaysTrue();

    public GuiTextPassword(int p_i45542_1_, FontRenderer p_i45542_2_, int p_i45542_3_, int p_i45542_4_, int p_i45542_5_, int p_i45542_6_)
    {
        this.field_175208_g = p_i45542_1_;
        this.fontRendererInstance = p_i45542_2_;
        this.xPosition = p_i45542_3_;
        this.yPosition = p_i45542_4_;
        this.width = p_i45542_5_;
        this.height = p_i45542_6_;
    }

    public void func_175207_a(GuiPageButtonList.GuiResponder p_175207_1_)
    {
        this.field_175210_x = p_175207_1_;
    }

    /**
     * Increments the cursor counter
     */
    public void updateCursorCounter()
    {
        ++this.cursorCounter;
    }

    /**
     * Sets the text of the textbox
     */
    public void setText(String text)
    {
        if (this.field_175209_y.apply(text))
        {
            if (text.length() > this.maxStringLength)
            {
                this.text = text.substring(0, this.maxStringLength);
                this.realText = text.substring(0,  this.maxStringLength);
            }
            else
            {
                this.text = "*";
                this.realText = text;
            }

            this.setCursorPositionEnd();
        }
    }

    /**
     * Returns the contents of the textbox
     */
    public String getText()
    {
        return this.text;
    }
    
    public String getRealText()
    {
    	return this.realText;
    }

    /**
     * returns the text between the cursor and selectionEnd
     */
    public String getSelectedText()
    {
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        return this.text.substring(i, j);
    }

    public void func_175205_a(Predicate<String> p_175205_1_)
    {
        this.field_175209_y = p_175205_1_;
    }

    /**
     * replaces selected text, or inserts text at the position on the cursor
     */
    public void writeText(String p_146191_1_)
    {
        String text = "";
        String realText = "";
        String s2 = ChatAllowedCharacters.filterAllowedCharacters(p_146191_1_);
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        int k = this.maxStringLength - this.text.length() - (i - j);

        if (this.text.length() > 0)
        {
        	text = text + this.text.substring(0, i);
        	realText = realText + this.realText.substring(0, i);
        }

        int l;

        if (k < s2.length())
        {
        	text = text +s2.substring(0, k);
        	realText = realText + s2.substring(0, k);
            l = k;
        }
        else
        {
        	text = text + "*";
        	realText = realText + s2;
            l = s2.length();
        }

        if (this.text.length() > 0 && j < this.text.length())
        {
        	text = text + this.text.substring(j);
        	realText = realText + this.realText.substring(j);
        }

        if (this.field_175209_y.apply(text))
        {
            this.text = text;
            this.realText = realText;
            
            this.moveCursorBy(i - this.selectionEnd + l);

            if (this.field_175210_x != null)
            {
                this.field_175210_x.func_175319_a(this.field_175208_g, this.text);
                this.field_175210_x.func_175319_a(this.field_175208_g, this.realText);
            }
        }
     
    }

    /**
     * Deletes the specified number of words starting at the cursor position. Negative numbers will delete words left of
     * the cursor.
     */
    public void deleteWords(int p_146177_1_)
    {
        if (this.text.length() != 0)
        {
            if (this.selectionEnd != this.cursorPosition)
            {
                this.writeText("");
            }
            else
            {
                this.deleteFromCursor(this.getNthWordFromCursor(p_146177_1_) - this.cursorPosition);
            }
        }
    }

    /**
     * delete the selected text, otherwsie deletes characters from either side of the cursor. params: delete num
     */
    public void deleteFromCursor(int p_146175_1_)
    {
        if (this.text.length() != 0)
        {
            if (this.selectionEnd != this.cursorPosition)
            {
                this.writeText("");
            }
            else
            {
                boolean flag = p_146175_1_ < 0;
                int j = flag ? this.cursorPosition + p_146175_1_ : this.cursorPosition;
                int k = flag ? this.cursorPosition : this.cursorPosition + p_146175_1_;
                String s = "";

                if (j >= 0)
                {
                    s = this.text.substring(0, j);
                }

                if (k < this.text.length())
                {
                    s = s + this.text.substring(k);
                }

                this.text = s;

                if (flag)
                {
                    this.moveCursorBy(p_146175_1_);
                }

                if (this.field_175210_x != null)
                {
                    this.field_175210_x.func_175319_a(this.field_175208_g, this.text);
                }
            }
        }
    }

    public int func_175206_d()
    {
        return this.field_175208_g;
    }

    /**
     * see @getNthNextWordFromPos() params: N, position
     */
    public int getNthWordFromCursor(int p_146187_1_)
    {
        return this.getNthWordFromPos(p_146187_1_, this.getCursorPosition());
    }

    /**
     * gets the position of the nth word. N may be negative, then it looks backwards. params: N, position
     */
    public int getNthWordFromPos(int p_146183_1_, int p_146183_2_)
    {
        return this.func_146197_a(p_146183_1_, p_146183_2_, true);
    }

    public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_)
    {
        int k = p_146197_2_;
        boolean flag1 = p_146197_1_ < 0;
        int l = Math.abs(p_146197_1_);

        for (int i1 = 0; i1 < l; ++i1)
        {
            if (flag1)
            {
                while (p_146197_3_ && k > 0 && this.text.charAt(k - 1) == 32)
                {
                    --k;
                }

                while (k > 0 && this.text.charAt(k - 1) != 32)
                {
                    --k;
                }
            }
            else
            {
                int j1 = this.text.length();
                k = this.text.indexOf(32, k);

                if (k == -1)
                {
                    k = j1;
                }
                else
                {
                    while (p_146197_3_ && k < j1 && this.text.charAt(k) == 32)
                    {
                        ++k;
                    }
                }
            }
        }

        return k;
    }

    /**
     * Moves the text cursor by a specified number of characters and clears the selection
     */
    public void moveCursorBy(int p_146182_1_)
    {
        this.setCursorPosition(this.selectionEnd + p_146182_1_);
    }

    /**
     * sets the position of the cursor to the provided index
     */
    public void setCursorPosition(int p_146190_1_)
    {
        this.cursorPosition = p_146190_1_;
        int j = this.text.length();
        this.cursorPosition = MathHelper.clamp_int(this.cursorPosition, 0, j);
        this.setSelectionPos(this.cursorPosition);
    }

    /**
     * sets the cursors position to the beginning
     */
    public void setCursorPositionZero()
    {
        this.setCursorPosition(0);
    }

    /**
     * sets the cursors position to after the text
     */
    public void setCursorPositionEnd()
    {
        this.setCursorPosition(this.text.length());
    }

    /**
     * Call this method from your GuiScreen to process the keys into the textbox
     */
    public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_)
    {
        if (!this.isFocused)
        {
            return false;
        }
        else if (GuiScreen.func_175278_g(p_146201_2_))
        {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
        }
        else if (GuiScreen.func_175280_f(p_146201_2_))
        {
            GuiScreen.setClipboardString(this.getSelectedText());
            return true;
        }
        else if (GuiScreen.func_175279_e(p_146201_2_))
        {
            if (this.isEnabled)
            {
                this.writeText(GuiScreen.getClipboardString());
            }

            return true;
        }
        else if (GuiScreen.func_175277_d(p_146201_2_))
        {
            GuiScreen.setClipboardString(this.getSelectedText());

            if (this.isEnabled)
            {
                this.writeText("");
            }

            return true;
        }
        else
        {
            switch (p_146201_2_)
            {
                case 14:
                    if (GuiScreen.isCtrlKeyDown())
                    {
                        if (this.isEnabled)
                        {
                            this.deleteWords(-1);
                        }
                    }
                    else if (this.isEnabled)
                    {
                        this.deleteFromCursor(-1);
                    }

                    return true;
                case 199:
                    if (GuiScreen.isShiftKeyDown())
                    {
                        this.setSelectionPos(0);
                    }
                    else
                    {
                        this.setCursorPositionZero();
                    }

                    return true;
                case 203:
                    if (GuiScreen.isShiftKeyDown())
                    {
                        if (GuiScreen.isCtrlKeyDown())
                        {
                            this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                        }
                        else
                        {
                            this.setSelectionPos(this.getSelectionEnd() - 1);
                        }
                    }
                    else if (GuiScreen.isCtrlKeyDown())
                    {
                        this.setCursorPosition(this.getNthWordFromCursor(-1));
                    }
                    else
                    {
                        this.moveCursorBy(-1);
                    }

                    return true;
                case 205:
                    if (GuiScreen.isShiftKeyDown())
                    {
                        if (GuiScreen.isCtrlKeyDown())
                        {
                            this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                        }
                        else
                        {
                            this.setSelectionPos(this.getSelectionEnd() + 1);
                        }
                    }
                    else if (GuiScreen.isCtrlKeyDown())
                    {
                        this.setCursorPosition(this.getNthWordFromCursor(1));
                    }
                    else
                    {
                        this.moveCursorBy(1);
                    }

                    return true;
                case 207:
                    if (GuiScreen.isShiftKeyDown())
                    {
                        this.setSelectionPos(this.text.length());
                    }
                    else
                    {
                        this.setCursorPositionEnd();
                    }

                    return true;
                case 211:
                    if (GuiScreen.isCtrlKeyDown())
                    {
                        if (this.isEnabled)
                        {
                            this.deleteWords(1);
                        }
                    }
                    else if (this.isEnabled)
                    {
                        this.deleteFromCursor(1);
                    }

                    return true;
                default:
                    if (ChatAllowedCharacters.isAllowedCharacter(p_146201_1_))
                    {
                        if (this.isEnabled)
                        {
                            this.writeText(Character.toString(p_146201_1_));
                        }

                        return true;
                    }
                    else
                    {
                        return false;
                    }
            }
        }
    }

    /**
     * Args: x, y, buttonClicked
     */
    public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_)
    {
        boolean flag = p_146192_1_ >= this.xPosition && p_146192_1_ < this.xPosition + this.width && p_146192_2_ >= this.yPosition && p_146192_2_ < this.yPosition + this.height;

        if (this.canLoseFocus)
        {
            this.setFocused(flag);
        }

        if (this.isFocused && flag && p_146192_3_ == 0)
        {
            int l = p_146192_1_ - this.xPosition;

            if (this.enableBackgroundDrawing)
            {
                l -= 4;
            }

            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            this.setCursorPosition(this.fontRendererInstance.trimStringToWidth(s, l).length() + this.lineScrollOffset);
        }
    }

    /**
     * Draws the textbox
     */
    public void drawTextBox()
    {
        if (this.getVisible())
        {
            if (this.getEnableBackgroundDrawing())
            {
                drawRect(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, -6250336);
                drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -16777216);
            }

            int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
            int l = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
            int i1 = this.enableBackgroundDrawing ? this.yPosition + (this.height - 8) / 2 : this.yPosition;
            int j1 = l;

            if (k > s.length())
            {
                k = s.length();
            }

            if (s.length() > 0)
            {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = this.fontRendererInstance.drawStringWithShadow(s1, (float)l, (float)i1, i);
            }

            boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int k1 = j1;

            if (!flag)
            {
                k1 = j > 0 ? l + this.width : l;
            }
            else if (flag2)
            {
                k1 = j1 - 1;
                --j1;
            }

            if (s.length() > 0 && flag && j < s.length())
            {
                j1 = this.fontRendererInstance.drawStringWithShadow(s.substring(j), (float)j1, (float)i1, i);
            }

            if (flag1)
            {
                if (flag2)
                {
                    Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT, -3092272);
                }
                else
                {
                    this.fontRendererInstance.drawStringWithShadow("_", (float)k1, (float)i1, i);
                }
            }

            if (k != j)
            {
                int l1 = l + this.fontRendererInstance.getStringWidth(s.substring(0, k));
                this.drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT);
            }
        }
    }

    /**
     * draws the vertical line cursor in the textbox
     */
    private void drawCursorVertical(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_)
    {
        int i1;

        if (p_146188_1_ < p_146188_3_)
        {
            i1 = p_146188_1_;
            p_146188_1_ = p_146188_3_;
            p_146188_3_ = i1;
        }

        if (p_146188_2_ < p_146188_4_)
        {
            i1 = p_146188_2_;
            p_146188_2_ = p_146188_4_;
            p_146188_4_ = i1;
        }

        if (p_146188_3_ > this.xPosition + this.width)
        {
            p_146188_3_ = this.xPosition + this.width;
        }

        if (p_146188_1_ > this.xPosition + this.width)
        {
            p_146188_1_ = this.xPosition + this.width;
        }

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(5387);
        worldrenderer.startDrawingQuads();
        worldrenderer.addVertex((double)p_146188_1_, (double)p_146188_4_, 0.0D);
        worldrenderer.addVertex((double)p_146188_3_, (double)p_146188_4_, 0.0D);
        worldrenderer.addVertex((double)p_146188_3_, (double)p_146188_2_, 0.0D);
        worldrenderer.addVertex((double)p_146188_1_, (double)p_146188_2_, 0.0D);
        tessellator.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }

    public void setMaxStringLength(int p_146203_1_)
    {
        this.maxStringLength = p_146203_1_;

        if (this.text.length() > p_146203_1_)
        {
            this.text = this.text.substring(0, p_146203_1_);
        }
    }

    /**
     * returns the maximum number of character that can be contained in this textbox
     */
    public int getMaxStringLength()
    {
        return this.maxStringLength;
    }

    /**
     * returns the current position of the cursor
     */
    public int getCursorPosition()
    {
        return this.cursorPosition;
    }

    /**
     * get enable drawing background and outline
     */
    public boolean getEnableBackgroundDrawing()
    {
        return this.enableBackgroundDrawing;
    }

    /**
     * enable drawing background and outline
     */
    public void setEnableBackgroundDrawing(boolean p_146185_1_)
    {
        this.enableBackgroundDrawing = p_146185_1_;
    }

    /**
     * Sets the text colour for this textbox (disabled text will not use this colour)
     */
    public void setTextColor(int p_146193_1_)
    {
        this.enabledColor = p_146193_1_;
    }

    public void setDisabledTextColour(int p_146204_1_)
    {
        this.disabledColor = p_146204_1_;
    }

    /**
     * Sets focus to this gui element
     */
    public void setFocused(boolean p_146195_1_)
    {
        if (p_146195_1_ && !this.isFocused)
        {
            this.cursorCounter = 0;
        }

        this.isFocused = p_146195_1_;
    }

    /**
     * Getter for the focused field
     */
    public boolean isFocused()
    {
        return this.isFocused;
    }

    public void setEnabled(boolean p_146184_1_)
    {
        this.isEnabled = p_146184_1_;
    }

    /**
     * the side of the selection that is not the cursor, may be the same as the cursor
     */
    public int getSelectionEnd()
    {
        return this.selectionEnd;
    }

    /**
     * returns the width of the textbox depending on if background drawing is enabled
     */
    public int getWidth()
    {
        return this.getEnableBackgroundDrawing() ? this.width - 8 : this.width;
    }

    /**
     * Sets the position of the selection anchor (i.e. position the selection was started at)
     */
    public void setSelectionPos(int p_146199_1_)
    {
        int j = this.text.length();

        if (p_146199_1_ > j)
        {
            p_146199_1_ = j;
        }

        if (p_146199_1_ < 0)
        {
            p_146199_1_ = 0;
        }

        this.selectionEnd = p_146199_1_;

        if (this.fontRendererInstance != null)
        {
            if (this.lineScrollOffset > j)
            {
                this.lineScrollOffset = j;
            }

            int k = this.getWidth();
            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), k);
            int l = s.length() + this.lineScrollOffset;

            if (p_146199_1_ == this.lineScrollOffset)
            {
                this.lineScrollOffset -= this.fontRendererInstance.trimStringToWidth(this.text, k, true).length();
            }

            if (p_146199_1_ > l)
            {
                this.lineScrollOffset += p_146199_1_ - l;
            }
            else if (p_146199_1_ <= this.lineScrollOffset)
            {
                this.lineScrollOffset -= this.lineScrollOffset - p_146199_1_;
            }

            this.lineScrollOffset = MathHelper.clamp_int(this.lineScrollOffset, 0, j);
        }
    }

    /**
     * if true the textbox can lose focus by clicking elsewhere on the screen
     */
    public void setCanLoseFocus(boolean p_146205_1_)
    {
        this.canLoseFocus = p_146205_1_;
    }

    /**
     * returns true if this textbox is visible
     */
    public boolean getVisible()
    {
        return this.visible;
    }

    /**
     * Sets whether or not this textbox is visible
     */
    public void setVisible(boolean p_146189_1_)
    {
        this.visible = p_146189_1_;
    }
}