package purse.coin.purse.model.enums;

public enum ValueTax {
     VALUE_TAX("0.01");

    private final String value;
    ValueTax(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
