import java.sql.Timestamp;
import java.util.List;

public class Post {
    private int postID;
    private String title;
    private List<String> category;
    private String content;
    private Timestamp postingTime;
    private String postingCity;
    private String authorName;
    private Timestamp authorRegistrationTime;
    private String authorID;
    private String authorPhone;
    private List<String> authorFollowedBy;
    private List<String> authorFavorite;
    private List<String> authorShared;
    private List<String> authorLiked;

    @Override
    public String toString() {
        return "Post{" +
                "postID=" + postID +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", content='" + content + '\'' +
                ", postingTime='" + postingTime + '\'' +
                ", postingCity='" + postingCity + '\'' +
                ", Author='" + authorName + '\'' +
                ", authorRegistrationTime='" + authorRegistrationTime + '\'' +
                ", authorID='" + authorID + '\'' +
                ", authorPhone='" + authorPhone + '\'' +
                ", authorFollowedBy=" + authorFollowedBy +
                ", authorFavorite=" + authorFavorite +
                ", authorShared=" + authorShared +
                ", authorLiked=" + authorLiked +
                '}';
    }


    /**
     * Getter Part
     */
    public int getPostID() {
        return postID;
    }
    public String getTitle() {
        return title;
    }
    public List<String> getCategory() {
        return category;
    }
    public String getContent() {
        return content;
    }
    public Timestamp getPostingTime() {
        return postingTime;
    }
    public String getAuthorName () {
        return authorName;
    }
    public String getPostingCity() {
        return postingCity;
    }
    public Timestamp getAuthorRegistrationTime() {
        return authorRegistrationTime;
    }
    public String getAuthorID() {
        return authorID;
    }
    public String getAuthorPhone () {
        return authorPhone;
    }
    public  List<String> getAuthorFollowedBy() {
        return authorFollowedBy;
    }
    public List<String> getAuthorFavorite() {
        return authorFavorite;
    }
    public List<String> getAuthorShared() {
        return authorShared;
    }
    public List<String> getAuthorLiked() {
        return authorLiked;
    }
    /**
     * Setter Part
     */
    public void setPostID(int postID) {
        this.postID = postID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setCategory(List<String> category) {
        this.category = category;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setPostingTime(Timestamp postingTime) {
        this.postingTime = postingTime;
    }
    public void setPostingCity(String postingCity) {
        this.postingCity = postingCity;
    }
    public void setAuthorName (String authorName) {
        this.authorName = authorName;
    }
    public void setAuthorRegistrationTime(Timestamp authorRegistrationTime) {
        this.authorRegistrationTime = authorRegistrationTime;
    }
    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }
    public void setAuthorPhone (String authorPhone) {
        this.authorPhone = authorPhone;
    }
    public void setAuthorFollowedBy(List<String> authorFollowedBy) {
        this.authorFollowedBy = authorFollowedBy;
    }
    public void setAuthorFavorite(List<String> authorFavorite) {
        this.authorFavorite = authorFavorite;
    }
    public void setAuthorShared(List<String> authorShared) {
        this.authorShared = authorShared;
    }
    public void setAuthorLiked(List<String> authorLiked) {
        this.authorLiked = authorLiked;
    }
}
