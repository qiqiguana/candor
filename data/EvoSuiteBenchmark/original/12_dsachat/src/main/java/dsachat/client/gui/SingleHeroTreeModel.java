package dsachat.client.gui;

import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import dsachat.share.hero.FightValue;
import dsachat.share.hero.Hero;


public class SingleHeroTreeModel implements TreeModel{
	
	Vector<TreeModelListener> list = new Vector<TreeModelListener>();
	Hero h;
	
	public SingleHeroTreeModel(Hero hero){
		h=hero;
	}

	public void addTreeModelListener(TreeModelListener l) {
		list.addElement(l);
	}

	public Object getChild(Object parent, int index) {
		if(parent.getClass().equals(String.class)){
			String p = parent.toString();
			// TODO Auto-generated method stub
			if( ((String)parent).equals(h.getName())){
				switch(index){
				case 0:return new String("Attributes");
				case 1:return new String("Talents");
				case 2:return new String("Weapons");
				}
			}
			if(p.equals("Attributes")){
				String type = (String)parent;
				if(type.equals("Attributes")){
					return h.getAttributes().elementAt(index);
				}
			}
			if(p.equals("Talents")){
				return h.getTalents().elementAt(index);
			}
			if(p.equals("Weapons")){
				return h.getFightvalues().elementAt(index);
			}
		}
		if(parent.getClass().equals(FightValue.class)){
			return ((FightValue)parent).getWeapons().elementAt(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if(parent.getClass().equals(String.class)){
			String p = parent.toString();
			// TODO Auto-generated method stub
			if(p.equals(h.getName())){
				return 3;
			}
			if(p.equals("Attributes")){
				return h.getAttributes().size();
			}
			if(p.equals("Talents")){
				return h.getTalents().size();
			}
			if(p.equals("Weapons")){
				return h.getFightvalues().size();
			}
		}
		if(parent.getClass().equals(FightValue.class)){
			return ((FightValue)parent).getWeapons().size();
		}
		return 0;
	}

	public int getIndexOfChild(Object parent, Object child) {
		String p = parent.toString();
		String s = child.toString();		
		// TODO Auto-generated method stub
		if(p.equals(h.getName())){
			if(s.equals("Attributes")) return 0;
			if(s.equals("Talents")) return 1;
			if(s.equals("Weapons")) return 2;
		}
		if(p.equals("Talents")){
			return h.getTalents().indexOf(child);
		}
		if(p.equals("Attributes")){
			return h.getAttributes().indexOf(child);
		}
		if(p.equals("Weapons")){
			return h.getFightvalues().indexOf(child);
		}
		if(parent.getClass().equals(FightValue.class)){
			return ((FightValue)parent).getWeapons().indexOf(child);
		}
		return -1;
	}

	public Object getRoot() {
		return h.getName();
	}

	public boolean isLeaf(Object node) {
		String s = node.toString();
		// TODO Auto-generated method stub
		if(s.equals(h.getName()))
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

	protected void fireTreeStructureChanged(Hero h2) {
		h=h2;
		TreeModelEvent e = new TreeModelEvent(this, 
				new Object[] {h});
		for (TreeModelListener tml : list) {
			tml.treeStructureChanged(e);
		}
	}

}
