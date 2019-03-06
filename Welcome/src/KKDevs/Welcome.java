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
 
 String SubMessage;
 
 String Name;
 
 int time;
 
 public void initConfig(){
this.getDataFolder().mkdirs();
this.saveResource("config.yml");
}
 
 
 public void loadCfg(){
this.reloadConfig();
this.Name = this.getConfig().getString("NameServer","§aName§fServer").replace("§", "\u00A7");
this.SubMessage = this.getConfig().getString("SubMessage", "§l§b>>  §fHave a nice game  §b<<").replace("§", "\u00A7");
this.time = this.getConfig().getInt("time", 60);
}
 
 
 @Override
 public void onEnable() {
  getServer().getPluginManager().registerEvents(this, this);
  this.initConfig();
  this.loadCfg();
  getServer().getScheduler().scheduleRepeatingTask(new BossBarTask(this), this.time);
 }
 
 

 @SuppressWarnings("deprecation")
@EventHandler
 public void onJoin(PlayerJoinEvent e) {

  e.setJoinMessage("");
  new NukkitRunnable() {
   public void run() {
    e.getPlayer().setSubtitle("§l" + SubMessage + "\n");
    e.getPlayer().sendTitle("§l" + Name);
   }
  }.runTaskLater(this, 75);
  bossbar.put(e.getPlayer().getName(), e.getPlayer().createBossBar(getBossText(e.getPlayer()), 25));
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

  String text = TextFormat.WHITE + "      §l>>   " + getRandomColor + "Welcîme to " + GREEN + Name + getRandomColor + ", " + BLUE + getNick + getRandomColor + " | Online: " + getOnline + TextFormat.WHITE + "   <<";
  
  
  
  
  return text;
 }

}
