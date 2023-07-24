import java.util.Scanner;

public class TicTacToe {
    private String playerOne;
    private String playerTwo;
    private char[][] gameArr = new char[4][4];
    private boolean is_valid_input = true;
    private Scanner sc = new Scanner(System.in);

    public void start() {

        greet();

        assign_player_names();

        display_players_info();

        fill_game_with_default_values();

        display_input_guide();

        display_game();

        start_playing();

    }

    private void start_playing() {
        int i;
        for (i = 1; i <= 9; i++) {
            System.out.println();

            String current_player = i % 2 == 0 ? playerTwo : playerOne;

            char current_player_symbol = i % 2 == 0 ? 'O' : 'X';

            System.out.println(current_player + "'s turn " + "(" + current_player_symbol + ")" + ":");

            int[] input_arr;

            do {
                if (!is_valid_input) {
                    System.out.println("Please Enter valid input: ");
                }
                input_arr = take_input();
                validate_input(input_arr);
            } while (!is_valid_input);

            gameArr[input_arr[0]][input_arr[1]] = current_player_symbol;

            display_game();

            if (i >= 5) {
                Boolean have_won = check_for_a_winner(current_player_symbol, input_arr);
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

    private void greet() {

        System.out.println("Let's Begin\n");

        System.out.println("First player symbol is X, second player symbol is O\n");
    }

    private boolean check_for_a_winner(char current_player_symbol, int[] input_arr) {

        int row = input_arr[0];
        int coloum = input_arr[1];

        if (row == coloum || (row == 1 && coloum == 3) || (row == 3 && coloum == 1)) {

            boolean found_in_row = check_in_row(row, current_player_symbol);
            if (!found_in_row) {

                boolean found_in_coloumn = check_in_coloum(coloum, current_player_symbol);

                if (!found_in_coloumn) {
                    boolean found_in_diagonal = check_in_diagonal(row, coloum, current_player_symbol);
                    return found_in_diagonal;
                }
                return found_in_coloumn;

            }
            return found_in_row;

        } else {
            boolean found_in_row = check_in_row(row, current_player_symbol);
            if (!found_in_row) {
                boolean found_in_coloumn = check_in_coloum(coloum, current_player_symbol);
                return found_in_coloumn;
            }
            return found_in_row;
        }
    }

    private boolean check_in_diagonal(int row, int coloum, char current_player_symbol) {
        if (row == 2 && coloum == 2) {
            boolean found_in_left_diagonal = check_in_left_diagonal(current_player_symbol);

            if (!found_in_left_diagonal) {
                boolean found_in_right_diagonal = check_in_right_diagonal(current_player_symbol);
                return found_in_right_diagonal;
            }
            return true;
        } else if (row == coloum) {
            boolean strike_found = check_in_left_diagonal(current_player_symbol);
            return strike_found;
        } else {
            boolean strike_found = check_in_right_diagonal(current_player_symbol);
            return strike_found;
        }
    }

    private boolean check_in_right_diagonal(char current_player_symbol) {
        for (int i = 1; i <= 3; i++) {
            if (gameArr[i][gameArr.length - i] != current_player_symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean check_in_left_diagonal(char current_player_symbol) {
        for (int i = 1; i <= 3; i++) {
            if (gameArr[i][i] != current_player_symbol) {
                return false;
            }
        }

        return true;
    }

    private boolean check_in_coloum(int coloum, char current_player_symbol) {
        for (int i = 1; i <= 3; i++) {
            if (gameArr[i][coloum] != current_player_symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean check_in_row(int row, char current_player_symbol) {
        for (int i = 1; i <= 3; i++) {
            if (gameArr[row][i] != current_player_symbol) {
                return false;
            }

        }
        return true;
    }

    private void validate_input(int[] input_arr) {
        int row = input_arr[0];
        int column = input_arr[1];

        if (row > 0 && column > 0 && row < 4 && column < 4 && gameArr[row][column] == '.') {
            is_valid_input = true;
            return;
        }
        is_valid_input = false;
    }

    private int[] take_input() {
        int row = sc.nextInt();
        int coloum = sc.nextInt();

        return new int[] { row, coloum };
    }

    private void assign_player_names() {
        System.out.println("Enter First player name:");
        this.playerOne = sc.nextLine();
        System.out.println("Enter Second player name:");
        this.playerTwo = sc.nextLine();
    }

    private void fill_game_with_default_values() {
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (i == 0 && j == 0) {
                    gameArr[i][j] = ' ';
                } else if (i == 0 || j == 0) {
                    gameArr[i][j] = i == 0 ? (char) (j + 48) : (char) (i + 48);
                } else {
                    gameArr[i][j] = '.';
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
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                System.out.print(gameArr[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void display_input_guide() {
        System.out.println();
        System.out.println("Enter co-ordinates like row space column. Ex 2 3");
    }
}
