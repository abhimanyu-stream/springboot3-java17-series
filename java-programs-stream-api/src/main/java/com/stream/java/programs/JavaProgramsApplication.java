package com.stream.java.programs;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SpringBootApplication
public class JavaProgramsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaProgramsApplication.class, args);
		//Predicate has test method
		//public interface Predicate<T> {
			//boolean test(T t);
		//}
		Predicate<Integer> predicate = x-> x % 2 == 0;
		List<Integer> list = Arrays.asList(1,2,3,4,5,6);
		for(Integer i: list){
			System.out.println(i + "-" +predicate.test(i));
		}

		Predicate<String> startsWithA = x->x.toLowerCase().charAt(0) == 'a';
		System.out.println("startsWithA " + startsWithA.test("abhimanyu"));
		Studentz s = new Studentz("Animal");
		System.out.println("startsWithA " + startsWithA.test(s.getName()));
		System.out.println("startsWithA " + startsWithA.negate().test(s.getName()));



		//public interface Consumer<T> {
			//void accept(T t);
		//}
		Consumer<Integer> consumer = x->System.out.println(x);
		consumer.accept(1);




		//public interface Supplier<T> {
			//T get();
		//}
		Supplier<String> supplier = ()-> "calli";
		System.out.println("supplier "+supplier.get());


		//Function
		//public interface Function<T, R> {
			//R apply(T t);
		//}

		Function<String, Integer> function = x-> x.length();
		System.out.println("function "+function.apply("sun"));
		Function<String, String> function2 = x-> x.substring(0,3);// three letter String[including count)
		System.out.println("function2 " +function2.apply("suppliers"));



		//List<T> to DTO

		List<Studentz> studentzs = Arrays.asList(new Studentz("Dog"),new Studentz("Tree"), new Studentz("Moutain"));
		Function<List<Studentz>, StudentRespose> resposeFunction = studentzs1 -> {

			StudentRespose respose = new StudentRespose();
			respose.setName(new ArrayList<String>(studentzs1.stream().map(m->m.getName()).toList()));
			return respose;
		};
		System.out.println("resposeFunction " +resposeFunction.apply(studentzs));


		Function<String, String> funIdentity = Function.identity();
		System.out.println("funIdentity " +funIdentity.apply("books"));


		Predicate<Integer> integerPredicate = x -> x % 2 == 0;
		Function<Integer, Integer> integerFunction = x->x * x;
		Consumer<Integer> integerConsumer=x->System.out.println(x);
		Supplier<Integer> integerSupplier=()->100;

		if(integerPredicate.test(integerSupplier.get())){
			integerConsumer.accept(integerFunction.apply(integerSupplier.get()));
		}


		Optional<List<String>> empty = Optional.empty();
		//empty.get().add("hum"); // it raises Exception java.util.NoSuchElementException : No value present

		Optional<String> stringOptional = Optional.of("hum");
		//Optional<Object> optionalofnull = Optional.of(null);// it raises null pointer exception[box[null] Exception in thread "main" java.lang.NullPointerException[ it mean we can not pass null as argument in of method of Optional]

		//System.out.println("optionalofnull "+ optionalofnull);
		Optional<String> stringOptional1 = Optional.ofNullable("");
		if(stringOptional1.isPresent()){
			System.out.println(stringOptional1.get());
		}
		Optional<Object> optionalnull = Optional.ofNullable(null);// it do not raises null pointer exception[it prints  empty box/space on console]
		if(optionalnull.isPresent()){
			System.out.println(optionalnull.get());
		}
		Optional<String> hum = Optional.ofNullable("hum");
		if(hum.isPresent()){
			System.out.println(hum.get());
		}

	}
}
@Data
class StudentRespose{

	private List<String> name;

}
class Studentz{
	private String name;

	public Studentz(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}
}
