package twentyone.Classes;

import twentyone.App;

public class PositionCalculationThread implements Runnable{

    private boolean isRunning;

    /**
     * The {@code Adams-Bashforth Solver}
     * @see twentyone.Classes.AdamsBashforth
     */
    private AdamsBashforth a = new AdamsBashforth();
    /**
     * The {@code Euler Solver}
     * @see twentyone.Classes.Euler
     */
    private Euler unreal = new Euler();
    /**
     * The {@code Verlet Solver}
     * @see twentyone.Classes.Verlet
     */
    private Verlet verlet = new Verlet();
    /**
     * The {@code Runge-Kutta Solver}
     * @see twentyone.Classes.RungeKutta
     */
    private RungeKutta rungeKutta = new RungeKutta();
    /**
     * The {@code Adams-Moulton Solver}
     * @see twentyone.Classes.AdamsMoulton
     */
    private AdamsMoulton aMoulton = new AdamsMoulton();

    /**
     * The chosen amount of {@code loops}. This is the amount of times the next {@code position} gets calculated before updating the GUI.
     * @see {@link twentyone.Controllers.SolarScene3DController.Movement#handle(ActionEvent) handle}
     */
    int eulerLoops = App.eulerLoops;
    /**
     * The array of {@code Celestial Bodies} which stores all the used {@code Celestial Bodies}. They get used for the calculations.
     * @see CelestialBody
     */
    CelestialBody[] bodies = new CelestialBody[12];
    /**
     * The current time in seconds. This one won't be reset to {@code 0} in contrary to {@link twentyone.Controllers.SolarScene3DController#seconds seconds}.
     */
    int k=App.totalSeconds;


    public PositionCalculationThread(){
        isRunning = true;
    }

    private void positionCalculation(){
        for (int i = 0; i < App.eulerLoops; i++) {
            for (int j = 0; j < App.bodies.length; j++) {
                App.bodies = solvers(App.bodies, j, App.stepSize);
            }
            //Keep track of the time
            App.seconds += App.stepSize;
            k += App.stepSize;
            App.totalSeconds = k;
            if(App.seconds >= 60){
                App.minutes++;
                App.seconds = 0;
                if(App.minutes % 60 == 0){
                    App.hours++;
                    App.minutes = 0;
                    if(App.hours % 24 == 0){
                        App.days++;
                        App.hours = 0;
                        if(App.days % 30 == 0){
                            App.months++;
                            App.days = 0;
                            if(App.months/12 == 1){
                                App.months = 0;
                                App.years++;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method checks which Solver method is chosen. It then calculates the next position using that chosen Solver method.
     * @param celestialBodies
     * @param chosenBody
     * @param chosenStepsize
     * @return the next position.
     */
    public CelestialBody[] solvers(CelestialBody[] celestialBodies, int chosenBody, double chosenStepsize){
        if(App.chosenSolver == 0){
            return a.adams(celestialBodies, chosenBody, chosenStepsize);
        } else if(App.chosenSolver == 1){
            return unreal.eulers(celestialBodies, chosenBody, chosenStepsize);
        } else if(App.chosenSolver == 2){
            return verlet.verlet(celestialBodies, chosenBody, chosenStepsize);
        } else if(App.chosenSolver == 3){
            return rungeKutta.rungKutta(celestialBodies, chosenBody, chosenStepsize);
        } else if(App.chosenSolver == 4){
            return aMoulton.adams(celestialBodies, chosenBody, chosenStepsize);
        } else {
            return celestialBodies;
        }
    }

    @Override
    public void run() {
        while(isRunning){
            positionCalculation();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        isRunning = false;
    }
}
