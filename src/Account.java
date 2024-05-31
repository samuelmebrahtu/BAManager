import java.math.BigDecimal;
import java.text.NumberFormat;

public abstract class Account {
    private BigDecimal balance;
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal bal) {
        balance = bal;
    }
    private String firstName, lastName;
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public abstract void deposit(BigDecimal in);

    public void withdraw(BigDecimal out) throws Exception {
        if (out.compareTo(getBalance())> 0) {
            System.out.println("Your current balance is "+ NumberFormat.getCurrencyInstance().format(getBalance()));
            throw new Exception();
        }
        setBalance(getBalance().subtract(out));
    }
}
