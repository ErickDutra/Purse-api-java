package purse.coin.purse.integration.dto;

public record TransectionsDto(
    String sender,
    String receiver,
    String transactionID,
    String signature,
    String type,
    String status,
    String timestamp
) {
    
}
