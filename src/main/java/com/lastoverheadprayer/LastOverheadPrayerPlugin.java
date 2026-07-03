package com.lastoverheadprayer;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Prayer;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
        name = "Last Overhead Prayer",
        description = "Displays the last overhead prayer you activated, and whether it is currently on or off",
        tags = {"prayer", "overhead", "protect", "pvm", "reminder"}
)
public class LastOverheadPrayerPlugin extends Plugin
{
    private static final Prayer[] OVERHEAD_PRAYERS = {
            Prayer.PROTECT_FROM_MELEE,
            Prayer.PROTECT_FROM_MISSILES,
            Prayer.PROTECT_FROM_MAGIC,
            Prayer.RETRIBUTION,
            Prayer.SMITE,
            Prayer.REDEMPTION
    };

    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private LastOverheadPrayerOverlay overlay;

    @Inject
    private LastOverheadPrayerConfig config;

    @Getter
    private Prayer lastOverheadPrayer = null;

    @Getter
    private boolean overheadActive = false;

    @Override
    protected void startUp()
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
        lastOverheadPrayer = null;
        overheadActive = false;
    }

    @Subscribe
    public void onGameTick(GameTick tick)
    {
        for (Prayer prayer : OVERHEAD_PRAYERS)
        {
            if (client.isPrayerActive(prayer))
            {
                lastOverheadPrayer = prayer;
                overheadActive = true;
                return;
            }
        }
        overheadActive = false;
    }

    @Provides
    LastOverheadPrayerConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(LastOverheadPrayerConfig.class);
    }
}
