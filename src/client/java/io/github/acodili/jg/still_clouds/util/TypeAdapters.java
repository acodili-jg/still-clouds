package io.github.acodili.jg.still_clouds.util;

import java.io.IOException;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * {@code TypeAdapters} is a utility class around {@link TypeAdapter} and
 * {@link TypeAdapterFactory}.
 */
public final class TypeAdapters {
    /**
     * The type adapter for {@link Duration} objects.
     */
    public static final TypeAdapter<Duration> DURATION;

    /**
     * The type adapter factory for {@link Duration} objects.
     */
    public static final TypeAdapterFactory DURATION_FACTORY;

    static {
        DURATION = new TypeAdapter<Duration>() {
			@Override
			public Duration read(final JsonReader reader) throws IOException {
                return switch (reader.peek()) {
                case NULL -> null;
                case STRING -> Duration.parse(reader.nextString());
                default -> throw new JsonParseException("Unexpected token " + reader.peek() +
                        " while parsing for java.time.Duration");
                };
			}

			@Override
			public void write(final JsonWriter writer, final Duration duration) throws IOException {
                if (duration != null)
				    writer.value(duration.toString());
                else
                    writer.nullValue();
			}
        };

        DURATION_FACTORY = newFactory(Duration.class, DURATION);
    }

    /**
     * Creates a simple type adapter factory for a single class.
     * 
     * @param <TT>    the target type
     * @param clazz   the class
     * @param adapter the type adapter
     * @return a type adapter factory
     */
    public static <TT> TypeAdapterFactory newFactory(final Class<TT> clazz,
            final TypeAdapter<? super TT> adapter) {
        return new TypeAdapterFactory() {
			@Override
            @SuppressWarnings("unchecked")
			public <T> TypeAdapter<T> create(final Gson gson, TypeToken<T> typeToken) {
				if (clazz.isAssignableFrom(typeToken.getRawType()))
                    return (TypeAdapter<T>) adapter;
                else
                    return null;
			}
        };
    }

    /**
     * Constructs a new {@code TypeAdapters} instance.
     */
    private TypeAdapters() {
    }
}
