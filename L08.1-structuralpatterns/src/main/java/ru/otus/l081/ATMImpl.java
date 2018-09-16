package ru.otus.l081;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ATMImpl implements ATMObserver,ATM {
    private Controller controller;
    private BigDecimal startBalance;

    public Controller getController() {
        return controller;
    }

    public ATMImpl() {
        controller = new ControllerImpl();
        startBalance = new BigDecimal(0);
    }

    @Override
    public void run() throws IOException {
        System.out.println("Welcome %user%\n" +
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
                    Map<NOTESVALUE,Integer> result = controller.get(Converter.toBigDecimal(matcher.group(3)));
                    if(result!=null){
                        System.out.println("Please take");
                        result.entrySet().stream().filter(x->x.getValue()>0).forEach(System.out::println);
                    }else{
                        System.out.println("Not enough");
                    };
                }
                if(matcher.group(1).equals("2")){
                    if(controller.put(Converter.toBigDecimal(matcher.group(3)))){
                        System.out.println("Complete");
                    }
                }
                if(matcher.group(1).equals("3")){
                    System.out.println("Balance is: " + controller.getBalance().toString());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ATMImpl().run();
    }

    @Override
    public BigDecimal sendBalance() {
        return controller.getBalance();
    }

    @Override
    public void updateBalance(String balance) {
        controller.put(Converter.toBigDecimal(balance));
        startBalance = Converter.toBigDecimal(balance);
    }

    @Override
    public void resetBalance() {
        controller.get(controller.getBalance());
        updateBalance(this.startBalance.toString());
    }
}
