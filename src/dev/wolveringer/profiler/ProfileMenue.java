package dev.wolveringer.profiler;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.BungeeCord;
import dev.wolveringer.BungeeUtil.Main;
import dev.wolveringer.BungeeUtil.Material;
import dev.wolveringer.BungeeUtil.item.MultiClickItemStack;
import dev.wolveringer.BungeeUtil.item.ItemStack;
import dev.wolveringer.api.inventory.Inventory;
import dev.wolveringer.api.inventory.ScrolingInventory;

public class ProfileMenue {
	private static ProfileMenue menue = new ProfileMenue();
	static{
		menue = new ProfileMenue();
	}

	public static ProfileMenue getProfilerMenue() {
		return menue;
	}

	private ScrolingInventory inv = new ScrolingInventory(4, "§aTimings");
	private Inventory inv_disabled = new Inventory(9, "§cTimings Disabled");

	public ProfileMenue() {
		rebuild();
		BungeeCord.getInstance().getScheduler().schedule(Main.getMain(), new Runnable() {
			@Override
			public void run() {
				rebuild();
			}
		}, 1, 5, TimeUnit.SECONDS);
		ItemStack is = new MultiClickItemStack(Material.BARRIER);
		is.getItemMeta().setDisplayName("§cTimings are §c§nDisabled");
		inv_disabled.setItem(4, is);
	}

	protected void rebuild() {
		if(!Profiler.isEnabled())
			return;
		inv.disableUpdate();
		inv.clear();
		for(Profiler p : Profiler.getProfilers()){
			p.updateInventory();
			inv.addItem(build(p));
		}
		inv.enableUpdate();
	}

	private ItemStack build(final Profiler profile) {
		ItemStack is = new ItemStack(Material.WATCH) {
			@Override
			public void click(Click p) {
				p.getPlayer().openInventory(profile.getInventory());
			}
		};
		is.getItemMeta().setDisplayName("§bProfiler: §5" + profile.getName());
		return is;
	}

	public Inventory getMenue() {
		if(Profiler.isEnabled())
			return inv;
		else
			return inv_disabled;
	}
}
