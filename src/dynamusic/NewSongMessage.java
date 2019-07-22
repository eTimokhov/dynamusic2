package dynamusic;

import java.io.Serializable;

public class NewSongMessage implements Serializable {

    private String songId;
    private String songGenre;
    private String title;

    public NewSongMessage(String songId, String songGenre, String title) {
        this.songId = songId;
        this.songGenre = songGenre;
        this.title = title;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongGenre() {
        return songGenre;
    }

    public void setSongGenre(String songGenre) {
        this.songGenre = songGenre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
