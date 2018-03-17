/*
 * 
 * 
 * 
 */
package com.go.core.base.impl;


import com.go.core.base.BaseService;
import com.go.core.util.Filter;
import com.go.core.util.Order;
import com.go.core.util.Page;
import com.go.core.util.Pageable;
import com.go.entity.OrderEntity;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Service - 基类
 */
@Transactional
public class BaseServiceImpl<T> implements BaseService<T, ID> {


    /**
     * 更新忽略属性
     */
    public static final String[] UPDATE_IGNORE_PROPERTIES = new String[]{};

    /**
     * 别名数
     */
    private static volatile long aliasCount = 0;

    /**
     * 实体类类型
     */
    private Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public T find(ID id) {
        if (id != null) {
            return entityManager.find(entityClass, id);
        }
        return null;
    }

    //TODO 逻辑
    public List<T> findAll() {
        return null;
    }

    //TODO 逻辑
    public List<T> findList(ID... ids) {
        return null;
    }

    //TODO 逻辑
    public List<T> findList(Integer count, List<Filter> filters, List<Order> orders) {
        return null;
    }

    //TODO 逻辑
    public List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
        return null;
    }

    public Page<T> findPage(com.go.core.util.Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return findPage(criteriaQuery, pageable);
    }

    protected Page<T> findPage(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
        Assert.notNull(criteriaQuery);
        Assert.notNull(criteriaQuery.getSelection());
        Assert.notEmpty(criteriaQuery.getRoots());

        if (pageable == null) {
            pageable = new Pageable();
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        Root<T> root = getRoot(criteriaQuery, entityClass);

        if (criteriaQuery.getOrderList().isEmpty()) {
            if (OrderEntity.class.isAssignableFrom(entityClass)) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(OrderEntity.ORDER_PROPERTY_NAME)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("")));
            }
        }
        long total = count(criteriaQuery, null);
        int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
        if (totalPages < pageable.getPageNumber()) {
            pageable.setPageNumber(totalPages);
        }
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
        query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        return new Page<T>(query.getResultList(), total, pageable);
    }


    protected Long count(CriteriaQuery<T> criteriaQuery, List<Filter> filters) {
        Assert.notNull(criteriaQuery);
        Assert.notNull(criteriaQuery.getSelection());
        Assert.notEmpty(criteriaQuery.getRoots());

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        for (Root<?> root : criteriaQuery.getRoots()) {
            Root<?> dest = countCriteriaQuery.from(root.getJavaType());
            dest.alias(getAlias(root));
            copyJoins(root, dest);
        }

        Root<?> countRoot = getRoot(countCriteriaQuery, criteriaQuery.getResultType());
        countCriteriaQuery.select(criteriaBuilder.count(countRoot));

        if (criteriaQuery.getGroupList() != null) {
            countCriteriaQuery.groupBy(criteriaQuery.getGroupList());
        }
        if (criteriaQuery.getGroupRestriction() != null) {
            countCriteriaQuery.having(criteriaQuery.getGroupRestriction());
        }
        if (criteriaQuery.getRestriction() != null) {
            countCriteriaQuery.where(criteriaQuery.getRestriction());
        }
        return entityManager.createQuery(countCriteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
    }

    private Root<T> getRoot(CriteriaQuery<?> criteriaQuery, Class<T> clazz) {
        if (criteriaQuery != null && criteriaQuery.getRoots() != null && clazz != null) {
            for (Root<?> root : criteriaQuery.getRoots()) {
                if (clazz.equals(root.getJavaType())) {
                    return (Root<T>) root.as(clazz);
                }
            }
        }
        return null;
    }


    private void copyJoins(From<?, ?> from, From<?, ?> to) {
        for (Join<?, ?> join : from.getJoins()) {
            Join<?, ?> toJoin = to.join(join.getAttribute().getName(), join.getJoinType());
            toJoin.alias(getAlias(join));
            copyJoins(join, toJoin);
        }
        for (Fetch<?, ?> fetch : from.getFetches()) {
            Fetch<?, ?> toFetch = to.fetch(fetch.getAttribute().getName());
            copyFetches(fetch, toFetch);
        }
    }

    private void copyFetches(Fetch<?, ?> from, Fetch<?, ?> to) {
        for (Fetch<?, ?> fetch : from.getFetches()) {
            Fetch<?, ?> toFetch = to.fetch(fetch.getAttribute().getName());
            copyFetches(fetch, toFetch);
        }
    }

    private synchronized String getAlias(Selection<?> selection) {
        if (selection != null) {
            String alias = selection.getAlias();
            if (alias == null) {
                if (aliasCount >= 1000) {
                    aliasCount = 0;
                }
                alias = "GeneratedAlias" + aliasCount++;
                selection.alias(alias);
            }
            return alias;
        }
        return null;
    }


    public boolean exists(ID id) {
        return this.find(id) != null;
    }


    public void save(T entity) {
        Assert.notNull(entity);
        entityManager.persist(entity);
    }

    public T update(T entity) {
        Assert.notNull(entity);
        return entityManager.merge(entity);
    }

    public T update(T entity, String... ignoreProperties) {
        Assert.notNull(entity);
        if (this.isManaged(entity)) {
            throw new IllegalArgumentException("Entity must not be managed");
        }
        T persistant = this.find(this.getIdentifier(entity));
        if (persistant != null) {
            copyProperties(entity, persistant, (String[]) ArrayUtils.addAll(ignoreProperties, UPDATE_IGNORE_PROPERTIES));
            return update(persistant);
        } else {
            return update(entity);
        }
    }

    public void delete(ID id) {
        delete(this.find(id));
    }

    public void delete(ID... ids) {
        if (ids != null) {
            for (ID id : ids) {
                delete(this.find(id));
            }
        }
    }

    public void delete(T entity) {
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    public boolean isManaged(T entity) {
        return entityManager.contains(entity);
    }

    @SuppressWarnings("unchecked")
    public ID getIdentifier(T entity) {
        Assert.notNull(entity);
        return (ID) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void copyProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(target.getClass());
        List<String> ignoreList = (ignoreProperties != null) ? new ArrayList<String>(Arrays.asList(ignoreProperties)) : null;
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object sourceValue = readMethod.invoke(source);
                        Object targetValue = readMethod.invoke(target);
                        if (sourceValue != null && targetValue != null && targetValue instanceof Collection) {
                            Collection collection = (Collection) targetValue;
                            collection.clear();
                            collection.addAll((Collection) sourceValue);
                        } else {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, sourceValue);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }
}