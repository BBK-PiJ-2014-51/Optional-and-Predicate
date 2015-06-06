package predicate;

import java.util.LinkedList;
import java.util.List;

public class Person {
	
	protected int id;
	protected String name;
	protected int wage;
	protected int age;
	
	public Person(int id, String name, int wage, int age){
		this.id = id;
		this.name = name;
		this.wage = wage;
		this.age = age;
	}
	
	/**
	 * Just loads some person objects into a new list and returns it
	 * 
	 * @return a list of people
	 */
	protected static List<Person> getSomePeople(){
		int wage = 20000;
		String[] names = new String[]{"Jack","Jeff","Tom", 
				"Bill","Diane","Alice","Suzy","Paul","Jill"};		
		
		List<Person> people = new LinkedList<Person>();
		for (int i = 0; i < names.length; i++)
			people.add(new Person(i,names[i], wage + i * 7000, i + 28));
		
		return people;
	}
}
