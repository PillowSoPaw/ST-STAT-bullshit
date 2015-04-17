package model;

import java.util.ArrayList;

public class ArrayListUtils {

	static public <T> T getTail(ArrayList<T> list) {
		return list.get(list.size()-1);
	}
	
	static public <T> T removeTail(ArrayList<T> list) {
		return list.remove(list.size()-1);
	}	
}
