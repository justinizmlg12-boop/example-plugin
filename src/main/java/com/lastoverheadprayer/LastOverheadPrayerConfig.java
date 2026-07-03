package com.lastoverheadprayer;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.Color;

@ConfigGroup("lastoverheadprayer")
public interface LastOverheadPrayerConfig extends Config
{
    @ConfigItem(
            keyName = "hideWhenActive",
            name = "Hide when prayer is active",
            description = "Hides the overlay while the overhead prayer is currently active (only shows when it is off)"
    )
    default boolean hideWhenActive()
    {
        return false;
    }

    @ConfigItem(
            keyName = "showPrayerName",
            name = "Show prayer name",
            description = "Shows the name of the last overhead prayer used"
    )
    default boolean showPrayerName()
    {
        return true;
    }

    @ConfigItem(
            keyName = "activeColor",
            name = "Active color",
            description = "Text color when the overhead prayer is currently active"
    )
    default Color activeColor()
    {
        return Color.GREEN;
    }

    @ConfigItem(
            keyName = "inactiveColor",
            name = "Inactive color",
            description = "Text color when the overhead prayer is not currently active"
    )
    default Color inactiveColor()
    {
        return Color.RED;
    }
}
