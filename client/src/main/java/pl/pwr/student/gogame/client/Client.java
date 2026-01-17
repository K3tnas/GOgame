package pl.pwr.student.gogame.client;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import pl.pwr.student.gogame.client.board.ConnectionManager;
import pl.pwr.student.gogame.client.board.GUICommands.GUICommand;
import pl.pwr.student.gogame.client.board.GUICommands.PutStone;
import pl.pwr.student.gogame.client.board.GUICommands.RedrawBoard;
import pl.pwr.student.gogame.client.board.GUICommands.Say;
import pl.pwr.student.gogame.model.board.Board;
import pl.pwr.student.gogame.model.board.Team;

public class Client extends Application {
  private ConnectionManager connMan;
  private Board board;
  private GridPane boardPane;

  public void setBoard(Board b) {
    this.board = b;
  }

  /**
   * Tworzy pierwszy widok pokazywany przy uruchomieniu aplikacji
   * @return root
   */
  private Parent welcomeView() {
    BorderPane root = new BorderPane();

    VBox form = new VBox();

    Label tLabel = new Label("Adres IP:");
    form.getChildren().add(tLabel);

    TextField ipInputField = new TextField();
    ipInputField.setPromptText("IP");
    ipInputField.setMaxWidth(400);
    form.getChildren().add(ipInputField);
    form.setAlignment(Pos.CENTER);

    Label info = new Label("");

    Button connectButton = new Button("Dołącz");
    connectButton.setOnMouseClicked(e -> {
      String ip = ipInputField.getText();
      info.setText("Łączenie z " + ip);
      try {
        connMan = new ConnectionManager(ip, this);
        connMan.start();
        rootScene.setRoot(playingView());
      } catch (IOException exc) {
        info.setText("Nie udało się połączyć z " + ip);
      }
    });
    form.getChildren().add(connectButton);

    form.getChildren().add(info);

    root.setCenter(form);

    return root;
  }

  private static final int CELL_SIZE = 40;
  // TODO: ustawianie z tego co przyszło po SIZE,
  private static final int BOARD_SIZE = 11;

  private int boardPositionToGridPaneIndex(int row, int col) {
    return row * BOARD_SIZE + col;
  }

  private Parent playingView() {
    BorderPane root = new BorderPane();

    boardPane = new GridPane();
    int boardSize = BOARD_SIZE;
    boardPane.setMaxHeight(boardSize * CELL_SIZE);
    boardPane.setMaxWidth(boardSize * CELL_SIZE);
    boardPane.setAlignment(Pos.TOP_LEFT);
    boardPane.setStyle("-fx-background-color: #f76f3a;");

    for (int row = 0; row < boardSize; row++) {
      for (int col = 0; col < boardSize; col++) {
        StackPane cell = createCell(row, col, Team.EMPTY);
        boardPane.add(cell, col, row);
      }
    }

    HBox boardAndChatContainer = new HBox();
    boardAndChatContainer.getChildren().add(boardPane);
    boardAndChatContainer.getChildren().add(chatWindow());

    root.setCenter(boardAndChatContainer);
    return root;
  }

  private StackPane createCell(int row, int col, Team t) {
    StackPane cell = new StackPane();
    cell.setPrefSize(CELL_SIZE, CELL_SIZE);

    Pane lines = new Pane();
    lines.setPrefSize(CELL_SIZE, CELL_SIZE);

    double mid = CELL_SIZE / 2.0;

    int boardSize = BOARD_SIZE;
    if (col > 0)
      lines.getChildren().add(new Line(0, mid, mid, mid));
    if (col < boardSize - 1)
      lines.getChildren().add(new Line(mid, mid, CELL_SIZE, mid));
    if (row > 0)
      lines.getChildren().add(new Line(mid, 0, mid, mid));
    if (row < boardSize - 1)
      lines.getChildren().add(new Line(mid, mid, mid, CELL_SIZE));

    lines.getChildren().forEach(n -> ((Line) n).setStroke(Color.BLACK));

    cell.getChildren().add(lines);

    if (t == Team.EMPTY) {
      cell.setOnMouseClicked(e -> {
        connMan.queueCommand(new PutStone(row, col));
      });
    } else {
      Circle c = new Circle(14, (t == Team.BLACK ? Color.BLACK : Color.WHITE));
      cell.getChildren().add(c);
    }

    return cell;
  }

  private VBox messagesBox;
  private BorderPane chatWindow() {
    messagesBox = new VBox(8);
    messagesBox.setPadding(new Insets(10));

    ScrollPane scrollPane = new ScrollPane(messagesBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

    TextField input = new TextField();

    Button send = new Button("Send");

    send.setOnAction(e -> {
      connMan.queueCommand(new Say(input.getText())); 
      sayInChat(input.getText());
    });

    HBox inputBox = new HBox(8, input, send);
    inputBox.setPadding(new Insets(10));
    HBox.setHgrow(input, Priority.ALWAYS);

    BorderPane root = new BorderPane();
    root.setCenter(scrollPane);
    root.setBottom(inputBox);

    return root;
  }

  private Scene rootScene;

  private void initGUI(Stage s) {
    s.setTitle("GO");
    rootScene = new Scene(welcomeView());
    s.setScene(rootScene);
    s.setMinWidth(800);
    s.setMinHeight(600);
    s.show();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    initGUI(primaryStage);
  }

  public void redrawBoard() {
    boardPane.getChildren().clear();
    for (int row = 1; row <= BOARD_SIZE; ++row) {
      for (int col = 1; col <= BOARD_SIZE; ++col) {
        boardPane.getChildren().add(createCell(row, col, board.getField(col, row).getTeam()));
      }
    }
  }

  public void sayInChat(String message) {
    messagesBox.getChildren().add(new Label(message));
  }
}
