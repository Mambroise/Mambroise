package controller;

import java.util.ArrayList;

public interface IDao<T> {
	public Boolean create(T object);

	public ArrayList<T> read(String txt);

	// public ArrayList<T> readPagination();
	public Object findById(int id);

	public Boolean update(T object);

	public Boolean Delete(int id);

	public Boolean activer(T object);

	public Boolean desactiver(T object);

	public Boolean isExist(String txt);

	public int total();
}
