import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Playlist implements Playable {
    private List<Recording> recordings = new ArrayList<>();

    public void addRecording(Recording r) {
        if (recordings.contains(r)) return;
        recordings.add(r);
    }

    public void addFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;
                String artist = parts[0].trim();
                String name   = parts[1].trim();
                String durStr = parts[2].trim();
                int duration;
                if (durStr.contains(":")) {
                    String[] t = durStr.split(":");
                    duration = Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);
                } else {
                    duration = Integer.parseInt(durStr);
                }
                addRecording(new AudioRecording(artist, name, duration));
            }
        } catch (IOException e) {
            System.err.println("Failed to load playlist file: " + e.getMessage());
        }
    }

    public void addFromPlaylist(Playlist other) {
        for (Recording r : other.recordings) {
            addRecording(r);
        }
    }

    public void removeRecordingByIndex(int idx) {
        if (idx >= 0 && idx < recordings.size()) {
            recordings.remove(idx);
        }
    }

    public void removeRecordingByName(String name) {
        recordings.removeIf(r -> r.getName().equalsIgnoreCase(name));
    }

    public void playRecordingByIndex(int idx) {
        try {
            recordings.get(idx).play();
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Invalid index");
        } catch (UnplayableException e) {
            System.err.println(e.getMessage());
        }
    }

    public void playByName(String name) {
        for (Recording r : recordings) {
            if (r.getName().equalsIgnoreCase(name)) {
                try {
                    r.play();
                } catch (UnplayableException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void play() {
        playAll();
    }

    public void playAll() {
        for (Recording r : recordings) {
            try {
                r.play();
            } catch (UnplayableException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void shuffle() {
        List<Recording> copy = new ArrayList<>(recordings);
        Collections.shuffle(copy);
        for (Recording r : copy) {
            try {
                r.play();
            } catch (UnplayableException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void save(String username) {
        String timestamp = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss").format(new Date());
        String fname = username + "_PLAYLIST_" + timestamp + ".csv";
        try (PrintWriter pw = new PrintWriter(new FileWriter(fname))) {
            pw.println("Artist,Name,Duration");
            for (Recording r : recordings) {
                pw.printf("%s,%s,%d%n",
                    r.getArtist(),
                    r.getName(),
                    r.getDuration()
                );
            }
            System.out.println("Playlist saved to " + fname);
        } catch (IOException e) {
            System.err.println("Error saving playlist: " + e.getMessage());
        }
    }

    public void displayStats() {
        for (Recording r : recordings) {
            System.out.printf("%s – %s – plays: %d%n",
                r.getArtist(), r.getName(), r.getPlayCount());
        }
    }
}
