package performance.monitoring.tracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;

public class PerformanceMonitoringTracker extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        primaryStage.setTitle("Performance Monitoring Tracker");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();

        // Close serial port on application exit
        FXMLDocumentController controller = loader.getController();
        primaryStage.setOnCloseRequest(event -> {
            controller.closeSerialPort();
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
