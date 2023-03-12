package me.dalot;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.dalot.commands.AnnounceCommand;
import me.dalot.initializing.Config;
import me.dalot.initializing.DependencyChecker;
import me.dalot.initializing.VelocityPlugin;
import me.dalot.managers.AnnouncerManager;
import me.dalot.managers.ChatManager;
import org.slf4j.Logger;

@Plugin(
        id = "ProxyAnnounce",
        name = "ProxyAnnounce",
        version = "1.3.6",
        authors = {"Dalot421"}
)
public class ProxyAnnouncer extends VelocityPlugin {
    static ProxyAnnouncer instance;
    private boolean protocolize;

    @Inject
    public ProxyAnnouncer(ProxyServer server, Logger logger) {
        this.init(server, logger, "announcer");
        if (DependencyChecker.protocolize()) {
            protocolize = true;
        } else {
            protocolize = false;
        }
        instance = this;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Config.init();
        getServer().getCommandManager().register("vannouncer", new AnnounceCommand(), "velocityannouncer", "va");
        getServer().getEventManager().register(this, new ChatManager());
        AnnouncerManager.load();
        AnnouncerManager.init();
    }

    public static ProxyAnnouncer getInstance() {
        return instance;
    }

    public static boolean hasProtocolize() {
        return instance.protocolize;
    }
}
