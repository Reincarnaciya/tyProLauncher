package tylauncher.Utilites.AdminConsole.Constants;

import java.util.HashMap;

public class CommandArgs {
    public static final HashMap<String, String> showArgs = new HashMap<String, String>(){{
        put("user pass", "Дополнительно выводит пароль");
    }};

    public static final HashMap<String, String> testArgs = new HashMap<String, String>(){{
        put("userName=...", "Добавляет к авторизации свой никнейм");
        put("password=...", "Добавляет к авторизации свой пароль");
        put("balance=...", "Добавляет к авторизации свой баланс");
        put("group=...", "Добавляет к авторизации свою группу");
    }};
}
