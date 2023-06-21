package twentyone.Classes;

import twentyone.App;

public class PositionCalculationThread implements Runnable{

    /**
     * Amount of {@code seconds} in the current {@code minute}.
     * @see {@link twentyone.Controllers.SolarScene3DController#minutes minutes}
     */
    int seconds = 0;
    /**
     * Amount of {@code minutes} in the current {@code hour}.
     * @see {@link twentyone.Controllers.SolarScene3DController#hours hours}
     */
    int minutes = 0;
    /**
     * Amount of {@code hours} in the current {@code day}.
     * @see {@link twentyone.Controllers.SolarScene3DController#days days}
     */
    int hours = 0;
    /**
     * Amount of {@code days} in the current {@code month}.
     * @see {@link twentyone.Controllers.SolarScene3DController#months months}
     */
    int days = 0;
    /**
     * Amount of {@code months} in the current {@code year}.
     * @see {@link twentyone.Controllers.SolarScene3DController#years years}
     */
    int months = 0;
    /**
     * Amount of {@code years}
     */
    int years = 0;

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
     * @see twentyone.Classes.VerletSolver
     */
    private VerletSolver verlet = new VerletSolver();
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
     * The chosen {@code Solver}. This could be one of the following:
     * <ul>
     * <li> {@code Adams-Bashforth Solver}
     * <li> {@code Adams-Moulton Solver}
     * <li> {@code Euler Solver}
     * <li> {@code Verlet Solver}
     * <li> {@code Runge-Kutta Solver}
     * </ul>
     * @see twentyone.Classes.AdamsBashforth
     * @see twentyone.Classes.AdamsMoulton
     * @see twentyone.Classes.Euler
     * @see twentyone.Classes.VerletSolver
     * @see twentyone.Classes.RungeKutta
     */
    private int chosenSolver = App.chosenSolver;

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
     * The current {@code stepsize}. It can be changed using {@code MenuItems}.
     * @see MenuItem
     */
    private double stepsize;
    /**
     * The current time in seconds. This one won't be reset to {@code 0} in contrary to {@link twentyone.Controllers.SolarScene3DController#seconds seconds}.
     */
    int k=App.totalSeconds;


    public PositionCalculationThread(CelestialBody[] bodies, double stepsize2){
        this.bodies = bodies;
        this.stepsize = stepsize2;
        isRunning = true;
        convertTime(App.totalSeconds);
    }

    /**
     * Transfers the time from seconds to the following format:<p>
     * years, months, days, hours, minutes, seconds
     * @param secs
     */
    private void convertTime(int secs){
        int yea = 0;
        int mont = 0;
        int das = 0;
        int hour = 0;
        int minute = 0;
        int sec = 0;
        int temporary;
        yea = secs / 31104000;
        if(yea != 0){
            years = yea;
        }
        temporary = (secs - (yea * 31104000));
        mont = temporary / 2592000;
        if(mont != 0){
            months = mont;
        }
        temporary -= mont * 2592000;
        das = temporary  / 86400;
        if(das != 0){
            days = das;
        }
        temporary -= das * 86400;
        hour = temporary / 3600;
        if(hour != 0){
            hours = hour;
        }
        temporary -= hour * 3600;
        minute = temporary / 60;
        if(minute != 0){
            minutes = minute;
        }
        temporary -= minute * 60;
        sec = temporary;
        if(sec != 0){
            seconds = sec;
        }
        }

    private void positionCalculation(){
        for (int i = 0; i < eulerLoops; i++) {
            for (int j = 0; j < bodies.length; j++) {
                bodies = solvers(bodies, j, stepsize);
            }

            //Keep track of the time
            App.seconds += stepsize;
            k += stepsize;
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
        if(chosenSolver == 0){
            return a.adams(celestialBodies, chosenBody, chosenStepsize);
        } else if(chosenSolver == 1){
            return unreal.Eulers(celestialBodies, chosenBody, chosenStepsize);
        } else if(chosenSolver == 2){
            return verlet.verlet(celestialBodies, chosenBody, chosenStepsize);
        } else if(chosenSolver == 3){
            return rungeKutta.rungKutta(celestialBodies, chosenBody, chosenStepsize);
        } else if(chosenSolver == 4){
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
        App.bodies = this.bodies;
        isRunning = false;
    }
}
