package pl.pwr.student.gogame.client;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
    form.setAlignment(Pos.CENTER);

    Label info = new Label("");

    Button b = new Button("Dołącz");
    b.setOnMouseClicked(e -> {
      String ip = t.getText();
      try {
        info.setText("Łączenie z " + ip);
        rootScene.setRoot(playingView());
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

  // TODO: pobieranie z GameInfo
  private static final int BOARD_SIZE = 19;
  private static final int CELL_SIZE = 40;

  private Parent playingView() {
    BorderPane root = new BorderPane();

    GridPane board = new GridPane();
    board.setMaxHeight(BOARD_SIZE * CELL_SIZE);
    board.setMaxWidth(BOARD_SIZE * CELL_SIZE);
    board.setAlignment(Pos.TOP_LEFT);
    board.setStyle("-fx-background-color: #f76f3a;");

    for (int row = 0; row < BOARD_SIZE; row++) {
      for (int col = 0; col < BOARD_SIZE; col++) {
        StackPane cell = createCell(row, col);
        board.add(cell, col, row);
      }
    }

    root.setCenter(board);
    return root;
  }

  private StackPane createCell(int row, int col) {
    StackPane cell = new StackPane();
    cell.setPrefSize(CELL_SIZE, CELL_SIZE);

    cell.setOnMouseClicked(e -> {
      // TODO: Kolor pobieramy z gameInfo.board
      // TODO: wysłanie put,row,col w tym miejscu do serwera
      Circle stone = new Circle(14, Color.BLACK);
      cell.getChildren().add(stone);
    });

    Pane lines = new Pane();
    lines.setPrefSize(CELL_SIZE, CELL_SIZE);

    double mid = CELL_SIZE / 2.0;

    if (col > 0)
      lines.getChildren().add(new Line(0, mid, mid, mid));
    if (col < BOARD_SIZE - 1)
      lines.getChildren().add(new Line(mid, mid, CELL_SIZE, mid));
    if (row > 0)
      lines.getChildren().add(new Line(mid, 0, mid, mid));
    if (row < BOARD_SIZE - 1)
      lines.getChildren().add(new Line(mid, mid, mid, CELL_SIZE));

    lines.getChildren().forEach(n -> ((Line) n).setStroke(Color.BLACK));

    cell.getChildren().add(lines);

    return cell;
  }

  private Scene rootScene;

  private void initGUI(Stage s) {
    s.setTitle("GO");
    rootScene = new Scene(welcomeView());
    s.setScene(rootScene);
    s.show();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    initGUI(primaryStage);
  }
}
