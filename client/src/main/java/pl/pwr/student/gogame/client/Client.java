package pl.pwr.student.gogame.client;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Client extends Application {
  private Parent createContent() {
    return new StackPane(new Text("Hello World"));
  }

  private void initGUI(Stage s) {
    s.setScene(new Scene(createContent(), 800, 600));
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
