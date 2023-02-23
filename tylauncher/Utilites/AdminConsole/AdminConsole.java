package tylauncher.Utilites.AdminConsole;

import tylauncher.Controllers.NewsController;
import tylauncher.Controllers.SettingsController;
import tylauncher.Main;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Settings;
import tylauncher.Utilites.Utils;

import java.util.*;

import static tylauncher.Main.user;
import static tylauncher.Utilites.AdminConsole.Constants.CommandArgs.showArgs;
import static tylauncher.Utilites.AdminConsole.Constants.CommandArgs.testArgs;

public class AdminConsole implements Runnable {
    private static final String[] windows = new String[]{"news", "accountauth", "account", "forum", "play", "register", "runtime", "settings", "updater"};

    public static final String[] object = new String[]{"settings", "user"};
    private static final HashMap<String, String> commands = new HashMap<String, String>(){{
        put("exit", "Выйти из лаунчера");
        put("test [optional..]", "Авторизовывает вас с тестового аккаунта(без возможности зайти на сервер)");
        put("open {window}", "Открывает любое окно лаунчера. Список окон: " + Arrays.toString(windows));
        put("help [optional]", "Вызывает это окно");
        put("love", "Немного любви всем нужно");
        put("reset", "Устанавливает настройки в начальные значение(Логически. Чтобы обновить их визуально, нужно перезайти во вкладку настроек)");
        put("show {whatToShow} [dopArg]" , "Показывает один из следующих объектов: " + Arrays.toString(object));
    }};


    private static final HashMap<String, HashMap<String, String>> commandArgs = new HashMap<String, HashMap<String, String>>(){{
        put("show", showArgs);
        put("test", testArgs);
    }};

    public static NewsController newsController;

    @Override
    public void run() {
        while (true){
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine();
            switch (command.toLowerCase(Locale.ROOT).split(" ")[0]){
                case "setclick":
                    newsController.clicks = Integer.parseInt(command.split(" ")[1]);
                    break;
                case "test":
                    loginTestAcc(command.split(" "));
                    break;
                case "help":
                    help(command.split(" "));
                    break;
                case "open":
                    if(command.split(" ").length != 2){
                        System.err.println("Введи окно, гений");
                        break;
                    }
                    openWindowCommand(command.split(" ")[1]);
                    break;
                case "love":
                    Utils.easter();
                    break;
                case "exit":
                    Main.exit();
                    break;
                case "reset":
                    Settings.reset();
                    SettingsController.writeSettingsToFile();
                    break;
                case "show":
                    if(command.split(" ").length < 2){
                        System.err.println("Введи объект, гений");
                        break;
                    }
                    show(command.split(" "));
                    break;
                default:
                    System.err.println("Неверная команда, список команд \"help\"");
                    break;
            }
        }
    }

    private void loginTestAcc(String[] args){
        System.err.println(Arrays.toString(args));
        if (args.length > 1){
            for (String s : args){
                if (s.toLowerCase(Locale.ROOT).contains("username")){
                    user.setLogin(s.split("=")[1]);
                }
                if (s.toLowerCase(Locale.ROOT).contains("password")){
                    user.setPassword(s.split("=")[1]);
                }
                if (s.toLowerCase(Locale.ROOT).contains("balance")){
                    user.setBalance(s.split("=")[1]);
                }
                if (s.toLowerCase(Locale.ROOT).contains("group")){
                    user.setGroup(s.split("=")[1]);
                }
                user.setSession("test");
            }
            try {
                user.auth(true);
                ManagerWindow.ACCOUNT.open();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        user.setLogin("test");
        user.setPassword("test");
        user.setSession("test");
        try {
            user.auth(true);
            ManagerWindow.ACCOUNT.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void help(String[] command){
        if (command.length > 1){
            switch (command[1]){
                case "show":
                    outHelpArg("show");
                    break;
                case "test":
                    outHelpArg("test");
                    break;
                default:
                    System.err.printf("Для %s команды нет никаких доп. аргументов", command[1]);
                    break;
            }
            return;
        }
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (Map.Entry<String, String> entry : commands.entrySet()) stringJoiner.add(entry.getKey() + " - " + entry.getValue());
        System.err.println(stringJoiner);
        System.err.println("------------------------\n{} - обязательный аргумент");
        System.err.println("[] - необязательный аргумент. Чтобы узнать список необязательных аргументов введите help {команда}");
    }

    private void outHelpArg(String forWhat){
        StringJoiner stringJoiner = new StringJoiner("\n");
        System.err.printf("Для %s команды есть следующие аргументы: \n", forWhat);
        for (Map.Entry<String, String> entry : commandArgs.get(forWhat).entrySet()) stringJoiner.add(entry.getKey() + " - " + entry.getValue());
        System.err.println(stringJoiner);
    }

    private void show(String[] arg){
        switch (arg[1].toLowerCase(Locale.ROOT)){
            case "settings":
                System.err.println(Settings.show());
                break;
            case "user":
                if (arg.length > 2 && arg[2].equalsIgnoreCase("pass")){
                    System.err.println(user);
                    System.err.println("pass = " + user.GetPassword());
                }else System.err.println(user);
                break;
        }
    }

    private void openWindowCommand(String arg){
        switch (arg.toLowerCase(Locale.ROOT)){
            case "news":
                ManagerWindow.NEWS.open();
                break;
            case "accountauth":
                ManagerWindow.ACCOUNT_AUTH.open();
                break;
            case "account":
                ManagerWindow.ACCOUNT.open();
                break;
            case "forum":
                ManagerWindow.FORUM.open();
                break;
            case "play":
                ManagerWindow.PLAY.open();
                break;
            case "register":
                ManagerWindow.REGISTER.open();
                break;
            case "runtime":
                ManagerWindow.RUNTIME_DOWNLOAD.open();
                break;
            case "settings":
                ManagerWindow.SETTINGS.open();
                break;
            case "updater":
                ManagerWindow.UPDATER.open();
                break;
            default:
                System.err.println("Такого окна нет, список окон: " + Arrays.toString(windows));
                break;
        }

    }
}



