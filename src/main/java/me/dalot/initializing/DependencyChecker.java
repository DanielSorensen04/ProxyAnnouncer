package me.dalot.initializing;

public class DependencyChecker {

    public static boolean protocolize() {
        try {
            Class.forName("dev.simplix.protocolize.api.Protocolize");
            return true;
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }
}
