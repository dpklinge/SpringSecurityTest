package com.fdmgroup.dataaccess;

import java.util.List;

/**
 * Interface dictating basic data access functionality. 
 * @author FDM Group
 *
 * @param <T>
 * Object type to be stored.
 * @param <ID>
 * Type of object's primary key/unique identifier.
 */
public interface Dao<T, ID> {
	public boolean write(T target);
	public T read(ID id);
	public boolean delete(ID id);
	public List<T> readAll();
	T update(T target, ID password);
}
