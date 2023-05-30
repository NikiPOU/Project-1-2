package twentyone.Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import twentyone.App;
import twentyone.Classes.Vector3d;

public class numberChooserScreenController {

    private double[] positions = {-148186906.893642, -27823158.5715694, 33746.8987977114};

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

    /**
     * When the {@code Next Button} gets pressed in the GUI, this method will be called. The following will be done:
     * <ul>
     * <li>The initial probe position will be set
     * <li>The initial probe velocity will be set
     * <li>The initial stepsize will be set
     * <li>The chosen time stamp will be set
     * </ul>
     * After this, the music will fade out and the screen will be changed.
     * 
     * @throws IOException
     * @see twentyone.Classes.musicPlayer
     * @see {@link javafx.scene.control.Button Button}
     */
    @FXML
    public void onNextButton() throws IOException {
        App.initialPosProbe = setPosition();
        App.chosenStepsize = setStepsize();
        App.stepSize = App.chosenStepsize;
        App.timeStamp = setTimestamp();
        App.MP.fadeOut();
        App.setRoot("fxml/SolarScene3D");
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
        if(positionZ.getText() == null || positionZ.getText().trim().isEmpty()){
            z = 0;
        } else {
            z = Double.parseDouble(positionZ.getText());
        }
        return setVector(positions[0] + x, positions[1] + y, positions[2] + z);
    }

    /**
     * This method will get the chosen {@code stepsize} from the {@code stepsizeTextfield} and then set the {@code stepsize} accordingly.<p>
     * If no {@code stepsize} was chosen, it will be set to {@code 10}
     * @return the chosen {@code stepsize} as an {@code Integer}
     * @see TextField
     */
    private int setStepsize(){
        int stepSize;
        if(stepsizeTextfield.getText() == null || stepsizeTextfield.getText().trim().isEmpty()){
            stepSize = 10;
        } else {
            stepSize = Integer.parseInt(stepsizeTextfield.getText());
        }
        return stepSize;
    }

    /**
     * This method will get the chosen {@code time stamp} from the {@code timestampTextfield} and then set the {@code time stamp} accordingly.<p>
     * If no {@code time stamp} was chosen, it will be set to {@code -1} and thus not be used.
     * @return the chosen {@code time stamp} as an {@code Integer}
     * @see TextField
     */
    private int setTimestamp(){
        int timestamp;
        if(timestampTextfield.getText() == null || timestampTextfield.getText().trim().isEmpty()){
            timestamp = -1;
        } else {
            timestamp = Integer.parseInt(timestampTextfield.getText());
        }
        return timestamp;
    }

    /**
     * When the {@code MenuItem} "North Pole" gets clicked, this method will run. This will set the initial position of the {@code Rocket} to the North Pole.
     * @see {@link javafx.scene.control.MenuItem MenuItem}
     */
    @FXML
    public void onNorthPoleButton(){
        positionZ.setText("6370");
        positionX.setText("");
        positionY.setText("");
    }

    /**
     * When the {@code MenuItem} "South Pole" gets clicked, this method will run. This will set the initial position of the {@code Rocket} to the South Pole.
     * @see {@link javafx.scene.control.MenuItem MenuItem}
     */
    @FXML
    public void onSouthPoleButton(){
        positionZ.setText("-6370");
        positionX.setText("");
        positionY.setText("");
    }

    /**
     * When the {@code MenuItem} "Pacific Ocean" gets clicked, this method will run. This will set the initial position of the {@code Rocket} to the Pacific Ocean.
     * @see {@link javafx.scene.control.MenuItem MenuItem}
     */
    @FXML
    public void onPacificOceanButton(){
        positionX.setText("6370");
        positionZ.setText("");
        positionY.setText("");
    }

    /**
     * When the {@code MenuItem} "Africa" gets clicked, this method will run. This will set the initial position of the {@code Rocket} to the Africa.
     * @see {@link javafx.scene.control.MenuItem MenuItem}
     */
    @FXML
    public void onAfricaButton(){
        positionX.setText("-6370");
        positionZ.setText("");
        positionY.setText("");
    }

    /**
     * When the {@code MenuItem} "South America" gets clicked, this method will run. This will set the initial position of the {@code Rocket} to the South America.
     * @see {@link javafx.scene.control.MenuItem MenuItem}
     */
    @FXML
    public void onSouthAmericaButton(){
        positionY.setText("6370");
        positionZ.setText("");
        positionX.setText("");
    }

    /**
     * When the {@code MenuItem} "Asia" gets clicked, this method will run. This will set the initial position of the {@code Rocket} to the Asia.
     * @see {@link javafx.scene.control.MenuItem MenuItem}
     */
    @FXML
    public void onAsiaButton(){
        positionY.setText("-6370");
        positionZ.setText("");
        positionX.setText("");
    }
    
}
