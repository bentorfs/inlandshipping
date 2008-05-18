package inlandshipping;

/**
 * A type of lock consisting of two adjacent chambers
 */
public class MultiChamberLock extends Lock {
	/*
	 * The current water levels of the chambers.
	 * chamberPrevious is the chamber next to the previous segment,
	 * chamberNext is the segment next to the next segment.
	 * 
	 * @invariant 	chamberPrevious can never be at NEXTLEVEL, while chamberNext is at PREVIOUSLEVEL
	 * 				
	 */
	LockPosition chamberPrevious = LockPosition.PREVIOUSLEVEL;
	LockPosition chamberNext = LockPosition.PREVIOUSLEVEL;
	
}
