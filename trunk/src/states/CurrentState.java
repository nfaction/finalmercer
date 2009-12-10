package states;

import java.util.ArrayList;

import engine.World;
import entities.Entities;

public class CurrentState extends AbstractState{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2022700114132027295L;
	private ArrayList<Entities> objList;
	public CurrentState(){
		super();
		objList= new ArrayList<Entities>();
	}
	@Override
	public ArrayList<Entities> getList() {
		return objList;
	}
	
	public void set(AbstractState list) {
		objList.clear();
		for(int i = 0 ; i < list.size() ; i++){
			objList.add(list.get(i));
		}
		
	}
	@Override
	public void reset(World world) {
		// TODO Auto-generated method stub
		
	}
	
	
}
