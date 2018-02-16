package com.example.demo.specs;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.toArray;

/**
 * //定义Specification（自定义Repository实现）
 */
public class CustomerSpecs {

    /**
     *1、定义一个返回值为specification的方法byAuto，这里使用的泛型T,所以这个specification是可以用于任意的实体类的。
     它接受的参数是entityManager和当前的包含值作为查询条件的实体类对象
     */
    public static <T> Specification<T> byAuto(final EntityManager entityManager, final T example) {
        //2、获得当前实体类的对象类的类型
        final Class<T> type = (Class<T>) example.getClass();
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 3、新建Predicate列表存储结构的查询条件
                List<Predicate> predicates = new ArrayList<Predicate>();
                // 4、获得实体类EntityType，可以从EntityType获得实体类的属性
                EntityType<T> entity = entityManager.getMetamodel().entity(type);
                // 5、对实体类的所有属性做循环
                for (Attribute<T, ?> attr : entity.getDeclaredAttributes()) {
                    // 6、获得实体类对象某一个属性的值
                    Object attrValue = getValue(example, attr);
                    if (attrValue != null) {
                        // 7、当前属性为字符串类型的时候
                        if (attr.getJavaType() == String.class) {
                            if (!StringUtils.isEmpty(attrValue)) {
                               //8、 构造当前属性like (前后%)属性值查询条件，并添加到条件列表中
                                predicates.add(cb.like(root.get(attribute(entity, attr.getName(), String.class)), pattern((String) attrValue)));
                            } else {
                                //9、其余情况下，构造属性和属性值equal查询条件，并添加到条件列表中
                                predicates.add(cb.equal(root.get(attribute(entity, attr.getName(), attrValue.getClass())), attrValue));
                            }
                        }
                    }
                }
                // 将条件列表转换成Predicate
                return predicates.isEmpty() ? cb.conjunction() : cb.and(toArray(predicates, Predicate.class));
            }
        };
    }

    /**
     * 通过反射获得实体类对象对应属性的属性值
     * @param example
     * @param attr
     * @param <T>
     * @return
     */
    private static <T> Object getValue(T example, Attribute<T, ?> attr) {
        return ReflectionUtils.getField((Field) attr.getJavaMember(), example);
    }

    /**
     *
     * 获得实体类的当前属性SingularAttribute，SingularAttribute包含的是实体类的某个单独属性
     * @param entity
     * @param fieldName
     * @param fieldClass
     * @param <E>
     * @param <T>
     * @return
     */
    private static <E, T> SingularAttribute<T, E> attribute(EntityType<T> entity, String fieldName, Class<E> fieldClass) {
        return entity.getDeclaredSingularAttribute(fieldName, fieldClass);
    }

    /**
     * 构造like的查询模式，即前后加%
     * @param str
     * @return
     */
    static private String pattern(String str) {
        return "%" + str + "%";
    }
}
