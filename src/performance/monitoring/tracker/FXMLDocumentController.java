package performance.monitoring.tracker;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;

public class FXMLDocumentController implements SerialPortManager.SerialDataListener {

    @FXML
    private Circle outerCircle1, outerCircle2, outerCircle3, outerCircle4, outerCircle5, outerCircle6;
    @FXML
    private Arc progressArc1, progressArc2, progressArc3, progressArc4, progressArc5, progressArc6;
    @FXML
    private Label topLabel1, topLabel2, topLabel3, topLabel4, topLabel5, topLabel6;
    @FXML
    private Label mainNumberLabel1, mainNumberLabel2, mainNumberLabel3, mainNumberLabel4, mainNumberLabel5, mainNumberLabel6;
    @FXML
    private Label subscriptLabel1, subscriptLabel2, subscriptLabel3, subscriptLabel4, subscriptLabel5, subscriptLabel6;

    private float[] targetValues = new float[6];
    private SerialPortManager serialPortManager;
    private ProgressBarManager progressBarManager;
    private SerialDataProcessor serialDataProcessor;

    public void initialize() {
        Arc[] progressArcs = new Arc[]{progressArc1, progressArc2, progressArc3, progressArc4, progressArc5, progressArc6};
        Label[] mainLabels = new Label[]{mainNumberLabel1, mainNumberLabel2, mainNumberLabel3, mainNumberLabel4, mainNumberLabel5, mainNumberLabel6};
        Label[] subscriptLabels = new Label[]{subscriptLabel1, subscriptLabel2, subscriptLabel3, subscriptLabel4, subscriptLabel5, subscriptLabel6};

        progressBarManager = new ProgressBarManager(progressArcs, mainLabels, subscriptLabels);
        serialDataProcessor = new SerialDataProcessor(targetValues);
        serialPortManager = new SerialPortManager("COM3", this);

        startProgressUpdater();
    }

    private void startProgressUpdater() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateProgressBars();
            }
        };
        timer.start();
    }

    private void updateProgressBars() {
        synchronized (targetValues) {
            for (int i = 0; i < targetValues.length; i++) {
                progressBarManager.updateProgressBar(i, targetValues[i], 90);
            }
        }
    }

    @Override
    public void onSerialDataReceived(String data) {
        serialDataProcessor.processSerialData(data);
    }

    public void closeSerialPort() {
        serialPortManager.closeSerialPort();
    }
}
