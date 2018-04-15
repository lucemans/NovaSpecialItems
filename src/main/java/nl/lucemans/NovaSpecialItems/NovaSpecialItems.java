package nl.lucemans.NovaSpecialItems;

import nl.lucemans.NovaItems.NItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Created by Lucemans at 15/04/2018
 * See https://lucemans.nl
 */
public class NovaSpecialItems extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        // Double stone slab
        ItemStack doubleSlab = NItem.create(Material.STEP).setName("&rDouble Slab").setEnchantment(Enchantment.SILK_TOUCH, 1).make();
        Bukkit.addRecipe(new ShapelessRecipe(doubleSlab).addIngredient(Material.STEP).addIngredient(Material.STEP));

        // Register Listeners
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled())
            return;

        // Sets the block
        if (event.getItemInHand().getType() == Material.STEP)
            if (event.getItemInHand().hasItemMeta())
                if (event.getItemInHand().getItemMeta().hasDisplayName())
                    if (event.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§rDouble Slab")) {
                        event.getBlockPlaced().setType(Material.DOUBLE_STEP);
                        event.getBlockPlaced().setData((byte) 8);
                    }
    }
}
