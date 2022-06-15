package it.polimi.ingsw.view.implementation.cli.utils;

/**
 * Class used to store ASCIIArt used in the CLI
 */
public class ASCIIArt {
    public static final String ERIANTYS = """
                                                                                \s
            ,------.,------. ,--.  ,---.  ,--.  ,--.,--------.,--.   ,--.,---.  \s
            |  .---'|  .--. '|  | /  O  \\ |  ,'.|  |'--.  .--' \\  `.'  /'   .-' \s
            |  `--, |  '--'.'|  ||  .-.  ||  |' '  |   |  |     '.    / `.  `-. \s
            |  `---.|  |\\  \\ |  ||  | |  ||  | `   |   |  |       |  |  .-'    |\s
            `------'`--' '--'`--'`--' `--'`--'  `--'   `--'       `--'  `-----' \s
                                                                                \s""";

    public static final String WIZARD_ONE = """
                                                                         \s
            ,--.   ,--.,--.,-------.  ,---.  ,------. ,------.       ,--.\s
            |  |   |  ||  |`--.   /  /  O  \\ |  .--. '|  .-.  \\     /   |\s
            |  |.'.|  ||  |  /   /  |  .-.  ||  '--'.'|  |  \\  :    `|  |\s
            |   ,'.   ||  | /   `--.|  | |  ||  |\\  \\ |  '--'  /     |  |\s
            '--'   '--'`--'`-------'`--' `--'`--' '--'`-------'      `--'\s
                                                                         \s""";

    public static final String WIZARD_TWO = """
                                                                           \s
            ,--.   ,--.,--.,-------.  ,---.  ,------. ,------.       ,---. \s
            |  |   |  ||  |`--.   /  /  O  \\ |  .--. '|  .-.  \\     '.-.  \\\s
            |  |.'.|  ||  |  /   /  |  .-.  ||  '--'.'|  |  \\  :     .-' .'\s
            |   ,'.   ||  | /   `--.|  | |  ||  |\\  \\ |  '--'  /    /   '-.\s
            '--'   '--'`--'`-------'`--' `--'`--' '--'`-------'     '-----'\s
                                                                           \s""";

    public static final String WIZARD_THREE = """
                                                                           \s
            ,--.   ,--.,--.,-------.  ,---.  ,------. ,------.      ,----. \s
            |  |   |  ||  |`--.   /  /  O  \\ |  .--. '|  .-.  \\     '.-.  |\s
            |  |.'.|  ||  |  /   /  |  .-.  ||  '--'.'|  |  \\  :      .' < \s
            |   ,'.   ||  | /   `--.|  | |  ||  |\\  \\ |  '--'  /    /'-'  |\s
            '--'   '--'`--'`-------'`--' `--'`--' '--'`-------'     `----' \s
                                                                           \s""";

    public static final String WIZARD_FOUR = """
                                                                           \s
            ,--.   ,--.,--.,-------.  ,---.  ,------. ,------.        ,---.\s
            |  |   |  ||  |`--.   /  /  O  \\ |  .--. '|  .-.  \\      /    |\s
            |  |.'.|  ||  |  /   /  |  .-.  ||  '--'.'|  |  \\  :    /  '  |\s
            |   ,'.   ||  | /   `--.|  | |  ||  |\\  \\ |  '--'  /    '--|  |\s
            '--'   '--'`--'`-------'`--' `--'`--' '--'`-------'        `--'\s
                                                                           \s""";

    public static final String ISLAND = """
            mother nature : \u001b[31m●\u001b[0m    influence tower: \u001b[97;1m●\u001b[0m \u001b[30m●\u001b[0m \u001b[37;1m●\u001b[0m
               __________             __________             __________             __________             __________             __________
              /  %s  %s    \\           /  %s   %s   \\           /  %s   %s   \\           /  %s   %s   \\           /  %s   %s   \\           /  %s   %s   \\
             /      %s     \\         /      %s     \\         /      %s     \\         /      %s     \\         /      %s     \\         /      %s     \\
            /  %s %s %s \\       /  %s %s %s \\       /  %s %s %s \\       /  %s %s %s \\       /  %s %s %s \\       /  %s %s %s \\
            \\    %s %s   /       \\    %s %s   /       \\    %s %s   /       \\    %s %s   /       \\    %s %s   /       \\    %s %s   /
             \\            /         \\            /         \\            /         \\            /         \\            /         \\            /
              \\__________/           \\__________/           \\__________/           \\__________/           \\__________/           \\__________/
                  %s                   %s                   %s                   %s                   %s                   %s
               __________             __________             __________             __________             __________             __________
              /  %s  %s    \\           /  %s   %s   \\           /  %s   %s   \\           /  %s   %s   \\           /  %s   %s   \\           /  %s   %s   \\
             /      %s     \\         /      %s     \\         /      %s     \\         /      %s     \\         /      %s     \\         /      %s     \\
            /  %s %s %s \\       /  %s %s %s \\       /  %s %s %s \\       /  %s %s %s \\       /  %s %s %s \\       /  %s %s %s \\
            \\    %s %s   /       \\    %s %s   /       \\    %s %s   /       \\    %s %s   /       \\    %s %s   /       \\    %s %s   /
             \\            /         \\            /         \\            /         \\            /         \\            /         \\            /
              \\__________/           \\__________/           \\__________/           \\__________/           \\__________/           \\__________/
                  %s                   %s                   %s                   %s                   %s                  %s
                  """;

    public static final String SCHOOL_BOARD = """
            ___________________
            | %s   %s   %s   %s   |
            | %s   %s   %s   %s   |
            |_________________|
            |  %s  %s  %s  %s  %s  |
            |_________________|
            |  %s  %s  %s  %s  %s  |
            |  %s  %s  %s  %s  %s  |
            |  %s  %s  %s  %s  %s  |
            |  %s  %s  %s  %s  %s  |
            |  %s  %s  %s  %s  %s  |
            |  %s  %s  %s  %s  %s  |
            |  %s  %s  %s  %s  %s  |
            |  %s  %s  %s  %s  %s  |
            |  %s  %s  %s  %s  %s  |
            |  %s  %s  %s  %s  %s  |
            |_________________|
            | %s  %s  %s  %s  %s   |
            | %s   %s   %s   %s   |
            |_________________|
            """;

    public static final String SCHOOL_BOARD_HORIZONTAL_2_0 = """
                               %s                                                                 %s
                       School Board:             Current Assistant Card:          School Board:                   Current Assistant Card:
            _______________________________________      __________          _______________________________________      __________
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      | %s   %s |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      | %s   %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |        |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |        |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |________|          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |________|
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                          | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |
            |_____|_____________________|___|_____|                          |_____|_____________________|___|_____|
            """;

    public static final String SCHOOL_BOARD_HORIZONTAL_2_1 = """
                               %s                                                          %s
                       School Board:             Current Assistant Card:          School Board:
            _______________________________________      __________          _______________________________________
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      | %s   %s |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |        |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |________|          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                          | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |
            |_____|_____________________|___|_____|                          |_____|_____________________|___|_____|
            """;

    public static final String SCHOOL_BOARD_HORIZONTAL_2_2 = """
                               %s                                                                 %s
                       School Board:                       School Board:                   Current Assistant Card:
            _______________________________________                _______________________________________      __________
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      | %s   %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |        |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |________|
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |
            |_____|_____________________|___|_____|                |_____|_____________________|___|_____|
            """;

    public static final String SCHOOL_BOARD_HORIZONTAL_2_3 = """
                               %s                                             %s
                       School Board:                                     School Board:
            _______________________________________                _______________________________________
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |
            |_____|_____________________|___|_____|                |_____|_____________________|___|_____|
            """;


    public static final String SCHOOL_BOARD_HORIZONTAL_3_0 = """
                            %s                                                           %s                                                           %s
                       School Board:          Current Assistant Card:       School Board:                Current Assistant Card:     School Board:              Current Assistant Card:
            _______________________________________   __________       _______________________________________   __________      _______________________________________    __________
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |   | %s   %s |       | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |   | %s   %s |       | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |   | %s   %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |   |        |       | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |   |        |       | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |   |        |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |   |________|       | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |   |________|       | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |   |________|
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                    | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                    | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                    | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                    | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |
            |_____|_____________________|___|_____|                    |_____|_____________________|___|_____|                    |_____|_____________________|___|_____|
            """;

    public static final String SCHOOL_BOARD_HORIZONTAL_3_1 = """
                               %s                                                                 %s                                                %s
                       School Board:             Current Assistant Card:          School Board:                   Current Assistant Card:          School Board:
            _______________________________________      __________          _______________________________________      __________          _______________________________________
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      | %s   %s |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      | %s   %s |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |        |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |        |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |________|          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |________|          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                          | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                          | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |
            |_____|_____________________|___|_____|                          |_____|_____________________|___|_____|                          |_____|_____________________|___|_____|
            """;

    public static final String SCHOOL_BOARD_HORIZONTAL_3_2 = """
                               %s                                                     %s                                              %s
                       School Board:             Current Assistant Card:          School Board:                             School Board:                   Current Assistant Card:
            _______________________________________      __________          _______________________________________            _______________________________________      __________
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      | %s   %s |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      | %s   %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |        |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |        |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |________|          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |________|
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                          | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |
            |_____|_____________________|___|_____|                          |_____|_____________________|___|_____|            |_____|_____________________|___|_____|
            """;

    public static final String SCHOOL_BOARD_HORIZONTAL_3_3 = """
                               %s                                                     %s                                               %s
                       School Board:             Current Assistant Card:          School Board:                                     School Board:
            _______________________________________      __________          _______________________________________            _______________________________________
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      | %s   %s |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |        |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |________|          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                          | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |
            |_____|_____________________|___|_____|                          |_____|_____________________|___|_____|            |_____|_____________________|___|_____|
            """;

    public static final String SCHOOL_BOARD_HORIZONTAL_3_4 = """
                               %s                                                                 %s                                                                 %s
                       School Board:                             School Board:                   Current Assistant Card:          School Board:                Current Assistant Card:
            _______________________________________           _______________________________________      __________          _______________________________________      __________
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |           | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      | %s   %s |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      | %s   %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |           | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |        |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |        |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |           | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |________|          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |________|
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |           | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |           | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                          | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |
            |_____|_____________________|___|_____|           |_____|_____________________|___|_____|                          |_____|_____________________|___|_____|
            """;

    public static final String SCHOOL_BOARD_HORIZONTAL_3_5 = """
                             %s                                                                 %s                                  %s
                       School Board:                           School Board:                   Current Assistant Card:          School Board:
            _______________________________________           _______________________________________      __________          _______________________________________
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |           | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      | %s   %s |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |           | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |        |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |           | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |________|          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                          | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |           | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                          | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |
            |_____|_____________________|___|_____|           |_____|_____________________|___|_____|                          |_____|_____________________|___|_____|
            """;

    public static final String SCHOOL_BOARD_HORIZONTAL_3_6 = """
                               %s                                             %s                                             %s
                       School Board:                                     School Board:                                        School Board:
            _______________________________________                _______________________________________                _______________________________________      __________
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      | %s   %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |        |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |      |________|
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |
            |_____|_____________________|___|_____|                |_____|_____________________|___|_____|                |_____|_____________________|___|_____|
            """;


    public static final String SCHOOL_BOARD_HORIZONTAL_3_7 = """
                          %s                                                %s                                                %s
                       School Board:                                       School Board:                                       School Board:
            _______________________________________                _______________________________________                _______________________________________
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |                | %s %s | %s %s %s %s %s %s %s %s %s %s | %s | %s %s |
            | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |                | %s   | %s %s %s %s %s %s %s %s %s %s | %s |     |
            |_____|_____________________|___|_____|                |_____|_____________________|___|_____|                |_____|_____________________|___|_____|
            """;

    public static final String AVAILABLE_PROFESSOR = """
            %s %s %s %s %s
            """;

    public static final String CLOUD_TILE_2_1 = """
               _____
             _(     )_
            (_ %s %s %s _)
              (_____)
                 %s
                 """;

    public static final String CLOUD_TILE_2_2 = """
               _____             _____
             _(     )_         _(     )_
            (_ %s %s %s _)       (_ %s %s %s _)
              (_____)           (_____)
                 %s                %s
                 """;

    public static final String CLOUD_TILE_3_1 = """
               _____
             _(     )_
            (_%s %s %s %s_)
              (_____)
                 %s
                 """;

    public static final String CLOUD_TILE_3_2 = """
               _____             _____
             _(     )_         _(     )_
            (_%s %s %s %s_)       (_%s %s %s %s_)
              (_____)           (_____)
                 %s                %s
                 """;

    public static final String CLOUD_TILE_3_3 = """
               _____             _____             _____
             _(     )_         _(     )_         _(     )_
            (_%s %s %s %s_)       (_%s %s %s %s_)       (_%s %s %s %s_)
              (_____)           (_____)           (_____)
                 %s                %s                %s
                 """;

    public static final String CURRENT_ASSISTANT_CARD = """
            Current Assistant Card
            __________
            | %s   %s |
            |        |
            |________|
            """;

    public static final String ASSISTANT_CARDS_1 = """
            __________
            | %s   %s |
            |        |
            |________|
                %s
                """;

    public static final String ASSISTANT_CARDS_2 = """
            __________        __________
            | %s   %s |        | %s   %s |
            |        |        |        |
            |________|        |________|
                %s                %s
                """;

    public static final String ASSISTANT_CARDS_3 = """
            __________        __________        __________
            | %s   %s |        | %s   %s |        | %s   %s |
            |        |        |        |        |        |
            |________|        |________|        |________|
                %s                 %s                 %s
                """;

    public static final String ASSISTANT_CARDS_4 = """
            __________        __________        __________        __________
            | %s   %s |        | %s   %s |        | %s   %s |        | %s   %s |
            |        |        |        |        |        |        |        |
            |________|        |________|        |________|        |________|
                %s                 %s                 %s                 %s
                """;

    public static final String ASSISTANT_CARDS_5 = """
            __________        __________        __________        __________        __________
            | %s   %s |        | %s   %s |        | %s   %s |        | %s   %s |        | %s   %s |
            |        |        |        |        |        |        |        |        |        |
            |________|        |________|        |________|        |________|        |________|
                %s                 %s                 %s                 %s                 %s
                """;

    public static final String CHARACTER_CARD = """
                %s                            %s                          %s
            %s        %s         %s
            __________                  __________                  __________
            |      %s |                  |      %s |                  |      %s |
            | %s %s %s %s|                  | %s %s %s %s|                  | %s %s %s %s|
            |________|                  |________|                  |________|
            %s %s %s %s %s %s                  %s %s %s %s %s %s                 %s %s %s %s %s %s
            """;

    public static final String ACTIVE_CHARACTER_CARD = """
            %s
            __________
            |      %s |
            | %s %s %s %s|
            |________|
            %s %s %s %s %s %s
            """;


}
