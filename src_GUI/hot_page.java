import java.sql.Timestamp;

public class hot_page {
    public int post_id;
    public String title;
    public String content;
    public Timestamp posting_time;
    public int posting_city_id;
    public String author_name;
    public String filename;
    public byte[] file;
    public boolean isunKnown;
    public int totalWeight;
    public int likeCnt;
    public int sharedCnt;
    public int favoritedCnt;
    public int replyCnt;
    public int timeDifference;

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getPosting_time() {
        return posting_time;
    }

    public void setPosting_time(Timestamp posting_time) {
        this.posting_time = posting_time;
    }

    public int getPosting_city_id() {
        return posting_city_id;
    }

    public void setPosting_city_id(int posting_city_id) {
        this.posting_city_id = posting_city_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

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

    public boolean isIsunKnown() {
        return isunKnown;
    }

    public void setIsunKnown(boolean isunKnown) {
        this.isunKnown = isunKnown;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }

    public int getSharedCnt() {
        return sharedCnt;
    }

    public void setSharedCnt(int sharedCnt) {
        this.sharedCnt = sharedCnt;
    }

    public int getFavoritedCnt() {
        return favoritedCnt;
    }

    public void setFavoritedCnt(int favoritedCnt) {
        this.favoritedCnt = favoritedCnt;
    }

    public int getReplyCnt() {
        return replyCnt;
    }

    public void setReplyCnt(int replyCnt) {
        this.replyCnt = replyCnt;
    }

    public int getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(int timeDifference) {
        this.timeDifference = timeDifference;
    }
}
