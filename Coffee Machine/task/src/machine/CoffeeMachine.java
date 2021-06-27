package machine;

import java.util.Scanner;
enum CoffeeTypes {
    ESPRESSO("espresso",250,0,16,4),
    LATTE("latte",350,75,20,7),
    CAPPUCCINO("cappuccino",200,100,12,6),
    UNKNOWN("unknown",0,0,0,0);

    String name;
    int water;
    int milk;
    int beans;
    int cost;

    CoffeeTypes(String name, int water, int milk, int beans, int cost) {
        this.name = name;
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getBeans() {
        return beans;
    }

    public int getCost() {
        return cost;
    }
}
enum MachineMode {
    WAITFORCOMMAND,
    BUY,
    FILL,
    TAKE,
    REMAINING,
    EXIT
}
class CoffeeDevice {
    private int waterAvailable;
    private int milkAvailable;
    private int beansAvailable;
    private int moneyAvailable;
    private int cupsAvailable;
    String actionvalue;
    int step;
    private MachineMode machineMode;
    public CoffeeDevice() {
        machineMode = machineMode.WAITFORCOMMAND;
        waterAvailable = 400;
        milkAvailable = 540;
        beansAvailable = 120;
        moneyAvailable = 550;
        cupsAvailable = 9;
        step = 0;
    }
    public int getStep() {
        return step;
    }
    public MachineMode getMachineMode() {
        return machineMode;
    }
    private void showState() {
        StringBuilder sb = new StringBuilder("");
        sb.append("The coffee machine has:\n");
        sb.append( waterAvailable + " ml of water\n");
        sb.append( milkAvailable + " ml of milk\n");
        sb.append(beansAvailable +" g of coffee beans\n");
        sb.append(cupsAvailable + " disposable cups\n");
        sb.append("$" + moneyAvailable + " of money\n");
        System.out.println(sb.toString());
    }
    private void fillMachine() {
        switch (step) {
            case 0: {
                System.out.println("Write how many ml of water you want to add:");
                break;
            }
            case 1: {
                waterAvailable = waterAvailable + Integer.parseInt(actionvalue);
                System.out.println("Write how many ml of milk you want to add:");
                break;
            }
            case 2: {
                milkAvailable = milkAvailable + Integer.parseInt(actionvalue);
                System.out.println("Write how many grams of coffee beans you want to add:");
                break;
            }
            case 3: {
                beansAvailable = beansAvailable + Integer.parseInt(actionvalue);
                System.out.println("Write how many disposable cups of coffee you want to add:");
                break;
            }
            case 4: {
                cupsAvailable = cupsAvailable + Integer.parseInt(actionvalue);
                break;
            }
            default: {
                break;
            }

        }
        step += 1;
        if (step > 4) {
            step = 0;
            machineMode = MachineMode.WAITFORCOMMAND;
        }
    }
    private void buyCoffee() {
        machineMode = MachineMode.WAITFORCOMMAND;
        CoffeeTypes selectedCoffee;
        switch (actionvalue) {
            case "1": {
                selectedCoffee = CoffeeTypes.ESPRESSO;
                break;
            }
            case "2": {
                selectedCoffee = CoffeeTypes.LATTE;
                break;
            }
            case "3": {
                selectedCoffee = CoffeeTypes.CAPPUCCINO;
                break;
            }
            default: {
                selectedCoffee = CoffeeTypes.UNKNOWN;
                return;
            }
        }
        if (cupsAvailable <= 0) {
            System.out.println("Sorry, not enough disposable cups!");
            return;
        }
        if (waterAvailable < selectedCoffee.getWater()) {
            System.out.println("Sorry, not enough water!");
            return;
        }
        if (milkAvailable < selectedCoffee.getMilk() ) {
            System.out.println("Sorry, not enough milk!");
            return;
        }
        if (beansAvailable < selectedCoffee.getBeans()) {
            System.out.println("Sorry, not enough beans!");
            return;
        }
        System.out.println("I have enough resources, making you a coffee!");
        cupsAvailable -= 1;
        waterAvailable -= selectedCoffee.getWater();
        milkAvailable -= selectedCoffee.getMilk();
        beansAvailable -= selectedCoffee.getBeans();
        moneyAvailable += selectedCoffee.getCost();
    }

    public void setCommand(String command) {
        switch (machineMode)  {
            case WAITFORCOMMAND: {
                switch (command) {
                    case "buy": {
                        machineMode = MachineMode.BUY;
                        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
                        break;
                    }
                    case "fill": {
                        machineMode = MachineMode.FILL;
                        break;
                    }
                    case "take": {
                        machineMode = MachineMode.TAKE;
                        System.out.println("I gave you $" + moneyAvailable);
                        moneyAvailable = 0;
                        machineMode = MachineMode.WAITFORCOMMAND;
                        break;
                    }
                    case "remaining": {
                        machineMode = MachineMode.TAKE;
                        showState();
                        machineMode = MachineMode.WAITFORCOMMAND;
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case BUY: {
                actionvalue = command;
                buyCoffee();
                break;
            }
            case FILL: {
                actionvalue = command;
                fillMachine();
                break;
            }
            default: {
                break;
            }
        }

    }
}
public class CoffeeMachine {
    final public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        CoffeeDevice coffeDevice = new CoffeeDevice();
        String action = "-1";
        while (!"exit".equals(action)) {
            if (coffeDevice.getMachineMode() == MachineMode.WAITFORCOMMAND) {
                System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
                action = scanner.next();
            }
            if (coffeDevice.getMachineMode() == MachineMode.BUY) {
                action = scanner.next();
            }
            if (coffeDevice.getMachineMode() == MachineMode.FILL) {
                if (coffeDevice.getStep() > 0 ) {
                    action = scanner.next();
                }
            }
            coffeDevice.setCommand(action);

        }
    }
}