package nl.lucemans.NovaSpecialItems;

import nl.lucemans.NovaItems.NItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
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

    @Override
    public void onDisable() {
        Bukkit.resetRecipes();
        Bukkit.clearRecipes();
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player))
        {
            sender.sendMessage("Players only, im sorry.");
            return true;
        }

        Player p = (Player) sender;

        if (command.getLabel().equalsIgnoreCase("nitem"))
        {
            if (args.length == 0)
            {
                sender.sendMessage("Hey did you mean /nitem droppable");
                return true;
            }

            if (args[0].equalsIgnoreCase("droppable"))
            {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (item == null || item.getType() == Material.AIR)
                {
                    sender.sendMessage("Sorry you must be holding an item to do that.");
                    return true;
                }

                NItem i = NItem.create(item);
                String nondrop = ChatColor.translateAlternateColorCodes('&', "&7Non-Droppable");
                if (i.description.contains(nondrop))
                    i.description.remove(nondrop);
                else
                    i.description.add(nondrop);

                sender.sendMessage("There ya go!");
                return true;
            }
        }

        return true;
    }

    public void onItemDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        NItem i = NItem.create(item);

        String nondrop = ChatColor.translateAlternateColorCodes('&', "&7Non-Droppable");
        if (i.description.contains(nondrop))
        {
            event.setCancelled(true);
        }
    }
}
