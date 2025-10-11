package embinmc.javaengine.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import embinmc.javaengine.resource.Identifier;

import java.util.*;
import java.util.function.Function;

public class MoreCodecs {
    public static Codec<Integer> POSITIVE_INT = rangedInt(1, Integer.MAX_VALUE, v -> "Value \"" + v + "\" must be positive");
    public static Codec<Integer> NON_NEGATIVE_INT = rangedInt(0, Integer.MAX_VALUE, v -> "Value \"" + v + "\" mustn't be negative");
    public static final Codec<Map<Identifier, Integer>> ID_MAPPED_INT = Codec.unboundedMap(Identifier.getCodec(), Codec.INT);
    public static final Codec<Map<Identifier, String>> ID_MAPPED_STRING = Codec.unboundedMap(Identifier.getCodec(), Codec.STRING);

    private static Codec<Integer> rangedInt(int min, int max, Function<Integer, String> message) {
        return Codec.INT.validate(
                value -> value.compareTo(min) >= 0 && value.compareTo(max) <= 0 ?
                        DataResult.success(value) :
                        DataResult.error(() -> message.apply(value))
        );
    }

    public static Codec<Integer> rangedInt(int min, int max) {
        return rangedInt(min, max, value -> "Value must be within range [" + min + ";" + max + "]: " + value);
    }

    public static <T> Codec<List<T>> listOrSingle(Codec<T> entryCodec) {
        return Codec.either(entryCodec.listOf(), entryCodec)
                .xmap(either -> either.map(list -> list, List::of), list -> list.size() == 1 ? Either.right(list.getFirst()) : Either.left(list));
    }

    public static <I, E> Codec<E> checkedId(Codec<I> codecOfId, Function<I, E> idToElement, Function<E, I> elementToId) {
        return codecOfId.flatXmap(id -> {
            E object = idToElement.apply(id);
            if (object != null) {
                return DataResult.success(object);
            }
            return DataResult.error(() -> String.format("Unknown element ID: %s", id));
        }, element -> {
            I object = elementToId.apply(element);
            if (object != null) {
                return DataResult.success(object);
            }
            return DataResult.error(() -> String.format("Element with unknown ID: %s", element));
        });
    }

    public static class IdMapper<I, V> {
        private final BiMap<I, V> values = HashBiMap.create();

        public IdMapper() {
        }

        public Codec<V> getCodec(Codec<I> idCodec) {
            BiMap<V, I> biMap = this.values.inverse();
            BiMap<I, V> values = this.values;
            Objects.requireNonNull(values);
            Function<I, V> func = values::get;
            Objects.requireNonNull(biMap);
            return MoreCodecs.checkedId(idCodec, func, biMap::get);
        }

        public IdMapper<I, V> put(I id, V value) {
            Objects.requireNonNull(value, () -> String.format("Value of %s is null!", id));
            this.values.put(id, value);
            return this;
        }

        public Set<V> valuesSet() {
            return Collections.unmodifiableSet(this.values.values());
        }
    }
}
