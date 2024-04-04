/*
TITLE - DISCIDAE
Description -
Author - Jun Nur
Last Edited -
*/

import java.util.*;
import java.io.*;

import java.util.regex.*;

import processing.core.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends PApplet {
    //COLOUR VARS DECLARATION
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static String bgFile = "blankPic.png";

    //public static String imageName = "blankPic.png";
    //COLOUR VARS DECLARATION


    @Override public void settings(){
        size(600,600);
    }
    @Override public void setup(){
        //Variables Setup

       // PImage img = loadImage(bgFile);
       // image(img,0,0);


        size(600,600);
        background(32);

    ;


    }

  public void draw(){
      PImage img = loadImage(bgFile);
      image(img,0,0);


    }

    public static void main(String[] args) {
        PApplet.main("Main");
        Scanner sc = new Scanner(System.in);


        HashMap<String,Integer> inventory = new HashMap<String,Integer>(); //item inventory of the user
        inventory.put("Decisions",0);
        HashMap<String,Boolean> keyPoints = new HashMap<String,Boolean>(); //stores all the major events/booleans in one hashmap so the whole hashmap can be put into a method vs multiple variables
        keyPoints.put("devMode",false);
        ArrayList<String> decisions = new ArrayList<>(); //a stack might work too to see the last made decision(LIFO) --> but if i wanted to access decisions later

        String name = null;


        //some boolean initialization
        keyPoints.put("cleoNecklace",false);
        keyPoints.put("holySword",false);
        keyPoints.put("altarKey",false);
        keyPoints.put("brandKey",false);
        keyPoints.put("floKey",false);
        keyPoints.put("doddsDead",false);


        if(sc.nextLine().equals("Dev")){
            String[]devDecisions = ((sc.nextLine()).split(" "));
            Collections.addAll(decisions,devDecisions);
            keyPoints.put("devMode",true);
        }


        name = beginningOne();
        beginningTwo(decisions, keyPoints, inventory);
        hallwayThree(decisions, keyPoints, inventory);


    }

    public static void imageChanger(String fileName){
        bgFile = fileName + ".png";

    }

    public static void pressEnter(){
        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_PURPLE + "\n Press Enter to Continue" + ANSI_RESET);
        sc.nextLine();
    }
    public static void deathScreen(String causeOfDeath, int turns, ArrayList<String> decisions) {
        System.out.println("You Died. Cause of death: " + causeOfDeath + "." + "You made it " + turns  + " turns.");
        System.out.println("Your decisions made were: " + decisions.toString());
       // runCounter(decisions,causeOfDeath);
    } public static void deathScreen(String causeOfDeath, int turns, ArrayList<String> decisions, String specialMessage) {
        System.out.println("You Died. Cause of death: " + causeOfDeath + "." + "You made it " + turns  + " turns.");
        System.out.println("Your decisions made were: " + decisions.toString());
     //   runCounter(decisions,causeOfDeath);
    }
    public static void turnIterator(HashMap<String,Integer>inventory){
        inventory.put("Decisions",inventory.get("Decisions")+1);
    }


    public static void bloodThirsty(ArrayList<String> decisions){

        pressEnter();

        System.out.println(ANSI_BLUE + "The holy sword delivered justice through you as a medium. Bloodthirsty Ending!" + ANSI_RESET);

        System.out.println("  ____   _                    _   _______  _      _             _            ______             _  _               \n" +
                " |  _ \\ | |                  | | |__   __|| |    (_)           | |          |  ____|           | |(_)              \n" +
                " | |_) || |  ___    ___    __| |    | |   | |__   _  _ __  ___ | |_  _   _  | |__    _ __    __| | _  _ __    __ _ \n" +
                " |  _ < | | / _ \\  / _ \\  / _` |    | |   | '_ \\ | || '__|/ __|| __|| | | | |  __|  | '_ \\  / _` || || '_ \\  / _` |\n" +
                " | |_) || || (_) || (_) || (_| |    | |   | | | || || |   \\__ \\| |_ | |_| | | |____ | | | || (_| || || | | || (_| |\n" +
                " |____/ |_| \\___/  \\___/  \\__,_|    |_|   |_| |_||_||_|   |___/ \\__| \\__, | |______||_| |_| \\__,_||_||_| |_| \\__, |\n" +
                "                                                                      __/ |                                   __/ |\n" +
                "                                                                     |___/                                   |___/ ");

        System.out.println("Your decisions made were: " + decisions.toString());
        //runCounter(decisions,"The holy sword delivered justice through you as a medium. Bloodthirsty Ending!");
    }
    public static void leaveSchool(ArrayList<String> decisions){
        pressEnter();

        System.out.println(ANSI_PURPLE + "You chose to leave the school, prioritizing your own safety. God knows what you left back in there." + ANSI_RESET);

        System.out.println("  ______                     _                     ______             _  _               \n" +
                " |  ____|                   | |                   |  ____|           | |(_)              \n" +
                " | |__  _ __  ___   ___   __| |  ___   _ __ ___   | |__    _ __    __| | _  _ __    __ _ \n" +
                " |  __|| '__|/ _ \\ / _ \\ / _` | / _ \\ | '_ ` _ \\  |  __|  | '_ \\  / _` || || '_ \\  / _` |\n" +
                " | |   | |  |  __/|  __/| (_| || (_) || | | | | | | |____ | | | || (_| || || | | || (_| |\n" +
                " |_|   |_|   \\___| \\___| \\__,_| \\___/ |_| |_| |_| |______||_| |_| \\__,_||_||_| |_| \\__, |\n" +
                "                                                                                    __/ |\n" +
                "                                                                                   |___/ ");

        System.out.println("Your decisions made were: " + decisions.toString());
    }

    public static void runCounter(ArrayList<String> decisions,String ending) throws IOException {
        //first opening the file as READ to get the run number

        File temp = new File("runs.txt");
        Scanner runsRead = new Scanner(temp);
        String runNum = null;

        while (runsRead.hasNext()){
            String next = runsRead.next();
            if("0123456789".contains(next)){
                runNum = next; //only need the most recent one so if it updates everytime theres a number that works
            }
        }
        runsRead.close();

        FileWriter runFile = new FileWriter("runs.txt");
        PrintWriter runs = new PrintWriter(runFile);
        runs.println("Run #: " + runNum + "\n Ending: " + ending + "\n" + "\n Decisions: " + decisions + "\n");
        runs.close();


    }

    public static void floFight(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory){
        int myRoll = 0, atkBoost = 0, atkRoll = 0, dodgeBoost =0, floRoll = 0, floAtk = 5;
        int floHealth = 25, myHealth = 25;

        if (keyPoints.get("holySword")){
            System.out.println(ANSI_BLUE + "You are entering battle. This battle is best presented in turn-based combat. " +
                    "You will have two options before Flo attacks, and two options after she attacks." +
                    "\n Attacking rolls out of 5, the holy sword gives you a +2 bonus. \n Waiting makes your next attack roll out of 12" +
                    "\n Blocking rolls out of 10, you divide that by 2 and add the holy sword bonus. Whatever you get is subtracted from Flo's attack. \n" +
                    "Dodging rolls out of 5. If it's higher than Flo's roll, you dodge. If not, you get hit with full force.");

            pressEnter();
            System.out.println("Good luck." + ANSI_RESET);
            atkBoost = 2;
            atkRoll = 5;


        } else if (keyPoints.get("altarKey")){
            System.out.println(ANSI_BLUE + "You are entering battle. This battle is best presented in ◻\uFE0E♓\uFE0E◻\uFE0E□\uFE0E  combat. " +
                    "You wilions beforel have two opt Flo attacks, and□\uFE0E⬧\uFE0E♎\uFE0E♋\uFE0Eer she attacks." +
                    "\n Attacking rolls rolls rolls rolls the of no bonus. \n Wai-aw wait □\uFE0E⬧\uFE0E♎\uFE0E♋\uFE0E out 10" +
                    "\n Blo of 10cking rolls out, you divide t□\uFE0E⬧\uFE0E♎\uFE0E♋\uFE0E andfrom Flo's attack. \n t is subtWhatever you geracted " +
                    "Dodging rolls ou□\uFE0E⬧\uFE0E♎\uFE0E♋\uFE0E If it's higher than ◆\uFE0E♒\uFE0E◆\uFE0E♒\uFE0E◆\uFE0E♒\uFE0Enot, you get hi full force.t with");

            atkBoost = 1;
            atkRoll = 4;
            pressEnter();
            System.out.println("Good luck." + ANSI_RESET);

        } else{
            System.out.println("I'm entering a fight with Flo. This isn't really happening right? I don't even think I know how to fight.");
            atkBoost = -1;
            atkRoll = 4;
        }

        String choice = null;
        Random rdm = new Random();
        int damage = 0, holdRoll = atkRoll;

        while(myHealth>0 && floHealth>0){

            if(keyPoints.get("holySword")){
                System.out.println(ANSI_BLUE + "Your health is: "  + myHealth + "\n Flo's health is: " + floHealth + "\n Make your next move.");
            } else if(keyPoints.get("altarKey")){
                System.out.println( "♋\uFE0E⬧\uFE0E♎\uFE0Ehea♋\uFE0E⬧\uFE0E♎\uFE0E:" + myHealth);
            } else{
                if(myHealth>18){
                    System.out.println("I feel.. fine I guess");
                } else if (myHealth<18 && myHealth>10){
                    System.out.println("I don't think it's going to be okay.");}
                else{
                    System.out.println("Damnit. God damnit. It hurts.");
                }
            }

            System.out.println(ANSI_PURPLE + "Make your decision. \n A. Attack" + " B. Wait" + ANSI_RESET);
            choice = inputTaker(new String[]{"A","B"});
            switch(choice){
                case "A":
                    System.out.println("\n I rush towards Flo and attack! \n");
                    myRoll = rdm.nextInt(atkRoll) + atkBoost;

                    System.out.println(myRoll + " ATTACK " + atkRoll);


                    atkRoll = holdRoll;
                    pressEnter();

                    if(myRoll<2){
                        System.out.println("Flo attempts to dodge ");
                        floRoll = rdm.nextInt(5);
                        if(floRoll > myRoll){
                            System.out.println(" and she succeeds, taking no damage.");
                        } else{
                            System.out.println(" but it doesn't work, and she takes full damage." + ANSI_PURPLE + "\nFlo took " + myRoll + " damage!"+ ANSI_RESET);
                            floHealth -= myRoll;
                        }
                    } else{
                        System.out.println("Flo attempts to block ");
                        floRoll = rdm.nextInt(10)/2;

                        System.out.println(floRoll + " BLOCK?");

                        damage = myRoll-floRoll; if(damage<=0){damage = 1;}
                        System.out.println(" and she reduces my damage to " + damage);
                        floHealth -= damage;
                    }
                    break;

                case "B":
                    System.out.println("I stand back and look at Flo, trying to keep a calm mind. \n I'm looking for weaknesses, slip-ups, anything.");
                    System.out.println(ANSI_PURPLE + "Your next roll (any) will be out of 12!" + ANSI_RESET);
                    atkRoll = 12;
                    break;

            }
            if(floHealth<0){break;}

            System.out.println("After my move, its up to Flo now.");
            pressEnter();
            floRoll = rdm.nextInt(5);

            if (floRoll > 3){
                System.out.println("Flo decides to wait and observe me. Her next attack will be more powerful.");
                floAtk = 12;
            } else{
                System.out.println("Flo is attacking!");
                floRoll = rdm.nextInt(floAtk) + 2;
                floAtk = 5;


                if (keyPoints.get("holySword")){
                    System.out.println(ANSI_BLUE + "Flo will be attacking you with " + floRoll + " damage!");
                } else if (keyPoints.get("altarKey")){
                    System.out.println(ANSI_BLUE + "Flo wi attyou ll ackingama b♏\uFE0E♏\uFE0Eth " + floRoll + " dge!");
                }


            }
            pressEnter();
            System.out.println(ANSI_PURPLE + "Make a decision: \n A. Block \n B. Dodge" + ANSI_RESET);
            choice = inputTaker(new String[]{"A","B"});
            switch(choice){
                case "A":
                    System.out.println("I decide to block, or attempt it at least. I end up blocking her attack to ");
                    myRoll = rdm.nextInt(atkRoll*2)/2 + atkBoost;


                    System.out.println(myRoll);


                    damage = floRoll - myRoll; if(damage<=0){damage = 1;}
                    System.out.println(damage + " damage.");
                    myHealth -= damage;
                    break;

                case "B":
                    System.out.println("I attempt to dodge ");
                    myRoll = rdm.nextInt(atkRoll);

                    System.out.println(myRoll + " " + floRoll);

                    if(floRoll-2 < myRoll){
                        System.out.println(" and I succeed, taking no damage.");
                    } else{
                        System.out.println(" but it doesn't work, and I take full damage." + ANSI_PURPLE + "\n You took " + floRoll + " damage!"+ ANSI_RESET);
                        myHealth -= floRoll;
                    }
                    break;
            }
            System.out.println(myHealth + " " + floHealth);

        }
        if(floHealth <= 0){
            pressEnter();
            System.out.println("Somehow. Somehow Flo lay in front of me, fading away into dust. \n There were no last words, or even words during the fight. " +
                    "\n The only sound in the unnaturally vacant hallway were my gasps for breath.");

            if(!(keyPoints.get("holySword"))){
                System.out.println(ANSI_PURPLE + "In recognition of your achievement without it, the Holy Sword has come to you." + ANSI_RESET);
                keyPoints.put("holySword",true);
            }

            keyPoints.put("floKey",true);

            pressEnter();
            System.out.println("What remained of Flo was a simple key, labeled #2. \n When I picked it up it.. felt like it was feeding me some information." + ANSI_PURPLE +
                    "\n You have acquired Flo's key! " + ANSI_RESET);
            pressEnter();


            if (keyPoints.get("brandKey") && keyPoints.get("doddsDead")){
                bloodThirsty(decisions);

            } else if(keyPoints.get("brandKey")){
                System.out.println(ANSI_PURPLE + "You have acquired both keys." + ANSI_RESET);
                pressEnter();
                System.out.println("After studying them for a while, having both keys to the barrier together told me a lot.\n");
                pressEnter();
                System.out.println("1. Dodds, Flo, and Brand were all malicious spirits working together. Basically, demons?" +
                        "\n 2. The school was enclosed via spicy demon magic, no exit allowed under threat of death. " +
                        "\n 3. Their goal was to eventually consume the whole school via ritual. \n For this ritual they needed 3 kids like me. So they were waiting." +
                        "\n 4. Yes, Dodds as in my teacher Dodds.");

                System.out.println(ANSI_PURPLE + "You can exit the barrier and leave the school with the two keys, or you can choose to eliminate Dodds. \n" + "\n Please Enter. \n A. Exit the School \n B. Eliminate Dodds.");
                String choose = decisionModule(new String[]{"A", "B"},keyPoints,inventory,decisions);

                switch (choose){
                    case "A":
                        leaveSchool(decisions);
                        break;

                    case "B":
                        pressEnter();
                        doddsFight(decisions,keyPoints,inventory);
                        break;

                }
                ;
            } else if(keyPoints.get("doddsDead")){
                System.out.println("After some study of both the key's information and the holy sword I had learned a few things.");
                pressEnter();
                System.out.println("1. Dodds and Flo were both malicious spirits working together. Basically, demons?" +
                        "\n 2. The school is enclosed via spicy demon magic, no exit allowed under threat of death. To unlock it, I'll need the other key. \n But only i can leave. " +
                        "\n 3. Their goal is to do something with whoever is inside the school." +
                        "\n 4. There's one more, my principal? " +
                        "\n 5. For now(?) The holy sword would help me complete whatever was needed. As in, by controlling my body mentally. \n I wouldn't even feel the need to protest. \n " +
                        "In fact, I was in front of Brand's office right now.");

                System.out.println(ANSI_BLUE + "Flo and Dodds are both dealt with. Onto the next?" + ANSI_RESET);
                pressEnter();
                System.out.println(ANSI_BLUE + "You make your way to the office area. The next culprit is Principal Brand \n " +
                        "\n Principal Brand was a tall man, with the demeanour of being overly competent but not haughty about it. \n He was generally personable, although your interaction with him may not reflect that this time. \n" +
                        "As soon as he saw you he squinted at you for half a second. \n\n Brand speaks familiarly  " + ANSI_YELLOW + "Not in class? Is everything okay?" + ANSI_RESET);
                pressEnter();
                System.out.println(ANSI_BLUE + "You affirm so, and while doing so Brand has noticed something about you. Probably the stench of Dodds and Flo" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "He invites you into his office regardless and you follow, before he stands at his desk." + ANSI_RESET);
                brandFight(decisions,keyPoints,inventory);


            } else{
                System.out.println("After some study of both the key's information and the holy sword I had learned a few things.");
                pressEnter();
                System.out.println("1. Flo was a both malicious spirit working together with two other. Basically, demons?" +
                        "\n 2. The school is enclosed via spicy demon magic, no exit allowed under threat of death. To unlock it, I'll need the other key. \n But only i can leave. " +
                        "\n 3. Their goal is to do something with whoever is inside the school." +
                        "\n 4. There's two more. Mrs. Dodds and Principal Brand.. my english teacher and principal.." +
                        "\n 5. For now(?) The holy sword would help me complete whatever was needed. As in, by controlling my body mentally. \n I wouldn't even feel the need to protest. \n "
                        );

                System.out.println(ANSI_BLUE + "Choose: fight Brand or Dodds. There is no other option." + "\n A. Fight Dodds \n B. Fight Brand" + ANSI_RESET);
                String choose = decisionModule(new String[]{"A","B"},keyPoints,inventory,decisions);
                switch(choose){
                    case "A":
                        System.out.println("Almost like a robot, I'd make my way to Dodds clasroom. Sword in hand.");
                        doddsFight(decisions,keyPoints,inventory);
                        break;

                    case "B":
                        System.out.println("Almost like a robot, I'd make my way to Brand's office. Sword in hand.");
                        brandFight(decisions,keyPoints,inventory);
                        break;

                }
            }


        } else {
            pressEnter();
            if(keyPoints.get("floLike")){
                System.out.println("I wasn't dead..?");
            } else{
            deathScreen("It was over as soon as it started.",decisions.size(),decisions);}
        }


    }
    public static void doddsFight(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory){
        pressEnter();
        System.out.println(ANSI_BLUE + "Dodd's fight format is the most simple, but also the most annoying. \n " +
                "Her mind attempts to take control of yours, if you win she perishes. \n To simplify the battle of the mind, the holy sword is using the format of riddles. \n" +
                "Solve her riddles and you win." +
                "\n All answers are one-word answers. You get three chances per riddle.");

        Scanner sc = new Scanner(System.in);
        String answer = null;
        pressEnter();
        String [] riddles = {"\n What gets bigger the more you take away?","\n What has four fingers and a thumb, but isn’t alive?","\nI’m tall when I’m young, and I’m short when I’m old. What am I?"};
        String [] answers = {"hole","glove","candle"};

        for(int j = 0; j<3;j++) {
            pressEnter();
            System.out.println(ANSI_YELLOW + "Riddle number" + (j+1) + " goes like this:" +
                    riddles[j]);
            for (int i = 0; i < 3; i++) {
                System.out.println("What is your " + (i + 1) + "st Answer?");
                answer = sc.nextLine();
                if ((answer).equalsIgnoreCase(answers[j])) {
                    System.out.println(ANSI_BLUE+ "Correct. You have finished riddle number " + (j+1) + ANSI_RESET);
                    break;
                }
            }
            if (!(answer).equalsIgnoreCase( answers[j])) {
                System.out.println(ANSI_YELLOW + "Goodbye!" + ANSI_RESET);
                deathScreen("You couldn't beat Dodd's riddles",decisions.size(),decisions);
                return;
            }
        }

        pressEnter();
        System.out.println("\n It felt like I had woken up from a deep sleep, and in front of me was Mrs. Dodds fading into dust. \n" +
                " Honestly speaking, the riddles were pretty damn easy. But I could also tell that the holy sword was keeping me calm. \n" +
                " It had taken control of my body for me, not allowing me to freak.. which I should be doing.");

        if(keyPoints.get("floKey") && keyPoints.get("brandKey")){ //both flo and brands got got, aka end
            bloodThirsty(decisions);
        } if(keyPoints.get("floKey")) { //only flo got got, have to go brand

            pressEnter();
            System.out.println(ANSI_BLUE + "Flo and Dodds are both dealt with. Onto the next?" + ANSI_RESET);
            pressEnter();
            System.out.println(ANSI_BLUE + "You make your way to the office area. The next culprit is Principal Brand \n " +
                    "\n Principal Brand was a tall man, with the demeanour of being overly competent but not haughty about it. \n He was generally personable, although your interaction with him may not reflect that this time. \n" +
                    "As soon as he saw you he squinted at you for half a second. \n\n Brand speaks familiarly  " + ANSI_YELLOW + "Not in class? Is everything okay?" + ANSI_RESET);
            pressEnter();
            System.out.println(ANSI_BLUE + "You affirm so, and while doing so Brand has noticed something about you. Probably the stench of Dodds and Flo" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "He invites you into his office regardless and you follow, before he stands at his desk." + ANSI_RESET);
            brandFight(decisions,keyPoints,inventory);

        } else { //flo has not gotten got got, go get her?
            pressEnter();

            System.out.println("Hmm, should I even go to my next class? I mean, there's kind of a bigger problem right now. My teacher just tried to kill me? ");
            pressEnter();
            System.out.println("I thought about going to the office and reporting it.. but who would believe me? \n At least It was a blessing that my thoughts were kept calm by the holy sword.");
            System.out.println("As I roamed the hallways I see Flo come out of a corner, she looks at me with a bit of surprise in her eyes. ");

            pressEnter();

            System.out.println(ANSI_YELLOW + "You're not in class either?" + ANSI_RESET + " She'd grin" + ANSI_YELLOW + "\n" +
                    "Well, I needed to talk to you anyway. You didn't meet me at lunch.");

            pressEnter();
            System.out.println("I didn't reply, too focused on something else. \n" + ANSI_YELLOW + "You know, you seem different today. Smell different? Like how you said I smelt like perfume. \n" + ANSI_RESET);
            pressEnter();
            System.out.println("Her speech was becoming a little more erratic, almost stilted.");
            pressEnter();
            System.out.println(ANSI_YELLOW + "Have you ever heard of Malicious Spirits?" + ANSI_RESET + "\n I shook my head." + ANSI_YELLOW + "\n" +
                    "They're these grotesque little things that take human form. They have to consume mass quantities of souls once in a while to survive." + ANSI_RESET);

            pressEnter();
            System.out.println(ANSI_YELLOW + "You already realized didn't you? You got Dodds" + ANSI_RESET + "" +
                    "\n The issue was.. I had realized nothing. First my teacher turns into a demon and attacks me. \n Next, my best friend.. or something like that, admits to knowing about it?"
                    );
            fightIntro(keyPoints);
            floFight(decisions,keyPoints,inventory);

        }






    }
    public static void doddsTalkOne(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory){

        System.out.println("I was roaming the hallways for a little before going to my next class. No need to be so early after all. And I had managed to be near my English classroom. \n" +
                "Of course, Ms. Dodds doesn't like me, she made that clear enough. So I try to walk especially fast by..." +
                ANSI_YELLOW + "HEY!!" + ANSI_RESET + "It was too late.");
        pressEnter();
        System.out.println(ANSI_YELLOW + "Come here! Are you being late to yet another class?" + ANSI_RESET  + "\n" +
                " Immediately I had been dragged into her classroom. Great scolding, but wouldn't this make me even more late?");
        pressEnter();
        System.out.println("\" I was heading there Ms. Dodds I promise, I was just a little delayed from lunch. \"");
        pressEnter();
        System.out.println(ANSI_YELLOW + "I swear I will get you out of this school. Do you understand me?" + ANSI_RESET + "\n As she spoke her harsh perfume sent became unbearably strong, forcing me to scrunch my nose for just a second. \n" +
                "\n It wouldn't go unnoticed by her though. Causing her to raise an eyebrow." + ANSI_RESET);
        pressEnter();
        System.out.println(ANSI_YELLOW + "Oh? You smell that don't you?" + ANSI_RESET + "\n \"Huh?\" I'd ask, her demeanour had changed so suddenly." +
                "\n" + ANSI_YELLOW + "Hm. I guess it was only a matter of time before we found more kids like you." + ANSI_RESET);
        pressEnter();
        System.out.println("As she spoke her pupils would slowly contract. Her skin, the wrinkled \n mess it usually was, started to become unnaturally smooth, almost like porcelain.");
        System.out.println(ANSI_YELLOW + "\"Ms. Dodd's? You okay there?\"" + ANSI_RESET + " \n At this point I had started to back off, but the door was closed. Great.");

        if(keyPoints.get("holySword")){
            System.out.println(ANSI_RESET + "\n Dodd's would be eyeing me. Clearly looking for something. After a while she would give a satisfied nod\n. \n " + ANSI_YELLOW + "You're one of the kids we call.. a holy child? I guess we could call you that. \n" +
                    "The only ones with the power to stop what we want to do at this school. " + ANSI_RESET);
            //this isn't cringe because percy jackson wrote it more randomly than this at first and people ate it up (it is cringe)
            fightIntro(keyPoints);
            doddsFight(decisions,keyPoints,inventory);

        } else{
            if(keyPoints.get("cleoNecklace")){
                System.out.println("You live!");
            } else{deathScreen("Dodds Killed You",decisions.size(),decisions);}
        }
    }

    public static void postLunch(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory){

        if(keyPoints.get("brandKey")){
            pressEnter();
            System.out.println("I was outside the office now, nobody had stopped me from just walking out. \n There were no questions,not even a gaze.\n" +
                    "After the fight the sword had promptly hid itself too.\n Its effects remained though, while I should be hyperventilating and having " +
                    "a few heart attacks, I was half-calm. \n I looked at the key in my palm. It was a simple one, like any other house key. \n");
            pressEnter();
            System.out.println("But I could feel it feeding me information. \n Or well, something like that. \n I could tell there was another one of it. It's other half" +
                    "\n Without it, it wouldn't be complete. Together they worked together to unlock the barrier around the school. \n" +
                    "It kept anyone from leaving via immediate disintegration, I would understand pretty quickly if I tried it...");

            pressEnter();

            System.out.println("Hmm, should I even go to my next class? I mean, there's kind of a bigger problem right now. Is it my problem? And how do I fix it?");
            System.out.println("As I roamed the hallways I see Flo come out of a corner, she looks at me with a bit of surprise in her eyes.");

            pressEnter();

            System.out.println(ANSI_YELLOW + "You're not in class either?" + ANSI_RESET + " She'd grin" + ANSI_YELLOW + "\n" +
                    "Well, I needed to talk to you anyway. You didn't meet me at lunch.");

            pressEnter();
            System.out.println("I didn't reply, too focused on something else. \n" + ANSI_YELLOW + "You know, you seem different today. Smell different? Like how you said I smelt like perfume. \n" + ANSI_RESET);
            pressEnter();
            System.out.println("Her speech was becoming a little more erratic, almost stilted.");
            pressEnter();
            System.out.println(ANSI_YELLOW + "Have you ever heard of Malicious Spirits?" + ANSI_RESET + "\n I shook my head." + ANSI_YELLOW + "\n" +
                    "They're these grotesque little things that take human form. They have to consume mass quantities of souls once in a while to survive." + ANSI_RESET);

            pressEnter();
            System.out.println("I had already known what I realized. The key was vibrating from inside my pocket. The issue was, I wasn't distressed.");
            System.out.println(ANSI_BLUE + "Let Flo continue?" + ANSI_RESET + ANSI_PURPLE + "\n A. No" + "\n B. Yes");
            String choice = inputTaker(new String[]{"A","B"});
            if (choice.equals("A")){
                System.out.println("Sorry, Flo");
                floFight(decisions,keyPoints,inventory);

            } else {
                System.out.println(ANSI_YELLOW + "\n You might have realized by now. You got Brand.. right? I can feel the stench radiating off of you. \n" +
                        " That guy was tough. Flo gave me a weak smile. I'm sorry about this, but if you wield that thing.." + ANSI_RESET);
                System.out.println("The Thing in question had already appeared in my hand. I found myself struggling against its will, as I felt control over my body slipping.");
                floFight(decisions, keyPoints, inventory);
            }




            //BRAND KEY STUFF AND ALSO FLO FUNCTION
        } else if (keyPoints.get("doddsDislike")){
            doddsTalkOne(decisions,keyPoints,inventory); //this one is a method unlike the other ones because I will need to use that exact dialogue again


        } else{
            System.out.println("After eating lunch and walking for a little I would start heading to class. With a few detours of course, no need to be too early." +
            "\n I was thinking of what Flo said earlier.. she was acting a little weird wasn't she?");
            pressEnter();
            System.out.println(ANSI_YELLOW + "Is he over here?" + ANSI_RESET + "\n I heard a small voice and humming, it was Flo of course. Not long after she would enter my vision at the end of the hallway.\n" +
                    ANSI_YELLOW + "\n Oh, you are" + ANSI_RESET + "\n\"You're not in class either?\" I'd ask the obvious.");
            pressEnter();
            System.out.println(ANSI_YELLOW + "Well obviously not, I told you I'd see you later right? Here I am!" + ANSI_YELLOW + "\n Flo would make a mock superhero pose here. Yeah, she was definitely hyper.. or something. \n" +
                    ANSI_YELLOW + "Hey, you smell too you know. Not what I'm used to though." + ANSI_RESET + "\" Well thanks. \" I'd respond dryly, about to ask Flo if she was okay.");

            pressEnter();
            System.out.println("Before I could ask though, she started." + ANSI_YELLOW + "This is going to feel a little random to you, but have you ever heard of Malicious Spirits?" + ANSI_RESET);
            pressEnter();
            System.out.println(ANSI_YELLOW + "They're these things that assume human form and live mostly human lives. Although their main purpose is to consume mass quantities of souls" + ANSI_RESET);
            pressEnter();
            System.out.println(ANSI_YELLOW + "If you were lucky, you met the other ones in this school first. Ms Dodds and Principal Brand. " +
                    "That might have made this explanation less janky? Yeah. \n That would've been all, you would've been left to your own devices until the end." +
                    "\n But there are always counterbalances to us spirits. And humans that can contract with those counterbalances." + ANSI_RESET);

            pressEnter();
            System.out.println(ANSI_YELLOW + "Your sense of smell was a small giveaway, but in the end it is what it is.");
            pressEnter();
            System.out.println("It's not like I was silent while Flo was talking, actually I was asking a bunch of questions. " +
                    "\n This felt like a really dumb prank. But like any joke, there was that crawling suspicion that it was real. \n " +
                    "And then feeling stupid about even considering it to be anything but a joke. In any case, she just kept yapping.");

            pressEnter();
            System.out.println(ANSI_PURPLE + "Flo is attacking you!" + ANSI_RESET);
            System.out.println("The hallway was completely scarce, which was rare even during classtime. \n " +
                    "And all I saw was a expressionless Flo running towards me, before throwing a fist at me." +
                    "\n I yelped, ducking on instinct, and barely dodged it. And where the fist landed I saw small cracks on the wall. ");

            pressEnter();
            System.out.println("She didnt give me time to ask questions though, it came coming, each faster than the other. \n Although she was clearly letting me dodge.");

            if(keyPoints.get("holySword") || keyPoints.get("altarKey")){
                fightIntro(keyPoints);
                floFight(decisions,keyPoints,inventory);
            } else{
                System.out.println("My inability to response led way to anger though, and I struck out. I didn't know how to fight though, so it was more of a scratch than anything.");
                floFight(decisions,keyPoints,inventory);
            }

        }

    }

    public static void brandFight(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory){
        System.out.println(ANSI_BLUE + "\n Brand is attacking you!" +
                "You lose if you die or he doesn't. You win if he dies. Good luck.  \n This fight is best presented in a choice-based format." + ANSI_RESET);

        System.out.println("It felt like someone had stabbed a plug into the back of my head. \n Except whatever it did, it had worked.");
        pressEnter();
        System.out.println("My vision felt sharper, but I couldn't savour it for long. " +
                ANSI_BLUE + "Brand is attacking you" + ANSI_RESET
        + "\n As that message ringed in my ears, I saw Principal Brand stand up at his desk and raise his foot against it.");
        pressEnter();
        System.out.println("Before I could question his movement, the answer was facing me. \n" +
                "His desk, an academic tool at best, maybe even the prized masterpiece of a now distraught craftsman" +
                " was now flipping through the air towards me. \n \n");

        pressEnter();
        System.out.println(ANSI_BLUE + "Brand's Desk is flying through the air at you. \n" + "I can see that." +
               ANSI_BLUE + "It's made of hardwood. 30 inches tall. 36 inches deep. 72 inches wide. \n If it hits, it'll hurt." + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Make a decision. A. Stand there. \n B. Block with the sword. C. Duck and slide under it.");
        String move = decisionModule(new String[]{"A","B","C"},keyPoints,inventory,decisions);
        switch(move){
            case "A":
                deathScreen("Brand's desk killed you",decisions.size(),decisions,"Why would you do that?");
                break;

            case "B":
                System.out.println("The sword is sharp. I.. am not. I don't know why I decided to stand in front of a flying desk, but I'm lucky the sword is helping me out.\n" +
                        "As the desk meets the holy sword, it is slightly repelled. I feel my arms strain under it's force and struggle to keep the sword in my grasp," +
                        "\n but in the end I'm sent flying towards the wall. The force of the desk must have been lowered by the sword because I'm not winded, despite my back \n" +
                        "making a touchdown with the wall in a way that can only be described as passionate ");
                System.out.println(ANSI_BLUE + "Your health has been lowered by 2." + ANSI_RESET);

                pressEnter();

                System.out.println("As I regain my footing I hear the rush of footsteps, letting me know of Brand's imminent attack. " +
                        "His hand is aflame, like literally. They're bright orange, but at the same time lightless, flames " +
                        " that stay contained around his hand. He doesn't give me much time to admire them though, because he attempts to grab my face with outstretched fingers.");

                pressEnter();
                System.out.println(ANSI_BLUE + "Brand is attempting to grab your face with his right arm. It's on fire. You see a knife concealed in his suit." + ANSI_RESET);
                System.out.println(ANSI_PURPLE + "Make a decision. \n A. Block his arm with your sword. \n B. Dodge to the right and swing  at his arm. \n C. Dodge to the right and kick at him.");
                move = decisionModule(new String[]{"A","B","C"},keyPoints,inventory,decisions);

                switch(move){
                    case "A":
                        System.out.println("Again, I block. His flame clashes with the blade as if physical in nature. But then he grabs my hand holding the sword. \n" +
                                "Immediately I feel something wrong deep inside. It's the worst kind of gut feeling. The last thing I see is");
                        deathScreen("You became fuel",decisions.size(),decisions);
                        break;

                    case "B":
                        System.out.println("This time, I dodge to the right. Brand's grip meets the wall and cracks it, \n but I'm too busy swinging the sword at his arm" +
                                "to notice. Please cut.");
                        System.out.println(ANSI_YELLOW + "Not bad!" + ANSI_RESET);
                        System.out.println("As if physical, the fire blocks the sword and pushes me out of balance. \n" +
                                "Immediately taking advantage of this Brand swivels and kicks at my face, sending me flying into the desk from before. \n" +
                                "I can't describe what happened in between, because I didn't see it. But I found myself amongst broken wood and splinters while my vision faded.");

                        deathScreen("You lost consciousness, and Brand doesn't take prisoners.",decisions.size(),decisions);
                        break;

                    case "C":
                        System.out.println("This time, I dodge to the right. Before he can give chase, I kick at Brand's stomach, forcing him to move backwards. \n" +
                                "He's taken a little by surprise. My kick does little damage but his sudden movement serves to get the knife out of his coat");
                        System.out.println(ANSI_BLUE + "Brand lost 1 health!"  + ANSI_RESET);

                        System.out.println("Brand's smile only gets thicker. The knife has a red gem on the hilt, and the blade is silver. The gem looks almost.. fragile? \n The blade is wickedly sharp, probably more sharp than my sword.");

                        System.out.println(ANSI_PURPLE + "Make a decision. \n A. Pick up the knife and attack Brand. \n B. Destroy the gem and attack Brand." + ANSI_RESET);

                        move = decisionModule(new String[]{"A","B"},keyPoints,inventory,decisions);
                        switch(move){
                            case "A":
                                System.out.println(ANSI_YELLOW + "You have gained Brand's Knife." + ANSI_RESET);
                                pressEnter();

                                System.out.println("I pick up the knife. It feels good in my hands, for a moment. Immediately after I feel something wrong deep inside." +
                                        "\n Brand starts walking towards me as a pain builds in my stomach. \n The last thing I see is");
                                deathScreen("You became fuel for the fire.",decisions.size(),decisions);

                                break;

                            case "B":
                                System.out.println("As I break the gem, I see... or feel something change in Brand immediately. His face is red with pain, and it looks like he needs to vomit.");
                                System.out.println(ANSI_BLUE + "Brand has lost 5 health!" + ANSI_RESET);
                                System.out.println("I don't waste my chance. Well, the sword doesn't waste its chance. I lunge forward and sword meets neck." +
                                        "Thankfully I'm saved the gore, as Brand's body disintegrates, leaving only a small key behind. I just killed my principal. Bruh?");
                                        keyPoints.put("brandKey",true);
                                        inventory.put("Brand's Key", 1);
                                System.out.println(ANSI_YELLOW + "You have acquired: Brand's Key --> 1/2 KEY." + ANSI_RESET);

                                if(!(keyPoints.get("floKey"))) {
                                    postLunch(decisions, keyPoints, inventory);
                                } else{
                                    if(keyPoints.get("floKey") && keyPoints.get("doddsDead")){
                                        System.out.println("DEMON SLAYER ENDINGING");
                                    } else if(keyPoints.get("floKey") && !(keyPoints.get("doddsDead"))){
                                        pressEnter();
                                        System.out.println("Having both keys to the barrier together told me a lot.\n");
                                        pressEnter();
                                        System.out.println("1. Dodds, Flo, and Brand were all malicious spirits working together. Basically, demons?" +
                                                "\n 2. The school was enclosed via spicy demon magic, no exit allowed under threat of death. " +
                                                "\n 3. Their goal was to eventually consume the whole school via ritual. \n For this ritual they needed 3 kids like me. So they were waiting." +
                                                "\n 4. Yes, Dodds as in my teacher Dodds." +
                                                "\n 5. While I had been thinking of this, the holy sword had taken me to Dodds' room. Standing before it though it allowed me control for one more choice.");

                                        System.out.println(ANSI_PURPLE + "You can exit the barrier and leave the school with the two keys, or you can choose to eliminate Dodds. \n" + "\n Please Enter. \n A. Exit the School \n B. Eliminate Dodds.");
                                        String choice = decisionModule(new String[]{"A", "B"},keyPoints,inventory,decisions);

                                        switch (choice){
                                            case "A":
                                                leaveSchool(decisions);
                                                break;

                                            case "B":
                                                pressEnter();
                                                doddsFight(decisions,keyPoints,inventory);
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
                System.out.println(ANSI_RESET + "I, or the holy sword, can see how the desk rotates through the air. In one fluid motion I run and slide under the desk as it flips over me  \n." +
                        "As I push off the ground to right myself, I can see Brand with a knife now in hand. He raises an eyebrow, probably at how I'm moving, but its quickly replaced by a smile." +
                        "\n Of course the question is still bouncing around in my head. Blaring would be a better word. Why is my principal attacking me? \n" +
                        "How is he this strong? Why the hell is a sword in my hand? Am I going to die? \n But the sword and well, the fight, doesn't allow me the time to think about that.");

                pressEnter();
                System.out.println("Involuntarily, my body lunges towards Brand. \n Hey, what? Shouldn't I be getting out of here or something?");
                System.out.println(ANSI_BLUE + "Brand is holding his knife in his left hand. He stands 6'2. His smirk is at an angle of 15 degrees. \n His jawline, immaculate. His eyes, tender. Choose a method of attack." + ANSI_RESET);
                System.out.println(ANSI_PURPLE+ "\n A. Thrust forward, a stab at his stomach" + "\n B. Step to the right and overhead slash" + "\n C. Step to the left and slash" + ANSI_RESET);

                move = decisionModule(new String[]{"A","B","C"},keyPoints,inventory,decisions);
                switch (move){
                    case "A":
                        System.out.println("Whatever was controlling my body didn't even let me shout. With a step forward I thrust the blade at Brand. \n " +
                                "The resounding clash of metal tells me it didn't hit.");
                        break;
                    case "B":
                        System.out.println(" Whatever was controlling my body didn't even let me shout. With a step to the right I bring the sword down," +
                                "\n but he didn't even have to move to block it. \n " +
                                "Immediately after, his hand turns into a blur, and I feel a line drawn on my stomach. \n" +
                                "Thankfully I had moved back on instinct, so it wasn't deep." +
                                " \n But for someone who went from no-lines-drawn to more-than-no-lines-drawn on their stomach in just a few minutes.. \n it hurt.");
                        System.out.println(ANSI_BLUE + "You have lost 1 health." + ANSI_RESET);
                        break;

                    case "C":
                        System.out.println("Whatever was controlling my body didn't even let me shout. I stepped to the left, clenching the sword " +
                                "and drawing a line from Brand's hip to his shoulder");
                        System.out.println("He's almost too surprised at my speed to dodge, but he manages to react. \n The blade had met his hip and left a small gash.");
                        System.out.println(ANSI_BLUE + "Brand has lost 1 health!" + ANSI_RESET);
                        break;

                }

                pressEnter();

                System.out.println(ANSI_YELLOW + "Not bad at all. If you're already this experienced I assume you've realized the school is closed off? You're trapped." + ANSI_RESET);
                System.out.println("Free villain info dialogue? Does he not know about the sword's help?" +
                        "\n \" Closed off? I can just walk outside? \" The sword forces me to be calm. It hides the lack of breath, stutter, and fear in my voice. " +
                        "\n" + ANSI_YELLOW + "Unless you want to disintegrate before you realize, and be erased from memory. Brand says calmly" + ANSI_RESET +"\n" +
                        "\" What about any other student? \" I ask, but Brand's smile only gets thicker");

                System.out.println(ANSI_BLUE + "Brand is attacking you." + ANSI_RESET + "\n Brand spins the knife in his hand and launches himself at me. \n" +
                        "His arm reminds me of a snake, coiling back before darting at my right eye. \n" + "I could practically feel the tip of his knife on my pupil, but I do have to make a decision. \n");
                pressEnter();

                System.out.println(ANSI_BLUE + "Brand is attacking your right eye. His midsection and right arm are exposed. His tie has a small coffee stain." + ANSI_RESET);
                System.out.println(ANSI_PURPLE + "Make a decision. \n A. Dodge to your right and swing at his right arm. \n B. Dodge to your left and stab his stomach. \n "
                        + ANSI_RESET);
                move = decisionModule(new String[]{"A","B"},keyPoints,inventory,decisions);

                switch(move){

                    case "A":
                        System.out.println("I dodge to my right, and in doing so, allow the knife to plunge into my left eye.");

                        deathScreen("Knife, eye, brain!",decisions.size(),decisions,"He's attacking with his left? Dodging right means moving away from the right arm.");

                        break;
                    case "B":
                        System.out.println("At the last moment, I dodge to the left and raise the sword. Between the stomach and arm, the stomach would hurt more right?" +
                                "\n I plunge the sword into his stomach and twist. It gets to know Brand in every way. Entrance, indoors, and exit." +
                                "\n Clearly, Brand wasn't expecting this either because his eyes open wide. Strangely enough, there's no blood. PG-friendly I guess?" +
                                "\n After a few steps back, he quite literally starts to crumble. I mean turn to dust, which also disappears." +
                                "\n The only thing left of him is a small key. I just killed my principal. Bruh?");

                        keyPoints.put("brandKey",true);
                        inventory.put("Brand's Key", 1);
                        System.out.println(ANSI_YELLOW + "You have acquired: Brand's Key --> 1/2 KEY." + ANSI_RESET);

                        if(!(keyPoints.get("floKey"))) {
                            postLunch(decisions, keyPoints, inventory);
                        } else{
                            if(keyPoints.get("floKey") && keyPoints.get("doddsDead")){
                                bloodThirsty(decisions);

                            } else if(keyPoints.get("floKey") && !(keyPoints.get("doddsDead"))){
                                pressEnter();
                                System.out.println("After some study, having both keys to the barrier together told me a lot.\n");
                                pressEnter();
                                System.out.println("1. Dodds, Flo, and Brand were all malicious spirits working together. Basically, demons?" +
                                        "\n 2. The school was enclosed via spicy demon magic, no exit allowed under threat of death. " +
                                        "\n 3. Their goal was to eventually consume the whole school via ritual. \n For this ritual they needed 3 kids like me. So they were waiting." +
                                        "\n 4. Yes, Dodds as in my teacher Dodds." +
                                        "\n 5. While I had been thinking of this, the holy sword had taken me to Dodds' room. Standing before it though it allowed me control for one more choice.");

                                System.out.println(ANSI_PURPLE + "You can exit the barrier and leave the school with the two keys, or you can choose to eliminate Dodds. \n" + "\n Please Enter. \n A. Exit the School \n B. Eliminate Dodds.");
                                String choice = decisionModule(new String[]{"A", "B"},keyPoints,inventory,decisions);

                                switch (choice){
                                    case "A":
                                        leaveSchool(decisions);
                                        break;

                                    case "B":
                                        pressEnter();
                                        doddsFight(decisions,keyPoints,inventory);
                                        break;

                                }

                            }
                        }

                        break;
                }

                break;

        }




    }
    public static void fightIntro(HashMap<String, Boolean> keyPoints){

        if (keyPoints.get("holySword")){

            System.out.println(ANSI_BLUE + "Initializing mainframe..." + ANSI_RESET);
            pressEnter();
            System.out.println(ANSI_BLUE + "Welcome to the Holy Sword! \n BUILD: 1.3011.3 \n Last Edited: 1353-12-31 \n \n" +
                    "This system was made to assist the user in battle should they be incapable of it. \n" +
                    "The holy sword, experienced in fighting, will execute movements and observation. The User will make the appropriate decision." +
                    "\n Receive Guide: How to Fight for Beginners?"); pressEnter();

            System.out.println(ANSI_BLUE +"How to Fight --> \" If fighting a malicious spi--" + "\n LOW POWER MODE, Guide OFF");


        } else if (keyPoints.get("altarKey")){
            System.out.println(ANSI_BLUE + "Initi♌\uFE0E❒\uFE0E◆\uFE0E♒\uFE0E mainframe..." + ANSI_RESET);
            pressEnter();
            System.out.println(" " + ANSI_YELLOW +
                    "\uD83D\uDDB2\uFE0E□\uFE0E◆\uFE0E♒\uFE0E□\uFE0E\uD83D\uDE70□\uFE0E" +
                    "This system system NOT MADE FOR FIGHTING" +
                    "But in ♏\uFE0E❍\uFE0E♏\uFE0E❒\uFE0E♑\uFE0E♏\uFE0E■\uFE0E♍\uFE0E♓\uFE0E♏\uFE0E⬧\uFE0E emergencies in usable when maybe" + ANSI_RESET);
                    pressEnter();
            System.out.println(ANSI_BLUE + "Bruh?" + ANSI_RESET);

        }
    }
    public static void brandMeet(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory){
        if(keyPoints.get("cleoNecklace")){
            postLunch(decisions,keyPoints,inventory);
            return;
        }
        System.out.println("I walked through the hallways, not slowly but not too quickly either. No need to get to class early." +
                "\n As I go near the office, I see Principal Brand near the doorway. I'd describe him as tall, but not too tall. He was wearing a simple black suit \n" +
                "and glasses. As for his face... he looked exactly how you'd expect a Brand to look. A full but trimmed beard, eyes on the sharper side" +
                "and his hair had a few white strands. \n In general he was known to have a calm demeanour. The kind of attitude you'd expect from someone overly competent.");

        pressEnter();
        System.out.println("Maybe he heard me describing him so thoroughly, but he'd notice me and make eye contact."
        + ANSI_YELLOW + "Good afternoon. Heading to classes I hope?" + ANSI_RESET +
                "\n \" Yes I was just on my way \" I'd reply, though I was a little taken aback at him talking to me so directly."
        + "Principal Brand would take a moment to consider me for a second,");

        if (!(keyPoints.get("altarKey") || keyPoints.get("holySword"))){
            System.out.println(ANSI_YELLOW + "Enjoy your classes. See you around." + ANSI_RESET);

        }
        System.out.println(ANSI_PURPLE + "Something in your inventory is waking up." + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "Now that I think about it, one of your teachers mentioned you to me. Good things of course. \n Could we go in my office to talk about something related to your school life?" + ANSI_RESET);
        System.out.println("Huh? I mean I had to go with him. But isn't that too cheap of a reason? Am I in trouble?" +
                "\n There is in no way that \"one of your teachers mentioned you to me \" is a reason");

        pressEnter();
        System.out.println("We make our way into his office and he closes the door before sitting down.");
        pressEnter();
        System.out.println(ANSI_YELLOW + "Truth be told, you could tell that wasn't the real reason" + ANSI_RESET
        + "I said nothing in response, utterly confused. Just what did I do to deserve this? I don't have the energy for something complicated");
        pressEnter();
        System.out.println(ANSI_YELLOW + "Actually it's less about you and more about what you have. You can come out now" + ANSI_RESET);

        for(int i = 0; i<3; i++)
            {
                System.out.println(ANSI_PURPLE + "Something in your possesion is moving." + ANSI_RESET);
                pressEnter();
            }
        if(keyPoints.get("holySword")){
            System.out.println("A sword would appear in my hands. It had a silver blade with a golden handle. Other than that there were no adornments."
            + "Wait. What?" + ANSI_YELLOW + "\n I see you're used to using it. This should be fun?");
            System.out.println(ANSI_PURPLE + "Brand is attacking you." + ANSI_RESET);

            fightIntro(keyPoints);
            brandFight(decisions,keyPoints,inventory);



        } else if(keyPoints.get("altarKey")){
            System.out.println("A large wrought iron key with a gold inlay appeared in my hands. What the hell?");
            System.out.println(ANSI_YELLOW + "It's interesting that you have that, how did you find it? I'll be taking it though. A shame you can't defend yourself with this one." + ANSI_RESET);
            deathScreen("You were killed by Brand.",decisions.size(),decisions, "I can't defend myself with the Altar Key.. but perhaps something else?");
        }

    }
    public static void floLunch(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory){
        System.out.println("Looks like it's time to meet Flo, she should by our usual spot on the third floor?" +
                "\n \n She waves as I approach, the perfume-like smell from before somehow stronger. \n As if sensing my thoughts she asks" +
                 ANSI_YELLOW + "Still appreciating my aroma?" + ANSI_RESET +
                "\n I go to deny, \"Hey if it bothers you--\" but she cuts me off with a wave and shrugs," +
                ANSI_YELLOW + "Nah it's fine. You know, me and Mrs. Dodds are more alike than you think." + ANSI_RESET +
                "\" What did you wanna talk about? \" I ask, brushing her previous remark off as classic Flo banter" +
                ANSI_YELLOW + "I just wanted to talk! Hey, do you believe in tests?"+ ANSI_RESET );
        pressEnter();
        System.out.println("\" Sure? Like school ones? \"" + ANSI_YELLOW + "\n No no, like tests in life. Like a challenge or something." + ANSI_RESET);
        pressEnter();
        System.out.println("\" Are you trying to be mysterious? \" \n" + ANSI_YELLOW +
                " \n Some people get more tests than others right? We call them the Special Few" + ANSI_RESET +
                "\n Whos we?");
        pressEnter();
        System.out.println("At this point a glint had entered Flo's eyes, which was more than unusual." +
                "Sure, the girl was crazy, but lowkey. Not.. whatever this was."
                + ANSI_YELLOW + "I have to go now, but I'll see you soon. Thanks for this." + ANSI_RESET );

        pressEnter();
        postLunch(decisions,keyPoints,inventory);
    }
    public static void frontFoyer(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory, int counter){
        Scanner sc = new Scanner(System.in);
        System.out.println("I had noticed something off about the foyer, so I decide to head there. \n");
        System.out.println("There, at the front, near the plaque on the wall. There was a small crack that had never been there before. \n");
        System.out.println("Inside it I saw dull metal. Looks like a wall support? Out of curiousity I poke it anyway.");
        System.out.println(ANSI_PURPLE + "Press Enter To Continue" + ANSI_RESET);
        sc.nextLine();
        System.out.println(ANSI_YELLOW + "Womp Womp" + ANSI_RESET + "\n What. I look around to hear if anyone had heard that voice, but nobody was here anyway." + ANSI_YELLOW +
                "\n \uD83D\uDD48\uFE0E♏\uFE0E●\uFE0E♍\uFE0E□\uFE0E❍\uFE0E♏\uFE0E ⧫\uFE0E□\uFE0E ⧫\uFE0E♒\uFE0E♏\uFE0E ♋\uFE0E●\uFE0E⧫\uFE0E♋\uFE0E❒\uFE0E \uD83D\uDE75♏\uFE0E⍓\uFE0E" +
                "Yahoo" + ANSI_RESET + ANSI_PURPLE + "Press enter to continue" + ANSI_RESET);
        sc.nextLine();
        System.out.println(ANSI_YELLOW + "mimimimimi\n " + ANSI_RESET + "What am I supposed to say? Who the hell is making these noises? Nobody's around?" +
                 ANSI_YELLOW + "\nWhat am I: Hoofed Frolicking.");

        System.out.println(ANSI_PURPLE + "You have three chances to solve this riddle. You must enter your answer in ONE word.");
        String answer = null;
        for(int i = 0; i<3; i++){
            System.out.println("What is your " + (i+1) + "st Answer?");
            answer = sc.nextLine();
            if((answer).equalsIgnoreCase("horseplay")){
                System.out.println("...it's Horseplay.");
                System.out.println(ANSI_YELLOW + "Correct. You have acquired the Altar Key" + ANSI_RESET);
                inventory.put("Altar Key",1);
                keyPoints.put("altarKey",true);
                break;
            }
        } if(!(answer).equalsIgnoreCase("horseplay")){
            System.out.println(ANSI_YELLOW + "Goodbye!" + ANSI_RESET);
        }

        System.out.println(ANSI_PURPLE+"Press enter to continue." + ANSI_RESET);
        sc.nextLine();

        System.out.println("What did I come here for again? This is where I enter school. \n I check the time on my phone and it's halfway through lunch.");

        counter++;
        if(counter<2){
            System.out.println("Time for my next visit! Where should I go? \n" +
                    ANSI_PURPLE+ "A. Cafeteria \n B. Basement");

            String decision = decisionModule(new String[]{"A","B"},keyPoints,inventory,decisions);
            switch(decision){
                case "A":
                    cafeteria(decisions,keyPoints,inventory,counter);
                    break;

                case "B":
                    basement(decisions,keyPoints,inventory,counter);
                    break;
            }
        }else{
            if(keyPoints.get("floLike")){floLunch(decisions,keyPoints,inventory);
            } else{
                brandMeet(decisions,keyPoints,inventory);
            }
        }

    }
    public static void basement(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory, int counter){
        Scanner sc = new Scanner(System.in);
        System.out.println("I head to my usual spot, the basement. It's nice and quiet, most of the time." +
                "\n You might spot the odd couple but there are so many winding hallways that their patronage usually isn't of consequence" +
                "\n This time though, something feels a little odd. More than the average highschool-basement-weird.\n" +
                ANSI_PURPLE + "Press enter to continue" + ANSI_RESET);
        sc.nextLine();
        System.out.println("I keep going into the hallways, my footsteps lighter than usual. " +
                "\n I felt the burning in my stomach slowly growing with each step. \n " +
                "Oddly enough it wasn't anxiety or fear, but I just didn't know what it was" +
                ANSI_PURPLE + "Press enter to continue" + ANSI_RESET);
        sc.nextLine();
        System.out.println(ANSI_YELLOW + "\n Hey." + ANSI_RESET +
                "\n Huh?" + ANSI_YELLOW + "\n Hey. " + ANSI_RESET
        + "\n Where the hell is that coming from?" +
                ANSI_YELLOW + "\n ♒\uFE0E♏\uFE0E⍓\uFE0E." + ANSI_RESET
        );
        System.out.println(ANSI_YELLOW + " \uD83D\uDC4E\uFE0E♓\uFE0E♎\uFE0E ⍓\uFE0E□\uFE0E◆\uFE0E ❒\uFE0E♏\uFE0E♋\uFE0E●\uFE0E●\uFE0E⍓\uFE0E ⧫\uFE0E❒\uFE0E♋\uFE0E■\uFE0E⬧\uFE0E●\uFE0E♋\uFE0E⧫\uFE0E♏\uFE0E ⧫\uFE0E♒\uFE0E♓\uFE0E⬧\uFE0E" +
                "\n \uD83D\uDD48\uFE0E♏\uFE0E●\uFE0E♍\uFE0E□\uFE0E❍\uFE0E♏\uFE0E ⧫\uFE0E□\uFE0E ⧫\uFE0E♒\uFE0E♏\uFE0E ♒\uFE0E□\uFE0E●\uFE0E⍓\uFE0E ⬧\uFE0E⬥\uFE0E□\uFE0E❒\uFE0E♎\uFE0E" +
                 "\n ☹\uFE0E☹\uFE0E☹\uFE0E☹\uFE0E☹\uFE0E☹\uFE0E☹\uFE0E☹\uFE0E☹\uFE0E☹\uFE0E☹\uFE0E☹\uFE0E☹\uFE0E" +
                ANSI_RESET);

        System.out.println("Hey." + ANSI_YELLOW + "I have keys but no locks.\n" +
                "I have space but no room.\n" +
                "You can enter but can't go outside.\n" +
                "What am I?" + ANSI_RESET);

        System.out.println("What?");

        System.out.println(ANSI_PURPLE + "You have three chances to solve this riddle. You must enter your answer in ONE word.");
        String answer = null;
        for(int i = 0; i<3; i++){
            System.out.println("What is your " + (i+1) + "st Answer?");
            answer = sc.nextLine();
            if((answer).equalsIgnoreCase("keyboard")){
                System.out.println("The hell? A keyboard?");
                System.out.println(ANSI_YELLOW + "Correct. You have acquired the Holy Sword." + ANSI_RESET);
                inventory.put("Holy Sword",1);
                keyPoints.put("holySword",true);
                break;
            }
        } if(!(answer).equalsIgnoreCase("keyboard")){
            System.out.println(ANSI_YELLOW + "Goodbye!" + ANSI_RESET);
        }

        System.out.println(ANSI_PURPLE+"Press enter to continue." + ANSI_RESET);
        sc.nextLine();

        System.out.println("I'm back at the entrance of the basement" +
                "\n Although you could've convinced me that I had never left... Did I?");

        System.out.println("I quickly checked the time on my phone and half of lunch had gone by ..?");
        counter++;

        if(counter<2){
            System.out.println("Time for my next visit! Where should I go? \n" +
                    ANSI_PURPLE+ "A. Cafeteria \n B. Front Foyer");
            String decision = decisionModule(new String[]{"A","B"},keyPoints,inventory,decisions);
            switch(decision){
                case "A":
                    cafeteria(decisions,keyPoints,inventory,counter);
                    break;

                case "B":
                    frontFoyer(decisions,keyPoints,inventory,counter);
                    break;
            }
        }else{
            if(keyPoints.get("floLike")){floLunch(decisions,keyPoints,inventory);
            } else{
                brandMeet(decisions,keyPoints,inventory);
            }
        }







    }

    public static void cafeteria(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory, int counter){
        System.out.println("I make my way to the cafeteria, a place I rarely frequent. Usually I just go to the basement.");
        System.out.println("It's neither busy nor empty, by this time of the year people have usually found their own spots.");
        System.out.println("But luckily for me, there is a snack service. Nothing ventured, no apples gained.");
        System.out.println(ANSI_YELLOW + "You have gained 1 Apple!" + ANSI_RESET);
        inventory.put("apple",1);
        counter++;
        if(counter<2){
            System.out.println("Time for my next visit! Where should I go? \n" +
                    ANSI_PURPLE+ "A. Basement \n B. Front Foyer");
            String decision = decisionModule(new String[]{"A","B"},keyPoints,inventory,decisions);
            switch(decision){
                case "A":
                    basement(decisions,keyPoints,inventory,counter);
                    break;

                case "B":
                    frontFoyer(decisions,keyPoints,inventory,counter);
                    break;
            }
        }else{
            if(keyPoints.get("floLike")){floLunch(decisions,keyPoints,inventory);
            } else{
                brandMeet(decisions,keyPoints,inventory);
            }
        }


    }

    public static void lunch(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory){

        int counter;

        System.out.println("The periods pass by until lunch.");

        if(keyPoints.get("floLike")){
            System.out.println("I'm meeting Flo at lunch, so I'll only have time to visit 1 place");
            counter = 1;
        } else{
            System.out.println("I told Flo I wouldn't meet with her, which means I have time to visit two places.");
            counter = 0;
        }

        System.out.println(ANSI_PURPLE + "A. Cafeteria \n B. Basement \n C. Front Foyer " + ANSI_RESET);
        String decision = decisionModule(new String[] {"A","B","C"},keyPoints,inventory,decisions);


        switch(decision){

            case "A":

                cafeteria(decisions,keyPoints,inventory,counter);
                break;

            case "B":
                System.out.println("Basement");
                basement(decisions,keyPoints,inventory,counter);
                break;

            case "C":
                frontFoyer(decisions,keyPoints,inventory,counter);
                break;

        }












    }
    public static void floClassTalk(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints,HashMap<String, Integer> inventory){
        System.out.println("\"Hey\", I greet Flo, followed by a short groan as I set my bag down. Flo was pretty much my only friend at this school. \n The same was true both ways." +
                ANSI_YELLOW+ "\" You're talkative today. I saw you and Dodds up there having your fun. \" " +
                "\n Her lopsided smile betrayed amusement, which her raised eyebrow only supported" + ANSI_RESET +
                "\" \n Don't even mention that. Am I the only one that finds her odd? I mean she literally makes the hairs on my arm raise.\" "
                + "\n Flo's smile pretended to be a thoughtful expression before she shrugged," + ANSI_YELLOW +
                "\"\nWho knows? You know I wanted to talk to you about something important too. Does the school feel weird today?\" "
                + ANSI_RESET + "\"\nHuh? It's gloomy outside, if that's what you mean\" As I replied to Flo, I realized I felt something different about her" +
                "\" \nDid you try out new perfume or something? I swear you smell kind of like what I get from Mrs. Dodds + \" I joked, although I did smell the same harsh perfume."
                + "\nFlo squinted at me, clearly playing along. Although the way her mouth curved downwards felt more real than anything. Did I offend her? "
                + ANSI_YELLOW + "\" \n Whatever, did you wanna meet later into lunch? The school year's ending so it'll be our last one for a while.\" " + ANSI_RESET + ANSI_PURPLE +
                "Do I want to meet Flo at Lunch? \n A. Yes B. No");


        String meetFlo = decisionModule(new String[]{"A","B"},keyPoints,inventory,decisions);

        switch (meetFlo){
            case "A":
                System.out.println(ANSI_YELLOW + "You'll meet Flo in the second of lunch. Flo is happy with you." + ANSI_RESET);
                keyPoints.put("floLike", true);
                break;

            case "B":
                System.out.println(ANSI_YELLOW + "You wont meet Flo at lunch. Flo is not happy with you." + ANSI_RESET);
                keyPoints.put("floLike", false);
                break;
        }
        System.out.println("In any case, she stopped paying attention in class and hopped on her phone. Something about managing the school's confession account.");

        lunch(decisions,keyPoints,inventory);


    }

    public static String decisionModule(String[]validInputs, HashMap<String,Boolean> keyPoints, HashMap<String,Integer> inventory, ArrayList<String> decisions){
        String decision = null;

        System.out.println(inventory.get("Decisions") + " " + decisions.size());
//        if (!(keyPoints.get("devMode")) && (decisions.size()>=inventory.get("Decisions"))){
//            turnIterator(inventory);
//            decision =(inputTaker(validInputs));
//            decisions.add(decision);
//        } else{
//            turnIterator(inventory);
//            decision = decisions.get(inventory.get("Decisions")-1);}

        if (keyPoints.get("devMode") && (decisions.size()>inventory.get("Decisions"))){
            turnIterator(inventory);
            decision = decisions.get(inventory.get("Decisions")-1);}
        else{
            turnIterator(inventory);
            decision =(inputTaker(validInputs));
            decisions.add(decision);
        }

        return decision;
    }

    public static void doddsConvoLike(ArrayList<String>decisions,HashMap<String, Boolean> keyPoints,HashMap<String, Integer> inventory){

        System.out.println("I chose to go talk to Dodds first... here we go. \n" +
                ANSI_YELLOW + "\" I'm surprised! Amongst so many late students you've arrived early. That's a welcome change from the last week as you know.\"" + ANSI_RESET);


        System.out.print("Her comments threaten to go in one ear and out the other, but I have to actually participate in this conversation." +
                "\n Mrs. Dodd's voice always reminds me of thick honey and perfume just sharp enough to be irritating. " +
                "\n Underneath all that her anger is unparalleled, her scoldings and lectures the stuff of legends amongst anybody who cares to gossip");

        System.out.println("Two replies come to mind, one of them especially... positive:" + ANSI_PURPLE +
                "\n A. I've been trying to fix my act up Mrs. Dodds, I appreciate your support so much! \n B. Thanks Mrs. Dodds, hopefully the rest come in soon."
                + ANSI_RESET);

        String input = inputTaker(new String[]{"A","B"});
        switch (input){
            case "A":
                System.out.println(ANSI_YELLOW + "\" That's great to hear! You can go take your seat now! But before you go take one of these." + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "Watermelon Jolly Rancher acquired from Mrs. Dodds" + ANSI_RESET);
                inventory.put("Watermelon Jolly Rancher",1);
                System.out.println("As always, conversation with her feels stilted and unnatural. Anyways, I take my prize candy and go to my seat beside Flo.");
                break;

            case "B":
                System.out.println(ANSI_YELLOW + "\" You're right, I'll never understand how students come in late everyday. You can go take your seat now \"" + ANSI_RESET);
                System.out.println("As always, conversation with her feels stilted and unnatural. Anyways, I go to my seat beside Flo.");
                break;
        }
        System.out.println(decisions.toString());
        System.out.println(inventory.get("Decisions"));
        floClassTalk(decisions,keyPoints,inventory);
    }

    public static void doddsClassOne(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints,HashMap<String, Integer> inventory){

        System.out.println("ADD DESCRIPTOR OF DODDS AND FLO APPEARANCE");


        if(keyPoints.get("doddsDislike")){
            //WHAT HAPPENS WHEN DODDs DOES DISLIKE YOU
            System.out.println("Dodds scolds me." + ANSI_YELLOW + " \"Late to class as always. Take your seat\"" + ANSI_RESET);
            floClassTalk(decisions,keyPoints,inventory);

        }
        else{ //WHAT HAPPENS WHEN DODDS DOESNT DISLIKE YOU
            System.out.println(ANSI_RESET);
            System.out.println("I make my way to English class. As I enter Ms Dodds gives me a rare smile and Flo waves at me as usual.");
            System.out.println("I have two options, I could go talk to Ms. Dodds and try to butter her up or I could just chat with Flo");
            System.out.println("ENTER" + ANSI_PURPLE + "\n A. Talk to Ms. Dodds \n B. Talk to Flo " + ANSI_RESET);

            String talkDecision = null;

            if (!(keyPoints.get("devMode")) && (decisions.size()>=inventory.get("Decisions"))){
                turnIterator(inventory);
                talkDecision =(inputTaker(new String[]{"A", "B"}));
                decisions.add(talkDecision);
            } else{
                turnIterator(inventory);
                talkDecision = decisions.get(inventory.get("Decisions")-1);}

            System.out.println(decisions.toString());
            System.out.println(inventory.get("Decisions"));

            switch((talkDecision)){
                case "A":
                    doddsConvoLike(decisions,keyPoints,inventory);
                    break;
                case "B":
                    System.out.println(2);
                    floClassTalk(decisions,keyPoints,inventory);
                    break;//FLO TALK METHOD
            }


        }

    }

    public static void hallwayThree(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints,HashMap<String, Integer> inventory){
        String choice = null;
        //turnIterator(inventory);
        if (!(keyPoints.get("devMode")) && (decisions.size()>=inventory.get("Decisions"))){
            choice = (decisions.get(decisions.size()-1)).toUpperCase();
        } else{choice = decisions.get(inventory.get("Decisions")-1);}

        switch (choice){
            case "A": //left hallway
                keyPoints.put("doddsDislike", false);

                doddsClassOne(decisions,keyPoints,inventory);
                break;

            case "B":  //right hallway
                keyPoints.put("doddsDislike", true);
                doddsClassOne(decisions,keyPoints,inventory);

                break;

            case "C": //exit
                deathScreen("-",decisions.size(), decisions);
                break;
        }
    }

    public static String beginningOne (){
//        PImage img = loadImage("blankPic.png");
//        image(img,0,0);
        imageChanger("foyerOnePic");

        Scanner sc = new Scanner(System.in);
        System.out.println("Another day of school. Same time, same place. As I enter the front foyer I see my old science teacher. He greets me by my name." +
                "\n What do I hear?");

        String name = sc.nextLine();
        System.out.println(ANSI_YELLOW + "Good morning " + name + "!" + ANSI_RESET + " He was already down the hallway by the end of his sentence, late to his class once again. ");
        return name;

    }

    public static void beginningTwo(ArrayList<String> decisions, HashMap<String,Boolean> keyPoints, HashMap<String, Integer> inventory){

        System.out.println("My attention back to the foyer, I had three directions: the left hallway, right hallway, or exiting the school entirely. \n (ENTER " + ANSI_PURPLE +
                "\n A. left hallway \n B. right hallway \n C. exit the school \n Enter letter A B or C" + ANSI_RESET);



        if (!(keyPoints.get("devMode")) && (decisions.size()>=inventory.get("Decisions"))){
            decisions.add(inputTaker(new String[]{"A", "B", "C"}));
        }
        turnIterator(inventory);
    }

    public static String inputTaker(String[]validInputs){
        Scanner sc = new Scanner(System.in);
        String input;
        do {

            System.out.println("Please enter a valid input." + Arrays.toString(validInputs));
            input = sc.nextLine();

        }while(!(inList(input,validInputs)));
        return input.toUpperCase();

    }
    public static Boolean inList(String input, String[]list){
        for (String element : list) {
            if (element.equals(input.toLowerCase()) || element.equals(input.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
    public static void clearEvents(HashMap<String,Boolean> keyPoints){
        keyPoints.forEach((key,value) -> {
            keyPoints.put(key,false);
        }); //iterates through every key and changes it back to false!

    }




}