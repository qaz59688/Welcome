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
		   while(true) {
		for (int x = -25; x <= 100; x = x + 25, Thread.sleep(75)) {
			p.getPlayer().updateBossBar(owner.getBossText(p), x, owner.bossbar.get(p.getName()));
		}
		   }
	  // p.getPlayer().updateBossBar(owner.getBossText(p), x, owner.bossbar.get(p.getName()));
	   
   } catch (Exception e) {}
  }
 }
}