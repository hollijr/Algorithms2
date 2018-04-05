public class TestHashMap {

    public static void main(String[] args) {
        Flights[] flights = new Flights[5];
        flights[0] = new Flights(123, "DTW", "SEA");
        flights[1] = new Flights(456, "SEA", "DTW");

    }

    // TODO: add static
    private static class Flights {
        private int flightNo;
        private String destination;
        private String origination;

        public Flights(int flightNo, String dest, String orig) {
            this.flightNo = flightNo;
            this.destination = dest;
            this.origination = orig;
        }

        public String toString() {
            return "Flight #" + flightNo + " to " + destination + " from " + origination;
        }
    }
}
