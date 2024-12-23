package com.font.specificationexecutor;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

@Transactional(readOnly = true)
public abstract class SpecExecutor<ENTITY> {
    public SpecExecutor() {}


    /* Generic Filter - build */
    protected <X> Specification<ENTITY> buildSpecification(Filter<X> filter, SingularAttribute<? super ENTITY, X> field) {
        return this.buildSpecification(filter, (root) -> root.get(field));
    }

    protected <X> Specification<ENTITY> buildSpecification(Filter<X> filter, Function<Root<ENTITY>, Expression<X>> metaclassFunction) {
        if(!Objects.isNull(filter.getEquals())) {
            return equalsSpecification(metaclassFunction, filter.getEquals());
        }
        if(!Objects.isNull(filter.getNotEquals())) {
            return notEqualsSpecification(metaclassFunction, filter.getNotEquals());
        }
        if(!Objects.isNull(filter.getIn())) {
            return valueInSpecification(metaclassFunction, filter.getIn());
        }
        if(!Objects.isNull(filter.getNotIn())) {
            return valueNotInSpecification(metaclassFunction, filter.getNotIn());
        }
        if(!Objects.isNull(filter.getIsNotNull())) {
            return isNotNullSpecification(metaclassFunction, filter.getIsNotNull());
        }

        throw new RuntimeException("FILTER DOES NOT CONTAIN FILTERABLE FIELDS.");
    }

    /* String Filter - build */
    protected Specification<ENTITY> buildSpecification(StringFilter filter, SingularAttribute<? super ENTITY, String> field) {
        return this.buildStringSpecification(filter, (root) -> root.get(field));
    }

    protected Specification<ENTITY> buildStringSpecification(StringFilter filter, Function<Root<ENTITY>, Expression<String>> metaclassFunction) {
        if(!Objects.isNull(filter.getContains())) {
            return likeUpperSpecification(metaclassFunction, filter.getContains());
        } else if(!Objects.isNull(filter.getDoesNotContain())){
            return notLikeUpperSpecification(metaclassFunction, filter.getDoesNotContain());
        } else {
            return buildSpecification(filter, metaclassFunction);
        }
    }

    /* Range Filter - build */
    protected <X extends Comparable<? super X>> Specification<ENTITY> buildSpecification(RangeFilter<X> filter, SingularAttribute<? super ENTITY, X> field) {
        return this.buildRangeSpecification(filter, (root) -> root.get(field));
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> buildRangeSpecification(RangeFilter<X> filter, Function<Root<ENTITY>, Expression<X>> metaclassFunction) {
        if(!Objects.isNull(filter.getGreaterThan())) {
            return greaterThanSpecification(metaclassFunction, filter.getGreaterThan());
        } else if (!Objects.isNull(filter.getGreaterThanOrEqual())) {
            return greaterThanOrEqualSpecification(metaclassFunction, filter.getGreaterThanOrEqual());
        } else if (!Objects.isNull(filter.getLessThan())) {
            return lessThanSpecification(metaclassFunction, filter.getLessThan());
        } else if (!Objects.isNull(filter.getLessThanOrEqual())) {
            return lessThanOrEqualSpecification(metaclassFunction, filter.getLessThanOrEqual());
        } else {
            return buildSpecification(filter, metaclassFunction);
        }
    }


    /* Private methods - specifications */
    private <X> Specification<ENTITY> equalsSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> builder.equal(metaclassFunction.apply(root), value);
    }

    private <X> Specification<ENTITY> notEqualsSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> builder.notEqual(metaclassFunction.apply(root), value);
    }

    private <X> Specification<ENTITY> valueInSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, Collection<X> values) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<X> in = builder.in(metaclassFunction.apply(root));
            values.forEach(in::value);
            return in;
        };
    }

    private <X> Specification<ENTITY> valueNotInSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, Collection<X> values) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<X> in = builder.in(metaclassFunction.apply(root));
            values.forEach(in::value);
            return builder.not(in);
        };
    }

    private <X> Specification<ENTITY> isNotNullSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, boolean isNotNull) {
        return isNotNull ?
                (root, query, builder) -> builder.isNotNull(metaclassFunction.apply(root)) :
                (root, query, builder) -> builder.isNull(metaclassFunction.apply(root));
    }

    private <X> Specification<ENTITY> likeUpperSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction, String value) {
        return (root, query, builder) -> builder.like(builder.upper(metaclassFunction.apply(root)), wrapLikeQuery(value));
    }

    private <X> Specification<ENTITY> notLikeUpperSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction, String value) {
        return (root, query, builder) -> builder.not(builder.like(builder.upper(metaclassFunction.apply(root)), wrapLikeQuery(value)));
    }

    protected String wrapLikeQuery(String txt) {
        return "%" + txt.toUpperCase() + '%';
    }

    private <X extends Comparable<? super X>> Specification<ENTITY> greaterThanSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> builder.greaterThan(metaclassFunction.apply(root), value);
    }

    private <X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(metaclassFunction.apply(root), value);
    }

    private <X extends Comparable<? super X>> Specification<ENTITY> lessThanSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> builder.lessThan(metaclassFunction.apply(root), value);
    }

    private <X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(metaclassFunction.apply(root), value);
    }
}
