package com.kannanrameshrk;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Restaurant {
	private Stack<SelectedOptions> backStack = new Stack<>();
	
	public static void main(String[] args) {
		Restaurant stackExample = new Restaurant();
		stackExample.start();
	}
	
	private void start() {
		int choice;
		backStack.push(new SelectedOptions(0,0) );
		System.out.println("*+---Tamil Mersal Restaurant-----+*");
		print(0,0);
		
		do {
			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			System.out.println("------------------");
			System.out.println("Enter Your Option:");
			choice = input.nextInt();
			
			if(choice == 9) {
				backStack.pop();
				
				if(backStack.empty()) {
					break;
				}
			}else {
				backStack.push(new SelectedOptions(backStack.peek().getStep()+1,choice));
			}
			SelectedOptions top = backStack.peek();
			print(top.getStep(), top.getChoice());
		}
		while(choice!=0);
		System.out.println("Thank you for visiting our website...");
		
	}


	private void print(int step, int choice) {
		for (String choiceString : getChoices(step, choice)) {
			System.out.println(choiceString);
		}
	}
	
	private ArrayList<String> getChoices(int step, int choice) {
        ArrayList<String> choiceList = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream("item.json");
             Scanner scanner = new Scanner(inputStream, "UTF-8")) {

            @SuppressWarnings("resource")
			String jsonContent = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
            
            JSONParser parser = new JSONParser();
            JSONObject choicesObject = (JSONObject) parser.parse(jsonContent);

            if (choicesObject.containsKey(String.valueOf(step))) {
                JSONObject levelObject = (JSONObject) choicesObject.get(String.valueOf(step));

                if (levelObject.containsKey(String.valueOf(choice))) {
                    Object optionsObject = levelObject.get(String.valueOf(choice));
                   // System.out.println("Step: " + step + ", Choice: " + choice);
                   // System.out.println("Options Object: " + optionsObject);
                    if (optionsObject instanceof JSONArray) {
                        choiceList.addAll((JSONArray) optionsObject);
                    } else if (optionsObject instanceof JSONObject) {
                        choiceList.addAll(((JSONObject) optionsObject).values());
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

        if (step == 3) {
            System.out.println("Your Option set Successfully");
            System.out.println("9-Back");
            System.out.println("0-Exit");
        }

        return choiceList;
    }


}
