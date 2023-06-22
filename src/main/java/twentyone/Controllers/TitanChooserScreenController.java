package twentyone.Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import twentyone.App;
import twentyone.Classes.PositionCalculationThread;
import twentyone.Classes.Vector3d;

public class TitanChooserScreenController {

    @FXML
    private TextField positionX;
    @FXML
    private TextField positionY;
    @FXML
    private TextField rotation;
    @FXML
    private TextField velocityX;
    @FXML
    private TextField velocityY;
    @FXML
    private TextField velocityZ;
    
    @FXML
    public void onNextButton() throws IOException{
        App.titanPos = setPosition();
        App.titanVel = setVelocity();
        if(App.titanChosen){
            App.setRoot("fxml/SolarScene3D");
        } else {
            App.PCT = new PositionCalculationThread(App.bodies, App.stepSize);
            Thread thread = new Thread(App.PCT);
            thread.start();
            App.setRoot("fxml/LandingScreen");
        }
    }

    /**
     * Creates the Vector3d class.
     * @param x as a {@code double}
     * @param y as a {@code double}
     * @param z as a {@code double}
     * @return A {@link Vector3d} class
     */
    private Vector3d setVector(double x, double y, double z){
        return new Vector3d(x, y, z);
    }

    /**
     * Sets the {@code position} of the {@code Probe} by checking which values were entered. If no values were entered or some were skipped, the value will be set to {@code 0}.
     * @return A {@code Vector3d Class} of the {@code position} of the {@code Probe}
     * @see TextField
     * @see Vector3d
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
        if(rotation.getText() == null || rotation.getText().trim().isEmpty()){
            z = 0;
        } else {
            z = Double.parseDouble(rotation.getText());
        }
        return setVector(x, y, z);
    }

    /**
     * Sets the {@code velocity} of the {@code Probe} by checking which values were entered. If no values were entered or some were skipped, the value will be set to {@code 0}.
     * @return A {@code Vector3d Class} of the {@code velocity} of the {@code Probe}
     * @see TextField
     * @see Vector3d
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

}
