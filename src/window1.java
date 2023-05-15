import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class window1 extends Application {
    private Button button;

    public static void main(String[] args) {
        launch(args);//调用start
    }

    public static String userName;
//    static APISpecification apiSpecification = new APISpecification();

    @Override
    public void start(Stage primaryStage) throws Exception {
        //舞台
        Stage stage = new Stage();
        stage.show();
//        stage.setTitle("DB_pro2");
//        Text text = new Text("Welcome to DataBase!");
//        text.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 30));
//        Button signIn = new Button("登录");
//        Button signUp = new Button("注册");
//        buttonStyle(signIn);
//        buttonStyle(signUp);
//        Group group1 = new Group();
//
////        group1.setPadding(new Insets(20, 200, 50, 50));
//        group1.getChildren().addAll(text, signIn, signUp);
//
//        signIn.setAlignment(Pos.TOP_CENTER);
//        text.setLayoutX(65);
//        text.setLayoutY(60);
//        signIn.setLayoutX(200);
//        signIn.setLayoutY(160);
//        signUp.setLayoutX(200);
//        signUp.setLayoutY(240);
//
//        signIn.setOnAction(e -> {
//            try {
//                signIn(stage);
//            } catch (Exception ex) {
//                System.out.println("signIn failed");
//            }
//        });
//        signUp.setOnAction(e -> {
//            try {
//                signUp(stage);
//            } catch (Exception ex) {
//                System.out.println("signUp failed");
//            }
//        });
//        Scene scene = new Scene(group1, 500, 500);
//        //场景放到舞台中
//        stage.setScene(scene);
//        stage.setX(700);//出现在屏幕中的位置
//        stage.setY(200);
//
//        stage.show();
//    }
//
//    public static int postCnt;
//
//    public static void home_page(Stage stage) throws Exception {
//        Group g1 = new Group();
//
//        MenuBar menuBar = new MenuBar();
//        Menu home_page = new Menu("Home Page");
//        Menu self = new Menu("Self Space");
//
//        MenuItem p = new MenuItem("Posts");
//        MenuItem self_space = new MenuItem("Self Space");
//        MenuItem write_post = new MenuItem("Write Post");
//        home_page.getItems().addAll(p);
//        self.getItems().addAll(self_space, write_post);
//        menuBar.getMenus().addAll(home_page, self);
//
//        ChoiceBox<String> searchBy = new ChoiceBox<>(FXCollections.observableArrayList("Author", "Title", "Content", "PostID"));
//        searchBy.setValue("Author");
//        searchBy.setLayoutX(310);
//        TextField search = new TextField();
//        search.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 12));
//        search.setPromptText("Search...");
//        search.setLayoutX(390);
//
//        Button searchBtn = new Button("Search");
//        setBtn_tp(searchBtn, "cadetblue", 12);
//        searchBtn.setLayoutX(550);
//        p.setOnAction(e ->
//                {
//                    try {
//                        home_page(stage);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//        );
//        self_space.setOnAction(e ->
//                {
//                    try {
//                        self_Space(stage);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//        );
//        write_post.setOnAction(e ->
//                {
//                    try {
//                        write_post();
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//        );
//
//        g1.getChildren().addAll(menuBar, searchBy, search, searchBtn);
//
//        Pagination pagination = new Pagination(calPostCnt() / 49 + 1, 0);
//        pagination.setPageFactory(pageIndex -> createPage(pageIndex, 50));
//        pagination.setLayoutY(30);
//        Group group = new Group(g1, pagination);
//
//
//        Scene scene = new Scene(group, 600, 600);
//
//        //场景放到舞台中
//        stage.setScene(scene);
////        stage.setX(700);//出现在屏幕中的位置
////        stage.setY(200);
//        stage.setResizable(false);
//        stage.show();
//
//    }
//
//    public static void write_post() throws FileNotFoundException {
//        FileInputStream input = new FileInputStream("./writeBack.jpeg");
//        Image image = new Image(input);
//        ImageView backGround = new ImageView(image);
//
//        Button button = new Button("发布");
//        buttonStyle(button);
//
//        Label title = new Label("Title:");
//        title.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        Label content = new Label("Content:");
//        content.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        Label country = new Label("Country:");
//        country.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
//
//        Label city = new Label("City:");
//        city.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
//
//        TextField tt = new TextField();//文本框
//        tt.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//        tt.setPrefWidth(500);
//
//        TextArea contt = new TextArea();//
//        contt.setPrefHeight(200);
//        contt.setPrefWidth(500);
//        contt.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.NORMAL, 18));
//        contt.setWrapText(true);
////        HTMLEditor contt = new HTMLEditor();//创建HTML富文本编辑器对象
////        contt.setHtmlText("设置默认初始化文本内容");
////        contt.setPrefHeight(300);//设置富文本编辑器对象高度
//
//
//        TextField country_text = new TextField();
//        country_text.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
//
//        TextField city_text = new TextField();
//        city_text.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
//
//
//        tt.setPromptText("Title...");
////        contt.setPromptText("Share interesting things...");
//        country_text.setPromptText("Your country name..");
//        city_text.setPromptText("Your city name..");
//
//        //布局
//        VBox group = new VBox(20);
//        group.setPadding(new Insets(20, 70, 10, 50));
//        Label wrongMess1 = new Label("");
//        wrongMess1.setTextFill(Color.web("#FF4136"));
//        wrongMess1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
//        group.getChildren().addAll(title, tt, content, contt, button, wrongMess1);
//
//        HBox cou_city = new HBox(5);
//        cou_city.setLayoutY(500);
//        cou_city.getChildren().addAll(country, country_text, city, city_text);
//        cou_city.setLayoutX(20);
////自由布局
//        //button.setLayoutX(330);
//        Stage stage = new Stage();
//        button.setOnAction(e -> {
//            try {
//                apiSpecification.PublishPost(userName, tt.getText(), contt.getText(), country_text.getText(), city_text.getText());
//                stage.close();
//            } catch (Exception ex) {
//                String message = ex.getMessage();
//                wrongMess1.setText(message);
//            }
//        });
//
//
//        Group groupAll = new Group(backGround, group, cou_city);
//        Scene scene = new Scene(groupAll, 600, 600);
//
//        stage.setScene(scene);
//        stage.setX(700);//出现在屏幕中的位置
//        stage.setY(200);
//
//        stage.show();
//    }
//
//    public static TableView<Post> my_post(int pageIndex, int pageSize) {
//        int page = pageIndex * pageSize;
//        TableView<Post> postTable = createTable();
//        List<Post> postList = getMyPostData(page + 1, page + pageSize);
//        postTable.setItems(FXCollections.observableList(postList));
//
//        postTable.setPrefHeight(300);
//        postTable.setPrefWidth(600);
////        group.getChildren().addAll(g1, postTable);
//        TableView<Post> sw = postTable;
//        postTable.setRowFactory(tv -> //双击查看post detail
//        {
//            TableRow<Post> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if (event.getClickCount() == 2 && (!row.isEmpty())) {
//                    int p_id = sw.getSelectionModel().getSelectedItems().get(0).getPostID();
//                    try {
//                        postDetail(p_id);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            return row;
//        });
//        return postTable;
//    }
//
//    public static void self_Space(Stage stage) throws Exception {
//        FileInputStream input = new FileInputStream("./self_space1.jpg");
//        Image image = new Image(input);
//        ImageView backGround = new ImageView(image);
//        ImageView toux;
//        if ((int) userName.charAt(0) < (int) ('m')) {
//            input = new FileInputStream("./img1.png");
//            Image image1 = new Image(input);
//            toux = new ImageView(image1);
//        } else {
//            input = new FileInputStream("./img2.png");
//            Image image2 = new Image(input);
//            toux = new ImageView(image2);
//        }
//        toux.setLayoutX(260);
//        toux.setLayoutY(25);
//
//        Text name = new Text(userName + "\n");
//        name.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.NORMAL, 20));
//        name.setWrappingWidth(600);
//        name.setTextAlignment(TextAlignment.CENTER);
//        name.setUnderline(true);
//        name.setLayoutY(120);
//
//        Button retBack = new Button("<--Back");
//        setBtn_tp(retBack, "Blue", 20);
//        String[] st = {"My posts", "Follow List", "Quit"};
//        ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(st));
//        choiceBox.setValue("My posts");
//        choiceBox.setLayoutX(500);
//        Group group = new Group(backGround, toux, name, retBack, choiceBox);
//
//        Pagination pagination = null;
//        try {
//            pagination = new Pagination(calMyPostCnt() / 9 + 1, 0);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        pagination.setPageFactory(pageIndex -> my_post(pageIndex, 10));
//        pagination.setLayoutY(200);
//        Group groupAll = new Group(group, pagination);
//        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
//
//            // if the item of the list is changed
//            public void changed(ObservableValue ov, Number value, Number new_value) {
//                if (st[new_value.intValue()].equals("My posts")) {
//                    Pagination pagination = null;
//                    try {
//                        pagination = new Pagination(calMyPostCnt() / 9 + 1, 0);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    pagination.setPageFactory(pageIndex -> my_post(pageIndex, 10));
//                    pagination.setLayoutY(200);
//                    Group groupAll = new Group(group, pagination);
//                    Scene scene = new Scene(groupAll, 600, 600);
//
//                    stage.setScene(scene);
//
//
//                    stage.show();
//                } else if (st[new_value.intValue()].equals("Follow List")) {
//                    Group groupAll = new Group(group);
//                    Scene scene = new Scene(groupAll, 600, 600);
//
//                    stage.setScene(scene);
//
//
//                    stage.show();
//                } else if (st[new_value.intValue()].equals("Quit")) {
//                    signIn(stage);
//                } else {
//                    Group groupAll = new Group(group);
//                    Scene scene = new Scene(groupAll, 600, 600);
//
//                    stage.setScene(scene);
//
//
//                    stage.show();
//                }
//                // set the text for the label to the selected item
//
//            }
//        });
//
//
//        retBack.setOnAction(e -> {
//            try {
//                home_page(stage);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        });
//        Scene scene = new Scene(groupAll, 600, 600);
//
//        stage.setScene(scene);
//        stage.setX(700);//出现在屏幕中的位置
//        stage.setY(200);
//
//        stage.show();
//    }
//
//    public static void buttonStyle(Button button) {
//        button.setStyle(
//                "-fx-background-color: cadetblue;" +
//                        "-fx-background-radius: 20;" +
//                        "-fx-text-fill: antiquewhite;" +
//                        "-fx-font-family: 'Microsoft YaHei UI';" +
//                        "-fx-font-size: 20"
//        );
//    }
//
//    public static TableView<Post> createTable() {
//        TableView<Post> table = new TableView<>();
//        TableColumn<Post, Integer> idCol = new TableColumn<>("PostID");
//        idCol.setCellValueFactory(new PropertyValueFactory("postID"));
//        TableColumn<Post, String> titleCol = new TableColumn<>("Title");
//        titleCol.setCellValueFactory(new PropertyValueFactory("title"));
//        TableColumn<Post, String> contentCol = new TableColumn<>("Content");
//        contentCol.setCellValueFactory(new PropertyValueFactory("content"));
//        TableColumn<Post, Integer> auCol = new TableColumn<>("Author");
//        auCol.setCellValueFactory(new PropertyValueFactory("authorName"));
//        TableColumn<Post, Integer> timeCol = new TableColumn<>("Post Time");
//        timeCol.setCellValueFactory(new PropertyValueFactory("postingTime"));
//
//        table.getColumns().addAll(idCol, titleCol, contentCol, auCol, timeCol);
////        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        return table;
//    }
//
//
//    public static int calPostCnt() throws Exception {
//        ResultSet resultSet = apiSpecification.getAllPost();
//        resultSet.last();
//        postCnt = resultSet.getRow();
//        resultSet.beforeFirst();
//        return postCnt;
//    }
//
//    public static int calMyPostCnt() throws Exception {
//        ResultSet resultSet = apiSpecification.getPublishedPost(userName);
//        if (resultSet == null) {
//            return 0;
//        }
//        resultSet.last();
//        int mypostCnt = resultSet.getRow();
//        resultSet.beforeFirst();
//        return mypostCnt;
//    }
//
//    public static List<Post> getMyPostData(int from, int to) {
//        List<Post> postData = new ArrayList<>();
//        ResultSet resultSet;
//        try {
//            resultSet = apiSpecification.getPublishedPost(userName);
//
//            for (int i = from; i <= to; i++) {
//                if (!resultSet.next()) {
//                    break;
//                }
//                Post p = new Post();
//                resultSet.absolute(i);//限定第几行
//                p.setPostID(resultSet.getInt("post_id"));
//
//                p.setTitle(resultSet.getString("title"));
////                    String t = resultSet.getString("title");
//                p.setContent(resultSet.getString("content"));
//                p.setAuthorName(resultSet.getString("author_name"));
//                p.setPostingTime(resultSet.getTimestamp("posting_time"));
//                postData.add(p);
//            }
//
//        } catch (Exception ex) {
//            String message = ex.getMessage();
//            System.out.println(message);
//        }
//        return postData;
//    }
//
//    public static List<Post> getTableData(int from, int to) {
//        List<Post> postData = new ArrayList<>();
//        ResultSet resultSet;
//        try {
//            resultSet = apiSpecification.getAllPost();
//
//            for (int i = from; i <= to; i++) {
//                if (!resultSet.next()) {
//                    break;
//                }
//                Post p = new Post();
//                resultSet.absolute(i);//限定第几行
//                p.setPostID(resultSet.getInt("post_id"));
//
//                p.setTitle(resultSet.getString("title"));
////                    String t = resultSet.getString("title");
//                p.setContent(resultSet.getString("content"));
//                p.setAuthorName(resultSet.getString("author_name"));
//                p.setPostingTime(resultSet.getTimestamp("posting_time"));
//                postData.add(p);
//            }
//
//        } catch (Exception ex) {
//            String message = ex.getMessage();
//            System.out.println(message);
//        }
//        return postData;
//    }
//
//    public static void postDetail(int postId) throws FileNotFoundException {
//        Stage stage = new Stage();
//
//        FileInputStream input = new FileInputStream("./post_pic.jpeg");
//        Image image = new Image(input);
//        ImageView imageView = new ImageView(image);
//        VBox vBox = new VBox();
//        HBox react = new HBox(20);
//        HBox seeReacts = new HBox(20);
//        react.setLayoutY(400);
//        seeReacts.setLayoutY(440);
//        Group group = new Group(imageView, vBox, react, seeReacts);
//
//        ResultSet resultSet;
//        try {
//            resultSet = apiSpecification.database.getIthPost(postId);
//            while (resultSet.next()) {
//                resultSet.absolute(1);//限定第几行
//                Text id = new Text("id: " + resultSet.getInt("post_id") + "");
//                id.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
//
//                Text title = new Text(resultSet.getString("title") + "\n");
//                title.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.NORMAL, 20));
//                title.setWrappingWidth(600);
//                title.setTextAlignment(TextAlignment.CENTER);
//                title.setUnderline(true);
//
//                Text content = new Text("    " + resultSet.getString("content") + "\n");
//                content.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.NORMAL, 18));
//                content.setFill(Paint.valueOf("#00008B"));
//                content.setWrappingWidth(600);
//                content.setLayoutX(30);
//                content.setTextAlignment(TextAlignment.CENTER);
//                Text au = new Text(resultSet.getString("author_name"));
//                au.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 18));
//
//                Text time = new Text(resultSet.getTimestamp("posting_time") + "");
//                time.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
//                vBox.getChildren().addAll(id, au, title, content, time);
//
//                Button like = new Button("Like");
//                Button share = new Button("Share");
//                Button favorite = new Button("Favorite");
//                Button seeLikes = new Button("See Likes");
//                Button seeShares = new Button("See Shares");
//                Button seeFavorites = new Button("See Favorites");
//                Button follow_au = new Button("Follow the author");
//                Button reply = new Button("Reply");
//                Button seeReply = new Button("See Replies");
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
//                react.getChildren().addAll(like, share, favorite, follow_au, reply);
//                seeReacts.getChildren().addAll(seeLikes, seeShares, seeFavorites, seeReply);
//                setHandle(like);
//                like.setOnAction(e ->
//                        {
//                            setBtn_tp(like, "black", 20);
//                        }
//                );
//                setHandle(share);
//                share.setOnAction(e ->
//                        {
//
//                        }
//                );
//                setHandle(favorite);
//                favorite.setOnAction(e ->
//                        {
//
//                        }
//                );
//                setHandle(seeLikes);
//                seeLikes.setOnAction(e ->
//                {
//                    VBox vBox1 = new VBox();
//                    Scene scene = new Scene(vBox1, 200, 400);
//                    //场景放到舞台中
//
//                    Stage smallStage = new Stage();
//                    smallStage.setScene(scene);
//                    Point p = MouseInfo.getPointerInfo().getLocation();
//                    smallStage.setX(p.getX());//出现在屏幕中的位置
//                    smallStage.setY(p.getY());
//                    stage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
//
//                        public void handle(MouseEvent event) {
//                            smallStage.close();
//                        }
//
//                    });
//
//                    smallStage.show();
//                });
//                setHandle(seeShares);
//                seeShares.setOnAction(e ->
//                        {
//                            VBox vBox1 = new VBox();
//                            Scene scene = new Scene(vBox1, 200, 400);
//                            //场景放到舞台中
//
//                            Stage smallStage = new Stage();
//                            smallStage.setScene(scene);
//                            Point p = MouseInfo.getPointerInfo().getLocation();
//                            smallStage.setX(p.getX());//出现在屏幕中的位置
//                            smallStage.setY(p.getY());
//                            stage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
//
//                                public void handle(MouseEvent event) {
//                                    smallStage.close();
//                                }
//
//                            });
//
//                            smallStage.show();
//                        }
//                );
//                setHandle(seeFavorites);
//                seeFavorites.setOnAction(e ->
//                        {
//                            VBox vBox1 = new VBox();
//                            Scene scene = new Scene(vBox1, 200, 400);
//                            //场景放到舞台中
//
//                            Stage smallStage = new Stage();
//                            smallStage.setScene(scene);
//                            Point p = MouseInfo.getPointerInfo().getLocation();
//                            smallStage.setX(p.getX());//出现在屏幕中的位置
//                            smallStage.setY(p.getY());
//                            stage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
//
//                                public void handle(MouseEvent event) {
//                                    smallStage.close();
//                                }
//
//                            });
//
//                            smallStage.show();
//                        }
//                );
//                setHandle(follow_au);
//                follow_au.setOnAction(e ->
//                        {
//
//                        }
//                );
//                setHandle(reply);
//                reply.setOnAction(e ->
//                        {
//
//                        }
//                );
//                setHandle(seeReply);
//                seeReply.setOnAction(e ->
//                        {
//
//                        }
//                );
//            }
//        } catch (Exception ex) {
//            String message = ex.getMessage();
//            System.out.println(message);
//        }
//
//        Scene scene = new Scene(group, 600, 600);
//        //场景放到舞台中
//        stage.setScene(scene);
//        stage.setX(700);//出现在屏幕中的位置
//        stage.setY(200);
//        stage.setResizable(false);
//        stage.show();
//
//
//    }
//
//
//    // get the mouse's position
//
//    public static void setHandle(Button button) {
//        button.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
//            setBtn_tp(button, "red", 20);
//        });
//        button.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
//            setBtn_tp(button, "cadetblue", 20);
//        });
//
//    }
//
//    public static void setBtn_tp(Button button, String color, int size) {//透明的button
//        button.setStyle(
//                "-fx-background-color: transparent;" +
//                        "-fx-background-radius: " + size + ";" +
//                        "-fx-text-fill: " + color + ";" +
//                        "-fx-font-family: 'Microsoft YaHei UI';" +
//                        "-fx-font-size: " + size + ";"
//        );
//    }
//
//    public static TableView<Post> createPage(int pageIndex, int pageSize) {
//        int page = pageIndex * pageSize;
//        TableView<Post> postTable = createTable();
//        List<Post> postList = getTableData(page + 1, page + pageSize);
//        postTable.setItems(FXCollections.observableList(postList));
//
//        postTable.setPrefHeight(500);
//        postTable.setPrefWidth(600);
////        group.getChildren().addAll(g1, postTable);
//        TableView<Post> sw = postTable;
//        postTable.setRowFactory(tv -> //双击查看post detail
//        {
//            TableRow<Post> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if (event.getClickCount() == 2 && (!row.isEmpty())) {
//                    int p_id = sw.getSelectionModel().getSelectedItems().get(0).getPostID();
//                    try {
//                        postDetail(p_id);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            return row;
//        });
//        return postTable;
//    }
//
//
//    public static void signUp(Stage stage) {//注册
//        Button button = new Button("注册");
//        buttonStyle(button);
//
//        Label user = new Label("User name:");
//        user.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        Label i = new Label("User id:");
//        i.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        Label p = new Label("Phone:");
//        p.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        Label p1 = new Label("Password:");
//        p1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        Label p2 = new Label("Password:");
//        p2.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        TextField username = new TextField();//文本框
//        username.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        TextField id = new TextField();//
//        id.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        TextField phone = new TextField();
//        phone.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        PasswordField password1 = new PasswordField();//密码框
//        password1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        PasswordField password2 = new PasswordField();//
//        password2.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        username.setPromptText("Enter your user name(length 6-20)");
//        id.setPromptText("Enter your id (length 18)");
//        phone.setPromptText("Enter your phone number (length 11)");
//        password1.setPromptText("Enter your password (length 6-20)");
//        password2.setPromptText("Please enter your password again");
//
//        //布局
////        HBox hBox = new HBox(text);
////自由布局
//        VBox group = new VBox(20);
//        group.setPadding(new Insets(20, 70, 10, 50));
//        Label wrongMess1 = new Label("");
//        wrongMess1.setTextFill(Color.web("#FF4136"));
//        wrongMess1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
//        group.getChildren().addAll(user, username, i, id, p, phone, p1, password1, p2, password2, button, wrongMess1);
//        //button.setLayoutX(330);
//
//        button.setOnAction(e -> {
//            try {
//                apiSpecification.RegisterNewUser(username.getText(), id.getText(), phone.getText(), password1.getText(), password2.getText());
//                signIn(stage);
//            } catch (Exception ex) {
//                String message = ex.getMessage();
//                wrongMess1.setText(message);
//            }
//        });
//        Scene scene = new Scene(group, 700, 700);
//
//        //场景放到舞台中
//        stage.setScene(scene);
//        stage.setX(700);//出现在屏幕中的位置
//        stage.setY(200);
//
//        stage.show();
//
//    }
//
//    public static void signIn(Stage stage) {//登录
//        Button button = new Button("登录");
//        buttonStyle(button);
//
//        Label user = new Label("User name:");
//        user.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        Label p1 = new Label("Password:");
//        p1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        TextField username = new TextField();//文本框
//        username.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        PasswordField password1 = new PasswordField();//密码框
//        password1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
//
//        username.setPromptText("Enter your user name");
//
//        VBox group = new VBox(20);
//        group.setPadding(new Insets(20, 70, 10, 50));
//        Label wrongMess1 = new Label("");
//        wrongMess1.setTextFill(Color.web("#FF4136"));
//        wrongMess1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
//        group.getChildren().addAll(user, username, p1, password1, button, wrongMess1);
//        //button.setLayoutX(330);
//
//        button.setOnAction(e -> {
//            try {
//                System.out.println(password1.getText());
//                if (apiSpecification.LogInUser(username.getText(), password1.getText())) {
//                    userName = username.getText();
//                    home_page(stage);
//                }
//            } catch (Exception ex) {
//                String message = ex.getMessage();
//                wrongMess1.setText(message);
//            }
//
//        });
//        Scene scene = new Scene(group, 700, 700);
//        //场景放到舞台中
//        stage.setScene(scene);
//        stage.setX(700);//出现在屏幕中的位置
//        stage.setY(200);
//
//        stage.show();
//    }

    }
}
