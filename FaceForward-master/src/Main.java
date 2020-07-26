import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {

    private Stage stage;
    private boolean faceDetected = false;
    private boolean secondaryText = false;
    private boolean helpButton = false;
    private boolean captured = false;
    private Text userDisplayTextMain;
    private Text userDisplayTextSub;
    private Text userDisplayTextHelp;
    private HBox buttonHBox;
    private String profileName = "Jill Bao";
    private static FaceID faceID = new FaceID();
    private WebcamIO webcamIO = new WebcamIO();
    private static Items item;
    private Image profileImage = new Image("0.png");
    private ImageView profileImageView;
    private static Profile profile;
    private static Scene mainScene;
    private boolean isTheySame;


    public final Timer clockTimer = new Timer();

    private HashMap<String, Profile> faceMap = new HashMap<>(); // dont delete


    private static final String jillBao =
            "{\"url\":\"https://images.squarespace-cdn.com/content/v1/5af0d64d4cde7ab9a2e29635/1560631263524-ECUQ1RR9KZ71BKV9GCA4/ke17ZwdGBToddI8pDm48kLR2rgEg1jPu1GtjV4K1vZ97gQa3H78H3Y0txjaiv_0fDoOvxcdMmMKkDsyUqMSsMWxHk725yiiHCCLfrh8O1z4YTzHvnKhyp6Da-NYroOW3ZGjoBKy3azqku80C789l0scl71iiVnMuLeEyTFSXT3qwhEKW1IfUKL5GUNLdDa9MjuPXcXiDenG_NSvE-2lGCg/NSCC-39.jpg\"}";
    private static final String jillbao2 =
            "{\"url\":\"https://media-exp1.licdn.com/dms/image/C5603AQFxKEZcNlZWYA/profile-displayphoto-shrink_200_200/0?e=1585180800&v=beta&t=ewtVAbYd4n8Moi8m_rNnMF7jfU5Z-O43GRpwbXqQntQ\"}";
    private static final String jill =
            "src/unnamed.jpg";

    public static void main(String[] args) {
        item = new Items("Macbook","laptops");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        faceMapSetup();
        stage = primaryStage;
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());


        userDisplayTextMain = new Text("Step up to begin your personalized experience.");
        userDisplayTextMain.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        userDisplayTextMain.setFill(Color.WHITE);
        userDisplayTextMain.setStyle("-fx-text-fill: white;");
        userDisplayTextMain.setX(300);
        userDisplayTextMain.setY(200);
        userDisplayTextMain.setTextAlignment(TextAlignment.CENTER);

        userDisplayTextSub = new Text(item.getName().substring(0,15) + "     Iphone 11      Airpod Pros");
        userDisplayTextSub.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        userDisplayTextSub.setFill(Color.WHITE);
        userDisplayTextSub.setTextAlignment(TextAlignment.CENTER);
        userDisplayTextSub.setVisible(false);

        userDisplayTextHelp = new Text("Would you like employee help?");
        userDisplayTextHelp.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        userDisplayTextHelp.setFill(Color.WHITE);
        userDisplayTextHelp.setTextAlignment(TextAlignment.CENTER);
        userDisplayTextHelp.setVisible(false);

        Button yesButton = new Button("Yes");
        yesButton.setAlignment(Pos.CENTER_LEFT);
        yesButton.setStyle("-fx-background-color: \n" +
                        "        #090a0c,\n" +
                        "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                        "        linear-gradient(#20262b, #191d22),\n" +
                        "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                        "    -fx-background-radius: 5,4,3,5;\n" +
                        "    -fx-background-insets: 0,1,2,0;\n" +
                        "    -fx-text-fill: white;\n" +
                        "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                        "    -fx-font-family: \"Arial\";\n" +
                        "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                        "    -fx-font-size: 12px;\n" +
                        "    -fx-padding: 10 20 10 20;");
        yesButton.setOnAction(e -> yesButtonClick());

        Button noButton = new Button("No");
        noButton.setAlignment(Pos.CENTER_RIGHT);
        noButton.setStyle("-fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 10 20 10 20;");
        noButton.setOnAction(e -> noButtonClick());

        buttonHBox = new HBox(70);
        buttonHBox.getChildren().add(yesButton);
        buttonHBox.getChildren().add(noButton);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setVisible(false);

        Button captureButton = new Button("Capture Face");
        captureButton.setAlignment(Pos.BOTTOM_CENTER);
        captureButton.setStyle("-fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 10 20 10 20;");
        captureButton.setOnAction(e -> captureButtonClick());


        VBox textBox = new VBox();
        textBox.getChildren().add(userDisplayTextMain);
        textBox.getChildren().add(userDisplayTextSub);
        textBox.getChildren().add(userDisplayTextHelp);
        textBox.getChildren().add(buttonHBox);
        textBox.getChildren().add(captureButton);
        textBox.setSpacing(70);
        textBox.setAlignment(Pos.CENTER);

        StackPane userPane = new StackPane(textBox);
        userPane.setPrefSize(2560,1600);
        userPane.setAlignment(Pos.CENTER);
        userPane.setStyle("-fx-background-image: url('UserDisplayBackground.png');" + "-fx-background-size: stretch;" + "-fx-background-size: no-repeat;" + "-fx-background-size: center;");

        //TODO add list of String of recommendation products, then run a for loop here to add them to names.
        ObservableList<String> names = FXCollections.observableArrayList(
                "MacBook Pro", "Iphone 11", "Airpod Pros", "Canon EOS R", "USB Adapter", "Iphone Cover", "Portable Charger");
        ListView<String> analyticBox = new ListView<>(names);
        analyticBox.setMaxSize(300, 370);

        //TODO use profile.getName().... ect.
        ObservableList<String> names1 = FXCollections.observableArrayList(
                "Name: Jill Bao", "Email: Jillbaobao@gmail.com", "Past Purchases: Iphone 6", "Canon T6S", "Beats Headphones");

        ListView<String> profileBox = new ListView<>(names1);
        profileBox.setMaxSize(300, 370);


        profileImageView = new ImageView();
        profileImageView.setImage(profileImage);
        profileImageView.setPreserveRatio(true);
        profileImageView.setFitHeight(250);
        profileImageView.setFitWidth(300);

        Label nameLabel = new Label(profileName);
        nameLabel.setFont(new Font(20));
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setPrefSize(300, 100);
        nameLabel.setMaxSize(300, 100);
        nameLabel.setTextAlignment(TextAlignment.CENTER);

        VBox customerBox = new VBox(10);
        customerBox.getChildren().add(profileImageView);
        customerBox.getChildren().add(nameLabel);
        customerBox.setAlignment(Pos.CENTER);

        customerBox.setAlignment(Pos.CENTER_LEFT);

        HBox dataBox = new HBox();
        dataBox.getChildren().add(customerBox);
        dataBox.getChildren().add(profileBox);
        dataBox.getChildren().add(analyticBox);
        dataBox.setSpacing(180);
        dataBox.setPadding(new Insets(270, 150, 100, 150));
        dataBox.setAlignment(Pos.CENTER);

        StackPane employeePane = new StackPane(dataBox);
        userPane.setPrefSize(2560,1600);
        userPane.setAlignment(Pos.CENTER);
        employeePane.setStyle("-fx-background-image: url('EmployeeDisplayBackground.png');" + "-fx-background-size: stretch;" + "-fx-background-size: no-repeat;" + "-fx-background-size: center;");

        Tab userTab = new Tab("User Interface");
        userTab.setClosable(false);
        userTab.setContent(userPane);
        userTab.setStyle("-fx-text-base-color: white;"+" -fx-background-color: grey;");

        userTab.setOnSelectionChanged(e->updatePls());


        Tab employeeTab = new Tab("Employee Interface");
        employeeTab.setClosable(false);
        employeeTab.setContent(employeePane);
        employeeTab.setStyle("-fx-text-base-color: white;"+" -fx-background-color: grey;");

        TabPane tabScreen = new TabPane(userTab, employeeTab);
        tabScreen.setOnKeyPressed(e -> buttonClick());

        mainScene = new Scene(tabScreen, 1600, 2560);

        primaryStage.setTitle("BestAI V0.1");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // This is where the program periodically refreshes the GUI and checks global variables.
        // You can set text fields and also their visibility here, depending on events.

        clockTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (isTheySame || faceDetected) {
                        userDisplayTextMain.setText("Hi Jill. Here are some recommendations.");
                    } else {
                        userDisplayTextMain.setText("Step up to begin your personalized experience.");
                    }

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (secondaryText || isTheySame) {
                        userDisplayTextSub.setVisible(true);
                    } else {
                        userDisplayTextSub.setVisible(false);
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (helpButton || isTheySame) {
                        userDisplayTextHelp.setVisible(true);
                        buttonHBox.setVisible(true);
                    } else {
                        userDisplayTextHelp.setVisible(false);
                        buttonHBox.setVisible(false);
                    }
//<<<<<<< HEAD
                    profileImage = new Image("file:/Users/alansmacbook/Desktop/BizHacks-2020/resources/0.png");
                    profileImageView.setImage(profileImage);
                    primaryStage.show();


                });
            }
        }, 0,500);
    }

    public void buttonClick() {
        faceDetected = true;
        secondaryText = true;
        helpButton = true;
    }

//<<<<<<< HEAD
    private void updatePls() {
        profileImage = new Image("file:/Users/alansmacbook/Desktop/BizHacks-2020/resources/0.png");
        profileImageView.setImage(profileImage);

    }

    public void captureButtonClick() {
        webcamIO.getImage();
        profileImage = new Image("file:/Users/alansmacbook/Desktop/BizHacks-2020/resources/0.png");
        profileImageView.setImage(profileImage);
        FaceRec();
    }

    public void yesButtonClick() {


        Text userDisplayTextYes = new Text("A customer representative is on the way!");
        userDisplayTextYes.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        userDisplayTextYes.setFill(Color.WHITE);
        userDisplayTextYes.setStyle("-fx-text-fill: white;");
        userDisplayTextYes.setX(300);
        userDisplayTextYes.setY(200);
        userDisplayTextYes.setTextAlignment(TextAlignment.CENTER);

        Button returnButton = new Button("Return");
        returnButton.setAlignment(Pos.BOTTOM_CENTER);
        returnButton.setPadding(new Insets(10,10,10,10));
        returnButton.setStyle("-fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 10 20 10 20;");
        returnButton.setOnAction(e -> returnButtonClick());


        StackPane userPane = new StackPane(userDisplayTextYes, returnButton);
        userPane.setPrefSize(2560,1600);
        userPane.setAlignment(Pos.CENTER);
        userPane.setStyle("-fx-background-image: url('UserDisplayBackground.png');" + "-fx-background-size: stretch;" + "-fx-background-size: no-repeat;" + "-fx-background-size: center;");

        Scene scene = new Scene(userPane, 1600, 2560);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public void noButtonClick() {
        faceDetected = false;
        secondaryText = false;
        helpButton = false;
    }

    public void returnButtonClick() {
        stage.setScene(mainScene);
        stage.setResizable(false);
        faceDetected = false;
        helpButton = false;
        secondaryText = false;
        stage.show();
    }

    public void FaceRec(){
        String faceId1 = FaceID.FaceRecognize("/Users/alansmacbook/Desktop/BizHacks-2020/resources/0.png", true);
        System.out.println("faceid1 works");
        String faceId2 = FaceID.FaceRecognize(jillBao, false);
        System.out.println("faceid2 works");

        isTheySame = FaceID.FaceCompare(faceId1, faceId2);
        System.out.println(isTheySame);
    }

    private void faceMapSetup(){
        //Profile alex = new Profile("Alex", "Lin", "alxander.lin@gmail.com", "password");
//          Profile jill = new Profile("Jill", "Bao", "jill.ba00@gmail.com", "hehe");
//
//        faceMap.put("88a3f43d-6a56-4323-b26b-765f00a41762", alex);
//        faceMap.put("96179100-afc0-4106-8172-89535c00ecb0", jill);

    }
}
