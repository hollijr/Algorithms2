package test;

import hashmap.MyHashMap;

public class TestHashMap {

    public static void main(String[] args) {
        Flight[] flights = new Flight[8];
        flights[0] = new Flight(123, "DTW", "SEA");
        flights[1] = new Flight(456, "SEA", "DTW");
        flights[2] = new Flight(333, "FLG", "SEA");
        flights[3] = new Flight(444, "SEA", "FLG");
        flights[4] = new Flight(555, "LAX", "SEA");
        flights[5] = new Flight(666, "SEA", "LAX");
        flights[6] = new Flight(777, "SFO", "SEA");
        flights[7] = new Flight(888, "SEA", "SFO");

        MyHashMap<Integer, Flight> hashTbl = new MyHashMap<>();

        for (int i = 0; i < 4; i++) {
            System.out.println("Adding " + flights[i]);
            hashTbl.add(flights[i].flightNo, flights[i]);
        }
        System.out.println();

        for (Flight flight : flights) {
            System.out.println(hashTbl.find(flight.flightNo));
        }
        System.out.println();
        System.out.println("Table size: " + hashTbl.size());

    }

    private static class Flight {
        private int flightNo;
        private String destination;
        private String origination;

        public Flight(int flightNo, String dest, String orig) {
            this.flightNo = flightNo;
            this.destination = dest;
            this.origination = orig;
        }

        public String toString() {
            return "Flight #" + flightNo + " to " + destination + " from " + origination;
        }
    }
}
