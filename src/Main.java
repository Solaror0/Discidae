/*
TITLE - DISCIDAE
Description - A choose your own adventure game. Please refer to the README file
Author - Jun Nur
Last Edited - 2024-04-09
*/

import java.util.*;
import java.io.*;

/**
 * The type Main.
 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    /**
     * The constant ANSI_RESET.
     */
//COLOUR VARS DECLARATION
    public static final String ANSI_RESET = "\u001B[0m";
    /**
     * The constant ANSI_YELLOW.
     */
    public static final String ANSI_YELLOW = "\u001B[33m";
    /**
     * The constant ANSI_PURPLE.
     */
    public static final String ANSI_PURPLE = "\u001B[35m";
    /**
     * The constant ANSI_BLUE.
     */
    public static final String ANSI_BLUE = "\u001B[34m";

    public static Boolean devMode = false;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        HashMap<String, Integer> inventory = new HashMap<>(); //item inventory of the user, also a convenient way to store decision count
        inventory.put("Decisions", 0);

        HashMap<String, Boolean> keyPoints = new HashMap<>(); //stores all the major events/booleans in one hashmap so the whole hashmap can be put into a method vs multiple variables
        keyPoints.put("devMode", false);
        ArrayList<String> decisions = new ArrayList<>(); //a stack might work too to see the last made decision(LIFO) --> but if i wanted to access decisions later


        //some boolean initialization
        keyPoints.put("cleoNecklace", false);
        keyPoints.put("holySword", false);
        keyPoints.put("altarKey", false);
        keyPoints.put("brandKey", false);
        keyPoints.put("floKey", false);
        keyPoints.put("doddsDead", false);


//        if (sc.nextLine().equals("Dev")) {
//            String[] devDecisions = ((sc.nextLine()).split(" "));
//            Collections.addAll(decisions, devDecisions);
//            devMode = true; //there is a very used method that needs to see devMode
//            // but its called in methods that don't need to pass keyPoints at ALL
//            keyPoints.put("devMode", true);
//        }
//UNCOMMENT THIS FOR DEV MODE ^^^

        printEngine("intro");
        beginningOne(decisions, keyPoints, inventory);

    }

    /**
     * Asks the user to press enter to continue.
     */
    public static void pressEnter() {

        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_PURPLE + "\n Press Enter to Continue" + ANSI_RESET);
        if (devMode){
            System.out.println("\n"); //skips past if dev mode is on
        } else{sc.nextLine();}
    }

    /**
     * Shows the deathscreen
     *
     * @param causeOfDeath the cause of death
     * @param turns        the turns
     * @param decisions    the decisions
     * @throws IOException the io exception
     */
    public static void deathScreen(String causeOfDeath, int turns, ArrayList<String> decisions) throws IOException {
        System.out.println("You Died. Cause of death: " + causeOfDeath + "." + "You made it " + turns + " turns.");
        System.out.println("Your decisions made were: " + decisions.toString());
        runCounter(decisions, causeOfDeath); //prints out death + shows decisions to player
    } public static void deathScreen(String causeOfDeath, int turns, ArrayList<String> decisions, String specialMessage) throws IOException {
        System.out.println("You Died. Cause of death: " + causeOfDeath + "." + "You made it " + turns + " turns.");
        System.out.println(specialMessage); //overloaded with special message
        System.out.println("Your decisions made were: " + decisions.toString());
        runCounter(decisions, causeOfDeath);
    }

    /**
     * Adds to the turn count to keep track.
     *
     * @param inventory the inventory Map
     */
    public static void turnIterator(HashMap<String, Integer> inventory) {
        inventory.put("Decisions", inventory.get("Decisions") + 1);
    } //increments to the number of decisions made by the player

    /**
     * Input taker string.
     *
     * @param validInputs The valid inputs taken
     * @return The input from the user
     */
    public static String inputTaker(String[] validInputs, HashMap<String, Integer> inventory) {
        Scanner sc = new Scanner(System.in);
        String input;
        do {

            System.out.println("Please enter a valid input." + Arrays.toString(validInputs));
            input = sc.nextLine(); //asks user for input and shows the valid inputs

            if (input.equalsIgnoreCase("inventory")){
                openInv(inventory); //opens inventory and comes back to loop if the user asks
            }

        } while (!(inList(input, validInputs)));
        return input.toUpperCase();

    }

    public static void openInv(HashMap<String, Integer> inventory){
        System.out.println(ANSI_BLUE + "\n---------------INVENTORY---------------" + ANSI_RESET);
        String[] keys = inventory.keySet().toArray(new String[0]); //converts keys into array

        for(int i =0; i<keys.length; i++){
            System.out.println("Item: " + keys[i] + "         Count: " + inventory.get(keys[i])); //iterates through each key and prints + accesses the values at the same time
        }
        System.out.println(ANSI_BLUE + "---------------INVENTORY---------------" + ANSI_RESET);
        pressEnter();
    }

    /**
     * Checks if users input is in the valid inputs list
     *
     * @param input the input
     * @param list  the list
     * @return the boolean
     */
    public static Boolean inList(String input, String[] list) {
        for (String element : list) { //goes through the valid input list and the input given until something is found, else return false
            if (element.equals(input.toLowerCase()) || element.equals(input.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for dev mode being active <br> Also checks if theres decisions left in the string given.
     *
     * @param validInputs the valid inputs
     * @param keyPoints   the key points
     * @param inventory   the inventory
     * @param decisions   the decisions
     * @return the string
     */
    public static String decisionModule(String[] validInputs, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory, ArrayList<String> decisions) {
        String decision;

       // System.out.println(inventory.get("Decisions") + " " + decisions.size());  FOR DEBUGGING

        if (keyPoints.get("devMode") && (decisions.size() > inventory.get("Decisions"))) { //checks if devmode is on, and if decisions are left in devmode. E.g if the game is on decision #4 but was only given "A A B" then it won't continue dev mode
            turnIterator(inventory); //adds to turn iterator like the game normally would.
            decision = decisions.get(inventory.get("Decisions") - 1); //and accesses index the game would normally be at
        } else {
            turnIterator(inventory); //otherwise, it normally runs where it increments decisions made and takes input
            decision = (inputTaker(validInputs,inventory)); //in normal cases the # of decisions made could just be taken with decisions.size(),
            // but it's incremented separately because we need to compare the .size() and actual turns completed like this
            decisions.add(decision);
        }

        return decision;
    }

    /**
     * Bloodthirsty ending print!
     *
     * @param decisions the decisions
     * @throws IOException the io exception
     */
    public static void bloodThirsty(ArrayList<String> decisions) throws IOException {

        pressEnter();
        System.out.println(ANSI_BLUE + "The holy sword delivered justice through you as a medium. Bloodthirsty Ending!" + ANSI_RESET);
        System.out.println("""
                  ____   _                    _   _______  _      _             _            ______             _  _              \s
                 |  _ \\ | |                  | | |__   __|| |    (_)           | |          |  ____|           | |(_)             \s
                 | |_) || |  ___    ___    __| |    | |   | |__   _  _ __  ___ | |_  _   _  | |__    _ __    __| | _  _ __    __ _\s
                 |  _ < | | / _ \\  / _ \\  / _` |    | |   | '_ \\ | || '__|/ __|| __|| | | | |  __|  | '_ \\  / _` || || '_ \\  / _` |
                 | |_) || || (_) || (_) || (_| |    | |   | | | || || |   \\__ \\| |_ | |_| | | |____ | | | || (_| || || | | || (_| |
                 |____/ |_| \\___/  \\___/  \\__,_|    |_|   |_| |_||_||_|   |___/ \\__| \\__, | |______||_| |_| \\__,_||_||_| |_| \\__, |
                                                                                      __/ |                                   __/ |
                                                                                     |___/                                   |___/ \
                """);

        System.out.println("Your decisions made were: " + decisions.toString());
        runCounter(decisions, "The holy sword delivered justice through you as a medium. Bloodthirsty Ending!");
    }

    /**
     * Leave school.
     *
     * @param decisions the decisions
     */
    public static void leaveSchool(ArrayList<String> decisions) throws IOException {
        pressEnter();

        System.out.println(ANSI_PURPLE + "You chose to leave the school, prioritizing your own safety. God knows what you left back in there." + ANSI_RESET);

        runCounter(decisions,"\"You chose to leave the school, prioritizing your own safety. God knows what you left back in there.\"");
        System.out.println("""
                  ______                     _                     ______             _  _              \s
                 |  ____|                   | |                   |  ____|           | |(_)             \s
                 | |__  _ __  ___   ___   __| |  ___   _ __ ___   | |__    _ __    __| | _  _ __    __ _\s
                 |  __|| '__|/ _ \\ / _ \\ / _` | / _ \\ | '_ ` _ \\  |  __|  | '_ \\  / _` || || '_ \\  / _` |
                 | |   | |  |  __/|  __/| (_| || (_) || | | | | | | |____ | | | || (_| || || | | || (_| |
                 |_|   |_|   \\___| \\___| \\__,_| \\___/ |_| |_| |_| |______||_| |_| \\__,_||_||_| |_| \\__, |
                                                                                                    __/ |
                                                                                                   |___/ \
                """);

        System.out.println("Your decisions made were: " + decisions.toString());
    }

    /**
     * Print engine, prints the file given.
     *
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public static void printEngine(String fileName) throws IOException {

        File file = new File("C:\\Users\\junnu\\IdeaProjects\\DiscidaeProcessing\\print\\" + fileName);
        Scanner printFile = new Scanner(file); //initializing file objects

        // System.out.println(fileName); FOR DEBUGGING
        System.out.println(ANSI_PURPLE + "~~~~~~~~~~~~===============================~~~~~~~~~~~~" + ANSI_RESET);

        while (printFile.hasNextLine()) {
            String line = printFile.nextLine(); //iterates through each line in the file

            switch (line) {
                case "pressEnter": //if the line is printEnter it runs the print enter function
                    pressEnter();
                    break;

                default: //adds different colour modifiers to the printed line depending on what each line contains.
                    if (line.contains("PURPLE")) {
                        line = line.replace("PURPLE", "");
                        line = ANSI_PURPLE + line;
                    } else if (line.contains("YELLOW")) {
                        line = line.replace("YELLOW", "");
                        line = ANSI_YELLOW + line;
                    } else if (line.contains("BLUE")) {
                        line = line.replace("BLUE", "");
                        line = ANSI_BLUE + line;
                    }

                    if (line.contains("RESET")) { //after every colour declaration there must be a "RESET" at some point so the colour goes back to default
                        line = line + ANSI_RESET;
                        line = line.replace("RESET", "");
                    }
                    System.out.println(line);

                    break;
            }
        }
        System.out.println(ANSI_PURPLE + "~~~~~~~~~~~~===============================~~~~~~~~~~~~" + ANSI_RESET);
    }

    /**
     * Run counter.
     *
     * @param decisions the decisions
     * @param ending    the ending
     * @throws IOException the io exception
     */
    public static void runCounter(ArrayList<String> decisions, String ending) throws IOException {
        //first opening runNum.txt file as READ to get the run number
        File temp = new File("runNum.txt"); //a file that only holds the most recent number
        Scanner runsRead = new Scanner(temp);
        int runNum = Integer.parseInt(runsRead.next()) + 1;
        //I could have done a thing where I read the entire runs.txt file to find the number,
        // and then reread it, but this was way less complicated because the first line of runNum will always be a number
        runsRead.close();

        PrintWriter runNumChanger = new PrintWriter("runNum.txt");
        runNumChanger.println(runNum); //updating runNum txt
        runNumChanger.close();

        FileWriter runFile = new FileWriter("runs.txt", true);
        PrintWriter runs = new PrintWriter(runFile); //opens runs.txt in append mode
        runs.println("\nRun #: " + runNum + "\nEnding: \"" + ending + "\"\n" + "Decisions: " + decisions + "\n"); //prints the log into it
        runs.close();
    }

    /**
     * Traitor ending.
     *
     * @param decisions the decisions
     * @throws IOException the io exception
     */
    public static void traitorEnding(ArrayList<String> decisions) throws IOException {
        printEngine("traitorEnding");
        runCounter(decisions, "You were killed by a traitor.");
        deathScreen("You were killed by a traitor. \n", decisions.size(), decisions);
        printEngine("traitorScreen");
    }

    /**
     * Kill stealer ending.
     *
     * @param decisions the decisions
     * @throws IOException the io exception
     */
    public static void killStealerEnding(ArrayList<String> decisions) throws IOException {
        printEngine("KilLStealerEnding");
        runCounter(decisions, "Is this enough?");
        deathScreen("Is this enough? \n", decisions.size(), decisions);
        printEngine("killStealerScreen");
    }

    /**
     * Incomplete traitor ending.
     *
     * @param decisions the decisions
     * @throws IOException the io exception
     */
    public static void incompleteTraitorEnding(ArrayList<String> decisions) throws IOException {
        printEngine("incompTraitorEnding");
        runCounter(decisions, "You were missing the key");
        deathScreen("You were missing the key", decisions.size(), decisions);
        printEngine("inCompTraitorScreen");
    }


    /**
     * Post traitor arc.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @throws IOException the io exception
     */
    public static void postTraitorArc(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {
        System.out.println("After a blur. It was over. Now, I had to make my way to the English.. classroom? Back to Flo. \n");
        String choice;

        if (keyPoints.get("altarKey")) {
            printEngine("altarKeyTraitor");
            choice = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);
            switch (choice) { //the user can choose to either go to altar orrr not
                case "A":
                    System.out.println(ANSI_PURPLE + "Altar Apple acquired!");
                    inventory.put("Altar Apple",1);

                    killStealerEnding(decisions);
                    break;
                case "B":
                    traitorEnding(decisions);
                    printEngine("ahmedTraitor");
                    break;
            }

        } else {
            printEngine("traitorChoice");
            choice = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);
            switch (choice) { //the user can choose to run or not
                case "A":
                    incompleteTraitorEnding(decisions);
                    break;
                case "B":
                    traitorEnding(decisions);
                    break;

            }
        }

    }

    /**
     * Traitor arc.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @throws IOException the io exception
     */
    public static void traitorArc(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {
        printEngine("traitorArc");
        String choice = decisionModule(new String[]{"A", "B", "C"}, keyPoints, inventory, decisions);
        switch (choice) {
            case "A":
                printEngine("cleoTraitor");
                break;
            case "B":
                printEngine("ahmedTraitor");
                break;
            case "C":
                printEngine("cameronTraitor");
                break;
        }
        postTraitorArc(decisions, keyPoints, inventory);


    }

    /**
     * Flo fight.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @throws IOException the io exception
     */
    public static void floFight(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {
        int myRoll, atkBoost, atkRoll, dodgeRoll = 5, floRoll, floAtk = 5;
        int floHealth = 25, myHealth = 25; //variable initialization

        if (keyPoints.get("holySword")) {
            printEngine("holySwordFlo");
            atkBoost = 2;
            atkRoll = 5;


        } else if (keyPoints.get("altarKey")) {
            printEngine("altarKeyFlo");
            atkBoost = 1;
            atkRoll = 4;

            System.out.println("Good luck." + ANSI_RESET);

        } else {
            System.out.println("I'm entering a fight with Flo. This isn't really happening right? I don't even think I know how to fight.");
            atkBoost = -1;
            atkRoll = 4;
        }
        //Depending on what the player has, their stats will be adjusted accordingly. Fists is by far the most difficult.

        String choice;
        Random rdm = new Random();
        int damage, holdRoll = atkRoll;

        while (myHealth > 0 && floHealth > 0) {

            if (keyPoints.get("holySword")) {
                System.out.println(ANSI_BLUE + "Your health is: " + myHealth + "\n Flo's health is: " + floHealth + "\n Make your next move.");
            } else if (keyPoints.get("altarKey")) {
                System.out.println("♋︎⬧︎♎︎hea♋︎⬧︎♎︎:" + myHealth);
            } else {
                if (myHealth > 18) {
                    System.out.println("My health.. I feel.. fine I guess");
                } else if (myHealth < 18 && myHealth > 10) {
                    System.out.println("My health.. I don't think it's going to be okay.");
                } else {
                    System.out.println("My health.. Damnit. God damnit. It hurts.");
                }
            } //the player's access to information also varies depending on what they have, a fist-only fight is super blind

            System.out.println(ANSI_PURPLE + "Make your decision. \n A. Attack" + " B. Wait" + ANSI_RESET); //attacking is a roll, waiting boosts next roll
            choice = inputTaker(new String[]{"A", "B"},inventory);

            switch (choice) {
                case "A":
                    myRoll = rdm.nextInt(1,atkRoll) + atkBoost;
                    atkRoll = holdRoll; //resets atkRoll to original value

                    System.out.println("\n I rush towards Flo and attack! \n");
                    //System.out.println(myRoll + " ATTACK " + atkRoll); FOR DEBUGGING

                    pressEnter();

                    if (myRoll < 2+atkBoost) { //If your attack roll is low enough, flo might attempt to dodge. This also means that she'll rarely dodge fists, since it's boost is -1.
                        System.out.println("Flo attempts to dodge ");
                        floRoll = rdm.nextInt(0,5);
                        if (floRoll > myRoll-atkBoost) { //subtracting 2 from myRoll to get the value without the atkBoost
                            System.out.println(" and she succeeds, taking no damage.");
                        } else {
                            System.out.println(" but it doesn't work, and she takes full damage." + ANSI_PURPLE + "\nFlo took " + myRoll + " damage!" + ANSI_RESET);
                            floHealth -= myRoll;
                        }
                    } else {
                        floRoll = rdm.nextInt(10) / 2;
                        damage = myRoll - floRoll; //blocking roll, Flo's atk boost doesn't apply here cause it turns out to be a lil unbalanced

                        System.out.println("Flo attempts to block ");
                        //System.out.println(floRoll + " BLOCK?"); FOR DEBUGGING

                        if (damage <= 0) {
                            damage = 1; //blocking cant reduce the damage to 0
                        }
                        floHealth -= damage;
                        System.out.println(" and she reduces my damage to " + damage);

                    }
                    break;

                case "B":
                    System.out.println("I stand back and look at Flo, trying to keep a calm mind. \n I'm looking for weaknesses, slip-ups, anything.");
                    System.out.println(ANSI_PURPLE + "Your next attack roll will be out of 12!" + "\n Dodging gets a +2." + ANSI_RESET);
                    atkRoll = 12; //boosts rolls to 12 & 7 respectively
                    dodgeRoll=7;
                    break;

            }
            if (floHealth < 0) {
                break;
            }

            System.out.println("After my move, its up to Flo now.");
            pressEnter();
            floRoll = rdm.nextInt(5);

            if (floRoll > 3) {
                System.out.println("Flo decides to wait and observe me. Her next attack will be more powerful.");
                floAtk = 12; //Flo doesnt get her dodge boosted for balance

            } else {
                System.out.println("Flo is attacking!");
                floRoll = rdm.nextInt(floAtk) + 2;
                floAtk = 5; //reset Flo's attack
                pressEnter();

                if (keyPoints.get("holySword")) { //different information based on what you have, fists gets no damage indicato
                    System.out.println(ANSI_BLUE + "Flo will be attacking you with " + floRoll + " damage!");
                } else if (keyPoints.get("altarKey")) {
                    System.out.println(ANSI_BLUE + "Flo wi attyou ll ackingama b︎th " + floRoll + " dge!");
                }

                System.out.println(ANSI_PURPLE + "Make a decision: \n A. Block \n B. Dodge" + ANSI_RESET);
                choice = inputTaker(new String[]{"A", "B"},inventory);

                switch (choice) {
                    case "A":
                        System.out.println("I decide to block, or attempt it at least. I end up blocking her attack to ");
                        myRoll = rdm.nextInt(holdRoll * 2) / 2 + atkBoost; //player gets the atkboost onto block, not good for fists
                        //System.out.println(myRoll); FOR DEBUGGING


                        damage = floRoll - myRoll;
                        if (damage <= 0) {
                            damage = 1;
                        } //cant get damage below 1
                        System.out.println(damage + " damage."); //player will get info after they have made a decision no matter what
                        myHealth -= damage;
                        break;

                    case "B":
                        System.out.println("I attempt to dodge ");
                        myRoll = rdm.nextInt(dodgeRoll);
                        dodgeRoll=5;//reset
                       // System.out.println(myRoll + " " + floRoll); FOR DEBUGGING

                        if (floRoll - 2 < myRoll) { //floroll-2 to account for her boost and only compare the roll itself
                            System.out.println(" and I succeed, taking no damage.");
                        } else {
                            System.out.println(" but it doesn't work, and I take full damage." + ANSI_PURPLE + "\n You took " + floRoll + " damage!" + ANSI_RESET);
                            myHealth -= floRoll;
                        }
                        break;
                }

            }



          //  System.out.println(myHealth + " " + floHealth); FOR DEBUGGING

        }
        pressEnter();
        if (floHealth <= 0) {
            System.out.println("""
                    Somehow. Somehow Flo lay in front of me, fading away into dust.\s
                     There were no last words, or even words during the fight.\s
                     The only sound in the unnaturally vacant hallway were my gasps for breath.""");

            if (!(keyPoints.get("holySword"))) {
                System.out.println(ANSI_PURPLE + "In recognition of your achievement without it, the Holy Sword has come to you." + ANSI_RESET);
                keyPoints.put("holySword", true);
            }

            keyPoints.put("floKey", true);

            pressEnter();
            System.out.println("What remained of Flo was a simple key, labeled #2. \n When I picked it up it.. felt like it was feeding me some information." + ANSI_PURPLE +
                    "\n You have acquired Flo's key! " + ANSI_RESET);
            pressEnter();


            if (keyPoints.get("brandKey") && keyPoints.get("doddsDead")) {
                bloodThirsty(decisions);
            } else if (keyPoints.get("brandKey")) {
                printEngine("brandKeyFloFight");

                String choose = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);

                switch (choose) {
                    case "A":
                        leaveSchool(decisions);
                        break;

                    case "B":
                        pressEnter();
                        doddsFight(decisions, keyPoints, inventory);
                        break;

                }
            } else if (keyPoints.get("doddsDead")) {
                printEngine("doddsDeadFloFight");

                brandFight(decisions, keyPoints, inventory);


            } else {
                printEngine("floKeyFloFight");

                String choose = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);
                switch (choose) {
                    case "A":
                        System.out.println("Almost like a robot, I'd make my way to Dodds clasroom. Sword in hand.");
                        doddsFight(decisions, keyPoints, inventory);
                        break;

                    case "B":
                        System.out.println("Almost like a robot, I'd make my way to Brand's office. Sword in hand.");
                        brandFight(decisions, keyPoints, inventory);
                        break;

                }
            }

        } else {
            if (keyPoints.get("floLike")) {
                printEngine("floLikeFloFight");
                String choose = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);
                switch (choose) {
                    case "A":
                        traitorArc(decisions, keyPoints, inventory);
                        break;
                    case "B":
                        deathScreen("You stayed true to your humanity.", decisions.size(), decisions);
                        break;
                }

            } else {
                deathScreen("It was over as soon as it started.", decisions.size(), decisions);
            }
        }
    }

    /**
     * Dodds fight.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @throws IOException the io exception
     */
    public static void doddsFight(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {
        pressEnter();
        System.out.println(ANSI_BLUE + "Dodd's fight format is the most simple, but also the most annoying. \n " +
                "Her mind attempts to take control of yours, if you win she perishes. \n To simplify the battle of the mind, the holy sword is using the format of riddles. \n" +
                "Solve her riddles and you win." +
                "\n All answers are one-word answers. You get three chances per riddle.");

        Scanner sc = new Scanner(System.in);
        String answer = null;
        pressEnter();
        String[] riddles = {"\n What gets bigger the more you take away?", "\n What has four fingers and a thumb, but isn’t alive?", "\nI’m tall when I’m young, and I’m short when I’m old. What am I?"};
        String[] answers = {"hole", "glove", "candle"};

        for (int j = 0; j < 3; j++) {
            pressEnter();
            System.out.println(ANSI_YELLOW + "Riddle number " + (j + 1) + " goes like this:" +
                    riddles[j]);
            for (int i = 0; i < 3; i++) {
                System.out.println("What is your " + (i + 1) + "st Answer?");
                answer = sc.nextLine();
                if ((answer).equalsIgnoreCase(answers[j])) {
                    System.out.println(ANSI_BLUE + "Correct. You have finished riddle number " + (j + 1) + ANSI_RESET);
                    break;
                }
            }
            if (!(answer).equalsIgnoreCase(answers[j])) {
                System.out.println(ANSI_YELLOW + "Goodbye!" + ANSI_RESET);
                deathScreen("You couldn't beat Dodd's riddles", decisions.size(), decisions);
                return;
            }
        }

        printEngine("doddsFightEnd");
        keyPoints.put("doddsDead",true);

        if (keyPoints.get("floKey") && keyPoints.get("brandKey")) { //both flo and brands got got, aka end
            bloodThirsty(decisions);
        }
        else if (keyPoints.get("floKey")) { //only flo got got, have to go brand

            printEngine("brandDoddsFight");

            brandFight(decisions, keyPoints, inventory);

        } else { //flo has not gotten got got, go get her?

            printEngine("floDoddsFight");

            fightIntro(keyPoints);
            floFight(decisions, keyPoints, inventory);

        }
    }

    /**
     * Dodds talk one.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @throws IOException the io exception
     */
    public static void doddsTalkOne(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {

        printEngine("doddsTalkOne");
        if (keyPoints.get("holySword")) {
            System.out.println(ANSI_RESET + "\n Dodd's would be eyeing me. Clearly looking for something. After a while she would give a satisfied nod\n. \n " + ANSI_YELLOW + "You're one of the kids we call.. a holy child? I guess we could call you that. \n" +
                    "The only ones with the power to stop what we want to do at this school. " + ANSI_RESET);
            //this isn't cringe because percy jackson wrote it more randomly than this at first and people ate it up (it is cringe)
            fightIntro(keyPoints);
            doddsFight(decisions, keyPoints, inventory);

        } else {
            if (keyPoints.get("cleoNecklace")) {
                System.out.println("You live!");
            } else {
                deathScreen("Dodds Killed You", decisions.size(), decisions);
            }
        }
    }

    /**
     * Post lunch.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @throws IOException the io exception
     */
    public static void postLunch(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {

        pressEnter();

        if (keyPoints.get("brandKey")) {
            printEngine("postLunchBrandFlo");

            String choice = inputTaker(new String[]{"A", "B"},inventory);
            if (choice.equals("A")) {
                System.out.println("Sorry, Flo");

                floFight(decisions, keyPoints, inventory);

            } else {
                System.out.println(ANSI_PURPLE + "Flo Apple acquired!");
                inventory.put("Flo Apple",1);

                System.out.println(ANSI_YELLOW + "\n You might have realized by now. You got Brand.. right? I can feel the stench radiating off of you. \n" +
                        " That guy was tough. Flo gave me a weak smile. I'm sorry about this, but if you wield that thing.." + ANSI_RESET);
                System.out.println("The Thing in question had already appeared in my hand. I found myself struggling against its will, as I felt control over my body slipping.");
                floFight(decisions, keyPoints, inventory);
            }


            //BRAND KEY STUFF AND ALSO FLO FUNCTION
        } else if (keyPoints.get("doddsDislike")) {
            doddsTalkOne(decisions, keyPoints, inventory); //this one is a method unlike the other ones because I will need to use that exact dialogue again


        } else {
            printEngine("postLunchFloTalk");


            if (keyPoints.get("holySword") || keyPoints.get("altarKey")) {
                fightIntro(keyPoints);
                floFight(decisions, keyPoints, inventory);
            } else {
                System.out.println("My inability to respond led way to anger though, and I struck out. I didn't know how to fight though, so it was more of a scratch than anything.");
                floFight(decisions, keyPoints, inventory);
            }

        }

    }

    /**
     * Brand fight.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @throws IOException the io exception
     */
    public static void brandFight(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {
        printEngine("brandfightOne");
        String move = decisionModule(new String[]{"A", "B", "C"}, keyPoints, inventory, decisions);
        switch (move) {
            case "A":
                deathScreen("Brand's desk killed you", decisions.size(), decisions, "Why would you do that?");
                break;

            case "B":
                printEngine("brandFightTwo");
                move = decisionModule(new String[]{"A", "B", "C"}, keyPoints, inventory, decisions);

                switch (move) {
                    case "A":
                        System.out.println("Again, I block. His flame clashes with the blade as if physical in nature. But then he grabs my hand holding the sword. \n" +
                                "Immediately I feel something wrong deep inside. It's the worst kind of gut feeling. The last thing I see is");
                        deathScreen("You became fuel", decisions.size(), decisions);
                        break;

                    case "B":
                        printEngine("brandFightThree");
                        deathScreen("You lost consciousness, and Brand doesn't take prisoners.", decisions.size(), decisions);
                        break;

                    case "C":
                        printEngine("brandFightFour");

                        move = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);

                        switch (move) {
                            case "A":
                                printEngine("brandFightFive");
                                deathScreen("You became fuel for the fire.", decisions.size(), decisions);

                                break;

                            case "B":
                                printEngine("brandFightSix");
                                keyPoints.put("brandKey", true);
                                inventory.put("Brand's Key", 1);

                                if (!(keyPoints.get("floKey"))) {
                                    postLunch(decisions, keyPoints, inventory);
                                } else {
                                    if (keyPoints.get("floKey") && keyPoints.get("doddsDead")) {
                                        bloodThirsty(decisions);
                                    } else if (keyPoints.get("floKey") && !(keyPoints.get("doddsDead"))) {

                                        printEngine("brandFightSeven");
                                        String choice = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);

                                        switch (choice) {
                                            case "A":
                                                leaveSchool(decisions);
                                                break;

                                            case "B":
                                                pressEnter();
                                                doddsFight(decisions, keyPoints, inventory);
                                                break;

                                        }

                                    }
                                }

                                break;
                        }
                        break;

                }

                break;
            case "C":
                printEngine("brandFightEight");
                move = decisionModule(new String[]{"A", "B", "C"}, keyPoints, inventory, decisions);
                switch (move) {
                    case "A":
                        System.out.println("Whatever was controlling my body didn't even let me shout. With a step forward I thrust the blade at Brand. \n " +
                                "The resounding clash of metal tells me it didn't hit.");
                        break;
                    case "B":
                        printEngine("brandFightNine");
                        break;

                    case "C":
                        System.out.println("Whatever was controlling my body didn't even let me shout. I stepped to the left, clenching the sword " +
                                "and drawing a line from Brand's hip to his shoulder");
                        System.out.println("He's almost too surprised at my speed to dodge, but he manages to react. \n The blade had met his hip and left a small gash.");
                        System.out.println(ANSI_BLUE + "Brand has lost 1 health!" + ANSI_RESET);
                        break;
                }

                printEngine("brandFightTen");
                move = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);

                switch (move) {
                    case "A":
                        System.out.println("I dodge to my right, and in doing so, allow the knife to plunge into my left eye.");

                        System.out.println(ANSI_PURPLE + "Brand Apple acquired!");
                        inventory.put("Brand Apple",1);

                        deathScreen("Knife, eye, brain!", decisions.size(), decisions, "He's attacking with his left? Dodging right means moving away from the right arm.");

                        break;
                    case "B":
                        printEngine("brandFightEleven");

                        keyPoints.put("brandKey", true);
                        inventory.put("Brand's Key", 1);
                        System.out.println(ANSI_YELLOW + "You have acquired: Brand's Key --> 1/2 KEY." + ANSI_RESET);

                        if (!(keyPoints.get("floKey"))) {
                            postLunch(decisions, keyPoints, inventory);
                        } else {
                            if (keyPoints.get("floKey") && keyPoints.get("doddsDead")) {
                                bloodThirsty(decisions);

                            } else if (keyPoints.get("floKey") && !(keyPoints.get("doddsDead"))) {
                                printEngine("brandFightSeven");
                                String choice = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);

                                switch (choice) {
                                    case "A":
                                        leaveSchool(decisions);
                                        break;

                                    case "B":
                                        pressEnter();
                                        doddsFight(decisions, keyPoints, inventory);
                                        break;

                                }

                            }
                        }

                        break;
                }

                break;

        }


    }

    /**
     * Fight intro.
     *
     * @param keyPoints the key points
     * @throws IOException the io exception
     */
    public static void fightIntro(HashMap<String, Boolean> keyPoints) throws IOException {

        if (keyPoints.get("holySword")) {
            printEngine("holySwordIntro.txt");
        } else if (keyPoints.get("altarKey")) {
            printEngine("altarKeyIntro.txt");
        }

    }

    /**
     * Brand meet.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @throws IOException the io exception
     */
    public static void brandMeet(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {

        if (keyPoints.get("cleoNecklace")) {
            postLunch(decisions, keyPoints, inventory);
            return;
        }
        printEngine("brandMeetOne.txt");

        if (!(keyPoints.get("altarKey") || keyPoints.get("holySword"))) {
            System.out.println(ANSI_YELLOW + "Enjoy your classes. See you around." + ANSI_RESET);
            postLunch(decisions, keyPoints, inventory);
            return;

        }
        printEngine("brandMeetTwo.txt");

        if (keyPoints.get("holySword")) {
            System.out.println("A sword would appear in my hands. It had a silver blade with a golden handle. Other than that there were no adornments."
                    + "Wait. What?" + ANSI_YELLOW + "\n I see you're used to using it. This should be fun?");
            System.out.println(ANSI_PURPLE + "Brand is attacking you." + ANSI_RESET);

            fightIntro(keyPoints);
            brandFight(decisions, keyPoints, inventory);


        } else if (keyPoints.get("altarKey")) {
            System.out.println("A large wrought iron key with a gold inlay appeared in my hands. What the hell?");
            System.out.println(ANSI_YELLOW + "It's interesting that you have that, how did you find it? I'll be taking it though. A shame you can't defend yourself with this one." + ANSI_RESET);
            deathScreen("You were killed by Brand.", decisions.size(), decisions, "I can't defend myself with the Altar Key.. but perhaps something else?");
        }

    }

    /**
     * Flo lunch.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @throws IOException the io exception
     */
    public static void floLunch(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {
        printEngine("floLunch");
        postLunch(decisions, keyPoints, inventory);
    }

    /**
     * Front foyer.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @param counter   the counter
     * @throws IOException the io exception
     */
    public static void frontFoyer(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory, int counter) throws IOException {
        Scanner sc = new Scanner(System.in);
        String answer = null;
        printEngine("frontFoyer");

        for (int i = 0; i < 3; i++) {
            System.out.println("What is your " + (i + 1) + "st Answer?");
            answer = sc.nextLine();
            if ((answer).equalsIgnoreCase("horseplay")) {
                System.out.println("...it's Horseplay.");
                System.out.println(ANSI_YELLOW + "Correct. You have acquired the Altar Key" + ANSI_RESET);
                inventory.put("Altar Key", 1);
                keyPoints.put("altarKey", true);
                break;
            }
        }
        if (!(answer).equalsIgnoreCase("horseplay")) {
            System.out.println(ANSI_YELLOW + "Goodbye!" + ANSI_RESET);
        }

        System.out.println(ANSI_PURPLE + "Press enter to continue." + ANSI_RESET);
        sc.nextLine();

        System.out.println("What did I come here for again? This is where I enter school. \n I check the time on my phone and it's halfway through lunch.");

        counter++;
        if (counter < 2) {
            System.out.println("Time for my next visit! Where should I go? \n" +
                    ANSI_PURPLE + "A. Cafeteria \n B. Basement");

            String decision = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);
            switch (decision) {
                case "A":
                    cafeteria(decisions, keyPoints, inventory, counter);
                    break;

                case "B":
                    basement(decisions, keyPoints, inventory, counter);
                    break;
            }
        } else {
            if (keyPoints.get("floLike")) {
                floLunch(decisions, keyPoints, inventory);
            } else {
                brandMeet(decisions, keyPoints, inventory);
            }
        }

    }

    /**
     * Basement.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @param counter   the counter
     * @throws IOException the io exception
     */
    public static void basement(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory, int counter) throws IOException {
        Scanner sc = new Scanner(System.in);
        printEngine("basement");

        String answer = null;
        for (int i = 0; i < 3; i++) {
            System.out.println("What is your " + (i + 1) + "st Answer?");
            answer = sc.nextLine();

            if ((answer).equalsIgnoreCase("keyboard")) {
                System.out.println("The hell? A keyboard?");
                System.out.println(ANSI_YELLOW + "Correct. You have acquired the Holy Sword." + ANSI_RESET);
                inventory.put("Holy Sword", 1);
                keyPoints.put("holySword", true);
                break;
            }
        }
        if (!(answer).equalsIgnoreCase("keyboard")) {
            System.out.println(ANSI_YELLOW + "Goodbye!" + ANSI_RESET);
        }

        System.out.println(ANSI_PURPLE + "Press enter to continue." + ANSI_RESET);
        sc.nextLine();

        System.out.println("I'm back at the entrance of the basement" +
                "\n Although you could've convinced me that I had never left... Did I?");

        System.out.println("I quickly checked the time on my phone and half of lunch had gone by ..?");
        counter++;

        if (counter < 2) {
            System.out.println("Time for my next visit! Where should I go? \n" +
                    ANSI_PURPLE + "A. Cafeteria \n B. Front Foyer");
            String decision = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);
            switch (decision) {
                case "A":
                    cafeteria(decisions, keyPoints, inventory, counter);
                    break;

                case "B":
                    frontFoyer(decisions, keyPoints, inventory, counter);
                    break;
            }
        } else {
            if (keyPoints.get("floLike")) {
                floLunch(decisions, keyPoints, inventory);
            } else {
                brandMeet(decisions, keyPoints, inventory);
            }
        }

    }

    /**
     * Cafeteria.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @param counter   the counter
     * @throws IOException the io exception
     */
    public static void cafeteria(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory, int counter) throws IOException {
        printEngine("cafeteria");
        inventory.put("apple", 1);
        counter++;
        if (counter < 2) {
            System.out.println("Time for my next visit! Where should I go? \n" +
                    ANSI_PURPLE + "A. Basement \n B. Front Foyer");
            String decision = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);
            switch (decision) {
                case "A":
                    basement(decisions, keyPoints, inventory, counter);
                    break;

                case "B":
                    frontFoyer(decisions, keyPoints, inventory, counter);
                    break;
            }

        } else {
            if (keyPoints.get("floLike")) {
                floLunch(decisions, keyPoints, inventory);
            } else {
                brandMeet(decisions, keyPoints, inventory);
            }
        }


    }

    /**
     * Lunch.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @throws IOException the io exception
     */
    public static void lunch(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {

        int counter;

        System.out.println("The periods pass by until lunch.");

        if (keyPoints.get("floLike")) {
            System.out.println("I'm meeting Flo at lunch, so I'll only have time to visit 1 place");
            counter = 1;
        } else {
            System.out.println("I told Flo I wouldn't meet with her, which means I have time to visit two places.");
            counter = 0;
        }

        System.out.println(ANSI_PURPLE + "A. Cafeteria \n B. Basement \n C. Front Foyer " + ANSI_RESET);
        String decision = decisionModule(new String[]{"A", "B", "C"}, keyPoints, inventory, decisions);


        switch (decision) {

            case "A":
                cafeteria(decisions, keyPoints, inventory, counter);
                break;

            case "B":
                System.out.println("Basement");
                basement(decisions, keyPoints, inventory, counter);
                System.out.println(ANSI_PURPLE + "Sword Apple acquired!");
                inventory.put("Sword Apple",1);
                break;

            case "C":
                frontFoyer(decisions, keyPoints, inventory, counter);
                break;

        }
    }

    /**
     * Flo class talk.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @throws IOException the io exception
     */
    public static void floClassTalk(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {
        printEngine("floClassTalk");


        String meetFlo = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);

        switch (meetFlo) {
            case "A":
                System.out.println(ANSI_PURPLE+ "You'll meet Flo in the second of lunch. Flo is happy with you." + ANSI_RESET);
                keyPoints.put("floLike", true);
                break;

            case "B":
                System.out.println(ANSI_PURPLE + "You wont meet Flo at lunch. Flo is not happy with you." + ANSI_RESET);
                keyPoints.put("floLike", false);
                break;
        }
        System.out.println("In any case, she stopped paying attention in class and hopped on her phone. Something about managing the school's confession account.");

        lunch(decisions, keyPoints, inventory);
    }

    /**
     * Dodds convo like.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @throws IOException the io exception
     */
    public static void doddsConvoLike(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {


        printEngine("doddsConvoLike");


        String input = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);
        switch (input) {
            case "A":
                System.out.println(ANSI_YELLOW + "\" That's great to hear! You can go take your seat now! But before you go take one of these." + ANSI_RESET);
                System.out.println(ANSI_PURPLE + "Dodd's Apple acquired from Mrs. Dodds" + ANSI_RESET);
                inventory.put("Dodd's Apple", 1);
                System.out.println("As always, conversation with her feels stilted and unnatural. Anyways, I take my prize candy and go to my seat beside Flo.");
                break;

            case "B":
                System.out.println(ANSI_YELLOW + "\" You're right, I'll never understand how students come in late everyday. You can go take your seat now \"" + ANSI_RESET);
                System.out.println("As always, conversation with her feels stilted and unnatural. Anyways, I go to my seat beside Flo.");
                break;
        }


        floClassTalk(decisions, keyPoints, inventory);
    }

    /**
     * Dodds class one.
     *
     * @param decisions the decisions
     * @param keyPoints the key points
     * @param inventory the inventory
     * @throws IOException the io exception
     */
    public static void doddsClassOne(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {

        if (keyPoints.get("doddsDislike")) {

            System.out.println("Dodds scolds me.. or screeches at me." + ANSI_YELLOW + " \"Late to class as always! Have you ever been on time? \n Take your seat.\"" + ANSI_RESET);
            pressEnter();
            floClassTalk(decisions, keyPoints, inventory);

        } else { //WHAT HAPPENS WHEN DODDS DOESNT DISLIKE YOU
            System.out.println(ANSI_RESET);
            printEngine("doddsConvoPieceTwo");

            String talkDecision;

            talkDecision = decisionModule(new String[]{"A", "B"}, keyPoints, inventory, decisions);

            switch ((talkDecision)) {
                case "A":
                    doddsConvoLike(decisions, keyPoints, inventory);
                    break;
                case "B":
                    System.out.println(2);
                    floClassTalk(decisions, keyPoints, inventory);
                    break;//FLO TALK METHOD
            }


        }

    }


    /**
     * The beginning function that calls the first other functions
     * @param decisions The arraylist containing all decisions
     * @param keyPoints The hashmap containing all significant game-altering booleans
     * @param inventory The hashmap containing all inventory items as well as a decision count alternate to size
     */
    public static void beginningOne(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Another day of school. Same time, same place. As I enter the front foyer I see my old science teacher. He greets me by my name." +
                "\n What do I hear?");

        String name = sc.nextLine();
        System.out.println(ANSI_YELLOW + "Good morning " + name + "!" + ANSI_RESET + " He was already down the hallway by the end of his sentence, late to his class once again. ");
        System.out.println("My attention back to the foyer, I had three directions: the left hallway which led to my first period \n, right hallway, or exiting the school entirely. \n" + ANSI_PURPLE +
                "\n A. left hallway \n B. right hallway \n C. exit the school \n Enter letter A B or C" + ANSI_RESET);

        String choice = decisionModule(new String[]{"A", "B", "C"},keyPoints,inventory,decisions);

        switch (choice) {
            case "A": //left hallway
                keyPoints.put("doddsDislike", false);

                doddsClassOne(decisions, keyPoints, inventory);
                break;

            case "B":  //right hallway
                System.out.println(ANSI_PURPLE + "You were late to class as a result. Dodds is not happy with you." + ANSI_RESET);
                keyPoints.put("doddsDislike", true);
                doddsClassOne(decisions, keyPoints, inventory);

                break;

            case "C": //exit
                deathScreen("-", decisions.size(), decisions);
                break;
        }

    }




}