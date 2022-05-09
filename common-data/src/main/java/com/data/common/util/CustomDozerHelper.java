package com.data.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dozer.Mapper;

public class CustomDozerHelper {

	private CustomDozerHelper() {
	}

	/**
	 * Copy list of source objects into list of destination class & return dest
	 * list.
	 */
	public static <F, T> List<T> map(final Mapper mapper, final List<F> source, final Class<T> destType) {

		final List<T> dest = new ArrayList<>();
		source.forEach(object -> {
			if (object != null) {
				T target = mapper.map(object, destType);
				dest.add(target);
			}
		});
		dest.removeIf(Objects::isNull);
		return dest;
	}

}