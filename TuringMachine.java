import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * The type Turing machine class.
 */
public class TuringMachine {
    /**
     * The current State.
     */
    String state;

    /**
     * The Tape.
     */
    ArrayList<String> tape;

    /**
     * The Tape head(pointer).
     */
    int tapeHead;

    /**
     * Instantiates a new Turing machine.
     *
     * @param tape the tape
     */
    public TuringMachine(ArrayList<String> tape) {
        this.tape = tape;
        this.state = "q0";
        this.tapeHead = 0;
    }

    /**
     * Gets tape head.
     *
     * @return the tape head
     */
    public int getTapeHead() {
        return tapeHead;
    }

    /**
     * Sets machine state.
     *
     * @param state the state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Updates machine with new input and new state.
     *
     * @param input    the input
     * @param newInput the new input
     * @param newState the new state
     * @return the boolean
     */
    public boolean updateMachine(String input, String newInput, String newState) {
        if (Objects.equals(tape.get(getTapeHead()), input)) {
            tape.set(this.tapeHead, newInput);
            setState(newState);
            return true;
        }
        return false;
    }

    /**
     * Move head to left or right direction.
     *
     * @param goRight the go right
     */
    public void moveHead(boolean goRight) {
        if (goRight) {
            this.tapeHead++;
        } else {
            this.tapeHead--;
        }
    }

    /**
     * Print machine status.
     *
     * @param tape     the tape
     * @param state    the state
     * @param tapeHead the tape head
     */
    public void printStatus(ArrayList<String> tape, String state, int tapeHead) {
        System.out.println("tape: " + tape);
        System.out.println("we are at state: " + state);
        System.out.println("tape head is on index: " + tapeHead);
        System.out.println("\n");
    }

    /**
     * Turing machine main function.
     */
    public void turingMachine() {
        while (true) {
            printStatus(tape, state, tapeHead);

            //apply switch case for different machine states and input and move head.
            switch (state) {
                case "q0":
                    if (updateMachine("-", "-", "q1"))
                        moveHead(true);
                    break;

                case "q1":
                    if (updateMachine("1", "1", "q1")) {
                        moveHead(true);
                    }
                    if (updateMachine("0", "0", "q1")) {
                        moveHead(true);
                    }
                    if (updateMachine("-", "-", "q2")) {
                        moveHead(false);
                    }
                    break;

                case "q2":
                    if (updateMachine("0", "0", "q3")) {
                        moveHead(true);
                    }
                    if (updateMachine("1", "0", "q6")) {
                        moveHead(false);
                    }
                    break;

                case "q3":
                    if (updateMachine("-", "-", "q4")) {
                        moveHead(false);
                    }
                    break;

                case "q4":
                    if (updateMachine("0", "-", "q5")) {
                        moveHead(true);
                    }
                    break;

                case "q6":
                    if (updateMachine("1", "0", "q6")) {
                        moveHead(false);
                    }
                    if (updateMachine("0", "1", "q7")) {
                        moveHead(false);
                    }
                    break;

                case "q7":
                    if (updateMachine("1", "1", "q7")) {
                        moveHead(true);
                    }
                    if (updateMachine("0", "0", "q7")) {
                        moveHead(true);
                    }
                    if (updateMachine("-", "-", "q8")) {
                        moveHead(false);
                    }
                    break;

                case "q8":
                    if (updateMachine("0", "-", "q9")) {
                        moveHead(true);
                    }
                    break;
            }

            //check machine acceptance requirement
            if (state.equals("q5") || state.equals("q9")) {
                System.out.println("accepted with status: ");
                printStatus(tape, state, tapeHead);

                return;
            }
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println("Please enter a binary number: ");

        //get our tape input.
        Scanner sc = new Scanner(System.in);
        String inputTape = sc.nextLine();

        String[] strSplit = inputTape.split("");
        ArrayList<String> tape = new ArrayList<>();

        for (int i = 0; i < strSplit.length + 2; i++) {

            //insert blank character at the beginning.
            if (i == 0) {
                tape.add("-");
                continue;
            }

            //insert blank character at the end.
            if (i == strSplit.length + 1) {
                tape.add("-");
                break;
            }

            //add input to tape.
            tape.add(strSplit[i - 1]);
        }

        //instantiate a new turing machine.
        TuringMachine t = new TuringMachine(tape);

        //perform turing machine operations.
        t.turingMachine();
    }
}