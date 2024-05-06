package dev.acentra.disguise.utils.permissions;

public interface Permissions {
    String NAMESPACE = "ldisguise.";

    String NICK = NAMESPACE + "nick";
    String NICK_LIST = NAMESPACE + "nicklist";

    String DISGUISE = NAMESPACE + "disguise";
    String DISGUISE_LIST = NAMESPACE + "disguiselist";

    String RESET = NAMESPACE + "reset";
    String REVEAL = NAMESPACE + "reveal";

    String ALERTS = NAMESPACE + "alerts";

    String BYPASS_BLACKLIST = NAMESPACE + "bypassblacklist";

    //Allows players to use the open command disguise-rank
    String RANKCHANGE = NAMESPACE + "rankchange";

    //Allows players to use open command disguise-gui
    String DISGUISECORE = NAMESPACE + "disguisecore";

    //Allows players to use the open command only staff disguise <name>
    String DISGUISE_NAME = NAMESPACE + "disguise_name";

    //Allows players to use the nick <name>
    String NICK_ARGS_NAME = NAMESPACE + "NICK_ARGS_NAME";

}
