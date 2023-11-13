package com.kannanrameshrk;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.Map;

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

	           StringBuilder jsonContentBuilder = new StringBuilder();
	           while (scanner.hasNextLine()) {
	               jsonContentBuilder.append(scanner.nextLine());
	           }

	           String jsonContent = jsonContentBuilder.toString();
	          

	           org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
	           Object obj = parser.parse(jsonContent);
	           org.json.simple.JSONObject choicesObject = (org.json.simple.JSONObject) obj;


	        if (choicesObject.get(String.valueOf(step)) instanceof org.json.simple.JSONObject) {
	            org.json.simple.JSONObject levelObject = (org.json.simple.JSONObject) choicesObject.get(String.valueOf(step));

	            if (levelObject.get(String.valueOf(choice)) != null) {
	                Object optionsObject = levelObject.get(String.valueOf(choice));

	                if (optionsObject instanceof org.json.simple.JSONArray) {
	                    org.json.simple.JSONArray optionsArray = (org.json.simple.JSONArray) optionsObject;
	                    for (Object option : optionsArray) {
	                        choiceList.add((String) option);
	                    }
	                } else if (optionsObject instanceof org.json.simple.JSONObject) {
	                    org.json.simple.JSONObject optionsObjectJson = (org.json.simple.JSONObject) optionsObject;
	                    @SuppressWarnings({ "rawtypes", "unchecked" })
						Set<Map.Entry> entrySet = optionsObjectJson.entrySet();
	                    for (@SuppressWarnings("rawtypes") Map.Entry entry : entrySet) {
	                        choiceList.add((String) entry.getValue());
	                    }
	                }
	            }
	        }

	    } catch (Exception e) {
	    	System.out.println(e);
	        e.printStackTrace();
	    }
	    if (step == 3) {
			System.out.println("Your Option set Sucessfully");
			System.out.println("9-Back");
			System.out.println("0-Exit");
		}
	    
	    return choiceList;
	}


}
