import com.alibaba.fastjson.JSON;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.awt.Button;
import java.io.*;
import java.net.Socket;
import java.rmi.server.RemoteRef;
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
                    int p_id = sw.getSelectionModel().getSelectedItems().get(0).getPostID();
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
        idCol.setCellValueFactory(new PropertyValueFactory("postID"));
        TableColumn<Post, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory("title"));
        TableColumn<Post, String> contentCol = new TableColumn<>("Content");
        contentCol.setCellValueFactory(new PropertyValueFactory("content"));
        TableColumn<Post, Integer> auCol = new TableColumn<>("Author");
        auCol.setCellValueFactory(new PropertyValueFactory("authorName"));
        TableColumn<Post, Integer> timeCol = new TableColumn<>("Post Time");
        timeCol.setCellValueFactory(new PropertyValueFactory("postingTime"));

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
                p.setPostID(posts.get(i).getPostID());
                p.setTitle(posts.get(i).getTitle());
                p.setContent(posts.get(i).getContent());
                p.setAuthorName(posts.get(i).getAuthorName());
                p.setPostingTime(posts.get(i).getPostingTime());
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

                p.setPostID(posts.get(i).getPostID());
                p.setTitle(posts.get(i).getTitle());
                p.setContent(posts.get(i).getContent());
                if (posts.get(i).isUnKnown()) {
                    p.setAuthorName("Unknown");
                } else {
                    p.setAuthorName(posts.get(i).getAuthorName());
                }
                p.setPostingTime(posts.get(i).getPostingTime());
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
        auCol.setCellValueFactory(new PropertyValueFactory("authorName"));
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
                p.setAuthorName(posts.get(i).getAuthorName());
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
        re_idCol.setCellValueFactory(new PropertyValueFactory("replyID"));
        TableColumn<Replies, Integer> postId = new TableColumn<>("Post ID");
        postId.setCellValueFactory(new PropertyValueFactory("postID"));
        TableColumn<Replies, String> re_conCol = new TableColumn<>("Content");
        re_conCol.setCellValueFactory(new PropertyValueFactory("replyContent"));
        TableColumn<Replies, Integer> starCol = new TableColumn<>("Reply stars");
        starCol.setCellValueFactory(new PropertyValueFactory("replyStars"));
        TableColumn<Replies, String> replyAuthor = new TableColumn<>("Reply Author");
        replyAuthor.setCellValueFactory(new PropertyValueFactory("replyAuthor"));

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

                r.setPostID(replies.get(i).getPostID());
                r.setReplyID(replies.get(i).getReplyID());
                r.setReplyContent(replies.get(i).getReplyContent());
                r.setReplyStars(replies.get(i).getReplyStars());
                r.setReplyAuthor(replies.get(i).getReplyAuthor());
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
                    int r_id = sw.getSelectionModel().getSelectedItems().get(0).getReplyID();
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

//    public static void replyDetail(int replyId, int post_id,Socket socket, ObjectOutputStream oos, ObjectInputStream iis) throws IOException {
//        Stage stage = new Stage();
//
//        FileInputStream input = new FileInputStream("./reply6_6.jpeg");
//        javafx.scene.image.Image image = new Image(input);
//        ImageView imageView = new ImageView(image);
//        VBox vBox = new VBox();
//        HBox react = new HBox(20);
//        HBox seeReacts = new HBox(20);
//        HBox message = new HBox(20);
//
//        react.setLayoutY(400);
//        seeReacts.setLayoutY(440);
//        message.setLayoutY(480);
//        message.setLayoutX(80);
//        Group group = new Group(imageView, vBox, react, seeReacts, message);
//
//
//        try {
//            Command command = new Command("getPostReply", new String[]{post_id + ""});
//            oos.writeObject(command);
//            oos.flush();
//
//            // 接收查询结果
//            Response response = (Response) iis.readObject();
//            String reply_json = response.responseContent;
//            List<Replies> reply = JSON.parseArray(reply_json, Replies.class);
//            if (reply.size() != 0) {
//                Replies r = reply.get(0);
//                Text id = new Text("id: " + r.getReplyID() + "");
//                id.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
//
////                Text title = new Text(r.g() + "\n");
////                title.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.NORMAL, 20));
////                title.setWrappingWidth(600);
////                title.setTextAlignment(TextAlignment.CENTER);
////                title.setUnderline(true);
//
//                Text content = new Text("    " + r.getReplyContent() + "\n");
//                content.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.NORMAL, 18));
//                content.setFill(Paint.valueOf("#00008B"));
//                content.setWrappingWidth(600);
//                content.setLayoutX(30);
//                content.setTextAlignment(TextAlignment.CENTER);
//                Text au = new Text(r.getReplyAuthor());
//                au.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 18));
//
////                Text time = new Text(r.g() + "");
////                time.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
//                vBox.getChildren().addAll(id, au, content,);
//
//                javafx.scene.control.Button reply_rec = new javafx.scene.control.Button("Reply this reply");
////                javafx.scene.control.Button share = new javafx.scene.control.Button("Share");
////                javafx.scene.control.Button favorite = new javafx.scene.control.Button("Favorite");
////                javafx.scene.control.Button seeLikes = new javafx.scene.control.Button("See Likes");
////                javafx.scene.control.Button seeShares = new javafx.scene.control.Button("See Shares");
////                javafx.scene.control.Button seeFavorites = new javafx.scene.control.Button("See Favorites");
//                javafx.scene.control.Button follow_au = new javafx.scene.control.Button("Follow the author");
//                javafx.scene.control.Button reply = new javafx.scene.control.Button("Reply");
//                javafx.scene.control.Button seeReply = new javafx.scene.control.Button("See Replies");
//
//                like.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
//
//                setBtn_tp(like, "cadetblue", 20);
//                setBtn_tp(share, "cadetblue", 20);
//                setBtn_tp(favorite, "cadetblue", 20);
//                setBtn_tp(seeLikes, "cadetblue", 20);
//                setBtn_tp(seeShares, "cadetblue", 20);
//                setBtn_tp(seeFavorites, "cadetblue", 20);
//                setBtn_tp(follow_au, "cadetblue", 20);
//                setBtn_tp(reply, "cadetblue", 20);
//                setBtn_tp(seeReply, "cadetblue", 20);
//
//                javafx.scene.control.Label label_mess = new Label("--");
//                label_mess.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//                react.getChildren().addAll(like, share, favorite, follow_au, reply);
//                seeReacts.getChildren().addAll(seeLikes, seeShares, seeFavorites, seeReply);
//                message.getChildren().addAll(label_mess);
//                window1.setHandle(like);
//                like.setOnAction(e ->
//                        {
//                            react_with_post(label_mess, like, oos, iis, postId, "likePost", "点赞", "点过赞了");
//                        }
//                );
//
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        Scene scene = new Scene(group, 600, 600);
//        //场景放到舞台中
//        stage.setScene(scene);
//        stage.setX(700);//出现在屏幕中的位置
//        stage.setY(200);
//        stage.setResizable(false);
//        stage.show();
//    }


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

    public static TableView<Author_foll> createPage_Follow(int pageIndex, int pageSize, Label mess, String react_type, ObjectOutputStream oos, ObjectInputStream iis) throws Exception {
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
                        window1.unfoll_author(authorName, mess, oos, iis);
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
                p.setPostID(posts.get(i).getPostID());
                p.setTitle(posts.get(i).getTitle());
                p.setContent(posts.get(i).getContent());
                p.setAuthorName(posts.get(i).getAuthorName());
                p.setPostingTime(posts.get(i).getPostingTime());
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
                    int p_id = sw.getSelectionModel().getSelectedItems().get(0).getPostID();
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
        idCol.setCellValueFactory(new PropertyValueFactory("postID"));
        TableColumn<Post, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory("title"));
        TableColumn<Post, String> contentCol = new TableColumn<>("Content");
        contentCol.setCellValueFactory(new PropertyValueFactory("content"));
        TableColumn<Post, Integer> auCol = new TableColumn<>("Author");
        auCol.setCellValueFactory(new PropertyValueFactory("authorName"));
        TableColumn<Post, Integer> timeCol = new TableColumn<>("Post Time");
        timeCol.setCellValueFactory(new PropertyValueFactory("postingTime"));

        table.getColumns().addAll(idCol, titleCol, contentCol, auCol, timeCol);
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }

    public static TableView<Replies> createTable_my_reply() {
        TableView<Replies> table = new TableView<>();
        TableColumn<Replies, Integer> re_idCol = new TableColumn<>("Reply ID");
        re_idCol.setCellValueFactory(new PropertyValueFactory("replyID"));
        TableColumn<Replies, Integer> postId = new TableColumn<>("Post ID");
        postId.setCellValueFactory(new PropertyValueFactory("postID"));
        TableColumn<Replies, String> re_conCol = new TableColumn<>("Content");
        re_conCol.setCellValueFactory(new PropertyValueFactory("replyContent"));
        TableColumn<Replies, Integer> starCol = new TableColumn<>("Reply stars");
        starCol.setCellValueFactory(new PropertyValueFactory("replyStars"));
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
                r.setPostID(replies.get(i).getPostID());
                r.setReplyID(replies.get(i).getReplyID());
                r.setReplyContent(replies.get(i).getReplyContent());
                r.setReplyStars(replies.get(i).getReplyStars());
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
                    int r_id = sw.getSelectionModel().getSelectedItems().get(0).getReplyID();
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