package com.tms.common.mapper;

import lombok.Data;
import lombok.SneakyThrows;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Component
public class OrikaBeanMapper {

    @Autowired
    private MapperFactory mapperFactory;

    @SneakyThrows
    public <R, T> R map(T sourceObj, Class<R> r) {
        mapperFactory.classMap(Class.forName(sourceObj.getClass().getName()), r);
        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        return mapperFacade.map(sourceObj, r);
    }

    public <R, T> Collection<R> mapAsCollection(Collection<T> sourceCollection, Class<R> r) {
        return sourceCollection.stream().map(s -> map(s, r)).collect(Collectors.toList());
    }

    public <R, T> List<R> mapAsList(List<T> sourceCollection, Class<R> r) {
        return sourceCollection.stream().map(s -> map(s, r)).collect(Collectors.toList());
    }
}
