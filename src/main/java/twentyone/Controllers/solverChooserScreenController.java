package twentyone.Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import twentyone.App;

public class solverChooserScreenController {
    
    /**
     * When the {@code Adams-Bashforth Button} gets pressed, the chosen {@code Solver} will be set to the {@code Adams-Bashforth solver}. It will then change to the next screen.
     * @throws IOException
     * @see twentyone.Classes.Solver
     * @see twentyone.Classes.AdamsBashforth
     * @see javafx.scene.control.Button
     */
    @FXML
    public void onAdamsButton() throws IOException{
        App.chosenSolver = 0;
        App.setRoot("fxml/numberChooserScreen");
    }

    /**
     * When the {@code Euler Button} gets pressed, the chosen {@code Solver} will be set to the {@code Euler solver}. It will then change to the next screen.
     * @throws IOException
     * @see twentyone.Classes.Solver
     * @see twentyone.Classes.Euler
     * @see javafx.scene.control.Button
     */
    @FXML
    public void onEulerButton() throws IOException{
        App.chosenSolver = 1;
        App.setRoot("fxml/numberChooserScreen");
    }

    /**
     * When the {@code Verlet Button} gets pressed, the chosen {@code Solver} will be set to the {@code Verlet solver}. It will then change to the next screen.
     * @throws IOException
     * @see twentyone.Classes.Solver
     * @see twentyone.Classes.VerletSolver
     * @see javafx.scene.control.Button
     */
    @FXML
    public void onVerletButton() throws IOException{
        App.chosenSolver = 2;
        App.setRoot("fxml/numberChooserScreen");
    }

    /**
     * When the {@code Runge-Kutta Button} gets pressed, the chosen {@code Solver} will be set to the {@code Runge-Kutta solver}. It will then change to the next screen.
     * @throws IOException
     * @see twentyone.Classes.Solver
     * @see twentyone.Classes.RungeKutta
     * @see javafx.scene.control.Button
     */
    @FXML
    public void onRungeButton() throws IOException{
        App.chosenSolver = 3;
        App.setRoot("fxml/numberChooserScreen");
    }
}
