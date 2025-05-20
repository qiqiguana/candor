/* $Id: ActionFactory.java,v 1.2 2004/04/27 19:26:22 bja Exp $ */

package state;

import util.AbstractFactory;
import util.ObjectManager;
import util.ManagedObject;

public class ActionFactory extends AbstractFactory<Action> {

    /** The reference to the MessageFactory instance. */
    protected static ActionFactory instance = null;

    /**
     * Creates a new instance of the ActionFactory class.
     */
    private ActionFactory() {
	super(new Action());
    }

    /**
     * Creates a new default Action with the specified arguments.
     *
     * @param type The type of Action to create.
     * @param actor The actorID associated with this Action.
     * @param target The targetID associated with this Action.
     * @return a new Action instance with the specified arguments.
     */
    public Action createAction(int type, int actor, int target) {
	Action action = createObject();
	action.set(type, actor, target, (byte)0, (byte)0, (byte)0);
	System.err.println("ActionFactory.createAction(): size = "
			   + getStockCount());
	return action;
    }

    /**
     * Creates a new move Action with the specified arguments.
     *
     * @param actor The actorID associated with this Action.
     * @param x the x coordinate of the destination point.
     * @param y the y coordinate of the destination point.
     * @param z the z coordinate of the destination point.
     * @return a new move Action with the specified arguments.
     */
    public Action createMoveAction(int actor, byte x, byte y, byte z) {
	Action action = createObject();
	action.set(Action.ACTION_MOVE, actor, -1, x, y, z);
	System.err.println("ActionFactory.createAction(): size = "
			   + getStockCount());
	return action;
    }

    /**
     * Returns the ActionFactory instance, currently the instance
     * returned is that of the last created MessageFactory.
     */
    public static ActionFactory getInstance() {
	return instance != null ? instance : new ActionFactory();
    }

}
