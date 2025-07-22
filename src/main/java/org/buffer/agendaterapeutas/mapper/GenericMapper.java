package org.buffer.agendaterapeutas.mapper;

import java.util.List;

public interface GenericMapper<E, BO,VO> {
    BO toBO(E entity);
    E toEntity(BO bo);
    VO toVO(BO bo);


    default List<BO> mapList(List<E> sourceList) {
        if (sourceList == null) return null;
        return sourceList.stream()
                .map(this::toBO)
                .toList();
    }

    default List<E> reverseMapList(List<BO> targetList) {
        if (targetList == null) return null;
        return targetList.stream()
                .map(this::toEntity)
                .toList();
    }
}
