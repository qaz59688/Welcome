package KKDevs;

import java.util.HashMap;
import java.util.Random;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.utils.TextFormat;

public class Welcome extends PluginBase implements Listener {

public HashMap < String, Long > bossbar = new HashMap < String, Long > ();
 String message;
 String submessage;

 @Override
 public void onEnable() {
  getServer().getPluginManager().registerEvents(this, this);
  getServer().getScheduler().scheduleRepeatingTask(new BossBarTask(this), 20);
  this.getLogger().info(TextFormat.GREEN + "Enable!");
  this.getDataFolder().mkdirs();
  this.saveDefaultConfig();
  this.getConfig();
 }

 @Override
 public void onDisable() {
  this.getLogger().info(TextFormat.RED + "Disable!");
 }

 @EventHandler
 public void onJoin(PlayerJoinEvent e) {

  e.setJoinMessage("");
  this.message = this.getConfig().getString("Message", "§l§aNameServer").replace("§", "\u00A7");
  this.submessage = this.getConfig().getString("SubMessage", "§l§b>>  §fприятной игры  §b<<").replace("§", "\u00A7");
  new NukkitRunnable() {
   public void run() {
    e.getPlayer().setSubtitle("§l" + submessage + "\n");
    e.getPlayer().sendTitle("§l" + message);
   }
  }.runTaskLater(this, 21);
   bossbar.put(e.getPlayer().getName(), e.getPlayer().createBossBar(getBossText(e.getPlayer()), 100));
 }

 @EventHandler
 public void onQuit(PlayerQuitEvent e) {
  e.setQuitMessage("");
 }

 @EventHandler
 public void onDeath(PlayerDeathEvent e) {
  e.setDeathMessage("");
 }


 public String getBossText(Player p) {
   int getOnline = this.getServer().getOnlinePlayers().size();
   String getNick = p.getDisplayName();
   String[] RC = {
    "§l§f",
    "§l§a",
    "§l§e",
    "§l§b",
    "§l§6"
   };
   String getRandomColor = RC[new Random().nextInt(RC.length)];
   TextFormat GREEN = TextFormat.GREEN;
   TextFormat BLUE = TextFormat.BLUE;
   
String text = TextFormat.WHITE + "§l>>   " + getRandomColor + "Добро пожаловать на " + GREEN + message + getRandomColor + ", " + BLUE + getNick + getRandomColor + " | Онлайн: " + getOnline + TextFormat.WHITE + "   <<";
   return text;
 }

}