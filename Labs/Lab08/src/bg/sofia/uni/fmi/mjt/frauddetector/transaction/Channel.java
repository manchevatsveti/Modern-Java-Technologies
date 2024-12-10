package bg.sofia.uni.fmi.mjt.frauddetector.transaction;

public enum Channel {
    ATM, ONLINE, BRANCH;

    public static Channel of(String line) {
        return switch (line.toUpperCase()) {
            case "ATM" -> ATM;
            case "ONLINE" -> ONLINE;
            case "BRANCH" -> BRANCH;
            default -> throw new IllegalArgumentException("Unknown channel: " + line);
        };
    }
}
