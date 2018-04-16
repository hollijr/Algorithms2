package test;

public enum Priority {

    HIGH(1, "Mandatory"),
    MEDIUM(2, "Important"),
    LOW(3, "Nice to Have"); // must end with semi-colon

    // an enum is a class that can include methods and fields
    // they must be defined after the constants are declared.
    private final int numRep;
    private final String desc;

    // constants are automatically constructed by the compiler
    // we need our own constructor for the other fields
    // our constructor must be either package-private or private
    Priority(int num, String desc) {
        this.numRep = num;
        this.desc = desc;
    }

    int getNumericPriority() {
        return numRep;
    }

    String getDesc() {
        return desc;
    }
}
