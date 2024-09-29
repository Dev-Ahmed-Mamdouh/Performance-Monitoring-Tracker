package performance.monitoring.tracker;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

public class ProgressBarManager {
    private Arc[] progressArcs;
    private Label[] mainLabels;
    private Label[] subscriptLabels;

    public ProgressBarManager(Arc[] progressArcs, Label[] mainLabels, Label[] subscriptLabels) {
        this.progressArcs = progressArcs;
        this.mainLabels = mainLabels;
        this.subscriptLabels = subscriptLabels;
    }

    public void updateProgressBar(int index, float targetValue, double radius) {
        Arc progressArc = progressArcs[index];
        Label mainLabel = mainLabels[index];
        Label subscriptLabel = subscriptLabels[index];
        
        double currentValue = (progressArc.getLength() / 360) * 100;
        if (Math.abs(currentValue - targetValue) >= 1) {
            double increment = (targetValue - currentValue) * 0.05; // Smooth the update
            progressArc.setLength(progressArc.getLength() + increment);
            currentValue = (progressArc.getLength() / 360) * 100; // Recalculate after changing the arc length
        }

        mainLabel.setText(String.format("%.0f", currentValue));
        subscriptLabel.setText(String.format("%.2f", targetValue - (int) targetValue).substring(1));
        
        updateProgressArcColors(index, progressArc);
    }

    private void updateProgressArcColors(int index, Arc progressArc) {
        double progressValue = progressArc.getLength() / 360 * 100;

        if (index == 2) {
            if (progressValue >= 50) {
                setProgressArcColor(Color.RED, index);
            } else {
                setProgressArcColor(Color.GREEN, index);
            }
        } else if (index == 1) {
            if (progressValue >= 3) {
                setProgressArcColor(Color.RED, index);
            } else if (progressValue >= 2) {
                setProgressArcColor(Color.YELLOW, index);
            } else {
                setProgressArcColor(Color.GREEN, index);
            }
        } else {
            if (progressValue >= 40) {
                setProgressArcColor(Color.RED, index);
            } else if (progressValue >= 30) {
                setProgressArcColor(Color.ORANGE, index);
            } else {
                setProgressArcColor(Color.YELLOW, index);
            }
        }
    }

    private void setProgressArcColor(Color color, int index) {
        progressArcs[index].setStroke(color);
    }
}
