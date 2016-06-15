package fr.ourten.lightpulse.common.items;

public class LightPulseItemManager
{
    private static volatile LightPulseItemManager instance = null;

    public static final LightPulseItemManager getInstance()
    {
        if (LightPulseItemManager.instance == null)
            synchronized (LightPulseItemManager.class)
            {
                if (LightPulseItemManager.instance == null)
                    LightPulseItemManager.instance = new LightPulseItemManager();
            }
        return LightPulseItemManager.instance;
    }

    public void initItems()
    {

    }
}