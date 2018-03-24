package net.codejava.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import net.codejava.spring.model.Student;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/**
 * An implementation of the ContactDAO interface.
 * @author www.codejava.net
 *
 */
public class StudentDAOImpl implements StudentDao {

	private JdbcTemplate jdbcTemplate;
	
	public StudentDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void saveOrUpdate(Student student) {
		
			// insert
			String sql = "INSERT INTO student (id, name, age, mark)"
						+ " VALUES (?, ?, ?, ?)";
			jdbcTemplate.update(sql, student.getId(), student.getName(),
					student.getAge(), student.getMark());
		
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM student WHERE id=?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Student> list() {
		String sql = "SELECT * FROM student";
		List<Student> listContact = jdbcTemplate.query(sql, new RowMapper<Student>() {

			@Override
			public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
				Student student = new Student();
	
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("name"));
				student.setAge(rs.getInt("age"));
				student.setMark(rs.getInt("mark"));
				
				
				return student;
			}
			
		});
		
		return listContact;
	}

	@Override
	public Student get(int contactId) {
		String sql = "SELECT * FROM student WHERE id=" + contactId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<Student>() {
			Student student = new Student();

			@Override
			public Student extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					student.setId(rs.getInt("id"));
					student.setName(rs.getString("name"));
					student.setAge(rs.getInt("age"));
					student.setMark(rs.getInt("mark"));
					return student;
				}
				
				return null;
			}
			
		});
	}
	
	

}
