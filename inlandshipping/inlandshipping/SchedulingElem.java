package inlandshipping;

/**
 * Represents an action that is planned at a specific timepoint.
 */
public class SchedulingElem {

    /****************************************************************
     * Constructor
     ****************************************************************/
    public SchedulingElem(Vessel vessel, SchedulingEvent event) {
        this.vessel = vessel;
        this.event = event;
    }

    /****************************************************************
     * Other
     ****************************************************************/
    /*
     * The vessel that is scheduled in this element.
     */
    private Vessel vessel;
    
    public Vessel getVessel() {
        return vessel;
    }
    
    /*
     * The event that is taking place
     */
    private SchedulingEvent event;
    
    public SchedulingEvent getEvent() {
        return event;
    }
    
}
