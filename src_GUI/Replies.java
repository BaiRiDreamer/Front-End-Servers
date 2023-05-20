public class Replies {
    public int post_id;
    public int reply_id;
    public String reply_content;
    public int reply_stars;
    public String reply_author;
    public String sec_reply_content;
    public int sec_reply_stars;
    public String sec_reply_author;
    public String content;
    public int sec_reply_id;

    public int getSec_reply_id() {
        return sec_reply_id;
    }

    public void setSec_reply_id(int sec_reply_id) {
        this.sec_reply_id = sec_reply_id;
    }

    public Replies() {
    }

    public Replies(int post_id, int reply_id, String reply_content, int reply_stars, String reply_author, String sec_reply_content, int sec_reply_stars, String sec_reply_author) {
        this.post_id = post_id;
        this.reply_id = reply_id;
        this.reply_content = reply_content;
        this.reply_stars = reply_stars;
        this.reply_author = reply_author;
        this.sec_reply_content = sec_reply_content;
        this.sec_reply_stars = sec_reply_stars;
        this.sec_reply_author = sec_reply_author;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public int getReply_stars() {
        return reply_stars;
    }

    public void setReply_stars(int reply_stars) {
        this.reply_stars = reply_stars;
    }

    public String getReply_author() {
        return reply_author;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setReply_author(String reply_author) {
        this.reply_author = reply_author;
    }

    public String getSec_reply_content() {
        return sec_reply_content;
    }

    public void setSec_reply_content(String sec_reply_content) {
        this.sec_reply_content = sec_reply_content;
    }

    public int getSec_reply_stars() {
        return sec_reply_stars;
    }

    public void setSec_reply_stars(int sec_reply_stars) {
        this.sec_reply_stars = sec_reply_stars;
    }

    public String getSec_reply_author() {
        return sec_reply_author;
    }

    public void setSec_reply_author(String sec_reply_author) {
        this.sec_reply_author = sec_reply_author;
    }

    @Override
    public String toString() {
        return "Replies{" +
                "postID=" + post_id +
                ", replyContent='" + reply_content + '\'' +
                ", replyStars=" + reply_stars +
                ", replyAuthor='" + reply_author + '\'' +
                ", secondaryReplyContent='" + sec_reply_content + '\'' +
                ", secondaryReplyStars=" + sec_reply_stars +
                ", secondaryReplyAuthor='" + sec_reply_author + '\'' +
                ", replyID=" + reply_id + '\'' +
                '}';
    }
}
