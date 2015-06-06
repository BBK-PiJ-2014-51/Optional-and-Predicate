package predicate;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
/**
 * Demonstrates some usage of Predicate and Optional
 *
 */
public class PredicateOptionalRunner {

	/**
	 * Uses predicate's test method to search a list using a lambda which is passed
	 * in as the 2nd paramter
	 * 
	 * @param list to search
	 * @param func - the lambda to use with predicate
	 * @return Optional - a return object that encapsulates the data or returns empty
	 */
	public static <T> Optional<T> findOne(List<T> list, Predicate<? super T> func){
		
		//each predicate uses test() from functional interface to return a boolean
		for (T item : list)
			if(func.test(item)) 
				return Optional.of(item);
		
		return Optional.empty();
	}
	
	public static void main(String[] args) {
	
		List<Person> people = Person.getSomePeople(); //list of people with some attributes to test with
		Person foundPerson = null; //instance variable for search results
		
		//simple search with a predicate being used as 2nd parameter in find
		Optional<Person> nameMatch = findOne(people, x -> x.name.equals("Jill"));
		
		if (nameMatch.isPresent()) //use isPresent() to check result of search
			foundPerson = nameMatch.get(); //use get() to retrieve data 
	
		System.out.println("We found " + foundPerson.name + " who is " 
			+ foundPerson.age + " years old.");
		
		//if no match is found, isPresent() is false
		Optional<Person> ageMatch = findOne(people, x -> x.age == 300);
		if(!ageMatch.isPresent());
			System.out.println("No Match Found");
		
		//also, store lambda as predicate in variable
		Predicate<Person> hasAge = x -> x.age == 36;
		Predicate<Person> hasName = x -> x.name.equals("Jill");
		
		//use and() to match multiple predicates
		Optional<Person> result = findOne(people, hasAge.and(hasName));
		if (result.isPresent());
			System.out.println("We found " + result.get().name + " again.");
		
		// or()
		Predicate<Person> hasWage = x -> x.wage > 60000;
		result = findOne(people, hasAge.or(hasWage));
		if (result.isPresent());
			System.out.println("We found " + result.get().name + " this time.");
			
		//negate
		result = findOne(people, hasName.negate());
		if (result.isPresent());
			System.out.println("We found " + result.get().name);
	}
	
}
