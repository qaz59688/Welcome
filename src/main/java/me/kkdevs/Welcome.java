package me.kkdevs;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.SetLocalPlayerAsInitializedPacket;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.util.HashMap;
import java.util.Random;

public class Welcome extends PluginBase implements Listener {

    private Config cfg;
    public HashMap <String, Long> bossbar = new HashMap ();
    private String Title;
    private String SubTitle;
    private int interval;

    @Override
    @SuppressWarnings("deprecation")
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.cfg = new Config("plugins/Welcome/config.yml", Config.YAML);
        if (this.cfg.get("Title") == null || this.cfg.get("SubTitle") == null || this.cfg.get("BossBar_Text") == null) {
            this.cfg.set("Title", "§l§aName§fServer");
            this.cfg.set("SubTitle", "§l§b>> §fHave a nice game §b<<");
            this.cfg.set("BossBar_Text", "      §l§f>>  {rnd} Welcоme to {Title}{rnd}, §b{name}{rnd} | Online: {online}   §f<<");
            this.cfg.set("interval", 20);
            this.cfg.save(true);
        }

        this.Title = this.cfg.getString("Title");
        this.SubTitle = this.cfg.getString("SubTitle");
        this.interval = this.cfg.getInt("interval");

        this.getServer().getScheduler().scheduleRepeatingTask(() -> {
            for(Player p: getServer().getOnlinePlayers().values()) {
                if(bossbar.get(p.getName().toLowerCase()) != null) {
                    p.updateBossBar(getText(p), 100, bossbar.get(p.getName().toLowerCase()));
                }
            }
        }, interval, true);
    }

    @Deprecated
    @EventHandler
    public void onPacketReceive(DataPacketReceiveEvent e) {
        Player p = e.getPlayer();
        if(e.getPacket() instanceof SetLocalPlayerAsInitializedPacket) {
            bossbar.put(p.getName().toLowerCase(), p.createBossBar(this.getText(p), 100));
            p.sendTitle(Title, SubTitle);
        }
    }

    public String getRndColor() {
        String[] RC = {
                "§2",
                "§3",
                "§4",
                "§5",
                "§6",
                "§7",
                "§a",
                "§b",
                "§c",
                "§d",
                "§e",
                "§f"
        };
        return RC[new Random().nextInt(RC.length)];
    }

    public String getText(Player p) {
        return this.cfg.getString("BossBar_Text")
                .replace("{rnd}", getRndColor())
                .replace("{Title}", this.Title)
                .replace("{name}", p.getName())
                .replace("{online}", Integer.toString(this.getServer().getOnlinePlayers().size()));
    }

    @Override
    public void onDisable() {
        this.cfg.save();
    }
}
