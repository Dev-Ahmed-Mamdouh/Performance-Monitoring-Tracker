package performance.monitoring.tracker;

public class SerialDataProcessor {
    private float[] targetValues;

    public SerialDataProcessor(float[] targetValues) {
        this.targetValues = targetValues;
    }

    public void processSerialData(String data) {
        try {
            System.out.println("Received data: " + data);
            if (!data.matches("Phase\\s*Current\\s*=\\s*[0-9.]+,Pump\\s*Voltage\\s*=\\s*[0-9.]+,Body\\s*Temperature\\s*=\\s*[0-9.]+,Pump\\s*Vibration\\s*=\\s*[0-9.]+,Delivery\\s*Pressure\\s*=\\s*[0-9.]+,Failure\\s*Mode\\s*=\\s*[0-9.]+")) {
                System.out.println("Invalid data format. Skipping: " + data);
                return;
            }

            String[] parts = data.split(",");

            synchronized (targetValues) {
                targetValues[0] = Float.parseFloat(parts[0].split("=")[1].trim());
                targetValues[1] = Float.parseFloat(parts[1].split("=")[1].trim());
                targetValues[2] = Float.parseFloat(parts[2].split("=")[1].trim());
                targetValues[3] = Float.parseFloat(parts[3].split("=")[1].trim());
                targetValues[4] = Float.parseFloat(parts[4].split("=")[1].trim());
                targetValues[5] = Float.parseFloat(parts[5].split("=")[1].trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
