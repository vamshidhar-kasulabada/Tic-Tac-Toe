import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {
    private String playerOne;
    private String playerTwo;
    private char[][] game_board = new char[4][4];
    private boolean is_valid_input = true;
    private Scanner sc = new Scanner(System.in);

    public void start() {

        greet();

        assign_player_names();

        display_players_info();

        fill_game_with_default_values();

        display_game();

        start_playing();

    }

    private void start_playing() {
        int i;
        for (i = 1; i <= 9; i++) {
            System.out.println();

            String current_player = i % 2 == 0 ? playerTwo : playerOne;

            char current_player_symbol = i % 2 == 0 ? 'O' : 'X';

            System.out.println(current_player + " (" + current_player_symbol + "), it's your turn!");
            System.out.println("Enter the row and column numbers of your move (e.g., 1 2): ");

            byte[] input_arr = new byte[2];

            take_input_and_validate_input(input_arr);

            byte row = input_arr[0];
            byte column = input_arr[1];

            game_board[row][column] = current_player_symbol;

            display_game();

            if (i >= 5) {
                Boolean have_won = check_for_a_winner(current_player_symbol, row, column);
                if (have_won) {
                    System.out.println();
                    System.out.println(current_player + " have won the game");
                    break;
                }
            }

        }
        if (i == 10) {
            System.out.println();
            System.out.println("-----Draw-----");
        }
        sc.close();
    }

    private void take_input_and_validate_input(byte[] input_arr) {
        do {
            take_input(input_arr);
            validate_input(input_arr[0], input_arr[1]);
        } while (!is_valid_input);
    }

    private void greet() {

        System.out.println("Let's Begin\n");

        System.out.println("Player One is X, Player Two is O\n");
    }

    private boolean check_for_a_winner(char current_player_symbol, byte row, byte column) {

        if (row == column || (row == 1 && column == 3) || (row == 3 && column == 1)) {

            boolean found_in_row = check_in_row(row, current_player_symbol);
            if (!found_in_row) {

                boolean found_in_columnn = check_in_column(column, current_player_symbol);

                if (!found_in_columnn) {
                    boolean found_in_diagonal = check_in_diagonal(row, column, current_player_symbol);
                    return found_in_diagonal;
                }
                return found_in_columnn;

            }
            return found_in_row;

        } else {
            boolean found_in_row = check_in_row(row, current_player_symbol);
            if (!found_in_row) {
                boolean found_in_columnn = check_in_column(column, current_player_symbol);
                return found_in_columnn;
            }
            return found_in_row;
        }
    }

    private boolean check_in_diagonal(byte row, byte column, char current_player_symbol) {
        if (row == 2 && column == 2) {
            boolean found_in_left_diagonal = check_in_left_diagonal(current_player_symbol);

            if (!found_in_left_diagonal) {
                boolean found_in_right_diagonal = check_in_right_diagonal(current_player_symbol);
                return found_in_right_diagonal;
            }
            return true;
        } else if (row == column) {
            boolean strike_found = check_in_left_diagonal(current_player_symbol);
            return strike_found;
        } else {
            boolean strike_found = check_in_right_diagonal(current_player_symbol);
            return strike_found;
        }
    }

    private boolean check_in_right_diagonal(char current_player_symbol) {
        for (byte i = 1; i <= 3; i++) {
            if (game_board[i][game_board.length - i] != current_player_symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean check_in_left_diagonal(char current_player_symbol) {
        for (byte i = 1; i <= 3; i++) {
            if (game_board[i][i] != current_player_symbol) {
                return false;
            }
        }

        return true;
    }

    private boolean check_in_column(byte column, char current_player_symbol) {
        for (byte i = 1; i <= 3; i++) {
            if (game_board[i][column] != current_player_symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean check_in_row(byte row, char current_player_symbol) {
        for (byte i = 1; i <= 3; i++) {
            if (game_board[row][i] != current_player_symbol) {
                return false;
            }

        }
        return true;
    }

    private void validate_input(byte row, byte column) {
        // Conditions for valid input
        if (row > 0 && column > 0 && row < 4 && column < 4 && game_board[row][column] == '.') {
            is_valid_input = true;
            return;
        }

        if (row < 0 || row > 3 || column < 0 || column > 3) {
            System.out.println("\nInvalid Input, Please enter valid row and column number");
        } else if (game_board[row][column] != '.') {
            System.out.println("\nInvalid Input, Cell already Taken");
        }

        System.out.println("Enter the row and column numbers of your move (e.g., 1 2): ");
        sc.nextLine();

        is_valid_input = false;
    }

    private void take_input(byte[] input_arr) {
        while (true) {
            try {
                input_arr[0] = sc.nextByte();
                input_arr[1] = sc.nextByte();
                break;

            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input, Please enter valid input: ");
                System.out.println("Enter the row and column numbers of your move (e.g., 1 2): ");
                sc.nextLine();

            }
        }
    }

    private void assign_player_names() {
        System.out.println("Enter First player name:");
        this.playerOne = sc.nextLine();
        System.out.println("Enter Second player name:");
        this.playerTwo = sc.nextLine();
    }

    private void fill_game_with_default_values() {
        for (byte i = 0; i <= 3; i++) {
            for (byte j = 0; j <= 3; j++) {
                if (i == 0 && j == 0) {
                    game_board[i][j] = ' ';
                } else if (i == 0 || j == 0) {
                    game_board[i][j] = i == 0 ? (char) (j + 48) : (char) (i + 48);
                } else {
                    game_board[i][j] = '.';
                }
            }
        }
    }

    private void display_players_info() {
        System.out.println();
        System.out.println("Player One is: " + this.playerOne + " (X)");
        System.out.println("Player Two is: " + this.playerTwo + " (O)");
    }

    private void display_game() {
        System.out.println();
        for (byte i = 0; i <= 3; i++) {
            for (byte j = 0; j <= 3; j++) {
                System.out.print(game_board[i][j] + " ");
            }
            System.out.println();
        }
    }

}
