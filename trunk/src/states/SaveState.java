package states;

import java.util.ArrayList;

import engine.World;
import entities.Entities;

public class SaveState extends AbstractState{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3694267945091870213L;
	private ArrayList<Entities> objList;
	public SaveState(){
		super();
		objList= new ArrayList<Entities>();
	}

	@Override
	public ArrayList<Entities> getList() {

		return objList;
	}

	@Override
	public void set(AbstractState list) {

		objList.clear();
		for(int i = 0 ; i < list.size() ; i++){
			objList.add(list.get(i));
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset(World world) {
		for(int i = 0 ; i < objList.size() ; i++){
			x.add(objList.get(i).getX());
			y.add(objList.get(i).getY());
		
	}

	
	}



}
