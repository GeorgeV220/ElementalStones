package com.lennertsoffers.elementalstones.event;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PlayerItemHeldEvent implements Listener {

    @EventHandler
    public void onPlayerItemHeld(org.bukkit.event.player.PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
        if (activePlayer != null) {
            if (activePlayer.isActive()) {
                ItemStack previousItem = player.getInventory().getItem(event.getPreviousSlot());
                if (!(previousItem == null)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}