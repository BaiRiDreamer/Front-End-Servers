import com.alibaba.fastjson.JSON;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.util.Duration;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class window1 extends Application {

    public window1() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);//调用start
    }

    public static String userName;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            String host = "10.25.3.249";
//            String host = "localhost";
            int port = 7345;
            Socket socket = new Socket(host, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream iis = new ObjectInputStream(socket.getInputStream());
            //舞台
            Stage stage = new Stage();
//        stage.show();
            stage.setTitle("DB_pro2");
            Text text = new Text("Welcome to DataBase!");
            text.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 30));
            Button signIn = new Button("登录");
            Button signUp = new Button("注册");
            buttonStyle(signIn);
            buttonStyle(signUp);
            Group group1 = new Group();

//        group1.setPadding(new Insets(20, 200, 50, 50));
            group1.getChildren().addAll(text, signIn, signUp);

            signIn.setAlignment(Pos.TOP_CENTER);
            text.setLayoutX(65);
            text.setLayoutY(60);
            signIn.setLayoutX(200);
            signIn.setLayoutY(160);
            signUp.setLayoutX(200);
            signUp.setLayoutY(240);

            signIn.setOnAction(e -> {
                try {
                    signIn(stage, socket, oos, iis);
                } catch (Exception ex) {
                    System.out.println("signIn failed");
                }
            });
            signUp.setOnAction(e -> {
                try {
                    signUp(stage, socket, oos, iis);
                } catch (Exception ex) {
                    System.out.println("signUp failed");
                }
            });
            Scene scene = new Scene(group1, 500, 500);
            //场景放到舞台中
            stage.setScene(scene);
            stage.setX(700);//出现在屏幕中的位置
            stage.setY(200);

            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static Menu hot;

    public static void home_page(Stage stage, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) throws Exception {
        stage.setTitle("Home Page");
        Group g1 = new Group();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(15), new EventHandler<ActionEvent>() {//15s更新一次
            @Override
            public void handle(ActionEvent event) {
                try {
                    stage.setTitle("Hot Lists-实时（15s刷新一次）");
                    hot_page(stage, g1, 1, 1, 1, 1, 500, 10000, 30, socket, oos, iis);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }));
//        timeline.stop();
        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
        timeline.pause();

        MenuBar menuBar = new MenuBar();
        Menu home_page = new Menu("Home Page");
        Menu self = new Menu("Self Space");

        MenuItem p = new MenuItem("Posts");
        hot = new Menu("Hot Lists");
        MenuItem self_space = new MenuItem("Self Space");
        MenuItem write_post = new MenuItem("Write Post");
        MenuItem lik_hot = new MenuItem("喜爱榜");
        MenuItem share_hot = new MenuItem("分享榜");
        MenuItem fav_hot = new MenuItem("收藏榜");
        MenuItem re_hot = new MenuItem("回复榜");
        MenuItem time_hot = new MenuItem("实时榜");
        hot.getItems().addAll(lik_hot, share_hot, fav_hot, re_hot, time_hot);
        home_page.getItems().addAll(p);
        self.getItems().addAll(self_space, write_post);
        menuBar.getMenus().addAll(home_page, hot, self);
        String[] search1 = {"Author", "Title", "Content", "PostID"};

        ChoiceBox<String> searchBy = new ChoiceBox<>(FXCollections.observableArrayList(search1));
        searchBy.setValue("Author");
        searchBy.setLayoutX(310);
        TextField search = new TextField();
        search.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 12));
        search.setPromptText("Search...");
        search.setLayoutX(390);
        Button searchBtn = new Button("Search");

        setBtn_tp(searchBtn, "cadetblue", 12);
        searchBtn.setLayoutX(550);

        searchBtn.setOnAction(e -> {
            stage.setTitle("Home Page");
            timeline.pause();
            System.out.println(search.getText());
            if (searchBy.getValue().equals("Author")) {
                try {
                    get_search(stage, g1, socket, search.getText(), "", "", 0, oos, iis);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (searchBy.getValue().equals("Title")) {
                try {
                    get_search(stage, g1, socket, "", search.getText(), "", 0, oos, iis);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (searchBy.getValue().equals("Content")) {
                try {
                    get_search(stage, g1, socket, "", "", search.getText(), 0, oos, iis);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (searchBy.getValue().equals("PostID")) {
                try {
                    get_search(stage, g1, socket, search.getText(), "", "", 0, oos, iis);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            // set the text for the label to the selected item

        });


        lik_hot.setOnAction(e -> {
            timeline.pause();
            try {
                stage.setTitle("Hot Lists-like");
                hot_page(stage, g1, 500, 10, 10, 10, 10, 100000, 30, socket, oos, iis);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        share_hot.setOnAction(e -> {
            try {
                timeline.pause();
                stage.setTitle("Hot Lists-share");
                hot_page(stage, g1, 10, 500, 10, 10, 10, 100000, 30, socket, oos, iis);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        fav_hot.setOnAction(e -> {
            try {
                timeline.pause();
                stage.setTitle("Hot Lists-favorite");
                hot_page(stage, g1, 10, 10, 500, 10, 10, 100000, 30, socket, oos, iis);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        re_hot.setOnAction(e -> {
            try {
                timeline.pause();
                stage.setTitle("Hot Lists-reply");
                hot_page(stage, g1, 10, 10, 10, 500, 10, 100000, 30, socket, oos, iis);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        time_hot.setOnAction(e -> {
            try {
                timeline.playFromStart();
                stage.setTitle("Hot Lists-实时（15s刷新一次)");
                hot_page(stage, g1, 1, 1, 1, 1, 500, 10000, 30, socket, oos, iis);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        p.setOnAction(e ->
                {
                    timeline.pause();
                    try {
                        home_page(stage, socket, oos, iis);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
        );
        self_space.setOnAction(e ->
                {
                    timeline.pause();
                    try {
                        self_Space(stage, socket, oos, iis);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
        );
        write_post.setOnAction(e ->
                {
                    timeline.pause();
                    try {
                        write_post(socket, oos, iis);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
        );

        g1.getChildren().addAll(menuBar, searchBy, search, searchBtn);

        Pagination pagination = new Pagination(calPostCnt(socket, oos, iis) / 49 + 1, 0);
        pagination.setPageFactory(pageIndex -> Tables.createPage(pageIndex, 50, socket, oos, iis));
        pagination.setLayoutY(30);
        Group group = new Group(g1, pagination);


        Scene scene = new Scene(group, 600, 600);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void hot_page(Stage stage, Group g1, int likedWeight, int sharedWeight,
                                int favoritedWeight, int replyWeight, int timeDifferenceWeight,
                                int timeDivParameter, int limit, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) throws Exception {

        Pagination pagination = new Pagination(1, 0);
        pagination.setPageFactory(pageIndex -> Tables.createPage_hot(pageIndex, 50, likedWeight, sharedWeight, favoritedWeight, replyWeight, timeDifferenceWeight, timeDivParameter, limit, socket, oos, iis));
        pagination.setLayoutY(30);
        Group group = new Group(g1, pagination);


        Scene scene = new Scene(group, 600, 600);

        //场景放到舞台中
        stage.setScene(scene);
//        stage.setX(700);//出现在屏幕中的位置
//        stage.setY(200);
        stage.setResizable(false);
        stage.show();

    }


    public static void get_search(Stage stage, Group g1, Socket socket, String author_name, String title, String content, int postId, ObjectOutputStream oos, ObjectInputStream iis) throws Exception {
        Pagination pagination = new Pagination(5, 0);
        pagination.setPageFactory(pageIndex -> Tables.createPage_search(pageIndex, 20, author_name, title, content, postId + "", socket, oos, iis));
        pagination.setLayoutY(30);
        Group group = new Group(g1, pagination);

        Scene scene = new Scene(group, 600, 600);

        //场景放到舞台中
        stage.setScene(scene);
//        stage.setX(700);//出现在屏幕中的位置
//        stage.setY(200);
        stage.setResizable(false);
        stage.show();
    }

    public static void write_post(Socket socket, ObjectOutputStream oos, ObjectInputStream iis) throws FileNotFoundException {
        FileInputStream input = new FileInputStream("./R-C.jpeg");
        Image image = new Image(input);
        ImageView backGround = new ImageView(image);

        Button button = new Button("发布");
        buttonStyle(button);

        Label title = new Label("Title:");
        title.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        Label content = new Label("Content:");
        content.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        Label country = new Label("Country:");
        country.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));

        Label city = new Label("City:");
        city.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));

        Button filel = new Button("Choose your File:");
        filel.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 12));

        TextField tt = new TextField();//文本框
        tt.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));
        tt.setPrefWidth(500);

        RadioButton niming = new RadioButton("匿名发送");
        niming.setUserData("false");
        niming.selectedProperty().addListener(new ChangeListener<Boolean>() {   //r1监听单独
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                niming.setUserData(newValue);
            }
        });


        TextArea contt = new TextArea();//
        contt.setPrefHeight(180);
        contt.setPrefWidth(500);
        contt.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.NORMAL, 18));
        contt.setWrapText(true);


        TextField country_text = new TextField();
        country_text.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));

        TextField city_text = new TextField();
        city_text.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));

        TextField file = new TextField();
        file.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 12));

        Stage stageFile = new Stage();
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("C:\\Users\\20699\\Desktop"));   //设置初始路径，默认为我的电脑
        chooser.setTitle("选择图片或视频");
//        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
//                new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
//                new FileChooser.ExtensionFilter("GIF", "*.gif"), new FileChooser.ExtensionFilter("MP4", "*.mp4")
//        );
//        String fileName;
        //筛选文件扩展
        Label temp = new Label();
        filel.setOnMouseClicked(e -> {
            try {
                File fileChoosen = chooser.showOpenDialog(stageFile);
                temp.setText(fileChoosen.getAbsolutePath());
                filel.setText(fileChoosen.getName());
            } catch (NullPointerException ex) {
            }

        });
        tt.setPromptText("Title...");
        contt.setPromptText("Share interesting things...");
        country_text.setPromptText("Your country name..");
        city_text.setPromptText("Your city name..");

        //布局
        VBox group = new VBox(20);
        group.setPadding(new Insets(20, 70, 10, 50));
        Label wrongMess1 = new Label("");
        wrongMess1.setTextFill(Color.web("#FF4136"));
        wrongMess1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
        group.getChildren().addAll(title, tt, content, contt, filel, wrongMess1);

        HBox butt = new HBox(5);
        butt.getChildren().addAll(button, niming);
        butt.setLayoutY(480);
        butt.setLayoutX(40);
        HBox cou_city = new HBox(5);
        cou_city.setLayoutY(600);
        cou_city.getChildren().addAll(country, country_text, city, city_text);
        cou_city.setLayoutX(20);
//自由布局
        //button.setLayoutX(330);
        Stage stage = new Stage();
        button.setOnAction(e -> {
            try {
                implementMethod.isContentValid(tt.getText(), contt.getText());
                implementMethod.isCityInfoValid(country_text.getText(), city_text.getText());

                Command command;
                if (!filel.getText().equals("Choose your File:")) {
                    command = new Command("PublishPost", new String[]{userName, tt.getText(), contt.getText(), country_text.getText(),
                            city_text.getText(), filel.getText(), niming.getUserData().toString()}, fileToByte(temp.getText()));
                } else {
//                    if (niming.getUserData().toString().equals("true")) {
//                        command = new Command("PublishPost", new String[]{"Unknown", tt.getText(), contt.getText(), country_text.getText(),
//                                city_text.getText(), filel.getText(), niming.getUserData().toString()}, null);
//                    } else {
                    command = new Command("PublishPost", new String[]{userName, tt.getText(), contt.getText(), country_text.getText(),
                            city_text.getText(), null, niming.getUserData().toString()}, null);
//                    }
                }
                oos.writeObject(command);
                oos.flush();

                // 接收查询结果
                Response response = (Response) iis.readObject();
                if (response.responseType.equals("true")) {
                    stage.close();
                }
                wrongMess1.setText(response.responseContent);

            } catch (Exception ex) {
                String message = ex.getMessage();
                wrongMess1.setText(message);
            }
        });


        Group groupAll = new Group(backGround, group, butt, cou_city);
        Scene scene = new Scene(groupAll, 600, 700);

        stage.setScene(scene);
        stage.setX(700);//出现在屏幕中的位置
        stage.setY(200);

        stage.show();
    }

    public static byte[] fileToByte(String filePath) {
        byte[] fileBytes = null;
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            fileBytes = new byte[(int) file.length()];
            fis.read(fileBytes);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileBytes;
    }

    public static TableView<Post> my_post(int pageIndex, int pageSize, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {
        int page = pageIndex * pageSize;
        TableView<Post> postTable = Tables.createTable();
        List<Post> postList = Tables.getMyPostData(page, page + pageSize, socket, oos, iis);
        postTable.setItems(FXCollections.observableList(postList));

        postTable.setPrefHeight(300);
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
                        postDetail(p_id, socket, oos, iis);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
        return postTable;
    }

    public static void self_Space(Stage stage, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) throws Exception {
        FileInputStream input = new FileInputStream("./self_space1.jpg");
        Image image = new Image(input);
        ImageView backGround = new ImageView(image);
        ImageView toux;
        if ((int) userName.charAt(0) < (int) ('m')) {
            input = new FileInputStream("./img1.png");
            Image image1 = new Image(input);
            toux = new ImageView(image1);
        } else {
            input = new FileInputStream("./img2.png");
            Image image2 = new Image(input);
            toux = new ImageView(image2);
        }
        toux.setLayoutX(260);
        toux.setLayoutY(25);

        Text name = new Text(userName + "\n");
        name.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.NORMAL, 20));
        name.setWrappingWidth(600);
        name.setTextAlignment(TextAlignment.CENTER);
        name.setUnderline(true);
        name.setLayoutY(120);

        Button retBack = new Button("<--Back");
        setBtn_tp(retBack, "Blue", 20);
        String[] st = {"My posts", "Follow List", "My Likes", "My Shares", "My Favorites", "My Replies", "Quit"};
        ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(st));
        choiceBox.setValue("My posts");
        choiceBox.setLayoutX(500);
        Group group = new Group(backGround, toux, name, retBack, choiceBox);

        Pagination pagination = null;
        try {
            pagination = new Pagination(calMyPostCnt(socket, oos, iis) / 9 + 1, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        pagination.setPageFactory(pageIndex -> my_post(pageIndex, 10, socket, oos, iis));
        pagination.setLayoutY(200);
        Group groupAll = new Group(group, pagination);
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {
                if (st[new_value.intValue()].equals("My posts")) {
                    Pagination pagination = null;
                    try {
                        pagination = new Pagination(calMyPostCnt(socket, oos, iis) / 9 + 1, 0);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    pagination.setPageFactory(pageIndex -> my_post(pageIndex, 10, socket, oos, iis));
                    pagination.setLayoutY(200);
                    Group groupAll = new Group(group, pagination);
                    Scene scene = new Scene(groupAll, 600, 600);
                    stage.setScene(scene);
                    stage.show();
                } else if (st[new_value.intValue()].equals("Follow List")) {
                    Label mess = new Label("");
                    mess.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

                    Pagination pagination_like = new Pagination(5, 0);
                    pagination_like.setPageFactory(pageIndex -> {
                        try {
                            return Tables.createPage_Follow(pageIndex, 10, pagination_like, mess, "getUserFollowBy", oos, iis);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    });
                    pagination_like.setLayoutY(200);
                    pagination_like.setLayoutX(200);

                    mess.setLayoutX(420);
                    mess.setLayoutY(350);

                    Group group_r1 = new Group(group, pagination_like, mess);
                    Scene scene = new Scene(group_r1, 600, 600);
                    stage.setScene(scene);
                    stage.show();
                } else if (st[new_value.intValue()].equals("My Likes")) {
                    try {
                        getUserHadReacted(stage, group, socket, "getUserHadLiked", oos, iis);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (st[new_value.intValue()].equals("My Shares")) {
                    try {
                        getUserHadReacted(stage, group, socket, "getUserHadShared", oos, iis);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (st[new_value.intValue()].equals("My Favorites")) {
                    try {
                        getUserHadReacted(stage, group, socket, "getUserHadFavorited", oos, iis);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (st[new_value.intValue()].equals("My Replies")) {
                    see_my_replies(stage, group, oos, iis, "getUserHadReply", socket);
                } else if (st[new_value.intValue()].equals("Quit")) {
                    signIn(stage, socket, oos, iis);
                } else {
                    Group groupAll = new Group(group);
                    Scene scene = new Scene(groupAll, 600, 600);
                    stage.setScene(scene);
                    stage.show();
                }
                // set the text for the label to the selected item
            }
        });


        retBack.setOnAction(e -> {
            try {
                home_page(stage, socket, oos, iis);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        Scene scene = new Scene(groupAll, 600, 600);

        stage.setScene(scene);
        stage.setX(700);//出现在屏幕中的位置
        stage.setY(200);

        stage.show();
    }

    public static void getUserHadReacted(Stage stage, Group g1, Socket socket, String reactType, ObjectOutputStream oos, ObjectInputStream iis) throws Exception {
        Pagination pagination = new Pagination(5, 0);
        pagination.setPageFactory(pageIndex -> Tables.createPage_my_reactions(pageIndex, 20, reactType, socket, oos, iis));
        pagination.setLayoutY(200);
        Group group = new Group(g1, pagination);

        Scene scene = new Scene(group, 600, 600);

        //场景放到舞台中
        stage.setScene(scene);
//        stage.setX(700);//出现在屏幕中的位置
//        stage.setY(200);
        stage.setResizable(false);
        stage.show();
    }

    public static void buttonStyle(Button button) {
        button.setStyle(
                "-fx-background-color: cadetblue;" +
                        "-fx-background-radius: 20;" +
                        "-fx-text-fill: antiquewhite;" +
                        "-fx-font-family: 'Microsoft YaHei UI';" +
                        "-fx-font-size: 20"
        );
    }


    public static int calPostCnt(Socket socket, ObjectOutputStream oos, ObjectInputStream iis) throws Exception {
        Command command = new Command("getPostCnt", new String[]{userName});
//        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(command);
        oos.flush();

        // 接收查询结果
        Response response = (Response) iis.readObject();
        if (Objects.equals(response.responseType, "true"))
            return Integer.parseInt(response.responseContent);
        else {
            return -1;
        }
    }

    public static int calMyPostCnt(Socket socket, ObjectOutputStream oos, ObjectInputStream iis) throws Exception {
        Command command = new Command("getUserPostCnt", new String[]{userName});
//        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(command);
        oos.flush();

        // 接收查询结果
        Response response = (Response) iis.readObject();
        if (Objects.equals(response.responseType, "true"))
            return Integer.parseInt(response.responseContent);
        else {
            return -1;
        }
    }


    static int reactAuthorCnt;


    public static void react_with_post(Label label_mess, Button react_byn, ObjectOutputStream oos, ObjectInputStream iis, int postId, String reactType, String output, String t2) {
        Command command1 = new Command(reactType, new String[]{userName, postId + ""});
        try {
            oos.writeObject(command1);
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // 接收查询结果
        try {
            Response response1 = (Response) iis.readObject();
            if (response1.responseType.equals("true")) {
                setBtn_tp(react_byn, "black", 20);
                label_mess.setText(output + "成功!");
            } else {
                label_mess.setText(output + "失败，您已经" + t2);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            label_mess.setText(output + "失败");
        }

    }

    public static void see_react_authors(ObjectOutputStream oos, ObjectInputStream iis, int postId, String reactType, Socket socket) throws IOException, ClassNotFoundException {
        Stage smallStage = new Stage();

        Point pp = MouseInfo.getPointerInfo().getLocation();
        smallStage.setX(pp.getX());//出现在屏幕中的位置
        smallStage.setY(pp.getY());

        Command command = new Command(reactType, new String[]{postId + ""});
        oos.writeObject(command);
        oos.flush();

        // 接收查询结果
        Response response = (Response) iis.readObject();
        String post_json = response.responseContent;
        List<Post> posts = JSON.parseArray(post_json, Post.class);
        reactAuthorCnt = posts.size();

        Pagination pagination_like = new Pagination(reactAuthorCnt / 24 + 1, 0);
        pagination_like.setPageFactory(pageIndex -> Tables.createPage_react(pageIndex, 25,posts, reactType, postId, socket, oos, iis));
        pagination_like.setLayoutY(0);
        Group group_r1 = new Group(pagination_like);

        Scene scene = new Scene(group_r1, 200, 400);
        smallStage.setScene(scene);
        smallStage.show();

    }

    public static void see_replies(ObjectOutputStream oos, ObjectInputStream iis, int postId, String reactType, Socket socket) {
        Stage smallStage = new Stage();
        smallStage.setTitle("Post Replies");

        Pagination pagination_like = new Pagination(Tables.replyCnt / 24 + 1, 0);
        pagination_like.setPageFactory(pageIndex -> Tables.createPage_reply(pageIndex, 25, reactType, postId, socket, oos, iis));
        pagination_like.setLayoutY(0);
        Group group_r1 = new Group(pagination_like);

        Scene scene = new Scene(group_r1, 600, 600);
        smallStage.setScene(scene);
        smallStage.show();

    }

    public static void see_my_replies(Stage stage, Group g1, ObjectOutputStream oos, ObjectInputStream iis, String reactType, Socket socket) {
//        Stage smallStage = new Stage();

        Pagination pagination_like = new Pagination(Tables.replyCnt / 14 + 1, 0);
        pagination_like.setPageFactory(pageIndex -> Tables.createPage_my_reply(pageIndex, 15, reactType, socket, oos, iis));
        pagination_like.setLayoutY(200);
        Group group_r1 = new Group(g1, pagination_like);

        Scene scene = new Scene(group_r1, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static Post getIthPost(int postId, ObjectOutputStream oos, ObjectInputStream iis) throws IOException, ClassNotFoundException {
        Command command = new Command("getIthIdPost", new String[]{postId + ""});
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(command);
        oos.flush();

        // 接收查询结果
        Response response = (Response) iis.readObject();
        String post_json = response.responseContent;
        List<Post> posts = JSON.parseArray(post_json, Post.class);
//        List<Post> postData = new ArrayList<>();
        if (posts.size() == 0) {
            return null;
        }
        Post p = new Post();
        p.setPost_id(posts.get(0).getPost_id());
        p.setTitle(posts.get(0).getTitle());
        p.setContent(posts.get(0).getContent());
        p.setAuthor_name(posts.get(0).getAuthor_name());
        p.setPosting_time(posts.get(0).getPosting_time());
        return p;
    }


    public static void postDetail(int postId, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) throws FileNotFoundException {
        Stage stage = new Stage();
        stage.setTitle("Post Details");

        FileInputStream input = new FileInputStream("./post_pic.jpeg");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        VBox vBox = new VBox();
        HBox react = new HBox(20);
        HBox seeReacts = new HBox(20);
        HBox message = new HBox(20);
        HBox reply_post = new HBox(20);
        Group block = new Group();

        Group group = new Group();
        react.setLayoutY(400);
        seeReacts.setLayoutY(440);
        message.setLayoutY(480);
        message.setLayoutX(80);
        reply_post.setLayoutY(550);
        block.setLayoutX(550);
        block.setLayoutY(550);
        Button block_btn = new Button("Block this user");
        block.getChildren().add(block_btn);

        try {
            Command command = new Command("getIthIdPost", new String[]{postId + ""});
            oos.writeObject(command);
            oos.flush();

            // 接收查询结果
            Response response = (Response) iis.readObject();
            String post_json = response.responseContent;
            List<Post> posts = JSON.parseArray(post_json, Post.class);
            if (posts.size() != 0) {
                Post p = posts.get(0);
                group = new Group(imageView, vBox, react, seeReacts, message, reply_post, block);

                Text id = new Text("id: " + p.getPost_id() + "");
                id.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));

                Text title = new Text(p.getTitle() + "\n");
                title.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.NORMAL, 20));
                title.setWrappingWidth(600);
                title.setTextAlignment(TextAlignment.CENTER);
                title.setUnderline(true);

                Text content = new Text("    " + p.getContent() + "\n");
                content.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.NORMAL, 18));
                content.setFill(Paint.valueOf("#00008B"));
                content.setWrappingWidth(600);
                content.setLayoutX(30);
                content.setTextAlignment(TextAlignment.CENTER);

                Text time = new Text(p.getPosting_time() + "");
                time.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));

                Button like = new Button("Like");
                Button share = new Button("Share");
                Button favorite = new Button("Favorite");
                Button seeLikes = new Button("See Likes");
                Button seeShares = new Button("See Shares");
                Button seeFavorites = new Button("See Favorites");
                Button follow_au = new Button("Follow the author");
                Button seeReply = new Button("See Replies");
                Button sendReply = new Button("Send Reply");
                Button seePhoto = new Button("View picture or video");

                like.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));

                Text au = new Text();
                if (p.isUnKnown()) {
                    au = new Text("UnKnown");
                    follow_au.setDisable(true);
                } else {
                    au = new Text(p.getAuthor_name());
                }
                au.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 18));
                vBox.getChildren().addAll(id, au, title, content, time, seePhoto);


                seePhoto.setOnAction(e ->
                        {
                            if (p.getFile() == null) {
                                seePhoto.setDisable(true);
                                seePhoto.setText("No picture or video");
                            } else {
                                Stage showPic = new Stage();
//                                if (Pattern.matches(p.getFilename(), "*.jpg") || Pattern.matches(p.getFilename(), "*.png") ||
//                                        Pattern.matches(p.getFilename(), "*.jpeg")) {
                                byte[] imageData = p.getFile(); // 从数据库中读取byte[]类型的数据
                                BufferedImage image1 = null;
                                try {
                                    image1 = ImageIO.read(new ByteArrayInputStream(imageData));
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                // 将BufferedImage对象保存为文件
                                File file = new File(".\\my_image.jpg");
                                try {
                                    ImageIO.write(image1, "jpg", file);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                FileInputStream input1 = null;
                                try {
                                    input1 = new FileInputStream(".\\my_image.jpg");
                                } catch (FileNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                                int height = image1.getHeight();
                                int width = image1.getWidth();
                                double image_height = height;
                                double image_width = width;
                                if (width > 800 || height > 800) {
                                    if (width > height) {
                                        image_width = 800;
                                        image_height = 800.0 / width * height;
                                    } else {
                                        image_height = 800;
                                        image_width = 800.0 / height * width;
                                    }

                                }

                                Image image_user = new Image(input1, image_width, image_height, false, false);

                                ImageView imageView_user = new ImageView(image_user);
                                showPic.setTitle("Picture or Video");
//                                imageView_user.setLayoutY(400);
                                Group group1 = new Group(imageView_user);
                                Scene scene1 = new Scene(group1, 800, 800);
                                showPic.setScene(scene1);
                                showPic.show();
//                                }
//                                else{
//                                    byte[] videoData =p.getFile(); // your byte array
//
//                                    String filePath = ".\\video.mp4";
//                                    File file = new File(filePath);
//                                    FileOutputStream fos = null;
//                                    try {
//                                        fos = new FileOutputStream(file);
//                                    } catch (FileNotFoundException ex) {
//                                        ex.printStackTrace();
//                                    }
//                                    try {
//                                        fos.write(videoData);
//                                        fos.close();
//                                    } catch (IOException ex) {
//                                        ex.printStackTrace();
//                                    }
//
//                                    Media media = new Media(file.toURI().toString());
//                                    MediaPlayer mediaPlayer = new MediaPlayer(media);
//                                    MediaView mediaView= new MediaView(mediaPlayer);
//                                    StackPane root = new StackPane();
//                                    root.getChildren().add(mediaView);
//                                    Scene scene = new Scene(root, 600, 600);
//                                    showPic.setScene(scene);
//                                    showPic.show();
//                                    mediaPlayer.play();
//
//                                }
                            }
                        }
                );

                setBtn_tp(like, "cadetblue", 20);
                setBtn_tp(share, "cadetblue", 20);
                setBtn_tp(favorite, "cadetblue", 20);
                setBtn_tp(seeLikes, "cadetblue", 20);
                setBtn_tp(seeShares, "cadetblue", 20);
                setBtn_tp(seeFavorites, "cadetblue", 20);
                setBtn_tp(follow_au, "cadetblue", 20);
//                setBtn_tp(reply, "cadetblue", 20);
                setBtn_tp(seeReply, "cadetblue", 20);
                setBtn_tp(sendReply, "blue", 15);
                setBtn_tp(block_btn, "black", 12);
                setBtn_tp(seePhoto, "cadetblue", 15);

                Label label_mess = new Label("--");
                label_mess.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

                TextField reply_post_t = new TextField();
                reply_post_t.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
                reply_post_t.setPromptText("Reply this Post...");


                react.getChildren().addAll(like, share, favorite, follow_au);
                seeReacts.getChildren().addAll(seeLikes, seeShares, seeFavorites, seeReply);
                message.getChildren().addAll(label_mess);
                block.setLayoutX(500);
                reply_post.getChildren().addAll(reply_post_t, sendReply);
                setHandle(like);
                like.setOnAction(e ->
                        {
                            react_with_post(label_mess, like, oos, iis, postId, "likePost", "点赞", "点过赞了");
                        }
                );
                setHandle(share);
                share.setOnAction(e ->
                        {
                            react_with_post(label_mess, share, oos, iis, postId, "sharePost", "分享", "分享过了");
                        }
                );
                setHandle(favorite);
                favorite.setOnAction(e ->
                        {
                            react_with_post(label_mess, favorite, oos, iis, postId, "favoritePost", "收藏", "收藏过了");
                        }
                );
                setHandle(seeLikes);
                seeLikes.setOnAction(e ->
                {
                    try {
                        see_react_authors(oos, iis, postId, "getPostLiked", socket);
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                });
                setHandle(seeShares);
                seeShares.setOnAction(e ->
                        {
                            try {
                                see_react_authors(oos, iis, postId, "getPostShared", socket);
                            } catch (IOException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }
                );
                setHandle(seeFavorites);
                seeFavorites.setOnAction(e ->
                        {
                            try {
                                see_react_authors(oos, iis, postId, "getPostfavorited", socket);
                            } catch (IOException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }
                );
                setHandle(follow_au);
                follow_au.setOnAction(e ->
                        {
                            Command command1 = null;
                            try {
                                command1 = new Command("followUser", new String[]{userName, getIthPost(postId, oos, iis).getAuthor_name()});
                            } catch (IOException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                            try {
                                oos.writeObject(command1);
                                oos.flush();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            // 接收查询结果
                            try {
                                Response response1 = (Response) iis.readObject();
                                if (response1.responseType.equals("true")) {
                                    setBtn_tp(follow_au, "black", 20);
                                    label_mess.setText("关注作者成功!");
                                } else {
                                    label_mess.setText("您已经关注过了");
                                }
                            } catch (IOException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                                label_mess.setText("关注作者失败");
                            }
                        }
                );
                setHandle(sendReply);
                sendReply.setOnAction(e ->
                        {
                            String reply = reply_post_t.getText();
                            if (reply.equals("")) {
                                label_mess.setText("回复不能为空");
                            } else {
                                Command command1 = null;
                                command1 = new Command("replyPost", new String[]{postId + "", reply, userName});
                                try {
                                    oos.writeObject(command1);
                                    oos.flush();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                // 接收查询结果
                                try {
                                    Response response1 = (Response) iis.readObject();
                                    if (response1.responseType.equals("true")) {
                                        label_mess.setText("回复成功!");
                                    } else {
                                        label_mess.setText("回复失败");
                                    }
                                } catch (IOException | ClassNotFoundException ex) {
                                    ex.printStackTrace();
                                    label_mess.setText("回复失败");
                                }
                            }
                        }
                );
                setHandle(seeReply);
                seeReply.setOnAction(e ->
                        {
                            see_replies(oos, iis, postId, "getPostReply", socket);
                        }
                );
                block_btn.setOnAction(e ->
                {
                    Command command1 = null;
                    command1 = new Command("blockUser", new String[]{userName, p.getAuthor_name()});
                    try {
                        oos.writeObject(command1);
                        oos.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // 接收查询结果
                    try {
                        Response response1 = (Response) iis.readObject();
                        if (response1.responseType.equals("true")) {
                            label_mess.setText("屏蔽成功!");
                        } else {
                            label_mess.setText("您已经屏蔽过了");
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                        label_mess.setText("屏蔽失败");
                    }
                });
            }
        } catch (Exception ex) {
            String messagew = ex.getMessage();
            System.out.println(messagew);
        }


        Scene scene = new Scene(group, 600, 600);
        //场景放到舞台中
        stage.setScene(scene);
        stage.setX(700);//出现在屏幕中的位置
        stage.setY(200);
        stage.setResizable(false);
        stage.show();

    }


    public static void replyDetail(int replyId, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Reply Details");
        FileInputStream input = new FileInputStream("./reply6_6.jpeg");
        javafx.scene.image.Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        VBox vBox = new VBox();
        HBox react = new HBox(20);
        HBox message = new HBox(20);
        react.setLayoutY(520);

        message.setLayoutY(560);
        message.setLayoutX(80);
        Group group = new Group(imageView, vBox, react, message);


        try {
            Command command = new Command("getIthreply", new String[]{replyId + ""});
            oos.writeObject(command);
            oos.flush();

            // 接收查询结果
            Response response = (Response) iis.readObject();
            String reply_json = response.responseContent;
            List<Replies> reply = JSON.parseArray(reply_json, Replies.class);
            if (reply.size() != 0) {
                Replies r = reply.get(0);
                Text id = new Text("id: " + replyId);
                id.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));

                Text content = new Text("    " + r.getReply_content() + "\n");
                content.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.NORMAL, 18));
                content.setFill(Paint.valueOf("#00008B"));
                content.setWrappingWidth(600);
                content.setLayoutX(30);
                content.setTextAlignment(TextAlignment.CENTER);
                Text au = new Text(r.getReply_author() + ":");
                au.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 18));


                Label label_mess = new Label("--");
                label_mess.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));


                TextField reply_reply_t = new TextField();
                reply_reply_t.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
                reply_reply_t.setPromptText("Reply this Reply...");

                Button send_secReply = new Button("Send Reply to this reply");
                setBtn_tp(send_secReply, "blue", 15);
                react.getChildren().addAll(reply_reply_t, send_secReply);
                message.getChildren().addAll(label_mess);

                Pagination pagination_like = new Pagination(Tables.replyCnt / 14 + 1, 0);
                pagination_like.setPageFactory(pageIndex -> Tables.createPage_sec_reply(pageIndex, 15, "getReplySecreply", replyId, socket, oos, iis));
                pagination_like.setLayoutY(200);
                Group group_r1 = new Group(pagination_like);
                vBox.getChildren().addAll(id, au, content, group_r1);

                setHandle(send_secReply);
                send_secReply.setOnAction(e ->
                        {
                            if (reply_reply_t.getText().equals("")) {
                                label_mess.setText("回复不能为空");
                            } else {
                                Command command1 = null;
                                command1 = new Command("replySecReply", new String[]{replyId + "", reply_reply_t.getText(), userName});
                                try {
                                    oos.writeObject(command1);
                                    oos.flush();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                // 接收查询结果
                                try {
                                    Response response1 = (Response) iis.readObject();
                                    if (response1.responseType.equals("true")) {
                                        label_mess.setText("回复成功!");
                                        pagination_like.setPageFactory(pageIndex -> Tables.createPage_sec_reply(pageIndex, 15, "getReplySecreply", replyId, socket, oos, iis));
                                    } else {
                                        label_mess.setText("回复失败");
                                    }
                                } catch (IOException | ClassNotFoundException ex) {
                                    ex.printStackTrace();
                                    label_mess.setText("回复失败");
                                }
                            }
                        }
                );


            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(group, 600, 600);
        //场景放到舞台中
        stage.setScene(scene);
        stage.setX(700);//出现在屏幕中的位置
        stage.setY(200);
        stage.setResizable(false);
        stage.show();
    }

    public static void unfoll_author(String authorname, Label mess, Pagination pagination_like, ObjectOutputStream oos, ObjectInputStream iis) throws FileNotFoundException {
        Stage stage1 = new Stage();
        Button unfoll = new Button("取消关注");
        VBox vBox = new VBox();
        vBox.getChildren().add(unfoll);
        Group group = new Group(vBox);
        setHandle(unfoll);
        unfoll.setOnAction(e ->
        {
            try {
                Command command = new Command("unFollowUser", new String[]{userName, authorname});
                oos.writeObject(command);
                oos.flush();

                // 接收查询结果
                Response response = (Response) iis.readObject();
                if (response.responseType.equals("true")) {
                    mess.setText("取消关注成功!");
                    pagination_like.setPageFactory(pageIndex -> {
                        try {
                            return Tables.createPage_Follow(pageIndex, 10, pagination_like, mess, "getUserFollowBy", oos, iis);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            return null;
                        }
                    });
                    stage1.close();
                } else {
                    mess.setText("您还未关注过该作者");
                    stage1.close();
                }
            } catch (Exception ex) {
                String messagew = ex.getMessage();
                System.out.println(messagew);
            }
        });

        Point pp = MouseInfo.getPointerInfo().getLocation();
        Scene scene = new Scene(group, 200, 100);
        //场景放到舞台中
        stage1.setScene(scene);
        stage1.setX(pp.getX());//出现在屏幕中的位置
        stage1.setY(pp.getY());
        stage1.setResizable(false);
        stage1.show();


    }

    public static void setHandle(Button button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
            setBtn_tp(button, "red", 20);
        });
        button.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
            setBtn_tp(button, "cadetblue", 20);
        });

    }

    public static void setBtn_tp(Button button, String color, int size) {//透明的button
        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background-radius: " + size + ";" +
                        "-fx-text-fill: " + color + ";" +
                        "-fx-font-family: 'Microsoft YaHei UI';" +
                        "-fx-font-size: " + size + ";"
        );
    }


    public static void signUp(Stage stage, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {//注册
        Button button = new Button("注册");
        buttonStyle(button);

        Button button_back = new Button("<--登录");
        setBtn_tp(button_back, "cadetblue", 20);

        Label user = new Label("User name:");
        user.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        Label i = new Label("User id:");
        i.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        Label p = new Label("Phone:");
        p.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        Label p1 = new Label("Password:");
        p1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        Label p2 = new Label("Password:");
        p2.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        TextField username = new TextField();//文本框
        username.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        TextField id = new TextField();//
        id.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        TextField phone = new TextField();
        phone.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        PasswordField password1 = new PasswordField();//密码框
        password1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        PasswordField password2 = new PasswordField();//
        password2.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        username.setPromptText("Enter your user name(length 6-20)");
        id.setPromptText("Enter your id (length 18)");
        phone.setPromptText("Enter your phone number (length 11)");
        password1.setPromptText("Enter your password (length 6-20)");
        password2.setPromptText("Please enter your password again");

        //布局
//        HBox hBox = new HBox(text);
//自由布局
        VBox group = new VBox(20);
        group.setPadding(new Insets(20, 70, 10, 50));
        Label wrongMess1 = new Label("");
        wrongMess1.setTextFill(Color.web("#FF4136"));
        wrongMess1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
        group.getChildren().addAll(user, username, i, id, p, phone, p1, password1, p2, password2, button, button_back, wrongMess1);
        //button.setLayoutX(330);

        button.setOnAction(e -> {
            try {
                implementMethod.isValidInformation(username.getText(), id.getText(), phone.getText(), password1.getText(), password2.getText());

                Command command = new Command("registerNewUser", new String[]{username.getText(), id.getText(), phone.getText(), password1.getText(), password2.getText()});
//                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(command);
                oos.flush();

                // 接收查询结果
                Response response = (Response) iis.readObject();
                if (response.responseType.equals("true")) {
                    signIn(stage, socket, oos, iis);
                } else {
                    wrongMess1.setText(response.responseContent);
                }
            } catch (Exception ex) {
                String message = ex.getMessage();
                wrongMess1.setText(message);
            }
        });
        button_back.setOnAction(e -> {
            signIn(stage, socket, oos, iis);
        });
        Scene scene = new Scene(group, 700, 750);

        //场景放到舞台中
        stage.setScene(scene);
        stage.setX(700);//出现在屏幕中的位置
        stage.setY(200);

        stage.show();

    }

    public static void signIn(Stage stage, Socket socket, ObjectOutputStream oos, ObjectInputStream iis) {//登录
        Button button = new Button("登录");
        buttonStyle(button);

        Button button_back = new Button("<--注册");
        setBtn_tp(button_back, "cadetblue", 20);

        Label user = new Label("User name:");
        user.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        Label p1 = new Label("Password:");
        p1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        TextField username = new TextField();//文本框
        username.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        PasswordField password1 = new PasswordField();//密码框
        password1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        username.setPromptText("Enter your user name");

        VBox group = new VBox(20);
        group.setPadding(new Insets(20, 70, 10, 50));
        Label wrongMess1 = new Label("");
        wrongMess1.setTextFill(Color.web("#FF4136"));
        wrongMess1.setFont(javafx.scene.text.Font.font("Comic Sans MS", FontWeight.BOLD, 15));
        group.getChildren().addAll(user, username, p1, password1, button, button_back, wrongMess1);
        //button.setLayoutX(330);

        button.setOnAction(e -> {
            try {
                System.out.println(password1.getText());
                Command command = new Command("isUserValid", new String[]{username.getText(), password1.getText()});
//                oos = (ObjectOutputStream) socket.getOutputStream();
//                ObjectOutputStream oos;
                oos.writeObject(command);
                oos.flush();

                // 接收查询结果
                Response response = (Response) iis.readObject();

                if (Objects.equals(response.responseType, "true")) {
                    userName = username.getText();
                    home_page(stage, socket, oos, iis);
                } else {
                    wrongMess1.setText(response.responseContent);
                }
            } catch (Exception ex) {
                String message = ex.getMessage();
                wrongMess1.setText(message);
            }

        });
        button_back.setOnAction(e -> {
            signUp(stage, socket, oos, iis);
        });
        Scene scene = new Scene(group, 700, 700);
        //场景放到舞台中
        stage.setScene(scene);
        stage.setX(700);//出现在屏幕中的位置
        stage.setY(200);

        stage.show();
    }

}

