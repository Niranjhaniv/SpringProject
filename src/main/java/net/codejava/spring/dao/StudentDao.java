package net.codejava.spring.dao;

import java.util.List;

import net.codejava.spring.model.Student;

/**
 * Defines DAO operations for the contact model.
 * @author www.codejava.net
 *
 */
public interface StudentDao {
	
	public void saveOrUpdate(Student contact);
	
	public void delete(int contactId);
	
	public Student get(int contactId);
	
	public List<Student> list();
	
}
