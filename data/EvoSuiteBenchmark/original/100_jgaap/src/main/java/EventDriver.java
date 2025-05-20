interface Eventifiable {
    
    public EventSet createEventSet(DocumentSet ds);

}

abstract class EventDriver implements Eventifiable{

    abstract public EventSet createEventSet(DocumentSet ds);

}
