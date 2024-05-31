import java.math.BigDecimal;

public class SavingsAccount extends Account {
    public SavingsAccount(BigDecimal bal, String firstName, String lastName) {
        setBalance(bal);
        setFirstName(firstName);
        setLastName(lastName);
    }

    @Override
    public void deposit(BigDecimal in) {
        setBalance(getBalance().add(in));
    }
}
