import java.math.BigDecimal;

public class CheckingAccount extends Account {
    public CheckingAccount(BigDecimal bal, String firstName, String lastName) {
        setBalance(bal);
        setFirstName(firstName);
        setLastName(lastName);
    }

    @Override
    public void deposit(BigDecimal in) {
        setBalance(getBalance().add(in));
    }
}
