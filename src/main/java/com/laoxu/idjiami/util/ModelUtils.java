package com.laoxu.idjiami.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import com.laoxu.idjiami.annotation.IdEncryption;
import org.apache.commons.lang3.ClassUtils;

/**
 * @Author: YLBG-LDH-1506
 * @Description:
 * @Date: 2018/05/16
 */
public class ModelUtils {

    /**
     * do转model 此方法
     * @param d do
     * @param clazz model的class
     * @param <M>
     * @param <D>
     * @return 如果d是null则返回null
     */
    public static <M, D> M toModel(D d, Class<M> clazz) {
        if (null == d) {
            return null;
        }
        return ReflectUtils.copyProperties(d, clazz, CONVERTER_ID_ENCRYPT);
    }

    /**
     * model转do 此方法
     * @param m model
     * @param clazz do的class
     * @param <M>
     * @param <D>
     * @return
     * @deprecated 请使用cn.ucmed.yilian.common.utils.ModelUtils#toModel(java.lang.Object,
     *             java.lang.Class)
     */
    @Deprecated
    public static <M, D> D toDo(M m, Class<D> clazz) {
        if (null == m) {
            return null;
        }
        return ReflectUtils.copyProperties(m, clazz, CONVERTER_ID_ENCRYPT);
    }

    /**
     * 转list类型的
     * @param ds
     * @param clazz
     * @param <M>
     * @param <D>
     * @return
     */
    public static <M, D> List<M> toModels(List<D> ds, Class<M> clazz) {
        if (CollectionUtils.isEmpty(ds)) {
            return Lists.newArrayList();
        }
        return ds.stream().map(d -> toModel(d, clazz)).collect(Collectors.toList());
    }

    /**
     * 拷贝pageInfo
     * @param source 源pageinfo
     * @param clazz 目标pageInfo的泛型类型
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> PageInfo<T> copyPager(PageInfo<S> source, Class<T> clazz) {
        PageInfo<T> res = ReflectUtils.copyProperties(source, PageInfo.class, null);
        res.setList(toModels(source.getList(), clazz));
        return res;
    }

    // 这是Idconverter用于处理id为
    public static final ReflectUtils.TriFunction<Field, Field, Object, Object> CONVERTER_ID_ENCRYPT = (
            sf, tf, v) -> {
        if (tf.isAnnotationPresent(IdEncryption.class)
                && !sf.isAnnotationPresent(IdEncryption.class) && null != v) {
            return IdHandler.idEncrypt(v.toString());
        }
        if (sf.isAnnotationPresent(IdEncryption.class) && null != v
                && tf.getType().equals(Long.class)) {
            return IdHandler.idDecryptToLong(v.toString());
        }
        if (sf.isAnnotationPresent(IdEncryption.class) && null != v
                && tf.getType().equals(Integer.class)) {
            return IdHandler.idDecryptToInteger(v.toString());
        }
        if (ClassUtils.isAssignable(sf.getType(), Enum.class)
                && ClassUtils.isAssignable(sf.getType(), AbstractCode.class)
                && (tf.getType().equals(int.class) || tf.getType().equals(Integer.class))
                && null != v) {
            return ((AbstractCode) v).getCode();
        }
        if (ClassUtils.isAssignable(tf.getType(), Enum.class)
                && ClassUtils.isAssignable(tf.getType(), AbstractCode.class)
                && sf.getType().equals(int.class)) {
            return EnumUtils.getByProperty((Class<Enum>) tf.getType(), v,
                    (e) -> ((AbstractCode) e).getCode());
        }
        if (ClassUtils.isAssignable(tf.getType(), Enum.class)
                && ClassUtils.isAssignable(tf.getType(), AbstractCode.class)
                && sf.getType().equals(Integer.class) && null != v) {
            return EnumUtils.getByProperty((Class<Enum>) tf.getType(), v,
                    (e) -> ((AbstractCode) e).getCode());
        }
        return v;
    };
}

