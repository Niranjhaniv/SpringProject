package net.codejava.spring.model;

public class Student {
	private long id;
	private String name;
	private long age ;
	private long mark;

	public Student() {
	}

	public Student(long id, String name, long age, long mark) {
		this.id=id;
		this.name = name;
		this.age = age;
		this.mark = mark;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public long getMark() {
		return mark;
	}

	public void setMark(long mark) {
		this.mark = mark;
	}


	
}
