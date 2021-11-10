package com.lennertsoffers.elementalstones.items;

import com.lennertsoffers.elementalstones.customClasses.tools.StringListTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class CraftItemManager {

    // Base items
    public static ItemStack BABY_ZOMBIE_HIDE;
    public static ItemStack ROTTEN_APPLE;
    public static ItemStack INSECT;
    public static ItemStack BAT;
    public static ItemStack THYME;
    public static ItemStack OREGANO;
    public static ItemStack DILL;
    public static ItemStack ROSEMARY;
    public static ItemStack GOLDEN_FEATHER;
    public static ItemStack DEAD_FLOWER;
    public static ItemStack TWIG;
    public static ItemStack SOUL_OF_EVOKER;
    public static ItemStack VILLAGER_BLOOD;

    // Craft Items
    public static ItemStack VOODOO_DOLL;
    public static ItemStack SHIP_IN_BOTTLE;
    public static ItemStack PALANTIR;
    public static ItemStack BLOOD_AND_QUIL;
    public static ItemStack BUNDLE_OF_HERBS;
    public static ItemStack MYSTERY_POTION;
    public static ItemStack POISONED_APPLE;
    public static ItemStack CARNIVOROUS_PLANT;
    public static ItemStack SCENTED_CANDLE;

    public static ArrayList<ItemStack> tradeItems = new ArrayList<>();

    // Shards
    public static ItemStack COMMON_SHARD;
    public static ItemStack UNCOMMON_SHARD;
    public static ItemStack RARE_SHARD;
    public static ItemStack ULTRA_RARE_SHARD;
    public static ItemStack LEGENDARY_SHARD;


    // Create Item
    private static int itemId = 0;
    private static ItemStack createNormalItem(String displayName, String lore) {
        ItemStack itemStack = new ItemStack(Material.CARROT);
        return setLore(displayName, lore, itemStack);
    }
    
    private static ItemStack createAlterMaterialItem(String displayName, String lore, Material material) {
        ItemStack itemStack = new ItemStack(material);
        return setLore(displayName, lore, itemStack);

    }
    
    private static ItemStack createShard(String displayName, String lore) {
        ItemStack itemStack = new ItemStack(Material.DIAMOND);
        return setLore(displayName, lore, itemStack);
    }
    
    private static ItemStack setLore(String displayName, String lore, ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(displayName);
            StringListTools.formatLore(lore, ChatColor.GRAY);
            itemMeta.setCustomModelData(itemId);
            itemId++;
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    } 


    // Set Items
    public static void init() {
        BABY_ZOMBIE_HIDE = createNormalItem("Baby Zombie Hide", "");
        ROTTEN_APPLE = createAlterMaterialItem("Rotten Apple", "", Material.APPLE);
        INSECT = createNormalItem("Insect", "");
        BAT = createNormalItem("Bat", "");
        THYME = createNormalItem("Thyme", "");
        OREGANO = createNormalItem("Oregano", "");
        DILL = createNormalItem("Dill", "");
        ROSEMARY = createNormalItem("Rosemary", "");
        GOLDEN_FEATHER = createNormalItem("Golden Feather", "");
        DEAD_FLOWER = createNormalItem("Dead Flower", "");
        TWIG = createNormalItem("Twig", "");
        SOUL_OF_EVOKER = createNormalItem("Soul Of Evoker", "");
        VILLAGER_BLOOD = createNormalItem("Blood", "");

        VOODOO_DOLL = createNormalItem("Voodoo Doll", "");
        SHIP_IN_BOTTLE = createNormalItem("Ship In Bottle", "");
        PALANTIR = createNormalItem("Palantir", "");
        BLOOD_AND_QUIL = createNormalItem("Blood And Quil", "");
        BUNDLE_OF_HERBS = createNormalItem("Bundle Of Herbs", "");
        MYSTERY_POTION = createAlterMaterialItem("Mystery Potion", "", Material.POTION);
        POISONED_APPLE = createAlterMaterialItem("Poisoned Apple", "", Material.APPLE);
        CARNIVOROUS_PLANT = createNormalItem("Carnivorous Plant", "");
        SCENTED_CANDLE = createAlterMaterialItem("Scented Candle", "", Material.CANDLE);

        COMMON_SHARD = createShard("Common Shard", "");
        UNCOMMON_SHARD = createShard("Uncommon Shard", "");
        RARE_SHARD = createShard("Rare Shard", "");
        ULTRA_RARE_SHARD = createShard("Ultra Rare Shard", "");
        LEGENDARY_SHARD = createShard("Legendary Shard", "");

        tradeItems.addAll(Arrays.asList(
                BABY_ZOMBIE_HIDE,
                ROTTEN_APPLE,
                INSECT,
                BAT,
                THYME,
                OREGANO,
                DILL,
                ROSEMARY,
                GOLDEN_FEATHER,
                DEAD_FLOWER,
                TWIG,
                SOUL_OF_EVOKER,
                VILLAGER_BLOOD,
                VOODOO_DOLL,
                SHIP_IN_BOTTLE,
                PALANTIR,
                BLOOD_AND_QUIL,
                BUNDLE_OF_HERBS,
                MYSTERY_POTION,
                POISONED_APPLE,
                CARNIVOROUS_PLANT,
                SCENTED_CANDLE
        ));
    }
}