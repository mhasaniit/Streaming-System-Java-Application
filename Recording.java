import java.util.Objects;

public abstract class Recording implements Playable {
    protected String artist;
    protected String name;
    protected int duration;     // in seconds
    protected int playCount = 0;

    public Recording(String artist, String name, int duration) {
        this.artist = artist;
        this.name = name;
        this.duration = duration;
    }

    @Override
    public void play() throws UnplayableException {
        if (duration <= 0) {
            throw new UnplayableException("Recording \"" + name + "\" is unplayable (duration = " + duration + ").");
        }
        playCount++;
        System.out.println("Playing: " + artist + " - " + name + " (" + duration + "s)");
    }

    public String getArtist() { return artist; }
    public String getName()   { return name; }
    public int    getDuration() { return duration; }
    public int    getPlayCount() { return playCount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recording)) return false;
        Recording that = (Recording) o;
        return Objects.equals(artist, that.artist)
            && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, name);
    }
}
