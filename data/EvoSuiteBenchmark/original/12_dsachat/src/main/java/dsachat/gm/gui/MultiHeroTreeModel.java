package dsachat.gm.gui;

import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import dsachat.share.hero.FightValue;
import dsachat.share.hero.Hero;

/**
 * This is a model for the game master tree.
 * It can display multiple heros at once
 * @author bernshausen
 *
 */
public class MultiHeroTreeModel implements TreeModel{
	
	Vector<TreeModelListener> list = new Vector<TreeModelListener>();
	Vector<Hero> heros = new Vector<Hero>();
	String root;
	
	/**
	 * create new model with multiple heros as data
	 * @param name the name of the root element 
	 */
	public MultiHeroTreeModel(String name){
		root = name;
	}
	
	/**
	 * create new model with multiple heros as data
	 * @param name the name of the root element
	 * @param heros the heros to display
	 */
	public MultiHeroTreeModel(String name,Vector<Hero> heros){
		root = name;
		this.heros = heros;
	}

	public void addTreeModelListener(TreeModelListener l) {
		list.addElement(l);
	}

	public Object getChild(Object parent, int index) {
		//differ the parent
		//only the root element is of type string
		if(parent.getClass().equals(String.class)){
			String p = parent.toString();
			if(p.equals(root)){
				return heros.elementAt(index);
			}
		}
		//heros have 3 childs attributes, talents and weapons
		if(parent.getClass().equals(Hero.class)){
			Hero h = (Hero)parent;
			switch(index){
			case 0:return new ValueHolder("Attributes", h);
			case 1:return new ValueHolder("Talents", h);
			case 2:return new ValueHolder("Weapons",h);
			default: return null;
			}
		}
		//value holder holds the value for attributes, talents and weapons
		if(parent.getClass().equals(ValueHolder.class)){
			ValueHolder vh = (ValueHolder)parent;
			if(vh.name.equals("Attributes")){
				return vh.parent.getAttributes().elementAt(index);
			}
			if(vh.name.equals("Talents")){
				return vh.parent.getTalents().elementAt(index);
			}
			if(vh.name.equals("Weapons")){
				return vh.parent.getFightvalues().elementAt(index);
			}
		}
		//fight values have a set of weapons
		if(parent.getClass().equals(FightValue.class)){
			return ((FightValue)parent).getWeapons().elementAt(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if(parent.getClass().equals(String.class)){
			String p = parent.toString();
			if(p.equals(root)){
				return heros.size();
			}
		}
		if(parent.getClass().equals(Hero.class)){
			return 3;
		}
		if(parent.getClass().equals(ValueHolder.class)){
			ValueHolder vh = (ValueHolder)parent;
			String p = parent.toString();
			if(p.equals("Attributes")){
				return vh.parent.getAttributes().size();
			}
			if(p.equals("Talents")){
				return vh.parent.getTalents().size();
			}
			if(p.equals("Weapons")){
				return vh.parent.getFightvalues().size();
			}
		}
		if(parent.getClass().equals(FightValue.class)){
			return ((FightValue)parent).getWeapons().size();
		}
		return 0;
	}

	public int getIndexOfChild(Object parent, Object child) {
		if(parent.toString().equals(root)){
			return heros.indexOf(child);
		}
		if(parent.getClass().equals(Hero.class)){
			String s = child.toString();
			if(s.equals("Attributes")) return 0;
			if(s.equals("Talents")) return 1;
			if(s.equals("Weapons")) return 2;
		}
		if(parent.getClass().equals(ValueHolder.class)){
			ValueHolder vh =(ValueHolder)parent;
			String p = vh.toString();
			if(p.equals("Talents")){
				return vh.parent.getTalents().indexOf(child);
			}
			if(p.equals("Attributes")){
				return vh.parent.getAttributes().indexOf(child);
			}
			if(p.equals("Weapons")){
				return vh.parent.getFightvalues().indexOf(child);
			}
		}
		if(parent.getClass().equals(FightValue.class)){
			return ((FightValue)parent).getWeapons().indexOf(child);
		}
		return -1;
	}

	public Object getRoot() {
		return root;
	}

	public boolean isLeaf(Object node) {
		String s = node.toString();
		if(s.equals(root))
			return false;
		if(node.getClass().equals(Hero.class))
			return false;
		if(s.equals("Attributes"))
			return false;
		if(s.equals("Talents"))
			return false;
		if(s.equals("Weapons"))
			return false;
		if(node.getClass().equals(FightValue.class))
			return false;
		return true;
	}

	public void removeTreeModelListener(TreeModelListener l) {
		list.removeElement(l);
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * display new data
	 * @param h2 the new data
	 */
	protected void fireTreeStructureChanged(Vector<Hero> h2) {
		heros=h2;
		TreeModelEvent e = new TreeModelEvent(this, 
				heros.toArray());
		for (TreeModelListener tml : list) {
			tml.treeStructureChanged(e);
		}
	}
}
