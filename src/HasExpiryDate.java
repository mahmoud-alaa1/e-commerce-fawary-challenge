import java.time.LocalDate;

public class HasExpiryDate implements Expirable {
    private LocalDate expiryDate;

    public HasExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }
}
