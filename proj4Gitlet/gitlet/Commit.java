package gitlet;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

/**
 * Represents a gitlet commit object.
 * <p>
 * does at a high level.
 * this class contains metadata about one commit  ,
 * 'pointer' to parent commit
 * *  @author JasonJ2021
 */
public class Commit implements Serializable {
    /**
     *
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    private String message;
    private Date date;
    private TreeMap<String, String> blobs; //filename : filesha
    private String parent; //Parent commit 's SHA-1 code ;
    private String otherParent;
    private boolean hasOtherParent;

    public Commit(String message) {
        Commit head = Repository.getHeadCommit();
        this.message = message;
        this.parent = Utils.sha1(Utils.serialize(head));
        blobs = new TreeMap<>();
        blobs.putAll(head.blobs);
        this.date = new Date();
    }

    public String getOtherParent() {
        return otherParent;
    }

    //Create mergeCommit ,
    public Commit(String parentSha, String otherParentSha, String givenBName, String curBName) {
        //Merged [given branch name] into [current branch name].
        Commit head = Repository.getHeadCommit();
        this.message = "Merged " + givenBName + " into " + curBName + ".";
        this.parent = parentSha;
        this.otherParent = otherParentSha;
        blobs = new TreeMap<>();
        blobs.putAll(head.blobs);
        this.date = new Date();
    }

    public Commit() {
        this.message = "initial commit";
        this.date = new Date(0);
        this.blobs = new TreeMap<>();
        this.parent = null;
    }

    public String getParent() {
        return parent;
    }

    public String getDate() {
        String strDateFormat = "EEE MMM d HH:mm:ss yyyy Z";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(this.date);
    }

    public String getMessage() {
        return this.message;
    }

    public boolean containBlob(String filename) {
        return blobs.containsKey(filename);
    }

    public boolean isSameBlob(String filename, String sha) {
        if (!containBlob(filename)) {
            return false;
        }
        return blobs.get(filename).equals(sha);
    }

    public TreeMap<String, String> getBlobs() {
        return this.blobs;
    }

    public void addFile(String filename, String sha) {
        blobs.put(filename, sha);
    }

    public void removeFile(String filename) {
        blobs.remove(filename);
    }

    public boolean fileTracked(String filename) {
        return blobs.containsKey(filename);
    }

    public String getFilesha(String filename) {
        return this.blobs.get(filename);
    }
}
