package inlandshipping;

/**
 * Class contains some configuration parameters for the simulation.
 */
public class Configuration {
	// The total timesteps in the simulation
	public static int simulationTime = 10000;
	
	// The time in milliseconds between timesteps
	public static int sleepTime = 100;
	
	// The probability that a new vessel will be large
	public static double fractionLargeVessels = 0.3;
	
	// The probability that a new vessel will be fast
	public static double fractionFastVessels = 0.3;
	
	// The number of vessels in the initial environment
	public static int nbStartVessels = 0;
	
	// The number of segments a slow vessel may advance in every timestep
	public static int nbSegmentsPerStepSlow = 1;
	
	// The number of segments a fast vessel may advance in every timestep
	public static int nbSegmentsPerStepFast = 2;
	
	// The probability that a new vessel appears in a given timestep
	public static double probNewVessel = 0.00;

	// The number of timesteps that a reservation in a lock stays active before it "evaporates"
    public static int reservationTTL = 3;
    
    // The time needed for locks to transfer the water level of their chambers
    public static int lockTimeNeeded = 5;
}
