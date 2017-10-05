package org.dudar.model.entity;

/**
 * Basic interface for GoF IBuilder pattern realization
 *
 * Class that wants to use this pattern for its instances creation should
 * implement this interface
 *
 * @author Dmitry
 *
 * @param <T> the generic type of the creating object
 */

public interface IBuilder<T> {
    T build();
}
