class Participant extends User {
    private int ticketId;
    public Participant(String name, String email, int ticketId) {
        super(name, email);
        this.ticketId = ticketId;
    }
    public int getTicketId() { return ticketId; }
    public String getRole() { return "Participant"; }
}
