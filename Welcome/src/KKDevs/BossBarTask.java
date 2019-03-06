package KKDevs;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;

public class BossBarTask extends PluginTask < Welcome > {
	
 public BossBarTask(Welcome owner) {
  super(owner);
 }

@SuppressWarnings("deprecation")
@Override
 public void onRun(int currentTick) {
	
  Welcome owner = this.owner;
  for (Player p: owner.getServer().getOnlinePlayers().values()) {
	   try {
	  p.getPlayer().updateBossBar(owner.getBossText(p), 100, owner.bossbar.get(p.getName()));
	   
   } catch (Exception e) {}
  }
 }
}
