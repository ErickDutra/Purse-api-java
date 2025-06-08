package purse.coin.purse.model.enums;

public enum TransactionStatus {
      PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    REJECTED("REJECTED");

    private final String value;

    TransactionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
