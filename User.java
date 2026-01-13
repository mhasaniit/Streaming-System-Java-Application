public class User implements Playable {
    private static int nextID = 1;
    private String username;
    private int userID;
    private Playlist playlist = new Playlist();

    public User(String username) {
        this.username = username;
        this.userID = nextID++;
    }

    public int getUserID() { return userID; }
    public String getUsername() { return username; }

    @Override
    public void play() {
        playlist.playAll();
    }

    // Delegate playlist operations
    public void addRecording(Recording r) {
        playlist.addRecording(r);
    }
    public void addFromFile(String fname) {
        playlist.addFromFile(fname);
    }
    public void addFromUser(User other) {
        playlist.addFromPlaylist(other.playlist);
    }
    public void removeByIndex(int i) {
        playlist.removeRecordingByIndex(i);
    }
    public void removeByName(String name) {
        playlist.removeRecordingByName(name);
    }
    public void playByIndex(int i) {
        playlist.playRecordingByIndex(i);
    }
    public void playByName(String name) {
        playlist.playByName(name);
    }
    public void playAll() {
        playlist.playAll();
    }
    public void shuffle() {
        playlist.shuffle();
    }
    public void savePlaylist() {
        playlist.save(username);
    }
    public void displayStats() {
        playlist.displayStats();
    }
}
