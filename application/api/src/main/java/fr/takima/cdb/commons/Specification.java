package fr.takima.cdb.commons;

public interface Specification<E>{
    boolean isSatisfiedBy(E entity);
}