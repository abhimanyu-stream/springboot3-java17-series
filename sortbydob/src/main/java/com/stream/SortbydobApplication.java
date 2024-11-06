package com.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

@SpringBootApplication
public class SortbydobApplication {

	public static void main(String[] args) {
		SpringApplication.run(SortbydobApplication.class, args);


		//Employee e1 = new Employee("Oracle", LocalDate.of(2024,10, 10));
		//Employee e2 = new Employee("Oracle", LocalDate.of(2024,10, 01));


		//List<Employee> listOfEmployee = new ArrayList<>();
		//listOfEmployee.add(e1);
		//listOfEmployee.add(e2);
		List<Employee> listOfEmployee = Arrays.asList(
				new Employee("Oracle", LocalDate.of(2024,10, 10)),
				new Employee("Oracle", LocalDate.of(2024,10, 01))
		);

		// Sort Employee by DOB using Comparator
		System.out.println(listOfEmployee.stream().sorted(Comparator.comparing(Employee::getDob).reversed()).collect(Collectors.toList()));
		System.out.println(listOfEmployee.stream().sorted( new DobComparator()).collect(Collectors.toList()));


		Comparator<Employee> reverseComparator = new Comparator<>() {
			@Override
			public int compare(Employee o1, Employee o2) {
				return o2.getDob().getYear() - o1.getDob().getYear();
			}
			// You can override other methods too


		};
		System.out.println(listOfEmployee.stream().sorted( reverseComparator).collect(Collectors.toList()));
	}

}
class DobComparator implements Comparator<Employee>{

	@Override
	public int compare(Employee o1, Employee o2) {
		return o2.getDob().getYear() - o1.getDob().getYear();
	}

	@Override
	public Comparator<Employee> reversed() {
		return Comparator.super.reversed();
	}

	@Override
	public Comparator<Employee> thenComparing(Comparator<? super Employee> other) {
		return Comparator.super.thenComparing(other);
	}

	@Override
	public <U> Comparator<Employee> thenComparing(Function<? super Employee, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
		return Comparator.super.thenComparing(keyExtractor, keyComparator);
	}

	@Override
	public <U extends Comparable<? super U>> Comparator<Employee> thenComparing(Function<? super Employee, ? extends U> keyExtractor) {
		return Comparator.super.thenComparing(keyExtractor);
	}

	@Override
	public Comparator<Employee> thenComparingInt(ToIntFunction<? super Employee> keyExtractor) {
		return Comparator.super.thenComparingInt(keyExtractor);
	}

	@Override
	public Comparator<Employee> thenComparingLong(ToLongFunction<? super Employee> keyExtractor) {
		return Comparator.super.thenComparingLong(keyExtractor);
	}

	@Override
	public Comparator<Employee> thenComparingDouble(ToDoubleFunction<? super Employee> keyExtractor) {
		return Comparator.super.thenComparingDouble(keyExtractor);
	}
}
