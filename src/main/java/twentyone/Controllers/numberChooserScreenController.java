package twentyone.Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import twentyone.App;
import twentyone.Classes.Vector3d;

public class numberChooserScreenController {

    private double[] positions = {-148186906.893642, -27823158.5715694, 33746.8987977113};

    @FXML
    private TextField stepsizeTextfield;
    @FXML
    private TextField timestampTextfield;
    @FXML
    private TextField positionX;
    @FXML
    private TextField positionY;
    @FXML
    private TextField positionZ;
    @FXML
    private TextField velocityX;
    @FXML
    private TextField velocityY;
    @FXML
    private TextField velocityZ;

    @FXML
    public void onNextButton() throws IOException {
        App.initialPosProbe = setPosition();
        App.initialVelProbe = setVelocity();
        App.chosenStepsize = setStepsize();
        App.stepSize = App.chosenStepsize;
        App.timeStamp = setTimestamp();
        App.MP.fadeOut();
        App.setRoot("fxml/SolarScene3D");
    }

    /**
     * Creates the Vector3d class.
     * @param x
     * @param y
     * @param z
     * @return A Vector3d class
     */
    private Vector3d setVector(double x, double y, double z){
        return new Vector3d(x, y, z);
    }

    /**
     * Sets the position of the Probe by checking which values were entered. If no values were entered or some were skipped, the value will be set to 0.
     * @return A Vector3d of the position of the Probe
     */
    private Vector3d setPosition(){
        double x;
        double y;
        double z;
        if(positionX.getText() == null || positionX.getText().trim().isEmpty()){
            x = 0;
        } else {
            x = Double.parseDouble(positionX.getText());
        }
        if(positionY.getText() == null || positionY.getText().trim().isEmpty()){
            y = 0;
        } else {
            y = Double.parseDouble(positionY.getText());
        }
        if(positionZ.getText() == null || positionZ.getText().trim().isEmpty()){
            z = 0;
        } else {
            z = Double.parseDouble(positionZ.getText());
        }
        return setVector(positions[0] + x, positions[1] + y, positions[2] + z);
    }

    /**
     * Sets the velocity of the Probe by checking which values were entered. If no values were entered or some were skipped, the value will be set to 0.
     * @return A Vector3d of the velocity of the Probe
     */
    private Vector3d setVelocity(){
        double x;
        double y;
        double z;
        if(velocityX.getText() == null || velocityX.getText().trim().isEmpty()){
            x = 0;
        } else {
            x = Double.parseDouble(velocityX.getText());
        }
        if(velocityY.getText() == null || velocityY.getText().trim().isEmpty()){
            y = 0;
        } else {
            y = Double.parseDouble(velocityY.getText());
        }
        if(velocityZ.getText() == null || velocityZ.getText().trim().isEmpty()){
            z = 0;
        } else {
            z = Double.parseDouble(velocityZ.getText());
        }
        return setVector(x, y, z);
    }

    private int setStepsize(){
        int stepSize;
        if(stepsizeTextfield.getText() == null || stepsizeTextfield.getText().trim().isEmpty()){
            stepSize = 10;
        } else {
            stepSize = Integer.parseInt(stepsizeTextfield.getText());
        }
        return stepSize;
    }

    private int setTimestamp(){
        int timestamp;
        if(timestampTextfield.getText() == null || timestampTextfield.getText().trim().isEmpty()){
            timestamp = -1;
        } else {
            timestamp = Integer.parseInt(timestampTextfield.getText());
        }
        return timestamp;
    }
    
}
