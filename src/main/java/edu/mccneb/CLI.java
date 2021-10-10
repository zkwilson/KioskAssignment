package edu.mccneb;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CLI {
    public static Scanner input = new Scanner(System.in);

    public int getTrackFromUser(){
        boolean result = true;
        int trackId = 0;
        while(result){
            try{
                System.out.print("\nSelect track by track id to add to your playlist:  ");
                trackId = input.nextInt();
                System.out.println();
                if(trackId < 1 || trackId > 3503){
                    System.out.println("Invalid track ID");
                }
                else {
                    result = false;
                }
            }catch (InputMismatchException e) {
                System.out.println("\nInvalid track ID");
                input.nextLine();
            }
        }
        return trackId;
    }

    public boolean continueAddingTracks() {
        boolean result = true;
        boolean loopAgain = true;
        while(loopAgain) {
            System.out.print("Would you like to add another track? (y/n):  ");
            String userInput = input.next();
            if (userInput.equalsIgnoreCase("y")) {
                loopAgain = false;
            } else if (userInput.equalsIgnoreCase("n")) {
                result = false;
                loopAgain = false;
            } else {
                System.out.println("\nInvalid input\n");
            }
        }
        return result;
    }
}
