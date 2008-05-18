package inlandshipping;

/**
 * The position a lock is in. 
 * NEXTLEVEL for the water level of the 'next' segment,
 * PREVLEVEL for the water level of the 'previous' segment,
 * TONEXT for water level inbetween, underway to the level of the 'next' segment,
 * TOPREV for water level inbetween, underway to the level of the 'previous' segment.
 */
public enum LockPosition {
	NEXTLEVEL, PREVLEVEL, TONEXT, TOPREV
}
