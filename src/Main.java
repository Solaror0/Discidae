/*
TITLE - DISCIDAE
Description -
Author - Jun Nur
Last Edited -
*/

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.*;
import java.io.*;
import processing.core.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends PApplet{
    //COLOUR VARS DECLARATION
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    //COLOUR VARS DECLARATION


    @Override public void settings(){
        size(600,600);
    }
    @Override public void setup(){
        size(600,600);
        background(32);
    }

    @Override public void draw(){

        rect(mouseX,mouseY,100,100);

    }


    public static void main(String[] args) {
        PApplet.main("Main");


        //Variables Setup
        Scanner sc = new Scanner(System.in);


        HashMap<String,Integer> inventory = new HashMap<String,Integer>(); //item inventory of the user
        inventory.put("Decisions",0);
        HashMap<String,Boolean> keyPoints = new HashMap<String,Boolean>(); //stores all the major events/booleans in one hashmap so the whole hashmap can be put into a method vs multiple variables
        keyPoints.put("devMode",false);
        ArrayList<String> decisions = new ArrayList<>(); //a stack might work too to see the last made decision(LIFO) --> but if i wanted to access decisions later

        String name = null;


        //Variables

        if(sc.nextLine().equals("Dev")){
            String[]devDecisions = ((sc.nextLine()).split(" "));
            Collections.addAll(decisions,devDecisions);
            keyPoints.put("devMode",true);
        }


        name = beginningOne();
        beginningTwo(decisions, keyPoints, inventory);
        hallwayThree(decisions, keyPoints, inventory);

        decisions.add(sc.nextLine());




    }

    public static void lunch(ArrayList<String> decisions, HashMap<String, Boolean> keyPoints, HashMap<String, Integer> inventory){

        System.out.println("The periods pass by until lunch.");

        if(keyPoints.get("floLike")){
            System.out.println("I'm meeting Flo at lunch, so I'll only have time to visit 1 place");
        } else{
            System.out.println("I told Flo I wouldn't meet with her, which means I have time to visit two places.");
        }

      //  Main.background(400);


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

                break;

            case "C": //exit
                deathScreen("-",decisions.size(), decisions);
                break;
        }
    }

    public static String beginningOne (){
        Scanner sc = new Scanner(System.in);
        System.out.println("Another day of school. Same time, same place. As I enter the front foyer I see my old science teacher. He greets me by my name." +
                "\n What do I hear?");

        String name = sc.nextLine();
        System.out.println(ANSI_YELLOW + "Good morning " + name + "!" + ANSI_RESET + " He was already down the hallway by the end of his sentence, late to his class once again. ");
        return name;
    }

    public static void beginningTwo(ArrayList<String> decisions, HashMap<String,Boolean> keyPoints, HashMap<String, Integer> inventory){
        System.out.println("My attention back to the foyer, I had three directions: the left or right hallway, or exiting the school entirely. \n (ENTER " + ANSI_PURPLE +
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

    public static void deathScreen(String causeOfDeath, int turns, ArrayList<String> decisions){
        System.out.println("You Died. Cause of death: " + causeOfDeath + "." + "You made it " + turns  + " turns.");
        System.out.println("Your decisions made were: " + decisions.toString());

    } public static void deathScreen(String causeOfDeath, int turns, ArrayList<String> decisions, String specialMessage){
        System.out.println("You Died. Cause of death: " + causeOfDeath + "." + "You made it " + turns  + " turns.");
        System.out.println("Your decisions made were: " + decisions.toString());
    }
    public static void turnIterator(HashMap<String,Integer>inventory){
        inventory.put("Decisions",inventory.get("Decisions")+1);
    }


}