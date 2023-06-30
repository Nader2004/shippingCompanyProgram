package com.example.shippingcompany;

import containers.BigContainer;
import containers.SmallContainer;
import items.Item;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import methods.Calculation;

import java.io.IOException;
import java.util.ArrayList;



public class HelloApplication extends Application {

    // creates the calculation class to use its functionalities
    Calculation calculation = new Calculation();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Shipping Company");

        // Create Splash Screen
        Label splashLabel = new Label("Shipping Company");
        splashLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        // Add scale and fade animations
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), splashLabel);
        scaleTransition.setByX(1.5);
        scaleTransition.setByY(1.5);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), splashLabel);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        // Splash scene
        VBox splashBox = new VBox(splashLabel);
        splashBox.setAlignment(Pos.CENTER);
        Scene splashScene = new Scene(splashBox, 500, 400);
        primaryStage.setScene(splashScene);
        primaryStage.show();

        scaleTransition.play();
        fadeTransition.play();

        fadeTransition.setOnFinished(e -> {
            // Create UI elements for main scene
            VBox mainBox = createMainScene(primaryStage);

            VBox root = new VBox();
            root.getChildren().addAll(mainBox);
            VBox.setVgrow(mainBox, Priority.ALWAYS);

            // Show main scene
            Scene mainScene = new Scene(root, 500, 400);
            primaryStage.setScene(mainScene);
        });
    }

    public String getProductIconPath(String product) {
        return switch (product) {
            case "Laptop" -> "laptopIcon.png";
            case "Mouse" -> "MouseIcon.png";
            case "LCD Screen" -> "lcdScreen.png";
            default -> "desktopIcon.png";
        };
    }

    private VBox createContainerBox(String iconPath, String containerName, int count) {
        Image image = new Image(iconPath, 100, 100, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        Label containerLabel = new Label(containerName + " " + count + "x");
        containerLabel.setWrapText(true);
        containerLabel.setTextAlignment(TextAlignment.CENTER);

        VBox vbox = new VBox(20, imageView,  containerLabel);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    private VBox createMainScene(Stage primaryStage) {
        VBox mainBox = new VBox();
        mainBox.setPadding(new Insets(10, 10, 10, 10));
        mainBox.setSpacing(10);

        // Title
        Label titleLabel = new Label("Choose your products");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        mainBox.getChildren().add(titleLabel);

        // Create a list to store product boxes
        ArrayList<HBox> productBoxes = new ArrayList<>();

        // For each product, create a spinner for quantity and add it to a styled box
        String[] products = {"Laptop", "Mouse", "LCD Screen", "Desktop"};
        int productIndex = 0;
        for (String product : products) {
            final int index = productIndex;

            Spinner<Integer> spinner = new Spinner<>(0, 1000, 0);
            spinner.setEditable(true);
            spinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {

                if (!newValue.matches("\\d*")) {
                    spinner.getEditor().setText(oldValue);

                    if (Integer.parseInt( newValue) > Integer.parseInt( oldValue)) {
                        Item item = new Item(product);
                        calculation.addItem(item);
                        System.out.println(calculation.getItems());
                    } else if (Integer.parseInt( newValue) < Integer.parseInt( oldValue)) {  // If the spinner value has decreased
                        calculation.removeItem(index);
                        System.out.println(calculation.getItems());
                    }

                }
            });

            spinner.valueProperty().addListener((obs, oldValue, newValue) -> {


                if (newValue > oldValue) {
                    for (int i = 0; i < newValue - oldValue; i++) {
                        // Create a new item
                        Item item = new Item(product);
                        // Add the item to the list
                        calculation.addItem(item);
                    }
                }
                // Check if the value has been decreased
                else if (newValue < oldValue) {
                    for (int i = 0; i < oldValue - newValue; i++) {
                        // Remove an item from the list
                        calculation.removeItem(index);
                    }
                }

            });



            Label label = new Label(product);
            label.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            String imagePath = getProductIconPath(product);

            ImageView icon = new ImageView(new Image(imagePath, 40, 40, true, true)); // placeholder for a real icon
            HBox hBox = new HBox(10, icon, label);
            hBox.setPadding(new Insets(0, 0, 0, 10)); // padding for the (product icon & name)

            HBox box = new HBox(5, hBox, spinner);
            box.setPadding(new Insets(10));
            box.setAlignment(Pos.CENTER);
            box.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px"); // slightly bolder with rounded corners
            HBox.setHgrow(hBox, Priority.ALWAYS);

            // Add the product box to the list
            productBoxes.add(box);
            mainBox.getChildren().add(box);

            productIndex++;
        }


        // Create button to switch to order preview scene
        Button previewButton = new Button("Preview order");
        previewButton.setOnAction(e -> {


            VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(10, 10, 10, 10));
            vbox.setAlignment(Pos.CENTER);

            // Add title
            Label title = new Label("Your Order");
            title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
            vbox.getChildren().add(title);

            for (HBox productBox : productBoxes) { // For each product box
                // Get the product name, icon and quantity
                Label productNameLabel = (Label) ((HBox) productBox.getChildren().get(0)).getChildren().get(1);
                ImageView productIcon = (ImageView) ((HBox) productBox.getChildren().get(0)).getChildren().get(0);

                @SuppressWarnings("unchecked")
                Spinner<Integer> quantitySpinner = (Spinner<Integer>) productBox.getChildren().get(1);

                long count = calculation.getItems().stream()
                        .filter(item -> item.getName().equals(productNameLabel.getText()))
                        .count();

                // If the quantity is more than 0, add the product to the order
                if (quantitySpinner.getValue() > 0) {
                    HBox orderItemBox = new HBox(10);
                    orderItemBox.setAlignment(Pos.CENTER);
                    Label orderItemLabel = new Label(productNameLabel.getText() + ": " + count);
                    orderItemBox.getChildren().addAll(new ImageView(productIcon.getImage()), orderItemLabel);
                    vbox.getChildren().add(orderItemBox);
                }
            }

            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(vbox);

            if (vbox.getChildren().size() == 1) { // Only title label is in vbox
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Products");
                alert.setHeaderText(null);
                alert.setContentText("No products added to the order!");
                alert.showAndWait();
            } else {
                // Create back and next buttons
                HBox hBoxButtons = new HBox(10);
                hBoxButtons.setAlignment(Pos.BOTTOM_RIGHT);
                hBoxButtons.setPadding(new Insets(10, 10, 10, 10));

                Button backButton = new Button("Back");
                backButton.setOnAction(ev -> {
                    calculation.setItems(new ArrayList<>());
                    primaryStage.setScene(new Scene(createMainScene(primaryStage), 500, 400));
                });

                Button nextButton = new Button("Next");

                nextButton.setOnAction(x -> {
                    // Use your actual calculation
                    calculation.bestShipping();  // Determine the best shipping method

                    // Title
                    Label finalTitle = new Label("Best Shipping Method");
                    finalTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 20;");
                    finalTitle.setAlignment(Pos.TOP_CENTER);

                    // Add the price label
                    int price = calculation.shippingPrice();
                    Label priceLabel = new Label("Shipment Price: " + price + "$");
                    priceLabel.setStyle("-fx-font-size: 18px; -fx-padding: 20;");
                    priceLabel.setAlignment(Pos.TOP_CENTER);


                    // Horizontal layout for the three types of containers
                    HBox hbox = new HBox(20);
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setSpacing(20);  // add spacing between the icon and the label

                    // Vertical layouts for each type of container
                    if (!calculation.getBigContainers().isEmpty()) {
                        VBox bigContainerBox = createContainerBox("bigContainer.png", "Big Container", calculation.getBigContainers().size());
                        hbox.getChildren().add(bigContainerBox);
                    }
                    if (!calculation.getSmallContainers().isEmpty()) {
                        VBox smallContainerBox = createContainerBox("smallContainer.png", "Small Container", calculation.getSmallContainers().size());
                        hbox.getChildren().add(smallContainerBox);
                    }


                    Button restartButton = new Button("Restart");
                    restartButton.setOnAction(event -> {
                        // Logic to restart from the first scene
                        VBox finalMainBox = createMainScene(primaryStage);
                        VBox root = new VBox();
                        root.getChildren().addAll(finalMainBox);
                        VBox.setVgrow(finalMainBox, Priority.ALWAYS);
                        primaryStage.setScene(new Scene(root, 500, 400));
                    });
                    HBox buttonBox = new HBox(20,  restartButton);
                    buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
                    buttonBox.setSpacing(10);  // add spacing between buttons

                    VBox finalVbox = new VBox(20, finalTitle, priceLabel, hbox, buttonBox);
                    finalVbox.setAlignment(Pos.CENTER);
                    finalVbox.setPadding(new Insets(20));

                    Scene scene = new Scene(finalVbox, 500, 400);

                    calculation.setItems(new ArrayList<Item>());
                    calculation.setBigContainers(new ArrayList<BigContainer>());
                    calculation.setSmallContainers(new ArrayList<SmallContainer>());

                    primaryStage.setScene(scene);
                });


                // Here you can set action for next button
                hBoxButtons.getChildren().addAll(backButton, nextButton);

                borderPane.setBottom(hBoxButtons);

                Scene previewScene = new Scene(borderPane, 500, 400);
                primaryStage.setScene(previewScene);
            }
        });



        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setPadding(new Insets(10, 10, 10, 10)); // padding for the preview button
        hBox.getChildren().add(previewButton);

        mainBox.getChildren().add(hBox);
        return mainBox;
    }
}
