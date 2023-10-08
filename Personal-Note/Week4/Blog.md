Function interface in Java

-   Function (T to R)
-   Consumer (T to void)
-   Predicate (T to boolean)
-   Supplier (... to R) //not sure

```java
	Function<String, Integer> func1 = x -> x.length();
```

```java
	Consumer<String> print = x -> System.out.println(x);
```

```java
	Predicate<Integer> myPass = mark -> mark >= 50;
```

```java
	List<Integer> passMarks = listMarks.stream()
                                        .filter(myPass)
				                        .collect(Collectors.toList());
```
