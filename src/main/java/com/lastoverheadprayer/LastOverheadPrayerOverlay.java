package com.lastoverheadprayer;

import net.runelite.api.Prayer;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class LastOverheadPrayerOverlay extends Overlay
{
    private final LastOverheadPrayerPlugin plugin;
    private final LastOverheadPrayerConfig config;
    private final PanelComponent panelComponent = new PanelComponent();

    @Inject
    public LastOverheadPrayerOverlay(LastOverheadPrayerPlugin plugin, LastOverheadPrayerConfig config)
    {
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.MED);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Prayer lastPrayer = plugin.getLastOverheadPrayer();

        if (lastPrayer == null)
        {
            return null;
        }

        if (config.hideWhenActive() && plugin.isOverheadActive())
        {
            return null;
        }

        panelComponent.getChildren().clear();

        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Last Overhead")
                .color(Color.WHITE)
                .build());

        if (config.showPrayerName())
        {
            Color textColor = plugin.isOverheadActive() ? config.activeColor() : config.inactiveColor();

            panelComponent.getChildren().add(LineComponent.builder()
                    .left(getPrayerDisplayName(lastPrayer))
                    .leftColor(textColor)
                    .right(plugin.isOverheadActive() ? "ON" : "OFF")
                    .rightColor(textColor)
                    .build());
        }

        panelComponent.setPreferredSize(new Dimension(160, 0));
        return panelComponent.render(graphics);
    }

    private String getPrayerDisplayName(Prayer prayer)
    {
        switch (prayer)
        {
            case PROTECT_FROM_MELEE:
                return "Protect Melee";
            case PROTECT_FROM_MISSILES:
                return "Protect Missiles";
            case PROTECT_FROM_MAGIC:
                return "Protect Magic";
            case RETRIBUTION:
                return "Retribution";
            case SMITE:
                return "Smite";
            case REDEMPTION:
                return "Redemption";
            default:
                return prayer.name();
        }
    }
}
