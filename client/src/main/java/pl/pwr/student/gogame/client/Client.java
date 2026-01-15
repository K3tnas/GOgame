package pl.pwr.student.gogame.client;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Client extends Application {
  private GoClient goClient;

  /**
   * Tworzy pierwszy widok pokazywany przy uruchomieniu aplikacji
   * @return root
   */
  private Parent welcomeView() {
    BorderPane root = new BorderPane();

    VBox form = new VBox();

    Label tLabel = new Label("Adres IP:");
    form.getChildren().add(tLabel);

    TextField t = new TextField();
    t.setPromptText("IP");
    t.setMaxWidth(400);
    form.getChildren().add(t);

    Label info = new Label("");

    Button b = new Button("Dołącz");
    b.setOnMouseClicked(e -> {
      String ip = t.getText();
      try {
        info.setText("Łączenie z " + ip);
        goClient = new GoClient(ip);
        goClient.connect();
      } catch (Exception exc) {
        info.setText("Nie udało się połączyć z " + ip);
      }
      info.setText("Połączono");
    });
    form.getChildren().add(b);

    form.getChildren().add(info);

    root.setCenter(form);

    return root;
  }

  private Parent playingView() {
   GridPane boardDisplay = new GridPane(); 

   return boardDisplay;
  }

  private void initGUI(Stage s) {
    // s.setScene(new Scene(welcomeView(), 800, 600));
    s.setScene(new Scene(playingView(), 800, 600));
    s.setTitle("GO");

    s.show();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    initGUI(primaryStage);

    // GoClient client = new GoClient(args.getFirst());
    // client.connect();
  }
}
