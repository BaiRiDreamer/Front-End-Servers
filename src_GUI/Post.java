import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class Post {
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public int post_id;
    public String title;
    public String content;
    public Timestamp posting_time;
    public int posting_city_id;
    public String author_name;
    public Timestamp authorRegistrationTime;
    public String authorID;
    public String authorPhone;

    public String filename;
    public byte[] file;
    public boolean isUnKnown;


    public boolean isUnKnown() {
        return isUnKnown;
    }


    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", title='" + title + '\'' +
//                ", category=" + category +
                ", content='" + content + '\'' +
                ", posting_time='" + posting_time + '\'' +
                ", posting_city_id='" + posting_city_id + '\'' +
                ", author_name='" + author_name + '\'' +
                ", authorRegistrationTime='" + authorRegistrationTime + '\'' +
                ", authorID='" + authorID + '\'' +
                ", authorPhone='" + authorPhone +
//                ", authorFollowedBy=" + authorFollowedBy +
//                ", authorFavorite=" + authorFavorite +
//                ", authorShared=" + authorShared +
//                ", authorLiked=" + authorLiked +
                ", filename=" + filename +
                ", file=" + Arrays.toString(file) +
                ", isUnknown=" + isUnKnown +
                '}';
    }


    /**
     * Getter Part
     */
    public int getPost_id() {
        return post_id;
    }

    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }

    public Timestamp getPosting_time() {
        return posting_time;
    }

    public String getAuthor_name() {
        return author_name;
    }


    /**
     * Setter Part
     */
    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public void setPosting_time(Timestamp posting_time) {
        this.posting_time = posting_time;
    }


    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

}
