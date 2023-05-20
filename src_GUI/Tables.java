import com.alibaba.fastjson.JSON;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Tables {
    public static TableView<Post> createPage(int pageIndex, int pageSize, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        int page = pageIndex * pageSize;
        TableView<Post> postTable = createTable();
        List<Post> postList = getTableData(page, page + pageSize, socket, oos, iis);
        postTable.setItems(FXCollections.observableList(postList));

        postTable.setPrefHeight(500);
        postTable.setPrefWidth(600);
//        group.getChildren().addAll(g1, postTable);
        TableView<Post> sw = postTable;
        postTable.setRowFactory(tv -> //双击查看post detail
        {
            TableRow<Post> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    int p_id = sw.getSelectionModel().getSelectedItems().get(0).getPost_id();
                    try {
                        window1.postDetail(p_id, socket, oos, iis);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
        return postTable;
    }

    public static TableView<Post> createTable() {
        TableView<Post> table = new TableView<>();
        TableColumn<Post, Integer> idCol = new TableColumn<>("PostID");
        idCol.setCellValueFactory(new PropertyValueFactory("post_id"));
        TableColumn<Post, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory("title"));
        TableColumn<Post, String> contentCol = new TableColumn<>("Content");
        contentCol.setCellValueFactory(new PropertyValueFactory("content"));
        TableColumn<Post, Integer> auCol = new TableColumn<>("Author");
        auCol.setCellValueFactory(new PropertyValueFactory("author_name"));
        TableColumn<Post, Integer> timeCol = new TableColumn<>("Post Time");
        timeCol.setCellValueFactory(new PropertyValueFactory("posting_time"));

        table.getColumns().addAll(idCol, titleCol, contentCol, auCol, timeCol);
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }

    public static List<Post> getMyPostData(int from, int to, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        List<Post> postData = new ArrayList<>();
        //ResultSet resultSet;
        try {
            Command command = new Command("getPublishedPost", new String[]{window1.userName});
            oos.writeObject(command);
            oos.flush();

            // 接收查询结果
            Response response = (Response) iis.readObject();
            String post_json = response.responseContent;
            ;
            List<Post> posts = JSON.parseArray(post_json, Post.class);

            for (int i = from; i <= to; i++) {
                if (posts.size() == 0) {
                    break;
                }
                Post p = new Post();
                p.setPost_id(posts.get(i).getPost_id());
                p.setTitle(posts.get(i).getTitle());
                p.setContent(posts.get(i).getContent());
                p.setAuthor_name(posts.get(i).getAuthor_name());
                p.setPosting_time(posts.get(i).getPosting_time());
                postData.add(p);
            }

        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return postData;
    }

    public static List<Post> getTableData(int from, int to, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        List<Post> postData = new ArrayList<>();
        try {
            Command command = new Command("getAllPost", new String[]{window1.userName});
            oos.writeObject(command);
            oos.flush();

            // 接收查询结果
            Response response = (Response) iis.readObject();
            String post_json = response.responseContent;
            List<Post> posts = JSON.parseArray(post_json, Post.class);
            for (int i = from; i <= to; i++) {
                if (posts.size() == 0) {
                    break;
                }
                Post p = new Post();

                p.setPost_id(posts.get(i).getPost_id());
                p.setTitle(posts.get(i).getTitle());
                p.setContent(posts.get(i).getContent());
                if (posts.get(i).isUnKnown()) {
                    p.setAuthor_name("Unknown");
                } else {
                    p.setAuthor_name(posts.get(i).getAuthor_name());
                }
                p.setPosting_time(posts.get(i).getPosting_time());
                postData.add(p);
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return postData;
    }

    public static TableView<Post> createTable_react() {
        TableView<Post> table = new TableView<>();
        TableColumn<Post, Integer> auCol = new TableColumn<>("Author");
        auCol.setCellValueFactory(new PropertyValueFactory("author_name"));
        auCol.setPrefWidth(180);
        table.getColumns().addAll(auCol);
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }

    public static TableView<Post> createPage_react(int pageIndex, int pageSize, String reactType, int postId, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        int page = pageIndex * pageSize;
        TableView<Post> postTable = createTable_react();
        List<Post> postList = getTableData_react(page, page + pageSize, postId, reactType, socket, oos, iis);
        postTable.setItems(FXCollections.observableList(postList));

        postTable.setPrefHeight(350);
        postTable.setPrefWidth(200);
//        group.getChildren().addAll(g1, postTable);
        TableView<Post> sw = postTable;
        return postTable;
    }


    public static int replyCnt;

    public static List<Post> getTableData_react(int from, int to, int postId, String react_type, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        List<Post> postData = new ArrayList<>();
        try {
            Command command = new Command(react_type, new String[]{postId + ""});
            oos.writeObject(command);
            oos.flush();

            // 接收查询结果
            Response response = (Response) iis.readObject();
            String post_json = response.responseContent;
            List<Post> posts = JSON.parseArray(post_json, Post.class);
            window1.reactAuthorCnt = posts.size();
            for (int i = from; i <= to; i++) {
                if (posts.size() == 0) {
                    break;
                }
                Post p = new Post();
                p.setAuthor_name(posts.get(i).getAuthor_name());
                postData.add(p);
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return postData;
    }


    public static TableView<Replies> createTable_reply() {
        TableView<Replies> table = new TableView<>();
        TableColumn<Replies, Integer> re_idCol = new TableColumn<>("Reply ID");
        re_idCol.setCellValueFactory(new PropertyValueFactory("reply_id"));
        TableColumn<Replies, Integer> postId = new TableColumn<>("Post ID");
        postId.setCellValueFactory(new PropertyValueFactory("post_id"));
        TableColumn<Replies, String> re_conCol = new TableColumn<>("Content");
        re_conCol.setCellValueFactory(new PropertyValueFactory("reply_content"));
        TableColumn<Replies, Integer> starCol = new TableColumn<>("Reply stars");
        starCol.setCellValueFactory(new PropertyValueFactory("reply_stars"));
        TableColumn<Replies, String> replyAuthor = new TableColumn<>("Reply Author");
        replyAuthor.setCellValueFactory(new PropertyValueFactory("reply_author"));

        table.getColumns().addAll(re_idCol, postId, re_conCol, starCol, replyAuthor);
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }

    public static List<Replies> getTableData_reply(int from, int to, int postId, String react_type, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        List<Replies> postData = new ArrayList<>();
        try {
            Command command = new Command(react_type, new String[]{postId + ""});
            oos.writeObject(command);
            oos.flush();

            // 接收查询结果
            Response response = (Response) iis.readObject();
            String replies_json = response.responseContent;
            List<Replies> replies = JSON.parseArray(replies_json, Replies.class);
            replyCnt = replies.size();
            for (int i = from; i <= to; i++) {
                if (replyCnt == 0) {
                    break;
                }
                Replies r = new Replies();

                r.setPost_id(replies.get(i).getPost_id());
                r.setReply_id(replies.get(i).getReply_id());
                r.setReply_content(replies.get(i).getReply_content());
                r.setReply_stars(replies.get(i).getReply_stars());
                r.setReply_author(replies.get(i).getReply_author());
                postData.add(r);
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return postData;
    }

    public static TableView<Replies> createPage_reply(int pageIndex, int pageSize, String reactType, int postId, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        int page = pageIndex * pageSize;
        TableView<Replies> replyTable = createTable_reply();
        List<Replies> replyList = getTableData_reply(page, page + pageSize, postId, reactType, socket, oos, iis);
        replyTable.setItems(FXCollections.observableList(replyList));

        replyTable.setPrefHeight(600);
        replyTable.setPrefWidth(600);
//        group.getChildren().addAll(g1, postTable);
        TableView<Replies> sw = replyTable;
        replyTable.setRowFactory(tv -> //双击查看post detail
        {
            TableRow<Replies> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    int r_id = sw.getSelectionModel().getSelectedItems().get(0).getReply_id();
                    try {
                        window1.replyDetail(r_id, socket, oos, iis);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
        return replyTable;
    }


    public static TableView<Replies> createTable_sec_reply() {
        TableView<Replies> table = new TableView<>();
        TableColumn<Replies, Integer> secre_idCol = new TableColumn<>("sec_Reply ID");
        secre_idCol.setCellValueFactory(new PropertyValueFactory("sec_reply_id"));
        TableColumn<Replies, String> re_conCol = new TableColumn<>("Content");
        re_conCol.setCellValueFactory(new PropertyValueFactory("sec_reply_content"));
        TableColumn<Replies, Integer> starCol = new TableColumn<>("Reply stars");
        starCol.setCellValueFactory(new PropertyValueFactory("sec_reply_stars"));
        TableColumn<Replies, String> replyAuthor = new TableColumn<>("Reply Author");
        replyAuthor.setCellValueFactory(new PropertyValueFactory("sec_reply_author"));

        table.getColumns().addAll(secre_idCol, re_conCol, starCol, replyAuthor);
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }

    public static List<Replies> getTableData_sec_reply(int from, int to, int replyid, String react_type, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        List<Replies> postData = new ArrayList<>();
        try {
            Command command = new Command(react_type, new String[]{replyid + ""});
            oos.writeObject(command);
            oos.flush();

            // 接收查询结果
            Response response = (Response) iis.readObject();
            String replies_json = response.responseContent;
            List<Replies> replies = JSON.parseArray(replies_json, Replies.class);
            replyCnt = replies.size();
            for (int i = from; i <= to; i++) {
                if (replyCnt == 0) {
                    break;
                }
                Replies r = new Replies();

                r.setSec_reply_id(replies.get(i).getSec_reply_id());
                r.setSec_reply_content(replies.get(i).getSec_reply_content());
                r.setSec_reply_stars(replies.get(i).getSec_reply_stars());
                r.setSec_reply_author(replies.get(i).getSec_reply_author());
                postData.add(r);
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return postData;
    }

    public static TableView<Replies> createPage_sec_reply(int pageIndex, int pageSize, String reactType, int postId, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        int page = pageIndex * pageSize;
        TableView<Replies> replyTable = createTable_sec_reply();
        List<Replies> replyList = getTableData_sec_reply(page, page + pageSize, postId, reactType, socket, oos, iis);
        replyTable.setItems(FXCollections.observableList(replyList));

        replyTable.setPrefHeight(350);
        replyTable.setPrefWidth(600);
        TableView<Replies> sw = replyTable;
        return replyTable;
    }


    public static List<Author_foll> getTableData_foll(int from, int to, Label mess, String react_type, ObjectOutputStream oos, ObjectInputStream iis) throws Exception {
        List<Author_foll> postData = new ArrayList<>();
//        List<Post> posts = null;

        try {
            Command command = new Command(react_type, new String[]{window1.userName});
            oos.writeObject(command);
            oos.flush();

            // 接收查询结果
            Response response = (Response) iis.readObject();
            String post_json = response.responseContent;
            List<Author_foll> posts = JSON.parseArray(post_json, Author_foll.class);
            window1.reactAuthorCnt = posts.size();
            for (int i = from; i <= to; i++) {
                if (i >= posts.size() || posts.size() == 0) {
                    break;
                }
                Author_foll p = new Author_foll();
//                p.getAuthorFollowedBy().addAll(posts.get(i).getAuthorFollowedBy());
                p.setFollowedAuthorName(posts.get(i).getFollowedAuthorName());
                postData.add(p);
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return postData;
    }

    public static TableView<Author_foll> createTable_Foll_author() {
        TableView<Author_foll> table = new TableView<>();
//        TableColumn<Post, Integer> auCol = new TableColumn<>("取消关注");
        TableColumn<Author_foll, String> auCol = new TableColumn<>("Author");
        auCol.setCellValueFactory(new PropertyValueFactory("followedAuthorName"));
        auCol.setPrefWidth(180);
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().add(auCol);

        return table;
    }

    public static TableView<Author_foll> createPage_Follow(int pageIndex, int pageSize,Pagination pagination_like, Label mess, String react_type, ObjectOutputStream oos, ObjectInputStream iis) throws Exception {
        int page = pageIndex * pageSize;
        TableView<Author_foll> postTable = createTable_Foll_author();

        List<Author_foll> postList = getTableData_foll(page, page + pageSize, mess, react_type, oos, iis);
        postTable.setItems(FXCollections.observableList(postList));

        postTable.setPrefHeight(350);
        postTable.setPrefWidth(200);
        TableView<Author_foll> sw = postTable;
        postTable.setRowFactory(tv -> //双击查看post detail
        {
            TableRow<Author_foll> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    String authorName = sw.getSelectionModel().getSelectedItems().get(0).getFollowedAuthorName();
                    try {
//                        window1.postDetail(p_id, socket, oos, iis);
                        window1.unfoll_author(authorName, mess,pagination_like, oos, iis);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
        return postTable;
    }

    public static List<Post> getTableData_my_reactions(int from, int to, String reactType, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        List<Post> postData = new ArrayList<>();
        try {
            Command command = new Command(reactType, new String[]{window1.userName});
            oos.writeObject(command);
            oos.flush();

            // 接收查询结果
            Response response = (Response) iis.readObject();
            String post_json = response.responseContent;
            List<Post> posts = JSON.parseArray(post_json, Post.class);
            for (int i = from; i <= to; i++) {
                if (posts.size() == 0) {
                    break;
                }
                Post p = new Post();
                p.setPost_id(posts.get(i).getPost_id());
                p.setTitle(posts.get(i).getTitle());
                p.setContent(posts.get(i).getContent());
                p.setAuthor_name(posts.get(i).getAuthor_name());
                p.setPosting_time(posts.get(i).getPosting_time());
                postData.add(p);
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return postData;
    }

    public static TableView<Post> createPage_my_reactions(int pageIndex, int pageSize, String reactType, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        int page = pageIndex * pageSize;
        TableView<Post> postTable = createTable_my_reactions();
        List<Post> postList = getTableData_my_reactions(page, page + pageSize, reactType, socket, oos, iis);
        postTable.setItems(FXCollections.observableList(postList));

        postTable.setPrefHeight(350);
        postTable.setPrefWidth(600);
//        group.getChildren().addAll(g1, postTable);
        TableView<Post> sw = postTable;
        postTable.setRowFactory(tv -> //双击查看post detail
        {
            TableRow<Post> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    int p_id = sw.getSelectionModel().getSelectedItems().get(0).getPost_id();
                    try {
                        window1.postDetail(p_id, socket, oos, iis);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
        return postTable;
    }

    public static TableView<Post> createTable_my_reactions() {
        TableView<Post> table = new TableView<>();
        TableColumn<Post, Integer> idCol = new TableColumn<>("PostID");
        idCol.setCellValueFactory(new PropertyValueFactory("post_id"));
        TableColumn<Post, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory("title"));
        TableColumn<Post, String> contentCol = new TableColumn<>("Content");
        contentCol.setCellValueFactory(new PropertyValueFactory("content"));
        TableColumn<Post, Integer> auCol = new TableColumn<>("Author");
        auCol.setCellValueFactory(new PropertyValueFactory("author_name"));
        TableColumn<Post, Integer> timeCol = new TableColumn<>("Post Time");
        timeCol.setCellValueFactory(new PropertyValueFactory("posting_time"));

        table.getColumns().addAll(idCol, titleCol, contentCol, auCol, timeCol);
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }

    public static TableView<Replies> createTable_my_reply() {
        TableView<Replies> table = new TableView<>();
        TableColumn<Replies, Integer> re_idCol = new TableColumn<>("Reply ID");
        re_idCol.setCellValueFactory(new PropertyValueFactory("reply_id"));
        TableColumn<Replies, Integer> postId = new TableColumn<>("Post ID");
        postId.setCellValueFactory(new PropertyValueFactory("post_id"));
        TableColumn<Replies, String> re_conCol = new TableColumn<>("Content");
        re_conCol.setCellValueFactory(new PropertyValueFactory("reply_content"));
        TableColumn<Replies, Integer> starCol = new TableColumn<>("Reply stars");
        starCol.setCellValueFactory(new PropertyValueFactory("reply_stars"));
        TableColumn<Replies, String> post_content = new TableColumn<>("Post content");
        post_content.setCellValueFactory(new PropertyValueFactory("content"));

        table.getColumns().addAll(re_idCol, postId, re_conCol, post_content, starCol);
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }

    public static List<Replies> getTableData_my_reply(int from, int to, String react_type, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        List<Replies> postData = new ArrayList<>();
        try {
            Command command = new Command(react_type, new String[]{window1.userName});
            oos.writeObject(command);
            oos.flush();

            // 接收查询结果
            Response response = (Response) iis.readObject();
            String replies_json = response.responseContent;
            List<Replies> replies = JSON.parseArray(replies_json, Replies.class);
            replyCnt = replies.size();
            for (int i = from; i <= to; i++) {
                if (replyCnt == 0) {
                    break;
                }
                Replies r = new Replies();
                r.setPost_id(replies.get(i).getPost_id());
                r.setReply_id(replies.get(i).getReply_id());
                r.setReply_content(replies.get(i).getReply_content());
                r.setReply_stars(replies.get(i).getReply_stars());
                r.setContent(replies.get(i).getContent());
                postData.add(r);
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return postData;
    }

    public static TableView<Replies> createPage_my_reply(int pageIndex, int pageSize, String reactType, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        int page = pageIndex * pageSize;
        TableView<Replies> replyTable = createTable_my_reply();
        List<Replies> replyList = getTableData_my_reply(page, page + pageSize, reactType, socket, oos, iis);
        replyTable.setItems(FXCollections.observableList(replyList));

        replyTable.setPrefHeight(350);
        replyTable.setPrefWidth(600);
//        group.getChildren().addAll(g1, postTable);
        TableView<Replies> sw = replyTable;
        replyTable.setRowFactory(tv -> //双击查看post detail
        {
            TableRow<Replies> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    int r_id = sw.getSelectionModel().getSelectedItems().get(0).getReply_id();
//                    try {
//                        replyDetail(r_id,postId, socket, oos, iis);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            });
            return row;
        });
        return replyTable;
    }

}