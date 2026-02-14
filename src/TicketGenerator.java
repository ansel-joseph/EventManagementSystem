class TicketGenerator {
    private static int counter = 1000;
    public static synchronized int generateTicket() {
        return counter++;
    }
}
