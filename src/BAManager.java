import java.text.NumberFormat;
import java.util.*;
import java.math.BigDecimal;

public class BAManager {
    public static void main(String[] args) {
        List<Account> accounts = initaliseAccounts();
        runATM(accounts);
    }

    public static void runATM(List<Account> accounts) {
        ATM(accounts);
        anythingElse(accounts);
    }

    public static List<Account> initaliseAccounts() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your first name >> ");
        String firstName = scanner.nextLine();

        System.out.println("Enter your last name >> ");
        String lastName = scanner.nextLine();

        //Initialising bank balances
        System.out.println("Initialising bank accounts...");
        System.out.println();

        BigDecimal businessBal, checkingBal, savingsBal;

        while (true) {
            System.out.println("What is the balance of your Business Account? >> ");
            String input = scanner.nextLine();
            try {
                businessBal = new BigDecimal(input);
                break;
            }
            catch (NumberFormatException nfe){
                System.out.println("Invalid input, please try again.");
            }
        }

        while (true) {
            System.out.println("What is the balance of your Checking Account? >> ");
            String input = scanner.nextLine();
            try {
                checkingBal = new BigDecimal(input);
                break;
            }
            catch (NumberFormatException nfe){
                System.out.println("Invalid input, please try again.");
            }
        }

        while (true) {
            System.out.println("What is the balance of your Savings Account? >> ");
            String input = scanner.nextLine();
            try {
                savingsBal = new BigDecimal(input);
                break;
            }
            catch (NumberFormatException nfe){
                System.out.println("Invalid input, please try again.");
            }
        }

        Account b = new BusinessAccount(businessBal, firstName, lastName);
        Account c = new CheckingAccount(checkingBal, firstName, lastName);
        Account s = new SavingsAccount(savingsBal, firstName, lastName);

        List<Account> accountsList = new ArrayList<>();
        accountsList.add(b);
        accountsList.add(c);
        accountsList.add(s);

        return accountsList;
    } //fine

    public static void ATM(List<Account> accounts) {
        boolean ready = transaction();
        if (!ready) {
            return;
        }
        int accountIndex = chooseAccount();
        Account selectedAcc = accounts.get(accountIndex);
        chooseOperation(selectedAcc, accounts);
    }

    public static boolean transaction() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Would you like to make a transaction? Y/N >> ");
            String confirmation = scanner.nextLine();
            switch (confirmation.toLowerCase()) {
                case "y": return true;
                case "n":
                    System.out.println("Thank you, goodbye!");
                    scanner.close();
                    return false;
                default:
                    System.out.println("Please enter a valid input.");
            }
        }
    } //fine

    public static int chooseAccount () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose 1 to access your Business Account");
        System.out.println("Choose 2 to access your Checking Account");
        System.out.println("Choose 3 to access your Savings Account");

        while (true) {
            System.out.println("Enter 1, 2 or 3 >> ");
            String input = scanner.nextLine();
            int num;
            try {
                num = Integer.parseInt(input);
                switch (num){
                    case 1: return 0;
                    case 2: return 1;
                    case 3: return 2;
                    default:
                        System.out.println("Invalid input, please try again.");
                }
            }
            catch (NumberFormatException nfe) {
                System.out.println("Invalid input, please try again.");
            }
        }
    } //fine

    public static void chooseOperation(Account acc, List<Account> list) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose 1 to Withdraw");
        System.out.println("Choose 2 to Deposit");
        System.out.println("Choose 3 to Check Balance");
        System.out.println("Choose 4 to Transfer Between Accounts");
        System.out.println("Choose 5 to EXIT");

        loop: while (true) {
            System.out.println("Enter a number from 1 to 5 >> ");
            String input = scanner.nextLine();
            int num;
            try {
                num = Integer.parseInt(input);
                switch (num) {
                    case 1: withdraw(acc, list);
                        break loop;
                    case 2: deposit(acc);
                        break loop;
                    case 3: checkBalance(acc);
                        break loop;
                    case 4: transfer(otherAcc(acc, list), list);
                        break loop;
                    case 5:
                        System.out.println("Exiting main menu...");
                        break loop;
                    default:
                        System.out.println("Invalid input, please try again.");
                }
            }
            catch (NumberFormatException nfe) {
                System.out.println("Invalid input, please try again.");
            }
        }
    }

    public static Map<String, Account> otherAcc(Account acc, List<Account> list) {
        List<Account> copy = new ArrayList<>(list);
        copy.remove(acc);
        Map<String, Account> transferAccs = new HashMap<>();
        Map<String, Account> otherAccs = new HashMap<>();
        List<String> otherAccsStrings = new ArrayList<>();
        for (Account account: copy) {
            if (account instanceof BusinessAccount) {
                otherAccs.put("Business Account", account);
                otherAccsStrings.add("Business Account");
            }
            else if (account instanceof CheckingAccount) {
                otherAccs.put("Checking Account", account);
                otherAccsStrings.add("Checking Account");
            }
            else if (account instanceof SavingsAccount) {
                otherAccs.put("Savings Account", account);
                otherAccsStrings.add("Savings Account");
            }
        }

        String accString;
        if (acc instanceof BusinessAccount) {
            accString = "Business Account";
            transferAccs.put("Business Account", acc);
        }
        else if (acc instanceof CheckingAccount) {
            accString = "Checking Account";
            transferAccs.put("Checking Account", acc);
        }
        else {
            accString = "Savings Account";
            transferAccs.put("Savings Account", acc);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("You are currently accessing your "+accString);
        System.out.println("Choose 1 to transfer between your "+accString+" and your "+otherAccsStrings.get(0));
        System.out.println("Choose 2 to transfer between your "+accString+" and your "+otherAccsStrings.get(1));

        while (true) {
            System.out.println("Enter 1 or 2 >> ");
            String input = scanner.nextLine();
            int num;
            try {
                num = Integer.parseInt(input);
                switch (num) {
                    case 1: transferAccs.put(otherAccsStrings.get(0), otherAccs.get(otherAccsStrings.get(0)));
                        return transferAccs;
                    case 2: transferAccs.put(otherAccsStrings.get(1), otherAccs.get(otherAccsStrings.get(1)));
                        return transferAccs;
                    default:
                        System.out.println("Invalid input, please try again.");
                }
            }
            catch (NumberFormatException nfe) {
                System.out.println("Invalid input, please try again.");
            }
        }
    }

    public static void deposit(Account acc) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How much money do you wish to deposit?");

        BigDecimal deposit;
        while (true) {
            System.out.println("Enter the deposit amount >> ");
            String input = scanner.nextLine();
            try {
                deposit = new BigDecimal(input);
                break;
            }
            catch (NumberFormatException nfe){
                System.out.println("Invalid input, please try again.");
            }
        }

        acc.deposit(deposit);
        System.out.println("Your new balance is "+ NumberFormat.getCurrencyInstance().format(acc.getBalance()));
    } //fine

    public static void withdraw(Account acc, List<Account> accs) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How much money do you wish to withdraw?");

        BigDecimal withdrawal;
        loop2: while (true) {
            System.out.println("Enter the withdrawal amount >> ");
            String input = scanner.nextLine();
            try {
                withdrawal = new BigDecimal(input);
                acc.withdraw(withdrawal);
                break;
            }
            catch (NumberFormatException nfe) {
                System.out.println("Invalid input, please try again.");
            }
            catch (Exception e) {
                System.out.println("Please ensure you have enough funds to cover the withdrawal.");
                System.out.println("Do you still wish to withdraw?");
                loop1: while (true) {
                    System.out.println("Enter Y/N");
                    String confirmation = scanner.nextLine();
                    switch (confirmation.toLowerCase()) {
                        case "y": continue loop2;
                        case "n": anythingElse(accs);
                            break loop1;
                        default:
                            System.out.println("Invalid input, please try again.");
                    }
                }
            }
        }

        System.out.println("Your new balance is "+ NumberFormat.getCurrencyInstance().format(acc.getBalance()));
    }

    public static void checkBalance(Account acc) {
        System.out.println(acc.getFirstName()+" "+ acc.getLastName()+", your current balance is " + NumberFormat.getCurrencyInstance().format(acc.getBalance()));
    } //fine

    public static void transfer(Map<String, Account> transferAccs, List<Account> accs) {
        Scanner scanner = new Scanner(System.in);
        String[] nameArr = new String[2];
        Account[] accArr = new Account[2];
        int i = 0;
        for (Map.Entry<String, Account> acc: transferAccs.entrySet()) {
            nameArr[i] = acc.getKey();
            accArr[i] = acc.getValue();
            i++;
        }
        System.out.println("Please confirm that you wish to transfer between your "+nameArr[0]+" and your "+nameArr[1]);
        while (true) {
            System.out.println("Confirmation Y/N >> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("y")) {
                break;
            }
            else if (input.equalsIgnoreCase("n")) {
                System.out.println("Returning to the main menu...");
                scanner.close();
                ATM(accs);
            }
            else {
                System.out.println("Invalid input, please try again.");
            }
        }

        Account minusAcc, plusAcc;

        System.out.println("Which direction do you want to transfer in?");
        System.out.println("Choose 1 to transfer from your "+nameArr[0]+" to your "+nameArr[1]);
        System.out.println("Choose 2 to transfer from your "+nameArr[1]+" to your "+nameArr[0]);

        loop: while (true) {
            System.out.println("Enter 1 or 2 >> ");
            String input = scanner.nextLine();
            int num;
            try {
                num = Integer.parseInt(input);
                switch (num) {
                    case 1: minusAcc = accArr[0];
                        plusAcc = accArr[1];
                        break loop;
                    case 2: minusAcc = accArr[1];
                        plusAcc = accArr[0];
                        break loop;
                    default:
                        System.out.println("Invalid input, please try again.");
                }
            }
            catch (NumberFormatException nfe) {
                System.out.println("Invalid input, please try again.");
            }
        }

        System.out.println("How much do you wish to transfer?");
        BigDecimal transfer;
        loop2: while (true) {
            System.out.println("Enter the transfer amount >> ");
            String input = scanner.nextLine();
            try {
                transfer = new BigDecimal(input);
                minusAcc.withdraw(transfer);
                break;
            }
            catch (NumberFormatException nfe) {
                System.out.println("Invalid input, please try again.");
            }
            catch (Exception e) {
                System.out.println("Please ensure you have enough funds to cover the transfer.");
                System.out.println("Do you still wish to make a transfer?");
                loop1: while (true) {
                    System.out.println("Enter Y/N");
                    String confirmation = scanner.nextLine();
                    switch (confirmation.toLowerCase()) {
                        case "y": continue loop2;
                        case "n": anythingElse(accs);
                            break loop1;
                        default:
                            System.out.println("Invalid input, please try again.");
                    }
                }
            }
        }

        plusAcc.deposit(transfer);
        System.out.println("The new balance of your "+nameArr[0]+" is "+NumberFormat.getCurrencyInstance().format(accArr[0].getBalance()));
        System.out.println("The new balance of your "+nameArr[1]+" is "+NumberFormat.getCurrencyInstance().format(accArr[1].getBalance()));
    }

    public static void anythingElse(List<Account> accs) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to do anything else?");
        loop: while (true) {
            System.out.println("Enter Y/N >> ");
            String input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case "y": runATM(accs);
                break loop;
                case "n":
                    System.out.println("Thanks, goodbye!");
                    break loop;
                default:
                    System.out.println("Invalid input, please try again.");
            }
        }
    }
}
