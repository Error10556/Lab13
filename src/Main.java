import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.ArrayList;
import java.util.Date;

abstract class Media {
    public abstract String mediaKind();

    private final String name;

    public String getName() {
        return name;
    }

    public Media(String name) {
        this.name = name;
    }
}

class Book extends Media {
    @Override
    public String mediaKind() {
        return mediaKindName();
    }

    public static String mediaKindName() {
        return "Book";
    }
    private final String authors;

    private final int pageCount;

    public Book(String name, String authors, int pageCount) {
        super(name);
        this.authors = authors;
        this.pageCount = pageCount;
    }

    public String getAuthors() {
        return authors;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public String toString() {
        return String.format("Book \"%s\" by %s: %d pages", getName(), authors, pageCount);
    }
}

class Video extends Media {
    @Override
    public String mediaKind() {
        return mediaKindName();
    }

    public static String mediaKindName() {
        return "Video";
    }

    private final long duration;

    public Video(String title, long duration) {
        super(title);
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public static String durationToString(long duration) {
        int s = (int)(duration % 60);
        duration /= 60;
        int m = (int)duration % 60;
        duration /= 60;
        if (duration != 0)
            return String.format("%s:%d:%d", Long.toString(duration), m, s);
        return String.format("%d:%d", m, s);
    }

    @Override
    public String toString() {
        return String.format("Video \"%s\" (%s)", getName(), durationToString(duration));
    }
}

class Newspaper extends Media {
    @Override
    public String mediaKind() {
        return mediaKindName();
    }

    public static String mediaKindName() {
        return "Newspaper";
    }

    private final Date issue;

    public Newspaper(String title, Date issued) {
        super(title);
        issue = issued;
    }

    public Date getIssueDate() {
        return issue;
    }

    @Override
    public String toString() {
        return String.format("Newspaper (%s): \"%s\"", issue, getName());
    }
}

class GenericLibrary<T extends Media> {
    private final ArrayList<T> list = new ArrayList<>();

    public ArrayList<T> searchByName(String name) {
        ArrayList<T> res = new ArrayList<>();
        for (T t : list) {
            if (t.getName().compareToIgnoreCase(name) == 0) {
                res.add(t);
            }
        }
        return res;
    }

    public void add(T item) {
        list.add(item);
    }

    public boolean remove(T item) {
        return list.remove(item);
    }
}

class NonGenericLibrary {
    ArrayList<Media> list = new ArrayList<>();

    private final String type;

    public NonGenericLibrary(String mediaKindName) {
        type = mediaKindName;
    }

    public ArrayList<Media> searchByName(String name) {
        ArrayList<Media> res = new ArrayList<>();
        for (Media t : list) {
            if (t.getName().compareToIgnoreCase(name) == 0) {
                res.add(t);
            }
        }
        return res;
    }

    private void assertType(Media media) throws ValueException {
        if (media.mediaKind().compareTo(type) == 0)
            throw new ValueException(String.format("Expected %s type, got %s", type, media.mediaKind()));
    }

    public void add(Media media) throws ValueException {
        assertType(media);
        list.add(media);
    }

    public boolean remove(Media media) throws ValueException {
        assertType(media);
        return list.remove(media);
    }

    public String getMediaKindName() {
        return type;
    }
}

public class Main {
    public static void main(String[] args) {

    }
}