package ru.otus.l071;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ATMImpl implements ATM{
    private Human human;
    private Controller controller;

    public ATMImpl() {
        human = new HumanImpl();
        controller = new ControllerImpl();
    }

    @Override
    public void run() throws IOException {
        human.draw("Welcome %user%\n" +
                "Type 1/summ to get money\n" +
                "Type 2/summ to put money\n" +
                "Type 3/0 to get balance\n");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = in.readLine()) != null && s.length() != 0) {
            Pattern pattern = Pattern.compile("(^\\d)(\\/)(\\d*)$");
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                if(matcher.group(1).equals("1")){
                    if(controller.get(human.issue(matcher.group(3)))){
                        human.draw("Complete");
                    }else{
                        human.draw("Not enough");
                    };
                }
                if(matcher.group(1).equals("2")){
                    if(controller.put(human.issue(matcher.group(3)))){
                        human.draw("Complete");
                    }
                }
                if(matcher.group(1).equals("3")){
                    human.draw("Balance is: " + human.issue(controller.getBalance()));
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ATMImpl().run();
    }
}
