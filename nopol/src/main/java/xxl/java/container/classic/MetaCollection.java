package xxl.java.container.classic;

import static java.util.Arrays.asList;
import static xxl.java.library.ClassLibrary.newInstance;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

public class MetaCollection {
	
	public static <T> Collection<T> withMany(Collection<T> destination, T element, int repetitions) {
		addMany(destination, element, repetitions);
		return destination;
	}
	
	@SafeVarargs
	public static <T> Collection<T> withAll(Collection<T> destination, T... elements) {
		addAll(destination, elements);
		return destination;
	}
	
	public static <T> Collection<T> withAll(Collection<T> destination, Iterable<? extends T> elements) {
		addAll(destination, elements);
		return destination;
	}
	
	public static <T> Collection<T> withAll(Collection<T> destination, Enumeration<? extends T> elements) {
		addAll(destination, elements);
		return destination;
	}
	
	@SafeVarargs
	public static <T> Collection<T> withAllFlat(Collection<T> destination, Collection<? extends T>... collections) {
		addAllFlat(destination, collections);
		return destination;
	}
	
	public static <T> Collection<T> withAllFlat(Collection<T> destination, Collection<Iterable<? extends T>> collections) {
		addAllFlat(destination, collections);
		return destination;
	}
	
	public static <T> void addMany(Collection<T> collection, T element, int repetitions) {
		if (repetitions > 0) {
			for (int repetition = 0; repetition < repetitions; repetition += 1) {
				collection.add(element);
			}
		}
	}
	
	@SafeVarargs
	public static <T> boolean addAll(Collection<T> destination, T... elements) {
		return addAll(destination, asList(elements));
	}
	
	public static <T> boolean addAll(Collection<T> destination, Iterable<? extends T> elements) {
		boolean changed = false;
		for (T element : elements) {
			changed = changed | destination.add(element);
		}
		return changed;
	}
	
	public static <T> boolean addAll(Collection<T> destination, Enumeration<? extends T> elements) {
		boolean changed = false;
		while (elements.hasMoreElements()) {
			changed = changed | destination.add(elements.nextElement());
		}
		return changed;
	}
	
	@SafeVarargs
	public static <T> boolean addAllFlat(Collection<T> destination, Collection<? extends T>... collections) {
		return addAllFlat(destination, asIterables(collections));
	}
	
	public static <T> boolean addAllFlat(Collection<T> destination, Collection<Iterable<? extends T>> collections) {
		boolean changed = false;
		for (Iterable<? extends T> collection : collections) {
			changed = changed | addAll(destination, collection);
		}
		return changed;
	}
	
	@SafeVarargs
	public static <T> Collection<Iterable<? extends T>> asIterables(Collection<? extends T>... collections) {
		Collection<Iterable<? extends T>> asIterables = MetaList.newArrayList(collections.length);
		for (Collection<? extends T> collection : collections) {
			asIterables.add(collection);
		}
		return asIterables;
	}
	
	public static int combinedSize(Collection<?>... collections) {
		int size = 0;
		for (Collection<?> collection : collections) {
			size += collection.size();
		}
		return size;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Class<T> aClass, Collection<T> aCollection) {
		T[] array = (T[]) Array.newInstance(aClass, aCollection.size());
		int index = 0;
		for (T element : aCollection) {
			array[index] = element;
			index += 1;
		}
		return array;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> Collection<T> copyOf(Collection<T> collection) {
		Class<? extends Collection> collectionClass = collection.getClass();
		if (collectionClass.getName().equals("java.util.Arrays$ArrayList")) {
			collectionClass = ArrayList.class;
		}
		Collection<T> copy = newInstance(collectionClass);
		copy.addAll(collection);
		return copy;
	}
	
	public static <T> int repetitions(Collection<T> collection, T targetElement) {
		int repetitions = 0;
		for (T element : collection) {
			if (element.equals(targetElement)) {
				repetitions += 1;
			}
		}
		return repetitions;
	}
	
	public static <T> T any(Collection<T> collection) {
		for (T element : collection) {
			return element;
		}
		return null;
	}
}