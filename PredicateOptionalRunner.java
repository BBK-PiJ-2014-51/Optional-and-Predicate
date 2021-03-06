package predicate;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
	
	/* list without stream */
	public static <T> Optional<List<T> > findAll(List<T> list, Predicate<? super T> func){
		List<T> newList = new LinkedList<T>();
		//each predicate uses test() from functional interface to return a boolean
		for (T item : list)
			if(func.test(item)) 
				newList.add(item);

		return Optional.ofNullable(newList);

	}
	
	/* find max */
	public static <T extends Comparable> T findMax(List<T> stuff, int begin){
		
		T max = null;
		for (int i = begin; i < stuff.size(); i++){
			if (i == begin) max = stuff.get(i);
			
			if (stuff.get(i).compareTo(max) > 0){
				max = stuff.get(i);
			}	
		}
		
		return max;
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
			
		Optional<List <Person>>resultList = findAll(people,hasWage);
		List<Person> searchResults = resultList.get();
		System.out.println("multiple results...");
		for (Person person : searchResults)
			System.out.println(person.name);
		
		//negate
		result = findOne(people, hasName.negate());
		if (result.isPresent());
			System.out.println("We found " + result.get().name);

		//filter list
		List<Person> newList = people.stream().filter(getLessThanWagePredicate(40000)
				.and(getNameBeginsPredicate('J')))
				.collect(Collectors.<Person>toList());
		
		for (Person p : newList)
			System.out.println(p.name);
		
		
	}
	
	/**
	 * encapsulate in method to use variable
	 * @param wage
	 * @return
	 */
	private static Predicate<Person> getLessThanWagePredicate(int wage){
		return x -> x.wage < wage;
	}
	
	private static Predicate<Person> getNameBeginsPredicate(char firstLetter){
		return x -> x.name.charAt(0) == firstLetter;
	}
	
}
