package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ShamanVillager;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VillagerCareerChangeEvent implements Listener {

    @EventHandler
    public void onVillagerCareerChange(org.bukkit.event.entity.VillagerCareerChangeEvent event) {
        Villager villager = event.getEntity();
        if (event.getProfession() != Villager.Profession.FLETCHER) {
            for (ShamanVillager shamanVillager : ShamanVillager.shamanVillagers) {
                if (shamanVillager.getVillager().getUniqueId() == villager.getUniqueId()) {
                    return;
                }
            }
        }
        new ShamanVillager(event.getEntity());
    }

}
