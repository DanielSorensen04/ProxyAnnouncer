package me.dalot.commands;

import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.dalot.ProxyAnnouncer;
import me.dalot.classes.AnnouncerMessage;
import me.dalot.gui.guis.AnnouncementsScreen;
import me.dalot.initializing.Config;
import me.dalot.initializing.VelocityChat;
import me.dalot.managers.AnnouncerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnounceCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player)) {
            invocation.source().sendMessage(Component.text("Sorry! That command is player only!").color(TextColor.color(255, 0, 0)));
            return;
        }
        Player p = (Player) invocation.source();
        if (p.hasPermission("vannouncer.commands.use")) {
            String[] args = invocation.arguments();
            if (args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "about" -> {
                        p.sendMessage(VelocityChat.color("&b&lBUILDERS REFUGE &7» &fRunning v1.1.14"));
                        p.sendMessage(VelocityChat.color("&b&lBUILDERS REFUGE &7» &fCreated by &b&nDalot421"));
                    }
                    case "reload" -> {
                        if (p.hasPermission("vannouncer.commands.reload")) {
                            p.sendMessage(VelocityChat.color("&b&lBUILDERS REFUGE &7» &fReloading!"));
                            Config.init();
                            AnnouncerManager.load();
                            p.sendMessage(VelocityChat.color("&b&lBUILDERS REFUGE &7» &fReloaded all data from files!"));
                        } else {
                            p.sendMessage(VelocityChat.color("&b&lBUILDERS REFUGE &7» &fRunning v1.1.14"));
                        }
                    }
                    case "force" -> {
                        if (args.length > 1) {
                            String val = args[1];
                            AnnouncerMessage msg = AnnouncerManager.byId(val);
                            if (msg != null) {
                                ProxyAnnouncer.getInstance().getServer().getAllPlayers().forEach(msg::execute);
                            } else {
                                p.sendMessage(VelocityChat.color("&b&lBUILDERS REFUGE &7» &cNo valid message by that ID!"));
                            }
                        }
                    }
                    default -> {
                        p.sendMessage(VelocityChat.color("&b&lBUILDERS REFUGE &7» &fRunning v1.1.14"));
                    }
                }
            } else {
                if (!ProxyAnnouncer.hasProtocolize()) {
                    p.sendMessage(VelocityChat.color("&b&lBUILDERS REFUGE &7» &fYou do not have &a&nProtocolize&f installed!")
                            .clickEvent(ClickEvent.openUrl("https://github.com/Exceptionflug/protocolize")));
                    return;
                }
                AnnouncementsScreen screen = new AnnouncementsScreen();
                screen.define(p);
                screen.open();
            }
        } else {
            p.sendMessage(VelocityChat.color("&b&lBUILDERS REFUGE &7» &fRunning v1.1.14"));
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] args = invocation.arguments();
        String current = args.length > 0 ? args[args.length - 1] : "";
        if (args.length == 1 || args.length == 0) {
            return Stream.of("about", "reload", "force")
                    .filter(m -> m.startsWith(current))
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("force")) {
                return AnnouncerManager.getAll().stream()
                        .map(AnnouncerMessage::getId)
                        .filter(current::startsWith)
                        .collect(Collectors.toList());
            }
        }
        return ImmutableList.of();
    }
}
