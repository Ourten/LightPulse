package fr.ourten.lightpulse.common.blocks.energy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ILightPulseBlockModel
{
    @SideOnly(Side.CLIENT)
    public abstract ILightPulseBlockModel initItemModel();
}